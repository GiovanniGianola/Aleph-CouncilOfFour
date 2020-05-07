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
package it.polimi.ingsw.ps16.server.model.deck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps16.server.model.gameboard.Colour;

/**
 * A factory for creating StandardDecks objects.
 */
public class StandardDecksFactory implements DecksFactory, Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8660103350095084855L;

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.deck.DecksFactory#createBusinessPermitCardDeck(java.util.List)
	 */
	@Override
	public Deck<BusinessPermitCard> createBusinessPermitCardDeck(List<BusinessPermitCard> cards)
	{
		Deck<BusinessPermitCard> deck = new Deck<>(cards);
		deck.shuffle();
		
		return deck;
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.deck.DecksFactory#createPoliticCardDeck(int)
	 */
	@Override
	public Deck<PoliticCard> createPoliticCardDeck(int numPlayers)
	{
		List<PoliticCard> cards = new ArrayList<>();
		
		int cardsToCreateForColour = (13 * numPlayers) / 4;
		
		for (Colour color : Colour.values())
		{
			for(int i = 0; i < cardsToCreateForColour; i++)
				cards.add(new PoliticCard(color));
		}
		
		Deck<PoliticCard> deck = new Deck<>(cards);
		deck.shuffle();
		
		return deck;
	}
}
