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
import it.polimi.ingsw.ps16.server.model.actions.bonus.ChooseABusinessPermitCardToDrawAction;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;



/**
 * The Class DrawBusinessPermitCardBonus.
 */
public class DrawBusinessPermitCardBonus extends Bonus implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7817357004929618145L;

	/**
	 * Instantiates a new draw business permit card bonus.
	 *
	 * @param quantity
	 *            the quantity
	 */
	public DrawBusinessPermitCardBonus(int quantity)
	{
		super(quantity);
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.bonus.Bonus#executeBonus(it.polimi.ingsw.ps16.server.model.Game)
	 */
	@Override
	public void executeBonus(Game game) throws SuspendPlayerException
	{
		ChooseABusinessPermitCardToDrawAction action = new ChooseABusinessPermitCardToDrawAction(game);
		
		boolean thereAreBusinessPermitCards = true;
		for( int i = 0; i < this.getQuantity() && thereAreBusinessPermitCards; i++ )
		{
			thereAreBusinessPermitCards = action.chooseABusinessPermitCardToDraw();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "DrawBusinessPermitCardBonus with value: " + this.getQuantity();
	}
}
