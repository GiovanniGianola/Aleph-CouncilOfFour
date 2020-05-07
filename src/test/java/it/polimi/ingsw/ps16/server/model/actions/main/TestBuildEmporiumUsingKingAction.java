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
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.exception.SuspendPlayerException;
import it.polimi.ingsw.ps16.server.model.gameboard.Balcony;
import it.polimi.ingsw.ps16.server.model.gameboard.City;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;
import it.polimi.ingsw.ps16.server.model.gameboard.Councillor;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestBuildEmporiumUsingKingAction.
 */
public class TestBuildEmporiumUsingKingAction {

	/** The test action. */
	private BuildEmporiumUsingKingAction testAction;

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
	
	/** The city where build. */
	private City cityWhereBuild;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class BuildEmporiumUsingKingAction");
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
		testAction = new BuildEmporiumUsingKingAction(testGame);
		cityWhereBuild = testGame.getGameBoard().getKing().getCity().getNearCities().get(0);
		testAction.setCity(cityWhereBuild);
	}
	
	
	
	/**
	 * Test set politic card list.
	 */
	@Test
	public void testSetPoliticCardList(){
		
		ArrayList<PoliticCard> cards = new ArrayList<PoliticCard>();
		cards.add(new PoliticCard(Colour.BLACK));
		cards.add(new PoliticCard(Colour.JOLLY));
		cards.add(new PoliticCard(Colour.WHITE));
		testAction.setPoliticCardsList(cards);
		assertEquals(cards,testAction.getPoliticCardsList());
	}
	
	/**
	 * Test set city.
	 */
	@Test
	public void testSetCity(){
		
		testAction.setCity(testGame.getGameBoard().getRegionBoard().get(0).getCities().get(0));
		assertEquals(testGame.getGameBoard().getRegionBoard().get(0).getCities().get(0),testAction.getCity());
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
	 * Test check player cards and king balcony.
	 */
	@Test
	public void testCheckPlayerCardsAndKingBalcony(){
		
		testGame.setCurrentPlayer(player1);
		assertEquals(false, testAction.checkPlayerCardsAndKingBalcony());
		player1.addPoliticCard(new PoliticCard(Colour.BLACK));
		player1.addPoliticCard(new PoliticCard(Colour.BLUE));
		player1.addPoliticCard(new PoliticCard(Colour.JOLLY));
		player1.addPoliticCard(new PoliticCard(Colour.ORANGE));
		player1.addPoliticCard(new PoliticCard(Colour.PINK));
		player1.addPoliticCard(new PoliticCard(Colour.PURPLE));
		player1.addPoliticCard(new PoliticCard(Colour.WHITE));
		player1.addPoliticCard(new PoliticCard(Colour.JOLLY));
		assertEquals(true,testAction.checkPlayerCardsAndKingBalcony());
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
		for(Councillor x: testGame.getGameBoard().getKingBoard().getBalcony().getBalcony())
		{
			trueCards.add(new PoliticCard(x.getColor()));
		}
		assertEquals(true,testAction.checkBalconyColors(trueCards));	
	}
	
	/**
	 * Test check money for cards.
	 */
	@Test
	public void testCheckMoneyForCards(){
		
		testGame.setCurrentPlayer(player3);
		ArrayList<PoliticCard> cards = new ArrayList<PoliticCard>();
		cards.add(new PoliticCard(Colour.JOLLY));
		assertEquals(false,testAction.checkMoneyForCards(cards));
		cards.add(new PoliticCard(Colour.BLACK));
		assertEquals(true,testAction.checkMoneyForCards(cards));
		cards.add(new PoliticCard(Colour.WHITE));
		assertEquals(true,testAction.checkMoneyForCards(cards));
		
	}
	
	/**
	 * Test check money for king and cards.
	 */
	@Test
	public void testCheckMoneyForKingAndCards(){
		
		testGame.setCurrentPlayer(player3);
		ArrayList<PoliticCard> cards = new ArrayList<PoliticCard>();
		cards.add(new PoliticCard(Colour.JOLLY));
		assertEquals(false,testAction.checkMoneyForKingAndCards(cards));
		cards.add(new PoliticCard(Colour.BLACK));
		assertEquals(true,testAction.checkMoneyForKingAndCards(cards));
		cards.add(new PoliticCard(Colour.WHITE));
		assertEquals(true,testAction.checkMoneyForKingAndCards(cards));
		testAction.setCity(testGame.getGameBoard().getKing().getCity());
		assertEquals(true,testAction.checkMoneyForKingAndCards(cards));
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
		List<PoliticCard> cards = new ArrayList<>();
		Balcony b = testGame.getGameBoard().getKingBoard().getBalcony();
		
		player2.addPoliticCard(new PoliticCard(b.getBalcony().peek().getColor()));
		
		cards.add(player2.getPoliticCardsList().get(player2.getPoliticCardsList().size()-1));
		testAction.setPoliticCardsList(cards);
		
		int beforeSize = Player.getNumberOfEmporiums();
		
		testAction.execute();
		
		assertEquals(1, beforeSize - player2.getEmporiumStack().size());
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
		System.out.println("End Test for the class BuildEmporiumUsingKingAction");
	}
}
