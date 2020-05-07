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
package it.polimi.ingsw.ps16.server.controller.gamemachines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.player.Player;
import it.polimi.ingsw.ps16.server.model.turn.marketturn.BuyState;
import it.polimi.ingsw.ps16.server.model.turn.marketturn.SaleState;
import it.polimi.ingsw.ps16.server.view.ConnectionView;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class MarketMachine is responsible for the market phase of the game, and therefore<br>
 * the alternation between BuyState and SaleState for all Players.
 */
public class MarketMachine 
{
	
	/** The game. */
	private Game game;
	
	/** The views. */
	private List<ConnectionView> views;
	
	/** The players. */
	private List<Player> players;
	
	/** The standard output. */
	private static OutputStream stdOut;
	
	/**
	 * Instantiates a new market machine.
	 *
	 * @param views
	 *            the connection to online players
	 * @param game
	 *            the game
	 * @param players
	 *            the online players
	 */
	public MarketMachine(List<ConnectionView> views,Game game,List<Player> players)
	{
		this.game = game;
		this.views = views;
		this.players = players;
		stdOut = new OutputStream();
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Gets the views.
	 *
	 * @return the views
	 */
	public List<ConnectionView> getViews() {
		return views;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Removes the player.
	 *
	 * @param index
	 *            the index
	 */
	public void removePlayer(int index)
	{
		this.players.remove(index);
	}
	
	/**
	 * Give back objects.<br>
	 * After a market phase can happen that not all objects are sold out,<br>
	 * this method returns these ones to the owners if none bought it.<br>
	 */
	private void giveBackObjects()
	{
		for( int i = 0; i < this.getGame().getMarketObjects().size(); i++ )
		{
			if( this.game.getMarketObjects().get(i).getForSaleObject() instanceof BusinessPermitCard )
			{
				 this.game.getMarketObjects().get(i).getSeller().addBusinessPermitCard((BusinessPermitCard)this.game.getMarketObjects().get(i).getForSaleObject());
			}
			else if( this.game.getMarketObjects().get(i).getForSaleObject() instanceof PoliticCard )
			{
				 this.game.getMarketObjects().get(i).getSeller().addPoliticCard((PoliticCard)this.game.getMarketObjects().get(i).getForSaleObject());
			}
		}
	}
	

	/**
	 * The principal method.
	 * Rotate player's market turns in BuyState/SaleState. 
	 */
	public void run()
	{		
		for(int i=0;i<players.size();i++)
		{			
			if(!this.players.get(i).isSuspended())
			{
				this.game.setCurrentPlayer(this.getPlayers().get(i));
				this.game.setCurrentState(new SaleState(this.getGame(),this.getViews().get(i)));
				
				this.game.getCurrentState().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_GAMEBOARD, this.game));
				
				this.executeState(this.getPlayers().get(i));
			}
		}
		
		List<Integer> randomIndex = new ArrayList<>();
		
		for(int i=0; i< players.size();i++)
		{
			if(!this.players.get(i).isSuspended())
			{
				randomIndex.add(i);
			}
		}
		
		Collections.shuffle(randomIndex);
	
		for(int i=0; i < randomIndex.size();i++)
		{					
			this.game.setCurrentPlayer(this.players.get(randomIndex.get(i)));
			this.game.setCurrentState(new BuyState(this.getGame(),this.getViews().get(randomIndex.get(i))));
										
			this.executeState(this.getPlayers().get(i));
		}
		
		//Restituisco gli oggetti non comprati ai giocatori
		this.giveBackObjects();
		this.getGame().setMarketObjects(new ArrayList<>());
	}
	
	/**
	 * Execute current state of the game.<br>
	 * It can be BuyState or SaleState.
	 *
	 * @param player
	 *            the player who is playing
	 */
	private void executeState(Player player)
	{
		try
		{
			this.game.getCurrentState().executeMarketState(this.getGame().getMarketObjects());
		}
		catch(SuspendPlayerException e)
		{
			stdOut.getStdOut().println("Player " + (player.getPlayerID() + 1) + " timeout expired.\n");
			this.game.getCurrentState().notifyObservers(new InfoMessageType( "Your timeout expired", this.game ));
			this.game.notifyObservers(new BroadcastMessage("Player " + (player.getPlayerID() + 1) + " timeout expired", this.game));
			
			this.checkNotEnoughPlayers();
		}
	}
	
	/**
	 * Check that players online are more than 1.<br>
	 * To use after suspended player to check whether to continue or bring the game to the end.
	 */
	private void checkNotEnoughPlayers()
	{
		int playersNotSuspended = 0;
		for( Player player : this.game.getPlayers() )
		{
			if( !player.isSuspended() )
				playersNotSuspended++;
		}
		if( playersNotSuspended < 2 )
		{
			this.game.setFinishGame(true);
		}
	}
}
