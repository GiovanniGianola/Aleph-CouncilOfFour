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

import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;


/**
 * The Class KingBoard provide every information about king board.
 */
public class KingBoard implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8411805434497188747L;

	/** The Constant NUMBER_OF_COUNCILLORS_FOR_BALCONY. */
	private static final int NUMBER_OF_COUNCILLORS_FOR_BALCONY = 4;
	
	/** The Constant NAME. */
	private static final String NAME = "KING";
	
	/** The balcony. */
	private final Balcony balcony;
	
	/** The bonus tiles. */
	private List<BonusTile> bonusTiles;
	
	/** The nobility track. */
	private NobilityTrack nobilityTrack;
	
	/**
	 * Instantiates a new king board.
	 */
	public KingBoard()
	{
		this.balcony = new Balcony(NUMBER_OF_COUNCILLORS_FOR_BALCONY);
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return NAME;
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
	 * Gets the bonus tiles.
	 *
	 * @return the bonus tiles
	 */
	public List<BonusTile> getBonusTiles() 
	{
		return bonusTiles;
	}

	/**
	 * Sets the bonus tiles.
	 *
	 * @param bonusTiles
	 *            the new bonus tiles
	 */
	public void setBonusTiles(List<BonusTile> bonusTiles) 
	{
		this.bonusTiles = bonusTiles;
	}

	/**
	 * Gets the nobility track.
	 *
	 * @return the nobility track
	 */
	public NobilityTrack getNobilityTrack() 
	{
		return nobilityTrack;
	}

	/**
	 * Sets the nobility track.
	 *
	 * @param nobilityTrack
	 *            the new nobility track
	 */
	public void setNobilityTrack(NobilityTrack nobilityTrack) 
	{
		this.nobilityTrack = nobilityTrack;
	}
	
	/**
	 * Draw bonus tile.
	 *
	 * @return the bonus tile
	 */
	public BonusTile drawBonusTile()
	{
		if( !bonusTiles.isEmpty() )
		{
			return bonusTiles.remove(0);
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
		
		toString.append("\tBonusTiles: ");
		if( bonusTiles != null && !bonusTiles.isEmpty() )
		{
			for( BonusTile bonus : bonusTiles )
			{
				toString.append(bonus.getBonus().toString() + "\n");
				if(bonus != bonusTiles.get(bonusTiles.size() - 1))
				{
					toString.append("\t\t    ");
				}
			}
		}
		else
		{
			toString.append("no bonus tiles");
			toString.append("\n");
		}
		
		toString.append("\tBalcony: ");
		toString.append(balcony.toString());
		toString.append("\n\n");
		
		toString.append("\tNobility track: ");
		if( nobilityTrack != null && !nobilityTrack.getTrack().isEmpty() )
		{
			toString.append(nobilityTrack.toString());
		}
		else
		{
			toString.append("no nobility track");
			toString.append("\n");
		}

		
		return toString.toString();
	}
}
