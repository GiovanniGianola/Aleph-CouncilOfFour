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
 * The Enum RequestMessageTypeInitializeEnum.
 */
public enum RequestMessageTypeInitializeEnum 
{
	
	/** The connection created. */
	CONNECTION_CREATED ("Server connection successful"),
	
	/** The choose map. */
	CHOOSE_MAP("Choose map number [ 1 - 2 - 3 - 4 - 5 - 6 - 7 - 8 ]: "),
	
	/** The choose king name. */
	CHOOSE_KING_NAME("Choose king name: "),
	
	/** The choose your name. */
	CHOOSE_YOUR_NAME("Insert your name: "),
	
	/** The choose color. */
	CHOOSE_COLOR("Choose your color:" 
				+ "\n\t[Black] -> Black" 
				+ "\n\t[Blue] -> Blue" 
				+ "\n\t[Red] -> Red"
				+ "\n\t[Yellow] -> Yellow"
				+ "\n\t[Green] -> Green"
				+ "\n\t[Cyan] -> Cyan");
	
	/** The message. */
	private final String message;
	
	/**
	 * Instantiates a new request message type initialize enum.
	 *
	 * @param message
	 *            the message
	 */
	private RequestMessageTypeInitializeEnum(String message) 
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
