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
package it.polimi.ingsw.ps16.client.net.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

import it.polimi.ingsw.ps16.client.net.NetView;

/**
 * The Class ClientSocket is the client that <br>
 * provide a connection with the server using Socket protocol.
 */
public class ClientSocket extends NetView
{
	
	/** The socket. */
	private Socket socket;
		
	/** The socket in. */
	private ObjectInputStream socketIn;
	
	/** The socket out. */
	private ObjectOutputStream socketOut;
	
	/**
	 * Instantiates a new client socket.
	 */
	public ClientSocket() 
	{    
		/* Empty constructor */
	}
	
	@Override
	public void connectToServer() throws IOException
	{
		while(this.socket ==  null)
		{
			stdOut.getStdOut().println("\nInsert IP address or Host Name:");
			stdOut.getStdOut().println("(default Host Name: localhost)");
			stdOut.getStdOut().print("--> ");
			this.setServerIP(this.getScanner().nextLine());
			
			stdOut.getStdOut().println("\nInsert Socket Port:");
			stdOut.getStdOut().println("(default Socket Port: 12000)");
			stdOut.getStdOut().print("--> ");
			this.setServerSocketPort(this.getScanner().nextLine());
			
	    	try
			{
	        	this.socket = new Socket(this.getServerIP(), this.getServerSocketPort());
				this.socketOut = new ObjectOutputStream(socket.getOutputStream());
				this.socketIn = new ObjectInputStream(socket.getInputStream());	
			}
	        catch(ConnectException e)
	        {
	        	stdOut.getStdOut().println("\nConnection Refused.");
	        	stdOut.getStdOut().println("SocketServer Offline on ip: " + this.getServerIP() + " and port: " + this.getServerSocketPort() + ".\n");
	        }
		}		
		this.startClient();
	}
	
	/**
	 * Start client handler for a socket connection.
	 */
	private void startClient()
	{
		new ClientHandlerSocket(this.socketOut, this.socketIn).initializeInterface( askUI() );
	}
}