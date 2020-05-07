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
import it.polimi.ingsw.ps16.server.model.actions.bonus.ChooseARewardInAllBusinessPermitCardAction;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;

/**
 * The Class BusinessPermitCardRewardBonus.
 */
public class BusinessPermitCardRewardBonus  extends Bonus implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8926283102502101882L;

	/**
	 * Instantiates a new business permit card reward bonus.
	 *
	 * @param quantity
	 *            the quantity
	 */
	public BusinessPermitCardRewardBonus(int quantity)
	{
		super(quantity);
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.bonus.Bonus#executeBonus(it.polimi.ingsw.ps16.server.model.Game)
	 */
	@Override
	public void executeBonus(Game game) throws SuspendPlayerException
	{
		ChooseARewardInAllBusinessPermitCardAction action = new ChooseARewardInAllBusinessPermitCardAction(game);
		
		boolean thereAreBusinessPermitCards = true;
		for( int i = 0; i < this.getQuantity() && thereAreBusinessPermitCards; i++ )
		{
			thereAreBusinessPermitCards = action.chooseARewardInAllBusinessPermitCardAction();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "BusinessPermitCardRewardBonus with value: " + this.getQuantity();
	}
}
