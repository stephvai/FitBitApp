package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;

/**
 * create a frame to store the minutes
 *
 */
public class Minutes extends JFrame {

	private JPanel contentPane;
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(0,0,0,60);
	private Color borderColor = new Color(121, 189, 154);
	private Color titleColor = new Color(11, 72, 107);
	private Color white = Color.white;
	private static final String backImage = "src/main/resources/images/arrowLeft4.png";
	private APIData apiData;
	private String date;
	/**
	 * create the frame
	 * @param date pass in the current date
	 * @param paramAPIData pass in the current APIData
	 */
	public Minutes(String date, APIData paramAPIData) {
		
		/*
		 * Graph
		 */
		
		/*-------------------------------------------*
		 * Default size and settings for all windows
		 *-------------------------------------------*/
		setTitle("Team08 Fitbit");
		setSize(1024, 768);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		this.date = date;
		this.apiData = paramAPIData;
		
		/*---------------------------------------------------*
		 * make the panel where all information will be shown
		 *---------------------------------------------------*/
		contentPane = new JPanel() {
		 @Override
		 protected void paintComponent(Graphics g) {
			 BufferedImage img = null;
			 try {
				img = ImageIO.read(new File("src/main/resources/images/track.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(img, 0,0, null);
		 }
	 };
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(bgColor);
		
		/*-----------------------------------------------------*
		 * Title of the "panel" which will be called Minutes
		 *-----------------------------------------------------*/
		JLabel titleMinutes = new JLabel("Minutes");
		titleMinutes.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		titleMinutes.setHorizontalAlignment(SwingConstants.CENTER);
		titleMinutes.setBounds(80, 21, 837, 84);
		contentPane.add(titleMinutes);
		titleMinutes.setForeground(white);

		/*-----------------------------------------*/
		//create a back button to return to dash board
		/*----------------------------------------*/
		JLabel backArrow = new JLabel();
		backArrow.setIcon(new ImageIcon(backImage));
		backArrow.setBounds(0, 0, 48, 48);
		//backArrow.setVisible(true);
		contentPane.add(backArrow);
		backArrow.addMouseListener(new MouseAdapter() {
			//private String date;

			@Override
			public void mouseClicked(MouseEvent arg0) {
				//what to do on button click
				home();
				dispose();
			}
		});
		/*-------------------------------------*/
		//the label to set the last updated time
		/*-------------------------------------*/
		 Calendar cal = Calendar.getInstance();
		 final JLabel lblDataUpdate = new JLabel("Last updated: " + cal.getTime().toString());
		 lblDataUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		 lblDataUpdate.setForeground(white);
		 lblDataUpdate.setBounds(51, 660, 923, 37);
		 contentPane.add(lblDataUpdate);
		 
		/*------------------------------------------*
		 * Panel where active minutes will be shown
		 *------------------------------------------*/
		JPanel pnlActiveMinutes = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		pnlActiveMinutes.setBounds(80, 138, 349, 461);
		contentPane.add(pnlActiveMinutes);
		pnlActiveMinutes.setLayout(null);
		pnlActiveMinutes.setOpaque(false);
		pnlActiveMinutes.setBackground(pannelColor);
		//pnlActiveMinutes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		/*----------------------------------------*
		 * Title of the panel 
		 *----------------------------------------*/
		JLabel activeTitle = new JLabel("<html> Active Minutes</html>");
		activeTitle.setBounds(52, 21, 229, 80);
		activeTitle.setOpaque(false);
		activeTitle.setForeground(white);
		//activeTitle.setBackground(pannelColor);
		pnlActiveMinutes.add(activeTitle);
		
		activeTitle.setFont(new Font("Trebuchet MS", Font.PLAIN, 35)); //font is here
		activeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		contentPane.repaint();
		/*-------------------------------------------------------------*
		 * information shown for lightly active minutes
		 *-------------------------------------------------------------*/
		JLabel activeMinutesValue = new JLabel("<html> Today you were lightly active for: <strong>"+ apiData.getLightlyActiveMin() +"</strong> minutes. </html>");
		activeMinutesValue.setBounds(24, 88, 300, 142);
		activeMinutesValue.setOpaque(false);
		activeMinutesValue.setForeground(white);
		activeMinutesValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		activeMinutesValue.setHorizontalAlignment(SwingConstants.CENTER);
		pnlActiveMinutes.add(activeMinutesValue);
		contentPane.repaint();
		
		/*-------------------------------------------------------------*
		 * information shown for fairly active minutes
		 *-------------------------------------------------------------*/
		JLabel fairlyActiveMinutesValue = new JLabel("<html> Today you were fairly active for: <strong>"+ apiData.getFairlyActiveMin()+ "</strong> minutes. </html>");
		fairlyActiveMinutesValue.setBounds(24, 213, 300, 123);
		fairlyActiveMinutesValue.setForeground(white);
		fairlyActiveMinutesValue.setBackground(pannelColor);
		pnlActiveMinutes.add(fairlyActiveMinutesValue);
		fairlyActiveMinutesValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		fairlyActiveMinutesValue.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.repaint();
		
		/*-------------------------------------------------------------*
		 * information shown for very active minutes, maybe we could add it all up?
		 *-------------------------------------------------------------*/
		JLabel veryActiveMinutesValue = new JLabel("<html> Today you were very active for: <strong>"+ apiData.getVeryActiveMin()+ "</strong> minutes. </html>");
		veryActiveMinutesValue.setBounds(24,332, 300, 123);
		veryActiveMinutesValue.setForeground(white);
		veryActiveMinutesValue.setBackground(pannelColor);
		pnlActiveMinutes.add(veryActiveMinutesValue);
		veryActiveMinutesValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		veryActiveMinutesValue.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*--------------------------------------------*
		 * panel where sedentary minutes will be shown
		 *--------------------------------------------*/
		JPanel pnlSedentMinutes = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		pnlSedentMinutes.setBounds(570, 138, 349, 200);
		pnlSedentMinutes.setLayout(null);
		pnlSedentMinutes.setOpaque(false);
		pnlSedentMinutes.setBackground(pannelColor);
		pnlSedentMinutes.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(pnlSedentMinutes);
		contentPane.repaint();
		
		/*------------------------------------------*
		 * Title of sedentary panel 
		 *------------------------------------------*/
		JLabel sedentTitle = new JLabel("<html>Sedentary Minutes</html>");
		sedentTitle.setBounds(22, 21, 321, 80);
		sedentTitle.setForeground(white);
		sedentTitle.setOpaque(false);
		sedentTitle.setFont(new Font("Trebuchet MS", Font.PLAIN, 35)); //font is here
		sedentTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pnlSedentMinutes.add(sedentTitle);
		contentPane.repaint();
		
		/*------------------------------------------*
		 * information for sedentary minutes 
		 *------------------------------------------*/
		JLabel sedentMinutesValue = new JLabel("<html> Today you were not active for: <strong>" + apiData.getSendentaryMinutes() +"</strong> minutes. </html>");
		sedentMinutesValue.setBounds(22,93, 321,96);
		sedentMinutesValue.setOpaque(false);
		sedentMinutesValue.setForeground(white);
		sedentMinutesValue.setFont(new Font("Trebouchet MS", Font.PLAIN, 25));
		sedentMinutesValue.setHorizontalAlignment(SwingConstants.CENTER);
		pnlSedentMinutes.add(sedentMinutesValue);
		contentPane.repaint();
		
		PGraph piGraph = new PGraph(apiData);
		piGraph.setBounds(570, 375, 349, 225);
		contentPane.add(piGraph);
		
		
	}
	/**
	 * returns the user to their daily dash board
	 */
	public void home()
	{
		MainScreen main = new MainScreen(this.date, apiData);
		main.setVisible(true);
	}
}
