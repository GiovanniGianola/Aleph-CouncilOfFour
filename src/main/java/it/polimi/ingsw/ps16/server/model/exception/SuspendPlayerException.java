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
package it.polimi.ingsw.ps16.server.model.exception;

/**
 * The Class SuspendPlayerException check if a player's turn run out.
 */
public class SuspendPlayerException extends Exception
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7049901208442123926L;

	/**
	 * Instantiates a new suspend player exception.
	 *
	 * @param msg
	 *            the msg
	 */
	public SuspendPlayerException(String msg)
	{
		super(msg);
	}
}
