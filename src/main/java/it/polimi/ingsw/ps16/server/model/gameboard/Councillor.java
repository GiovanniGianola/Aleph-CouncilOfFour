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
package it.polimi.ingsw.ps16.server.model.gameboard;

import java.io.Serializable;

/**
 * The Class Councillor provides the object councillor.
 */
public class Councillor implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3775811060379264730L;
	
	/** The color. */
	private final Colour color;
	
	/**
	 * Instantiates a new councillor.
	 *
	 * @param color
	 *            the color
	 */
	public Councillor(Colour color) 
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
