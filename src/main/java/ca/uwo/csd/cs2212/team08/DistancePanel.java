package ca.uwo.csd.cs2212.team08;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * creates a JFrame that displays the users Distance information
 *
 */
public class DistancePanel extends JFrame {

	//a content pane to store all the components
	private JPanel contentPane;
	//a string to store the current date the user wants
	private String date;
	//constants to store the picture locations
	private static final String backImage = "src/main/resources/images/arrowLeft4.png";
	//use to get the API data
	private APIData apiData;

	//Color Scheme
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(206,206,206);
	private Color borderColor = new Color(121,189,154);
	private Color titleColor = new Color(11,72,107);
	private Color white = Color.white;

	/**
	 * create a distance panel to show user information on their distance traveled
	 * @param date this is the date selected by the user 
	 * @param paramAPIData an instance of the APIData that passes in the fitbit information
	 */
	public DistancePanel(String date, APIData paramAPIData) {
		
		/*-----------------------------------------*/
		//create the main window for the Distance panel
		/*	-----------------------------------------*/
		setResizable(false);
		this.date = date;
		setTitle("team08-Fitbit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024,768);
		this.setLocationRelativeTo(null); 
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
		
		//create a new instance of the api data
		apiData = paramAPIData;		

		/*	-----------------------------------------*/
		//create a title for the current pane
		/*	-----------------------------------------*/
		JLabel lblTitle = new JLabel("DISTANCE");
		lblTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(80, 21, 837, 84);
		contentPane.add(lblTitle);
		lblTitle.setForeground(white);


		/*	-----------------------------------------*/
		//create a back button to return to dash board
		/*	-----------------------------------------*/
		final JLabel imgBack = new JLabel();
		imgBack.setIcon(new ImageIcon(backImage));
		imgBack.setBounds(0, 0, 48, 48);
		imgBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//what to do on button click
				home();
				dispose();
			}
			
			@Override
   		 	public void mouseEntered(MouseEvent e) {
				imgBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
   		 	@Override
   		 	public void mouseExited(MouseEvent e) {
   		 	imgBack.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
   		 	}
		});
		contentPane.add(imgBack);


		LGraph lineGraph = new LGraph("Distance Traveled", "Distance",apiData.getDistanceTimeSeries());

		lineGraph.setBounds(80, 409, 837, 239);

		contentPane.add(lineGraph);

		/*--------------------------------------------*/
		//create a panel to display Distance information for today
		/*--------------------------------------------*/
		JPanel pnlTodaysValue = new JPanel();
		pnlTodaysValue.setBounds(80, 138, 240, 224);
		contentPane.add(pnlTodaysValue);
		pnlTodaysValue.setLayout(null);
		pnlTodaysValue.setBackground(pannelColor);
		pnlTodaysValue.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		/*--------------------------------------------*/
		//add a label to display the Distance for today
		/*--------------------------------------------*/
		JLabel lblDailyValue = new JLabel("<html> Today you traveled <strong>"+ apiData.getDistance() +"km. </strong> </html>");
		lblDailyValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblDailyValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblDailyValue.setBounds(21, 21, 198, 182);
		pnlTodaysValue.add(lblDailyValue);

		/*--------------------------------------------*/
		//create a panel to display distance information for lifetime totals
		/*--------------------------------------------*/
		JPanel pnlLifetimeTotal = new JPanel();
		pnlLifetimeTotal.setBounds(373, 138, 240, 224);
		contentPane.add(pnlLifetimeTotal);
		pnlLifetimeTotal.setLayout(null);
		pnlLifetimeTotal.setBackground(pannelColor);
		pnlLifetimeTotal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		/*--------------------------------------------*/
		//add a label to display the distance lifetime total
		/*--------------------------------------------*/
		JLabel lblLifetimeTotal = new JLabel("<html> In your lifetime you have traveled <strong>"+ apiData.getTotalDistance()+"km. </strong> </html>");
		lblLifetimeTotal.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblLifetimeTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblLifetimeTotal.setBounds(6, 21, 228, 182);
		pnlLifetimeTotal.add(lblLifetimeTotal);
		
		/*--------------------------------------------*/
		//create a panel to display distance information for the users best day
		/*--------------------------------------------*/
		JPanel pnlBestDay = new JPanel();
		pnlBestDay.setBounds(677, 138, 240, 224);
		contentPane.add(pnlBestDay);
		pnlBestDay.setLayout(null);
		pnlBestDay.setBackground(pannelColor);
		pnlBestDay.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		/*--------------------------------------------*/
		//add a label to display the distance for the best days
		/*--------------------------------------------*/
		JLabel lblBestDay = new JLabel("<html> On your best day you traveled <strong>"+ apiData.getBestDistance()+"km. </strong> </html>");
		lblBestDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblBestDay.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblBestDay.setBounds(21, 21, 198, 182);
		pnlBestDay.add(lblBestDay);
		
		/*-------------------------------------*/
		//the label to set the last updated time
		 /*------------------------------------*/
		 Calendar cal = Calendar.getInstance();
		 final JLabel lblDataUpdate = new JLabel("Last updated: " + cal.getTime().toString());
		 lblDataUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		 lblDataUpdate.setForeground(white);
		 lblDataUpdate.setBounds(51, 660, 923, 37);
		 contentPane.add(lblDataUpdate);
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
