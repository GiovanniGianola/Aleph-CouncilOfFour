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
package it.polimi.ingsw.ps16.client.net.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface ClientViewRemote.<br>
 * Provides remote methods for the server to communicate with the client
 */
@FunctionalInterface
public interface ClientViewRemote extends Remote
{
	
	/**
	 * the server use this method to send any type of message to the client.
	 *
	 * @param o
	 *            the message to send
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void sendMessageToClient(Object o) throws RemoteException;
}
