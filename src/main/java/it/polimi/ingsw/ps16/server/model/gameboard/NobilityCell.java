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
import java.util.List;

import it.polimi.ingsw.ps16.server.model.bonus.Bonus;


/**
 * The Class NobilityCell represents each cell of the nobility track.
 */
public class NobilityCell implements Serializable
{	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3319911626795882279L;
	
	/** The bonus array. */
	private final List<Bonus> bonusArray;
	 
	/**
	 * Instantiates a new nobility cell.
	 *
	 * @param bonusArray
	 *            the bonus array
	 */
	public NobilityCell(List<Bonus> bonusArray)
	{		
		this.bonusArray = bonusArray ;
	}

	/**
	 * Gets the bonus.
	 *
	 * @return the bonus
	 */
	public List<Bonus> getBonus() 
	{		
		return bonusArray;
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		StringBuilder toString = new StringBuilder("");
		
		//toString.append("Bonus:  ");
		if( bonusArray != null && !bonusArray.isEmpty() )
		{
			for( Bonus bonus : bonusArray )
			{
				toString.append(bonus.toString() + "\n");
				if(bonus != bonusArray.get(bonusArray.size() - 1))
				{
					toString.append("\t\t\t");
				}
			}
		}
		else
		{
			toString.append("no bonus");
			toString.append("\n");
		}
		
		return toString.toString();
	}
}
