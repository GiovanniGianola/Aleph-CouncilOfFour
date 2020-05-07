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

import it.polimi.ingsw.ps16.server.model.turn.TurnState;


/**
 * The Class ErrorMessage.
 */
public class ErrorMessage extends InfoMessage 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2680478916178692681L;
	
	/** The error. */
	private String error;
	
	/** The current turn state clone. */
	private final TurnState currentTurnStateClone;

	/**
	 * Instantiates a new error message.
	 *
	 * @param error
	 *            the error
	 * @param currentTurnState
	 *            the current turn state
	 */
	public ErrorMessage(String error, TurnState currentTurnState) 
	{
		this.error = error;
		this.currentTurnStateClone = currentTurnState;
		
		if(this.currentTurnStateClone != null)
			this.currentTurnStateClone.setChanges();
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessage#getCurrentPlayerID()
	 */
	@Override
	public int getCurrentPlayerID()
	{
		return this.currentTurnStateClone.getGame().getCurrentPlayer().getPlayerID();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "[ERROR] " + error;
	}

}
