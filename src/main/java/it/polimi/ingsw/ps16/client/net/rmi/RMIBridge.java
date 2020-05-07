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

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.client.net.HandlerView;

/**
 * The Class RMIBridge works as a broker between the ClientHandlerRMI and the client view.<br>
 * contribute the communications within server and client in both directions
 */
public class RMIBridge extends HandlerView
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( RMIBridge.class.getName() );
	
	/** The handler object. */
	private Object handlerObject;
	
	/** The client handler. */
	private ClientHandlerRMI clientHandler;
	
	/**
	 * Instantiates a new RMIBridge.
	 *
	 * @param client
	 *            the client handler
	 */
	public RMIBridge(ClientHandlerRMI client) 
	{
		this.clientHandler = client;
	}
	
	@Override
	public void initializeInterface(String ui) 
	{
		this.startUserInterface(ui, this);
	}
	
	/**
	 * Update RMI bridge is method used by ClientHandlerRMi to send<br>
	 * messages to the client.
	 *
	 * @param arg
	 *            the message to send
	 */
	public void updateRMIBrige(Object arg)
	{
		this.handlerObject = arg;

		this.setChanged();
		this.notifyObservers(this.handlerObject);
	}

	/**
	 * Update is method used by the client view to send<br>
	 * answer messages to the ClientHandlerRMi.
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		try 
		{
			this.clientHandler.updateHandlerRMI(arg);
		} 
		catch (RemoteException e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
	}

	@Override
	public void run() {
		/* Empty method */
	}
}
