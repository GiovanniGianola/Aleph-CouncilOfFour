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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestChangeBusinessPermitCardAction.
 */
public class TestChangeBusinessPermitCardAction 
{
	
	/** The test action. */
	private ChangeBusinessPermitCardAction testAction;
	
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
		System.out.println("Begin Test for the class ChangeBusinessPermitCardAction");
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
		System.out.println("End Test for the class ChangeBusinessPermitCardAction");
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
		
		testAction = new ChangeBusinessPermitCardAction(this.testGame);
	}

	/**
	 * Test execute.
	 */
	@Test
	public void testExecute() 
	{
		testGame.setCurrentPlayer(player2);
		
		RegionBoard rb = this.testGame.getGameBoard().getRegionBoard().get(0);
		testAction.setRegionChosen(rb);
		BusinessPermitCard b1 = this.testGame.getGameBoard().getRegionBoard().get(0).getBusinessPermitTileOne();
		
		testAction.execute();
		
		BusinessPermitCard b2 = this.testGame.getGameBoard().getRegionBoard().get(0).getBusinessPermitTileOne();
		
		assertThat(b1,  not(equalTo(b2)));		
	}

	/**
	 * Test change business permit card action.
	 */
	@Test
	public void testChangeBusinessPermitCardAction() 
	{
		testAction = new ChangeBusinessPermitCardAction(this.testGame);
		assertNotNull(testAction);
	}

	/**
	 * Test get region board.
	 */
	@Test
	public void testGetRegionBoard() 
	{
		RegionBoard rb = this.testGame.getGameBoard().getRegionBoard().get(0);
		testAction.setRegionChosen(rb);
		
		assertEquals( rb, testAction.getRegionBoard() );
	}

	/**
	 * Test set region chosen.
	 */
	@Test
	public void testSetRegionChosen() 
	{
		RegionBoard rb = this.testGame.getGameBoard().getRegionBoard().get(0);
		testAction.setRegionChosen(rb);
		
		assertEquals( rb, testAction.getRegionBoard() );
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

	/**
	 * Test check business permit cards deck.
	 */
	@Test
	public void testCheckBusinessPermitCardsDeck() 
	{
		RegionBoard rb = this.testGame.getGameBoard().getRegionBoard().get(0);
		testAction.setRegionChosen(rb);
		
		assertEquals( true, testAction.checkBusinessPermitCardsDeck() );
	}

	/**
	 * Test check business permit cards deck all.
	 */
	@Test
	public void testCheckBusinessPermitCardsDeckAll()
	{
		RegionBoard rb = this.testGame.getGameBoard().getRegionBoard().get(0);
		testAction.setRegionChosen(rb);
		
		assertEquals( true, testAction.checkBusinessPermitCardsDeckAll() );
	}
}
