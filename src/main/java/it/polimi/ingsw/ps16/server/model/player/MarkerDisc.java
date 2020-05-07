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
 * The Class MarkerDisc.
 */
public class MarkerDisc implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3008357599347656081L;
	
	/** The player ID. */
	private final int playerID;
	
	/** The limit position. */
	private final int limitPosition; //posizione massima del percorso in cui si trova
	
	/** The position. */
	private int position;	
	

	/**
	 * Instantiates a new marker disc.
	 *
	 * @param playerID
	 *            the player ID
	 * @param position
	 *            the position
	 * @param limitPosition
	 *            the limit position
	 */
	public MarkerDisc(int playerID, int position,int limitPosition)
	{ 
		this.playerID = playerID;
		this.limitPosition = limitPosition;
		this.position = position;
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
	
	/**
	 * Gets the limit position.
	 *
	 * @return the limit position
	 */
	public int getLimitPosition() 
	{
		return limitPosition;
	}
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public int getPosition() 
	{
		return position;
	}
	
    /**
	 * Move forward.
	 *
	 * @param increase
	 *            the increase
	 */
    public void moveForward(int increase)
    { 
			this.position = this.position + increase; 
	}
	
	/**
	 * Move back.
	 *
	 * @param reduction
	 *            the reduction
	 */
	public void moveBack(int reduction)
	{
			this.position = this.position - reduction; 
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "" + this.getPosition();
	}
}
