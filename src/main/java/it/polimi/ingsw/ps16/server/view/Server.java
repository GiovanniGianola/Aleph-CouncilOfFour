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
package it.polimi.ingsw.ps16.server.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps16.server.view.rmi.ServerRMIView;
import it.polimi.ingsw.ps16.server.view.singleton.GamesParameters;
import it.polimi.ingsw.ps16.server.view.socket.SocketView;
import it.polimi.ingsw.ps16.utils.ColorCodes;
import it.polimi.ingsw.ps16.utils.OutputStream;

/**
 * The Class Server is responsible to enable RMI server<br>
 * and accept Socket connections.
 */
public class Server 
{
	
	/** The Constant log. */
	//private static final Logger log = Logger.getLogger( Server.class.getName() );
	
	/** The Constant RMI_PORT. */
	private static final int RMI_PORT = 12001;
	
	/** The Constant SOCKET_PORT. */
	private static final int SOCKET_PORT = 12000;

	/** The Constant NAME. */
	private static final String NAME = "Cof"; 
	
	/** The time out expired. */
	private static boolean timeOutExpired  = false;
	
	/** The server socket. */
	private ServerSocket serverSocket;
	
	/** The server RMI view. */
	private ServerRMIView serverRMIView;
	
	/** The registry. */
	private Registry registry;
	
	/** The standard out. */
	private static OutputStream stdOut;
	
	/** The game controllers. */
	private static List<Thread> gameControllers;
	
	/** The is stopped. */
	private boolean isStopped;
	
	/** The start handler. */
	private GameStartingHandler startHandler;

	/**
	 * Instantiates a new server.<br>
	 * It also acquires the public IP-address of this host<br>
	 * if the Internet connection is enabled
	 */
	public Server()
	{
		stdOut = new OutputStream();
		Server.gameControllers = new ArrayList<>();
		this.isStopped = false;
		this.startHandler = new GameStartingHandler();
		
		/* Server public IP-Address */
		URL whatismyip;
		try 
		{
			whatismyip = new URL("http://myip.dnsomatic.com/");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			stdOut.getStdOut().println(ColorCodes.parseColors(":red,n:Servers Public IP address: " + InetAddress.getByName(in.readLine()).toString() + "[RC]"));
			stdOut.getStdOut().println("Servers are waiting Connections...\n");
		} 
		catch (IOException e) 
		{
			//log.log( Level.SEVERE, "No internet Connection: Public Ip Addresse not reachable.", e);
			stdOut.getStdOut().println("No internet Connection: Public Ip Addresse not reachable.");
     	}
	}
	
	
	/**
	 * This method enables RMI connections and instantiates<br>
	 * the class responsible of them.
	 */
	public void runRMI()
	{
        try 
        {
			registry = LocateRegistry.createRegistry(RMI_PORT);
			
			this.serverRMIView = new ServerRMIView();
			registry.bind(NAME, UnicastRemoteObject.exportObject(this.serverRMIView, RMI_PORT));
			
			stdOut.getStdOut().println(ColorCodes.parseColors(":blue,n:*** RMI Server is UP ***[RC]"));
			stdOut.getStdOut().println("\tServer Port: [" + RMI_PORT + "]");
	        
	     	/* Server local/private IP-Address */
			stdOut.getStdOut().println("\tServer Local IP address: " +  InetAddress.getLocalHost().getHostAddress());
        } 
        catch (RemoteException | AlreadyBoundException | UnknownHostException e) 
        {
        	stdOut.getStdOut().println("\nRMIServer Error.\nPort already in use: " + RMI_PORT);
		}
    }
	

	/**
	 * This method enables Socket connections and it is responsible of them.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void runSocket() throws IOException 
	{
		try
        {
			this.serverSocket = new ServerSocket(SOCKET_PORT);
			
			stdOut.getStdOut().println(ColorCodes.parseColors(":blue,n:*** Socket Server is UP ***[RC]"));
			stdOut.getStdOut().println("\tServer Port: [" + serverSocket.getLocalPort() + "]");
	        
	     	/* Server local/private IP-Address */
			stdOut.getStdOut().println("\tServer Local IP address: " + InetAddress.getLocalHost().getHostAddress());
         
        	while(!isStopped)
        	{
		        GamesParameters.getGameParametersInstance().newGameParameters(Server.gameControllers.size() + 1);
        		
        		while (!Server.isTimeOutExpired()) 
		        {
		        	Socket socket = this.serverSocket.accept();
		        	
		        	if(!Server.isTimeOutExpired())
		        		(new Thread(new SocketView(socket, Server.gameControllers.size() + 1))).start();
		        }
		        
		        this.startHandler.startGame(Server.gameControllers.size() + 1);
		        this.serverRMIView.increaseNGame();
	            Server.setTimeOutExpired(false);
        	}
    	}
        catch (IOException e) 
        {
        	stdOut.getStdOut().println("\nSocketServer Error.\nPort already in use: " + SOCKET_PORT);
        }	
	}

	/**
	 * Sets the time out expired.
	 *
	 * @param isTimeOutExpired
	 *            the new time out expired
	 */
	public static void setTimeOutExpired(boolean isTimeOutExpired) 
	{
		Server.timeOutExpired = isTimeOutExpired;
	}
	
	/**
	 * Checks if is time out expired.
	 *
	 * @return true, if is time out expired
	 */
	public static boolean isTimeOutExpired() 
	{
		return Server.timeOutExpired;
	}
	
	/**
	 * Gets the game controllers.
	 *
	 * @return the game controllers
	 */
	public static List<Thread> getGameControllers() 
	{
		return gameControllers;
	}
}

