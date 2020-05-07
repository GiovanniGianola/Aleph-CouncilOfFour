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
package it.polimi.ingsw.ps16.server.model.actions.gamelogic;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.Action;

/**
 * The Class GameLogicActionis the superclass of each game logic action<br>
 * needed for strategy pattern.
 */
public abstract class GameLogicAction extends Action 
{
	
	/**
	 * Instantiates a new game logic action.
	 *
	 * @param game
	 *            the game
	 */
	public GameLogicAction(Game game) 
	{
		super(game);
	}
}
