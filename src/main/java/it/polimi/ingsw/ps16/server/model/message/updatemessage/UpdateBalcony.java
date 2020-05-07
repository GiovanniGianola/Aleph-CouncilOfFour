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
package it.polimi.ingsw.ps16.server.model.message.updatemessage;

import java.io.Serializable;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.gameboard.Councillor;

/**
 * The Class UpdateBalcony.
 */
public class UpdateBalcony implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1930943193888247454L;
	
	/** The game. */
	private Game game;
	
	/** The balconies. */
	String[] balconies;

	/**
	 * Instantiates a new update balcony.
	 *
	 * @param game
	 *            the game
	 */
	public UpdateBalcony(Game game) 
	{
		this.game = game;
		game.setChanges();
		
		balconies = new String[4];
		
		this.fillBalconies();
	}
	
	/**
	 * Fill balconies.
	 */
	private void fillBalconies()
	{
		balconies[0] = getKingBalcony();
		balconies[1] = getCoastBalcony();
		balconies[2] = getHillBalcony();
		balconies[3] = getMountBalcony();
	}
	
	/**
	 * Gets the king balcony.
	 *
	 * @return the king balcony
	 */
	private String getKingBalcony()
	{
		StringBuilder str =  new StringBuilder("");
		for(Councillor s : this.game.getGameBoard().getKingBoard().getBalcony().getBalcony()) 
			  str.append(s.getColor().toString() + ";");
		return str.toString();
	}
	
	/**
	 * Gets the coast balcony.
	 *
	 * @return the coast balcony
	 */
	private String getCoastBalcony()
	{
		StringBuilder str =  new StringBuilder("");
		for(Councillor s : this.game.getGameBoard().getRegionBoard().get(0).getBalcony().getBalcony()) 
			str.append(s.getColor().toString() + ";");
		return str.toString();
	}
	
	/**
	 * Gets the hill balcony.
	 *
	 * @return the hill balcony
	 */
	private String getHillBalcony()
	{
		StringBuilder str =  new StringBuilder("");
		for(Councillor s : this.game.getGameBoard().getRegionBoard().get(1).getBalcony().getBalcony()) 
			str.append(s.getColor().toString() + ";");
		return str.toString();
	}
	
	/**
	 * Gets the mount balcony.
	 *
	 * @return the mount balcony
	 */
	private String getMountBalcony()
	{
		StringBuilder str =  new StringBuilder("");
		for(Councillor s : this.game.getGameBoard().getRegionBoard().get(2).getBalcony().getBalcony()) 
			str.append(s.getColor().toString() + ";");
		return str.toString();
	}
	
	/**
	 * Gets the infos.
	 *
	 * @return the infos
	 */
	public String[] getInfos() 
	{
		return balconies;
	}
}
