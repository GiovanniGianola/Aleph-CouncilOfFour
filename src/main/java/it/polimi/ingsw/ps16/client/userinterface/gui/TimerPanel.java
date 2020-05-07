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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 * The Class TimerPanel provide a panel with a JscrolBar used to show to the player his current turn time.
 */
public class TimerPanel extends JPanel implements ActionListener
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7173199408617764239L;
	
	/** The custom font. */
	private Font customFont;
	
	/** The timer label. */
	private JLabel timerLabel;
	
	/** The timer bar. */
	private JProgressBar timerBar;
	
	/** The timer. */
	private Timer timer;
	
	/** The Constant INTERVAL. */
	private static final int INTERVAL = 1000;
	
	/** The seconds. */
	private int seconds;
	
	/** The time. */
	private Integer time;
	
	/**
	 * Instantiates a new timer panel.
	 *
	 * @param font
	 *            the font
	 */
	public TimerPanel(Font font) 
	{
		this.customFont = font;
		
		setLayout(null);
		
		this.initialize();
	}

	/**
	 * initialize panel components
	 */
	private void initialize() 
	{
		timerLabel = new JLabel("");
		timerLabel.setBounds(103, 0, 150, 24);
		timerLabel.setFont(customFont);
		add(timerLabel);
		
		timerBar = new JProgressBar();
		timerBar.setBounds(12, 26, 272, 24);
		timerBar.setFont(customFont);
		add(timerBar);
		
	}
	
	/**
	 * Start timer when the current turn of the player begins.
	 *
	 * @param seconds
	 *            the seconds
	 */
	public void startTimer(int seconds)
	{
		this.seconds = seconds;
		timerBar.setValue(0);
		timerBar.setMaximum(this.seconds);
		timerBar.setStringPainted(true);
		
		time = 0;
		
		timer = new Timer(INTERVAL,this);
		timer.start();
	}
	
	/**
	 * Stop timer when the current turn of the player ends.
	 */
	public void stopTimer()
	{
		timer.stop();
		this.timerLabel.setText("Opponent Turn");
		this.timerBar.setValue(0);
		time = 0;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object action = e.getSource();
		
		if(action == timer)
		{
			if(time == this.seconds)
			{
				timer.stop();
				this.timerLabel.setText("Opponent Turn");
				this.timerBar.setValue(0);
				time = 0;
			}
			else
			{
				time++;
				this.timerLabel.setText("Timer: " + (this.seconds - time));
				this.timerBar.setValue(time);
			}
		}
	}
	
	
}
