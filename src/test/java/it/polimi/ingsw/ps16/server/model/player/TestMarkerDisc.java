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
package it.polimi.ingsw.ps16.server.model.player;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The Class TestMarkerDisc.
 */
public class TestMarkerDisc {

	/** The marker disc. */
	private MarkerDisc markerDisc;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class MarkerDisc");
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
		markerDisc = new MarkerDisc(2,11,30);
		
	}	
	
	/**
	 * Test get player ID.
	 */
	@Test
	public void testGetPlayerID(){
		
		assertEquals(2,markerDisc.getPlayerID());
		
	}
	
	/**
	 * Test get limit position.
	 */
	@Test
	public void testGetLimitPosition(){
		
		assertEquals(30,markerDisc.getLimitPosition());
		
	}
	
	/**
	 * Test get position.
	 */
	@Test
	public void testGetPosition(){
		
		assertEquals(11,markerDisc.getPosition());
		
	}
	
	/**
	 * Test move back.
	 */
	@Test
	public void testMoveBack(){
		
		markerDisc.moveBack(3);
		assertEquals(8,markerDisc.getPosition());
		
	}
	
	/**
	 * Test move forward.
	 */
	@Test
	public void testMoveForward(){
		
		markerDisc.moveForward(5);;
		assertEquals(16,markerDisc.getPosition());
		
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
		System.out.println("End Test for the class MarkerDisc");
	}
	
}
