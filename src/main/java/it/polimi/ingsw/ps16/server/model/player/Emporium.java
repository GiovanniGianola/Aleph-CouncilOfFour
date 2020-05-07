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
package it.polimi.ingsw.ps16.server.model.player;

import java.io.Serializable;

/**
 * The Class Emporium.
 */
public class Emporium implements Serializable
{	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3086161990963454502L;
	
	/** The player ID. */
	private final int playerID;
	
	/**
	 * Instantiates a new emporium.
	 *
	 * @param playerID
	 *            the player ID
	 */
	public Emporium(int playerID)
	{
		this.playerID = playerID;
	}
	
	/**
	 * Gets the player ID.
	 *
	 * @return the player ID
	 */
	public int getPlayerID() 
	{	
		return playerID;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "" + playerID;
	}
}
