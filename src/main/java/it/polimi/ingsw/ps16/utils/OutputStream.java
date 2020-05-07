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
package it.polimi.ingsw.ps16.utils;

import java.io.PrintStream;

/**
 * The Class OutputStream provide the standard output stream for all the  project.
 */
public class OutputStream 
{
	
	/** The standard out. */
	private PrintStream standardOut;

	/**
	 * Instantiates a new output stream.
	 */
	public OutputStream() 
	{
		 this.standardOut = new PrintStream( System.out );
	}
	
	/**
	 * Gets the standard output.
	 *
	 * @return the std out
	 */
	public PrintStream getStdOut()
	{
		return this.standardOut;
	}

}
