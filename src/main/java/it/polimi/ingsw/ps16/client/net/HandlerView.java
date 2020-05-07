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
package it.polimi.ingsw.ps16.client.net;

import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import it.polimi.ingsw.ps16.client.userinterface.cli.GameConsole;
import it.polimi.ingsw.ps16.client.userinterface.gui.GameWindow;

/**
 * The Class HandlerView is used to differentiate clients handler (RMI-SOCKET).<br>
 * it used as abstraction for strategy pattern<br>
 * it provides useful methods for the communications interface-client-server
 */
public abstract class HandlerView extends Observable implements Observer, Runnable
{
	
	/** The is finish game. */
	private boolean isFinishGame;	
	
	/** The current handler view. */
	private HandlerView currentHandlerView;

	/**
	 * Instantiates a new handler view.
	 * Se the game finish boolean variable false
	 */
	public HandlerView() 
	{
		this.isFinishGame = false;
	}
	
	/**
	 * Start user interface chosen by the player.<br>
	 * the interface are lunched in a separated thread<br>
	 *
	 * @param ui
	 *            the user interfaces chosen
	 * @param handlerView
	 *            the current handler view chosen
	 */
	protected void startUserInterface(String ui, HandlerView handlerView)
	{
		this.currentHandlerView = handlerView;
		
		if("1".equals(ui))
		{
			SwingUtilities.invokeLater(()-> new GameConsole(currentHandlerView));
		}
		else if("2".equals(ui))
		{
			SwingUtilities.invokeLater(()-> new GameWindow(currentHandlerView));
		}
		this.timer(3);
	}
	
	/**
	 * Sets the finish game.
	 *
	 * @param isFinishGame
	 *            true if the game is done
	 */
	public void setFinishGame(boolean isFinishGame) 
	{
		this.isFinishGame = isFinishGame;
	}
	
	/**
	 * Checks if the game is finished.
	 *
	 * @return true, if the game is finished
	 */
	public boolean isFinishGame() 
	{
		return isFinishGame;
	}
	
	/**
	 * Timer.
	 *
	 * @param secTimer
	 *            the time in seconds
	 */
	private void timer(long secTimer)
	{
		boolean running =  true;
		long now = System.currentTimeMillis();
		long countDown = secTimer/1000;
		
		while(running)
		{
			if(System.currentTimeMillis() - now > 1000)
			{
				now += 1000;
				countDown -= 1;
				if(countDown <= 0)
					running = false;
			}
		}
	}
	
	/**
	 * Initialize interface.
	 *
	 * @param ui
	 *            the ux
	 */
	public abstract void initializeInterface(String io);
}
