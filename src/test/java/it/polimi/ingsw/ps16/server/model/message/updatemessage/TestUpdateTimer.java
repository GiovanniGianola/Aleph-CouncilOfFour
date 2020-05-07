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
package it.polimi.ingsw.ps16.server.model.message.updatemessage;

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
 * The Class TestUpdateTimer.
 */
public class TestUpdateTimer 
{
	
	/** The upm. */
	private UpdateTimer upm;
	
	/** The action. */
	private String action;
	
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
	
	/** The timer. */
	private long timer;
	
	/** The get timer. */
	private int getTimer;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class UpdateTimer");
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
		System.out.println("End Test for the class UpdateTimer");
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
		kingName = "Pradella";
		map = "3";
		testGame = new Game(players,map,kingName);
		
		this.timer = (long) 60 * 1000;
		
		upm = new UpdateTimer(this.testGame, this.timer, "Action");
	}

	/**
	 * Test update timer.
	 */
	@Test
	public void testUpdateTimer() 
	{
		upm = new UpdateTimer(this.testGame, this.timer, "Action");
		
		assertNotNull(upm);
	}

	/**
	 * Test get timer.
	 */
	@Test
	public void testGetTimer()
	{
		getTimer = upm.getTimer();
		
		assertEquals(this.timer/1000, this.getTimer);
	}

	/**
	 * Test get action.
	 */
	@Test
	public void testGetAction()
	{
		action = upm.getAction();
		
		assertEquals("Action", action);
	}

}
