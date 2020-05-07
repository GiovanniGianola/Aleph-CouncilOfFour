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
 * The Class TestCity.
 */
public class TestCity 
{
	
	/** The c test. */
	private City cTest;
	
	/** The name test. */
	private String nameTest;
	
	/** The n player. */
	private int nPlayer;
	
	/** The bonus bool. */
	private String bonusBool;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class City");
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
		System.out.println("End Test for the class City");
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
		this.bonusBool = "true";
		this.nameTest = "Milan";
		this.nPlayer = 4;
		cTest = new City(this.nameTest, this.nPlayer, this.bonusBool );
	}

	/**
	 * Test get emporiums.
	 */
	@Test
	public void testGetEmporiums() 
	{
		assertEquals(this.nPlayer, cTest.getEmporiums().length);
	}

	/**
	 * Test get region board.
	 */
	@Test
	public void testGetRegionBoard() 
	{
		cTest.setRegionBoard(new RegionBoard("Lombardy"));
		
		assertEquals("Lombardy", cTest.getRegionBoard().getName());
		assertNotNull("Error: Region not setted yet.", cTest.getRegionBoard());
	}

	/**
	 * Test get type.
	 */
	@Test
	public void testGetType() 
	{
		cTest.setType(new CityType("TOP"));
		
		assertEquals("TOP", cTest.getType().getName());
		assertNotNull("Error: CityType not setted yet.", cTest.getType());
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() 
	{
		assertNotNull("Problem in toString Method", cTest.toString());
		
		cTest.setRegionBoard(new RegionBoard("Lombardy"));
		
		assertNotNull("Problem in toString Method", cTest.toString());
		
		cTest.setType(new CityType("TOP"));
		
		assertNotNull("Problem in toString Method", cTest.toString());
	}

}
