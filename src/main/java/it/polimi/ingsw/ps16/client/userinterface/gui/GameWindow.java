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
package it.polimi.ingsw.ps16.client.userinterface.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import it.polimi.ingsw.ps16.client.net.HandlerView;
import it.polimi.ingsw.ps16.client.userinterface.OutputView;
import it.polimi.ingsw.ps16.server.model.message.broadcastmessage.BroadcastMessage;
import it.polimi.ingsw.ps16.server.model.message.chatmessage.ChatMessage;
import it.polimi.ingsw.ps16.server.model.message.finishmessage.FinishMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.ErrorMessage;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageInitializeType;
import it.polimi.ingsw.ps16.server.model.message.infomessage.InfoMessageType;
import it.polimi.ingsw.ps16.server.model.message.initializeplayermessage.InitializePlayerMessage;
import it.polimi.ingsw.ps16.server.model.message.replymessage.ReplyMessage;
import it.polimi.ingsw.ps16.server.model.message.requestmessage.RequestMessage;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateBalcony;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateMap;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdatePlayerMessage;
import it.polimi.ingsw.ps16.server.model.message.updatemessage.UpdateTimer;

/**
 * The Class GameWindow is the GUI version of the Output View.<br>
 * it allow player to play CouncilOfFour using a simple<br>
 *  and intuitive graphic interface
 */
public class GameWindow extends OutputView implements ActionListener, KeyListener, WindowListener 
{
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger( GameWindow.class.getName() );
	
	/** The Constant BACKGROUND_PATH. */
	private static final String BACKGROUND_PATH = "/images/medievalbg.jpg";
	
	/** The Constant ICON_PATH. */
	private static final String ICON_PATH = "/images/medievalIcon.jpg";
	
	/** The Constant WINDOW_TITLE. */
	private static final String WINDOW_TITLE = "Council of Four";
	
	/** The Constant FONT_PATH. */
	private static final String FONT_PATH = "/font/GODOFWAR.ttf";
	
	/** The custom font. */
	private Font customFont;

	/** The main frame. */
	private JFrame mainFrame;
	
	/** The command field. */
	private JTextField commandField;
	
	/** The enter button. */
	private JButton enterButton;
	
	/** The output pane. */
	private JScrollPane outputPane;
	
	/** The background label. */
	private JLabel backgroundLabel;
	
	/** The output label. */
	private JLabel outputLabel;
	
	/** The output text. */
	private JTextPane outputText;
	
	/** The chat pane. */
	private JScrollPane chatPane;
	
	/** The chat text. */
	private JTextPane chatText;
	
	/** The chat label. */
	private JLabel chatLabel;
	
	/** The chat enter button. */
	private JButton chatEnterButton;
	
	/** The chat field. */
	private JTextField chatField;
	
	/** The client handler. */
	private HandlerView clientHandler;

	/** The doc out. */
	private StyledDocument docOut;
	
	/** The doc chat. */
	private StyledDocument docChat;
	
	/** The player ID. */
	private int playerID;
	
	/** The player name. */
	private String playerName;
	
	/** The player color. */
	private Color playerColor;
	
	/** The game ready. */
	private boolean gameReady;
	
	/** The your turn. */
	private boolean yourTurn;
	
	/** The info. */
	private JPanel info;
	
	/** The player info. */
	private PlayerPanel playerInfo;
	
	/** The about. */
	private JPanel about;
	
	/** The about info. */
	private AboutPanel aboutInfo;
	
	/** The map. */
	private JPanel map;
	
	/** The map info. */
	private MapPanel mapInfo;
	
	/** The balconies. */
	private JPanel balconies;
	
	/** The balcony info. */
	private BalconyPanel balInfo;
	
	/** The timer. */
	private JPanel timer;
	
	/** The timer info. */
	private TimerPanel timerInfo;
	
	/** The screen. */
	private int screen;

	/**
	 * Instantiates a new game window.
	 *
	 * @param clientHandler
	 *            the client handler
	 */
	public GameWindow(HandlerView clientHandler) 
	{
		this.clientHandler = clientHandler;
		
		this.clientHandler.addObserver(this);
		this.addObserver(this.clientHandler);
		
		this.gameReady = false;
		this.yourTurn = false;
		
		
		initialize();
		
		new Thread(this.clientHandler).start();
	}

	/**
	 * Initialize the main frame with all his components
	 */
	private void initialize() 
	{
		customFont = setFont();
		mainFrame = setFrame();
		chatPane = setChatPanel();
		mainFrame.getContentPane().setLayout(null);

		playerInfo = new PlayerPanel(this.customFont);
		this.info = playerInfo;
		this.info.setOpaque(false);
		this.info.setBounds(24, 13, 544, 351);
		this.info.setVisible(false);
		mainFrame.getContentPane().add(info);
		
		aboutInfo = new AboutPanel(this.customFont);
		this.about = aboutInfo;
		this.about.setOpaque(false);
		this.about.setBounds(97, 29, 322, 351);
		this.about.setVisible(true);
		mainFrame.getContentPane().add(about);
		
		mapInfo = new MapPanel();
		this.map = mapInfo;
		this.map.setOpaque(false);
		this.map.setBounds(562, 13, 708, 351);
		this.map.setVisible(false);
		mainFrame.getContentPane().add(map);
		
		balInfo = new BalconyPanel(this.customFont);
		this.balconies = balInfo;
		this.balconies.setOpaque(false);
		this.balconies.setBounds(312, 366, 959, 50);
		this.balconies.setVisible(false);
		mainFrame.getContentPane().add(balconies);
		
		timerInfo = new TimerPanel(this.customFont);
		this.timer = timerInfo;
		this.timer.setOpaque(false);
		this.timer.setBounds(24, 366, 272, 51);
		this.timer.setVisible(false);
		mainFrame.getContentPane().add(timer);
		
		commandField = new JTextField();
		commandField.setBounds(24, 759 - screen, 554, 43);
		commandField.setFont(customFont);
		commandField.addKeyListener(this);
		mainFrame.getContentPane().add(commandField);
		commandField.setColumns(10);

		enterButton = new JButton("<html><font color = Black size = 5><center>Enter</center></font></html>");
		enterButton.setBounds(590, 656, 97, 43);
		enterButton.setBounds(591, 759 - screen, 97, 43);
		enterButton.setFont(customFont);
		enterButton.addActionListener(this);
		mainFrame.getContentPane().add(enterButton);
		
		outputPane = new JScrollPane();
		outputPane.setOpaque(false);
		outputPane.setBounds(24, 417, 664, 229);
		outputPane.setBounds(24, 417, 664, 329 - screen);
		outputPane.setFont(customFont);
		mainFrame.getContentPane().add(outputPane);
		
		outputText = new JTextPane();
		outputText.setEditable(false);
		outputPane.setViewportView(outputText);
		docOut = outputText.getStyledDocument();
		
		outputLabel = new JLabel("<html><font color = Black size = 5><center>OUTPUT</center></font></html>");
		outputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		outputLabel.setFont(customFont);
		outputPane.setColumnHeaderView(outputLabel);
		
		backgroundLabel = new JLabel("");
		backgroundLabel.setBounds(0, 0, 1300, 866);
		mainFrame.getContentPane().add(backgroundLabel);
		backgroundLabel.setIcon(new ImageIcon(getClass().getResource(BACKGROUND_PATH)));
	}
	
	/**
	 * Sets the chat panel for the communications between player during the match.
	 *
	 * @return the j scroll pane
	 */
	private JScrollPane setChatPanel()
	{
		chatEnterButton = new JButton("<html><font color = Black size = 5><center>Enter</center></font></html>");
		chatEnterButton.setFont(null);
		chatEnterButton.setBounds(1173, 759 - screen, 97, 43);
		chatEnterButton.addActionListener(this);
		chatEnterButton.setFont(customFont);
		mainFrame.getContentPane().add(chatEnterButton);
	
		chatPane = new JScrollPane();
		chatPane.setOpaque(false);
		chatPane.setFont(null);
		chatPane.setBounds(710, 417, 560, 329 - screen);
		mainFrame.getContentPane().add(chatPane);
		
		chatField = new JTextField();
		chatField.setFont(null);
		chatField.setColumns(10);
		chatField.setBounds(710, 759 - screen, 451, 43);
		chatField.addKeyListener(this);
		chatField.setFont(customFont);
		mainFrame.getContentPane().add(chatField);
		
		chatText = new JTextPane();
		chatText.setIgnoreRepaint(true);
		chatText.setEditable(false);
		chatPane.setViewportView(chatText);
		
		docChat = chatText.getStyledDocument();
		
		chatLabel = new JLabel("<html><font color = Black size = 5><center>CHAT</center></font></html>");
		chatLabel.setHorizontalAlignment(SwingConstants.CENTER);
		chatLabel.setFont(customFont);
		chatPane.setColumnHeaderView(chatLabel);
		
		return chatPane;
	}
	
	/**
	 * Sets the main frame according to the dimension of the calculator monitor.
	 *
	 * @return the j frame
	 */
	private JFrame setFrame() 
	{
		mainFrame =  new JFrame();
		mainFrame.addWindowListener(this);
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(ICON_PATH)));
		mainFrame.setTitle(WINDOW_TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double height = screenSize.getHeight();
		
		if(height < 860.0)
		{
			mainFrame.setBounds(0, 0, 1300, 750);
			screen = 100;
		}
		else
			mainFrame.setBounds(0, 0, 1300, 860);
		
		mainFrame.setLocationRelativeTo(backgroundLabel);
		mainFrame.setVisible(true);
		
		
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		return mainFrame;
	}
	
	/**
	 * Sets the custom font .
	 *
	 * @return the font
	 */
	private Font setFont() 
	{
		try 
		{
			 customFont = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(FONT_PATH)).deriveFont(15f);
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(FONT_PATH)));
		} 
		catch (IOException | FontFormatException e) 
		{
			log.log( Level.SEVERE, e.toString(), e);
		}
		return customFont;
	}
	
	/**
	 * Append allow to uses a JtextPane like a document and insert Strings at the end of file.
	 *
	 * @param doc
	 *            the document can be chatDoc or OutputDoc
	 * @param s
	 *            the string to show
	 * @param color
	 *            the color of the string (optional)
	 */
	private void append(StyledDocument doc, String s, Color color) 
	{		
		if(color != null)
		{
			SimpleAttributeSet keyWord = new SimpleAttributeSet();
			StyleConstants.setForeground(keyWord, color);
			
			try 
			   {
					doc.insertString(doc.getLength(), s, keyWord);
			   } 
			   catch(Exception  exc) 
			   {
				   log.log( Level.SEVERE, exc.toString(), exc);
			   }
		}
		else
		{
		   try 
		   {
			   doc.insertString(doc.getLength(), s, null );
		   } 
		   catch(Exception  exc) 
		   {
			   log.log( Level.SEVERE, exc.toString(), exc);
		   }
		}
		scrollToBottom();
	}
	
	/**
	 * Scroll to bottom the JTextPane when appends new stuffs.
	 */
	private void scrollToBottom()
	{
	    SwingUtilities.invokeLater(()-> outputPane.getVerticalScrollBar().setValue(outputPane.getVerticalScrollBar().getMaximum()) );
	    SwingUtilities.invokeLater(()-> chatPane.getVerticalScrollBar().setValue(chatPane.getVerticalScrollBar().getMaximum()) );
	}
	
	/**
	 * get the player text written in the corresponding field<br>
	 * and send it into a ReplyMessage to the handler.
	 */
	private void sendOutput()
	{
		if(this.yourTurn)
		{
			this.append(docOut, commandField.getText(), Color.GRAY);
			
			setChanged();
			this.notifyObservers(new ReplyMessage(commandField.getText()));
			this.commandField.setText("");
			this.yourTurn = false;
		}
	}
	
	/**
	 *get the player text written in the chat field<br>
	 * and send it into a ChatMessage to the handler<br>
	 * and then to all other players in the game
	 */
	private void sendChatMessage()
	{		
		if(!this.gameReady)
		{
			this.append(docChat, "[Warning] Game not ready yet, wait untill the match begins.\n", Color.RED);
			this.chatField.setText("");
		}
		else
		{
			setChanged();
			this.notifyObservers(new ChatMessage("[ID: " + this.playerID + " Name: " + this.playerName + "]: ", this.playerColor,  chatField.getText()));
			this.chatField.setText("");
		}
	}
	
	
/************************************************** UPDATE: INPUT **************************************************/
	
	
	/**
	 * check the type of the message received from the server.
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		if( arg instanceof InfoMessageType )
		{
			this.gameReady = true;
			this.append(docOut, "\n" + arg.toString(), null);
		}
		else if( arg instanceof InfoMessageInitializeType )
			this.append(docOut, "\n" + arg.toString(), null);
		else if( arg instanceof BroadcastMessage )
			this.append(docOut, "\n" + arg.toString(), Color.GREEN);
		else if( arg instanceof ErrorMessage )
			this.append(docOut, "\n" + arg.toString(), Color.RED);
		else if( arg instanceof RequestMessage )
		{
			this.append(docOut, "\n" + arg.toString() + "\n-> ", Color.BLUE);
			this.yourTurn = true;
		}
		else if( arg instanceof FinishMessage )
		{
			this.append(docOut, "\n" + arg.toString(), Color.RED);
			this.yourTurn = false;
		}
		else if( arg instanceof InitializePlayerMessage )
		{
			this.playerID = ((InitializePlayerMessage)arg).getPlayerID()+1;
			if( "".equals(((InitializePlayerMessage)arg).getPlayerName()) )
				this.playerName = "#NoName";
			else
				this.playerName = ((InitializePlayerMessage)arg).getPlayerName();
			this.playerColor = ((InitializePlayerMessage)arg).getPlayerColor();
			
			playerInfo.setPlayerName(this.playerName);
			playerInfo.setPlayerID(this.playerID);
		}
		else if( arg instanceof ChatMessage )
		{
			this.append(docChat, "\n" + ((ChatMessage)arg).senderHeader(), ((ChatMessage)arg).senderColor());
			this.append(docChat, arg.toString(), null);
		}	
		
		checkUpdateGUI( arg );
	}
	
	/**
	 * Check update GUI message and rivalidate it .
	 *
	 * @param arg
	 *            the message
	 */
	private void checkUpdateGUI( Object arg )
	{
		if( arg instanceof UpdatePlayerMessage )
		{
			UpdatePlayerMessage upm = (UpdatePlayerMessage)arg;
			playerInfo.setAssistant(upm.getInfos()[0]);
			playerInfo.setEmporiums(upm.getInfos()[1]);
			playerInfo.setPoliticCard(upm.getInfos()[2]);
			playerInfo.setKingInfo(upm.getInfos()[3], upm.getInfos()[4]);
			playerInfo.setVictoryTrack(upm.getInfos()[5]);
			playerInfo.setRichnessTrack(upm.getInfos()[6]);
			playerInfo.setNobilityTrack(upm.getInfos()[7]);
			
			this.info.setVisible(true);
			this.about.setVisible(false);
		}
		else if( arg instanceof UpdateMap )
		{
			this.mapInfo.setMap(((UpdateMap)arg).getMap());
			this.map.setVisible(true);
		}	
		else if( arg instanceof UpdateBalcony )
		{
			UpdateBalcony upm = (UpdateBalcony)arg;
			
			this.balInfo.setKingBalcony(upm.getInfos()[0]);
			this.balInfo.setCoastBalcony(upm.getInfos()[1]);
			this.balInfo.setHillBalcony(upm.getInfos()[2]);
			this.balInfo.setMountBalcony(upm.getInfos()[3]);
			
			this.balconies.setVisible(true);
		}
		else if( arg instanceof UpdateTimer )
		{
			UpdateTimer upm = (UpdateTimer)arg;
			
			if("start".equals(upm.getAction()))
				this.timerInfo.startTimer(upm.getTimer());
			if("stop".equals(upm.getAction()))
				this.timerInfo.stopTimer();
			this.balconies.setVisible(true);
			
			this.timer.setVisible(true);
		}
	}
	

/************************************************** LISTENER **************************************************/
	

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * check the pressure of the buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object action = e.getSource();
		
		if(enterButton == action)
			sendOutput();
		if(chatEnterButton == action)
			sendChatMessage();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		/* Empty method. */
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		/* Empty method. */	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) 
	{
		Object action = e.getSource();
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER && action == commandField)
			this.sendOutput();
		if(e.getKeyCode() == KeyEvent.VK_ENTER && action == chatField)
			this.sendChatMessage();
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		/* Empty method. */	
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if(JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to quit?", "Really Closing?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
		{
			if(!this.gameReady)
			{
				setChanged();
				this.notifyObservers(new ReplyMessage("ExitInit"));
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			else
			{
				setChanged();
				this.notifyObservers(new ChatMessage("*** Name: " + this.playerName + " ID: " + this.playerID + " DISCONNECTED ***", this.playerColor,  chatField.getText()));
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e){
		/* Empty method. */	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		/* Empty method. */	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		/* Empty method. */	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		/* Empty method. */	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		/* Empty method. */	
	}
}
