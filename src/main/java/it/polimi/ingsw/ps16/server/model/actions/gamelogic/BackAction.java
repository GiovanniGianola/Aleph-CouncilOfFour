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
 * The Class BackAction allow server to catch if the player want to go back in action choice
 */
public class BackAction extends Action 
{
	
	/**
	 * Instantiates a new back action.
	 *
	 * @param game
	 *            the game
	 */
	public BackAction(Game game) 
	{
		super(game);
	}


	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.actions.Action#execute()
	 */
	@Override
	public void execute() 
	{
		/* empty method */
	}

}
