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
 * The Enum Colour class provides different color for politic cards.
 */
public enum Colour implements Serializable
{	
	
	/** The black. */
	BLACK{},
	
	/** The blue. */
	BLUE{},
	
	/** The purple. */
	PURPLE{},
	
	/** The orange. */
	ORANGE{},
	
	/** The white. */
	WHITE{},
	
	/** The pink. */
	PINK{},
	
	/** The jolly. */
	JOLLY{};
	
	/**
	 * Search color by name.
	 *
	 * @param color
	 *            the color
	 * @return the colour
	 */
	public static Colour searchColorByName(String color)
	{
		for( Colour c : Colour.values() ) 
		{
			if(c.name().equalsIgnoreCase(color))
				return c;
		}
		return null;
	}
}