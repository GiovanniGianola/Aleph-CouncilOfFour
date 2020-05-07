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
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

/**
 * The Class PlayerPanel provide a panel with useful player informations.
 */
public class PlayerPanel extends JPanel 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1329355055060120450L;
	
	/** The Constant COIN_ICON_PATH. */
	private static final String COIN_ICON_PATH = "/images/wide/COIN.png";
	
	/** The Constant VICTORY_ICON_PATH. */
	private static final String VICTORY_ICON_PATH = "/images/wide/VICTORY.png";
	
	/** The Constant NOBILITY_ICON_PATH. */
	private static final String NOBILITY_ICON_PATH = "/images/wide/NOBILITY.png";
	
	/** The Constant PLAYER_ICON_PATH. */
	private static final String PLAYER_ICON_PATH = "/images/wide/defaultIcon.png";
	
	/** The Constant ASSISTANT_ICON_PATH. */
	private static final String ASSISTANT_ICON_PATH = "/images/wide/ASSISTANT.png";
	
	/** The Constant EMPORIUM_ICON_PATH. */
	private static final String EMPORIUM_ICON_PATH = "/images/wide/emporium.png";
	
	/** The Constant KING_ICON_PATH. */
	private static final String KING_ICON_PATH = "/images/wide/icon.png";
	
	/** The track panel. */
	private JPanel trackPanel;
	
	/** The icon coin label. */
	private JLabel iconCoinLabel;
	
	/** The icon victory label. */
	private JLabel iconVictoryLabel;
	
	/** The icon nobility label. */
	private JLabel iconNobilityLabel;
	
	/** The coin label. */
	private JLabel coinLabel;
	
	/** The victory label. */
	private JLabel victoryLabel;
	
	/** The nobility label. */
	private JLabel nobilityLabel;
	
	/** The pl panel. */
	private JPanel plPanel;
	
	/** The id player label. */
	private JLabel idPlayerLabel;
	
	/** The name player label. */
	private JLabel namePlayerLabel;
	
	/** The icon player label. */
	private JLabel iconPlayerLabel;
	
	/** The info panel. */
	private JPanel infoPanel;
	
	/** The icon assistant label. */
	private JLabel iconAssistantLabel;
	
	/** The assistant label. */
	private JLabel assistantLabel;
	
	/** The icon emporium label. */
	private JLabel iconEmporiumLabel;
	
	/** The emporium label. */
	private JLabel emporiumLabel;
	
	/** The icon king label. */
	private JLabel iconKingLabel;
	
	/** The king label. */
	private JLabel kingLabel;
	
	/** The politic card label. */
	private JLabel politicCardLabel;
	
	/** The info player label. */
	private JLabel infoPlayerLabel;
	
	/** The info track label. */
	private JLabel infoTrackLabel;
	
	/** The custom font. */
	private Font customFont;
	
	/** The player card scroll panel. */
	private JScrollPane playerCardScrollPanel;
		
	/**
	 * Instantiates a new player panel.
	 *
	 * @param font
	 *            the font
	 */
	public PlayerPanel(Font font) 
	{
		setLayout(null);
		
		this.customFont = font;
		
		this.createplPanel();
		this.createInfoPanel();
		
		
		this.createTrackPanel();
		
		this.setVisible(true);
	}
	
	/**
	 * Initialize the current player panel informations
	 *
	 * @return the j panel
	 */
	private JPanel createplPanel()
	{
		plPanel = new JPanel();
		plPanel.setBorder(new MatteBorder(2, 10, 2, 2, SystemColor.windowBorder));
		plPanel.setOpaque(false);
		plPanel.setBackground(new Color(245, 245, 245));
		plPanel.setBounds(27, 13, 490, 80);
		add(plPanel);
		plPanel.setLayout(null);
		
		idPlayerLabel = new JLabel("Player ID:");
		idPlayerLabel.setBounds(87, 34, 130, 16);
		idPlayerLabel.setFont(customFont);
		plPanel.add(idPlayerLabel);
		
		namePlayerLabel = new JLabel("Player Name:");
		namePlayerLabel.setBounds(229, 34, 273, 16);
		namePlayerLabel.setFont(customFont);
		plPanel.add(namePlayerLabel);
		
		iconPlayerLabel = new JLabel("");
		iconPlayerLabel.setIcon(new ImageIcon(getClass().getResource(PLAYER_ICON_PATH)));
		iconPlayerLabel.setBounds(12, 13, 69, 61);
		plPanel.add(iconPlayerLabel);
		
		return plPanel;
	}
	
	/**
	 * Creates the info panel thath show current player<br>
	 * assistants<br>
	 * emporiums<br>
	 * king position<br>
	 * politic cards.
	 *
	 * @return the j panel
	 */
	private JPanel createInfoPanel()
	{
		infoPlayerLabel = new JLabel("<html><font color = black size = 6><centre>Player Status</centre></font></html>");
		infoPlayerLabel.setBounds(12, 95, 243, 26);
		infoPlayerLabel.setFont(customFont);
		add(infoPlayerLabel);
		
		infoPanel = new JPanel();
		infoPanel.setBorder(new MatteBorder(2, 10, 2, 2, new Color(100, 100, 100)));
		infoPanel.setOpaque(false);
		infoPanel.setBackground(new Color(245, 245, 245));
		infoPanel.setBounds(27, 120, 490, 89);
		add(infoPanel);
		infoPanel.setLayout(null);
		
		iconAssistantLabel = new JLabel("");
		iconAssistantLabel.setIcon(new ImageIcon(getClass().getResource(ASSISTANT_ICON_PATH)));
		iconAssistantLabel.setBounds(22, 0, 15, 45);
		infoPanel.add(iconAssistantLabel);
		
		assistantLabel = new JLabel("");
		assistantLabel.setBounds(58, 13, 56, 16);
		assistantLabel.setFont(customFont);
		infoPanel.add(assistantLabel);
		
		iconEmporiumLabel = new JLabel("");
		iconEmporiumLabel.setIcon(new ImageIcon(getClass().getResource(EMPORIUM_ICON_PATH)));
		iconEmporiumLabel.setBounds(360, 5, 20, 33);
		infoPanel.add(iconEmporiumLabel);
		
		emporiumLabel = new JLabel("");
		emporiumLabel.setBounds(397, 13, 56, 16);
		emporiumLabel.setFont(customFont);
		infoPanel.add(emporiumLabel);
		
		iconKingLabel = new JLabel("");
		iconKingLabel.setIcon(new ImageIcon(getClass().getResource(KING_ICON_PATH)));
		iconKingLabel.setBounds(144, 5, 32, 32);
		infoPanel.add(iconKingLabel);
		
		kingLabel = new JLabel("");
		kingLabel.setBounds(188, 0, 141, 45);
		kingLabel.setFont(customFont);
		infoPanel.add(kingLabel);
		
		playerCardScrollPanel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		playerCardScrollPanel.setOpaque(false);
		playerCardScrollPanel.getViewport().setOpaque(false);
		playerCardScrollPanel.setBorder(null);
		playerCardScrollPanel.setBounds(22, 42, 456, 40);
		infoPanel.add(playerCardScrollPanel);
		
		politicCardLabel = new JLabel("Politic Card:");
		playerCardScrollPanel.setViewportView(politicCardLabel);
		politicCardLabel.setFont(customFont);
		
		return infoPanel;
	}
	
	/**
	 * Creates the track panel that show the current player position<br>
	 * in the three tracks:<br>
	 * richness<br>
	 * nobility<br>
	 * victory.
	 *
	 * @return the j panel
	 */
	private JPanel createTrackPanel()
	{
		infoTrackLabel = new JLabel("<html><font color = black size = 6><centre>Victory Rush</centre></font></html>");
		infoTrackLabel.setBounds(12, 209, 220, 26);
		infoTrackLabel.setFont(customFont);
		add(infoTrackLabel);
		
		trackPanel = new JPanel();
		trackPanel.setBorder(new MatteBorder(2, 10, 2, 2, new Color(100, 100, 100)));
		trackPanel.setOpaque(false);
		trackPanel.setBackground(new Color(245, 245, 245));
		trackPanel.setBounds(27, 235, 490, 103);
		add(trackPanel);
		trackPanel.setLayout(null);
		
		iconCoinLabel = new JLabel("");
		iconCoinLabel.setBounds(12, 13, 86, 80);
		trackPanel.add(iconCoinLabel);
		iconCoinLabel.setIcon(new ImageIcon(getClass().getResource(COIN_ICON_PATH)));
		
		iconVictoryLabel = new JLabel("");
		iconVictoryLabel.setBounds(196, 13, 61, 80);
		trackPanel.add(iconVictoryLabel);
		iconVictoryLabel.setIcon(new ImageIcon(getClass().getResource(VICTORY_ICON_PATH)));
		
		iconNobilityLabel = new JLabel("");
		iconNobilityLabel.setBounds(354, 13, 79, 80);
		trackPanel.add(iconNobilityLabel);
		iconNobilityLabel.setIcon(new ImageIcon(getClass().getResource(NOBILITY_ICON_PATH)));
		
		coinLabel = new JLabel("");
		coinLabel.setBounds(110, 48, 56, 16);
		coinLabel.setFont(customFont);
		trackPanel.add(coinLabel);
		
		victoryLabel = new JLabel("");
		victoryLabel.setBounds(269, 48, 56, 16);
		victoryLabel.setFont(customFont);
		trackPanel.add(victoryLabel);
		
		nobilityLabel = new JLabel("");
		nobilityLabel.setBounds(438, 48, 56, 16);
		nobilityLabel.setFont(customFont);
		trackPanel.add(nobilityLabel);
		
		return trackPanel;
	}
	
	/**
	 * Show the player name.
	 *
	 * @param name
	 *            the new player name
	 */
	protected void setPlayerName(String name)
	{
		namePlayerLabel.setText("Player Name: " + name);
	}
	
	/**
	 * Show the player ID.
	 *
	 * @param id
	 *            the new player ID
	 */
	protected void setPlayerID(Integer id)
	{
		idPlayerLabel.setText("Player ID: " + id.toString());
	}
	
	/**
	 * Show the assistant.
	 *
	 * @param nAssistant
	 *            the new assistant
	 */
	protected void setAssistant(String nAssistant)
	{
		assistantLabel.setText(nAssistant);
	}
	
	/**
	 * Show the emporiums.
	 *
	 * @param nEmporiums
	 *            the new emporiums
	 */
	protected void setEmporiums(String nEmporiums)
	{
		emporiumLabel.setText(nEmporiums);
	}
	
	/**
	 * Show the politic card.
	 *
	 * @param politicCard
	 *            the new politic card
	 */
	protected void setPoliticCard(String politicCard)
	{
		politicCardLabel.setText("Politic Card: " + politicCard);
	}
	
	/**
	 * Show the king info.
	 *
	 * @param nameKing
	 *            the name king
	 * @param cityKing
	 *            the city king
	 */
	protected void setKingInfo(String nameKing, String cityKing)
	{
		kingLabel.setText("<html>" + cityKing + "<br>" + nameKing + "</html>");
	}
	
	/**
	 * Show the victory track.
	 *
	 * @param pos
	 *            the new victory track
	 */
	protected void setVictoryTrack(String pos)
	{
		victoryLabel.setText(pos);
	}
	
	/**
	 * Show the richness track.
	 *
	 * @param pos
	 *            the new richness track
	 */
	protected void setRichnessTrack(String pos)
	{
		coinLabel.setText(pos);
	}
	
	/**
	 * Show the nobility track.
	 *
	 * @param pos
	 *            the new nobility track
	 */
	protected void setNobilityTrack(String pos)
	{
		nobilityLabel.setText(pos);
	}
}
