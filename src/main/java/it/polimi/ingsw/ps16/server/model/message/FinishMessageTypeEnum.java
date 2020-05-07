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
package it.polimi.ingsw.ps16.server.model.message;

/**
 * The Enum FinishMessageTypeEnum.
 */
public enum FinishMessageTypeEnum 
{
	
	/** The finish game message. */
	FINISH_GAME_MESSAGE("\n\t*^*^*^* THE END *^*^*^*"),
	
	/** The stop game message. */
	STOP_GAME_MESSAGE("\n\t**** All players disconnected ****"),
	
	/** The server disconnected. */
	SERVER_DISCONNETTED("\n\t**Server turned down**"),
	
	/** The no enough players. */
	NO_ENOUGH_PLAYERS("\n\t**Not enough players, the game can't start**");
	
	
	/** The message. */
	private final String message;
	
	/**
	 * Instantiates a new finish message type enum.
	 *
	 * @param message
	 *            the message
	 */
	private FinishMessageTypeEnum(String message)
	{
		this.message = message;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() 
	{
		return message;
	}
}
