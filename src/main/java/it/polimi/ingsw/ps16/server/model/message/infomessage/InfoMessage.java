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

import java.io.Serializable;


/**
 * The Class InfoMessage.
 */
public abstract class InfoMessage implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1411394558712321744L;
	
	/**
	 * Instantiates a new info message.
	 */
	public InfoMessage() 
	{
		/* Empty Constructor */
	}
	
	/**
	 * Gets the current player ID.
	 *
	 * @return the current player ID
	 */
	public abstract int getCurrentPlayerID();
}
