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
package it.polimi.ingsw.ps16.server.view.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.client.net.rmi.ClientViewRemote;

/**
 * The Class ServerRMIView is responsible for the management of the RMI connections.
 */
public class ServerRMIView implements ServerRMIRemoteView
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( ServerRMIView.class.getName() );
	
	/** The client. */
	private RMIView client;
	
	/** The n game. */
	private int nGame;
	
	/**
	 * Instantiates a new server RMI view.
	 */
	public ServerRMIView() 
	{
		super();
		this.nGame = 1;
	}

	/**
	 * This method manage the single client connection and create the manager for communication<br>
	 * with him in the game.
	 */
	@Override
	public RMIRemoteView registerClient(ClientViewRemote clientStub) 
	{
		this.client = new RMIView(clientStub, this.nGame);		
		(new Thread(this.client)).start();		
		try 
		{
			return (RMIRemoteView) UnicastRemoteObject.exportObject(this.client, 12001);
		} 
		catch (RemoteException e) 
		{
			log.log( Level.SEVERE, e.toString(), e );
			return null;
		}
	}
	
	/**
	 * Increase the number of game that this object refers to.
	 */
	public void increaseNGame()
	{
		nGame++;
	}
}
