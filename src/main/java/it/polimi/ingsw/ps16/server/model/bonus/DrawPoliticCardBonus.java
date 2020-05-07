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
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;


/**
 * The Class DrawPoliticCardBonus.
 */
public class DrawPoliticCardBonus extends Bonus implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8346960820871685335L;

	/**
	 * Instantiates a new draw politic card bonus.
	 *
	 * @param quantity
	 *            the quantity
	 */
	public DrawPoliticCardBonus(int quantity)
	{
		super(quantity);
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.bonus.Bonus#executeBonus(it.polimi.ingsw.ps16.server.model.Game)
	 */
	@Override
	public void executeBonus(Game game)
	{
		PoliticCard cardDrawed;
		
		boolean emptyDeck = false;
		for( int i = 0; i < this.getQuantity() && !emptyDeck; i++ )
		{
			if( !(game.getGameBoard().getPoliticCardsDeck().isEmpty()) )
			{
				cardDrawed = game.getGameBoard().drawPoliticCard();
				game.getCurrentPlayer().addPoliticCard(cardDrawed);
			}
			else
			{
				emptyDeck = true;
			}
		}
		if(emptyDeck)
		{
			game.getCurrentState().notifyObservers(new ErrorMessage("No more Politic Cards.", game.getCurrentState()));
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "DrawPoliticCardBonus with value: " + this.getQuantity();
	}
}
