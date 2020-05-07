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
 * The Enum BroadcastMessageTypeEnum.
 */
public enum BroadcastMessageTypeEnum 
{
	
	/** The show current player turn. */
	SHOW_CURRENT_PLAYER_TURN,
	
	/** The show current player turn ended. */
	SHOW_CURRENT_PLAYER_TURN_ENDED,
	
	/** The show current player sale turn. */
	SHOW_CURRENT_PLAYER_SALE_TURN,
	
	/** The show current player buy turn. */
	SHOW_CURRENT_PLAYER_BUY_TURN,
	
	/** The show current player buy turn is ended. */
	SHOW_CURRENT_PLAYER_BUY_TURN_IS_ENDED,
	
	/** The show current player sale turn is ended. */
	SHOW_CURRENT_PLAYER_SALE_TURN_IS_ENDED,
	
	/** The show current player state. */
	SHOW_CURRENT_PLAYER_STATE,
	
	/** The show current player action. */
	SHOW_CURRENT_PLAYER_ACTION,
	
	/** The show current player choice. */
	SHOW_CURRENT_PLAYER_CHOICE;
}
