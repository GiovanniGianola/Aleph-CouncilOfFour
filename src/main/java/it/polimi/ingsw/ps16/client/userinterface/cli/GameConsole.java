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
package it.polimi.ingsw.ps16.client.userinterface.cli;

import java.awt.Color;
import java.util.Observable;
import java.util.Scanner;

import it.polimi.ingsw.ps16.client.net.HandlerView;
import it.polimi.ingsw.ps16.client.userinterface.OutputView;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.chatmessage.ChatMessage;
import it.polimi.ingsw.ps16.server.model.message.finishmessage.FinishMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageInitializeType;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.initializeplayermessage.InitializePlayerMessage;
import it.polimi.ingsw.ps16.server.model.message.replymessage.ReplyMessage;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class GameConsole is the CLI version of the Output View.<br>
 * it allow player to play CouncilOfFour using the standard input<br>
 * and output offered by JVM
 */
public class GameConsole extends OutputView
{
	
	/** The Constant CHAT. */
	private static final String CHAT = "chat ";
	
	/** The client handler. */
	private HandlerView clientHandler;
	
	/** The player ID. */
	private int playerID;
	
	/** The player name. */
	private String playerName;
	
	/** The player color. */
	private Color playerColor;
	
	/** The standard in. */
	private Scanner stdIn;
	
	/** The standard out. */
	private static OutputStream stdOut;
	
	/** The choice. */
	private String choice;
	
	/** The game ready. */
	private boolean gameReady;
	
	/** The your turn. */
	private boolean yourTurn;

	/**
	 * Instantiates a new game console.
	 *
	 * @param clientHandler
	 *            the client handler
	 */
	public GameConsole(HandlerView clientHandler) 
	{
		this.stdIn = new Scanner(System.in);
		stdOut = new OutputStream();
		
		this.gameReady = false;
		this.yourTurn = false;
		this.choice = "";
		
		this.clientHandler = clientHandler;
		
		this.clientHandler.addObserver(this);
		this.addObserver(this.clientHandler);
		
		new Thread(this.clientHandler).start();
	}

	/**
	 * check the type of the message received from the server.
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		if( arg instanceof InfoMessageType)
		{
			this.gameReady = true;
			stdOut.getStdOut().println("\n" + arg.toString());
		}
		else if( arg instanceof BroadcastMessage || arg instanceof ErrorMessage ||  arg instanceof InfoMessageInitializeType )
		{
			stdOut.getStdOut().println("\n" + arg.toString());
		}
		else if( arg instanceof RequestMessage )
		{
			stdOut.getStdOut().print("\n" + arg.toString() + "\n--> ");
			this.yourTurn = true;

			choice = stdIn.nextLine();
		}
		else if( arg instanceof FinishMessage )
		{
			stdOut.getStdOut().println(arg.toString());
			this.clientHandler.setFinishGame(true);
			this.yourTurn = false;
			Runtime.getRuntime().exit(9);
		}
		else if( arg instanceof InitializePlayerMessage )
		{			
			this.playerID = ((InitializePlayerMessage)arg).getPlayerID();
			if( "".equals(((InitializePlayerMessage)arg).getPlayerName()) )
				this.playerName = "#NoName";
			else
				this.playerName = ((InitializePlayerMessage)arg).getPlayerName();
			this.playerColor = ((InitializePlayerMessage)arg).getPlayerColor();
		}
		else if( arg instanceof ChatMessage )
		{
			stdOut.getStdOut().println("\n** " + ((ChatMessage)arg).senderHeader() +  arg.toString() + " **");
		}
		
		this.interaction();
	}
	
	/**
	 * Interaction method check player input on the console.
	 */
	private void interaction()
	{		
		if(this.gameReady && choice.startsWith(CHAT))
		{
			setChanged();
			this.notifyObservers(new ChatMessage("[ID: " + this.playerID + " Name: " + this.playerName + "]: ", this.playerColor, choice.substring(4)));
			stdOut.getStdOut().print("\n--> ");
			choice = stdIn.nextLine();
		}
		else if(!this.gameReady && choice.startsWith(CHAT))
		{
			stdOut.getStdOut().print("\n[Warning] Game not ready yet, wait untill the match begins.\n--> ");
			choice = stdIn.nextLine();
		}
		if(this.yourTurn && !choice.startsWith(CHAT))
		{
			setChanged();
			this.notifyObservers(new ReplyMessage(choice));

			this.yourTurn = false;
		}
	}
}
