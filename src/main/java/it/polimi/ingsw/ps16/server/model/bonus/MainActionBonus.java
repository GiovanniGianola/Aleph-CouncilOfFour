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
package it.polimi.ingsw.ps16.server.model.bonus;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.actions.bonus.ChooseAMainActionToDoAction;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;


/**
 * The Class MainActionBonus.
 */
public class MainActionBonus extends Bonus implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7671884060823867149L;

	/**
	 * Instantiates a new main action bonus.
	 *
	 * @param quantity
	 *            the quantity
	 */
	public MainActionBonus(int quantity)
	{
		super(quantity);
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.bonus.Bonus#executeBonus(it.polimi.ingsw.ps16.server.model.Game)
	 */
	@Override
	public void executeBonus(Game game) throws SuspendPlayerException
	{
		ChooseAMainActionToDoAction action = new ChooseAMainActionToDoAction(game);
		for( int i = 0; i < this.getQuantity(); i++ )
		{
			action.chooseAMainActionToDo();
		}	
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "MainActionBonus with value: " + this.getQuantity();
	}
}
