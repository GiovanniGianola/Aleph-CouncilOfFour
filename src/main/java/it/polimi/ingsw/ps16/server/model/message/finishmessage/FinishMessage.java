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
package it.polimi.ingsw.ps16.server.model.message.finishmessage;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.message.FinishMessageTypeEnum;

/**
 * The Class FinishMessage.
 */
public class FinishMessage implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1717466947081888249L;
	
	/** The finish message. */
	private FinishMessageTypeEnum finishMessage;
		
	/**
	 * Instantiates a new finish message.
	 *
	 * @param finishMessage
	 *            the finish message
	 */
	public FinishMessage(FinishMessageTypeEnum finishMessage) 
	{
		this.finishMessage = finishMessage;
	}
	
	/**
	 * Instantiates a new finish message.
	 *
	 * @param finishMessage
	 *            the finish message
	 * @param game
	 *            the game
	 */
	public FinishMessage(FinishMessageTypeEnum finishMessage, Game game) 
	{
		this.finishMessage = finishMessage;
		game.setChanges();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.finishMessage.toString();
	}
}
