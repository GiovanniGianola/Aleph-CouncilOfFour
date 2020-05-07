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
 * The Enum InfoMessageTypeEnum.
 */
public enum InfoMessageTypeEnum 
{	
	
	/** The show initial message. */
	SHOW_INITIAL_MESSAGE,
	
	/** The show gameboard. */
	SHOW_GAMEBOARD,
	
	/** The show is your turn. */
	SHOW_IS_YOUR_TURN,
	
	/** The show your turn is ended. */
	SHOW_YOUR_TURN_IS_ENDED,
	
	/** The show is your sale turn. */
	SHOW_IS_YOUR_SALE_TURN,
	
	/** The show is your buy turn. */
	SHOW_IS_YOUR_BUY_TURN,
	
	/** The show your buy turn is ended. */
	SHOW_YOUR_BUY_TURN_IS_ENDED,
	
	/** The show your sale turn is ended. */
	SHOW_YOUR_SALE_TURN_IS_ENDED,
		
	/** The show player state. */
	SHOW_PLAYER_STATE,
	
	/** The show modifies string. */
	SHOW_MODIFIES_STRING,
	
	/** The show councillors color. */
	SHOW_COUNCILLORS_COLOR,
	
	/** The show current player. */
	SHOW_CURRENT_PLAYER,
	
	/** The show region balconies. */
	SHOW_REGION_BALCONIES,
	
	/** The show balconies. */
	SHOW_BALCONIES,
	
	/** The show player politiccards. */
	SHOW_PLAYER_POLITICCARDS,
	
	/** The show player tracks. */
	SHOW_PLAYER_TRACKS,
	
	/** The show king balcony. */
	SHOW_KING_BALCONY,
	
	/** The show regions businesspermitcards. */
	SHOW_REGIONS_BUSINESSPERMITCARDS,
	
	/** The show regions businesspermitcards2. */
	SHOW_REGIONS_BUSINESSPERMITCARDS2,
	
	/** The show all regions businesspermitcards. */
	SHOW_ALL_REGIONS_BUSINESSPERMITCARDS,
	
	/** The show all player businesspermitcards. */
	SHOW_ALL_PLAYER_BUSINESSPERMITCARDS,
	
	/** The show all businesspermitcards faceup. */
	SHOW_ALL_BUSINESSPERMITCARDS_FACEUP,
	
	/** The show all cities. */
	SHOW_ALL_CITIES,
	
	/** The show cities where built current player. */
	SHOW_CITIES_WHERE_BUILT_CURRENT_PLAYER,
	
	/** The show request satisfy king council. */
	SHOW_REQUEST_SATISFY_KING_COUNCIL,
	
	/** The show request satisfy council for business. */
	SHOW_REQUEST_SATISFY_COUNCIL_FOR_BUSINESS,
	
	/** The show finish cards. */
	SHOW_FINISH_CARDS,
	
	/** The show player emporiums. */
	SHOW_PLAYER_EMPORIUMS,
	
	/** The show last lap. */
	SHOW_LAST_LAP,
	
	/** The show market objects. */
	SHOW_MARKET_OBJECTS,
	
	/** The show ranking. */
	SHOW_RANKING,
	
	/** The show king position. */
	SHOW_KING_POSITION;
}
