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
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.server.controller.gamemachines.MarketMachine;
import it.polimi.ingsw.ps16.server.controller.gamemachines.TurnMachine;
import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.message.FinishMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.chatmessage.ChatMessage;
import it.polimi.ingsw.ps16.server.model.message.finishmessage.FinishMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.replymessage.ReplyMessage;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateMap;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdatePlayerMessage;
import it.polimi.ingsw.ps16.server.model.player.Player;
import it.polimi.ingsw.ps16.server.model.turn.gameturn.FinishState;
import it.polimi.ingsw.ps16.server.view.ConnectionView;
import it.polimi.ingsw.ps16.server.view.singleton.GamesParameters;
import it.polimi.ingsw.ps16.utils.ColorCodes;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class GameController represent a single game in progress.
 * It's responsible for the rotation of turnMachines
 * and therefore for the flow of the game
 */
public class GameController implements Runnable, Observer
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( GameController.class.getName() );
	
	/** The Constant CARDS_TO_DRAW_INITIALLY. */
	private static final int CARDS_TO_DRAW_INITIALLY = 6; 
	
	/** The Constant VICTORY_POINTS_FOR_FIRST_ENDED_EMPORIUMS. */
	private static final int VICTORY_POINTS_FOR_FIRST_ENDED_EMPORIUMS = 3; 
	
	/** The map number. */
	private String mapNumber;
	
	/** The king name. */
	private String kingName;
	
	/** The views. */
	private List<ConnectionView> views;
	
	/** The players. */
	private List<Player> players;
	
	/** The turn machines. */
	private List<TurnMachine> turnMachines;
	
	/** The market machine. */
	private MarketMachine marketMachine;
	
	/** The game. */
	private Game game;
	
	/** The n game. */
	private int nGame;
	
	/** The std out. */
	private static OutputStream stdOut;	
	
	/**
	 * Instantiates a new game controller.
	 *
	 * @param views
	 *            the connections to online players
	 * @param players
	 *            the online players
	 * @param nGame
	 *            the n° of this game
	 */
	public GameController(List<ConnectionView> views, List<Player> players, int nGame) 
	{	
		this.views = views;
		this.players = players;	
		this.nGame = nGame;
		stdOut = new OutputStream();
	}
		
	/**
	 * Gets the map number.
	 *
	 * @return the map number of this game
	 */
	public String getMapNumber() 
	{
		return mapNumber;
	}
	
	/**
	 * Gets the king name.
	 *
	 * @return the king name of this game
	 */
	public String getKingName() 
	{
		return kingName;
	}
	
	/**
	 * Gets the views.
	 *
	 * @return the connections to online players
	 */
	public List<ConnectionView> getViews() 
	{
		return views;
	}
	
	/**
	 * Gets the players.
	 *
	 * @return the online players
	 */
	public List<Player> getPlayers() 
	{
		return players;
	}
	
	/**
	 * Gets the game.
	 *
	 * @return the object that represent the state of game
	 */
	public Game getGame() 
	{
		return game;
	}
	
	/**
	 * Check that all parameters of players connected are set.
	 *
	 * @param players
	 *            the online players
	 * @return true, if all parameters of players connected are set
	 */
	private boolean allPlayersSetted(List<Player> players)
    {
    	boolean allPlayersSetted = true;
    	for( int i = 0; i < players.size() && allPlayersSetted; i++ )
    	{
    		if( players.get(i) != null && (players.get(i).getName() == null || players.get(i).getColor() == null) )
    		{
    			allPlayersSetted = false;
    		}
    	}
    	return allPlayersSetted;
    }
	
	/**
	 * Do initial thing as draw cards
	 * After this method the game in ready to start.
	 */
	private void initialize()
	{
		while(!allPlayersSetted(this.getPlayers()));
		
		if(GamesParameters.getGameParametersInstance().getMapNumber(nGame).length() == 0)
			this.mapNumber = "1";
		else
			this.mapNumber = GamesParameters.getGameParametersInstance().getMapNumber(nGame);
		
		this.kingName = GamesParameters.getGameParametersInstance().getKingName(nGame); 
		this.game = new Game(this.getPlayers(), this.mapNumber, this.kingName);
		stdOut.getStdOut().println(printGame());
		
		GamesParameters.reset(this.nGame);

		for(ConnectionView view : views)
		{
			this.game.addObserver(view);
		}
		//Faccio pescare le carte iniziali ai giocatori
		this.playersDrawInitialCards();
		
		this.turnMachines = new ArrayList<>();
		for( int i = 0; i < views.size(); i++ )
			this.turnMachines.add(new TurnMachine(views.get(i), game, players.get(i)));
		
        this.marketMachine = new MarketMachine(this.views, this.getGame(), this.getPlayers());
		
		//Faccio stampare ai giocatori lo stato iniziale della partita
		this.game.notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_INITIAL_MESSAGE, this.game));
		this.game.notifyObservers(new UpdateMap(this.game));
	}
	
	/**
	 * Players draw initial cards.
	 */
	private void playersDrawInitialCards()
	{
		PoliticCard cardDrawed;
		for( Player player : this.game.getPlayers() )
		{
			for( int i = 0 ; i < GameController.CARDS_TO_DRAW_INITIALLY ; i++ )
			{
				if ( !(this.getGame().getGameBoard().getPoliticCardsDeck().isEmpty()) )
				{
					cardDrawed = this.getGame().getGameBoard().drawPoliticCard();
					player.addPoliticCard(cardDrawed);
				}
			}
		}
	}
    
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() 
	{
		this.initialize();
		
		//Si rimane in questo while fino alla fine del gioco
		int finalPlayerIndex = 0;
		while( !this.game.isFinishGame() )
		{
			for( int i = 0; i < this.turnMachines.size() && !this.game.isFinishGame(); i++ )
			{
				if(!this.players.get(i).isSuspended())
				{
					this.turnMachines.get(i).run();
					finalPlayerIndex = i;
				}
			}
			if( !this.game.isFinishGame() )
			{
				this.marketMachine.run();
			}
		}
		
		this.goToFinish(finalPlayerIndex);
	}
	
	/**
	 * Prints the actual situation of game.
	 *
	 * @return the string that represent the actual situation of game
	 */
	private String printGame()
	{
		String space = "|\t\t\t\t\t|";
		String end = "\t\t\t|\n";
		String begin = "\n|\t\t";
		
		StringBuilder str = new StringBuilder(ColorCodes.parseColors(":red,n:***** NEW GAME SUCCESSFULLY CREATED *****[RC]\n"));
		str.append(space);
		str.append("\n|\tGame Number: " + this.nGame + end);
		str.append(space);
		str.append("\n|\tPlayers Number: " + this.players.size() + "\t\t|\n");
		str.append(space);
		str.append("\n|\tMap Number: " + GamesParameters.getGameParametersInstance().getMapNumber(this.nGame) + end);
		str.append(space);
		str.append("\n|\tKing Name: " + GamesParameters.getGameParametersInstance().getKingName(this.nGame) + end);
		str.append(space);
		
		str.append("\n|\tPlayers Name: \t\t\t|");
		for(int i = 0; i < this.players.size(); i++)
			str.append(begin + this.players.get(i).getName() + "\t\t\t|");
		
		str.append("\n" + space);
		str.append("\n|\tConnections: \t\t\t|");
		for(int i = 0; i < this.views.size(); i++)
		{
			if("SocketView".equalsIgnoreCase(this.views.get(i).getClass().getSimpleName()))
				str.append(begin + this.views.get(i).getClass().getSimpleName() + "\t\t|");
			else
				str.append(begin + this.views.get(i).getClass().getSimpleName() + "\t\t\t|");
		}
		
		str.append("\n" + space);
		str.append("\n*---------------------------------------*");
		
		return str.toString();
	}
	
	/**
	 * Provide to bring the game at the end.<br>
	 * This includes the last lap, the assignment of the final victory points,<br>
	 * stipulate and show ranking.<br>
	 *
	 * @param finalPlayerIndex
	 *			  index of the last player before calling this method
	 */
	private void goToFinish(int finalPlayerIndex)
	{
		//Comunico ai giocatori chi è stato l'ultimo giocatore a costruire e che ci sarà l'ultimo giro
		this.getGame().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_LAST_LAP, this.getGame()));
		
		//Incremento di 3 punti vittoria al primo giocatore che termina gli empori
		this.getGame().getPlayers().get(finalPlayerIndex).getVictoryMarkerDisc().moveForward(VICTORY_POINTS_FOR_FIRST_ENDED_EMPORIUMS);
		
		//Ciclo di giocata finale
		if( finalPlayerIndex < this.turnMachines.size() - 1 )
		{
			for( int i = finalPlayerIndex + 1; i < this.turnMachines.size(); i++ )
			{
				if(!this.players.get(i).isSuspended())
				{
					this.turnMachines.get(i).run();
				}
			}
		}
		if( finalPlayerIndex > 0 )
		{
			for( int i = 0; i < finalPlayerIndex; i++ )
			{
				if(!this.players.get(i).isSuspended())
				{
					this.turnMachines.get(i).run();
				}
			}
		}
		
		GameFinisher finisher = new GameFinisher(this.game);
		try 
		{
			finisher.doFinalThings();
		} 
		catch (SuspendPlayerException e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
		//Comunico agli utenti il vincitore
		this.sendPersonalInformationsAtAllPlayers();
		this.getGame().notifyObservers(new InfoMessageType(InfoMessageTypeEnum.SHOW_RANKING, this.getGame()));
		this.getGame().notifyObservers(new FinishMessage(FinishMessageTypeEnum.FINISH_GAME_MESSAGE, this.getGame()));
		
	}
	
	/**
	 * Send personal informations at all players.
	 */
	private void sendPersonalInformationsAtAllPlayers()
	{
		for( int i = 0; i < this.players.size(); i++ )
		{
			this.getGame().setCurrentPlayer(this.players.get(i));
			this.getGame().setCurrentState(new FinishState(this.game, this.views.get(i)));
			this.getGame().getCurrentState().notifyObservers(new UpdatePlayerMessage(this.getGame()));
		}
	}
	

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable observable, Object object) 
	{		
		if( object instanceof Player )
		{
			this.game.deleteObserver((Observer)observable);
			
			this.game.notifyObservers(new InfoMessageType("Player " + (((Player)object).getPlayerID() + 1) + " disconnected.", this.game));
			this.suspendPlayer((Player)object);
		}
		else
		{
			if(object instanceof ReplyMessage)
			{
				this.game.getCurrentState().setMsg((ReplyMessage)object);
				LockSupport.unpark(this.game.getCurrentState().getCurrentThread());
			}
			if(object instanceof ChatMessage)
			{
				this.game.setChanges();
				this.game.notifyObservers(object);
			}
		}
	}
	
	/**
	 * Suspend player from the game.<br>
	 * To use in case of disconnection of the player.
	 *
	 * @param player
	 *            the player to suspend
	 */
	private void suspendPlayer(Player player)
	{		
		player.setSuspend(true);
		this.game.getCurrentState().suspendPlayer();
		if( !enoughPlayersOnline() )
		{
			this.game.notifyObservers(new FinishMessage(FinishMessageTypeEnum.STOP_GAME_MESSAGE, this.game));
			this.game.setFinishGame(true);
		}
		LockSupport.unpark(game.getCurrentState().getCurrentThread());
	}
	
	/**
	 * Check that players online are more than 1.<br>
	 * To use after suspended player to check whether to continue or bring the game to the end.
	 *
	 * @return true, if successful
	 */
	private boolean enoughPlayersOnline()
	{
		int playersOnline = 0;
		for( Player player : this.game.getPlayers() )
			if( !player.isSuspended() )
				playersOnline++;
		
		return playersOnline > 1;
				
	}
}
