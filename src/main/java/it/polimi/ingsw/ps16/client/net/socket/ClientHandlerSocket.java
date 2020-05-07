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
package it.polimi.ingsw.ps16.client.net.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.client.net.HandlerView;
import it.polimi.ingsw.ps16.server.model.message.FinishMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.finishmessage.FinishMessage;

/**
 * The Class ClientHandlerSocket provide the communication between client and server.<br>
 * Route the messages from the server to the final player view<br>
 * and the answer of the player to the server
 */
public class ClientHandlerSocket extends HandlerView implements Runnable
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( ClientHandlerSocket.class.getName() );
	
	/** The socket out. */
	private ObjectOutputStream socketOut;
	
	/** The socket in. */
	private ObjectInputStream socketIn;
	
	/** The received object. */
	private Object receivedObject;
	
	/**
	 * Instantiates a new client handler socket.
	 *
	 * @param socketOut
	 *            the socket output
	 * @param socketIn
	 *            the socket input
	 */
	public ClientHandlerSocket( ObjectOutputStream socketOut, ObjectInputStream socketIn) 
	{
		this.socketOut = socketOut;
		this.socketIn = socketIn;
		
		this.receivedObject = null;
	}
	
	@Override
	public void initializeInterface(String ui)
	{		
		this.startUserInterface(ui, this);
	}

	@Override
	public void run() 
	{		
		while(!this.isFinishGame())
		{
			runClient();
		}
	}
	
	/**
	 * Run client manage the communication from the server to the player view.
	 */
	private void runClient()
	{
		try 
		{	
			this.receivedObject = this.socketIn.readObject();
			
			this.setChanged();
			this.notifyObservers(this.receivedObject);
		} 
		catch (ClassNotFoundException | IOException e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
			this.setChanged();
			this.notifyObservers(new FinishMessage(FinishMessageTypeEnum.SERVER_DISCONNETTED));
			this.setFinishGame(true);
		}
	}

	/**
	 * Update is method that manage the communication from the player view to the server.
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		try 
		{
			this.socketOut.writeObject(arg);
			this.socketOut.flush();
		} 
		catch (IOException e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
	}
}
