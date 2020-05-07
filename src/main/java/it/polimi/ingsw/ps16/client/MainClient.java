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
package it.polimi.ingsw.ps16.client;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps16.client.net.rmi.ClientRMI;
import it.polimi.ingsw.ps16.client.net.socket.ClientSocket;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The MainClient is the class to run for a new player of the game.
 */
public class MainClient 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( MainClient.class.getName() );
	
	/** The std in. */
	private static Scanner stdIn;
	
	/** The std out. */
	private static OutputStream stdOut;
	
	/**
	 * Instantiates a new main client.
	 */
	private MainClient()
	{
		/* Empty Constructor */
	}

	/**
	 * The main method just provides a simple CLI interface that allowed to choose a connection type:
	 * RMI or SOCKET, to connect to the server.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
		stdOut = new OutputStream();
		stdIn = new Scanner(System.in);
		String connection;
		
		stdOut.getStdOut().print(councilOfFour());
		
		stdOut.getStdOut().print("Type of connection:\n\t[1] -> Socket\n\t[2] -> RMI\n--> ");
		connection = stdIn.nextLine();
		
		while( !"1".equals(connection) && !"2".equals(connection) )
		{
			stdOut.getStdOut().println("Wrong input");
			stdOut.getStdOut().print("Type of connection:\n\t[1] -> Socket\n\t[2] -> RMI\n--> ");
			connection = stdIn.nextLine();
		}			
		if( "1".equals(connection))
		{
			try 
			{
				new ClientSocket().connectToServer();
			}
			catch (IOException e) 
			{
				log.log( Level.SEVERE, e.toString(), e);
			}
		}
		else
		{
			new ClientRMI().connectToServer();
		}
	}
	
	/**
	 * Provide Council of Four nice ASCII art string.
	 *
	 * @return the ASCII art String Council Of Four
	 */
	private static String councilOfFour()
	{
		return "\n\n\t ██████╗ ██████╗ ██╗   ██╗███╗   ██╗ ██████╗██╗██╗\n"       
			     + "\t██╔════╝██╔═══██╗██║   ██║████╗  ██║██╔════╝██║██║\n"         
			     + "\t██║     ██║   ██║██║   ██║██╔██╗ ██║██║     ██║██║\n"         
			     + "\t██║     ██║   ██║██║   ██║██║╚██╗██║██║     ██║██║\n"         
			     + "\t╚██████╗╚██████╔╝╚██████╔╝██║ ╚████║╚██████╗██║███████╗\n"    
			     + "\t ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝╚═╝╚══════╝\n"    
				                                                           
			  	+ "\t ██████╗ ███████╗    ███████╗ ██████╗ ██╗   ██╗██████╗\n"     
			  	+ "\t██╔═══██╗██╔════╝    ██╔════╝██╔═══██╗██║   ██║██╔══██╗\n"    
			  	+ "\t██║   ██║█████╗      █████╗  ██║   ██║██║   ██║██████╔╝\n"   
			  	+ "\t██║   ██║██╔══╝      ██╔══╝  ██║   ██║██║   ██║██╔══██╗\n"   
			  	+ "\t╚██████╔╝██║         ██║     ╚██████╔╝╚██████╔╝██║  ██║\n"
			  	+ "\t ╚═════╝ ╚═╝         ╚═╝      ╚═════╝  ╚═════╝ ╚═╝  ╚═╝\n\n\n";
	}
}
