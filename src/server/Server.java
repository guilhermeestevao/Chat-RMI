package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.Client;

public interface Server extends Remote {
	
	public void sendMessage(Message message) throws RemoteException;
	public void registerCliente(Client client) throws RemoteException;
	public void unRegisterClient(Client client) throws RemoteException;
	
}
