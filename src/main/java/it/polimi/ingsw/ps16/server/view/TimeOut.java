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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class TimeOut is used by the server to stop <br>
 * the connections for a single game.<br>
 * After the timer expired, if the players are enough, the game begins.
 */
public class TimeOut extends Thread
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( TimeOut.class.getName() );
	
	/** The timer. */
	private final long timer;
	
	/** The number of game. */
	private final int numberOfGame;
	
	/** The server socket port. */
	private final int serverSocketPort;
	
	/** The standard out. */
	private static OutputStream stdOut;

	
	/**
	 * Instantiates a new time out.
	 *
	 * @param timeOut
	 *            the time out in milliseconds
	 * @param numberOfGame
	 *            the number of the game that this timer refers to
	 * @param serverSocketPort
	 *            the server socket port of the server
	 */
	public TimeOut(long timeOut, int numberOfGame, int serverSocketPort)
	{
		stdOut = new OutputStream();
		this.timer = timeOut;
		this.numberOfGame = numberOfGame;
		this.serverSocketPort = serverSocketPort;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{		
		stdOut.getStdOut().println("***TIMER STARTED: " + (this.timer / 1000) + " SECONDS.***\n");
		
		boolean running =  true;
		long now = System.currentTimeMillis();
		long countDown = this.timer/1000;
		
		while(running)
		{
			if(System.currentTimeMillis() - now > 5000)
			{
				now += 5000;
				countDown -= 5;
				stdOut.getStdOut().print("[" + countDown + "] seconds left\n");
				if(countDown <= 0)
					running = false;
			}
		}
		
		try 
		{
			Server.setTimeOutExpired(true);
			stdOut.getStdOut().println("\n*** TIMER EXPIRED, NO MORE CONNECTION IN THE " + this.numberOfGame + "° GAME ***\n");
			(new Socket("localhost", serverSocketPort)).close();
		}
		catch (IOException e) 
		{
			log.log( Level.SEVERE, e.toString(), e );
		}
	}
}
