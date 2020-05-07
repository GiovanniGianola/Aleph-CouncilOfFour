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
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;

/**
 * The Class Bonus.
 */
public abstract class Bonus implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2395121478686819106L;
	
	/** The quantity. */
	private final int quantity;
	
	/**
	 * Instantiates a new bonus.
	 *
	 * @param quantity
	 *            the quantity
	 */
	public Bonus(int quantity)
	{
		this.quantity = quantity;
	}
	
	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() 
	{
		return quantity;
	}
	
	/**
	 * Execute bonus.
	 *
	 * @param game
	 *            the game
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public abstract void executeBonus(Game game) throws SuspendPlayerException;
}
