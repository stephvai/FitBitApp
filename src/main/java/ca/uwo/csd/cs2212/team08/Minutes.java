package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Minutes extends JFrame {

	private JPanel contentPane;
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(168, 219, 168);
	private Color borderColor = new Color(121, 189, 154);
	private Color titleColor = new Color(11, 72, 107);
	private Color white = Color.white;
	private static final String backImage = "src/main/resources/images/arrowLeft4.png";
	private APIData apiData;
	private String date;
	/**
	 * Create the frame.
	 */
	public Minutes(String date, APIData paramAPIData) {
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
		contentPane = new JPanel();
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
				//MainScreen main = new MainScreen(this.date, apiData);
				//main.setVisible(true);
				dispose();
			}
		});
		
		/*------------------------------------------*
		 * Panel where active minutes will be shown
		 *------------------------------------------*/
		JPanel pnlActiveMinutes = new JPanel();
		pnlActiveMinutes.setBounds(80, 138, 349, 461);
		contentPane.add(pnlActiveMinutes);
		pnlActiveMinutes.setLayout(null);
		pnlActiveMinutes.setBackground(pannelColor);
		pnlActiveMinutes.setBorder(BorderFactory.createLineBorder(borderColor));
		
		/*----------------------------------------*
		 * Title of the panel 
		 *----------------------------------------*/
		JLabel activeTitle = new JLabel("<html> Active Minutes</html>");
		activeTitle.setBounds(52, 21, 229, 80);
		pnlActiveMinutes.add(activeTitle);
		activeTitle.setFont(new Font("Trebuchet MS", Font.PLAIN, 35)); //font is here
		activeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*-------------------------------------------------------------*
		 * information shown for active minutes minus the API currently
		 *-------------------------------------------------------------*/
		JLabel activeMinutesValue = new JLabel("<html> Today you were active for: "+ "        500        " +" minutes. </html>");
		activeMinutesValue.setBounds(24, 152, 300, 183);
		pnlActiveMinutes.add(activeMinutesValue);
		activeMinutesValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		activeMinutesValue.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*--------------------------------------------*
		 * panel where sedentary minutes will be shown
		 *--------------------------------------------*/
		JPanel pnlSedentMinutes = new JPanel();
		pnlSedentMinutes.setBounds(570, 138, 349, 461);
		contentPane.add(pnlSedentMinutes);
		pnlSedentMinutes.setLayout(null);
		pnlSedentMinutes.setBackground(pannelColor);
		pnlSedentMinutes.setBorder(BorderFactory.createLineBorder(borderColor));
		
		/*------------------------------------------*
		 * Title of sedentary panel 
		 *------------------------------------------*/
		JLabel sedentTitle = new JLabel("<html>Sedentary Minutes</html>");
		sedentTitle.setBounds(22, 21, 321, 80);
		pnlSedentMinutes.add(sedentTitle);
		sedentTitle.setFont(new Font("Trebuchet MS", Font.PLAIN, 35)); //font is here
		sedentTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*----------------------------------------------------------*
		 * information for sedentary minutes minus the API currently
		 *----------------------------------------------------------*/
		JLabel sedentMinutesValue = new JLabel("<html> Today you were not active for: " + "500 \n"+ " minutes. </html>");
		sedentMinutesValue.setBounds(22,151, 321,182);
		pnlSedentMinutes.add(sedentMinutesValue);
		sedentMinutesValue.setFont(new Font("Trebouchet MS", Font.PLAIN, 25));
		sedentMinutesValue.setHorizontalAlignment(SwingConstants.CENTER);
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
