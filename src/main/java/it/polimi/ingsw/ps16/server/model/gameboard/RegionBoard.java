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
package it.polimi.ingsw.ps16.server.model.gameboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.deck.Deck;


/**
 * The Class RegionBoard provide every information about each region.
 */
public class RegionBoard implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8295106486871413814L;
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( RegionBoard.class.getName() );

	/** The Constant NUMBER_OF_COUNCILLORS_FOR_BALCONY. */
	private static final int NUMBER_OF_COUNCILLORS_FOR_BALCONY = 4;
	
	/** The name. */
	private final String name;
	
	/** The balcony. */
	private final Balcony balcony;
	
	/** The business permit cards deck. */
	private Deck<BusinessPermitCard> businessPermitCardsDeck;
	
	/** The business permit tile one. */
	private BusinessPermitCard businessPermitTileOne;
	
	/** The business permit tile two. */
	private BusinessPermitCard businessPermitTileTwo;
	
	/** The bonus tile. */
	private BonusTile bonusTile;
	
	/** The a cities. */
	private final List<City> aCities;
	
	/**
	 * Instantiates a new region board.
	 *
	 * @param name
	 *            the name
	 */
	public RegionBoard(String name) 
	{
		this.name = name;
		this.balcony = new Balcony(NUMBER_OF_COUNCILLORS_FOR_BALCONY);
		this.aCities = new ArrayList<>();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Gets the balcony.
	 *
	 * @return the balcony
	 */
	public Balcony getBalcony() 
	{
		return balcony;
	}
	
	/**
	 * Sets the business permit cards deck for the current region.
	 *
	 * @param businessPermitCardsDeck
	 *            the new business permit cards deck
	 */
	public void setBusinessPermitCardsDeck(Deck<BusinessPermitCard> businessPermitCardsDeck)
	{
		this.businessPermitCardsDeck = businessPermitCardsDeck;
		
		try
		{
			this.businessPermitTileOne = this.businessPermitCardsDeck.drawCard();
		}
		catch(Exception exceptionOfBusinessPermitTileDeckEmpty)
		{
			log.log( Level.SEVERE, "Deck with no cards!", exceptionOfBusinessPermitTileDeckEmpty );
		}
		try
		{
			this.businessPermitTileTwo = this.businessPermitCardsDeck.drawCard();
		}
		catch(Exception exceptionOfBusinessPermitTileDeckEmpty)
		{
			log.log( Level.SEVERE, "Deck with only one card!", exceptionOfBusinessPermitTileDeckEmpty );
		}
	}

	/**
	 * Gets the business permit cards deck.
	 *
	 * @return the business permit cards deck
	 */
	public Deck<BusinessPermitCard> getBusinessPermitCardsDeck() 
	{
		return businessPermitCardsDeck;
	}

	/**
	 * Gets the business permit tile one.
	 *
	 * @return the business permit tile one
	 */
	public BusinessPermitCard getBusinessPermitTileOne() 
	{
		return businessPermitTileOne;
	}

	/**
	 * Gets the business permit tile two.
	 *
	 * @return the business permit tile two
	 */
	public BusinessPermitCard getBusinessPermitTileTwo() 
	{
		return businessPermitTileTwo;
	}
	
	/**
	 * Sets the bonus tile.
	 *
	 * @param bonusTile
	 *            the new bonus tile
	 */
	public void setBonusTile(BonusTile bonusTile) 
	{
		this.bonusTile = bonusTile;
	}
	
	/**
	 * Gets the bonus tile.
	 *
	 * @return the bonus tile
	 */
	public BonusTile getBonusTile() 
	{
		return bonusTile;
	}
	
	/**
	 * Gets the cities.
	 *
	 * @return the cities
	 */
	public List<City> getCities() 
	{
		return aCities;
	}

	/**
	 * Draw business permit tile one.
	 *
	 * @return the business permit card
	 */
	public BusinessPermitCard drawBusinessPermitTileOne() 
	{
		BusinessPermitCard businessPermitTileToDraw = this.businessPermitTileOne;
	
		try
		{
			this.businessPermitTileOne = this.businessPermitCardsDeck.drawCard();
		}
		catch(Exception exceptionOfBusinessPermitTileDeckEmpty)
		{
			log.log( Level.SEVERE, exceptionOfBusinessPermitTileDeckEmpty.toString(), exceptionOfBusinessPermitTileDeckEmpty );
			this.businessPermitTileOne = null;
		}
		
		return businessPermitTileToDraw;
	}

	/**
	 * Draw business permit tile two.
	 *
	 * @return the business permit card
	 */
	public BusinessPermitCard drawBusinessPermitTileTwo() 
	{
		BusinessPermitCard businessPermitTileToDraw = this.businessPermitTileTwo;
		try
		{
			this.businessPermitTileTwo = this.businessPermitCardsDeck.drawCard();
		}
		catch(Exception exceptionOfBusinessPermitTileDeckEmpty)
		{
			log.log( Level.SEVERE, exceptionOfBusinessPermitTileDeckEmpty.toString(), exceptionOfBusinessPermitTileDeckEmpty );
			this.businessPermitTileTwo = null;
		}
		
		return businessPermitTileToDraw;
	}

	/**
	 * Draw bonus tile.
	 *
	 * @return the bonus tile
	 */
	public BonusTile drawBonusTile() 
	{
		BonusTile bonusTileToDraw = this.bonusTile;
		this.bonusTile = null;
		
		return bonusTileToDraw;
	}
	
	/**
	 * Search for bonus tile.
	 *
	 * @param playerID
	 *            the player ID
	 * @param kingBoard
	 *            the king board
	 * @return the list
	 */
	public List<BonusTile> searchForBonusTile(int playerID, KingBoard kingBoard)
	{
		List<BonusTile> bonusTiles = new ArrayList<>();
		
		for( City city : this.aCities )
		{
			if( city.getEmporiums()[playerID] == null )
			{
				return null;
			}
		}
		
		BonusTile regionBonusTile = this.drawBonusTile();
		if( regionBonusTile != null )
		{
			bonusTiles.add(regionBonusTile);
		}
		BonusTile kingBonusTile = kingBoard.drawBonusTile();
		if( kingBonusTile != null )
		{
			bonusTiles.add(kingBonusTile);
		}
		return bonusTiles;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder toString = new StringBuilder("");
		
		toString.append("\tName: " + name + "\n");
		
		toString.append("\tBonusTile: ");
		if(bonusTile == null)
		{
			toString.append("no bonus tile");
		}
		else
		{
			toString.append(bonusTile.getBonus().toString() + "\n");
		}
		
		toString.append("\tBalcony: ");
		toString.append(balcony.toString() + "\n");
		
		toString.append("\n\tBusinessPermitTile 1: \t");
		if(businessPermitTileOne == null)
		{
			toString.append("\tno business permit tile 1");
		}
		else
		{
			toString.append("\n" + businessPermitTileOne.toString());
		}
		toString.append("\n");
		
		toString.append("\n\tBusinessPermitTile 2: \t");
		if(businessPermitTileTwo == null)
		{
			toString.append("\tno business permit tile 2");
		}
		else
		{
			toString.append("\n" + businessPermitTileTwo.toString());
		}
		toString.append("\n");
		
		toString.append("\n\tCities list: ");
		if(aCities.isEmpty())
		{
			toString.append("\n\tno cities");
		}
		else
		{
			toString.append("\n");
			for( City city : aCities )
			{
				toString.append(city.toString() + "\n\n");
			}
		}

		return toString.toString();
	}
}
