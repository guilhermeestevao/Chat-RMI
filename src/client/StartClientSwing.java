package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import server.Message;
import server.Server;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StartClientSwing extends JFrame implements ClientImpl.ListenerRecivedMessage{

	private JPanel panelHead;
	private JPanel panelBody;
	private JTextField txtNewMessage;
	private JTextField txtHostname;
	private JButton btnRegistrarse;
	private JButton btnSair;
	private JButton btnEnviar;
	private JTextArea txtrAllMessages;
	private static Server server;
	private static Client client;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartClientSwing frame = new StartClientSwing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartClientSwing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panelHead = new JPanel();
		panelHead.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelHead);
		panelHead.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 438, 34);
		panelHead.add(panel);
		panel.setLayout(null);

		btnRegistrarse = new JButton("Registrar-se");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSair.setEnabled(true);
				btnRegistrarse.setEnabled(false);
				txtNewMessage.setEnabled(true);
				btnEnviar.setEnabled(true);
				txtHostname.setEnabled(false);

				String hostName = txtHostname.getText();

				try {
					registerOnServer(hostName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});
		btnRegistrarse.setBounds(212, 6, 134, 29);
		panel.add(btnRegistrarse);

		btnSair = new JButton("Sair");
		btnSair.setBounds(344, 6, 88, 29);
		btnSair.setEnabled(false);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSair.setEnabled(false);
				btnRegistrarse.setEnabled(true);
				txtNewMessage.setEnabled(false);
				btnEnviar.setEnabled(false);
				txtHostname.setEnabled(true);

				try {
					unregisterServer();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel.add(btnSair);

		txtHostname = new JTextField();
		txtHostname.setBounds(5, 5, 206, 28);
		panel.add(txtHostname);
		txtHostname.setColumns(10);

		panelBody = new JPanel();
		panelBody.setBounds(6, 46, 438, 226);
		panelHead.add(panelBody);
		panelBody.setLayout(null);

		Action action = new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String text = txtNewMessage.getText();

				try {
					sendMessage(text);
					txtNewMessage.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		};

		txtNewMessage = new JTextField();
		txtNewMessage.setBounds(6, 190, 360, 28);
		txtNewMessage.setEnabled(false);
		txtNewMessage.addActionListener(action);
		panelBody.add(txtNewMessage);

		txtNewMessage.setColumns(10);

		btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(360, 191, 78, 29);
		btnEnviar.setEnabled(false);
		panelBody.add(btnEnviar);

		txtrAllMessages = new JTextArea();
		txtrAllMessages.setBounds(6, 6, 432, 166);
		txtrAllMessages.setEditable(false);
		txtrAllMessages.setLineWrap(true);
		
		scrollPane = new JScrollPane(txtrAllMessages);
		scrollPane.setBounds(6, 6, 432, 166);
		scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panelBody.add(scrollPane);
		
		btnEnviar.addActionListener(action);
	}

	private void registerOnServer(String hostName) throws IOException, NotBoundException{
		String registryURL = "rmi://localhost:" + 1099 + "/chat";  
		server = (Server)Naming.lookup(registryURL);
		client = new ClientImpl(hostName, this);
		server.registerCliente(client);
	}

	private void unregisterServer() throws RemoteException{
		server.unRegisterClient(client);
	}

	private void sendMessage(String text) throws IOException{		
		Message msg = new Message(client.getName(), text);
		server.sendMessage(msg);
	}

	@Override
	public void addMessage(Message message) {
		// TODO Auto-generated method stub
		addOutputMessage(message);
	}

	private void addOutputMessage(Message message){

		String appendIt = message.getName()+": \n" + message.getContent()+"\n\n";

		txtrAllMessages.append(appendIt);

	}
	
}