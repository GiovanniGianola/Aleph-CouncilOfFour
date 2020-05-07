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
package it.polimi.ingsw.ps16.server.view.rmi;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.client.net.rmi.ClientViewRemote;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeInitializeEnum;
import it.polimi.ingsw.ps16.server.model.message.RequestMessageTypeInitializeEnum;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageInitializeType;
import it.polimi.ingsw.ps16.server.model.message.initializeplayermessage.InitializePlayerMessage;
import it.polimi.ingsw.ps16.server.model.message.replymessage.ReplyMessage;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;
import it.polimi.ingsw.ps16.server.model.player.Player;
import it.polimi.ingsw.ps16.server.view.ConnectionView;
import it.polimi.ingsw.ps16.server.view.singleton.GamesParameters;

/**
 * The Class ClientRMI is the server that <br>
 * provide a connection with the client using RMI protocol.
 */
public class RMIView extends ConnectionView implements RMIRemoteView, Runnable, Serializable
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( RMIView.class.getName() );
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1271509066012916761L;
	
	/** The received object. */
	private transient Object receivedObject;
	
	/** The player. */
	private Player player;
	
	/** The n player. */
	private int nPlayer;
	
	/** The current thread. */
	private transient Thread currentThread = new Thread();
	
	/** The player choice. */
	private String  playerChoice;
	
	/**
	 * Instantiates a new RMI view.
	 *
	 * @param clientStub
	 *            the client stub
	 * @param nGame
	 *            the n° of the game
	 */
	public RMIView(ClientViewRemote clientStub, int nGame) 
	{
		super(nGame);
		
		this.client = clientStub;
		this.currentThread = Thread.currentThread(); 
		this.receivedObject =  null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() 
	{
		this.currentThread = Thread.currentThread();
		try 
		{
			this.initialize();
		} 
		catch (RemoteException e) 
		{
			if(!this.player.isSuspended())
				stdOut.getStdOut().print("RMIView of " + this.getNGame() 
						+ "° game, and " + (this.nPlayer+1) + " °"
						+ "player quit, during initialize.");
			
			if( this.nPlayer == 1 )
				GamesParameters.getGameParametersInstance().getPlayers(this.getNGame()).add(0, null);
			else
				GamesParameters.getGameParametersInstance().getPlayers(this.getNGame()).add(this.nPlayer, null);
		}
	}
	
	/** 
	 * Manage the initialization phase, so adding parameters to the GameParameters of this game.
	 */
	@Override
	protected void initialize() throws RemoteException 
	{
		synchronized(this)
		{
			GamesParameters.getGameParametersInstance().getConnections(this.getNGame()).add(this);
			
			if( GamesParameters.getGameParametersInstance().getPlayers(this.getNGame()).isEmpty() )
				this.nPlayer = 0;
			else
				this.nPlayer = GamesParameters.getGameParametersInstance().getPlayers(this.getNGame()).size();
			
			this.player = new Player(this.nPlayer, this.nPlayer + 1, 10 + this.nPlayer);
			
			GamesParameters.getGameParametersInstance().getPlayers(this.getNGame()).add(this.player);
		}
		
		stdOut.getStdOut().println(this.nPlayer+1 + "° player connected using RMI.");
		
		this.client.sendMessageToClient(new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_CONNECTION_CREATED, this.nPlayer+1, this.getNGame()));
		this.client.sendMessageToClient(new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_PLAYER_GAME_NUMBER, this.nPlayer+1, this.getNGame()));
		
		this.setMapKing();
		
		if( this.nPlayer == 1 )
			this.initializeGameTimer();
		
		this.client.sendMessageToClient(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_YOUR_NAME));
		LockSupport.park(this);
		
		this.playerChoice = ((ReplyMessage)this.receivedObject).getChoice();
		
		if( "ExitInit".equalsIgnoreCase( playerChoice ) )
			disconnectInit();
		else
			this.player.setName(playerChoice);
		
		this.setColor(); 
		
		this.client.sendMessageToClient(new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_WAIT_TIMER, this.nPlayer+1, this.getNGame()));
		this.client.sendMessageToClient(new InitializePlayerMessage(this.player));
		this.client.sendMessageToClient(null);
		
		stdOut.getStdOut().println(this.nPlayer+1 + "° player ready.");
	}
	
	/**
	 * If this player is the first, manage the choose of the number of map<br>
	 * and the king's name, so add them to the GameParameters of this game.
	 */
	@Override
	protected void setMapKing() throws RemoteException
	{
		if( nPlayer == 0 )
		{
			stdOut.getStdOut().println("\nThe registration for the " + this.getNGame() + "° game is just began\n");
			
			String mapChoice = "";
			boolean correctMap = false;
			while(!correctMap)
			{
				this.client.sendMessageToClient(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_MAP));
				LockSupport.park(this);
				
				try
				{
					mapChoice = ((ReplyMessage)this.receivedObject).getChoice();
					Integer.parseInt(mapChoice);
					correctMap = true;
				}
				catch(NumberFormatException e)
				{
					if( "ExitInit".equalsIgnoreCase( mapChoice ) )
					{
						disconnectInit();
					}
					else
						this.client.sendMessageToClient(new ErrorMessage("Invalid Input", null));
				}
			}
			GamesParameters.getGameParametersInstance().setMapNumber(this.getNGame(), mapChoice);
			
			GamesParameters.getGameParametersInstance().setMapNumber(this.getNGame(), ((ReplyMessage)this.receivedObject).getChoice());
		
			this.client.sendMessageToClient(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_KING_NAME));
			LockSupport.park(this);
			
			this.playerChoice = ((ReplyMessage)this.receivedObject).getChoice();
			if( "ExitInit".equalsIgnoreCase(this.playerChoice) )
				disconnectInit();
			else
				GamesParameters.getGameParametersInstance().setKingName(this.getNGame(), this.playerChoice);
		}
	}

	/**
	 * Manage the choose of the color, so add it to the GameParameters of this game.
	 */
	@Override
	protected void setColor() throws RemoteException 
	{
		this.client.sendMessageToClient(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_COLOR));
		LockSupport.park(this);
		String cl = ((ReplyMessage)this.receivedObject).getChoice();
		
		while( !"black".equalsIgnoreCase(cl) && !"blue".equalsIgnoreCase(cl) && !"red".equalsIgnoreCase(cl) &&
			   !"yellow".equalsIgnoreCase(cl) && !"green".equalsIgnoreCase(cl) && !"cyan".equalsIgnoreCase(cl))
		{
			if( "ExitInit".equalsIgnoreCase(cl) )
				disconnectInit();
			
			this.client.sendMessageToClient(new ErrorMessage("Invalid Input", null));
			this.client.sendMessageToClient(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_COLOR));
			LockSupport.park(this);
			cl = ((ReplyMessage)this.receivedObject).getChoice();
		}
		
		switch( cl )
		{
			case "black": this.player.setColor(Color.BLACK);
					break; 
			case "blue": this.player.setColor(Color.BLUE);
			  		break; 
			case "red": this.player.setColor(Color.RED);
			  		break; 
			case "yellow": this.player.setColor(Color.YELLOW);
			  		break; 
			case "green": this.player.setColor(Color.GREEN);
			  		break; 
			case "cyan": this.player.setColor(Color.CYAN);
			  		break; 
			default: 
					break;
		}
	}
	
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.view.rmi.RMIRemoteView#sendInitialMessage(java.lang.Object)
	 */
	@Override
	public void sendInitialMessage(Object receivedObject) throws RemoteException 
	{
		this.receivedObject = receivedObject;
		LockSupport.unpark(this.getCurrentThread());
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.view.rmi.RMIRemoteView#sendMessage(java.lang.Object)
	 */
	@Override
	public void sendMessage(Object receivedObject) throws RemoteException 
	{
		this.setChanged();
		this.notifyObservers(receivedObject);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object object) 
	{
		if( (object instanceof BroadcastMessage) && (((BroadcastMessage)object).getCurrentPlayerID() == this.player.getPlayerID()) )
		{
			return;
		}
		try 
		{
			this.client.sendMessageToClient(object);
		} 
		catch (RemoteException e) 
		{
			if(!this.player.isSuspended())
				stdOut.getStdOut().print("RMIView of " + this.getNGame() 
				+ "° game, and " + (this.nPlayer+1) + "° "
				+ "player quit");
						
			this.setChanged();
			this.notifyObservers(this.player);
		}	
	}
	
	/**
	 * Gets the current thread.
	 *
	 * @return the current thread
	 */
	private Thread getCurrentThread()
	{
		return this.currentThread;
	}
	
	/**
	 * cathc the disconnection of a player during initialization phase.
	 */
	private void disconnectInit()
	{
		stdOut.getStdOut().println( "SocketView of " + this.getNGame()  
		+ "° game, and " + (this.nPlayer+1) + "°"
		+ "player quit during initialize.");

		GamesParameters.getGameParametersInstance().getPlayers(this.getNGame()).remove(this.player);
		GamesParameters.getGameParametersInstance().getConnections(this.getNGame()).remove(this);
		return;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.ps16.server.view.ConnectionView#notifyDisconnections(java.lang.Object)
	 */
	@Override
	protected void notifyDisconnections(Object arg) 
	{
		try 
		{
			this.client.sendMessageToClient(arg);
		} 
		catch (RemoteException e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
	}

	
	
}
