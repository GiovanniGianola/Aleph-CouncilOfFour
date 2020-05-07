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
package it.polimi.ingsw.ps16.server.model.actions;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;

/**
 * The Class Action is the superclass of each type of actions:<br><br>
 * - Main Actions<br>
 * - Quick Actions<br>
 * - GameLogic Actions<br>
 * - BonusAction.
 */
public abstract class Action
{
	
	/** The Constant INVALID_INPUT. */
	private static final String INVALID_INPUT = "Invalid Input.";
	
	/** The game. */
	private final Game game;	
	

	/**
	 * Instantiates a new action.
	 *
	 * @param game
	 *            the game
	 */
	public Action(Game game)
	{
		this.game = game;
	}
	
	/**
	 * Gets the current game.
	 *
	 * @return the game
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * Gets the invalid input string to show to the player.
	 *
	 * @return the invalid input
	 */
	public String getInvalidInput()
	{
		return Action.INVALID_INPUT;
	}
	
	/**
	 * Check player to suspend if the turn timeout run out.
	 *
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public void checkPlayerToSuspend() throws SuspendPlayerException
	{
		if( this.game.getCurrentState() != null && this.game.getCurrentState().isPlayerToSuspend() )
		{
			throw new SuspendPlayerException(null);
		}
	}

	
	/**
	 * Execute methods is an abstract method that every action must implement<br>
	 * that provide the real execution of an action chosen by the player.
	 *
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public abstract void execute() throws SuspendPlayerException;
}
