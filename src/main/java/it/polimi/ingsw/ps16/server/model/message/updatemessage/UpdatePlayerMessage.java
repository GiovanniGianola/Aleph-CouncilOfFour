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
import it.polimi.ingsw.ps16.server.model.gameboard.King;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class UpdatePlayerMessage.
 */
public class UpdatePlayerMessage implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6620010118643903547L;
	
	/** The player. */
	private Player player;
	
	/** The king. */
	private King king;
	
	/** The infos. */
	private String[] infos;

	/**
	 * Instantiates a new update player message.
	 *
	 * @param game
	 *            the game
	 */
	public UpdatePlayerMessage(Game game) 
	{
		this.player = game.getCurrentPlayer();
		this.king = game.getGameBoard().getKing();
		game.setChanges();	
		if(game.getCurrentState() != null)
			game.getCurrentState().setChanges();
		
		infos = new String[8];
		
		this.fillInfos();
	}
	
	/**
	 * Fill infos.
	 */
	private void fillInfos()
	{
		infos[0] = this.getAssistant().toString();
		infos[1] = this.getEmporium().toString();
		infos[2] = this.getPoliticCard();
		infos[3] = this.getKingInfo()[0];
		infos[4] = this.getKingInfo()[1];
		infos[5] = this.getVictoryTrack().toString();
		infos[6] = this.getCoinsTrack().toString();
		infos[7] = this.getNobilityTrack().toString();
	}
	
	/**
	 * Gets the assistant.
	 *
	 * @return the assistant
	 */
	private Integer getAssistant()
	{
		return this.player.getAssistants().getAssistants();
	}
	
	/**
	 * Gets the emporium.
	 *
	 * @return the emporium
	 */
	private Integer getEmporium()
	{
		return this.player.getEmporiumStack().size();
	}
	
	/**
	 * Gets the politic card.
	 *
	 * @return the politic card
	 */
	private String getPoliticCard()
	{
		StringBuilder cards = new StringBuilder("");
		for(int i = 0; i < this.player.getPoliticCardsList().size(); i++)
			cards.append(this.player.getPoliticCardsList().get(i).getColor().toString() + " ");
		return cards.toString();
	}
	
	/**
	 * Gets the king info.
	 *
	 * @return the king info
	 */
	private String[] getKingInfo()
	{
		String[] kingS = new String[2];
		if(king.getName() != null && king.getName().length() > 0 && !king.getName().isEmpty())
		{
			kingS[1] = "Name: " + king.getName();
			kingS[0] = "City: " + king.getCity().getName();
			return kingS;
		}
		
		kingS[1] = "#NoName";
		kingS[0] = "City: " + king.getCity().getName();
		return kingS;
	}
	
	/**
	 * Gets the victory track.
	 *
	 * @return the victory track
	 */
	private Integer getVictoryTrack()
	{
		return this.player.getVictoryMarkerDisc().getPosition();
	}
	
	/**
	 * Gets the coins track.
	 *
	 * @return the coins track
	 */
	private Integer getCoinsTrack()
	{
		return this.player.getRichnessMarkerDisc().getPosition();
	}
	
	/**
	 * Gets the nobility track.
	 *
	 * @return the nobility track
	 */
	private Integer getNobilityTrack()
	{
		return this.player.getNobilityMarkerDisc().getPosition();
	}
	
	/**
	 * Gets the infos.
	 *
	 * @return the infos
	 */
	public String[] getInfos() 
	{
		return infos;
	}
}
