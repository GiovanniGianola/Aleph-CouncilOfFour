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
package it.polimi.ingsw.ps16.server.model.message.initializeplayermessage;

import java.awt.Color;
import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class InitializePlayerMessage.
 */
public class InitializePlayerMessage implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3401110807558072995L;
	
	/** The player. */
	private Player player;

	/**
	 * Instantiates a new initialize player message.
	 *
	 * @param player
	 *            the player
	 */
	public InitializePlayerMessage(Player player)
	{
		this.player = player;
	}
	
	/**
	 * Gets the player ID.
	 *
	 * @return the player ID
	 */
	public int getPlayerID()
	{
		return this.player.getPlayerID();
	}
	
	/**
	 * Gets the player name.
	 *
	 * @return the player name
	 */
	public String getPlayerName()
	{
		return this.player.getName();
	}
	
	/**
	 * Gets the player color.
	 *
	 * @return the player color
	 */
	public Color getPlayerColor()
	{
		return this.player.getColor();
	}

}
