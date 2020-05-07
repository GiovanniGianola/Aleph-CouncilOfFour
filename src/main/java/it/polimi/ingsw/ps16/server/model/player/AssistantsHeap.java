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
package it.polimi.ingsw.ps16.server.model.player;

import java.io.Serializable;

/**
 * The Class AssistantsHeap.
 */
public class AssistantsHeap implements Serializable
{	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7411912440639051823L;
	
	/** The assistants. */
	private int assistants;
	
	
	/**
	 * Instantiates a new assistants heap.
	 *
	 * @param assistants
	 *            the assistants
	 */
	public AssistantsHeap(int assistants)
	{		
		this.assistants = assistants;
	}
	
	/**
	 * Gets the assistants.
	 *
	 * @return the assistants
	 */
	public int getAssistants()
	{		
		return assistants;
	}
	
	/**
	 * Adds the assistants.
	 *
	 * @param increase
	 *            the increase
	 */
	public void addAssistants(int increase)
	{		
		this.assistants = this.assistants + increase;
	}
	
	/**
	 * Removes the assistants.
	 *
	 * @param reduction
	 *            the reduction
	 * @return the int
	 */
	public int removeAssistants(int reduction)
	{		
		this.assistants = this.assistants - reduction; 
		return reduction; //the operation of remove from the player 
		//and add the assistants to the game are linked
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "" + this.getAssistants();
	}
}
