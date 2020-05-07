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
package it.polimi.ingsw.ps16.server.controller.gamemachines;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.player.Player;
import it.polimi.ingsw.ps16.server.model.turn.TurnState;
import it.polimi.ingsw.ps16.server.model.turn.gameturn.DrawPoliticCardState;
import it.polimi.ingsw.ps16.server.model.turn.gameturn.FinishState;
import it.polimi.ingsw.ps16.server.view.ConnectionView;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class TurnMachine is responsible for the rotation of the states of the individual player,<br>
 * this rotation happen by a state machine.<br>
 * It is used for StatePattern
 */
public class TurnMachine 
{
	
	/** The view. */
	private final ConnectionView view;
	
	/** The game. */
	private final Game game;
	
	/** The player. */
	private final Player player;
	
	/** The standard out. */
	private static OutputStream stdOut;
	
	/**
	 * Instantiates a new turn machine.
	 *
	 * @param view
	 *            the connection to the single player
	 * @param game
	 *            the game
	 * @param player
	 *            the player
	 */
	public TurnMachine(ConnectionView view, Game game, Player player)
	{
		this.view = view;
		this.game = game;
		this.player = player;
		stdOut = new OutputStream();
	}
	
	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public ConnectionView getView() 
	{
		return view;
	}
	
	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() 
	{
		return game;
	}
	
	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() 
	{
		return player;
	}
	
	/**
	 * The principal method.<br>
	 * Rotate player's turns in the state machine and suspend him if the timer of the turn is expired.
	 */
	public void run() 
	{
		this.game.setCurrentPlayer(this.player);
		
		TurnState nextState = new DrawPoliticCardState(this.game, this.view);
		this.game.setCurrentState(nextState);
		
		this.game.getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_GAMEBOARD, this.game));
		this.game.notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_TURN, this.game));
		this.game.getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_IS_YOUR_TURN, this.game));
		try
		{
			while( !(this.game.getCurrentState() instanceof FinishState) )
			{
				nextState = this.game.getCurrentState().executeGameState();					
				this.game.setCurrentState(nextState);
			}
			nextState = this.game.getCurrentState().executeGameState();
		}
		catch(SuspendPlayerException e)
		{
			stdOut.getStdOut().println("Player " + (player.getPlayerID() + 1) + " timeout expired.\n");
			this.game.getCurrentState().notifyObservers(new InfoMessageType( "Your timeout expired", this.game ));
			this.game.notifyObservers(new BroadcastMessage("Player " + (player.getPlayerID() + 1) + " timeout expired", this.game));
			
			this.game.setCurrentState(new FinishState(this.game, this.view));
			nextState = (new FinishState(this.game, this.view)).executeGameState();
		}
		this.game.notifyObservers(new BroadcastMessage(BroadcastMessageTypeEnum.SHOW_CURRENT_PLAYER_TURN_ENDED, this.game));
		this.game.getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_YOUR_TURN_IS_ENDED, this.game));
	}
}
