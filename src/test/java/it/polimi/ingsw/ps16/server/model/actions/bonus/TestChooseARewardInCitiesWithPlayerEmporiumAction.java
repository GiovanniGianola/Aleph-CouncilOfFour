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

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.Game;
import it.polimi.ingsw.ps16.server.model.bonus.MoveMarkerNobilityTrackBonus;
import it.polimi.ingsw.ps16.server.model.gameboard.City;
import it.polimi.ingsw.ps16.server.model.player.Player;

/**
 * The Class TestChooseARewardInCitiesWithPlayerEmporiumAction.
 */
public class TestChooseARewardInCitiesWithPlayerEmporiumAction {

	/** The test action. */
	private ChooseARewardInCitiesWithPlayerEmporiumAction testAction;
	
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
	
	/** The city. */
	private City city;
	
	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class ChooseARewardInCitiesWithPlayerEmporiumAction");
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
		testAction = new ChooseARewardInCitiesWithPlayerEmporiumAction(testGame);
		city = testGame.getGameBoard().getRegionBoard().get(0).getCities().get(0);
		testAction.setCity(city);
	}
	

	/**
	 * Test set city.
	 */
	@Test
	public void testSetCity() {
	
		testAction.setCity(city);
		assertEquals(city,testAction.getCity());
	}

	/**
	 * Test check city.
	 */
	@Test
	public void testCheckCity() {
		
		testGame.setCurrentPlayer(player1);
		assertEquals(false, testAction.checkCity());

		for(int x = 0 ; x < Player.getNumberOfEmporiums() && x < testGame.getGameBoard().getRegionBoard().get(0).getCities().size(); x++)	
		{
			boolean haveNobilityBonus=false;
			
			for(int i =0; i< testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).getBonus().size() && !haveNobilityBonus;i++)
				{	
					if( testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).getBonus().get(i) instanceof MoveMarkerNobilityTrackBonus)
					{	
						haveNobilityBonus = true;									
						i= city.getBonus().size();
						
					}	
					
				}
			if(haveNobilityBonus == true){
				
				testAction.setCity( testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x));
				 testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).buildEmporium(player1.removeEmporium(), testGame.getGameBoard().getKingBoard());
				assertEquals(false,testAction.checkCity());
			}
			else
			{
				testAction.setCity( testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x));
				 testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).buildEmporium(player1.removeEmporium(), testGame.getGameBoard().getKingBoard());
				assertEquals(true,testAction.checkCity());
			}	
	
		}
	}

	/**
	 * Test check cities.
	 */
	@Test
	public void testCheckCities() {
	
		testGame.setCurrentPlayer(player1);
		assertEquals(false,testAction.checkCities());
		player1.removeEmporium();
		assertEquals(true,testAction.checkCities());
	}

	/**
	 * Test check nobility cities.
	 */
	@Test
	public void testCheckNobilityCities() {
		
		testGame.setCurrentPlayer(player1);
		assertEquals(false, testAction.checkNobilityCities());
		
		int badCities = 0;

		for(int x = 0 ; x < Player.getNumberOfEmporiums() && x < testGame.getGameBoard().getRegionBoard().get(0).getCities().size(); x++)	
		{
			boolean haveNobilityBonus=false;
			
			for(int i =0; i< testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).getBonus().size() && !haveNobilityBonus;i++)
				{	
					if( testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).getBonus().get(i) instanceof MoveMarkerNobilityTrackBonus)
					{	
						haveNobilityBonus = true;									
						i= city.getBonus().size();
						
					}	
					
				}
	
			if(haveNobilityBonus == true && badCities>0)
			{
				badCities ++;
				testAction.setCity( testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x));
				 testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).buildEmporium(player1.removeEmporium(), testGame.getGameBoard().getKingBoard());
				assertEquals(true,testAction.checkNobilityCities());
			}
			if(haveNobilityBonus ==false)
			{
				badCities ++;
				testAction.setCity( testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x));
				 testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).buildEmporium(player1.removeEmporium(), testGame.getGameBoard().getKingBoard());
				assertEquals(true,testAction.checkNobilityCities());
			}	
	
		}
	}


	/**
	 * Test check cities bonus.
	 */
	@Test
	public void testCheckCitiesBonus() {
	

		testGame.setCurrentPlayer(player1);
		ArrayList<City> citiesToPass = new ArrayList<City>();
		assertEquals(true,testAction.checkCitiesBonus(citiesToPass));
		
		for(int x = 0 ; x < Player.getNumberOfEmporiums() && x < testGame.getGameBoard().getRegionBoard().get(0).getCities().size(); x++)	
		{
			boolean haveNobilityBonus=false;
			
			for(int i =0; i< testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).getBonus().size() && !haveNobilityBonus;i++)
				{	
					if( testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).getBonus().get(i) instanceof MoveMarkerNobilityTrackBonus)
					{	
						haveNobilityBonus = true;									
						i= city.getBonus().size();
						
					}	
					
				}
			if(haveNobilityBonus == false){
				
				testAction.setCity( testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x));
				 testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x).buildEmporium(player1.removeEmporium(), testGame.getGameBoard().getKingBoard());
				citiesToPass.add(testGame.getGameBoard().getRegionBoard().get(0).getCities().get(x));
			}	
	
		}
		assertEquals(false,testAction.checkCitiesBonus(citiesToPass));
	}

	/**
	 * Test check city bonus.
	 */
	@Test
	public void testCheckCityBonus() {
	

		testGame.setCurrentPlayer(player1);
		ArrayList<City> citiesToPass = new ArrayList<City>();
		citiesToPass.add(testGame.getGameBoard().getRegionBoard().get(0).getCities().get(0));
		citiesToPass.add(testGame.getGameBoard().getRegionBoard().get(0).getCities().get(1));
		testAction.setCity(testGame.getGameBoard().getRegionBoard().get(0).getCities().get(1));
		assertEquals(false,testAction.checkCityBonus(citiesToPass));
		testAction.setCity(testGame.getGameBoard().getRegionBoard().get(0).getCities().get(2));
		assertEquals(true,testAction.checkCityBonus(citiesToPass));
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
		System.out.println("End Test for the class ChooseARewardInCitiesWithPlayerEmporiumAction");
	}

}
