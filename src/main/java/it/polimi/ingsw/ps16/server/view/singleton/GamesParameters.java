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

import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.ps16.server.model.player.Player;
import it.polimi.ingsw.ps16.server.view.ConnectionView;

/**
 * Is a singleton class that contains an HashMap of all parameters of<br>
 * starting games.
 */
public class GamesParameters 
{
	
	/** The game parameters instance. */
	private static GamesParameters gameParametersInstance;
	
	/** The games param. */
	private HashMap<Integer,SingleGameParameters> gamesParam;

	
	
	/**
	 * Instantiates a new games parameters.
	 */
	public GamesParameters()
	{
		gamesParam = new HashMap<>();
	}
	
	
	
	/**
	 * Gets the game parameters instance.
	 *
	 * @return the game parameters instance
	 */
	public static GamesParameters getGameParametersInstance()
	{
		if(gameParametersInstance == null)
			gameParametersInstance = new GamesParameters();
		return gameParametersInstance;
	}
	
	
	
	/**
	 * New game parameters.
	 *
	 * @param nGame
	 *            the n game
	 */
	public void newGameParameters(int nGame)
	{
		gameParametersInstance.gamesParam.put(nGame, new SingleGameParameters());
	}
	
	/**
	 * Reset.
	 *
	 * @param nGame
	 *            the n game
	 */
	public static void reset(int nGame)
	{
		gameParametersInstance.gamesParam.remove(nGame);
	}
	
	/**
	 * Gets the map number.
	 *
	 * @param nGame
	 *            the n game
	 * @return the map number
	 */
	public String getMapNumber(int nGame) 
	{
		return gameParametersInstance.gamesParam.get(nGame).getMapNumber();
	}

	/**
	 * Sets the map number.
	 *
	 * @param nGame
	 *            the n game
	 * @param mapNumber
	 *            the map number
	 */
	public void setMapNumber(int nGame, String mapNumber) 
	{
		gameParametersInstance.gamesParam.get(nGame).setMapNumber(mapNumber);
	}

	/**
	 * Gets the king name.
	 *
	 * @param nGame
	 *            the n game
	 * @return the king name
	 */
	public String getKingName(int nGame) 
	{
		return gameParametersInstance.gamesParam.get(nGame).getKingName();
	}

	/**
	 * Sets the king name.
	 *
	 * @param nGame
	 *            the n game
	 * @param kingName
	 *            the king name
	 */
	public void setKingName(int nGame, String kingName) 
	{
		gameParametersInstance.gamesParam.get(nGame).setKingName(kingName);
	}

	/**
	 * Gets the players.
	 *
	 * @param nGame
	 *            the n game
	 * @return the players
	 */
	public List<Player> getPlayers(int nGame) 
	{
		return gameParametersInstance.gamesParam.get(nGame).getPlayers();
	}

	/**
	 * Sets the players.
	 *
	 * @param nGame
	 *            the n game
	 * @param players
	 *            the players
	 */
	public void setPlayers(int nGame, List<Player> players) 
	{
		gameParametersInstance.gamesParam.get(nGame).setPlayers(players);
	}

	/**
	 * Gets the connections.
	 *
	 * @param nGame
	 *            the n game
	 * @return the connections
	 */
	public List<ConnectionView> getConnections(int nGame) 
	{
		return gameParametersInstance.gamesParam.get(nGame).getConnections();
	}

	/**
	 * Sets the connections.
	 *
	 * @param nGame
	 *            the n game
	 * @param connections
	 *            the connections
	 */
	public void setConnections(int nGame, List<ConnectionView> connections) 
	{
		gameParametersInstance.gamesParam.get(nGame).setConnections(connections);
	}
}
