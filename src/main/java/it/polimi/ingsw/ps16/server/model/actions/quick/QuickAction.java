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
package it.polimi.ingsw.ps16.server.model.actions.quick;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.Action;

/**
 * The Class QuickAction is the superclass of each quick actions<br>
 * needed for strategy pattern.
 */
public abstract class QuickAction extends Action
{
	
	/**
	 * Instantiates a new quick action.
	 *
	 * @param game
	 *            the game
	 */
	public QuickAction(Game game) 
	{
		super(game);
	}
}
