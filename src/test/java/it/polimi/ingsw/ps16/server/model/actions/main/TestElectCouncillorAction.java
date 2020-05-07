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
package it.polimi.ingsw.ps16.server.model.actions.main;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.gameboard.Councillor;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestElectCouncillorAction.
 */
public class TestElectCouncillorAction {

	/** The test action. */
	private ElectCouncillorAction testAction;
	
	/** The test game. */
	private Game testGame;
	
	/** The player 1. */
	private Player player1;
	
	/** The player 2. */
	private Player player2;
	
	/** The player 3. */
	private Player player3;
	
	/** The king name. */
	private String kingName;
	
	/** The players. */
	private List<Player> players;
	
	/** The map. */
	private String map;
	
	/** The councillor chosen. */
	private Councillor councillorChosen;
	
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class ElectCouncillor");
	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		players = new ArrayList<>();
		player1 = new Player(1,10,10);
		players.add(player1);
		player2 = new Player(2,100,100);
		players.add(player2);
		player3 = new Player(3,10,10);
		players.add(player3);
		kingName = "Aleph";
		map = "3";
		testGame = new Game(players,map,kingName);
		testAction = new ElectCouncillorAction(testGame);
		councillorChosen = new Councillor(Colour.WHITE);
		testAction.setCouncillorChosen(councillorChosen);
		testAction.setRegionChosen(testGame.getGameBoard().getRegionBoard().get(1));
	}
	
	/**
	 * Test execute.
	 */
	@Test
	public void testExecute()
	{
		testGame.setCurrentPlayer(player1);
		Councillor councillor1= testGame.getGameBoard().getRegionBoard().get(1).getBalcony().getBalcony().peek();
		testAction.execute();
		assertThat(testGame.getGameBoard().getRegionBoard().get(1).getBalcony().getBalcony().peek(), not(equalTo(councillor1)));
	}
	
	/**
	 * Test set region chosen.
	 */
	@Test
	public void testSetRegionChosen() 
	{
		testAction.setRegionChosen();
		assertNotNull(testAction);
		testAction.setRegionChosen(this.testGame.getGameBoard().getRegionBoard().get(0));
		assertNotNull(testAction);
	}
	
	/**
	 * Tear down after class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		System.out.println("End Test for the class ElectCouncillor");
	}
}
