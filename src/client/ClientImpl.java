package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import server.Message;

public class ClientImpl extends UnicastRemoteObject implements Client {

	private static final long serialVersionUID = 1L;

	private List<Message> messages;
	
	private String name;
	private ListenerRecivedMessage listener;
	
	
	public ClientImpl(String name, ListenerRecivedMessage listener) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		messages = new ArrayList<Message>();
		this.name = name;
		this.listener = listener;
	}

	@Override
	public void notify(Message message) throws RemoteException {
		// TODO Auto-generated method stub
		listener.addMessage(message);
		messages.add(message);
	
	}

	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public List<Message> getMessagesRecived() throws RemoteException {
		// TODO Auto-generated method stub
		return this.messages;
	}
	
	public interface ListenerRecivedMessage{
		public void addMessage(Message message);
	}
	
}
