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
package it.polimi.ingsw.ps16.server.model.message.infomessage;

import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeInitializeEnum;


/**
 * The Class InfoMessageInitializeType.
 */
public class InfoMessageInitializeType extends InfoMessage
{	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8927135685711411218L;
	
	/** The message enum. */
	private final InfoMessageTypeInitializeEnum messageEnum;
	
	/** The str. */
	private final StringBuilder str;
	
	/** The player number. */
	private int playerNumber;
	
	/** The game number. */
	private int gameNumber;
	
	/**
	 * Instantiates a new info message initialize type.
	 *
	 * @param message
	 *            the message
	 * @param playerNumber
	 *            the player number
	 * @param gameNumber
	 *            the game number
	 */
	public InfoMessageInitializeType(InfoMessageTypeInitializeEnum message, int playerNumber, int gameNumber) 
	{
		this.messageEnum = message;
		this.playerNumber = playerNumber;
		this.gameNumber = gameNumber;
		
		str = new StringBuilder();
		
		searchMessageByEnum();
	}
	
	/**
	 * Search message by enum.
	 */
	private void searchMessageByEnum() 
	{
		switch(this.messageEnum)
		{
			case SHOW_CONNECTION_CREATED:
			{
				str.append("\n*** Connection succesfully created ***\n");
				break;
			}
			case SHOW_PLAYER_GAME_NUMBER:
			{
				str.append("Connected in the " + this.gameNumber + "° game\n");
				if(this.playerNumber == 1)
					str.append("Congratulations! You are the " + this.playerNumber + "° player.\n");
				else
					str.append("You are the " + this.playerNumber + "° player.\n");
				break;
			}
			case SHOW_WAIT_TIMER:
			{
				str.append("Please wait until the timer run out...");
				break;
			}
			case SHOW_NO_ENOUGH_PLAYER:
			{
				str.append("Not enough players, the game can't start");
				break;
			}
			default:
				break;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessage#getCurrentPlayerID()
	 */
	@Override
	public int getCurrentPlayerID()
	{
		return playerNumber;
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
