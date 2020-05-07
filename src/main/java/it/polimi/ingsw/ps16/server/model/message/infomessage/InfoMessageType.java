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
package it.polimi.ingsw.ps16.server.model.message.infomessage;

import java.util.List;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.bonus.MoveMarkerNobilityTrackBonus;
import it.polimi.ingsw.ps16.server.model.gameboard.City;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class InfoMessageType.
 */
public class InfoMessageType extends InfoMessage
{	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8927135685711411218L;
	
	/** The message enum. */
	private final InfoMessageTypeEnum messageEnum;
	
	/** The str. */
	private final StringBuilder str;
	
	/** The game clone. */
	private Game gameClone;
	
	/**
	 * Instantiates a new info message type.
	 *
	 * @param message
	 *            the message
	 * @param game
	 *            the game
	 */
	public InfoMessageType(InfoMessageTypeEnum message, Game game) 
	{
		this.messageEnum = message;
		this.gameClone = game;
		
		this.gameClone.setChanges();
		
		if(this.gameClone.getCurrentState() !=  null)
			this.gameClone.getCurrentState().setChanges();
		
		str = new StringBuilder();
		
		searchMessageByEnum();
	}
	
	/**
	 * Instantiates a new info message type.
	 *
	 * @param message
	 *            the message
	 * @param game
	 *            the game
	 */
	public InfoMessageType(String message, Game game) 
	{
		this.messageEnum = null;
		
		if( game != null )
		{
			this.gameClone = game;
			
			this.gameClone.setChanges();
			
			if(this.gameClone.getCurrentState() !=  null)
				this.gameClone.getCurrentState().setChanges();
		}

		str = new StringBuilder();
		
		str.append(message);
	}
	
	/**
	 * Search message by enum.
	 */
	private void searchMessageByEnum() 
	{
		switch(this.messageEnum)
		{
			case SHOW_INITIAL_MESSAGE:
			{
				str.append("*** The game is ready ***");
				break;
			}
			case SHOW_GAMEBOARD:
			{
				str.append("\n*** GameBoard state: ***\n");
				str.append(this.gameClone.toString());
				break;
			}
			case SHOW_PLAYER_STATE:
			{
				str.append("\n------- TurnState: " + this.gameClone.getCurrentState().getClass().getSimpleName() + " -------\n");
				break;
			}
			case SHOW_IS_YOUR_TURN:
			{
				str.append("\n~~~~~ YOUR TURN ~~~~~");
				break;
			}
			case SHOW_YOUR_TURN_IS_ENDED:
			{
				str.append("\n~~~~~ YOUR TURN IS ENDED ~~~~~");
				break;
			}
			case SHOW_IS_YOUR_SALE_TURN:
			{
				str.append("\n~~~~~ YOUR SALE TURN ~~~~~");
				break;
			}
			case SHOW_IS_YOUR_BUY_TURN:
			{
				str.append("\n~~~~~ YOUR BUY TURN ~~~~~");
				break;
			}
			case SHOW_YOUR_BUY_TURN_IS_ENDED:
			{
				str.append("\n~~~~~ YOUR BUY TURN IS ENDED ~~~~~");
				break;
			}
			case SHOW_YOUR_SALE_TURN_IS_ENDED:
			{
				str.append("\n~~~~~ YOUR SALE TURN IS ENDED ~~~~~");
				break;
			}
			case SHOW_MODIFIES_STRING:
			{
				str.append("\n------- After Action Modifies -------\n");
				break;
			}
			case SHOW_PLAYER_POLITICCARDS:
			{
				str.append("\nYour Politic Cards: ");
				if( this.gameClone.getCurrentPlayer().getPoliticCardsList().isEmpty() )
				{
					str.append("no Politic Cards");
				}
				else
				{
					for(int i = 0; i < this.gameClone.getCurrentPlayer().getPoliticCardsList().size(); i++)
						str.append(this.gameClone.getCurrentPlayer().getPoliticCardsList().get(i).toString() + " ");
				}
				break;
			}
			case SHOW_PLAYER_TRACKS:
			{
				str.append("Victory Track position: " + this.gameClone.getCurrentPlayer().getVictoryMarkerDisc().getPosition() + "\n");
				str.append("Richness Track position: " + this.gameClone.getCurrentPlayer().getRichnessMarkerDisc().getPosition() + "\n");
				str.append("Nobility Track position: " + this.gameClone.getCurrentPlayer().getNobilityMarkerDisc().getPosition() + "\n");
				break;
			}
			case SHOW_COUNCILLORS_COLOR:
			{
				for(int i = 0; i < Colour.values().length - 1; i++ )
				{
				     str.append("\n\t" + "[" + Colour.values()[i] + "] ->");
				     str.append(" " + Colour.values()[i]);
				}
				break;
			}
			case SHOW_CURRENT_PLAYER:
			{
				str.append("\n\tYour status: \n");
				str.append(this.gameClone.getCurrentPlayer().toString());
				break;
			}
			case SHOW_REGION_BALCONIES:
			{
				str.append("\n\tRegions:");
				for(int i = 0; i < this.gameClone.getGameBoard().getRegionBoard().size(); i++)
				{
					str.append("\n\t" + this.gameClone.getGameBoard().getRegionBoard().get(i).getName());
					str.append(" Balcony: " + this.gameClone.getGameBoard().getRegionBoard().get(i).getBalcony().toString());
				}
				break;
			}
			case SHOW_BALCONIES:
			{
				for(int i = 0; i < this.gameClone.getGameBoard().getRegionBoard().size(); i++)
				{
					str.append("\n\t" + "[" + this.gameClone.getGameBoard().getRegionBoard().get(i).getName() + "] ->");
					str.append(" " + this.gameClone.getGameBoard().getRegionBoard().get(i).getName());
					str.append(" Balcony: " + this.gameClone.getGameBoard().getRegionBoard().get(i).getBalcony().toString());
				}
				str.append("\n\t" + "[" + "KING" + "] ->" );
				str.append(" " + "KING");
				str.append(" Balcony: " + this.gameClone.getGameBoard().getKingBoard().getBalcony().toString());
				break;
			}
			case SHOW_KING_BALCONY:
			{
				str.append("KING");
				str.append(" Balcony: " + this.gameClone.getGameBoard().getKingBoard().getBalcony().toString());
				break;
			}
			case SHOW_REGIONS_BUSINESSPERMITCARDS:
			{
				for(int i = 0; i < this.gameClone.getGameBoard().getRegionBoard().size(); i++)
				{
					str.append("\n\t" + this.gameClone.getGameBoard().getRegionBoard().get(i).getName());
					str.append("\n\tBusiness Permit Cards: ");
					if( this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne() != null )
					{
						str.append("\n" + this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne().toString());
					}
					if( this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo() != null )
					{
						str.append("\n" + this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo().toString());
					}
					if(this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo() == null
							&& this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne() == null)
						str.append("\n\t" + "No Business Permit Cards.");
				}
				break;
			}
			case SHOW_REGIONS_BUSINESSPERMITCARDS2:
			{
				for(int i = 0; i < this.gameClone.getGameBoard().getRegionBoard().size(); i++)
				{
					str.append("\n\t" + "[" + this.gameClone.getGameBoard().getRegionBoard().get(i).getName() + "]");
					str.append("\n\tBusiness Permit Cards: ");
					if( this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne() != null )
					{
						str.append("\n" + this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne().toString());
					}
					if( this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo() != null )
					{
						str.append("\n" + this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo().toString());
					}
					if(this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo() == null
							&& this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne() == null)
						str.append("\n\t" + "No Business Permit Cards.");
				}
				break;
			}
			case SHOW_ALL_REGIONS_BUSINESSPERMITCARDS:
			{
				for(int i = 0; i < this.gameClone.getGameBoard().getRegionBoard().size(); i++)
				{
					str.append("\n\t" + this.gameClone.getGameBoard().getRegionBoard().get(i).getName());
					str.append("\n\tBusiness Permit Cards: ");
					if( this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne() != null )
					{
						str.append("\n\t" + "[" + ((i*2)+1) + "] ->");
						str.append(this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne().toString());
					}
					if( this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo() != null )
					{
						str.append("\n\t" + "[" + ((i*2)+2) + "] ->");
						str.append(this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo().toString());
					}
					if(this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileTwo() == null
							&& this.gameClone.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne() == null)
						str.append("\n\t" + "No Business Permit Cards.");
				}
				break;
			}
			case SHOW_REQUEST_SATISFY_COUNCIL_FOR_BUSINESS:
			{
				str.append("You need to satisfy a Council to acquire the business permit card, using your politc cards.");
				break;
			}
			case SHOW_REQUEST_SATISFY_KING_COUNCIL:
			{
				str.append("\nYou need to satisfy King Council to ask his help.");
				break;
			}
			case SHOW_ALL_BUSINESSPERMITCARDS_FACEUP:
			{
				str.append("\nYour Business Permit Cards: ");
				if( this.gameClone.getCurrentPlayer().getBusinessPermitCards().isEmpty() )
				{
					str.append("no business permit cards");
				}
				else
				{
					for(int i = 0; i < this.gameClone.getCurrentPlayer().getBusinessPermitCards().size(); i++)
					{
						str.append("\n\t" + "[" + ( i + 1 ) + "]" + " ->");
						str.append(this.gameClone.getCurrentPlayer().getBusinessPermitCards().get(i));
						str.append("\n\t\tCities of this permit:");
						for( int j = 0; j < this.gameClone.getCurrentPlayer().getBusinessPermitCards().get(i).getCities().size(); j++ )
						{
							str.append("\n\n\t" + this.gameClone.getCurrentPlayer().getBusinessPermitCards().get(i).getCities().get(j).toString());
						}
					}
				}
				break;
			}
			case SHOW_ALL_PLAYER_BUSINESSPERMITCARDS:
			{
				str.append("\n\tYour Business Permit Cards: ");
				if( this.gameClone.getCurrentPlayer().getBusinessPermitCards().isEmpty() )
				{
					str.append("no business permit cards");
				}
				else
				{
					for(int i = 0; i < ( this.gameClone.getCurrentPlayer().getBusinessPermitCards().size() + this.gameClone.getCurrentPlayer().getBusinessPermitCardsFaceDown().size() ); i++)
					{
						str.append("\n\t" + "[" + ( i + 1 ) + "]" + " ->");
						if( i < this.gameClone.getCurrentPlayer().getBusinessPermitCards().size() )
						{
							str.append(this.gameClone.getCurrentPlayer().getBusinessPermitCards().get(i).toString());
						}
						else
						{
							str.append(this.gameClone.getCurrentPlayer().getBusinessPermitCardsFaceDown().get(i - this.gameClone.getCurrentPlayer().getBusinessPermitCards().size()).toString());
						}
					}
				}
				break;
			}
			case SHOW_ALL_CITIES:
			{
				str.append("\n\tCities: ");
				for(RegionBoard regionBoard : this.gameClone.getGameBoard().getRegionBoard())
				{
					for(City city : regionBoard.getCities())
					{
						str.append("\n\t" + "[" + city.getName() + "]" + " ->");
						str.append(city.toString());
					}
				}
				break;
			}
			case SHOW_CITIES_WHERE_BUILT_CURRENT_PLAYER:
			{
				str.append("\n\tCities: ");
				
				boolean haveNobilityBonus = false;
				for(RegionBoard regionBoard : this.gameClone.getGameBoard().getRegionBoard())
				{
					for(City city : regionBoard.getCities())
					{
						if(city.getEmporiums()[this.getCurrentPlayerID()] != null)
						{
							for(int i =0; i< city.getBonus().size() && !haveNobilityBonus;i++)
							{	
								if(city.getBonus().get(i) instanceof MoveMarkerNobilityTrackBonus)
									haveNobilityBonus = true;									
							}
							if(haveNobilityBonus)
							{
								haveNobilityBonus = false;
							}
							else
							{
								str.append("\n\t" + "[" + city.getName() + "]" + " ->");
								str.append(city.toString());
							}
						}
					}
				}
				if( "\n\tCities: ".equals(str.toString()) )
				{
					str.append("you haven't build in any city");
				}
				
			}
			case SHOW_FINISH_CARDS:
			{
				str.append("\n\tThe deck is empty");
				break;
			}
			case SHOW_PLAYER_EMPORIUMS:
			{
				str.append("\tYou still have " + this.gameClone.getCurrentPlayer().getEmporiumStack().size() + " emporiums to build");
				break;
			}
			case SHOW_LAST_LAP:
			{
				if( this.checkNotEnoughPlayers() )
				{
					str.append("\n\tThe players not suspended are less than 2");
					str.append("\n\n\t-----LAST LAP FOR PLAYERS NOT SUSPENDED-----");
				}
				else
				{
					str.append("\n\tThe player:");
					str.append("\n\t\tID: " + (this.gameClone.getCurrentPlayer().getPlayerID()+1));
					str.append("\n\t\tColor: " + this.gameClone.getCurrentPlayer().getColorString());
					str.append("\n\tHas built its latest emporium.");
					str.append("\n\n\t-----LAST LAP FOR PLAYERS NOT SUSPENDED-----");
				}
				break;
			}
			case SHOW_RANKING:
			{
				str.append("\n\tRanking:");
				
				int position = 1;
				List<List<List<List<Player>>>> playersGroupByVictory = this.gameClone.getRank();
				for( int i = 0; i < playersGroupByVictory.size(); i++ )
				{
					List<List<List<Player>>> playersGroupByAssistants = playersGroupByVictory.get(i);
					for( int j = 0; j < playersGroupByAssistants.size(); j++ )
					{
						List<List<Player>> playersGroupByPoliticCards = playersGroupByAssistants.get(j);
						for( int k = 0; k < playersGroupByPoliticCards.size(); k++ )
						{
							str.append("\n\n\t---Players at " + position + "° position:---");
							List<Player> playersAtSamePosition = playersGroupByPoliticCards.get(k);
							for( Player player : playersAtSamePosition )
							{
								str.append("\n\n\t\tID: " + (player.getPlayerID() + 1));
								str.append("\n\t\tColor: " + player.getColorString());
								str.append("\n\t\tVictory points: " + player.getVictoryMarkerDisc().getPosition());
								str.append("\n\t\tAssistants: " + player.getAssistants().getAssistants());
								str.append("\n\t\tPolitic cards: " + player.getPoliticCardsList().size());
							}
							position++;
						}
					}					
				}
				break;
			}
			case SHOW_MARKET_OBJECTS:
			{
				str.append("\n\tOn sale objects:");
				if( this.gameClone.getMarketObjects().isEmpty() )
				{
					str.append("\n\t\tNo objects");
				}
				else
				{
					int numberOfObject = 0;
					for( int i = 0; i < this.gameClone.getMarketObjects().size(); i++ )
					{
						if( this.gameClone.getMarketObjects().get(i).getSeller() != this.gameClone.getCurrentPlayer() )
						{
							numberOfObject++;
							str.append("\n\t[" + numberOfObject + "] ->" + this.gameClone.getMarketObjects().get(i).toString());
						}
					}
				}
				str.append("\n");
				break;
			}
			case SHOW_KING_POSITION:
			{
				str.append("\tKing City: \n");
				str.append(this.gameClone.getGameBoard().getKing().toString());
				break;
			}
			default:
				break;
		}
	}
	
	/**
	 * Check not enough players.
	 *
	 * @return true, if successful
	 */
	private boolean checkNotEnoughPlayers()
	{
		int playersNotSuspended = 0;
		for( Player player : this.gameClone.getPlayers() )
		{
			if( !player.isSuspended() )
				playersNotSuspended++;
		}
		if( playersNotSuspended < 2 )
		{
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessage#getCurrentPlayerID()
	 */
	@Override
	public int getCurrentPlayerID()
	{
		return this.gameClone.getCurrentPlayer().getPlayerID();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return str.toString();
	}
}
