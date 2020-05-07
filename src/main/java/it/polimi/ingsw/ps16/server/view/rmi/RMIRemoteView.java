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
package it.polimi.ingsw.ps16.server.view.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface RMIRemoteView.<br>
 * Provides remote methods for the client to communicate with the server.
 */
public interface RMIRemoteView extends Remote
{
	
	/**
	 * the server use this method to send any type of message to the client during the game.
	 *
	 * @param o
	 *            the message to send
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void sendMessage(Object o) throws RemoteException;
	
	/**
	 * the server use this method to send any type of message to the client during the initialize phase.
	 *
	 * @param o
	 *            the message to send
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void sendInitialMessage(Object o) throws RemoteException;
}
