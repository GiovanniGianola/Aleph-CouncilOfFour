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

import it.polimi.ingsw.ps16.server.model.deck.Card;
import it.polimi.ingsw.ps16.server.model.deck.Deck;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.deck.StandardDecksFactory;


/**
 * The Class GameBoard represent the game board's objects according to CoF rulesbook.
 */
public class GameBoard implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1689151766216334365L;
	
	/** The king. */
	private King king;
	
	/** The king board. */
	private KingBoard kingBoard;
	
	/** The politic cards deck. */
	private Deck<PoliticCard> politicCardsDeck;
	
	/** The discarded politic cards deck. */
	private Deck<PoliticCard> discardedPoliticCardsDeck;
	
	/** The region boards. */
	private List<RegionBoard> regionBoards;
	
	/** The city types. */
	private List<CityType> cityTypes;
	
	

	/**
	 * Gets the king.
	 *
	 * @return the king
	 */
	public King getKing()
	{
		return king;
	}
	
	/**
	 * Sets the king.
	 *
	 * @param kingName
	 *            the new king
	 */
	public void setKing(String kingName)
	{
		for(int i = 0; i <cityTypes.size(); i++)
		{
			if(("false").equalsIgnoreCase(cityTypes.get(i).getCities().get(0).getBonusBoolean()))
				this.king = new King(kingName, cityTypes.get(i).getCities().get(0));
		}
	}
	
	/**
	 * Gets the king board.
	 *
	 * @return the king board
	 */
	public KingBoard getKingBoard()
	{
		return kingBoard;
	}

	/**
	 * Sets the king board.
	 *
	 * @param kingBoard
	 *            the new king board
	 */
	public void setKingBoard(KingBoard kingBoard) 
	{
		this.kingBoard = kingBoard;
	}
	
	/**
	 * Gets the politic cards deck.
	 *
	 * @return the politic cards deck
	 */
	public Deck<PoliticCard> getPoliticCardsDeck() 
	{
		return politicCardsDeck;
	}

	/**
	 * Sets the politic cards deck.
	 *
	 * @param numPlayers
	 *            the new politic cards deck
	 */
	public void setPoliticCardsDeck(int numPlayers) 
	{
		this.politicCardsDeck = (new StandardDecksFactory()).createPoliticCardDeck(numPlayers);
	}

	/**
	 * Gets the discarded politic cards deck.
	 *
	 * @return the discarded politic cards deck
	 */
	public Deck<PoliticCard> getDiscardedPoliticCardsDeck() 
	{
		return discardedPoliticCardsDeck;
	}

	/**
	 * Sets the discarded politic cards deck.
	 */
	public void setDiscardedPoliticCardsDeck() 
	{
		this.discardedPoliticCardsDeck = new Deck<>(null);
	}

	/**
	 * Gets the region board.
	 *
	 * @return the region board
	 */
	public List<RegionBoard> getRegionBoard() 
	{
		return regionBoards;
	}

	/**
	 * Sets the region board.
	 *
	 * @param regionBoard
	 *            the new region board
	 */
	public void setRegionBoard(List<RegionBoard> regionBoard) 
	{
		this.regionBoards = regionBoard;
	}

	/**
	 * Gets the city types.
	 *
	 * @return the city types
	 */
	public List<CityType> getCityTypes() 
	{
		return cityTypes;
	}

	/**
	 * Sets the city types.
	 *
	 * @param cityTypes
	 *            the new city types
	 */
	public void setCityTypes(List<CityType> cityTypes) 
	{
		this.cityTypes = cityTypes;
	}
	
	/**
	 * Put discard deck in politic deck.
	 */
	public void putDiscardDeckInPoliticDeck()
	{
		politicCardsDeck = discardedPoliticCardsDeck;
		discardedPoliticCardsDeck = new Deck<>(new ArrayList<PoliticCard>());
		politicCardsDeck.shuffle();
	}
	
	/**
	 * Draw politic card.
	 *
	 * @return the politic card
	 */
	public PoliticCard drawPoliticCard()
	{
		Card cardToDraw = politicCardsDeck.drawCard();
		
		if( politicCardsDeck.isEmpty() )
		{
			putDiscardDeckInPoliticDeck();
		}
		
		return (PoliticCard)cardToDraw;
	}
	
	/**
	 * Search region by name.
	 *
	 * @param region
	 *            the region
	 * @return the region board
	 */
	public RegionBoard searchRegionByName(String region)
	{
		for(int i = 0; i < this.regionBoards.size(); i++)
		{
			if(region.equalsIgnoreCase(this.regionBoards.get(i).getName()))
				return this.regionBoards.get(i);
		}
		return null;
	}
	
	/**
	 * Search city by name.
	 *
	 * @param city
	 *            the city
	 * @return the city
	 */
	public City searchCityByName(String city)
	{
		for(int i = 0; i < this.regionBoards.size(); i++)
		{
			for(int j = 0; j < this.regionBoards.get(i).getCities().size(); j++)
			{
				if(city.equalsIgnoreCase(this.regionBoards.get(i).getCities().get(j).getName()))
					return this.regionBoards.get(i).getCities().get(j);
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder toString = new StringBuilder("");
		
		if(this.getKing() != null)
			toString.append("\nKing:\n" + king.toString());
		if(this.getKingBoard() != null)
			toString.append("\n\n\nKingBoard:\n" + kingBoard.toString());
		if(this.getRegionBoard() != null)	
			toString.append("\n\n\nRegionBoards:\n");
		if(this.regionBoards != null || !this.regionBoards.isEmpty())
		{
			for(RegionBoard regionBoard : regionBoards)
			{
				toString.append(regionBoard.toString() + "\n\n");
			}
		}
		else
		{
			toString.append("no regions");
		}
		toString.append("\nCityTypes:\n");
		if(this.cityTypes != null || !this.cityTypes.isEmpty())
		{
			for(CityType cityType : cityTypes)
			{
				toString.append(cityType.toString() + "\n");
			}
		}
		else
		{
			toString.append("no city types");
		}		
		
		return toString.toString();
	}
}