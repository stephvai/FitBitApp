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
 * StepsPanel.java creates the panel that the user will see when the user clicks 
 * the Steps "button" which is just really a panel with an action Listener method
 */
public class StepsPanel extends JFrame {

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
	private Color pannelColor = new Color(0,0,0,64);
	private Color borderColor = new Color(121,189,154);
	private Color titleColor = new Color(11,72,107);
	private Color white = Color.white;
	private final String infoButton = "src/main/resources/New accolades/info.png";

	/**
	 * create a steps panel to show user information on their steps
	 * @param date this is the date selected by the user 
	 * @param paramAPIData an instance of the APIData that passes in the fitbit information
	 */
	public StepsPanel(String date, APIData paramAPIData) {

		/*-----------------------------------------*/
		//create the main window for the daily dash board 
		/*	-----------------------------------------*/
		setResizable(false);
		this.date = date;
		setTitle("team08-Fitbit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024,768);
		this.setLocationRelativeTo(null); 
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
		
		//save the instance of apiData
		apiData = paramAPIData;		

		/*	-----------------------------------------*/
		//create a title for the current pane
		/*	-----------------------------------------*/
		JLabel lblTitle = new JLabel("STEPS");
		lblTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(386, 21, 193, 84);
		contentPane.add(lblTitle);
		lblTitle.setForeground(white);
		
		/*------------------------------------------*
		 * information button 
		 *------------------------------------------*/
		final JLabel informButtonLabel = new JLabel();
		informButtonLabel.setSize(24,24);
		informButtonLabel.setLocation(918,409);
		informButtonLabel.setIcon(new ImageIcon(infoButton));
		informButtonLabel.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent arg0) {
		    		 JOptionPane.showMessageDialog(contentPane, "drag to the right to zoom in to a specific time frame! \n Drag to the left to zoom out");
			 } @Override
   		 public void mouseEntered(MouseEvent e) {
				 informButtonLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
   		 }
   		 @Override
   		 public void mouseExited(MouseEvent e) {
   			informButtonLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
   		 }
		 });
		contentPane.add(informButtonLabel);

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



		LGraph lineGraph = new LGraph("Steps Taken", "steps",apiData.getStepsTimeSeries());

		lineGraph.setBounds(80, 409, 837, 239);

		contentPane.add(lineGraph);



		/*	-----------------------------------------*/
		//create a tabbed pane to store the graphs
		/*	-----------------------------------------*/
	/*	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
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

		contentPane.add(tabbedPane);*/

		/*--------------------------------------------*/
		//create a panel to display steps information for today
		/*--------------------------------------------*/
		JPanel pnlTodaysSteps = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		pnlTodaysSteps.setBounds(80, 138, 240, 224);
		pnlTodaysSteps.setOpaque(false);
		pnlTodaysSteps.setLayout(null);
		pnlTodaysSteps.setBackground(pannelColor);
		pnlTodaysSteps.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(pnlTodaysSteps);
		contentPane.repaint();
		/*--------------------------------------------*/
		//add a label to display the users steps
		/*--------------------------------------------*/
		JLabel lblDailySteps = new JLabel("<html> Today you took <strong>"+ apiData.getSteps()+"</strong> steps. </html>");
		lblDailySteps.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblDailySteps.setOpaque(false);
		lblDailySteps.setForeground(white);
		lblDailySteps.setHorizontalAlignment(SwingConstants.CENTER);
		lblDailySteps.setBounds(21, 21, 198, 182);
		pnlTodaysSteps.add(lblDailySteps);
		contentPane.repaint();

		/*--------------------------------------------*/
		//create a panel to display steps information for lifetime totals
		/*--------------------------------------------*/
		JPanel pnlLifetimeTotal = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		pnlLifetimeTotal.setBounds(373, 138, 240, 224);
		pnlLifetimeTotal.setOpaque(false);
		pnlLifetimeTotal.setLayout(null);
		pnlLifetimeTotal.setBackground(pannelColor);
		pnlLifetimeTotal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(pnlLifetimeTotal);
		contentPane.repaint();
		
		/*--------------------------------------------*/
		//add a label to display the steps lifetime total
		/*--------------------------------------------*/
		JLabel lblLifetimeTotal = new JLabel("<html> In your lifetime you have taken <strong>"+ apiData.getTotalSteps()+"</strong> steps. </html>");
		lblLifetimeTotal.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblLifetimeTotal.setOpaque(false);
		lblLifetimeTotal.setForeground(white);
		lblLifetimeTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblLifetimeTotal.setBounds(21, 31, 198, 182);
		pnlLifetimeTotal.add(lblLifetimeTotal);
		contentPane.repaint();
		
		/*--------------------------------------------*/
		//create a panel to display steps information for their best Day
		/*--------------------------------------------*/
		JPanel pnlBestDay = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		pnlBestDay.setBounds(677, 138, 240, 224);
		pnlBestDay.setOpaque(false);
		pnlBestDay.setLayout(null);
		pnlBestDay.setBackground(pannelColor);
		pnlBestDay.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(pnlBestDay);
		contentPane.repaint();
		/*--------------------------------------------*/
		//add a label to display the steps best day
		/*--------------------------------------------*/
		JLabel lblBestDay = new JLabel("<html> On your best day you took <strong>"+ apiData.getBestSteps()+"</strong> steps. </html>");
		lblBestDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblBestDay.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblBestDay.setBounds(21, 21, 198, 182);
		lblBestDay.setOpaque(false);
		lblBestDay.setForeground(white);
		pnlBestDay.add(lblBestDay);
		contentPane.repaint();
		
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
