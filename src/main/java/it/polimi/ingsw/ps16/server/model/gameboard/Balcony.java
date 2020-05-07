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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * The Class Balcony provides a balcony for each region king board included. <br>
 * that actually is queue of councillors of predeterminated size
 */
public class Balcony implements Serializable
{	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5296220177047308426L;
	
	/** The balcony. */
	private final Queue<Councillor> bal;
	
	/**
	 * Instantiates a new balcony.
	 *
	 * @param dimension
	 *            the dimension
	 */
	public Balcony(int dimension)
	{
		this.bal = new LinkedList<>();
		
		//Creo i consiglieri in modo casuale
		Random random = new Random();
		Colour[] colors = Colour.values();
		int numberOfColors = colors.length;
		
		for(int i = 0; i < dimension; i++)
		{
			bal.add(new Councillor(colors[random.nextInt(numberOfColors - 1)])); // -1 because the last is JOLLY
		}
	}

	/**
	 * Gets the balcony.
	 *
	 * @return the balcony
	 */
	public Queue<Councillor> getBalcony() 
	{
		return bal;
	}
	
	/**
	 * Elect councillor remove first councillor of the balcony<br>
	 * and add the new one in the queue of the list
	 *
	 * @param councillor
	 *            the councillor
	 */
	public void electCouncillor(Councillor councillor)
	{
		//Eleggo il consigliere passato come parametro(mettendolo in ultima posizione) togliendo il primo
		this.bal.add(councillor);
		this.bal.remove();
	}
	
	/**
	 * Search councillor presence check the presence of a councillor n times.
	 *
	 * @param color
	 *            the color
	 * @param numColoredCards
	 *            the num colored cards
	 * @return true, if successful
	 */
	public boolean searchCouncillorPresence(Colour color, int numColoredCards)
	{
		int nCouncillor = numColoredCards;
		for(Councillor x: bal)
		{
			if(x.getColor()==color)
			{
				nCouncillor--;
			}
		}
		if (nCouncillor <= 0)
			return true;
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		StringBuilder str = new StringBuilder("");
		if(bal.isEmpty())
		{
			str.append("no councillors");
		}
		else
		{
			for( Councillor councillor : bal )
				str.insert(0, councillor.toString() + " ");
		}
		return str.toString();
	}
}
