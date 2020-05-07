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
package it.polimi.ingsw.ps16.server.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps16.server.model.bonus.Bonus;
import it.polimi.ingsw.ps16.server.model.bonus.BonusFactory;
import it.polimi.ingsw.ps16.server.model.bonus.BonusTile;
import it.polimi.ingsw.ps16.server.model.deck.BusinessPermitCard;
import it.polimi.ingsw.ps16.server.model.deck.StandardDecksFactory;
import it.polimi.ingsw.ps16.server.model.gameboard.City;
import it.polimi.ingsw.ps16.server.model.gameboard.CityType;
import it.polimi.ingsw.ps16.server.model.gameboard.GameBoard;
import it.polimi.ingsw.ps16.server.model.gameboard.KingBoard;
import it.polimi.ingsw.ps16.server.model.gameboard.NobilityCell;
import it.polimi.ingsw.ps16.server.model.gameboard.NobilityTrack;
import it.polimi.ingsw.ps16.server.model.gameboard.RegionBoard;

/*
 * Configurations Files:
 * 		map1.xml  	coast [a] + hill [a] + mountain [a]  
 * 		map2.xml  	coast [a] + hill [a] + mountain [b]  
 * 		map3.xml	coast [a] + hill [b] + mountain [a]  
 * 		map4.xml	coast [a] + hill [b] + mountain [b]  
 * 		map5.xml	coast [b] + hill [a] + mountain [a]  
 * 		map6.xml	coast [b] + hill [a] + mountain [b]  
 * 		map7.xml	coast [b] + hill [b] + mountain [a]  
 * 		map8.xml	coast [b] + hill [b] + mountain [b]  
 */

/**
 * The Class ParseStreamXML read and parse every information about game settings<br>
 * from XML files located in the resources folder.<br>
 * is possible to modifies the XML files in the folder to create some new game configures
 */
public class ParseStreamXML 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( ParseStreamXML.class.getName() );
	
	/** The Constant MAP_PATH. */
	private static final String MAP_PATH = "/XML/map";
	
	/** The Constant CITY_BONUS_PATH. */
	private static final String CITY_BONUS_PATH = "/XML/cityBonus.xml";
	
	/** The Constant BUSINESS_PERMIT_CARD_PATH. */
	private static final String BUSINESS_PERMIT_CARD_PATH = "/XML/businessPermitCard.xml";
	
	/** The Constant BONUS_TILE_PATH. */
	private static final String BONUS_TILE_PATH = "/XML/bonusTile.xml";
	
	/** The Constant NOBILITY_TRACK_PATH. */
	private static final String NOBILITY_TRACK_PATH = "/XML/nobilityTrack.xml";
	
	/** The Constant TAG_REGION_NAME. */
	private static final String TAG_REGION_NAME = "RegionName";
	
	/** The Constant TAG_QUANTITY. */
	private static final String TAG_QUANTITY = "Quantity";
	
	/** The std err. */
	private PrintStream stdErr;
	
	/** The game board. */
	private GameBoard gameBoard;
	
	/**
	 * Instantiates a new parses the stream XML.
	 *
	 * @param gameBoard
	 *            the game board that will be fill with the informations of XML files
	 */
	public ParseStreamXML(GameBoard gameBoard) 
	{
		this.gameBoard = gameBoard;
		this.stdErr = new PrintStream(System.err);
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
 * Inits the game board.
 *
 * @param mapNumber
 *            the map number
 * @param numPlayers
 *            the num players
 * @return the game board
 */
	public GameBoard initGameBoard(String mapNumber, int numPlayers)
	{
		List<RegionBoard> aRegions = new ArrayList<>();
		List<CityType> aTypes = new ArrayList<>();
		KingBoard kingBoard = new KingBoard();
		
		parseMap(mapNumber, numPlayers, aRegions, aTypes, kingBoard);
		gameBoard = new GameBoard(); 
		
		gameBoard.setRegionBoard(aRegions);
		gameBoard.setCityTypes(aTypes);
		gameBoard.setKingBoard(kingBoard);
		
		return gameBoard;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////

    /**
 * Given the map number, this method read and parse the corresponding XML file.<br>
 * then fill the game board for the match
 *
 * @param mapNumber
 *            the map number chosen by the host of the match
 * @param numPlayers
 *            the number of players in the current match
 * @param aRegions
 *            the list of regions
 * @param aTypes
 *            the list of city types
 * @param kingBoard
 *            the king board
 */
private void parseMap(String mapNumber, int numPlayers, List<RegionBoard> aRegions, List<CityType> aTypes, KingBoard kingBoard) 
    {
    	try 
    	{     		
    		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = documentFactory.newDocumentBuilder(); 
    		Document document = builder.parse(this.getClass().getResourceAsStream(MAP_PATH + mapNumber + ".xml")); 
    		    		
    		// region.getLength() give the number of regions
    		NodeList regions = document.getElementsByTagName("Region");
    		NodeList cityTypes = document.getElementsByTagName("CityType");  
    		NodeList cities = document.getElementsByTagName("City"); 
    		
    		/** Support ArrayLists */
    		List<City> aCities = new ArrayList<>();
    		
    		/** Create Empty Regions with just the given name by XML file */
    		for(int i = 0; i < regions.getLength(); i++)
    		{
    			Node nodo = regions.item(i); 
    			Element region = (Element)nodo;
    			
    			aRegions.add(new RegionBoard(region.getAttribute(TAG_REGION_NAME)));
    		}
    		
    		/** Create Empty CityType with just the given name by XML file */
    		for(int i = 0; i < cityTypes.getLength(); i++)
    		{
    			Node nodo = cityTypes.item(i); 
    			Element cityType = (Element)nodo;
    			
    			aTypes.add(new CityType(cityType.getElementsByTagName("TypeC").item(0).getFirstChild().getNodeValue()));
    		}
    		
    		/** Create Empty City with just the given name, number of players and a boolean value that say if that city has a bonus by XML file */
    		String cityName;
    		String cityRegName;
    		String cityTypeName;
    		String cityBonus;
    		
    		for(int i = 0; i < cities.getLength(); i++)
    		{
    			Node nodo = cities.item(i); 
    			Element city = (Element)nodo;
    			
    			/** get region name and city type name of the current city */
    			cityName = city.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
    			cityRegName = city.getAttribute(TAG_REGION_NAME);
    			cityTypeName = city.getElementsByTagName("Type").item(0).getFirstChild().getNodeValue();
    			cityBonus = city.getElementsByTagName("Bonus").item(0).getFirstChild().getNodeValue();
    			
    			aCities.add(new City(cityName, numPlayers, cityBonus));
    			
    			/** add to every city his own region board 
    			 *  add to every region a node of arrayList of city */
    			for(int j = 0; j < regions.getLength(); j++)
        		{
        			if(aRegions.get(j).getName().equals(cityRegName))
        			{
        				aCities.get(i).setRegionBoard(aRegions.get(j));
        				aRegions.get(j).getCities().add(aCities.get(i));
        			}
        		}
    			
    			/** add to every city his own city type 
    			 *  add to every city type a node of arrayList of city */
    			for(int j = 0; j < cityTypes.getLength(); j++)
        		{
        			if(aTypes.get(j).getName().equals(cityTypeName))
        			{
        				aCities.get(i).setType(aTypes.get(j));
        				aTypes.get(j).getCities().add(aCities.get(i));
        			}
        		}
    		}
    		
    		/** let assign bonus to every city from XML */
    		parseCityBonus(aCities);
    		
    		/** let create an adjacency list to every city from XML */
    		parseCityAdjacencyList(aCities, document);
    		
    		/** let create business permit card list to every region from XML */
    		parseBusinessPermitCard(aRegions, aCities);
    		
    		/** let create bonus tile to every region, city type and king reward from XML */
    		parseBonusTile(aRegions, aTypes, kingBoard);
    		
    		/** let create nobility track to the kingBoard from XML */
    		parseNobilityTrack(kingBoard);    		
    	} 
    	catch(Exception e) 
    	{ 
    		log.log( Level.SEVERE, e.toString(), e );
    	}
    }

/////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Parses the cityBonus.xml, and assign to cities every bonus
 *
 * @param aCities
 *            the full list of cities
 */
    private void parseCityBonus(List<City> aCities)
    {
    	try
    	{
	    	DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = documentFactory.newDocumentBuilder(); 
			Document document = builder.parse(this.getClass().getResourceAsStream(CITY_BONUS_PATH)); 
			
			NodeList bonuses = document.getElementsByTagName("Bonus");
    		
    		BonusFactory bFactory = new BonusFactory();
    		
    		/** Support ArrayLists using polimorphism */
    		List<Bonus> aBonuses = new ArrayList<>();
    		
    		String type;
    		String quantity;
    		
    		/** Create Bonuses with the given type and quantity by XML file using Factory Pattern */
    		for(int i = 0; i < bonuses.getLength(); i++)
    		{
    			Node nodo = bonuses.item(i); 
    			Element bonus = (Element)nodo;
    			
    			type =  bonus.getElementsByTagName("Type").item(0).getFirstChild().getNodeValue();
    			quantity =  bonus.getElementsByTagName(TAG_QUANTITY).item(0).getFirstChild().getNodeValue();
    			
    			aBonuses.add(bFactory.createBonus(type, Integer.parseInt(quantity)));
    		}
    		
    		Collections.shuffle(aBonuses);
    		
    		/** add to every city his own bonus randomly */
    		for(City city: aCities)
    		{
    			if(("true").equalsIgnoreCase(city.getBonusBoolean()))
    				city.getBonus().add(aBonuses.remove(0));
    			else if(!("false").equalsIgnoreCase(city.getBonusBoolean()))
    				this.stdErr.println("Error in XML file: map.xml wrong format field");
    		}
    		
    		if(!aBonuses.isEmpty())
    		{
    			int k = aBonuses.size();
        		Random r = new Random();
        		int random;
        		
	    		/** add remaining bonuses to cities randomly */
	    		for(int i = 0; i < k; i++)
	    		{
	    			random = r.nextInt(aCities.size());
	    			if(("true").equalsIgnoreCase(aCities.get(random).getBonusBoolean()))
	    				aCities.get(random).addBonus(aBonuses.remove(0));
	    			else if(("false").equalsIgnoreCase(aCities.get(random).getBonusBoolean()))
	    				/*check possible loop*/
	    				i--;
	    			else this.stdErr.println("Error in XML file: map.xml wrong format field");
	    		}
    		}
    		
    	}
    	catch(Exception e) 
    	{ 
    		log.log( Level.SEVERE, e.toString(), e );
    	} 
	}
    
/////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
 * create for each city his adjacency list.
 *
 * @param aCities
 *            the list of cities
 * @param document
 *            the XML document
 */
private void parseCityAdjacencyList(List<City> aCities, Document document) 
    {
    	NodeList cities = document.getElementsByTagName("City");
    	
    	String neigh;
    	String[] nearCity;
    	List<City> cityTemp;
    	
    	for(int i = 0; i < aCities.size(); i++)
		{
			Node nodo = cities.item(i); 
			Element city = (Element)nodo;
			
			neigh = city.getElementsByTagName("Near").item(0).getFirstChild().getNodeValue();
			nearCity = neigh.split(",");
			
			for(City c: aCities) // cicla tutte le città
			{
				cityTemp = aCities.get(i).getNearCities();
				for(int x = 0; x < nearCity.length; x++)
				{
					if(c.getName().equalsIgnoreCase(nearCity[x]))
					{
						cityTemp.add(c);
					}
				}
			}
		}
    	
	}
    
/////////////////////////////////////////////////////////////////////////////////////////////////
    
	/**
 * Parses the businessPermitCard.xml, and create business permit card deck each regions of the game
 *
 * @param aRegions
 *            the list of regions
 * @param aCities
 *            the list of cities
 */
private void parseBusinessPermitCard(List<RegionBoard> aRegions, List<City> aCities) 
	{
		try
    	{
	    	DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = documentFactory.newDocumentBuilder(); 
			Document document = builder.parse(this.getClass().getResourceAsStream(BUSINESS_PERMIT_CARD_PATH)); 
			
			
			NodeList regions = document.getElementsByTagName("Region");   
    		NodeList businessPermitCard = document.getElementsByTagName("BusinessPermitCard");  
    		
    		String[] letters;
    		String[] bonusTypes;
    		String[] bonusQuantities;
    		
    		String letter;
    		String bonusType;
    		String bonusQuantity;
    		
    		List<BusinessPermitCard> bPC;
    		List<City> bPCCities;
    		List<Bonus> bPCBonuses;
    		
    		StandardDecksFactory dFactory = new StandardDecksFactory();
    		
    		for(int i = 0; i < aRegions.size(); i++)
    		{
    			Node nodo = regions.item(i); 
    			Element region = (Element)nodo;
    			
    			bPC = new ArrayList<>();
    			
    			//controllo regione per regione
    			if(aRegions.get(i).getName().equalsIgnoreCase(region.getAttribute(TAG_REGION_NAME)))
    			{
    				//ciclo sugli elementi regione per regione
	    			for(int j = 0; j < (businessPermitCard.getLength()) / (aRegions.size()); j++)
	        		{
	        			letter = region.getElementsByTagName("Letter").item(j).getFirstChild().getNodeValue();
	        			bonusType = region.getElementsByTagName("Type").item(j).getFirstChild().getNodeValue();
	        			bonusQuantity = region.getElementsByTagName(TAG_QUANTITY).item(j).getFirstChild().getNodeValue();
	        			
	        			letters = letter.split(",");
	    				bPCCities = new ArrayList<>();
	    				
		    			/* create a list of city according to the letters read on the cards*/
		    			//ciclo sugli elementi dell array di lettere lette
		    			for(int x = 0; x <letters.length; x++)
		    			{
		    				//ciclo sulle città
		    				for(int y = 0; y < aCities.size(); y++)
		    				{
		    					//controllo se la città è presente
		    					if(letters[x].equalsIgnoreCase(aCities.get(y).getName().substring(0, 1)))
		    					{
		    						bPCCities.add(aCities.get(y));
		    					}
		    				}
		    			}
		    			bonusTypes = bonusType.split(",");
		    			bonusQuantities = bonusQuantity.split(",");
		    			bPCBonuses = new ArrayList<>();
		    			BonusFactory bFactory = new BonusFactory();
		    			
		    			/* create a list of bonus according to the bonuses read on the cards */
		    			/* ciclo sugli elementi dell array di bonus letti */
		    			for(int x = 0; x <bonusTypes.length; x++)
		    			{
		    				bPCBonuses.add(bFactory.createBonus(bonusTypes[x], Integer.parseInt(bonusQuantities[x])));
		    			}
		    			
		    			bPC.add(new BusinessPermitCard(bPCBonuses,bPCCities));
	        		}
	    			aRegions.get(i).setBusinessPermitCardsDeck(dFactory.createBusinessPermitCardDeck(bPC));
    			}
	    	}
    	}
		catch(Exception e) 
    	{ 
			log.log( Level.SEVERE, e.toString(), e );
    	}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
 * Parses the bonusTile.xml document, and assign to each region his own bonus tile<br>
 * then provide a bonus tile for each city type
 *
 * @param aRegions
 *            the list of regions
 * @param aTypes
 *            the list of city types
 * @param kingBoard
 *            the king board
 */
private void parseBonusTile(List<RegionBoard> aRegions, List<CityType> aTypes, KingBoard kingBoard)
	{
		try 
    	{ 
    		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = documentFactory.newDocumentBuilder(); 
			Document document = builder.parse(this.getClass().getResourceAsStream(BONUS_TILE_PATH)); 
    		
    		NodeList bonusTileRegions = document.getElementsByTagName("BonusRegion");   
    		NodeList bonusTileCityType = document.getElementsByTagName("BonusCityType");   
    		NodeList bonusTileKing = document.getElementsByTagName("BonusKing");   
    		
    		BonusFactory bFactory = new BonusFactory();
    		
    		String type;
    		String quantity;
    		String bonusType = "victorypointbonus";
    		
    		int numCityType = 0;
    		
    		List<BonusTile> kBonuses = new ArrayList<>();
    		    		
    		/** add to every regions his own bonus  */
    		for(int i = 0; i < aRegions.size(); i++)
    		{
    			Node nodo = bonusTileRegions.item(i); 
    			Element bonus = (Element)nodo;
    			
    			type =  bonus.getElementsByTagName("Type").item(0).getFirstChild().getNodeValue();
    			quantity =  bonus.getElementsByTagName(TAG_QUANTITY).item(0).getFirstChild().getNodeValue();
    			
    			/** add to every regions his own bonus  */
    			for(int j = 0; j < aRegions.size(); j++)
        		{
	    			if(aRegions.get(j).getName().equalsIgnoreCase(bonus.getAttribute(TAG_REGION_NAME))
	    					&& (bonusType).equalsIgnoreCase(type))
	    				aRegions.get(j).setBonusTile(new BonusTile(bFactory.createBonus(type, Integer.parseInt(quantity))));
	    			else if(!(bonusType).equalsIgnoreCase(type))
	    				this.stdErr.println("Error: wrong type of bonus, it must be a victory point bonus");
        		}
    		}
    		
    		/** calculate number of citytype set true */
    		for(int i = 0; i < aTypes.size(); i++)
    		{
    			if(("true").equalsIgnoreCase(aTypes.get(i).getCities().get(0).getBonusBoolean()))
    					numCityType++;
    		}
    		
    		/** add to every citytype his own bonus  */
    		for(int i = 0; i < numCityType; i++)
    		{
    			Node nodo = bonusTileCityType.item(i); 
    			Element bonus = (Element)nodo;
    			
    			type =  bonus.getElementsByTagName("Type").item(0).getFirstChild().getNodeValue();
    			quantity =  bonus.getElementsByTagName(TAG_QUANTITY).item(0).getFirstChild().getNodeValue();
    			
    			/** add to every regions his own bonus  */
    			for(int j = 0; j < numCityType; j++)
        		{
	    			if((aTypes.get(j).getName().equalsIgnoreCase(bonus.getAttribute("CityType"))) 
	    					&& ("true").equalsIgnoreCase(aTypes.get(j).getCities().get(0).getBonusBoolean())
	    					&& (bonusType).equalsIgnoreCase(type))
	    				aTypes.get(j).setBonusTile(new BonusTile(bFactory.createBonus(type, Integer.parseInt(quantity))));
	    			else if(!(bonusType).equalsIgnoreCase(type))
	    				this.stdErr.println("Error: wrong type of bonus, must be a victory point bonus");
        		}
    		}
    		
    		/** add to every kingboard his own list of bonuses  */
    		for(int i = 0; i < bonusTileKing.getLength(); i++)
    		{
    			Node nodo = bonusTileKing.item(i); 
    			Element bonus = (Element)nodo;
    			
    			type =  bonus.getElementsByTagName("Type").item(0).getFirstChild().getNodeValue();
    			quantity =  bonus.getElementsByTagName(TAG_QUANTITY).item(0).getFirstChild().getNodeValue();
    			
    			/** add to every regions his own bonus  */
    			if((bonusType).equalsIgnoreCase(type))
    				kBonuses.add(new BonusTile(bFactory.createBonus(type, Integer.parseInt(quantity))));
    			else if(!(bonusType).equalsIgnoreCase(type))
    				this.stdErr.println("Error: wrong type of bonus, must be a victory point bonus");
    		}
    		kingBoard.setBonusTiles(kBonuses);
    		
    	}
		catch(Exception e) 
    	{ 
			log.log( Level.SEVERE, e.toString(), e ); 
    	}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
 * Parses the nobilityTrack.xml, and create the nobility track according to the file XML read<br>
 * then provide it to the king board
 *
 * @param kingBoard
 *            the king board
 */
private void parseNobilityTrack(KingBoard kingBoard) 
	{
		try 
    	{ 
    		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = documentFactory.newDocumentBuilder(); 
			Document document = builder.parse(this.getClass().getResourceAsStream(NOBILITY_TRACK_PATH)); 
    		
    		NodeList nobilityTrackCells = document.getElementsByTagName("Cell");   
    		
    		List<NobilityCell> nC = new ArrayList<>();
    		List<Bonus> cellBonuses = new ArrayList<>();
    		
    		BonusFactory bFactory = new BonusFactory();

    		String[] bonusTypes;
    		String[] bonusQuantities;
    		
    		String type;
    		String quantity;
    		
    		for(int i = 0; i < nobilityTrackCells.getLength(); i++)
    		{
    			Node nodo = nobilityTrackCells.item(i); 
    			Element cell = (Element)nodo;
    			
    			if(("true").equalsIgnoreCase(cell.getAttribute("BonusBool")))
    			{
	    			type =  cell.getElementsByTagName("Type").item(0).getFirstChild().getNodeValue();
	    			quantity =  cell.getElementsByTagName(TAG_QUANTITY).item(0).getFirstChild().getNodeValue();
	    			
	    			bonusTypes = type.split(",");
	    			bonusQuantities = quantity.split(",");
	    			
	    			for(int x = 0; x <bonusTypes.length; x++)
	    			{
	    				cellBonuses.add(bFactory.createBonus(bonusTypes[x], Integer.parseInt(bonusQuantities[x])));
	    			}
	    			nC.add(new NobilityCell(cellBonuses));
	    		}
    			else if(("false").equalsIgnoreCase(cell.getAttribute("BonusBool")))
    				nC.add(new NobilityCell(null));
    			else
    				this.stdErr.println("Error in XML file: nobilityTrack.xml wrong format field");
    			cellBonuses = new ArrayList<>();
    		}

    		kingBoard.setNobilityTrack(new NobilityTrack(nC));
    	}
		catch(Exception e) 
    	{ 
			log.log( Level.SEVERE, e.toString(), e );
    	}
	}
}






