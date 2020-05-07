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


/**
 * The Class NobilityTrack provides the nobility track according to CoF rules.
 */

public class NobilityTrack implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7946286906024419714L;
	
	/** The track. */
	private final List<NobilityCell> track;
	
	/**
	 * Instantiates a new nobility track.
	 *
	 * @param track
	 *            the track
	 */
	public NobilityTrack(List<NobilityCell> track) 
	{
		this.track = track;
	}
	
	/**
	 * Gets the track.
	 *
	 * @return the track
	 */
	public List<NobilityCell> getTrack() 
	{
		return track;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder toString = new StringBuilder("");
		
		if( track != null && !track.isEmpty() )
		{
			toString.append("\n");
			for( int i = 0; i < track.size(); i++ )
			{
				toString.append("\t\tCell " + i + ": " + track.get(i).toString());
			}
		}
		else
		{
			toString.append("no cells");
		}
		
		return toString.toString();
	}
}