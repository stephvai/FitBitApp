package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.Color;

public class StepsPanel extends JFrame {

	private JPanel contentPane;
	private String date;
	private static final String backImage = "src/main/resources/images/arrowLeft4.png";
	private APIData apiData;

	/**
	 * Create the frame.
	 */
	public StepsPanel(String date) {
		this.date = date;
		setTitle("team08-Fitbit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024,768);
		this.setLocationRelativeTo(null); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//create a new instance of the api data
		apiData = new APIData();
		apiData.refreshDailyDashBoardData(date);
		

		//create a title for the current pane
		JLabel lblTitle = new JLabel("STEPS");
		lblTitle.setFont(new Font("Noteworthy", Font.BOLD, 50));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(386, 21, 193, 84);
		contentPane.add(lblTitle);


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

		JLabel lblDailySteps = new JLabel("Today you took "+ apiData.getSteps()+" steps");
		lblDailySteps.setBounds(21, 21, 198, 182);
		pnlTodaysSteps.add(lblDailySteps);

		JPanel pnlLifetimeTotal = new JPanel();
		pnlLifetimeTotal.setBounds(373, 138, 240, 224);
		contentPane.add(pnlLifetimeTotal);
		pnlLifetimeTotal.setLayout(null);
		
		JLabel lblLifetimeTotal = new JLabel("In your lifetime you have taken "+ apiData.getTotalSteps()+"steps.");
		lblLifetimeTotal.setBounds(21, 21, 198, 182);
		pnlLifetimeTotal.add(lblLifetimeTotal);
		
		JPanel pnlBestDay = new JPanel();
		pnlBestDay.setBounds(677, 138, 240, 224);
		contentPane.add(pnlBestDay);
		pnlBestDay.setLayout(null);
		
		JLabel lblBestDay = new JLabel("On your best day you took "+ apiData.getBestSteps()+" steps.");
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
