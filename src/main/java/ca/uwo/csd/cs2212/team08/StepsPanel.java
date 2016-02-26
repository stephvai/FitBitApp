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

public class StepsPanel extends JFrame {

	private JPanel contentPane;
	private static final String backImage = "src/main/resources/Placeholder.png";

	/**
	 * Create the frame.
	 */
	public StepsPanel() {
		setTitle("team08-Fitbit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024,768);
		this.setLocationRelativeTo(null); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*//create a back button to return you to the dash board
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(5, 5, 84, 41);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainScreen main = new MainScreen();
				main.setVisible(true);
				dispose();
			}
		});
		btnBack.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(btnBack);*/

		//create a title for the current pane
		JLabel lblTitle = new JLabel("STEPS");
		lblTitle.setFont(new Font("Noteworthy", Font.BOLD, 50));
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
   			 MainScreen main = new MainScreen();
   			 main.setVisible(true);
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
		tabbedPane.addTab("Today's Progress", pnlToday3);
		
		JPanel pnlToday5 = new JPanel();
		tabbedPane.addTab("Today's Progress", pnlToday5);
		
		contentPane.add(tabbedPane);
		
		
		/*ImageIcon backButton = new ImageIcon(backImage);
		JLabel back = new JLabel(backButton);
		back.setBounds(100, 100, 300, 200);
		contentPane.add(back);*/
	}
}
