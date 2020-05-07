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


/**
 * The Class VictoryPointBonus.
 */
public class VictoryPointBonus extends Bonus implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 209315273695187435L;

	/**
	 * Instantiates a new victory point bonus.
	 *
	 * @param quantity
	 *            the quantity
	 */
	public VictoryPointBonus(int quantity)
	{
		super(quantity);
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.bonus.Bonus#executeBonus(it.polimi.ingsw.ps16.server.model.Game)
	 */
	@Override
	public void executeBonus(Game game)
	{
		try
		{
			game.getCurrentPlayer().getVictoryMarkerDisc().moveForward(this.getQuantity());
		}
		catch(Exception exceptionOfReachLimit)
		{
			throw exceptionOfReachLimit;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "VictoryPointBonus with value: " + this.getQuantity();
	}
}
