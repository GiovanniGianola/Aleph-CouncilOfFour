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
import java.util.List;

import it.polimi.ingsw.ps16.server.model.bonus.Bonus;
import it.polimi.ingsw.ps16.server.model.gameboard.City;


/**
 * The Class BusinessPermitCard.
 */
public class BusinessPermitCard extends Card implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3473816211853771137L;
	
	/** The bonus. */
	private final List<Bonus> bonus;
	
	/** The cities. */
	private final List<City> cities;
	
	/**
	 * Instantiates a new business permit card.
	 *
	 * @param bonus
	 *            the bonus
	 * @param cities
	 *            the cities
	 */
	public BusinessPermitCard( List<Bonus> bonus, List<City> cities) 
	{
		this.bonus = bonus;
		this.cities = cities;
	}

	/**
	 * Gets the bonus.
	 *
	 * @return the bonus
	 */
	public List<Bonus> getBonus() 
	{
		return bonus;
	}

	/**
	 * Gets the cities.
	 *
	 * @return the cities
	 */
	public List<City> getCities() 
	{
		return cities;
	}
	
	/**
	 * Cities to string.
	 *
	 * @return the string
	 */
	public String citiesToString()
	{
		StringBuilder toString = new StringBuilder("");

		toString.append("Cities: ");
		if( cities != null && !cities.isEmpty())
		{
			for( City city : cities )
			{
				toString.append(city.getName() + " ");
			}
		}
		else
		{
			toString.append("no cities");
		}
		
		return toString.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		StringBuilder toString = new StringBuilder("");
		
		toString.append("\tBonus:  ");
		if( bonus != null && !bonus.isEmpty() )
		{
			for( Bonus b : bonus )
			{
				if(b != null)
				{
					toString.append(b.toString() + "\n");
					if(b != this.bonus.get(this.bonus.size() - 1))
					{
						toString.append("\t\t\t");
					}
				}
			}
		}
		else
		{
			toString.append("\tno bonus");
			toString.append("\n");
		}
		
		toString.append("\t\tCities: ");
		if( cities != null && !cities.isEmpty())
		{
			for( City city : cities )
			{
				toString.append(city.getName() + " ");
			}
		}
		else
		{
			toString.append("no cities");
		}
		
		return toString.toString();
	}
}
