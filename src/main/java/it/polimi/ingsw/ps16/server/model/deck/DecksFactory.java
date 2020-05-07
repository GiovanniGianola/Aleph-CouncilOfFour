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

import java.util.List;

/**
 * A factory for creating Decks objects.
 */
public interface DecksFactory 
{
	
	/**
	 * Creates a new Decks object.
	 *
	 * @param cards
	 *            the cards
	 * @return the deck< business permit card>
	 */
	public Deck<BusinessPermitCard> createBusinessPermitCardDeck(List<BusinessPermitCard> cards);
	
	/**
	 * Creates a new Decks object.
	 *
	 * @param numeroGiocatori
	 *            the numero giocatori
	 * @return the deck< politic card>
	 */
	public Deck<PoliticCard> createPoliticCardDeck(int numeroGiocatori);
}
