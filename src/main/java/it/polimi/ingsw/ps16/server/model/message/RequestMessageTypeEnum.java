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

import it.polimi.ingsw.ps16.server.model.player.Player;


/**
 * The Enum RequestMessageTypeEnum.
 */
public enum RequestMessageTypeEnum 
{
	
	/** The choose one state. */
	CHOOSE_ONE_STATE ("Choose a class of Actions: " 
			+ "\n\t[Main] -> Main Actions."
			+ "\n\t[Quick] -> Quick Actions."),
	
	/** The choose two state. */
	CHOOSE_TWO_STATE ("Choose an Actions: " 
			+ "\n\t[Quick] -> Quick Actions."
			+ "\n\t[End] -> End Turn."),
	
	/** The main actions one. */
	MAIN_ACTIONS_ONE("Choose a Main Action: "
			+ "\n\t[Acquire] -> Acquire a Business Permit Card." 
			+ "\n\t[BuildKing] -> Build an Emporium Using king." 
			+ "\n\t[BuildPermit] -> Build an Emporium using a Business PermitCard."
			+ "\n\t[Elect] -> Elect a Councillor"
			+ "\n\t[Back] -> Back"),
	
	/** The main actions two. */
	MAIN_ACTIONS_TWO("Choose a Main Action: "
			+ "\n\t[Acquire] -> Acquire a Business Permit Card." 
			+ "\n\t[BuildKing] -> Build an Emporium Using king." 
			+ "\n\t[BuildPermit] -> Build an Emporium using a Business PermitCard."
			+ "\n\t[Elect] -> Elect a Councillor"),
	
	/** The quick actions. */
	QUICK_ACTIONS("Choose a Quick Action: "
			+ "\n\t[AddMain] -> Do an Additional Main Action." 
			+ "\n\t[Change] -> Change a Business Permit Card." 
			+ "\n\t[ElectAssistant] -> Elect a Councillor using Assistants."
			+ "\n\t[EngageAssistant] -> Engage Assistants."
			+ "\n\t[Back] -> Back"),
	
	/** The ask region. */
	ASK_REGION("Select a Region: "),
	
	/** The ask councillor. */
	ASK_COUNCILLOR("Select a Councillor: "),
	
	/** The ask businesspermitcard. */
	ASK_BUSINESSPERMITCARD("Select a BusinessPermitCard( [Stop] -> Stop to choose BusinessPermitCards ): "),
	
	/** The ask politiccards. */
	ASK_POLITICCARDS("Select a PoliticCard( [Stop] -> Stop to choose cards ): "),
	
	/** The ask city. */
	ASK_CITY("Select a City: "),
	
	/** The ask what to sell. */
	ASK_WHAT_TO_SELL("Choose what you want to sell: "
			+"\n\t[PoliticCard] -> If you want to sell a PoliticCard"
			+"\n\t[BusinessPermitCard] -> If you want to sell a BusinessPermitCard" 
			+"\n\t[Nothing] -> If you have nothing more to sell"),
	
	/** The ask money or assistant. */
	ASK_MONEY_OR_ASSISTANT("Choose if you want to get: "
			+"\n\t[Assistants]"
			+"\n\t[Coins]"),
	
	/** The ask the price. */
	ASK_THE_PRICE("Insert the price:"
			+"(number of Assistants[1-15]/ quantity of Coins [1-" + Player.getRichnessLimit() + "]"),
	
	/** The ask market object. */
	ASK_MARKET_OBJECT("Select an object to buy( [Stop] -> Stop to buy ):");
	
	/** The message. */
	private final String message;
	
	/**
	 * Instantiates a new request message type enum.
	 *
	 * @param message
	 *            the message
	 */
	private RequestMessageTypeEnum(String message) 
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
