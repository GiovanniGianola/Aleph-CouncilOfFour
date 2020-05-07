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
package it.polimi.ingsw.ps16;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import it.polimi.ingsw.ps16.server.model.TestGame;
import it.polimi.ingsw.ps16.server.model.TestParseStreamXML;
import it.polimi.ingsw.ps16.server.model.gameboard.TestBalcony;
import it.polimi.ingsw.ps16.server.model.gameboard.TestCity;
import it.polimi.ingsw.ps16.server.model.gameboard.TestGameBoard;
import it.polimi.ingsw.ps16.server.model.gameboard.TestRegionBoard;
import it.polimi.ingsw.ps16.server.model.player.TestPlayer;


	/**
	 * The Class MainTest.
	 */
	@RunWith(Suite.class)
	@SuiteClasses({ 
		
		TestBalcony.class,
		TestCity.class,
		TestGameBoard.class,
		TestRegionBoard.class,
		
		
		TestPlayer.class,
		
		TestGame.class,
		TestParseStreamXML.class,
		
		})
	
	
public class MainTest 
{
		
}
