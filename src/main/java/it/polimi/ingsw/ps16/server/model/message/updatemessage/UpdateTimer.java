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
package it.polimi.ingsw.ps16.server.model.message.updatemessage;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.Game;

/**
 * The Class UpdateTimer.
 */
public class UpdateTimer implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4691636881229280168L;
	
	/** The timer. */
	private long timer;
	
	/** The action. */
	private String action;
	
	/**
	 * Instantiates a new update timer.
	 *
	 * @param game
	 *            the game
	 * @param timer
	 *            the timer
	 * @param action
	 *            the action
	 */
	public UpdateTimer(Game game, long timer, String action) 
	{
		game.setChanges();
		
		if(game.getCurrentState() != null)
			game.getCurrentState().setChanges();
		
		this.timer = timer;
		
		this.action = action;
	}
	
	/**
	 * Gets the timer.
	 *
	 * @return the timer
	 */
	public Integer getTimer()
	{
		return (int)(this.timer/1000);
	}
	
	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public String getAction()
	{
		return this.action;
	}

}
