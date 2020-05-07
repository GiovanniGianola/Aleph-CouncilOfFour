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

import it.polimi.ingsw.ps16.server.model.bonus.Bonus;
import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;
import it.polimi.ingsw.ps16.server.model.player.Emporium;

/**
 * The Class City contains every informations about city.<br>
 * and provides some useful methods to modifies it
 */
public class City extends Node implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9159533141671784732L;
	
	/** The name. */
	private final String name;
	
	/** The a bonus. */
	private final List<Bonus> aBonus;
	
	/** The bonus boolean. */
	private final String bonusBoolean;
	
	/** The emporiums. */
	private final Emporium[] emporiums;
	
	/** The region board. */
	private RegionBoard regionBoard;
	
	/** The type. */
	private CityType type;
	
	/** The near cities. */
	private List<City> nearCities;
	
	
	/**
	 * Instantiates a new city.
	 *
	 * @param name
	 *            the name
	 * @param numPlayers
	 *            the num players
	 * @param bonusBoolean
	 *            the bonus boolean
	 */
	//Constructor use numPlayer for create its ArraList emporiums
	public City(String name, int numPlayers, String bonusBoolean) 
	{
		//Chiamata al costruttore della superclasse "Node", il quale setta il colore del nodo della città a "WHITE"
		super();
		
		this.name = name;
		this.aBonus = new ArrayList<>();
		this.bonusBoolean = bonusBoolean;
		
		emporiums = new Emporium[numPlayers];
		for( int i = 0 ; i < numPlayers ; i++ )
		{
			emporiums[i] = null;
		}
		
		this.nearCities = new ArrayList<>();
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
	 * Gets the bonus.
	 *
	 * @return the bonus
	 */
	public List<Bonus> getBonus() 
	{
		return aBonus;
	}
	
	/**
	 * Gets the bonus boolean.
	 *
	 * @return the bonus boolean
	 */
	public String getBonusBoolean() 
	{
		return bonusBoolean;
	}
	
	/**
	 * Gets the emporiums.
	 *
	 * @return the emporiums
	 */
	public Emporium[] getEmporiums() 
	{
		return emporiums;
	}
	
	/**
	 * Sets the region board.
	 *
	 * @param regionBoard
	 *            the new region board
	 */
	public void setRegionBoard(RegionBoard regionBoard) 
	{
		this.regionBoard = regionBoard;
	}

	/**
	 * Gets the region board.
	 *
	 * @return the region board
	 */
	public RegionBoard getRegionBoard() 
	{
		return regionBoard;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(CityType type) 
	{
		this.type = type;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public CityType getType() 
	{
		return type;
	}

	/**
	 * Gets the near cities.
	 *
	 * @return the near cities
	 */
	public List<City> getNearCities() 
	{
		return nearCities;
	}
	
	/**
	 * Adds the bonus.
	 *
	 * @param bonus
	 *            the bonus
	 */
	public void addBonus(Bonus bonus)
	{
		this.aBonus.add(bonus);
	}
	
	/**
	 * Builds the emporium.
	 *
	 * @param emporium
	 *            the emporium
	 * @param kingBoard
	 *            the king board
	 * @return the list
	 */
	public List<BonusTile> buildEmporium(Emporium emporium, KingBoard kingBoard)
	{
		this.emporiums[emporium.getPlayerID()] = emporium;
		
		List<BonusTile> bonusTiles = new ArrayList<>();
		List<BonusTile> regionBonusTiles = this.regionBoard.searchForBonusTile(emporium.getPlayerID(), kingBoard);
		if( regionBonusTiles != null )
		{
			bonusTiles.addAll(regionBonusTiles);
		}
		List<BonusTile> typeBonusTiles = this.type.searchForBonusTile(emporium.getPlayerID(), kingBoard);
		if( typeBonusTiles != null )
		{
			bonusTiles.addAll(typeBonusTiles);
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
		
		toString.append("\t\tName: " + name + "\n");
		
		toString.append("\t\tRegion: ");
		if(regionBoard == null)
		{
			toString.append("\t\tno region");
		}
		else
		{
			toString.append(regionBoard.getName());
		}
		toString.append("\n");
		
		toString.append("\t\tType: ");
		if(type == null)
		{
			toString.append("\t\tno type");
		}
		else
		{
			toString.append(type.getName());
		}
		toString.append("\n");
		
		toString.append(this.printBonus());
		
		toString.append("\t\tAre built emporiums of players: ");
		if(emporiums.length == 0)
		{
			toString.append("list of emporiums have length 0");
		}
		else
		{
			StringBuilder emporiumsOfPlayers = new StringBuilder("");
			for( Emporium emporium : emporiums )
			{
				if( emporium != null )
				{
					emporiumsOfPlayers.append((emporium.getPlayerID()+1) + " ");
				}
			}
			if( emporiumsOfPlayers.length() == 0 )
			{
				toString.append("no emporiums built");
			}
			else
			{
				toString.append(emporiumsOfPlayers);
			}
		}
		toString.append("\n");
		
		toString.append("\t\tNear cities: ");
		if(nearCities.isEmpty())
		{
			toString.append("no near cities");
		}
		else
		{
			for( City city : nearCities )
			{
				toString.append(city.getName() + " ");
			}
		}
		
		return toString.toString();
	}
	
	/**
	 * Prints the bonus.
	 *
	 * @return the string
	 */
	private String printBonus()
	{
		StringBuilder str = new StringBuilder("");
		str.append("\t\tBonus:  ");
		if( !aBonus.isEmpty() )
		{
			for( Bonus bonus : aBonus )
			{
				str.append(bonus.toString() + "\n");
				if(bonus != aBonus.get(aBonus.size() - 1))
				{
					str.append("\t\t\t");
				}
			}
		}
		else
		{
			str.append("no bonus");
			str.append("\n");
		}
		return str.toString();
	}
}