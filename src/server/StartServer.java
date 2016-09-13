package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {
	
	public static void main(String[] args) {
		
		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		String registryURL;
		int port = 1099; 
		try{     
			
			startRegistry(port);
			Server exportedObj = new ServerImpl();
			registryURL = "rmi://localhost:" + port + "/chat";
			Naming.rebind(registryURL, exportedObj);
			System.out.println("Server ready.");
			
		}catch (Exception e) {
			System.out.println("Exception in StartServer: " + e);
		} 
	}
	
	private static void startRegistry(int RMIPortNum) throws RemoteException{	  
		try {
			Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			registry.list( );    
		}catch (RemoteException e) {     
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
		}
	} 
	
}
