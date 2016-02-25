package ca.uwo.csd.cs2212.team08;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class DashBoardPanel extends JPanel {
	private static final int WIDTH = 111;
	private static final int HEIGHT = 123;

	/**
	 * creates a new panel for the daily dashboard
	 * @param x the starting x position
	 * @param y the starting y position
	 * @return 
	 */
	public DashBoardPanel(int x, int y)
	{
		super();
		Rectangle r = new Rectangle(x, y, WIDTH, HEIGHT);
		this.setBounds(r);
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
