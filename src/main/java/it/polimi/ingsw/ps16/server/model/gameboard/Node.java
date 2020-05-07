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


/**
 * The Class Node for the graph visits.
 */
public class Node 
{
	
	/**
	 * The Enum NodeColor.
	 */
	public enum NodeColor
	{
		
		/** The black. */
		BLACK("BLACK"),
		
		/** The grey. */
		GREY("GREY"),
		
		/** The white. */
		WHITE("WHITE");
		
		/** The node colour. */
		private String nodeColour;
		
		/**
		 * Instantiates a new node color.
		 *
		 * @param nodeColor
		 *            the node color
		 */
		private NodeColor(String nodeColor)
		{
			this.nodeColour = nodeColor;
		}
		
		/**
		 * Gets the color.
		 *
		 * @return the color
		 */
		public String getColor()
		{
			return(this.nodeColour);
		}
	}
	
	/** The node color. */
	private NodeColor nodeColor;
	
	/** The distance. */
	private int distance;
	
	
	
	/**
	 * Instantiates a new node.
	 */
	public Node()
	{
		this.nodeColor = NodeColor.valueOf("WHITE");
		this.distance = 0;
	}
	
	
	
	/**
	 * Sets the color.
	 *
	 * @param nodeColor
	 *            the new color
	 */
	public void setColor(String nodeColor)
	{
		this.nodeColor = NodeColor.valueOf(nodeColor);
	}
	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public NodeColor getColor()
	{
		return this.nodeColor;
	}
	
	/**
	 * Sets the distance.
	 *
	 * @param distance
	 *            the new distance
	 */
	public void setDistance(int distance)
	{
		this.distance = distance;
	}
	
	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public int getDistance()
	{
		return distance;
	}
}
