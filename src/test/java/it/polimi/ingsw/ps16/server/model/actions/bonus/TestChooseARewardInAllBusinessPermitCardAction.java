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
package it.polimi.ingsw.ps16.server.model.actions.bonus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestChooseARewardInAllBusinessPermitCardAction.
 */
public class TestChooseARewardInAllBusinessPermitCardAction {
	
	/** The test action. */
	private ChooseARewardInAllBusinessPermitCardAction testAction;
	
	/** The test game. */
	private Game testGame;
	
	/** The player 1. */
	private Player player1;
	
	/** The player 2. */
	private Player player2;
	
	/** The player 3. */
	private Player player3;
	
	/** The king name. */
	private String kingName;
	
	/** The players. */
	private List<Player> players;
	
	/** The map. */
	private String map;
	
	/** The business permit card. */
	private BusinessPermitCard businessPermitCard;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class ChooseARewardInAllBusinessPermitCardAction");
	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception{

		players = new ArrayList<>();
		player1 = new Player(1,10,10);
		players.add(player1);
		player2 = new Player(2,100,100);
		players.add(player2);
		player3 = new Player(3,10,10);
		players.add(player3);
		kingName = "Aleph";
		map = "3";
		testGame = new Game(players,map,kingName);
		testAction = new ChooseARewardInAllBusinessPermitCardAction(testGame);
		businessPermitCard = testGame.getGameBoard().getRegionBoard().get(0).getBusinessPermitTileOne();
		testAction.setBusinessPermitCard(businessPermitCard);
		
	}

	/**
	 * Test set business permit card.
	 */
	@Test
	public void testSetBusinessPermitCard() {
		
		testAction.setBusinessPermitCard(businessPermitCard);
		assertEquals(businessPermitCard, testAction.getBusinessPermitCard());
	}

	/**
	 * Test check business permit cards.
	 */
	@Test
	public void testCheckBusinessPermitCards() {
		
		testGame.setCurrentPlayer(player1);
		assertEquals(false,testAction.checkBusinessPermitCards());
		player1.addBusinessPermitCard(businessPermitCard);
		assertEquals(true,testAction.checkBusinessPermitCards());
	}
	
	/**
	 * Test execute.
	 */
	@Test
	public void testExecute() {
		
		testGame.setCurrentPlayer(player3);
		testAction.execute();
		assertNotNull("no bonus",player3.getAssistants().getAssistants());
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
		System.out.println("End Test for the class ChooseARewardInAllBusinessPermitCardAction");
	}
}
