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
package it.polimi.ingsw.ps16.server.model.turn;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.concurrent.locks.LockSupport;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateTimer;
import it.polimi.ingsw.ps16.server.view.ConnectionView;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class TurnTimeOut.
 */
public class TurnTimeOut implements Runnable, Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1958398894507720425L;
	
	/** The timer. */
	private long timer;
	
	/** The timer ID. */
	private final int timerID;
	
	/** The game. */
	private final Game game;
	
	/** The standard out. */
	private static OutputStream stdOut;
	
	private final ConnectionView conn;
	
	/**
	 * Instantiates a new turn time out.
	 *
	 * @param timerID
	 *            the timer ID
	 * @param game
	 *            the game
	 * @param timer
	 *            the timer
	 */
	public TurnTimeOut(int timerID, Game game, long timer, ConnectionView conn)
	{
		stdOut = new OutputStream();
		this.timerID = timerID;
		this.game = game;
		this.timer = timer;
		this.conn = conn;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		this.game.getCurrentState().notifyObservers(new UpdateTimer(this.game, this.timer, "start"));
		
		boolean running = true;
		long now = System.currentTimeMillis();
		long now2 = System.currentTimeMillis();
		
		while( this.game.getCurrentTimeout() == timerID && running )
		{
			if(System.currentTimeMillis() - now >= this.timer)
				running = false;
			if(System.currentTimeMillis() - now2 > 1000)
			{
				stdOut.getStdOut().print("");
				now2 += 1000;
			}
		}
				
		stdOut.getStdOut().println("TimerID: " + timerID + " time: " + (System.currentTimeMillis() - now));
		
		if( this.game.getCurrentTimeout() == timerID )
		{
			try 
			{
				if(this.conn.getClientRemote()!=null)
				{
					this.conn.getClientRemote().sendMessageToClient(new InfoMessageType(" ",this.game));
				}
				game.getCurrentState().suspendPlayer();
				LockSupport.unpark(game.getCurrentState().getCurrentThread());
			} 
			catch (RemoteException e) 
			{
				this.conn.update(null, new InfoMessageType("",this.game));
			}
		}
	}
}
