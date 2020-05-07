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
package it.polimi.ingsw.ps16.server.view;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps16.client.net.rmi.ClientViewRemote;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class ConnectionView is the superclass of the different type<br>
 * of client handler in server side: RMIView and SocketView.
 */
public abstract class ConnectionView extends Observable implements Observer
{
	
	/** The client. */
	protected transient ClientViewRemote client;
	
	/** The Constant TIME_OUT. */
	private static final long TIME_OUT = (long) 30 * 1000;
	
	/** The Constant SOCKET_PORT. */
	private static final int SOCKET_PORT = 12000;
	
	/** The n game. */
	private int nGame;
	
	/** The standard out. */
	protected static OutputStream stdOut;

	/**
	 * Instantiates a new connection view.
	 */
	public ConnectionView()
	{
		/* Empty Constructor */
	}
	
	/**
	 * Instantiates a new connection view.
	 *
	 * @param nGame
	 *            the n game
	 */
	public ConnectionView(int nGame)
	{
		stdOut = new OutputStream();
		this.nGame = nGame;
	}
	
	/**
	 * Initialize game timer.
	 */
	protected void initializeGameTimer()
	{
		(new TimeOut(TIME_OUT, this.nGame, SOCKET_PORT)).start();
	}
	
	/**
	 * Initialize.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	protected abstract void initialize() throws IOException, ClassNotFoundException;
	
	/**
	 * Sets the map king.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	protected abstract void setMapKing() throws IOException, ClassNotFoundException;
	
	/**
	 * Sets the color.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	protected abstract void setColor() throws IOException, ClassNotFoundException;

	/**
	 * Gets the n game.
	 *
	 * @return the n game
	 */
	protected int getNGame()
	{
		return nGame;
	}
	
	public ClientViewRemote getClientRemote()
	{
		return client;
	}
	
	/**
	 * Notify disconnections.
	 *
	 * @param arg
	 *            the arg
	 */
	protected abstract void notifyDisconnections( Object arg );
}
