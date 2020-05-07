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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import it.polimi.ingsw.ps16.server.model.deck.Deck;


/**
 * The Class TestRegionBoard.
 */
public class TestRegionBoard 
{
	
	/** The rb test. */
	private RegionBoard rbTest;
	
	/** The l test. */
	private List<BusinessPermitCard> lTest;
	
	/** The tile 1. */
	private BusinessPermitCard tile1;
	
	/** The tile 2. */
	private BusinessPermitCard tile2;
	
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
	
	/** The deck test. */
	private Deck<BusinessPermitCard> deckTest; 
	
	/** The bt test. */
	private BonusTile btTest;
	
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
		System.out.println("Begin Test for the class RegionBoard");
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
		System.out.println("End Test for the class RegionBoard");
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
		bFactory = new BonusFactory();
		String regionName = "TestRegion";
		rbTest = new RegionBoard(regionName);
				
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
		

		lTest = new ArrayList<>();
		lTest.add(new BusinessPermitCard(bTest1,cTest1));
		lTest.add(new BusinessPermitCard(bTest2,cTest2));
		lTest.add(new BusinessPermitCard(bTest3,cTest3));
		lTest.add(new BusinessPermitCard(bTest4,cTest4));
		
		deckTest = new Deck<BusinessPermitCard>(lTest);
		tile1 =  deckTest.getcards().get(0);
		tile2 =  deckTest.getcards().get(1);
		rbTest.setBusinessPermitCardsDeck(deckTest);
		
		btTest = new BonusTile(bFactory.createBonus("victorypointbonus", (new Random()).nextInt(5)));
		
		System.out.println("An instance of the class RegionBoard for tests");
	}

	/**
	 * Test get balcony.
	 */
	@Test
	public void testGetBalcony() 
	{
		Balcony bTest = rbTest.getBalcony();
		
		assertEquals(bTest, rbTest.getBalcony());
		assertNotNull("Balcony null", rbTest.getBalcony());
	}

	/**
	 * Test set business permit cards deck.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testSetBusinessPermitCardsDeck() throws Exception 
	{
		assertEquals(deckTest.getcards(), rbTest.getBusinessPermitCardsDeck().getcards());
		assertNotNull("Deck null", rbTest.getBusinessPermitCardsDeck());
	}

	/**
	 * Test get business permit cards deck.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetBusinessPermitCardsDeck() throws Exception 
	{
		assertNotNull("Deck null", rbTest.getBusinessPermitCardsDeck());
	}

	/**
	 * Test get business permit tile one.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetBusinessPermitTileOne() throws Exception 
	{
		assertEquals(tile1, rbTest.getBusinessPermitTileOne());
		assertNotNull("Tile1 null", rbTest.getBusinessPermitTileOne());
	}

	/**
	 * Test get business permit tile two.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetBusinessPermitTileTwo() throws Exception 
	{
		assertEquals(tile2, rbTest.getBusinessPermitTileTwo());
		assertNotNull("Tile2 null", rbTest.getBusinessPermitTileTwo());
	}

	/**
	 * Test set bonus tile.
	 */
	@Test
	public void testSetBonusTile() 
	{
		rbTest.setBonusTile(btTest);
		
		assertEquals(btTest, rbTest.getBonusTile());
		assertNotNull("BonusTile null", rbTest.getBonusTile());
	}

	/**
	 * Test get bonus tile.
	 */
	@Test
	public void testGetBonusTile() 
	{
		rbTest.setBonusTile(btTest);
		
		assertNotNull("BonusTile null", rbTest.getBonusTile());
	}

	/**
	 * Test get cities.
	 */
	@Test
	public void testGetCities() 
	{
		rbTest.getCities().add(new City("CityTestA",4,"true"));
		rbTest.getCities().add(new City("CityTestB",4,"true"));
		rbTest.getCities().add(new City("CityTestC",4,"true"));
		
		assertNotNull("BonusTile null", rbTest.getCities());
	}

	/**
	 * Test draw business permit tile one.
	 */
	@Test
	public void testDrawBusinessPermitTileOne() 
	{
		assertEquals(tile1, rbTest.drawBusinessPermitTileOne());
		assertNotNull("Drawed Tile1 null", rbTest.drawBusinessPermitTileOne());
	}

	/**
	 * Test draw business permit tile two.
	 */
	@Test
	public void testDrawBusinessPermitTileTwo() 
	{
		assertEquals(tile2, rbTest.drawBusinessPermitTileTwo());
		assertNotNull("Drawed Tile2 null", rbTest.drawBusinessPermitTileTwo());
	}

	/**
	 * Test draw bonus tile.
	 */
	@Test
	public void testDrawBonusTile() 
	{
		rbTest.setBonusTile(btTest);
		
		assertNotNull("BonusTile already drawed", rbTest.drawBonusTile());
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() 
	{
		assertNotNull("Problem in toString Method", rbTest.toString());
	}

}
