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
package it.polimi.ingsw.ps16.server.model.actions.quick;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.gameboard.Councillor;
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestElectCouncillorUsingAssistantAction.
 */
public class TestElectCouncillorUsingAssistantAction 
{
	
	/** The test action. */
	private ElectCouncillorUsingAssistantAction testAction;
	
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
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class ElectCouncillorUsingAssistantAction");
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
		System.out.println("End Test for the class ElectCouncillorUsingAssistantAction");
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
		player1 = new Player(1,0,10);
		players.add(player1);
		player2 = new Player(2,100,100);
		players.add(player2);
		player3 = new Player(3,10,10);
		players.add(player3);
		kingName = "Aleph";
		map = "3";
		testGame = new Game(players, map, kingName);
		
		testAction = new ElectCouncillorUsingAssistantAction(this.testGame);
	}

	/**
	 * Test execute.
	 */
	@Test
	public void testExecute() 
	{
		Councillor cou = new Councillor(Colour.BLACK);
		RegionBoard rb = this.testGame.getGameBoard().getRegionBoard().get(0);
		
		testGame.setCurrentPlayer(player2);
		testAction.setCouncillorChosen(cou);
		testAction.setRegionChosen(rb);
		
		testAction.execute();
		
		Councillor cou2 = null;
		
		int size = rb.getBalcony().getBalcony().size();
		
		for(int i = 0; i < size; i++)
			cou2 = rb.getBalcony().getBalcony().remove();
		
		assertEquals( cou.getColor(), cou2.getColor() );
	}

	/**
	 * Test elect councillor using assistant action.
	 */
	@Test
	public void testElectCouncillorUsingAssistantAction() 
	{
		testAction = new ElectCouncillorUsingAssistantAction(this.testGame);
		assertNotNull(testAction);
	}

	/**
	 * Test set councillor chosen.
	 */
	@Test
	public void testSetCouncillorChosen() 
	{
		testAction.setCouncillorChosen(new Councillor(Colour.BLACK));
		assertNotNull(testAction);
	}

	/**
	 * Test set region chosen region board.
	 */
	@Test
	public void testSetRegionChosenRegionBoard() 
	{
		testAction.setRegionChosen(this.testGame.getGameBoard().getRegionBoard().get(0));
		assertNotNull(testAction);
	}

	/**
	 * Test set region chosen.
	 */
	@Test
	public void testSetRegionChosen() 
	{
		testAction.setRegionChosen();
		assertNotNull(testAction);
	}

	/**
	 * Test check assistants.
	 */
	@Test
	public void testCheckAssistants() 
	{
		testGame.setCurrentPlayer(player2);
		assertEquals( true, testAction.checkAssistants() );
		
		testGame.setCurrentPlayer(player1);
		assertEquals( false, testAction.checkAssistants() );
	}
}
