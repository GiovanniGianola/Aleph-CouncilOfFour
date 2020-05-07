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
package it.polimi.ingsw.ps16.server.model.market;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.player.AssistantsHeap;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class MarketObject is the object that contains what a player want to sell during market phase.
 */
public class MarketObject implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8576330712137892634L;

	/** The seller. */
	private Player seller;
	
	/** The for sale object. */
	private transient Object forSaleObject;
	
	/** The price. */
	private transient Object price;
	

	/**
	 * Sets the seller.
	 *
	 * @param seller
	 *            the new seller
	 */
	public void setSeller(Player seller) {
		this.seller = seller;
	}

	/**
	 * Sets the for sale object.
	 *
	 * @param forSaleObject
	 *            the new for sale object
	 */
	public void setForSaleObject(Object forSaleObject) {
		this.forSaleObject = forSaleObject;
	}

	/**
	 * Sets the price.
	 *
	 * @param price
	 *            the new price
	 */
	public void setPrice(Object price) {
		this.price = price;
	}

	/**
	 * Gets the seller.
	 *
	 * @return the seller
	 */
	public Player getSeller() {
		
		return seller;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Object getPrice() {
		return price;
	}

	/**
	 * Gets the for sale object.
	 *
	 * @return the for sale object
	 */
	public Object getForSaleObject() {
		
		return forSaleObject;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder toString = new StringBuilder("");
		
		toString.append("\tSeller ID: " + (this.getSeller().getPlayerID() + 1));
		toString.append("\n\t\tFor sale object:");
		if( this.getForSaleObject() instanceof PoliticCard )
		{
			toString.append("\n\t\tPolitic card: ");
		}
		else
		{
			toString.append("\n\t\tBusinessPermitCard: ");
		}
		toString.append(this.getForSaleObject().toString());
		toString.append("\n\t\tPrice in ");
		if( this.getPrice() instanceof AssistantsHeap )
		{
			toString.append("Assistants: ");
		}
		else
		{
			toString.append("Coins: ");
		}
		toString.append(this.getPrice());
		
		return toString.toString();
	}
}
