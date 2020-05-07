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


/**
 * The Class BonusTile.
 */
public class BonusTile implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4771231161212249461L;
	
	/** The bonus. */
	private final Bonus bonus;
	
	/**
	 * Instantiates a new bonus tile.
	 *
	 * @param bonus
	 *            the bonus
	 */
	public BonusTile(Bonus bonus)
	{
		this.bonus = bonus;
	}
	
	/**
	 * Gets the bonus.
	 *
	 * @return the bonus
	 */
	public Bonus getBonus() 
	{
		return bonus;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "BonusTile: " + bonus.toString();
	}
}
