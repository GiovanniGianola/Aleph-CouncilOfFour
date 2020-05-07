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

import static org.junit.Assert.assertNotNull;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.gameboard.GameBoard;


/**
 * The Class TestParseStreamXML.
 */
public class TestParseStreamXML 
{
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class ParseStreamXML");
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
		System.out.println("End Test for the class ParseStreamXML");
	}

	/**
	 * Test parse stream XML.
	 */
	@Test
	public void testParseStreamXML() 
	{
		assertNotNull("Error: object not initializated", new ParseStreamXML(new GameBoard()));
	}

	/**
	 * Test init game board.
	 */
	@Test
	public void testInitGameBoard() 
	{
		Random r = new Random();
		String mapNumber = r.nextInt(8)+1 + "";
		int numberOfPlayers = r.nextInt(4);
		
		assertNotNull("Error: object not initializated", (new ParseStreamXML(new GameBoard()).initGameBoard(mapNumber, numberOfPlayers)));
	}

}
