package ca.uwo.csd.cs2212.team08;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.io.Serializable;
import java.awt.*;

/**
 * a dash board object that can be used to create the main panels on the daily dashboard
 */
public class DashBoardPanel extends JPanel {
	
	//the height and width of all the panels
	private static final int WIDTH = 265;
	private static final int HEIGHT = 155;
	//Color scheme
	//private Color pannelColor = new Color(168,219,168);
	//private Color borderColor = new Color(121,189,154);
	private Color borderColor = new Color(255,255,255);
	private Color pannelColor = new Color(205,206,206);
	private Color transParentColor = new Color(0,0,0,60);
	
	//a rectangle variable to store the dimensions of the panel
	private Rectangle r;

	/**
	 * creates a new panel for the daily dash board
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
   	 	this.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	}
}
