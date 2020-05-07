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
package it.polimi.ingsw.ps16.server.model.actions.main;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.actions.ActionType;

/**
 * The Class TestActionType.
 */
public class TestActionType 
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
		System.out.println("Begin Test for the class ActionType");
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
		System.out.println("End Test for the class ActionType");
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
	 * Test get action type.
	 */
	@Test
	public void testGetActionType() 
	{
		assertNotNull(ActionType.ADDMAIN.getActionType());
		assertNotNull(ActionType.ACQUIRE.getActionType());
		assertNotNull(ActionType.ASSISTANTS.getActionType());
		assertNotNull(ActionType.BACK.getActionType());
		assertNotNull(ActionType.BUILDKING.getActionType());
		assertNotNull(ActionType.BUILDPERMIT.getActionType());
		assertNotNull(ActionType.BUSINESSPERMITCARD.getActionType());
		assertNotNull(ActionType.CHANGE.getActionType());
		assertNotNull(ActionType.COINS.getActionType());
		assertNotNull(ActionType.ELECT.getActionType());
		
		assertNotNull(ActionType.ELECTASSISTANT.getActionType());
		assertNotNull(ActionType.END.getActionType());
		assertNotNull(ActionType.ENGAGEASSISTANT.getActionType());
		assertNotNull(ActionType.MAIN.getActionType());
		assertNotNull(ActionType.NOTHING.getActionType());
		assertNotNull(ActionType.POLITICCARD.getActionType());
		assertNotNull(ActionType.QUICK.getActionType());
		assertNotNull(ActionType.STOP.getActionType());
	}

}
