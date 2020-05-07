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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The Class TestBalcony.
 */
public class TestBalcony 
{
	
	/** The dim. */
	private final int DIM = 4;
	
	/** The bal test. */
	private Balcony balTest;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class Balcony");
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
		System.out.println("End Test for the class Balcony");
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
		balTest = new Balcony(DIM);
	}

	/**
	 * Test balcony.
	 */
	@Test
	public void testBalcony() 
	{
		Balcony bal = new Balcony(new Integer(4));
		bal.getBalcony();
		
		assertNotNull("Error: Balcony not setted yet.", balTest);
	}

	/**
	 * Test get balcony.
	 */
	@Test
	public void testGetBalcony() 
	{
		assertNotNull("Error: Balcony not setted yet.", balTest.getBalcony());
	}

	/**
	 * Test elect councillor.
	 */
	@Test
	public void testElectCouncillor() 
	{
		balTest.electCouncillor(new Councillor(Colour.BLACK));
		
		for(int i = 0; i < DIM - 1; i++)
			 balTest.getBalcony().remove();
		
		assertEquals(Colour.BLACK, balTest.getBalcony().remove().getColor());
	}

	/**
	 * Test search councillor presence.
	 */
	@Test
	public void testSearchCouncillorPresence() 
	{
		balTest.electCouncillor(new Councillor(Colour.BLACK));
		
		assertNotNull("Error: Colour absent",balTest.searchCouncillorPresence(Colour.BLACK, 1));
		assertEquals(true, balTest.searchCouncillorPresence(Colour.BLACK, 1));
		
		for(int i = 0; i < DIM; i++)
			 balTest.getBalcony().remove();
		
		assertEquals(false, balTest.searchCouncillorPresence(Colour.BLACK, 1));
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() 
	{
		assertNotNull("Problem in toString Method", balTest.toString());
		
		for(int i = 0; i < DIM; i++)
			 balTest.getBalcony().remove();
		
		assertNotNull("Problem in toString Method", balTest.toString());
	}

}
