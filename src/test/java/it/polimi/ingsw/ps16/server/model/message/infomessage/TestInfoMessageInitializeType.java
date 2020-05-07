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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.message.InfoMessageTypeInitializeEnum;

/**
 * The Class TestInfoMessageInitializeType.
 */
public class TestInfoMessageInitializeType 
{
	
	/** The player number. */
	private int playerNumber;
	
	/** The game number. */
	private int gameNumber;
	
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
		System.out.println("Begin Test for the class InfoMessageInitializeType ");
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
		System.out.println("End Test for the class InfoMessageInitializeType ");
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
		this.playerNumber = 1;
		this.gameNumber = 1;
		
		
	}

	/**
	 * Test get current player ID.
	 */
	@Test
	public void testGetCurrentPlayerID() 
	{
		msg = new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_CONNECTION_CREATED, playerNumber, gameNumber);

		assertEquals(1, this.msg.getCurrentPlayerID());
	}

	/**
	 * Test info message initialize type.
	 */
	@Test
	public void testInfoMessageInitializeType() 
	{
		msg = new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_CONNECTION_CREATED, playerNumber, gameNumber);
		
		assertNotNull("Problem in Constructor Method", msg);
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() 
	{
		msg = new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_CONNECTION_CREATED, playerNumber, gameNumber);
		msg = new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_PLAYER_GAME_NUMBER, playerNumber, gameNumber);
		msg = new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_WAIT_TIMER, playerNumber, gameNumber);
		msg = new InfoMessageInitializeType(InfoMessageTypeInitializeEnum.SHOW_NO_ENOUGH_PLAYER, playerNumber, gameNumber);
		
		assertNotNull("Problem in toString Method", msg.toString());
	}

}
