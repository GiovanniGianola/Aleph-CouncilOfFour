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

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The Class TestColour.
 */
public class TestColour 
{
	
	/** The color. */
	private String color;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class Colour");
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
		System.out.println("End Test for the class Colour");
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
		
	}

	/**
	 * Test search color by name.
	 */
	@Test
	public void testSearchColorByName() 
	{
		color = "Black";
		assertNotNull( Colour.searchColorByName(color) );
		
		color = "Blue";
		assertNotNull( Colour.searchColorByName(color) );
		
		color = "Purple";
		assertNotNull( Colour.searchColorByName(color) );
		
		color = "Orange";
		assertNotNull( Colour.searchColorByName(color) );
		
		color = "White";
		assertNotNull( Colour.searchColorByName(color) );
		
		color = "Pink";
		assertNotNull( Colour.searchColorByName(color) );
		
		color = "Jolly";
		assertNotNull( Colour.searchColorByName(color) );
	}

}
