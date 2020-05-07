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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
 * The Class TestChooseABusinessPermitCardToDrawAction.
 */
public class TestChooseABusinessPermitCardToDrawAction {

	/** The test action. */
	private ChooseABusinessPermitCardToDrawAction testAction;
	
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
		System.out.println("Begin Test for the class ChooseABusinessPermitCardToDrowAction");
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
		testAction = new ChooseABusinessPermitCardToDrawAction(testGame);
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
	 * Test check business permit cards deck all.
	 */
	@Test
	public void testCheckBusinessPermitCardsDeckAll(){
		
		assertEquals(true, testAction.checkBusinessPermitCardsDeckAll());
			
	
		
		for(int i =0; i<testGame.getGameBoard().getRegionBoard().size();i++)
		{
			while(testGame.getGameBoard().getRegionBoard().get(i).getBusinessPermitTileOne()!= null)
				{testGame.getGameBoard().getRegionBoard().get(i).drawBusinessPermitTileOne();}
		}
		
		assertEquals(true,testAction.checkBusinessPermitCardsDeckAll());
		
		for(int j=0;j<testGame.getGameBoard().getRegionBoard().size();j++)
		{	
			while(testGame.getGameBoard().getRegionBoard().get(j).getBusinessPermitTileTwo()!= null)
				{testGame.getGameBoard().getRegionBoard().get(j).drawBusinessPermitTileTwo();}
		}
		assertEquals(false, testAction.checkBusinessPermitCardsDeckAll());
	}
	
	/**
	 * Test execute.
	 */
	@Test
	public void testExecute() {
		
		testGame.setCurrentPlayer(player1);
		testAction.execute();
		assertThat(testGame.getGameBoard().getRegionBoard().get(0).getBusinessPermitTileOne(), not(equalTo(businessPermitCard)));
		testAction.setBusinessPermitCard(testGame.getGameBoard().getRegionBoard().get(0).getBusinessPermitTileTwo());
		testAction.execute();
		assertThat(testGame.getGameBoard().getRegionBoard().get(0).getBusinessPermitTileTwo(), not(equalTo(businessPermitCard)));
		
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
		System.out.println("End Test for the class ChooseABusinessPermitCardToDrowAction");
	}

}
