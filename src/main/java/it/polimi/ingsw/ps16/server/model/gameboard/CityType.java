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

import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;


/**
 * The Class CityType contains every type of city.<br>
 * e.g. IRON, GOLD, BLUE, PURPLE, BRONZE
 */
public class CityType implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3974765751664147009L;
	
	/** The name. */
	private final String name;
	
	/** The a cities. */
	private final List<City> aCities;
	
	/** The bonus tile. */
	private BonusTile bonusTile;
		
	/**
	 * Instantiates a new city type.
	 *
	 * @param name
	 *            the name
	 */
	public CityType(String name) 
	{
		this.name = name;
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
	 * Gets the cities.
	 *
	 * @return the cities
	 */
	public List<City> getCities() 
	{
		return aCities;
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
		
		BonusTile typeBonusTile = this.drawBonusTile();
		if( typeBonusTile != null )
		{
			bonusTiles.add(typeBonusTile);
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
		
		toString.append("\tName Type: " + name + "\n");
		
		toString.append("\tBonusTile: ");
		if(bonusTile == null)
		{
			toString.append("no bonus tile");
		}
		else
		{
			toString.append(bonusTile.getBonus().toString());
		}
		toString.append("\n");
		
		toString.append("\tCities list: ");
		if(aCities.isEmpty())
		{
			toString.append("no cities");
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
