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
import java.util.Collections;
import java.util.List;

/**
 * The Class Deck.
 *
 * @param <E>
 *            the element type
 */
public class Deck<E extends Card> implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7478425763762759717L;
	
	/** The cards. */
	private List<E> cards;
	
	/**
	 * Instantiates a new deck.
	 *
	 * @param cards
	 *            the cards
	 */
	public Deck(List<E> cards)
	{
		this.cards = cards;
	}
	
	/**
	 * Gets the cards.
	 *
	 * @return the cards
	 */
	public List<E> getcards() 
	{
		return cards;
	}
	
	/**
	 * Draw card.
	 *
	 * @return the e
	 */
	public E drawCard()
	{
		if( isEmpty() )
		{
			return null;
		}
		return cards.remove(0);
	}
	
	/**
	 * Adds the card.
	 *
	 * @param card
	 *            the card
	 */
	public void addCard(E card)
	{
		if( cards == null )
		{
			cards = new ArrayList<>();
			cards.add(card);
		}
		else
		{
			cards.add(card);
		}
	}
	
	/**
	 * Shuffle.
	 */
	public void shuffle()
	{
		if( !isEmpty() )
		{
			Collections.shuffle(cards);
		}
	}
	
	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty()
	{
		return cards.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder toString = new StringBuilder("");
		if(cards.isEmpty())
		{
			toString.append("no cards");
		}
		else
		{
			for( E card : cards )
			{
				toString.append(card.toString() + " ");
			}
		}
		return toString.toString();		
	}
}
