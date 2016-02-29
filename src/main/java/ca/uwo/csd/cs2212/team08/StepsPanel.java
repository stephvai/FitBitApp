package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import java.awt.Color;

public class StepsPanel extends JFrame {

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
	public StepsPanel(String date, APIData paramAPIData) {
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

		//create a title for the current pane
		JLabel lblTitle = new JLabel("STEPS");
		lblTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(386, 21, 193, 84);
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
		JPanel pnlTodaysSteps = new JPanel();
		pnlTodaysSteps.setBounds(80, 138, 240, 224);
		contentPane.add(pnlTodaysSteps);
		pnlTodaysSteps.setLayout(null);
		pnlTodaysSteps.setBackground(pannelColor);
		pnlTodaysSteps.setBorder(BorderFactory.createLineBorder(borderColor));

		JLabel lblDailySteps = new JLabel("<html> Today you took "+ apiData.getSteps()+" steps. </html>");
		lblDailySteps.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblDailySteps.setHorizontalAlignment(SwingConstants.CENTER);
		lblDailySteps.setBounds(21, 21, 198, 182);
		pnlTodaysSteps.add(lblDailySteps);

		JPanel pnlLifetimeTotal = new JPanel();
		pnlLifetimeTotal.setBounds(373, 138, 240, 224);
		contentPane.add(pnlLifetimeTotal);
		pnlLifetimeTotal.setLayout(null);
		pnlLifetimeTotal.setBackground(pannelColor);
		pnlLifetimeTotal.setBorder(BorderFactory.createLineBorder(borderColor));
		
		JLabel lblLifetimeTotal = new JLabel("<html> In your lifetime you have taken "+ apiData.getTotalSteps()+" steps. </html>");
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
		
		JLabel lblBestDay = new JLabel("<html> On your best day you took "+ apiData.getBestSteps()+" steps. </html>");
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
