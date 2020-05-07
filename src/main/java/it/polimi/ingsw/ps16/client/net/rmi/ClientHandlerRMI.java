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

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.server.model.message.FinishMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.finishmessage.FinishMessage;
import it.polimi.ingsw.ps16.server.view.rmi.RMIRemoteView;

/**
 * The Class ClientHandlerRMI.
 */
public class ClientHandlerRMI extends UnicastRemoteObject implements Serializable, ClientViewRemote
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( ClientHandlerRMI.class.getName() );
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1090658785756186593L;
	
	/** The server stub. */
	private transient RMIRemoteView serverStub;
	
	/** The initialize. */
	private boolean init;
	
	/** The received object. */
	private transient  Object receivedObject;
	
	/** The bridge. */
	private transient RMIBridge bridge;

	/**
	 * Instantiates a new client handler RMI.
	 *
	 * @param ui
	 *            the user interface chosen
	 * @throws RemoteException
	 *             the remote exception
	 */
	public ClientHandlerRMI(String ui) throws RemoteException 
	{
		this.serverStub = null;
		this.receivedObject = null;
		
		this.init = true;
		
		this.bridge = new RMIBridge(this);
		this.bridge.initializeInterface(ui);
	}


	@Override
	public void sendMessageToClient(Object object) throws RemoteException 
	{
		if( object != null )
		{
			this.receivedObject = object;
			this.bridge.updateRMIBrige(this.receivedObject);
		}
		else
			init = false;			
	}
	
	/**
	 * this method is used from RMIBridge to communicate with<br>
	 * the handler RMI an then to the server
	 *
	 * @param arg
	 *            the message to send to the server
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void updateHandlerRMI(Object arg) throws RemoteException
	{
		if(!init)
		{
			try
			{
				this.serverStub.sendMessage(arg);
			}
			catch(RemoteException e)
			{
				log.log( Level.SEVERE, e.toString(), e);
				this.bridge.updateRMIBrige(new FinishMessage(FinishMessageTypeEnum.SERVER_DISCONNETTED));
				this.bridge.setFinishGame(true);
				System.exit(0);
			}
		}
		else
		{
			try
			{
				this.serverStub.sendInitialMessage(arg);
			}
			catch(RemoteException e)
			{
				log.log( Level.SEVERE, e.toString(), e);
				this.bridge.updateRMIBrige(new FinishMessage(FinishMessageTypeEnum.SERVER_DISCONNETTED));
				this.bridge.setFinishGame(true);
				System.exit(0);
			}
		}
	}
	
	/**
	 * Sets the stub.
	 *
	 * @param serverStub
	 *           server stub
	 */
	public void setStub(RMIRemoteView serverStub)
	{
		this.serverStub = serverStub;
	}
}
