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
package it.polimi.ingsw.ps16.server.model.message.replymessage;

import java.io.Serializable;


/**
 * The Class ReplyMessage.
 */
public class ReplyMessage implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4781481210628768664L;
	
	/** The choice. */
	private String choice;

	/**
	 * Instantiates a new reply message.
	 *
	 * @param choice
	 *            the choice
	 */
	public ReplyMessage(String choice) 
	{
		this.choice = choice;
	}

	/**
	 * Gets the choice.
	 *
	 * @return the choice
	 */
	public String getChoice() 
	{
		return choice;
	}
}
