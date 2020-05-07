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
package it.polimi.ingsw.ps16.server.model.message.infomessage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeEnum;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestInfoMessageType.
 */
public class TestInfoMessageType 
{
	
	/** The game. */
	Game game;
	
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
	
	/** The msg. */
	private InfoMessage msg;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class InfoMessageType ");
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
		System.out.println("End Test for the class InfoMessageType ");
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
		
		this.game = new Game(plTest, "1", kingName);
	}

	/**
	 * Test get current player ID.
	 */
	@Test
	public void testGetCurrentPlayerID() 
	{
		this.game.setCurrentPlayer(pTest1);
		
		assertEquals(pTest1, this.game.getCurrentPlayer());
	}

	/**
	 * Test info message type info message type enum game.
	 */
	@Test
	public void testInfoMessageTypeInfoMessageTypeEnumGame() 
	{
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_INITIAL_MESSAGE, this.game);
		assertNotNull("Problem in Constructor Method", msg);
		
		this.game = null;
		this.game = null;
		msg = new InfoMessageType("Test", this.game);
		assertNotNull("Problem in Constructor Method", msg);
		
		this.game = null;
		msg = new InfoMessageType("Test", this.game);
		assertNotNull("Problem in Constructor Method", msg);
	}

	/**
	 * Test info message type string game.
	 */
	@Test
	public void testInfoMessageTypeStringGame() 
	{
		msg = new InfoMessageType("Test", this.game);
		assertNotNull("Problem in Constructor Method", msg);
		
		this.game = null;
		
		msg = new InfoMessageType("Test", this.game);
		assertNotNull("Problem in Constructor Method", msg);
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() 
	{
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_INITIAL_MESSAGE, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_GAMEBOARD, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_IS_YOUR_TURN, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_YOUR_TURN_IS_ENDED, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_IS_YOUR_SALE_TURN, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_IS_YOUR_BUY_TURN, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_YOUR_BUY_TURN_IS_ENDED, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_YOUR_SALE_TURN_IS_ENDED, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_MODIFIES_STRING, this.game);
		
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_COUNCILLORS_COLOR, this.game);
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_CURRENT_PLAYER, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_REGION_BALCONIES, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_BALCONIES, this.game);
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_POLITICCARDS, this.game);
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_TRACKS, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_KING_BALCONY, this.game);
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_REGIONS_BUSINESSPERMITCARDS, this.game);
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_REGIONS_BUSINESSPERMITCARDS2, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_REGIONS_BUSINESSPERMITCARDS, this.game);
		
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_PLAYER_BUSINESSPERMITCARDS, this.game);
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_BUSINESSPERMITCARDS_FACEUP, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_ALL_CITIES, this.game);
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_CITIES_WHERE_BUILT_CURRENT_PLAYER, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_REQUEST_SATISFY_KING_COUNCIL, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_REQUEST_SATISFY_COUNCIL_FOR_BUSINESS, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_FINISH_CARDS, this.game);
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_PLAYER_EMPORIUMS, this.game);
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_LAST_LAP, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_MARKET_OBJECTS, this.game);
		
		//msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_RANKING, this.game);
		msg = new InfoMessageType(InfoMessageTypeEnum.SHOW_KING_POSITION, this.game);
		
		assertNotNull("Problem in toString Method", msg.toString());
	}
}
