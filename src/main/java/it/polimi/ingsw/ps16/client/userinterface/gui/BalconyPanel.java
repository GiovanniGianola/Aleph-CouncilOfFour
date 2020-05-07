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

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

/**
 * The Class BalconyPanel provide a panel that show 4 different councillors balconies.
 */
public class BalconyPanel extends JPanel 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1346425997007606163L;
	
	/** The Constant PATH. */
	private static final String PATH = "/images/";
	
	/** The custom font. */
	Font customFont;

	/** The mountain bal panel. */
	private JPanel mountainBalPanel;
	
	/** The m 1 label. */
	private JLabel m1Label;
	
	/** The m 2 label. */
	private JLabel m2Label;
	
	/** The m 3 label. */
	private JLabel m3Label;
	
	/** The m 4 label. */
	private JLabel m4Label;
	
	/** The mountain label. */
	private JLabel mountainLabel;

	/** The hill bal panel. */
	private JPanel hillBalPanel;
	
	/** The h 1 label. */
	private JLabel h1Label;
	
	/** The h 2 label. */
	private JLabel h2Label;
	
	/** The h 3 label. */
	private JLabel h3Label;
	
	/** The h 4 label. */
	private JLabel h4Label;
	
	/** The hill label. */
	private JLabel hillLabel;

	/** The coast bal panel. */
	private JPanel coastBalPanel;
	
	/** The c 1 label. */
	private JLabel c1Label;
	
	/** The c 2 label. */
	private JLabel c2Label;
	
	/** The c 3 label. */
	private JLabel c3Label;
	
	/** The c 4 label. */
	private JLabel c4Label;
	
	/** The coast label. */
	private JLabel coastLabel;

	/** The king bal panel. */
	private JPanel kingBalPanel;
	
	/** The k 1 label. */
	private JLabel k1Label;
	
	/** The k 2 label. */
	private JLabel k2Label;
	
	/** The k 3 label. */
	private JLabel k3Label;
	
	/** The k 4 label. */
	private JLabel k4Label;
	
	/** The king label. */
	private JLabel kingLabel;
	
	/** The mountain panel. */
	private JPanel mountainPanel;
	
	/** The hill panel. */
	private JPanel hillPanel;
	
	/** The coast panel. */
	private JPanel coastPanel;
	
	/** The king panel. */
	private JPanel kingPanel;

	/**
	 * Instantiates a new balcony panel.
	 *
	 * @param font
	 *            the font
	 */
	public BalconyPanel(Font font) 
	{
		this.customFont = font; 
		
		this.initialize();
		
		this.initKing();
		this.initCoast();
		this.initHill();
		this.initMount();
	}
	
	/**
	 * Initialize the mountain balcony with 5 Jlabel
	 */
	private void initMount() 
	{
		
		mountainPanel = new JPanel();
		mountainPanel.setOpaque(false);
		mountainPanel.setBounds(723, 0, 236, 50);
		add(mountainPanel);
		mountainPanel.setLayout(null);
		mountainBalPanel = new JPanel();
		mountainBalPanel.setBounds(68, 0, 104, 50);
		mountainPanel.add(mountainBalPanel);
		mountainBalPanel.setBorder(new MatteBorder(2, 2, 2, 2, SystemColor.windowBorder));
		mountainBalPanel.setOpaque(false);
		mountainBalPanel.setLayout(null);
		
		m1Label = new JLabel("");
		m1Label.setBounds(-14, 0, 54, 50);
		mountainBalPanel.add(m1Label);
		
		m2Label = new JLabel("");
		m2Label.setBounds(12, 0, 54, 50);
		mountainBalPanel.add(m2Label);
		
		m3Label = new JLabel("");
		m3Label.setBounds(37, 0, 54, 50);
		mountainBalPanel.add(m3Label);
		
		m4Label = new JLabel("");
		m4Label.setBounds(60, 0, 54, 50);
		mountainBalPanel.add(m4Label);
		
		mountainLabel = new JLabel("Mount");
		mountainLabel.setBounds(0, 17, 68, 16);
		mountainPanel.add(mountainLabel);
		mountainLabel.setFont(customFont);
	}

	/**
	 * Initialize the hill balcony with 5 Jlabel
	 */
	private void initHill() 
	{
		
		hillPanel = new JPanel();
		hillPanel.setOpaque(false);
		hillPanel.setBounds(487, 0, 236, 50);
		add(hillPanel);
		hillPanel.setLayout(null);
		hillBalPanel = new JPanel();
		hillBalPanel.setBounds(60, 0, 104, 50);
		hillPanel.add(hillBalPanel);
		hillBalPanel.setBorder(new MatteBorder(2, 2, 2, 2, SystemColor.windowBorder));
		hillBalPanel.setOpaque(false);
		hillBalPanel.setLayout(null);
		
		h1Label = new JLabel("");
		h1Label.setBounds(-14, 0, 54, 50);
		hillBalPanel.add(h1Label);
		
		h2Label = new JLabel("");
		h2Label.setBounds(12, 0, 54, 50);
		hillBalPanel.add(h2Label);
		
		h3Label = new JLabel("");
		h3Label.setBounds(37, 0, 54, 50);
		hillBalPanel.add(h3Label);
		
		h4Label = new JLabel("");
		h4Label.setBounds(60, 0, 54, 50);
		hillBalPanel.add(h4Label);
		
		hillLabel = new JLabel("Hill");
		hillLabel.setBounds(0, 17, 56, 16);
		hillPanel.add(hillLabel);
		hillLabel.setFont(customFont);
	}

	/**
	 * Initialize the cost balcony with 5 Jlabel
	 */
	private void initCoast() 
	{
		
		coastPanel = new JPanel();
		coastPanel.setOpaque(false);
		coastPanel.setBounds(251, 0, 236, 50);
		add(coastPanel);
		coastPanel.setLayout(null);
		coastBalPanel = new JPanel();
		coastBalPanel.setBounds(60, 0, 104, 50);
		coastPanel.add(coastBalPanel);
		coastBalPanel.setBorder(new MatteBorder(2, 2, 2, 2, SystemColor.windowBorder));
		coastBalPanel.setOpaque(false);
		coastBalPanel.setLayout(null);
		
		c1Label = new JLabel("");
		c1Label.setBounds(-14, 0, 54, 50);
		coastBalPanel.add(c1Label);
		
		c2Label = new JLabel("");
		c2Label.setBounds(12, 0, 54, 50);
		coastBalPanel.add(c2Label);
		
		c3Label = new JLabel("");
		c3Label.setBounds(37, 0, 54, 50);
		coastBalPanel.add(c3Label);
		
		c4Label = new JLabel("");
		c4Label.setBounds(60, 0, 54, 50);
		coastBalPanel.add(c4Label);
		
		coastLabel = new JLabel("Coast");
		coastLabel.setBounds(0, 17, 56, 16);
		coastPanel.add(coastLabel);
		coastLabel.setFont(customFont);
	}

	/**
	 * Initialize the king balcony with 5 Jlabel
	 */
	private void initKing() 
	{
		
		kingPanel = new JPanel();
		kingPanel.setOpaque(false);
		kingPanel.setBounds(10, 0, 236, 50);
		add(kingPanel);
		kingPanel.setLayout(null);
		kingBalPanel = new JPanel();
		kingBalPanel.setBounds(60, 0, 104, 50);
		kingPanel.add(kingBalPanel);
		kingBalPanel.setBorder(new MatteBorder(2, 2, 2, 2, SystemColor.windowBorder));
		kingBalPanel.setOpaque(false);
		kingBalPanel.setLayout(null);
		
		k1Label = new JLabel("");
		k1Label.setBounds(-14, 0, 54, 50);
		kingBalPanel.add(k1Label);
		
		k2Label = new JLabel("");
		k2Label.setBounds(12, 0, 54, 50);
		kingBalPanel.add(k2Label);
		
		k3Label = new JLabel("");
		k3Label.setBounds(37, 0, 54, 50);
		kingBalPanel.add(k3Label);
		
		k4Label = new JLabel("");
		k4Label.setBounds(60, 0, 54, 50);
		kingBalPanel.add(k4Label);
		
		kingLabel = new JLabel("King");
		kingLabel.setBounds(0, 17, 56, 16);
		kingPanel.add(kingLabel);
		kingLabel.setFont(customFont);
	}

	/**
	 * Initialize the panel settings
	 */
	private void initialize()
	{
		setOpaque(false);
		setBounds(349, 366, 959, 50);
		setLayout(null);
	}
	
	/**
	 * update ad show king balcony
	 *
	 * @param bal
	 *            the new king balcony
	 */
	public void setKingBalcony(String bal)
	{
		String[] counc = bal.split(";");
		
		k4Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[0] + ".png")));
		k3Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[1] + ".png")));
		k2Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[2] + ".png")));
		k1Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[3] + ".png")));
	}
	
	/**
	 * update ad show coast balcony
	 *
	 * @param bal
	 *            the new coast balcony
	 */
	public void setCoastBalcony(String bal)
	{
		String[] counc = bal.split(";");
		
		c4Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[0] + ".png")));
		c3Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[1] + ".png")));
		c2Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[2] + ".png")));
		c1Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[3] + ".png")));
	}
	
	/**
	 * update ad show hill balcony
	 *
	 * @param bal
	 *            the new hill balcony
	 */
	public void setHillBalcony(String bal)
	{
		String[] counc = bal.split(";");
		
		h4Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[0] + ".png")));
		h3Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[1] + ".png")));
		h2Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[2] + ".png")));
		h1Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[3] + ".png")));
	}
	
	/**
	 * update ad show mountain balcony
	 *
	 * @param bal
	 *            the new mount balcony
	 */
	public void setMountBalcony(String bal)
	{
		String[] counc = bal.split(";");
		
		m4Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[0] + ".png")));
		m3Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[1] + ".png")));
		m2Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[2] + ".png")));
		m1Label.setIcon(new ImageIcon(getClass().getResource(PATH + counc[3] + ".png")));
	}
}
