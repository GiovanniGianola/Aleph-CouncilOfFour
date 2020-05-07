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
package it.polimi.ingsw.ps16.server.model.gameboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.ParseStreamXML;
import it.polimi.ingsw.ps16.server.model.player.Player;


/**
 * The Class TestGameBoard.
 */
public class TestGameBoard 
{
	
	/** The gb test. */
	private GameBoard gbTest;
	
	/** The players. */
	private List<Player> players;
	
	/** The king name. */
	private String kingName= "Alexander Magnus";
	
	/** The p test 1. */
	private Player pTest1;
	
	/** The p test 2. */
	private Player pTest2;
	
	/** The p test 3. */
	private Player pTest3;
	
	/** The p test 4. */
	private Player pTest4;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class GameBoard");
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
		System.out.println("End Test for the class RegionBoard");
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
		players =  new ArrayList<>();
		pTest1 = new Player(1,10,10);
		players.add(pTest1);
		pTest2 = new Player(2,10,10);
		players.add(pTest2);
		pTest3 = new Player(3,10,10);
		players.add(pTest3);
		pTest4 = new Player(4,10,10);
		players.add(pTest4);
		
		
		final ParseStreamXML psXML;
		
		psXML = new ParseStreamXML(new GameBoard());
		gbTest = psXML.initGameBoard((((new Random()).nextInt(8) + 1) + ""), players.size());
		
		gbTest.setKing(kingName);
	}

	/**
	 * Test get king.
	 */
	@Test
	public void testGetKing() 
	{
		King k = new King(kingName, new City("TestKingCity", 4, "false"));

		assertEquals(k.getName(), gbTest.getKing().getName());
		assertNotNull("Error: King not settet yet", gbTest.getKing());
	}

	/**
	 * Test set king.
	 */
	@Test
	public void testSetKing() 
	{
		King k = new King(kingName, new City("TestKingCity", 4, "false"));
		
		gbTest.setKing(kingName + "2");
		assertEquals(k.getName() + "2", gbTest.getKing().getName());
		assertNotNull("Error: King not settet yet", gbTest.getKing());
	}

	/**
	 * Test get king board.
	 */
	@Test
	public void testGetKingBoard() 
	{
		assertNotNull("Error: KingBoard not settet yet", gbTest.getKingBoard());
	}

	/**
	 * Test get politic cards deck.
	 */
	@Test
	public void testGetPoliticCardsDeck() 
	{
		gbTest.setPoliticCardsDeck(players.size());
		assertNotNull("Error: PoliticCardsDeck not settet yet", gbTest.getPoliticCardsDeck());
	}

	/**
	 * Test set politic cards deck.
	 */
	@Test
	public void testSetPoliticCardsDeck() 
	{
		gbTest.setPoliticCardsDeck(players.size());
		assertNotNull("Error: PoliticCardsDeck not settet yet", gbTest.getPoliticCardsDeck());
	}

	/**
	 * Test get discarded politic cards deck.
	 */
	@Test
	public void testGetDiscardedPoliticCardsDeck() 
	{
		gbTest.setDiscardedPoliticCardsDeck();
		assertNotNull("Error: DiscardedPoliticCardsDeck not settet yet", gbTest.getDiscardedPoliticCardsDeck());
	}

	/**
	 * Test set discarded politic cards deck.
	 */
	@Test
	public void testSetDiscardedPoliticCardsDeck() 
	{
		gbTest.setDiscardedPoliticCardsDeck();
		assertNotNull("Error: DiscardedPoliticCardsDeck not settet yet", gbTest.getDiscardedPoliticCardsDeck());
	}

	/**
	 * Test get region board.
	 */
	@Test
	public void testGetRegionBoard() 
	{
		assertNotNull("Error: RegionBoard not settet yet", gbTest.getRegionBoard());
	}

	/**
	 * Test get city types.
	 */
	@Test
	public void testGetCityTypes() 
	{
		assertNotNull("Error: CityTYpe not settet yet", gbTest.getCityTypes());
	}

	/**
	 * Test put discard deck in politic deck.
	 */
	@Test
	public void testPutDiscardDeckInPoliticDeck() 
	{
		gbTest.setDiscardedPoliticCardsDeck();
		assertNotNull("Error: CityTYpe not settet yet", gbTest.getCityTypes());
	}

	/**
	 * Test draw politic card.
	 */
	@Test
	public void testDrawPoliticCard() 
	{
		gbTest.setPoliticCardsDeck(players.size());
		assertEquals(gbTest.getPoliticCardsDeck().getcards().get(0), gbTest.drawPoliticCard());
		assertNotNull("Error: DiscardedPoliticCardsDeck not settet yet or empty deck", gbTest.drawPoliticCard());
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() 
	{
		assertNotNull("Problem in toString Method", gbTest.toString());
	}

}
