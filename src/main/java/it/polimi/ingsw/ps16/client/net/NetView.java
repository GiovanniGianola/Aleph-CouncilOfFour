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
package it.polimi.ingsw.ps16.client.net;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class NetView is the superclass of the different type of client<br>
 * connections: ClientRMI and ClientSocket.<br>
 * It provide some useful methods that allowed client to connect to the server.
 */
public abstract class NetView
{
	
	/** The server IP. */
	private String serverIP = "";
	
	/** The server socket port. */
	private int serverSocketPort;
	
	/** The server RMI port. */
	private int serverRMIPort;
	
	/** The standard in. */
	private Scanner stdIn;
	
	/** The standard out. */
	protected static OutputStream stdOut;
	
	/** The UserIterface String. */
	private String ui;

	/**
	 * Instantiates a new NetView
	 * provide some default options:<br><br>
	 * serverIP = localhost<br>
	 * SocketPort = 12000<br>
	 * RMIPort = 12001.<br>
	 */
	public NetView() 
	{
		this.stdIn = new Scanner(System.in);
		stdOut = new OutputStream();
		
		this.serverIP = "localhost";
		this.serverSocketPort = 12000;
		this.serverRMIPort = 12001;
	}
	
	/**
	 * Ask to the player to choose UserInterface he prefers.<br><br>
	 * GUI = Graphics User Interface<br>
	 * CLI = Command Line Interface<br>
	 *
	 * @return the string of the user interface
	 */
	protected String askUI()
	{
		stdOut.getStdOut().println("\nType of UI:\n\t[1] -> CLI\n\t[2] -> \"GUI\"");
		stdOut.getStdOut().print("--> ");
		ui = this.stdIn.nextLine();
		
		while(!"1".equals(ui) && !"2".equals(ui))
		{
			stdOut.getStdOut().println("\n[ERROR] Invalid Input.");
			stdOut.getStdOut().println("\nType of ui:\n\t[1] -> CLI\n\t[2] -> \"GUI\"");
			stdOut.getStdOut().print("--> ");
			ui = this.stdIn.nextLine();
		}
		
		return ui;
	}
	
	/**
	 * Gets the scanner.
	 *
	 * @return the scanner
	 */
	protected Scanner getScanner()
	{
		return this.stdIn;
	}
	
	/**
	 * Gets the server IP.
	 *
	 * @return the server IP
	 */
	protected String getServerIP()
	{
		return this.serverIP;
	}
	
	/**
	 * Sets the server IP chosen by the player.<br>
	 * if the ip address or the host name are malformed<br>
	 * the system will going to set default ipAddresse<br><br>
	 * 
	 * HostName = "LocalHost" = 127.0.0.1<br>
	 *
	 * @param ip
	 *            the new server IP
	 */
	protected void setServerIP(String ip)
	{
		if("pi".equalsIgnoreCase(ip))
			this.serverIP = "over9000.noip.me";
		else if(validIP(ip))
			this.serverIP = ip;
		else this.serverIP = "localhost";
	}
	
	/**
	 * Gets the server socket port.
	 *
	 * @return the server socket port
	 */
	protected int getServerSocketPort()
	{
		return this.serverSocketPort;
	}
	
	/**
	 * Sets the server socket port chosen by the player.<br>
	 * if the socket port are malformed<br>
	 * the system will going to set default socket port<br><br>
	 * 
	 * SocketPort = 12000<br>
	 * 
	 * @param port
	 *            the new server socket port
	 */
	protected void setServerSocketPort(String port)
	{
		if("pi".equalsIgnoreCase(port))
			this.serverSocketPort = 3212;
		else if(validPort(port))
			this.serverSocketPort = Integer.parseInt(port);
		else 
			this.serverSocketPort = 12000;
	}
	
	/**
	 * Gets the server RMI port.
	 *
	 * @return the server RMI port
	 */
	protected int getServerRMIPort()
	{
		return this.serverRMIPort;
	}
	
	/**
	 * Sets the server RMI port chosen by the player.<br>
	 * if the RMI port are malformed<br>
	 * the system will going to set default RMI port<br><br>
	 * 
	 * RMIPort = 12001<br>
	 *
	 * @param port
	 *            the new server RMI port
	 */
	protected void setServerRMIPort(String port)
	{
		if("pi".equalsIgnoreCase(port))
			this.serverRMIPort = 3213;
		else if(validPort(port))
			this.serverRMIPort = Integer.parseInt(port);
		else 
			this.serverRMIPort = 12001;
	}
	
	/**
	 * Check if the IP chosen is valid.
	 *
	 * @param ip
	 *            the ip address
	 * @return true, if the ip is valid
	 */
	private boolean validIP(String ip) 
	{
		String ipAddress = ip;
		
        if (ipAddress == null || ipAddress.isEmpty()) 
        	return false;
        
        ipAddress = ipAddress.trim();
        if ( (ipAddress.length() < 6) || (ipAddress.length() > 15) ) 
        	return false;

        try 
        {
            Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        } 
        catch (PatternSyntaxException ex) 
        {
        	stdOut.getStdOut().println("Incorrect IP format\n");
            return false;
        }
    }
	
	/**
	 * check if the port chosen is valid.
	 *
	 * @param p
	 *            the port chosen
	 * @return true, if the port is valid
	 */
	private boolean validPort(String p)
	{
		String port = p;
		
		if (port == null || port.isEmpty()) 
        	return false;
		
		if ((port.length() < 5) || (port.length() > 6)) 
        	return false;
		
		try
		{
			this.serverSocketPort = Integer.parseInt(port);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
	
	/**
	 * Abstract methods needed to Connect to server.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public abstract void connectToServer() throws IOException;
}
