package team08_app;

import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class DashBoardPanel extends JPanel {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 150;
	
	public DashBoardPanel(int x, int y)
	{
		JLabel lblJ = new JLabel("j");
		Rectangle r = new Rectangle(x, y, WIDTH, HEIGHT);
		lblJ.setBounds(r);
	}
}
