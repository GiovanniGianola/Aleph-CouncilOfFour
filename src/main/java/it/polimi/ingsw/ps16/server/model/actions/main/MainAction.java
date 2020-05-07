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
package it.polimi.ingsw.ps16.server.model.actions.main;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.Action;

/**
 * The Class MainAction is the superclass of each main actions<br>
 * needed for strategy pattern.
 */
public abstract class MainAction extends Action
{
	
	/**
	 * Instantiates a new main action.
	 *
	 * @param game
	 *            the current game
	 */
	public MainAction(Game game) 
	{
		super(game);
	}
}
