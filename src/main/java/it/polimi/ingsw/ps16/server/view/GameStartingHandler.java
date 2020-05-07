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
package it.polimi.ingsw.ps16.server.view;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps16.server.controller.GameController;
import it.polimi.ingsw.ps16.server.model.message.FinishMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.message.finishmessage.FinishMessage;
import it.polimi.ingsw.ps16.server.model.player.Player;
import it.polimi.ingsw.ps16.server.view.singleton.GamesParameters;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class GameStartingHandler is responsible for checking<br>
 *  if the number of players are enough the game (at least 2) and, in that case, 
 *  create and start the GameController.
 */
public class GameStartingHandler 
{
	
	/** The standard out. */
	private static OutputStream stdOut;

	/**
	 * Instantiates a new game starting handler.
	 */
	public GameStartingHandler() 
	{
		stdOut = new OutputStream();
	}
	
	/**
	 * Start a new game.
	 *
	 * @param nGame
	 *            the number of the game
	 */
	public void startGame(int nGame) 
	{
		if( connectedPlayer(nGame) >= 2 )
		{			
			List<Player> pl = new ArrayList<>();
			List<ConnectionView> connections = new ArrayList<>();
			for(int i = 0; i < GamesParameters.getGameParametersInstance().getPlayers(nGame).size(); i++)
			{
				if(GamesParameters.getGameParametersInstance().getPlayers(nGame).get(i) != null)
				{
					pl.add(GamesParameters.getGameParametersInstance().getPlayers(nGame).get(i));
					connections.add(GamesParameters.getGameParametersInstance().getConnections(nGame).get(i));
				}
			}
			
	       	GameController gameController = new GameController(connections, pl, nGame);
	       	
			for(ConnectionView view : connections)
			{
				view.addObserver(gameController);
			}
			
			Server.getGameControllers().add(new Thread(gameController));
			Server.getGameControllers().get(Server.getGameControllers().size() - 1).start();
		}
		else
		{
			stdOut.getStdOut().print("Not enough players, the game " + nGame + " can't start\n");

			for(int i = 0; i < GamesParameters.getGameParametersInstance().getPlayers(nGame).size(); i++)
			{
				if(GamesParameters.getGameParametersInstance().getPlayers(nGame).get(i) != null)
					GamesParameters.getGameParametersInstance().getConnections(nGame).get(i).notifyDisconnections(new FinishMessage(FinishMessageTypeEnum.NO_ENOUGH_PLAYERS));
			}
			GamesParameters.reset(nGame);
		}
	}
	
	/**
	 * Count the players not disconnected in the initialization phase.
	 *
	 * @param nGame
	 *            the n° of game
	 * @return the number of players not disconnected in the initialization phase
	 */
	private int connectedPlayer(int nGame)
	{
		int connected = 0;
		
		for(int i = 0; i < GamesParameters.getGameParametersInstance().getPlayers(nGame).size(); i++)
		{
			if(GamesParameters.getGameParametersInstance().getPlayers(nGame).get(i) != null)
				connected++;
		}
		
		return connected;
	}
}
