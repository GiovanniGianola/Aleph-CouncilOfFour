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


/**
 * The Class King provides every informations about king.
 */
public class King implements Serializable
{	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4764517367296261250L;
	
	/** The name. */
	private final String name;
	
	/** The city. */
	private City city;
	
	
	/**
	 * Instantiates a new king.
	 *
	 * @param name
	 *            the name
	 * @param city
	 *            the city
	 */
	public King(String name, City city)
	{
		this.name = name;
		this.city = city;
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
	 * Gets the city where the king is located.
	 *
	 * @return the city
	 */
	public City getCity() 
	{
		return city;
	}
	
	/**
	 * Sets the city.
	 *
	 * @param city
	 *            the new city
	 */
	public void setCity(City city) 
	{
		this.city = city;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder toString = new StringBuilder("");
		
		toString.append("\tName: " + name + "\n");
		toString.append("\tCity: " + city.getName());
		
		return toString.toString();
	}
}
