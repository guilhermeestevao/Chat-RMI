package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import server.Message;

public interface Client extends Remote {
	
	public void notify(Message message) throws RemoteException;
	public String getName() throws RemoteException;
	public List<Message> getMessagesRecived() throws RemoteException;
	
}
