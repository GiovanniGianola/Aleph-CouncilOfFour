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
package it.polimi.ingsw.ps16.server.model.message.broadcastmessage;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.message.BroadcastMessageTypeEnum;

/**
 * The Class BroadcastMessage.
 */
public class BroadcastMessage implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2272763734615411058L;

	/** The game clone. */
	private Game gameClone;
	
	/** The broadcast message enum. */
	private BroadcastMessageTypeEnum broadcastMessageEnum;
	
	/** The str. */
	private StringBuilder str;
	
	/**
	 * Instantiates a new broadcast message.
	 *
	 * @param message
	 *            the message
	 * @param game
	 *            the game
	 */
	public BroadcastMessage(String message, Game game) 
	{
		this.gameClone = game;
		
		this.gameClone.setChanges();
		
		if(this.gameClone.getCurrentState() !=  null)
			this.gameClone.getCurrentState().setChanges();
		
		str = new StringBuilder(message);		
	}
	
	/**
	 * Instantiates a new broadcast message.
	 *
	 * @param broadcastMessageEnum
	 *            the broadcast message enum
	 * @param game
	 *            the game
	 */
	public BroadcastMessage(BroadcastMessageTypeEnum broadcastMessageEnum, Game game) 
	{
		this.broadcastMessageEnum = broadcastMessageEnum;
		this.gameClone = game;
		
		this.gameClone.setChanges();
		
		if(this.gameClone.getCurrentState() !=  null)
			this.gameClone.getCurrentState().setChanges();
		
		str = new StringBuilder();
		
		searchMessageByEnum();
	}
	
	/**
	 * Gets the current player ID.
	 *
	 * @return the current player ID
	 */
	public int getCurrentPlayerID()
	{
		return gameClone.getCurrentPlayer().getPlayerID();
	}
	
	/**
	 * Search message by enum.
	 */
	private void searchMessageByEnum()
	{
		switch(this.broadcastMessageEnum)
		{
			case SHOW_CURRENT_PLAYER_TURN:
			{
				str.append("\n~~~~~ TURN OF THE PLAYERID: " + (this.getCurrentPlayerID() + 1) + " ~~~~~");
				break;
			}
			case SHOW_CURRENT_PLAYER_TURN_ENDED:
			{
				str.append("\n~~~~~ TURN OF THE PLAYERID: " + (this.getCurrentPlayerID() + 1) + " ENDED ~~~~~");
				break;
			}
			case SHOW_CURRENT_PLAYER_SALE_TURN:
			{
				str.append("\n~~~~~ SALE TURN OF THE PLAYERID: " + (this.getCurrentPlayerID() + 1) + " ~~~~~");
				break;
			}
			case SHOW_CURRENT_PLAYER_BUY_TURN:
			{
				str.append("\n~~~~~ BUY TURN OF THE PLAYERID: " + (this.getCurrentPlayerID() + 1) + "  ~~~~~");
				break;
			}
			case SHOW_CURRENT_PLAYER_BUY_TURN_IS_ENDED:
			{
				str.append("\n~~~~~ BUY TURN OF THE PLAYER ID: " + (this.getCurrentPlayerID() + 1) + " ENDED ~~~~~");
				break;
			}
			case SHOW_CURRENT_PLAYER_SALE_TURN_IS_ENDED:
			{
				str.append("\n~~~~~ SALE TURN OF THE PLAYER ID: " + (this.getCurrentPlayerID() + 1) + " ENDED ~~~~~");
				break;
			}
			case SHOW_CURRENT_PLAYER_STATE:
			{
				str.append("\n\t<< Player ID: " + (this.getCurrentPlayerID() + 1) + " state: " + this.gameClone.getCurrentState().getClass().getSimpleName() + " >>");
				break;
			}
			case SHOW_CURRENT_PLAYER_ACTION:
			{
				str.append("\n\t\t## Player ID: " + (this.getCurrentPlayerID() + 1) + " action: [" + this.gameClone.getCurrentState().getMsg().getChoice().toUpperCase() + "] ##");
				break;
			}
			case SHOW_CURRENT_PLAYER_CHOICE:
			{
				str.append("\t\t\t== Player ID: " + (this.getCurrentPlayerID() + 1) + " choice: [" + this.gameClone.getCurrentState().getMsg().getChoice().toUpperCase() + "] ==");
				break;
			}
			default:
				break;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return str.toString();
	}
}
