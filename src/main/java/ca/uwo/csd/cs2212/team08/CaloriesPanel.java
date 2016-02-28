package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class CaloriesPanel extends JFrame {

	private JPanel contentPane;
	private String date;
	private static final String backImage = "src/main/resources/Placeholder.png";

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaloriesPanel frame = new CaloriesPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public CaloriesPanel(String date) {
		this.date = date;
		setTitle("team08-Fitbit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024,768);
		this.setLocationRelativeTo(null); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//create a title for the current pane
		JLabel lblTitle = new JLabel("STEPS");
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 50));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(386, 21, 193, 84);
		contentPane.add(lblTitle);

		//ImageIcon BackButton = createImageIcon(backImage, "return to the home page");
		JLabel imgBack = new JLabel();
		imgBack.setIcon(new ImageIcon(backImage));
		imgBack.setBounds(0, 0, 50, 50);
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
		tabbedPane.setBounds(86, 166, 831, 482);

		JPanel pnlToday4 = new JPanel();
		tabbedPane.addTab("Today's Progress", pnlToday4);

		//create a panel for the Lifetime progress
		JPanel pnlLifetime = new JPanel();
		tabbedPane.addTab("lifetime Progress", pnlLifetime);


		//create a panel for the Lifetime progress
		JPanel pnlToday3 = new JPanel();
		tabbedPane.addTab("Goal Progress", pnlToday3);

		JPanel pnlToday5 = new JPanel();
		tabbedPane.addTab("Today's Progress", pnlToday5);

		contentPane.add(tabbedPane);
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