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
package it.polimi.ingsw.ps16.server.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.player.Player;


/**
 * The Class TestGame.
 */
public class TestGame 
{
	
	/** The g test. */
	private Game gTest;	
	
	/** The pl test. */
	private List<Player> plTest;
	
	/** The king name. */
	private String kingName;
	
	/** The p test 1. */
	private Player pTest1;
	
	/** The p test 2. */
	private Player pTest2;
	
	/** The p test 3. */
	private Player pTest3;
	

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class Game");
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
		System.out.println("End Test for the class Game");
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
		plTest = new ArrayList<>();
		pTest1 = new Player(1,10,10);
		plTest.add(pTest1);
		pTest2 = new Player(2,10,10);
		plTest.add(pTest2);
		pTest3 = new Player(3,10,10);
		plTest.add(pTest3);
		
		kingName = "Alexander Magnus";
	}

	/**
	 * Test game.
	 */
	@Test
	public void testGame() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(7) + 1) + "", kingName);
		assertNotNull("Error: Game not settet yet", gTest);
	}

	/**
	 * Test get game board.
	 */
	@Test
	public void testGetGameBoard() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(7) + 1) + "", kingName);
		assertNotNull("Error: GameBoard not settet yet", gTest.getGameBoard());
	}

	/**
	 * Test get players.
	 */
	@Test
	public void testGetPlayers() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(7) + 1) + "", kingName);
		assertNotNull("Error: Players not settet yet", gTest.getPlayers());
	}

	/**
	 * Test get current state.
	 */
	@Test
	public void testGetCurrentState() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(8) + 1) + "", kingName);
		assertNull("Error: CurrentState not settet yet", gTest.getCurrentState());
	}

	/**
	 * Test set current state.
	 */
	@Test
	public void testSetCurrentState() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(8) + 1) + "", kingName);
		assertNull("Error: CurrentState not settet yet", gTest.getCurrentState());
	}

	/**
	 * Test get current player.
	 */
	@Test
	public void testGetCurrentPlayer() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(8) + 1) + "", kingName);
		gTest.setCurrentPlayer(pTest1);
		assertNotNull("Error: CurrentPlayer not settet yet", gTest.getCurrentPlayer());
	}

	/**
	 * Test set current player.
	 */
	@Test
	public void testSetCurrentPlayer() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(7) + 1) + "", kingName);
		gTest.setCurrentPlayer(pTest1);
		assertNotNull("Error: CurrentPlayer not settet yet", gTest.getCurrentPlayer());
	}

	/**
	 * Test is finish game.
	 */
	@Test
	public void testIsFinishGame() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(7) + 1) + "", kingName);
		assertEquals(false, gTest.isFinishGame());
	}

	/**
	 * Test set finish game.
	 */
	@Test
	public void testSetFinishGame() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(7) + 1) + "", kingName);
		gTest.setFinishGame(true);
		assertEquals("Game done",true, gTest.isFinishGame());
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() 
	{
		gTest = new Game(plTest, ((new Random()).nextInt(7) + 1) + "", kingName);
		assertNotNull("Problem in toString Method", gTest.toString());
	}

}
