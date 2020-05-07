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
package it.polimi.ingsw.ps16.server.model.actions.bonus;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.Action;


/**
 * The Class BonusAction the superclass of each bonus action<br>
 * needed for strategy pattern.
 */
public abstract class BonusAction extends Action
{
	
	/**
	 * Instantiates a new bonus action.
	 *
	 * @param game
	 *            the game
	 */
	public BonusAction(Game game) 
	{
		super(game);
	}
}
