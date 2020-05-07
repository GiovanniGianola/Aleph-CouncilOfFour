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
package it.polimi.ingsw.ps16.server.model.bonus;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;


/**
 * The Class MoveMarkerNobilityTrackBonus.
 */
public class MoveMarkerNobilityTrackBonus extends Bonus implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6721695461054045950L;

	/**
	 * Instantiates a new move marker nobility track bonus.
	 *
	 * @param quantity
	 *            the quantity
	 */
	public MoveMarkerNobilityTrackBonus(int quantity)
	{
		super(quantity);
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.bonus.Bonus#executeBonus(it.polimi.ingsw.ps16.server.model.Game)
	 */
	@Override
	public void executeBonus(Game game) throws SuspendPlayerException
	{
		try
		{
			game.getCurrentPlayer().getNobilityMarkerDisc().moveForward(this.getQuantity());
		}
		catch(Exception exceptionOfReachLimit)
		{
			throw exceptionOfReachLimit;
		}
		
		List<Bonus> bonusInNewPositionOfNobilityMarkerDisc = game.getGameBoard().getKingBoard().getNobilityTrack().getTrack().get(game.getCurrentPlayer().getNobilityMarkerDisc().getPosition()).getBonus();
		if( bonusInNewPositionOfNobilityMarkerDisc != null )
		{
			for( Bonus bonus : bonusInNewPositionOfNobilityMarkerDisc )
			{
				bonus.executeBonus(game);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "MoveMarkerNobilityTrackBonus with value: " + this.getQuantity();
	}
}
