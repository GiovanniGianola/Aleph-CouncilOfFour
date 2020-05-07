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
package it.polimi.ingsw.ps16.server.model.message.updatemessage;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.Game;

/**
 * The Class UpdateMap.
 */
public class UpdateMap implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7798630315023873872L;
	
	/** The map number. */
	private String mapNumber;

	/**
	 * Instantiates a new update map.
	 *
	 * @param game
	 *            the game
	 */
	public UpdateMap(Game game) 
	{
		this.mapNumber = game.getMapNumber();
		
		game.setChanges();	
	}
	
	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public String getMap()
	{
		return this.mapNumber;
	}

}
