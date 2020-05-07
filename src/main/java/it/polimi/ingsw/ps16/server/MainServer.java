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
package it.polimi.ingsw.ps16.server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;

import it.polimi.ingsw.ps16.server.view.Server;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class MainServer is the class to run for starting server.
 */
public class MainServer 
{
	
	/** The std out. */
	private static OutputStream stdOut;
	
	/**
	 * Instantiates a new main server.
	 */
	private MainServer()
	{
		/* Empty Constructor */
	}
	
	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws AlreadyBoundException
	 *             the already bound exception
	 */
	public static void main(String[] args) throws IOException, AlreadyBoundException 
	{
		stdOut = new OutputStream();
		
		stdOut.getStdOut().print(councilOfFour());
		
		Server server = new Server();
		
		stdOut.getStdOut().println("Starting RMI server.\n");
		server.runRMI();
		
		stdOut.getStdOut().println("Starting SOCKET server.");
		server.runSocket();
	}
	
	/**
	 * Council of four.
	 *
	 * @return the string
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
