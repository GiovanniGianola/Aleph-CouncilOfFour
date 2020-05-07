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
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestEngageAssistantAction.
 */
public class TestEngageAssistantAction 
{
	
	/** The test action. */
	private EngageAssistantAction  testAction;
	
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
		System.out.println("Begin Test for the class EngageAssistantAction");
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
		System.out.println("End Test for the class EngageAssistantAction");
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
		player1 = new Player(1,0,0);
		players.add(player1);
		player2 = new Player(2,100,100);
		players.add(player2);
		player3 = new Player(3,10,10);
		players.add(player3);
		kingName = "Aleph";
		map = "3";
		testGame = new Game(players, map, kingName);
		
		testAction = new EngageAssistantAction(this.testGame);
	}

	/**
	 * Test execute.
	 */
	@Test
	public void testExecute() 
	{
		testGame.setCurrentPlayer(player2);
		
		int assi = player2.getAssistants().getAssistants();
		
		testAction.execute();
		
		int assi2 = player2.getAssistants().getAssistants();
		
		assertEquals(1, assi2 - assi);
	}

	/**
	 * Test engage assistant action.
	 */
	@Test
	public void testEngageAssistantAction() 
	{
		testAction = new EngageAssistantAction(this.testGame);
		assertNotNull(testAction);
	}

	/**
	 * Test check money.
	 */
	@Test
	public void testCheckMoney() 
	{
		testGame.setCurrentPlayer(player2);
		assertEquals( true, testAction.checkMoney() );
		
		testGame.setCurrentPlayer(player1);
		assertEquals( false, testAction.checkMoney() );
	}
}
