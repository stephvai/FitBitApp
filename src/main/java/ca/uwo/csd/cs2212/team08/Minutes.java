package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class Minutes extends JFrame {

	private JPanel contentPane;
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(168,219,168);
	private Color borderColor = new Color(121,189,154);
	private Color titleColor = new Color(11,72,107);
	private Color white = Color.white;

	/**
	 * Create the frame.
	 */
	public Minutes() {
	contentPane = new JPanel();
		
	//setContentPane(contentPane);

	 setTitle("Team08 Fitbit");
   	 setSize(1024, 768);
   	 setDefaultCloseOperation(EXIT_ON_CLOSE);
   	 this.setLocationRelativeTo(null);
   	 setResizable(false);
   	 setVisible(true);
   	 
   	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	contentPane.setBackground(bgColor);
   	 
		JLabel titleMinutes = new JLabel("Minutes");
		titleMinutes.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		titleMinutes.setHorizontalAlignment(SwingConstants.CENTER);
		titleMinutes.setBounds(80, 21, 837, 84);
		contentPane.add(titleMinutes);
		titleMinutes.setForeground(white);
		
   	 
		
		
	}

}
