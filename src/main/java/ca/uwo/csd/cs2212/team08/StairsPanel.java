package ca.uwo.csd.cs2212.team08;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * creates a JFrame that displays the users floors climbed information
 *
 */
public class StairsPanel extends JFrame {

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
	private Color pannelColor = new Color(168,219,168);
	private Color borderColor = new Color(121,189,154);
	private Color titleColor = new Color(11,72,107);
	private Color white = Color.white;

	/**
	 * create a stairs panel to show user information on their floors climbed
	 * @param date this is the date selected by the user 
	 * @param paramAPIData an instance of the APIData that passes in the fitbit information
	 */
	public StairsPanel(String date, APIData paramAPIData) {
		
		/*-----------------------------------------*/
		//create the main window for the stairs panel
		/*-----------------------------------------*/
		setResizable(false);
		this.date = date;
		setTitle("team08-Fitbit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024,768);
		this.setLocationRelativeTo(null); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(bgColor);
		
		//create a new instance of the api data
		apiData = paramAPIData;		

		/*-----------------------------------------*/
		// create a title for the current pane
		/*-----------------------------------------*/
		JLabel lblTitle = new JLabel("FLOORS CLIMBED");
		lblTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(80, 21, 837, 84);
		contentPane.add(lblTitle);
		lblTitle.setForeground(white);

		/*-----------------------------------------*/
		//create a back button to return to dash board
		/*-----------------------------------------*/
		JLabel imgBack = new JLabel();
		imgBack.setIcon(new ImageIcon(backImage));
		imgBack.setBounds(0, 0, 48, 48);
		imgBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//what to do on button click
				home();
				dispose();
			}
		});
		contentPane.add(imgBack);


		/*-----------------------------------------*/
		// create a tabbed pane to store the graphs
		/*-----------------------------------------*/
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(80, 409, 837, 239);

		//create a panel for the Lifetime progress
		JPanel pnlLifetime = new JPanel();
		tabbedPane.addTab("lifetime Progress", pnlLifetime);

		JPanel pnlToday4 = new JPanel();
		tabbedPane.addTab("Today's Progress", pnlToday4);


		//create a panel for the Lifetime progress
		JPanel pnlToday3 = new JPanel();
		tabbedPane.addTab("Today's Progress", pnlToday3);

		JPanel pnlToday5 = new JPanel();
		tabbedPane.addTab("Today's Progress", pnlToday5);

		contentPane.add(tabbedPane);

		/*--------------------------------------------*/
		// create a panel to display floors climbed information for today
		/*--------------------------------------------*/
		JPanel pnlTodaysValue = new JPanel();
		pnlTodaysValue.setBounds(80, 138, 240, 224);
		contentPane.add(pnlTodaysValue);
		pnlTodaysValue.setLayout(null);
		pnlTodaysValue.setBackground(pannelColor);
		pnlTodaysValue.setBorder(BorderFactory.createLineBorder(borderColor));

		/*--------------------------------------------*/
		// add a label to display the floors climbed today
		/*--------------------------------------------*/
		JLabel lblDailyValue = new JLabel("<html> Today you climbed "+ apiData.getFloorsClimbed() +" floors. </html>");
		lblDailyValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblDailyValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblDailyValue.setBounds(21, 21, 198, 182);
		pnlTodaysValue.add(lblDailyValue);

		/*--------------------------------------------*/
		// create a panel to display floors climbed information for their lifetime totals
		/*--------------------------------------------*/
		JPanel pnlLifetimeTotal = new JPanel();
		pnlLifetimeTotal.setBounds(373, 138, 240, 224);
		contentPane.add(pnlLifetimeTotal);
		pnlLifetimeTotal.setLayout(null);
		pnlLifetimeTotal.setBackground(pannelColor);
		pnlLifetimeTotal.setBorder(BorderFactory.createLineBorder(borderColor));
		
		/*--------------------------------------------*/
		// add a label to display the floors climbed in the users lifetime total
		/*--------------------------------------------*/
		JLabel lblLifetimeTotal = new JLabel("<html> In your lifetime you have climbed "+ apiData.getTotalFloors()+" floors. </html>");
		lblLifetimeTotal.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblLifetimeTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblLifetimeTotal.setBounds(21, 31, 198, 182);
		pnlLifetimeTotal.add(lblLifetimeTotal);
		
		/*--------------------------------------------*/
		// create a panel to display floors climbed information for today
		/*--------------------------------------------*/
		JPanel pnlBestDay = new JPanel();
		pnlBestDay.setBounds(677, 138, 240, 224);
		contentPane.add(pnlBestDay);
		pnlBestDay.setLayout(null);
		pnlBestDay.setBackground(pannelColor);
		pnlBestDay.setBorder(BorderFactory.createLineBorder(borderColor));
		
		/*--------------------------------------------*/
		// add a label to display the floors climbed on the users best day
		/*--------------------------------------------*/
		JLabel lblBestDay = new JLabel("<html> On your best day you climbed "+ apiData.getBestFloors()+" floors. </html>");
		lblBestDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblBestDay.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblBestDay.setBounds(21, 21, 198, 182);
		pnlBestDay.add(lblBestDay);
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
