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
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DistancePanel extends JFrame {

	private JPanel contentPane;
	private String date;
	private static final String backImage = "src/main/resources/images/arrowLeft4.png";
	private APIData apiData;

	//Color Scheme
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(168,219,168);
	private Color borderColor = new Color(121,189,154);
	private Color titleColor = new Color(11,72,107);
	private Color white = Color.white;

	/**
	 * Create the frame.
	 */
	public DistancePanel(String date) {
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
		apiData = new APIData();
		apiData.refreshDailyDashBoardData(date);
		

		//create a title for the current pane
		JLabel lblTitle = new JLabel("DISTANCE");
		lblTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(80, 21, 837, 84);
		contentPane.add(lblTitle);
		lblTitle.setForeground(white);


		//ImageIcon BackButton = createImageIcon(backImage, "return to the home page");
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


		//create a tabbed pane to store each panel
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

		/*************************************/
		//create the panels to display steps information
		/*************************************/
		JPanel pnlTodaysValue = new JPanel();
		pnlTodaysValue.setBounds(80, 138, 240, 224);
		contentPane.add(pnlTodaysValue);
		pnlTodaysValue.setLayout(null);
		pnlTodaysValue.setBackground(pannelColor);
		pnlTodaysValue.setBorder(BorderFactory.createLineBorder(borderColor));

		JLabel lblDailyValue = new JLabel("<html> Today you traveled "+ apiData.getDistance() +"km. </html>");
		lblDailyValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblDailyValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblDailyValue.setBounds(21, 21, 198, 182);
		pnlTodaysValue.add(lblDailyValue);

		JPanel pnlLifetimeTotal = new JPanel();
		pnlLifetimeTotal.setBounds(373, 138, 240, 224);
		contentPane.add(pnlLifetimeTotal);
		pnlLifetimeTotal.setLayout(null);
		pnlLifetimeTotal.setBackground(pannelColor);
		pnlLifetimeTotal.setBorder(BorderFactory.createLineBorder(borderColor));
		
		JLabel lblLifetimeTotal = new JLabel("<html> In your lifetime you have traveled "+ apiData.getTotalDistance()+"km. </html>");
		lblLifetimeTotal.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblLifetimeTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblLifetimeTotal.setBounds(21, 31, 198, 182);
		pnlLifetimeTotal.add(lblLifetimeTotal);
		
		JPanel pnlBestDay = new JPanel();
		pnlBestDay.setBounds(677, 138, 240, 224);
		contentPane.add(pnlBestDay);
		pnlBestDay.setLayout(null);
		pnlBestDay.setBackground(pannelColor);
		pnlBestDay.setBorder(BorderFactory.createLineBorder(borderColor));
		
		JLabel lblBestDay = new JLabel("<html> On your best day you traveled "+ apiData.getBestDistance()+"km. </html>");
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
		MainScreen main = new MainScreen(this.date);
		main.setVisible(true);
	}

}
