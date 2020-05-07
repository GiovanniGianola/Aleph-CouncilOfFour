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
package it.polimi.ingsw.ps16.server.model.market;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.player.AssistantsHeap;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestMarketObject.
 */
public class TestMarketObject {

	/** The player 1. */
	private Player player1;
	
	/** The market object. */
	private MarketObject marketObject;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class MarketObject");
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
		player1 = new Player(1,10,10);
		marketObject = new MarketObject();
		
	}
	
	/**
	 * Test set seller.
	 */
	@Test
	public void testSetSeller(){
		 
		marketObject.setSeller(player1);
		assertEquals(player1,marketObject.getSeller());
		
	}
	

	/**
	 * Test set for sale object.
	 */
	@Test
	public void testSetForSaleObject(){
		
		PoliticCard forSaleObject = new PoliticCard(Colour.BLACK);
		marketObject.setForSaleObject(forSaleObject);
		assertEquals(forSaleObject,marketObject.getForSaleObject());
		
	} 
	
	/**
	 * Test set price.
	 */
	@Test
	public void testSetPrice(){
		
		int price = 3;
		marketObject.setPrice(price);
		assertEquals(price,marketObject.getPrice());
		
	} 
	
	/**
	 * Test to string.
	 */
	@Test
	public void testToString() 
	{
		marketObject.setSeller(player1);
		AssistantsHeap price = new AssistantsHeap(3);
		marketObject.setPrice(price);
		PoliticCard forSaleObject = new PoliticCard(Colour.BLACK);
		marketObject.setForSaleObject(forSaleObject);
		assertNotNull("Problem in toString Method", marketObject.toString());
		
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
		System.out.println("End Test for the class MarketObject");
	}
	
}