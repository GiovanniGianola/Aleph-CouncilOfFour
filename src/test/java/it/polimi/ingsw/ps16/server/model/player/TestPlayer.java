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
import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps16.server.model.bonus.Bonus;
import it.polimi.ingsw.ps16.server.model.bonus.BonusFactory;
import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.deck.PoliticCard;
import it.polimi.ingsw.ps16.server.model.gameboard.City;
import it.polimi.ingsw.ps16.server.model.gameboard.Colour;


/**
 * The Class TestPlayer.
 */
public class TestPlayer 
{
	
	/** The p test 1. */
	private Player pTest1;
	
	/** The p test 2. */
	private Player pTest2;
	
	/** The p test 3. */
	private Player pTest3;
	
	/** The b test 1. */
	private List<Bonus> bTest1;
	
	/** The b test 2. */
	private List<Bonus> bTest2;
	
	/** The b test 3. */
	private List<Bonus> bTest3;
	
	/** The b test 4. */
	private List<Bonus> bTest4;
	
	/** The c test 1. */
	private List<City> cTest1;
	
	/** The c test 2. */
	private List<City> cTest2;
	
	/** The c test 3. */
	private List<City> cTest3;
	
	/** The c test 4. */
	private List<City> cTest4;
	
	/** The b factory. */
	private BonusFactory bFactory;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("Begin Test for the class Player");
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
		System.out.println("End Test for the class Player");
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
		pTest1 = new Player(1,10,10);
		pTest2 = new Player(2,10,10);
		pTest3 = new Player(3,10,10);
		
		bFactory = new BonusFactory();
		
		bTest1 = new ArrayList<>();
		bTest1.add(bFactory.createBonus("assistantbonus", (new Random()).nextInt(5)));
		bTest2 = new ArrayList<>();
		bTest2.add(bFactory.createBonus("coinbonus", (new Random()).nextInt(5)));
		bTest3 = new ArrayList<>();
		bTest3.add(bFactory.createBonus("drawpoliticcardbonus", (new Random()).nextInt(5)));
		bTest4 = new ArrayList<>();
		bTest4.add(bFactory.createBonus("assistantbonus", (new Random()).nextInt(5)));
		
		cTest1 = new ArrayList<>();
		cTest1.add(new City("CityTest1",4,"true"));
		cTest2 = new ArrayList<>();
		cTest2.add(new City("CityTest2",4,"true"));
		cTest3 = new ArrayList<>();
		cTest3.add(new City("CityTest3",4,"true"));
		cTest4 = new ArrayList<>();
		cTest4.add(new City("CityTest4",4,"true"));
	}

	/**
	 * Test get player ID.
	 */
	@Test
	public void testGetPlayerID() 
	{
		assertEquals(1, pTest1.getPlayerID());
		assertEquals(2, pTest2.getPlayerID());
		assertEquals(3, pTest3.getPlayerID());
	}

	/**
	 * Test get name.
	 */
	@Test
	public void testGetName() 
	{
		pTest1.setName("Giovanni");
		pTest2.setName("Filippo");
		pTest3.setName("Valentina");

		assertEquals("Giovanni", pTest1.getName());
		assertEquals("Filippo", pTest2.getName());
		assertEquals("Valentina", pTest3.getName());
	}

	/**
	 * Test set name.
	 */
	@Test
	public void testSetName() 
	{
		pTest1.setName("Giovanni");
		pTest2.setName("Filippo");
		pTest3.setName("Valentina");

		assertEquals("Giovanni", pTest1.getName());
		assertEquals("Filippo", pTest2.getName());
		assertEquals("Valentina", pTest3.getName());
	}

	/**
	 * Test get color.
	 */
	@Test
	public void testGetColor() 
	{
		pTest1.setColor(Color.BLUE);
		pTest2.setColor(Color.GREEN);
		pTest3.setColor(Color.PINK);
		
		assertEquals(Color.BLUE, pTest1.getColor());
		assertEquals(Color.GREEN, pTest2.getColor());
		assertEquals(Color.PINK, pTest3.getColor());
	}

	/**
	 * Test set color.
	 */
	@Test
	public void testSetColor() 
	{
		pTest1.setColor(Color.BLUE);
		pTest2.setColor(Color.GREEN);
		pTest3.setColor(Color.PINK);
		
		assertEquals(Color.BLUE, pTest1.getColor());
		assertEquals(Color.GREEN, pTest2.getColor());
		assertEquals(Color.PINK, pTest3.getColor());
	}

	/**
	 * Test get emporium stack.
	 */
	@Test
	public void testGetEmporiumStack() 
	{
		assertNotNull("Error: EmporiumStack not settet yet", pTest1.getEmporiumStack());
		assertNotNull("Error: EmporiumStack not settet yet", pTest2.getEmporiumStack());
		assertNotNull("Error: EmporiumStack not settet yet", pTest3.getEmporiumStack());
	}

	/**
	 * Test get assistants.
	 */
	@Test
	public void testGetAssistants() 
	{
		assertNotNull("Error: AssistantHeap not settet yet", pTest1.getAssistants());
		assertNotNull("Error: AssistantHeap not settet yet", pTest2.getAssistants());
		assertNotNull("Error: AssistantHeap not settet yet", pTest3.getAssistants());
	}

	/**
	 * Test get politic cards list.
	 */
	@Test
	public void testGetPoliticCardsList() 
	{
		pTest1.getPoliticCardsList().add(new PoliticCard(Colour.WHITE));
		pTest1.getPoliticCardsList().add(new PoliticCard(Colour.BLACK));
		pTest1.getPoliticCardsList().add(new PoliticCard(Colour.JOLLY));

		assertNotNull("Error: PoliticCardsList not settet yet", pTest1.getPoliticCardsList());
	}

	/**
	 * Test get business permit cards.
	 */
	@Test
	public void testGetBusinessPermitCards() 
	{
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest1,cTest1));
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest2,cTest2));
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest3,cTest3));
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest4,cTest4));
		
		assertEquals((new BusinessPermitCard(bTest1,cTest1)).getClass(), pTest1.getBusinessPermitCards().get(0).getClass());
		assertNotNull("Error: BusinessPermitCard not added yet", pTest1.getBusinessPermitCards());
	}

	/**
	 * Test get business permit cards face down.
	 */
	@Test
	public void testGetBusinessPermitCardsFaceDown() 
	{
		pTest1.getBusinessPermitCardsFaceDown().add(new BusinessPermitCard(bTest1,cTest1));
		pTest1.getBusinessPermitCardsFaceDown().add(new BusinessPermitCard(bTest2,cTest2));
		
		assertEquals((new BusinessPermitCard(bTest1,cTest1)).getClass(), pTest1.getBusinessPermitCardsFaceDown().get(0).getClass());
		assertNotNull("Error: BusinessPermitCardFaceDown not added yet", pTest1.getBusinessPermitCardsFaceDown());
	}

	/**
	 * Test get bonus tiles.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetBonusTiles() throws Exception 
	{
		BonusTile tile1 = new BonusTile(bFactory.createBonus("victorypointbonus", (new Random()).nextInt(5)));
		BonusTile tile2 = new BonusTile(bFactory.createBonus("victorypointbonus", (new Random()).nextInt(5)));
		
		List<BonusTile> tileList = new ArrayList<>();
		tileList.add(tile1);
		tileList.add(tile2);
		
		pTest1.addBonusTiles(tileList);
		
		assertEquals(tile1, pTest1.getBonusTiles().get(0));
		assertNotNull("Error: the player has no bonus tile.", pTest1.getBonusTiles());
	}

	/**
	 * Test remove emporium.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testRemoveEmporium() throws Exception 
	{
		for(int i = 0; i < Player.getNumberOfEmporiums(); i++)
			assertNotNull("Error: the EmporiumStack is empty.", pTest1.removeEmporium());
	}

	/**
	 * Test add politic card.
	 */
	@Test
	public void testAddPoliticCard() 
	{
		int size = pTest1.getPoliticCardsList().size();
		pTest1.addPoliticCard(new PoliticCard(Colour.ORANGE));
		
		assertEquals(size + 1 , pTest1.getPoliticCardsList().size());
	}

	/**
	 * Test search cards existence.
	 */
	@Test
	public void testSearchCardsExistence() 
	{
		pTest1.getPoliticCardsList().add(new PoliticCard(Colour.BLUE));
		pTest1.getPoliticCardsList().add(new PoliticCard(Colour.WHITE));
		
		assertEquals(true , pTest1.searchCardsExistence(Colour.BLUE, 1));
		assertEquals(false , pTest1.searchCardsExistence(Colour.PURPLE, 1));
	}

	/**
	 * Test discard cards.
	 */
	@Test
	public void testDiscardCards() 
	{
		pTest1.getPoliticCardsList().add(new PoliticCard(Colour.BLUE));
		pTest1.getPoliticCardsList().add(new PoliticCard(Colour.JOLLY));
		pTest1.getPoliticCardsList().add(new PoliticCard(Colour.BLUE));
		pTest1.getPoliticCardsList().add(new PoliticCard(Colour.WHITE));
		int size = pTest1.getPoliticCardsList().size();
		
		List<Colour> colorArray = new ArrayList<>();
		colorArray.add(Colour.JOLLY);
		colorArray.add(Colour.WHITE);
		
		pTest1.discardCards(colorArray);
		
		assertEquals(size - 2 , pTest1.getPoliticCardsList().size());
	}

	/**
	 * Test add business permit card.
	 */
	@Test
	public void testAddBusinessPermitCard() 
	{
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest1,cTest1));
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest2,cTest2));
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest3,cTest3));
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest4,cTest4));
		
		assertEquals((new BusinessPermitCard(bTest1,cTest1)).getClass(), pTest1.getBusinessPermitCards().get(0).getClass());
		assertNotNull("Error: BusinessPermitCard not added yet", pTest1.getBusinessPermitCards());
	}

	/**
	 * Test turn upside down business permit card.
	 */
	@Test
	public void testTurnUpsideDownBusinessPermitCard() 
	{
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest1,cTest1));
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest2,cTest2));
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest3,cTest3));
		pTest1.addBusinessPermitCard(new BusinessPermitCard(bTest4,cTest4));
		int size = pTest1.getBusinessPermitCards().size();
		
		pTest1.turnUpsideDownBusinessPermitCard(0);
		pTest1.turnUpsideDownBusinessPermitCard(2);
		
		assertEquals(size - 2 , pTest1.getBusinessPermitCards().size());
	}

	/**
	 * Test add bonus tiles.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testAddBonusTiles() throws Exception 
	{
		BonusTile tile1 = new BonusTile(bFactory.createBonus("victorypointbonus", (new Random()).nextInt(5)));
		BonusTile tile2 = new BonusTile(bFactory.createBonus("victorypointbonus", (new Random()).nextInt(5)));
		
		List<BonusTile> tileList = new ArrayList<>();
		tileList.add(tile1);
		tileList.add(tile2);
		
		pTest1.addBonusTiles(tileList);
		
		assertEquals( 2 , pTest1.getBonusTiles().size());
		assertNotNull("Error: BonusTile not added yet", pTest1.getBonusTiles());
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() 
	{
		assertNotNull("Problem in toString Method", pTest1.toString());
		assertNotNull("Problem in toString Method", pTest2.toString());
		assertNotNull("Problem in toString Method", pTest3.toString());
	}

}
