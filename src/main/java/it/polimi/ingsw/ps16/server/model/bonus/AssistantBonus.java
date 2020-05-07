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
package it.polimi.ingsw.ps16.server.model.bonus;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.player.AssistantsHeap;

/**
 * The Class AssistantBonus.
 */
public class AssistantBonus extends Bonus implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1989919431263605388L;

	/**
	 * Instantiates a new assistant bonus.
	 *
	 * @param quantity
	 *            the quantity
	 */
	public AssistantBonus(int quantity)
	{
		super(quantity);
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.bonus.Bonus#executeBonus(it.polimi.ingsw.ps16.server.model.Game)
	 */
	@Override
	public void executeBonus(Game game) 
	{
		AssistantsHeap playerAssistantsHeap = game.getCurrentPlayer().getAssistants();
		
		playerAssistantsHeap.addAssistants(this.getQuantity());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "AssistantBonus with value: " + this.getQuantity();
	}
}
