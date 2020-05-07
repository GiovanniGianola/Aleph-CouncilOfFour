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
package it.polimi.ingsw.ps16.server.controller;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class GameFinisher is responsible for the execution of the final things in the game:<br><br>
 * 		- Assign victory points in the bonus tiles of all players.<br>
 * 		- Assign victory points to the player with more nobility points.<br>
 * 		- Assign victory points to the player with more business permit cards.<br>
 * 		- Stipulate the rank.
 */
public class GameFinisher 
{
	
	/** The Constant VICTORY_POINTS_FOR_FIRST_NOBILITY_PLACE. */
	private static final int VICTORY_POINTS_FOR_FIRST_NOBILITY_PLACE = 5; 
	
	/** The Constant VICTORY_POINTS_FOR_SECOND_NOBILITY_PLACE. */
	private static final int VICTORY_POINTS_FOR_SECOND_NOBILITY_PLACE = 2; 
	
	/** The Constant VICTORY_POINTS_FOR_BUSINESS_PERMIT_CARDS. */
	private static final int VICTORY_POINTS_FOR_BUSINESS_PERMIT_CARDS = 3; 
	
	/** The game. */
	Game game;
	
	
	
	/**
	 * Instantiates a new game finisher.
	 *
	 * @param game
	 *            the game
	 */
	public GameFinisher(Game game)
	{
		this.game = game;
	}
	
	
	
	/**
	 * Do final things.<br>
	 * It consist in:<br><br>
	 * - Assign victory points in the bonus tiles of all players.<br>
	 * - Assign victory points to the player with more nobility points.<br>
	 * - Assign victory points to the player with more business permit cards.<br>
	 * - Stipulate the rank.<br>
	 *
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public void doFinalThings()  throws SuspendPlayerException
	{
		this.bonusTilesVictoryPoints();
		this.nobilityVictoryPoints();
		this.businessPermitCardsVictoryPoints();
		this.game.setRank(this.makeRanking());
	}
	
	/**
	 * Assign victory points in the bonus tiles of all players.
	 *
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	//Applico le bonus tiles pescate nel corso della partita ai giocatori
	private void bonusTilesVictoryPoints() throws SuspendPlayerException
	{
		for( Player player : this.game.getPlayers() )
		{
			this.game.setCurrentPlayer(player);
			for( BonusTile bonusTile : player.getBonusTiles() )
			{
				bonusTile.getBonus().executeBonus(game);
			}
		}
	}
	
	/**
	 * Assign victory points to the player with more nobility points.
	 */
	//Applico i punti vittoria in base alla classifica sul percorso nobiltà
	private void nobilityVictoryPoints()
	{
		List<Player> playersOrderedByNobility = new ArrayList<>();
		for( Player player : this.game.getPlayers() )
		{
			playersOrderedByNobility.add(player);
		}
		
		//Ordinamento giocatori in base alla posizione nel percorso nobiltà
		for( int i = 1; i < playersOrderedByNobility.size(); i++ )
		{
			Player temp1 = playersOrderedByNobility.get(i);
			Player temp2;
			for( int j = 0; j < i; j++ )
			{
				if( temp1.getNobilityMarkerDisc().getPosition()
						> playersOrderedByNobility.get(j).getNobilityMarkerDisc().getPosition() )
				{
					temp2 = playersOrderedByNobility.get(j);
					playersOrderedByNobility.set(j, temp1);
					temp1 = temp2;
				}
			}
			playersOrderedByNobility.set(i, temp1);
		}
		//Assegno i punti vittoria al primo in classifica
		playersOrderedByNobility.get(0).getVictoryMarkerDisc().moveForward(VICTORY_POINTS_FOR_FIRST_NOBILITY_PLACE);
		//Assegno i punti vittoria agli altri primi in classifica
		boolean moreFirstPlace = false;
		for( int i = 1; i < playersOrderedByNobility.size(); i++ )
		{
			if( playersOrderedByNobility.get(i).getNobilityMarkerDisc().getPosition() 
					== playersOrderedByNobility.get(0).getVictoryMarkerDisc().getPosition() )
			{
				playersOrderedByNobility.get(i).getVictoryMarkerDisc().moveForward(VICTORY_POINTS_FOR_FIRST_NOBILITY_PLACE);
				moreFirstPlace = true;
			}
		}
		if( !moreFirstPlace )
		{
			//Assegno i punti vittoria al secondo in classifica
			playersOrderedByNobility.get(1).getVictoryMarkerDisc().moveForward(VICTORY_POINTS_FOR_SECOND_NOBILITY_PLACE);
			if( playersOrderedByNobility.size() > 2 )
			{
				for( int i = 2; i < playersOrderedByNobility.size(); i++ )
				{
					if( playersOrderedByNobility.get(i).getNobilityMarkerDisc().getPosition() 
							== playersOrderedByNobility.get(1).getVictoryMarkerDisc().getPosition() )
					{
						playersOrderedByNobility.get(i).getVictoryMarkerDisc().moveForward(VICTORY_POINTS_FOR_SECOND_NOBILITY_PLACE);
					}
				}
			}
		}
	}
	
	/**
	 * Assign victory points to the player with more business permit cards.
	 */
	//Aggiungo i relativi punti vittoria al giocatore con più tessere permesso costruzione
	private void businessPermitCardsVictoryPoints()
	{
		List<Player> playersOrderedByNumberOfBusinessPermitCards = new ArrayList<>();
		for( Player player : this.game.getPlayers() )
		{
			playersOrderedByNumberOfBusinessPermitCards.add(player);
		}
		
		//Ordinamento giocatori in base alle tessere permesso costruzione possedute
		for( int i = 1; i < playersOrderedByNumberOfBusinessPermitCards.size(); i++ )
		{
			Player temp1 = playersOrderedByNumberOfBusinessPermitCards.get(i);
			Player temp2;
			for( int j = 0; j < i; j++ )
			{
				if( temp1.getBusinessPermitCards().size() + temp1.getBusinessPermitCardsFaceDown().size()
						> playersOrderedByNumberOfBusinessPermitCards.get(j).getBusinessPermitCards().size()
						+ playersOrderedByNumberOfBusinessPermitCards.get(j).getBusinessPermitCardsFaceDown().size() )
				{
					temp2 = playersOrderedByNumberOfBusinessPermitCards.get(j);
					playersOrderedByNumberOfBusinessPermitCards.set(j, temp1);
					temp1 = temp2;
				}
			}
			playersOrderedByNumberOfBusinessPermitCards.set(i, temp1);
		}
		//Assegno i punti vittoria ai primi in classifica a pari numero di business permit cards
		playersOrderedByNumberOfBusinessPermitCards.get(0).getVictoryMarkerDisc().moveForward(VICTORY_POINTS_FOR_BUSINESS_PERMIT_CARDS);
		boolean check = true;
		for(int i = 0; i < playersOrderedByNumberOfBusinessPermitCards.size() - 1 && check; i++)
		{
			if( playersOrderedByNumberOfBusinessPermitCards.get(i).getBusinessPermitCards().size() + playersOrderedByNumberOfBusinessPermitCards.get(i).getBusinessPermitCardsFaceDown().size() == 
					playersOrderedByNumberOfBusinessPermitCards.get(i + 1).getBusinessPermitCards().size() + playersOrderedByNumberOfBusinessPermitCards.get(i + 1).getBusinessPermitCardsFaceDown().size())
				playersOrderedByNumberOfBusinessPermitCards.get(i + 1).getVictoryMarkerDisc().moveForward(VICTORY_POINTS_FOR_BUSINESS_PERMIT_CARDS);
			else
				check = false;
		}
		
		//Assegno i punti vittoria agli altri primi in classifica
		for( int i = 1; i < playersOrderedByNumberOfBusinessPermitCards.size(); i++ )
		{
			if( playersOrderedByNumberOfBusinessPermitCards.get(i).getBusinessPermitCards().size() 
					+ playersOrderedByNumberOfBusinessPermitCards.get(i).getBusinessPermitCardsFaceDown().size()
					== playersOrderedByNumberOfBusinessPermitCards.get(0).getBusinessPermitCards().size()
					+ playersOrderedByNumberOfBusinessPermitCards.get(0).getBusinessPermitCardsFaceDown().size() )
			{
				playersOrderedByNumberOfBusinessPermitCards.get(i).getVictoryMarkerDisc().moveForward(VICTORY_POINTS_FOR_BUSINESS_PERMIT_CARDS);
			}
		}
	}
	
	
	/**
	 * Make ranking.
	 *
	 * @return the rank where a single list represent:<br>
	 * 			- Players ordered by victory points.<br>
	 * 			- Players ordered by number of assistants.<br>
	 * 			- Players ordered by number of politic cards.<br>
	 * 			
	 */
	//Stipulo la classifica secondo le regole del gioco
	private List<List<List<List<Player>>>> makeRanking()
	{
		List<Player> players = new ArrayList<>();
		for( Player player : this.game.getPlayers() )
		{
			players.add(player);
		}
		
		//Ordinamento giocatori in base alla posizione nel percorso vittoria
		List<Player> playersOrderedByVictory = this.orderByVictory(players);
		
		//Metto in delle liste i giocatori con ugual numero di punti vittoria
		List<List<Player>> playersGroupByVictory = this.groupByVictory(playersOrderedByVictory);
		
		//Ordinamento gruppi in base al numero di assistenti
		List<List<Player>> playersGroupsOrderedByAssistants = this.orderByAssistants(playersGroupByVictory);
		
		//Metto in delle liste i giocatori con ugual numero di punti vittoria e assistenti
		List<List<List<Player>>> playersGroupByAssistants = this.groupByAssistants(playersGroupsOrderedByAssistants);
		
		//Ordinamento gruppi in base al numero di carte politiche
		List<List<List<Player>>> playersGroupsOrderedByPoliticCards = this.orderByPoliticCards(playersGroupByAssistants);
		
		//Metto in delle liste i giocatori con ugual numero di punti vittoria, assistenti e carte politiche
		List<List<List<List<Player>>>> playersGroupByPoliticCards = this.groupByPoliticCards(playersGroupsOrderedByPoliticCards);

		return playersGroupByPoliticCards;
	}
	
	/**
	 * Order by victory.
	 *
	 * @param players
	 *            the players
	 * @return the players ordered
	 */
	//Ordinamento giocatori in base alla posizione nel percorso vittoria
	private List<Player> orderByVictory(List<Player> players)
	{
		for( int i = 0; i < players.size(); i++ )
		{
			Player temp1 = players.get(i);
			Player temp2;
			for( int j = 0; j < i; j++ )
			{
				if( temp1.getVictoryMarkerDisc().getPosition()
						> players.get(j).getVictoryMarkerDisc().getPosition() )
				{
					temp2 = players.get(j);
					players.set(j, temp1);
					temp1 = temp2;
				}
			}
			players.set(i, temp1);
		}
		return players;
	}
	
	/**
	 * Group by victory.
	 *
	 * @param playersOrderedByVictory
	 *            the players ordered by victory
	 * @return the players grouped
	 */
	//Metto in delle liste i giocatori con ugual numero di punti vittoria
	private List<List<Player>> groupByVictory(List<Player> playersOrderedByVictory)
	{
		List<List<Player>> playersGroupByVictory = new ArrayList<>();
		playersGroupByVictory.add(new ArrayList<>());
		
		int currentVictoryPosition = playersOrderedByVictory.get(0).getVictoryMarkerDisc().getPosition();
		int currentVictoryGroup = 0;
		for( int i = 0; i < playersOrderedByVictory.size(); i++ )
		{
			if( playersOrderedByVictory.get(i).getVictoryMarkerDisc().getPosition() < currentVictoryPosition )
			{
				playersGroupByVictory.add(new ArrayList<>());
				currentVictoryPosition = playersOrderedByVictory.get(i).getVictoryMarkerDisc().getPosition();
				currentVictoryGroup++;
			}
			playersGroupByVictory.get(currentVictoryGroup).add(playersOrderedByVictory.get(i));
		}
		return playersGroupByVictory;
	}
	
	/**
	 * Order by assistants.
	 *
	 * @param playersGroupByVictory
	 *            the players group by victory
	 * @return the players ordered
	 */
	//Ordinamento gruppi in base al numero di assistenti
	private List<List<Player>> orderByAssistants(List<List<Player>> playersGroupByVictory)
	{
		for( int i = 0; i < playersGroupByVictory.size(); i++)
		{
			for( int j = 0; j < playersGroupByVictory.get(i).size(); j++ )
			{
				Player temp1 = playersGroupByVictory.get(i).get(j);
				Player temp2;
				for( int k = 0; k < j; k++ )
				{
					if( temp1.getAssistants().getAssistants()
							> playersGroupByVictory.get(i).get(k).getAssistants().getAssistants() )
					{
						temp2 = playersGroupByVictory.get(i).get(k);
						playersGroupByVictory.get(i).set(k, temp1);
						temp1 = temp2;
					}
				}
				playersGroupByVictory.get(i).set(j, temp1);
			}
		}
		return playersGroupByVictory;
	}
	
	/**
	 * Group by assistants.
	 *
	 * @param playersGroupsOrderedByAssistants
	 *            the players groups ordered by assistants
	 * @return the players grouped
	 */
	//Metto in delle liste i giocatori con ugual numero di punti vittoria e assistenti
	private List<List<List<Player>>> groupByAssistants(List<List<Player>> playersGroupsOrderedByAssistants)
	{
				List<List<List<Player>>> playersGroupByAssistants = new ArrayList<>();
				int currentAssistants;
				int currentAssistantsGroup;

				for( int i = 0; i < playersGroupsOrderedByAssistants.size(); i++ )
				{
					playersGroupByAssistants.add(new ArrayList<>());
					playersGroupByAssistants.get(i).add(new ArrayList<>());
					
					currentAssistants = playersGroupsOrderedByAssistants.get(i).get(0).getAssistants().getAssistants();
					currentAssistantsGroup = 0;
					for( int j = 0; j < playersGroupsOrderedByAssistants.get(i).size(); j++ )
					{
						if( playersGroupsOrderedByAssistants.get(i).get(j).getAssistants().getAssistants() < currentAssistants )
						{
							playersGroupByAssistants.get(i).add(new ArrayList<>());
							currentAssistants = playersGroupsOrderedByAssistants.get(i).get(j).getAssistants().getAssistants();
							currentAssistantsGroup++;
						}
						playersGroupByAssistants.get(i).get(currentAssistantsGroup).add(playersGroupsOrderedByAssistants.get(i).get(j));
					}
				}
				return playersGroupByAssistants;
	}
	
	/**
	 * Order by politic cards.
	 *
	 * @param playersGroupByAssistants
	 *            the players group by assistants
	 * @return the players ordered
	 */
	//Ordinamento gruppi in base al numero di carte politiche
	private List<List<List<Player>>> orderByPoliticCards(List<List<List<Player>>> playersGroupByAssistants)
	{
				for(int i = 0; i < playersGroupByAssistants.size(); i++)
				{
					for( int j = 0; j < playersGroupByAssistants.get(i).size(); j++)
					{
						for( int k = 0; k < playersGroupByAssistants.get(i).get(j).size(); k++ )
						{
							Player temp1 = playersGroupByAssistants.get(i).get(j).get(k);
							Player temp2;
							for( int w = 0; w < k; w++ )
							{
								if( temp1.getPoliticCardsList().size()
										> playersGroupByAssistants.get(i).get(j).get(w).getPoliticCardsList().size() )
								{
									temp2 = playersGroupByAssistants.get(i).get(j).get(w);
									playersGroupByAssistants.get(i).get(j).set(w, temp1);
									temp1 = temp2;
								}
							}
							playersGroupByAssistants.get(i).get(j).set(k, temp1);
						}
					}
				}
				return playersGroupByAssistants;
	}
	
	/**
	 * Group by politic cards.
	 *
	 * @param playersGroupsOrderedByPoliticCards
	 *            the players groups ordered by politic cards
	 * @return the players grouped
	 */
	//Metto in delle liste i giocatori con ugual numero di punti vittoria, assistenti e carte politiche
	private List<List<List<List<Player>>>> groupByPoliticCards(List<List<List<Player>>> playersGroupsOrderedByPoliticCards)
	{
		List<List<List<List<Player>>>> playersGroupByPoliticCards = new ArrayList<>();
		int currentPoliticCards;
		int currentPoliticCardsGroup;
		
		for( int i = 0; i < playersGroupsOrderedByPoliticCards.size(); i++)
		{
			playersGroupByPoliticCards.add(new ArrayList<>());
			for( int j = 0; j < playersGroupsOrderedByPoliticCards.get(i).size(); j++ )
			{
				playersGroupByPoliticCards.get(i).add(new ArrayList<>());
				playersGroupByPoliticCards.get(i).get(j).add(new ArrayList<>());
				
				currentPoliticCards = playersGroupsOrderedByPoliticCards.get(i).get(j).get(0).getPoliticCardsList().size();
				currentPoliticCardsGroup = 0;
				for( int k = 0; k < playersGroupsOrderedByPoliticCards.get(i).get(j).size(); k++ )
				{
					if( playersGroupsOrderedByPoliticCards.get(i).get(j).get(k).getPoliticCardsList().size() < currentPoliticCards )
					{
						playersGroupByPoliticCards.get(i).get(j).add(new ArrayList<>());
						currentPoliticCards = playersGroupsOrderedByPoliticCards.get(i).get(j).get(k).getPoliticCardsList().size();
						currentPoliticCardsGroup++;
					}
					playersGroupByPoliticCards.get(i).get(j).get(currentPoliticCardsGroup).add(playersGroupsOrderedByPoliticCards.get(i).get(j).get(k));
				}
			}
		}
		return playersGroupByPoliticCards;
	}
}
