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
package it.polimi.ingsw.ps16.server.model.turn;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.market.MarketObject;
import it.polimi.ingsw.ps16.server.model.message.replymessage.ReplyMessage;
import it.polimi.ingsw.ps16.server.view.ConnectionView;

/**
 * The Class TurnState.
 */
public abstract class TurnState extends Observable implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7888528845084505311L;
	
	/** The Constant INVALID_INPUT. */
	private static final String INVALID_INPUT = "Invalid Input.";
	
//	/** The Constant TIMER. */
//	private static final long TIMER = (long)3 * 60 * 1000;
	
//	/** The current timeout. */
//	private static int currentTimeout = 0;
	
	/** The game. */
	private final Game game;
	
	/** The message. */
	private ReplyMessage msg;
	
	/** The current thread. */
	private transient Thread currentThread;
	
	/** The view. */
	private transient ConnectionView view;
	
	/** The is player to suspend. */
	private transient boolean isPlayerToSuspend;
	
//	/** The timer TH. */
//	private transient Thread timerTH;
	
	/**
	 * Instantiates a new turn state.
	 *
	 * @param game
	 *            the game
	 * @param view
	 *            the view
	 */
	public TurnState(Game game, ConnectionView view)
	{
		this.game = game;
		this.currentThread = Thread.currentThread(); 
		this.view = view;
		this.addObserver(this.view);
	}
	
	/**
	 * Gets the current thread.
	 *
	 * @return the current thread
	 */
	public Thread getCurrentThread()
	{
		return this.currentThread;
	}
	
	/**
	 * Sets the changes.
	 */
	public void setChanges()
	{
		this.setChanged();
	}	
	
	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() 
	{
		return game;
	}
	
	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public ReplyMessage getMsg() 
	{
		return this.msg;
	}
	
	/**
	 * Sets the msg.
	 *
	 * @param msg
	 *            the new msg
	 */
	public void setMsg(ReplyMessage msg) 
	{
		this.msg = msg;
	}
	
	/**
	 * Sets the observer.
	 *
	 * @param o
	 *            the new observer
	 */
	public void setObserver(Observer o)
	{
		this.addObserver(o);
	}
	
	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public ConnectionView getView()
	{
		return view;
	}
	
	/**
	 * Checks if is player to suspend.
	 *
	 * @return true, if is player to suspend
	 */
	public boolean isPlayerToSuspend()
	{
		return isPlayerToSuspend;
	}
	
	/**
	 * Suspend player.
	 */
	public void suspendPlayer()
	{
		isPlayerToSuspend = true;
	}
	
	/**
	 * Check player to suspend.
	 *
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public void checkPlayerToSuspend() throws SuspendPlayerException
	{
		if( this.isPlayerToSuspend() )
		{
			throw new SuspendPlayerException(null);
		}
	}
	
	/**
	 * Execute game state.
	 *
	 * @return the turn state
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public TurnState executeGameState() throws SuspendPlayerException
	{
		return null;
	}
	
	/**
	 * Execute market state.
	 *
	 * @param marketObjects
	 *            the market objects
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	public void executeMarketState(List<MarketObject> marketObjects) throws SuspendPlayerException 
	{}
	
//	/**
//	 * Execute game state.
//	 *
//	 * @return the turn state
//	 * @throws SuspendPlayerException
//	 *             the suspend player exception
//	 */
//	public TurnState executeGameState() throws SuspendPlayerException
//	{
//		TurnState.setCurrentTimeout();
//		timerTH = new Thread(new TurnTimeOut(TurnState.getCurrentTimeout(), this.game, TIMER));
//		timerTH.start();
//		return null;
//	}
//	
//	/**
//	 * Execute market state.
//	 *
//	 * @param marketObjects
//	 *            the market objects
//	 * @throws SuspendPlayerException
//	 *             the suspend player exception
//	 */
//	public void executeMarketState(List<MarketObject> marketObjects) throws SuspendPlayerException 
//	{
//		TurnState.setCurrentTimeout();
//		timerTH = new Thread(new TurnTimeOut(TurnState.getCurrentTimeout(), this.game, TIMER));
//		timerTH.start();
//	}
	
//	/**
//	 * Sets the current timeout.
//	 */
//	private static synchronized void setCurrentTimeout()
//	{
//		TurnState.currentTimeout++;
//	}
//	
//	/**
//	 * Gets the current timeout.
//	 *
//	 * @return the current timeout
//	 */
//	public static int getCurrentTimeout()
//	{
//		return TurnState.currentTimeout;
//	}
	
//	/**
//	 * Gets the timer.
//	 *
//	 * @return the timer
//	 */
//	public static long getTimer()
//	{
//		return TIMER;
//	}

	/**
	 * Gets the invalid input.
	 *
	 * @return the invalid input
	 */
	public String getInvalidInput() 
	{
		return INVALID_INPUT;
	}

//	/**
//	 * Gets the thread timer.
//	 *
//	 * @return the thread timer
//	 */
//	public Thread getThreadTimer() 
//	{
//		return this.timerTH;
//	}
}
