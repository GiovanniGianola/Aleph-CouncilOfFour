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

import it.polimi.ingsw.ps16.client.net.rmi.ClientViewRemote;

/**
 * The Interface ServerRMIRemoteView.<br>
 * Provides remote methods for the client to register to the server.
 */
@FunctionalInterface
public interface ServerRMIRemoteView extends Remote 
{
	
	/**
	 * Register client.
	 *
	 * @param clientStub
	 *            the client stub
	 * @return the RMI remote view
	 * @throws RemoteException
	 *             the remote exception
	 */
	public RMIRemoteView registerClient( ClientViewRemote clientStub ) throws RemoteException;
}
