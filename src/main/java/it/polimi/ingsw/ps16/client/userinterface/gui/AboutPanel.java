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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

/**
 * The Class AboutPanel provide a jPanel with some informations about<br>
 * the project and about developers.
 */
public class AboutPanel extends JPanel 
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7598156308536376398L;

	/** The Constant BACKGROUND_ABOUT_PATH. */
	private static final String BACKGROUND_ABOUT_PATH = "/images/brickbg.jpg";
	
	/** The about label. */
	private JLabel aboutLabel;
	
	/** The about bg label. */
	private JLabel aboutBgLabel;
	
	/** The custom font. */
	private Font customFont;
	
	/**
	 * Instantiates a new about panel.
	 *
	 * @param font
	 *            the font
	 */
	public AboutPanel(Font font) 
	{
		this.customFont = font;
		this.initialize();
		
		this.setVisible(true);
	}
	
	/**
	 * Initialize the about Panel.
	 */
	private void initialize()
	{
		setOpaque(false);
		setBounds(97, 29, 322, 351);
		setLayout(null);
		
		aboutLabel = new JLabel("<html><font color = white size = 9><center><strong>Council Of Four</strong></center></font><font color = black size = 5><br><p align=center><strong>Written by:</p><p align=center>Giovanni Gianola<br>Filippo Leveni<br>Valentina Ionata</p><br><p align=center>Designed by:</p><p align=center>CranioCreations</p><br><p align=center>Software Engineering Project<br>Politecnico di Milano<br>Academic year 2015/2016</strong></p></font></html>");
		aboutLabel.setBounds(12, 0, 298, 351);
		add(aboutLabel);
		aboutLabel.setFont(customFont);
		
		aboutBgLabel = new JLabel("");
		aboutBgLabel.setBorder(new MatteBorder(2, 2, 2, 2, new Color(240, 255, 240)));
		aboutBgLabel.setIcon(new ImageIcon(getClass().getResource(BACKGROUND_ABOUT_PATH)));
		aboutBgLabel.setBounds(0, 0, 320, 351);
		add(aboutBgLabel);
	}

}
