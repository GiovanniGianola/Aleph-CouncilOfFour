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
package it.polimi.ingsw.ps16.server.model.deck;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.gameboard.Colour;


/**
 * The Class PoliticCard.
 */
public class PoliticCard extends Card implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6693710839079382123L;
	
	/** The color. */
	public final Colour color;
	
	/**
	 * Instantiates a new politic card.
	 *
	 * @param color
	 *            the color
	 */
	public PoliticCard(Colour color)
	{
		this.color = color;
	}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Colour getColor() 
	{
		return color;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return color.toString();
	}
}
