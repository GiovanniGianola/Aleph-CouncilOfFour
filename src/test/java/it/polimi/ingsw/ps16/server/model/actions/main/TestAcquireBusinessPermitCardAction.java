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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.gameboard.Balcony;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.gameboard.Councillor;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestAcquireBusinessPermitCardAction.
 */
public class TestAcquireBusinessPermitCardAction {

	/** The test action. */
	private AcquireBusinessPermitCardAction testAction;
	
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
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class AcquireBusinessPermitCardAction");
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
		kingName = "Pradella";
		map = "3";
		testGame = new Game(players,map,kingName);
		testAction = new AcquireBusinessPermitCardAction(testGame);
		bCard =testGame.getGameBoard().getRegionBoard().get(1).getBusinessPermitTileOne();
		testAction.setBusinessPermitCard( bCard);
	}	
	
	/**
	 * Testcheck player cards.
	 */
	@Test
	public void testcheckPlayerCards(){
		
		testGame.setCurrentPlayer(player1);
		assertEquals(false, testAction.checkPlayerCards());
		PoliticCard politicCard1 = new PoliticCard(Colour.BLACK);
		player1.addPoliticCard(politicCard1);
		assertEquals(true, testAction.checkPlayerCards());
		
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
	 * Test check player cards and regions balcony.
	 */
	@Test
	public void testCheckPlayerCardsAndRegionsBalcony(){
		
		testGame.setCurrentPlayer(player1);
		assertEquals(false, testAction.checkPlayerCardsAndRegionsBalcony());
		player1.addPoliticCard(new PoliticCard(Colour.BLACK));
		player1.addPoliticCard(new PoliticCard(Colour.BLUE));
		player1.addPoliticCard(new PoliticCard(Colour.JOLLY));
		player1.addPoliticCard(new PoliticCard(Colour.ORANGE));
		player1.addPoliticCard(new PoliticCard(Colour.PINK));
		player1.addPoliticCard(new PoliticCard(Colour.PURPLE));
		player1.addPoliticCard(new PoliticCard(Colour.WHITE));
		player1.addPoliticCard(new PoliticCard(Colour.JOLLY));
		assertEquals(true,testAction.checkPlayerCardsAndRegionsBalcony());
	}
	
	/**
	 * Test check player cards and region choose balcony.
	 */
	@Test
	public void testCheckPlayerCardsAndRegionChooseBalcony(){
		
		testGame.setCurrentPlayer(player2);
		assertEquals(false, testAction.checkPlayerCardsAndRegionChooseBalcony());
		player2.addPoliticCard(new PoliticCard(Colour.BLACK));
		player2.addPoliticCard(new PoliticCard(Colour.BLUE));
		player2.addPoliticCard(new PoliticCard(Colour.JOLLY));
		player2.addPoliticCard(new PoliticCard(Colour.ORANGE));
		player2.addPoliticCard(new PoliticCard(Colour.PINK));
		player2.addPoliticCard(new PoliticCard(Colour.PURPLE));
		player2.addPoliticCard(new PoliticCard(Colour.WHITE));
		player2.addPoliticCard(new PoliticCard(Colour.JOLLY));
		assertEquals(true,testAction.checkPlayerCardsAndRegionChooseBalcony());
	}
	
	/**
	 * Testcheck card ownership.
	 */
	@Test
	public void testcheckCardOwnership(){
		
		ArrayList<PoliticCard> cards = new ArrayList<PoliticCard>();
		cards.add(new PoliticCard(Colour.BLACK));
		cards.add(new PoliticCard(Colour.JOLLY));
		cards.add(new PoliticCard(Colour.WHITE));
		testGame.setCurrentPlayer(player3);
		assertEquals(false,testAction.checkCardOwnership(cards));
		player3.addPoliticCard(new PoliticCard(Colour.BLACK));
		player3.addPoliticCard(new PoliticCard(Colour.JOLLY));
		player3.addPoliticCard(new PoliticCard(Colour.WHITE));
		assertEquals(true, testAction.checkCardOwnership(cards));
	}
	
	/**
	 * Testcheck balcony colors.
	 */
	@Test
	public void testcheckBalconyColors(){
		
		testGame.setCurrentPlayer(player1);
		ArrayList<PoliticCard> cards = new ArrayList<PoliticCard>();
		cards.add(new PoliticCard(Colour.BLACK));
		cards.add(new PoliticCard(Colour.BLACK));
		cards.add(new PoliticCard(Colour.BLACK));
		cards.add(new PoliticCard(Colour.BLACK));
		assertEquals(false,testAction.checkBalconyColors(cards));
		ArrayList<PoliticCard> trueCards = new ArrayList<PoliticCard>();
		for(Councillor x: bCard.getCities().get(0).getRegionBoard().getBalcony().getBalcony())
		{
			trueCards.add(new PoliticCard(x.getColor()));
		}
		assertEquals(true,testAction.checkBalconyColors(trueCards));	
	}
	
	/**
	 * Test check money.
	 */
	@Test
	public void testCheckMoney(){
		
		testGame.setCurrentPlayer(player3);
		ArrayList<PoliticCard> cards = new ArrayList<PoliticCard>();
		cards.add(new PoliticCard(Colour.JOLLY));
		assertEquals(false,testAction.checkMoney(cards));
		cards.add(new PoliticCard(Colour.BLACK));
		assertEquals(true,testAction.checkMoney(cards));
		cards.add(new PoliticCard(Colour.WHITE));
		assertEquals(true,testAction.checkMoney(cards));
		
	}
	
	/**
	 * Test execute.
	 */
	@Test
	public void testExecute()
	{	
		testGame.setCurrentPlayer(player2);
		List<PoliticCard> cards = new ArrayList<>();
		Balcony b = testGame.getGameBoard().getRegionBoard().get(0).getBalcony();
		
		player2.addPoliticCard(new PoliticCard(b.getBalcony().peek().getColor()));
		
		cards.add(player2.getPoliticCardsList().get(player2.getPoliticCardsList().size()-1));
		testAction.setPoliticCardsList(cards);
		
		BusinessPermitCard businessCard = testGame.getGameBoard().getRegionBoard().get(0).getBusinessPermitTileOne();
		if(!businessCard.getBonus().get(0).toString().equalsIgnoreCase("mainactionbonus"))
			testAction.setBusinessPermitCard(businessCard);
		else
			testAction.setBusinessPermitCard(testGame.getGameBoard().getRegionBoard().get(0).getBusinessPermitTileTwo());
		
		int beforeSize = player2.getBusinessPermitCards().size();
		
		testAction.execute();
		
		assertEquals(1,player2.getBusinessPermitCards().size() - beforeSize);
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
		System.out.println("End Test for the class AcquireBusinessPermitCardAction");
	}
	
}
