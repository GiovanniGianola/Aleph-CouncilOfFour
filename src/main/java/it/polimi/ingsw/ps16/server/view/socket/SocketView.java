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
package it.polimi.ingsw.ps16.server.view.socket;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * The Class SocketView provide the communication between client and server.<br>
 * Route the messages from the server to the player<br>
 * and the answer of the player to the objects that wait it.
 */
public class SocketView extends ConnectionView implements Runnable
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( SocketView.class.getName() );
	
	/** The socket. */
	private Socket socket;
	
	/** The socket in. */
	private ObjectInputStream socketIn;
	
	/** The socket out. */
	private ObjectOutputStream socketOut;
	
	/** The player. */
	private Player player;
	
	/** The is finish game. */
	private boolean isFinishGame;
	
	/** The received object. */
	private Object receivedObject;
	
	/** The number of players. */
	private int nPlayer;
	
	/** The player choice. */
	private String  playerChoice;

	/**
	 * Instantiates a new socket view.
	 *
	 * @param socket
	 *            the socket
	 * @param nGame
	 *            the n° of the game
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public SocketView(Socket socket, int nGame) throws IOException 
	{
		super(nGame);
		
		this.socket = socket;
		this.receivedObject = null;
		
		this.socketOut = new ObjectOutputStream(this.socket.getOutputStream());
		this.socketIn = new ObjectInputStream(this.socket.getInputStream());
		
		this.isFinishGame = false;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public synchronized void update(Observable o, Object object) 
	{
		if( (object instanceof BroadcastMessage) && (((BroadcastMessage)object).getCurrentPlayerID() == this.player.getPlayerID()) )
		{
			return;
		}
		try 
		{
			this.socketOut.writeObject(object);
			this.socketOut.flush();
		} 
		catch (IOException e) 
		{
			if(!this.player.isSuspended())
				stdOut.getStdOut().println( "SocketView of " + this.getNGame()  
				+ "° game, and " + (this.nPlayer+1) + "° "
				+ "player quit\n");
		}
	}

	/* *
	 * Run manage the communication from the player view to objects of the server.
	 */
	@Override
	public void run() 
	{
		try 
		{
			this.initialize();
		} 
		catch (ClassNotFoundException | IOException e1) 
		{
			disconnectInit();
		}
		
		while(!this.isFinishGame)	
		{
			try 
			{
				receivedObject = this.socketIn.readObject();

				this.setChanged();
				this.notifyObservers(receivedObject);
			} 
			catch(IOException | ClassNotFoundException  e)
			{
				if(!this.player.isSuspended())
					stdOut.getStdOut().println( "SocketView of " + this.getNGame()  
							+ "° game, and " + (this.nPlayer+1) + "° "
							+ "player quit");
				
				this.isFinishGame = true;
				this.setChanged();
				this.notifyObservers(this.player);
			}
		}
	}

	/**
	 * Manage the initialization phase, so adding parameters to the GameParameters(Singleton) of this game.
	 */
	@Override
	protected void initialize() throws IOException, ClassNotFoundException
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
		
		stdOut.getStdOut().println(this.nPlayer+1 + "° player connected using SOCKET.");
		
		this.socketOut.writeObject(new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_CONNECTION_CREATED, this.nPlayer+1, this.getNGame()));
		
		this.socketOut.writeObject(new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_PLAYER_GAME_NUMBER, this.nPlayer+1, this.getNGame()));
		
		this.setMapKing();
		
		if( this.nPlayer == 1 )
			this.initializeGameTimer();

		
		this.socketOut.writeObject(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_YOUR_NAME));
		this.playerChoice = ((ReplyMessage)this.socketIn.readObject()).getChoice();
		this.player.setName(playerChoice);
		
		this.setColor();
		
		this.socketOut.writeObject(new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_WAIT_TIMER, this.nPlayer+1, this.getNGame()));
		this.socketOut.writeObject(new InitializePlayerMessage(this.player));
		
		stdOut.getStdOut().println(this.nPlayer+1 + "° player ready.");
	}

	/**
	 * If the current player is the host og the match, manage the choice of the number of map<br>
	 * and the king's name, so add them to the GameParameters(Singleton) of this game.
	 */
	@Override
	protected void setMapKing() throws IOException, ClassNotFoundException
	{
		if( this.nPlayer == 0 )
		{
			stdOut.getStdOut().println("\nThe registration for the " + this.getNGame() + "° game is just began\n");
			
			String mapChoice = "";
			boolean correctMap = false;
			while(!correctMap)
			{
				this.socketOut.writeObject(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_MAP));
				try
				{
					mapChoice = ((ReplyMessage)this.socketIn.readObject()).getChoice();
					Integer.parseInt(mapChoice);
					correctMap = true;
				}
				catch(NumberFormatException e)
				{
					this.socketOut.writeObject(new ErrorMessage("Invalid Input", null));
				}
			}
			GamesParameters.getGameParametersInstance().setMapNumber(this.getNGame(), mapChoice);
			
			this.socketOut.writeObject(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_KING_NAME));
			this.playerChoice = ((ReplyMessage)this.socketIn.readObject()).getChoice();
			GamesParameters.getGameParametersInstance().setKingName(this.getNGame(), playerChoice);
		}
	}

	/**
	 * Manage the choice of the color, so add it to the GameParameters(Singleton) of this game.
	 */
	@Override
	protected void setColor() throws IOException, ClassNotFoundException 
	{
		this.socketOut.writeObject(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_COLOR));
		String cl = ((ReplyMessage)this.socketIn.readObject()).getChoice();
		
		while( !"black".equalsIgnoreCase(cl) && !"blue".equalsIgnoreCase(cl) && !"red".equalsIgnoreCase(cl) &&
			   !"yellow".equalsIgnoreCase(cl) && !"green".equalsIgnoreCase(cl) && !"cyan".equalsIgnoreCase(cl))
		{
			this.socketOut.writeObject(new ErrorMessage("Invalid Input", null));
			this.socketOut.writeObject(new RequestMessage(RequestMessageTypeInitializeEnum.CHOOSE_COLOR));
			cl = ((ReplyMessage)this.socketIn.readObject()).getChoice();
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
	
	/**
	 * Manage the disconnections in initialization phase.
	 */
	private void disconnectInit()
	{
		stdOut.getStdOut().println( "SocketView of " + this.getNGame()  
		+ "° game, and " + (this.nPlayer+1) + "°"
		+ "player quit during initialize.");

		this.isFinishGame = true;

		GamesParameters.getGameParametersInstance().getPlayers(this.getNGame()).remove(this.player);
		GamesParameters.getGameParametersInstance().getConnections(this.getNGame()).remove(this);
		return;
	}

	/**
	 * Notify the disconnections to the remaining player
	 */
	@Override
	protected void notifyDisconnections( Object arg ) 
	{
		try 
		{
			this.socketOut.writeObject(arg);
			this.socketOut.flush();
		} 
		catch (IOException e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
	}
}
