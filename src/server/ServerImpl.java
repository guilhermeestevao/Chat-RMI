package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import client.Client;

public class ServerImpl implements Server {

	
	private List<Client> clients;
		
	public ServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		clients = new ArrayList<Client>();
	}

	public void registerCliente(Client client) throws RemoteException {
		// TODO Auto-generated method stub
		addClient(client);
		
	}

	public void unRegisterClient(Client client) throws RemoteException {
		// TODO Auto-generated method stub
		removeClient(client);
	}

	public void sendMessage(Message message) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Recived: "+message.getName());
		System.out.println("Received : "+message.getContent());
		sendToEveryBody(message);
	}
	
	private synchronized void addClient(Client client){
		
		if(clients != null){
			System.out.println(clients.size());
			if(!clients.contains(client))
				clients.add(client);
		}
		
	}
	
	private synchronized void removeClient(Client client){
		clients.remove(client);
	}

	private void sendToEveryBody(Message message){
		for (Client client : clients) {
			try {
				client.notify(message);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				continue;
			}
		}
	}
	
}
