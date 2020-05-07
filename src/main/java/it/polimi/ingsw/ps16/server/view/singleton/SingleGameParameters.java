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
package it.polimi.ingsw.ps16.server.view.singleton;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps16.server.model.player.Player;
import it.polimi.ingsw.ps16.server.view.ConnectionView;

/**
 * The Class SingleGameParameters contains all the parameters<br>
 * of a single game:<br><br>
 * 	- The number of map<br>
 * 	- The name of the King<br>
 * 	- The list of players<br>
 * 	- The list of connections to the players 
 */
public class SingleGameParameters 
{
	
	/** The map number. */
	private String mapNumber;
	
	/** The king name. */
	private String kingName;
	
	/** The players. */
	private List<Player> players;
	
	/** The connections. */
	private List<ConnectionView> connections;
	
	
	
	/**
	 * Instantiates a new single game parameters.
	 */
	public SingleGameParameters()
	{
		this.mapNumber = new String();
		this.kingName = new String();
		this.players = new ArrayList<>();
		this.connections = new ArrayList<>();
	}
	
	
		
	/**
	 * Gets the map number.
	 *
	 * @return the map number
	 */
	public String getMapNumber() 
	{
		return mapNumber;
	}
	
	/**
	 * Sets the map number.
	 *
	 * @param mapNumber
	 *            the new map number
	 */
	public void setMapNumber(String mapNumber) 
	{
		this.mapNumber = mapNumber;
	}
	
	/**
	 * Gets the king name.
	 *
	 * @return the king name
	 */
	public String getKingName() 
	{
		return kingName;
	}
	
	/**
	 * Sets the king name.
	 *
	 * @param kingName
	 *            the new king name
	 */
	public void setKingName(String kingName) 
	{
		this.kingName = kingName;
	}
	
	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<Player> getPlayers() 
	{
		return players;
	}
	
	/**
	 * Sets the players.
	 *
	 * @param players
	 *            the new players
	 */
	public void setPlayers(List<Player> players) 
	{
		this.players = players;
	}
	
	/**
	 * Gets the connections.
	 *
	 * @return the connections
	 */
	public List<ConnectionView> getConnections() 
	{
		return connections;
	}
	
	/**
	 * Sets the connections.
	 *
	 * @param connections
	 *            the new connections
	 */
	public void setConnections(List<ConnectionView> connections) 
	{
		this.connections = connections;
	}
}
