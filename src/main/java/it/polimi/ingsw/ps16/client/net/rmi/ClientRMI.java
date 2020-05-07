/*****************************************
 * 										 *
 * 		Council Of Four					 *
 * 										 *
 * 		Software Engineering Project	 *
 * 										 *
 * 		Politecnico di Milano 			 *
 * 										 *
 * 		Academic Year: 2015 - 2016		 *
 * 										 *
 * 		Authors: Gianola Giovanni		 *
 * 				 Leveni Filippo			 *
 * 				 Ionata Valentina		 *
 * 										 *
 ****************************************/
package it.polimi.ingsw.ps16.client.net.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.client.net.NetView;
import it.polimi.ingsw.ps16.server.view.rmi.RMIRemoteView;
import it.polimi.ingsw.ps16.server.view.rmi.ServerRMIRemoteView;

/**
 * The Class ClientRMI is the client that <br>
 * provide a connection with the server using RMI protocol.
 */
public class ClientRMI extends NetView
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( ClientRMI.class.getName() );
	
	/** The Constant NAME. */
	private static final String NAME = "Cof";
	
	/** The registry. */
	private Registry registry;
	
	/** The server connection. */
	private ServerRMIRemoteView serverConnection;
	
	/** The client handler. */
	private ClientHandlerRMI clientHandler;
	
	/** The server stub. */
	private RMIRemoteView serverStub;
	
	/**
	 * Instantiates a new client RMI.
	 */
	public ClientRMI()
	{
		/* Empty constructor */
	}
	
	@Override
	public void connectToServer()
	{
		while(this.registry ==  null || this.serverConnection == null)
		{
			stdOut.getStdOut().println("\nInsert IP address or Host Name:");
			stdOut.getStdOut().println("(default Host Name: localhost)");
			stdOut.getStdOut().print("--> ");
			this.setServerIP(this.getScanner().nextLine());
			
			stdOut.getStdOut().println("\nInsert RMI Port:");
			stdOut.getStdOut().println("(default RMI Port: 12001)");
			stdOut.getStdOut().print("--> ");
			this.setServerRMIPort(this.getScanner().nextLine());
			
			try 
			{
				registry = LocateRegistry.getRegistry(this.getServerIP(), this.getServerRMIPort());
				serverConnection = (ServerRMIRemoteView) registry.lookup(NAME);
			} 
			catch (RemoteException | NotBoundException e) 
			{
				stdOut.getStdOut().println("Connection Refused.");
				stdOut.getStdOut().println("RMIServer Offline on ip: " + this.getServerIP() + " and port: " +  this.getServerRMIPort() + ".");
				stdOut.getStdOut().println("\nPress enter to try again.");
	        	this.getScanner().nextLine();
			}
		}
		
		try 
		{
			this.startClient();
		} 
		catch (RemoteException | AlreadyBoundException e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
	}
	
	/**
	 * Start client handler for a RMI connection.
	 *
	 * @throws RemoteException
	 *             the remote exception
	 * @throws AlreadyBoundException
	 *             the already bound exception
	 */
	private void startClient() throws RemoteException, AlreadyBoundException
	{
		clientHandler = new ClientHandlerRMI(askUI());
		serverStub = serverConnection.registerClient(clientHandler);
		
		clientHandler.setStub(serverStub);
	}
}
