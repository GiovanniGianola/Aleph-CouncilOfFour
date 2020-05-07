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
package it.polimi.ingsw.ps16.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import it.polimi.ingsw.ps16.server.model.gameboard.GameBoard;
import it.polimi.ingsw.ps16.server.model.market.MarketObject;
import it.polimi.ingsw.ps16.server.model.player.Player;
import it.polimi.ingsw.ps16.server.model.turn.TurnState;
import it.polimi.ingsw.ps16.server.model.turn.TurnTimeOut;
import it.polimi.ingsw.ps16.server.view.ConnectionView;

/**
 * The Class Game is the model class of the current game,<br>
 *  it contains the refers of every single elements<br>
 *   of the current game board, player and state.
 */
public class Game extends Observable implements Serializable
{	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8153121855572408639L;
	
	/** The Constant TIMER. */
	private static final long TIMER = (long)6 * 60 * 1000;
		
	/** The game board. */
	private GameBoard gameBoard;
	
	/** The players. */
	private final List<Player> players;
	
	/** The current state. */
	private TurnState currentState;
	
	/** The current player. */
	private Player currentPlayer;
	
	/** The market objects. */
	private List<MarketObject> marketObjects;
	
	/** The is finish game. */
	private boolean isFinishGame; 
	
	/** The rank. */
	private transient List<List<List<List<Player>>>> rank;
	
	/** The map number. */
	private String mapNumber;
	
	/** The current timeout. */
	private int currentTimeout = 0;
	
	/** The timer TH. */
	private transient Thread timerTH;
	
	
	/**
	 * this methods is called by game controller and initialize a new game board
	 *
	 * @param players
	 *            the list of players
	 * @param mapNumber
	 *            the map number
	 * @param kingName
	 *            the king name
	 */
	public Game(List<Player> players, String mapNumber, String kingName) 
	{
		super();
		
		this.players = players;
		this.currentState = null;
		this.currentPlayer = null;
		this.mapNumber = mapNumber;
		this.marketObjects = new ArrayList<>();
		
		this.isFinishGame = false;
		
		//Inizializzazione GameBoard
		final ParseStreamXML psXML;
		
		psXML = new ParseStreamXML(new GameBoard());
		this.gameBoard = psXML.initGameBoard(mapNumber, players.size());
		this.gameBoard.setKing(kingName);
		this.gameBoard.setPoliticCardsDeck(players.size());
		this.gameBoard.setDiscardedPoliticCardsDeck();
	}
	
	/**
	 * Sets the changes.
	 */
	public void setChanges()
	{
		this.setChanged();
	}
	
	/**
	 * Gets the map number.
	 *
	 * @return the map number
	 */
	public String getMapNumber()
	{
		return this.mapNumber;
	}
	
	/**
	 * Gets the game board.
	 *
	 * @return the game board
	 */
	public GameBoard getGameBoard() 
	{
		return gameBoard;
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
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public TurnState getCurrentState() 
	{
		return currentState;
	}

	/**
	 * Sets the current state.
	 *
	 * @param currentState
	 *            the new current state
	 */
	public void setCurrentState(TurnState currentState) 
	{
		this.currentState = currentState;
	}
	
	/**
	 * Gets the current player.
	 *
	 * @return the current player
	 */
	public Player getCurrentPlayer() 
	{
		return currentPlayer;
	}

	/**
	 * Sets the current player.
	 *
	 * @param currentPlayer
	 *            the new current player
	 */
	public void setCurrentPlayer(Player currentPlayer) 
	{
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * Sets the market objects.
	 *
	 * @param marketObjects
	 *            the new market objects
	 */
	public void setMarketObjects(List<MarketObject> marketObjects)
	{
		this.marketObjects = marketObjects;
	}
	
	/**
	 * Gets the market objects.
	 *
	 * @return the market objects
	 */
	public List<MarketObject> getMarketObjects()
	{
		return marketObjects;
	}

	/**
	 * Checks if is finish game.
	 *
	 * @return true, if is finish game
	 */
	public boolean isFinishGame() 
	{
		return isFinishGame;
	}

	/**
	 * Sets the finish game.
	 *
	 * @param isFinishState
	 *            the new finish game
	 */
	public void setFinishGame(boolean isFinishState) 
	{
		this.isFinishGame = isFinishState;
	}
	
	/**
	 * Sets the rank.
	 *
	 * @param rank
	 *            the new rank
	 */
	public void setRank(List<List<List<List<Player>>>> rank)
	{
		this.rank = rank;
	}
	
	/**
	 * Gets the rank.
	 *
	 * @return the rank
	 */
	public List<List<List<List<Player>>>> getRank()
	{
		return rank;
	}
	
	public void launchTimer(ConnectionView conn) 
	{
		this.setCurrentTimeout();
		timerTH = new Thread(new TurnTimeOut(getCurrentTimeout(), this, TIMER, conn));
		timerTH.start();
	}
	
	/**
	 * Sets the current timeout.
	 */
	public void setCurrentTimeout()
	{
		this.currentTimeout++;
	}
	
	/**
	 * Gets the current timeout.
	 *
	 * @return the current timeout
	 */
	public int getCurrentTimeout()
	{
		return currentTimeout;
	}
	
	/**
	 * Gets the timer.
	 *
	 * @return the timer
	 */
	public static long getTimer()
	{
		return TIMER;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder("Game Board:\n");
		
		str.append("\t" + this.gameBoard.toString());
		str.append("Players:\n" );
		for(int i = 0; i < players.size(); i++)
			str.append(this.players.get(i).toStringWithoutPoliticCards());
		
		return str.toString();
	}
}
