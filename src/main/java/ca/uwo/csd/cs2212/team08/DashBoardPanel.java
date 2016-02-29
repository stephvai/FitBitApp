package ca.uwo.csd.cs2212.team08;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * a dash board object that can be used to create the main panels on the daily dashboard
 * @author James-Featherstone
 *
 */
public class DashBoardPanel extends JPanel {
	
	//the height and width of all the panels
	private static final int WIDTH = 265;
	private static final int HEIGHT = 155;
	//Color scheme
	private Color pannelColor = new Color(168,219,168);
	private Color borderColor = new Color(121,189,154);
	
	//a rectangle variable to store the dimensions of the panel
	private Rectangle r;

	/**
	 * creates a new panel for the daily dashboard
	 * @param x the starting x position
	 * @param y the starting y position
	 * @return 
	 */
	public DashBoardPanel(int x, int y)
	{
		super();
		//create a new rectangle with the passed in values
		r = new Rectangle(x, y, WIDTH, HEIGHT);
		this.setBounds(r);
		//set the background color of the panel
   	 	this.setBackground(pannelColor);
   	 	this.setBorder(BorderFactory.createLineBorder(borderColor));
	}
	
	
	/* code that can be used to track for clicks
	   panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			//what to do on button click here!
			}
		});
	 */
}
