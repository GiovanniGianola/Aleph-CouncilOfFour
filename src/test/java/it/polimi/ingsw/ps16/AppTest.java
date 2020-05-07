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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * The Class AppTest.
 */
public class AppTest 
    extends TestCase
{
    
    /**
	 * Instantiates a new app test.
	 *
	 * @param testName
	 *            the test name
	 */
    public AppTest( String testName )
    {
        super( testName );
    }
    

    /**
	 * Suite.
	 *
	 * @return the test
	 */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
	 * Test app.
	 */
    public void testApp()
    {
        assertTrue( true );
    }
}
