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
package it.polimi.ingsw.ps16.server.model.message.requestmessage;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeInitializeEnum;
import it.polimi.ingsw.ps16.server.model.turn.TurnState;


/**
 * The Class RequestMessage.
 */
public class RequestMessage implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6102753812263073991L;
	
	/** The request. */
	private RequestMessageTypeEnum request;
	
	/** The request init. */
	private RequestMessageTypeInitializeEnum requestInit;
	
	/** The current turn state clone. */
	private TurnState currentTurnStateClone;
	

	/**
	 * Instantiates a new request message.
	 *
	 * @param request
	 *            the request
	 * @param currentTurnState
	 *            the current turn state
	 */
	public RequestMessage(RequestMessageTypeEnum request, TurnState currentTurnState) 
	{
		this.request = request;
		this.currentTurnStateClone = currentTurnState;
		
		this.currentTurnStateClone.setChanges();
	}
	
	/**
	 * Instantiates a new request message.
	 *
	 * @param requestInit
	 *            the request init
	 */
	public RequestMessage(RequestMessageTypeInitializeEnum requestInit) 
	{
		this.requestInit = requestInit;
	}
	
	/**
	 * Gets the current player ID.
	 *
	 * @return the current player ID
	 */
	public int getCurrentPlayerID()
	{
		if( this.currentTurnStateClone.getGame().getCurrentPlayer() != null)
			return this.currentTurnStateClone.getGame().getCurrentPlayer().getPlayerID();
		return 1000;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		if(this.request != null)
			return this.request.toString();
		return this.requestInit.toString();
	}
}
