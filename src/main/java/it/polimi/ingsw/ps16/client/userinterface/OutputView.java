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
package it.polimi.ingsw.ps16.client.userinterface;

import java.util.Observable;
import java.util.Observer;

/**
 * The Class OutputView is the superclass of the different type of user <br>
 * connections: GUI and CLI.<br>
 * It provide some useful methods that allowed player to play.<br>
 * it used as abstraction for strategy pattern
 */
public abstract class OutputView extends Observable implements Observer
{

	/**
	 * Instantiates a new output view.
	 */
	public OutputView() 
	{
		/* Empty Constructor */
	}

}
