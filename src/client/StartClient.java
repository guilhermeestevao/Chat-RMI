package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import server.Message;
import server.Server;

public class StartClient {
	
	private static final String[] MENU = {
		"1 - Registra-se",  
		"2 - Enviar mensagem", 
		"3 - Ver mesnagens recebidas", 
		"4 - Sair" 
	};
	
	private static final int REGISTRY = 1;
	private static final int SEND_MESSAGE = 2;
	private static final int SEE_MESSAGES = 3;
	private static final int OUT = 4;
			
	private static Server server;
	private static Client client;
	
	private JFrame mainWindow;
	private JPanel contentPanel;
	private JButton unRegisterButton;
	private JButton registerButton;
	
	public static void main(String[] args) {
		
		new StartClient().buildWindow();
		
		
		try {
			
			int opt = 0;
			
			while(opt != OUT){
				opt = showOptions();
				
				if (opt == REGISTRY)
					registerOnServer();
				else if(opt == SEND_MESSAGE)
					sendMessage();
				else if(opt == SEE_MESSAGES)
					System.out.println();
				else if(opt == OUT){
					unregisterServer(); 
				}else
					System.out.println("Opção invalida");
				
			}
			
		} catch (IOException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void registerOnServer() throws IOException, NotBoundException{
		        
		String hostName;
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		System.out.println("Hostname:");
		hostName = br.readLine();
		
		String registryURL = "rmi://localhost:" + 1099 + "/chat";  
		server = (Server)Naming.lookup(registryURL);
		client = new ClientImpl(hostName, null);
		server.registerCliente(client);
		
	}
	
	private static void unregisterServer() throws RemoteException{
		server.unRegisterClient(client);
	}

	private static int showOptions() throws IOException{
		for (String option : MENU) {
			System.out.println(option);
		}
		
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		String portNum = br.readLine();
		int opt  = Integer.parseInt(portNum); 
		return opt;
	}
	
	private static void sendMessage() throws IOException{
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		String text = br.readLine();
		
		Message msg = new Message(client.getName(), text);
		
		server.sendMessage(msg);
	}
	
	
	private void buildWindow(){
		prepareWindow();
		prepareContentPanel();
		prepareRegisterButton();
		prepareUnregisterButton();
		showWindow();
	}
	
	private void prepareWindow(){
		mainWindow = new JFrame("Chat RMI");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void showWindow(){
		mainWindow.pack();
		mainWindow.setSize(540, 540);
	    mainWindow.setVisible(true);
	}
	
	private void prepareContentPanel() {
		contentPanel = new JPanel();
		mainWindow.add(contentPanel);
	}
	
	private void prepareRegisterButton() {
		registerButton = new JButton("Registrar-se");
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unRegisterButton.setEnabled(true);
				registerButton.setEnabled(false);
			}
		});
		contentPanel.add(registerButton);
	}

	private void prepareUnregisterButton() {
		unRegisterButton = new JButton("Sair");
		unRegisterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unRegisterButton.setEnabled(false);
				registerButton.setEnabled(true);
			}
		});
		unRegisterButton.setEnabled(false);
		contentPanel.add(unRegisterButton);
	}
	
	
}
