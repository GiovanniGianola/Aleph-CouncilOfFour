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
 * The Class TestAssistantsHeap.
 */
public class TestAssistantsHeap {

	/** The assistants heap. */
	private AssistantsHeap assistantsHeap;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class AssistantsHeap");
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
		assistantsHeap = new AssistantsHeap(5);
	}	
	
	
	/**
	 * Test get assistants.
	 */
	@Test
	public void testGetAssistants(){
		
		assertEquals(5, assistantsHeap.getAssistants());
		
	}
	
	/**
	 * Test add assistants.
	 */
	@Test
	public void testAddAssistants(){
	
		assistantsHeap.addAssistants(13);
		assertEquals(18,assistantsHeap.getAssistants());
	}
	
	/**
	 * Test remove assistants.
	 */
	@Test
	public void testRemoveAssistants(){
		
		assistantsHeap.removeAssistants(3);
		assertEquals(2,assistantsHeap.getAssistants());
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
		System.out.println("End Test for the class AssistantsHeap");
	}
}
