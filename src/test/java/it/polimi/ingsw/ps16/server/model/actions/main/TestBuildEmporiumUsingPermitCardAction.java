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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.bonus.Bonus;
import it.polimi.ingsw.ps16.server.model.bonus.BonusFactory;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.deck.Deck;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.City;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestBuildEmporiumUsingPermitCardAction.
 */
public class TestBuildEmporiumUsingPermitCardAction {
	
	/** The test action. */
	private BuildEmporiumUsingPermitCardAction testAction;
	
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
	
	/** The b card. */
	private BusinessPermitCard bCard;
	
	/** The city where build. */
	private City cityWhereBuild;
	
	/** The l test. */
	private List<BusinessPermitCard> lTest;
	
	/** The tile 1. */
	private BusinessPermitCard tile1;
	
	/** The b test 1. */
	private List<Bonus> bTest1;
	
	/** The deck test. */
	private Deck<BusinessPermitCard> deckTest; 
	
	/** The b factory. */
	BonusFactory bFactory;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class BuildEmporiumUsingPermitCardAction");
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
		kingName = "GioGippoVale";
		map = "1";
		testGame = new Game(players,map,kingName);
		testAction = new BuildEmporiumUsingPermitCardAction(testGame);
		bCard =testGame.getGameBoard().getRegionBoard().get(1).getBusinessPermitTileOne();
		testAction.setBusinessPermitCard( bCard);
		cityWhereBuild = bCard.getCities().get(0);
		testAction.setCity(cityWhereBuild);
		
		bFactory = new BonusFactory();
		
		bTest1 = new ArrayList<>();
		bTest1.add(bFactory.createBonus("assistantbonus", (new Random()).nextInt(5)));
		bTest1.add(bFactory.createBonus("businesspermitcardrewardbonus", 1));
		bTest1.add(bFactory.createBonus("coinbonus", 5));
		bTest1.add(bFactory.createBonus("drawpoliticcardbonus", 1));
		bTest1.add(bFactory.createBonus("movemarkernobilitytrackbonus", 1));
		bTest1.add(bFactory.createBonus("victorypointbonus", 10));
		bTest1.add(bFactory.createBonus("drawbusinesspermitcardbonus", 1));
		

		lTest = new ArrayList<>();
		lTest.add(new BusinessPermitCard(bTest1,bCard.getCities()));
		
		deckTest = new Deck<BusinessPermitCard>(lTest);
		tile1 =  deckTest.getcards().get(0);
	}
	
	/**
	 * Test set business permit card.
	 */
	@Test
	public void testSetBusinessPermitCard(){
		
		testAction.setBusinessPermitCard(testGame.getGameBoard().getRegionBoard().get(1).getBusinessPermitTileOne());
		assertEquals(testGame.getGameBoard().getRegionBoard().get(1).getBusinessPermitTileOne(),testAction.getBusinessPermitCard());
		
	}
	
	/**
	 * Test set city.
	 */
	@Test
	public void testSetCity(){
		
		testAction.setCity(bCard.getCities().get(0));
		assertEquals(bCard.getCities().get(0),testAction.getCity());
	}
	
	/**
	 * Test check business permit cards.
	 */
	@Test
	public void testCheckBusinessPermitCards(){
		
		testGame.setCurrentPlayer(player1);
		assertEquals(false,testAction.checkBusinessPermitCards());
		player1.addBusinessPermitCard(bCard);
		assertEquals(true,testAction.checkBusinessPermitCards());
	}
	
	/**
	 * Test check city.
	 */
	@Test
	public void testCheckCity(){
		
		assertEquals(true,testAction.checkCity());
		bCard =testGame.getGameBoard().getRegionBoard().get(0).getBusinessPermitTileOne();
		testAction.setBusinessPermitCard( bCard);
		assertEquals(false,testAction.checkCity());
	}
	
	/**
	 * Test check assistants.
	 */
	@Test
	public void testCheckAssistants(){
		
		testGame.setCurrentPlayer(player1);
		assertEquals(true,testAction.checkAssistants());
	}
	
	/**
	 * Test check emporium.
	 */
	@Test
	public void testCheckEmporium(){
		
		testGame.setCurrentPlayer(player1);
		assertEquals(true,testAction.checkEmporium());
		
	}
	
	/**
	 * Test check emporiums.
	 */
	@Test
	public void testCheckEmporiums(){
		
		testGame.setCurrentPlayer(player1);
		assertEquals(true,testAction.checkEmporiums());
		while(!testGame.getCurrentPlayer().getEmporiumStack().isEmpty())
			testGame.getCurrentPlayer().removeEmporium();
		assertEquals(false,testAction.checkEmporiums());
	}
	
	/**
	 * Test execute.
	 *
	 * @throws SuspendPlayerException
	 *             the suspend player exception
	 */
	@Test
	public void testExecute() throws SuspendPlayerException
	{	
		testGame.setCurrentPlayer(player2);
		player2.addBusinessPermitCard(tile1);
		
		cityWhereBuild = bCard.getCities().get(0);
		testAction.setCity(cityWhereBuild);
		
		testAction.setBusinessPermitCard(player2.getBusinessPermitCards().get(0));
		
		int beforeSize = Player.getNumberOfEmporiums();
		
		testAction.execute();
		
		assertEquals(1, beforeSize - player2.getEmporiumStack().size());
		
		player2.addBusinessPermitCard(tile1);
		
		testAction.setCity(cityWhereBuild.getNearCities().get(1));
		
		testAction.setBusinessPermitCard(player2.getBusinessPermitCards().get(0));
		
		testAction.execute();
		
		assertEquals(2, beforeSize - player2.getEmporiumStack().size());
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
		System.out.println("End Test for the class BuildEmporiumUsingPermitCardAction");
	}
}
