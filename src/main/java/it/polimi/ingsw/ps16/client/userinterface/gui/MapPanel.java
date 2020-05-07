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

import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

/**
 * The Class MapPanel provide a simple panel that shows the map selected from the first player.
 */
public class MapPanel extends JPanel 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2194409191179793667L;
	
	/** The Constant MAP_PATH. */
	private static final String MAP_PATH = "/images/map";
	
	/** The icon map label. */
	private JLabel iconMapLabel;

	/**
	 * Instantiates a new map panel.
	 */
	public MapPanel() 
	{
		this.initialize();
	}
	
	/**
	 * Initialize the panel.
	 */
	private void initialize()
	{
		setBorder(null);
		setBounds(562, 13, 708, 351);
		setLayout(null);
	}
	
	/**
	 * add the map image to the panel according to the player request
	 *
	 * @param map
	 *            the new map
	 */
	public void setMap(String map)
	{
		iconMapLabel = new JLabel("");
		iconMapLabel.setBorder(new MatteBorder(2, 2, 2, 2, SystemColor.windowBorder));
		iconMapLabel.setIcon(new ImageIcon(getClass().getResource(MAP_PATH + map + ".png")));
		iconMapLabel.setBounds(0, 0, 708, 351);
		add(iconMapLabel);
	}
}
