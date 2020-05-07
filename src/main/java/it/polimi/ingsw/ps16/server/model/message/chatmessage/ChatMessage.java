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
package it.polimi.ingsw.ps16.server.model.message.chatmessage;

import java.awt.Color;
import java.io.Serializable;

/**
 * The Class ChatMessage.
 */
public class ChatMessage implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -258909766277861785L;
	
	/** The message. */
	private String message;
	
	/** The header. */
	private String header;
	
	/** The color. */
	private Color color;
	
	/**
	 * Instantiates a new chat message.
	 *
	 * @param header
	 *            the header
	 * @param color
	 *            the color
	 * @param message
	 *            the message
	 */
	public ChatMessage(String header, Color color, String message) 
	{
		this.message = message;
		this.header = header;
		this.color = color;
	}
	
	/**
	 * Sender header.
	 *
	 * @return the string
	 */
	public String senderHeader()
	{
		return this.header;
	}
	
	/**
	 * Sender color.
	 *
	 * @return the color
	 */
	public Color senderColor()
	{
		return this.color;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return this.message;
	}
}
