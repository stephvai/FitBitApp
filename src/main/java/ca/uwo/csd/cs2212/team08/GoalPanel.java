package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * a panel object used to allow users to set their goals
 *
 */
public class GoalPanel extends JFrame {

	private JPanel contentPane;
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(0,0,0,60);
	private Color borderColor = new Color(121, 189, 154);
	private Color titleColor = new Color(11, 72, 107);
	private Color white = Color.white;
	private static final String backImage = "src/main/resources/images/arrowLeft4.png";
	private String date;
	private GoalTracker goalTracker;
	private APIData paramAPIData;


	/**
	 * Create the frame.
	 */
	public GoalPanel(final String date, final APIData paramAPIData) {
		try {
			goalTracker = new GoalTracker(paramAPIData);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		/*-----------------------------------------*/
		//create the main window for the daily goals panel
		/*	-----------------------------------------*/
		setResizable(false);
		this.date = date;
		this.paramAPIData = paramAPIData;
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

		/*---------------------------------------------------*
		 * Title of the entire page
		 *---------------------------------------------------*/
		JLabel titleGoals = new JLabel("   Here are your daily goals: ");
		titleGoals.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		titleGoals.setHorizontalAlignment(SwingConstants.CENTER);
		titleGoals.setBounds(80, 21, 837, 84);
		titleGoals.setForeground(white);
		contentPane.add(titleGoals);
		/*-----------------------------------------*/
		//create a back button to return to dash board
		/*----------------------------------------*/
		JLabel backArrow = new JLabel();
		backArrow.setIcon(new ImageIcon(backImage));
		backArrow.setBounds(0, 0, 48, 48);
		//backArrow.setVisible(true);
		contentPane.add(backArrow);
		backArrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//what to do on button click
				home();
				dispose();
			}
		});
		
		/*-------------------------------------------*
		 * the panel where step goals will be displayed
		 *-------------------------------------------*/
		JPanel stepGoalPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		stepGoalPanel.setLocation(38, 172);
		stepGoalPanel.setSize(285, 201);
		stepGoalPanel.setOpaque(false);
		stepGoalPanel.setLayout(null);
		stepGoalPanel.setBackground(pannelColor);
		stepGoalPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.setBounds(50,50,400, 500);
		contentPane.add(stepGoalPanel);
		contentPane.repaint();
		/*------------------------------------------*
		 * Title of the step goal panel
		 *------------------------------------------*/
		final JLabel lblStepsGoal = new JLabel("<html> Steps </html>");
		lblStepsGoal.setBounds(66, 6, 150, 56);
		lblStepsGoal.setForeground(white);
		lblStepsGoal.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblStepsGoal.setHorizontalAlignment(SwingConstants.CENTER);
		lblStepsGoal.setBackground(pannelColor);
		lblStepsGoal.setOpaque(false);
		stepGoalPanel.add(lblStepsGoal);
		contentPane.repaint();
		/*--------------------------------------------*
		 * where the data for steps goal would be displayed
		 *--------------------------------------------*/
		final JLabel label = new JLabel(goalTracker.getGoal(GoalsEnum.steps));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(false);
		label.setForeground(white);
		label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		label.setBounds(6, 90, 273, 29);
		stepGoalPanel.add(label);
		contentPane.repaint();
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editButton = new JButton("Edit Goals");
		editButton.setOpaque(false);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				optionPromp("How many steps will you take?", label, GoalsEnum.steps);

			}
		});
		editButton.setBounds(82, 147, 117, 29);
		stepGoalPanel.add(editButton);
		editButton.repaint();
		
		/*-------------------------------------------*
		 * the panel where distance goals will be displayed
		 *-------------------------------------------*/
		JPanel distanceGoalPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		distanceGoalPanel.setLayout(null);
		distanceGoalPanel.setOpaque(false);
		distanceGoalPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		distanceGoalPanel.setBackground(new Color(168, 219, 168));
		distanceGoalPanel.setBounds(368, 172, 285, 201);
		distanceGoalPanel.setBackground(pannelColor);
		contentPane.add(distanceGoalPanel);
		contentPane.repaint();
		
		/*------------------------------------------*
		 * Title of the distance goal panel
		 *------------------------------------------*/
		final JLabel distanceGoalLbl = new JLabel("<html> Distance </html>");
		distanceGoalLbl.setBounds(68, 6, 150, 56);
		distanceGoalLbl.setForeground(white);
		distanceGoalLbl.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		distanceGoalLbl.setHorizontalAlignment(SwingConstants.CENTER);
		distanceGoalPanel.add(distanceGoalLbl);
		contentPane.repaint();
		/*-------------------------------------------------------------*
		 * information shown for distance goals 
		 *-------------------------------------------------------------*/
		final JLabel lblDistanceValue = new JLabel(goalTracker.getGoal(GoalsEnum.distance));
		lblDistanceValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistanceValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblDistanceValue.setBounds(6, 87, 273, 29);
		lblDistanceValue.setForeground(white);
		lblDistanceValue.setOpaque(false);
		lblDistanceValue.setBackground(pannelColor);
		distanceGoalPanel.add(lblDistanceValue);
		contentPane.repaint();
		
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editTheDistance = new JButton("Edit Goals");
		editTheDistance.setBounds(88, 146, 117, 29);
		editTheDistance.setOpaque(false);
		editTheDistance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionPromp("How much distance will you travel?", lblDistanceValue, GoalsEnum.distance);

			}
		});
		distanceGoalPanel.add(editTheDistance);
		editTheDistance.repaint();
		
		
		/*-------------------------------------------*
		 * the panel where calories goals will be displayed
		 *-------------------------------------------*/
		final JPanel caloriesGoalPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		caloriesGoalPanel.setLayout(null);
		caloriesGoalPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		caloriesGoalPanel.setBackground(pannelColor);
		caloriesGoalPanel.setBounds(703, 172, 285, 201);
		contentPane.add(caloriesGoalPanel);
		contentPane.repaint();
		
		/*------------------------------------------*
		 * Title of the calories goal panel
		 *------------------------------------------*/
		final JLabel lblCaloriesGoalPanel = new JLabel("<html> Calories </html>");
		lblCaloriesGoalPanel.setBounds(68,6, 150,56);
		lblCaloriesGoalPanel.setOpaque(false);
		lblCaloriesGoalPanel.setForeground(white);
		lblCaloriesGoalPanel.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblCaloriesGoalPanel.setHorizontalAlignment(SwingConstants.CENTER);
		caloriesGoalPanel.add(lblCaloriesGoalPanel);
		contentPane.repaint();
		/*-------------------------------------------------------------*
		 * information shown for calories goals 
		 *-------------------------------------------------------------*/
		final JLabel lblCalorieValue = new JLabel(goalTracker.getGoal(GoalsEnum.calorieBurned));
		lblCalorieValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblCalorieValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblCalorieValue.setOpaque(false);
		lblCalorieValue.setForeground(white);
		lblCalorieValue.setBounds(0, 88, 279, 29);
		caloriesGoalPanel.add(lblCalorieValue);
		contentPane.repaint();
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editTheCalories = new JButton("Edit Goals");
		editTheCalories.setBounds(89, 149, 117, 29);
		editTheCalories.setOpaque(false);
		editTheCalories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				optionPromp("How many calories are you going to burn?", lblCalorieValue, GoalsEnum.calorieBurned);

			}
		});
		caloriesGoalPanel.add(editTheCalories);
		contentPane.repaint();
				
		/*-------------------------------------------*
		 * the panel where floor goals will be displayed
		 *-------------------------------------------*/
		JPanel floorsGoalPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		floorsGoalPanel.setLayout(null);
		floorsGoalPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		floorsGoalPanel.setBackground(pannelColor);
		floorsGoalPanel.setBounds(183, 466, 285, 201);
		floorsGoalPanel.setOpaque(false);
		contentPane.add(floorsGoalPanel);
		contentPane.repaint();
		
		/*------------------------------------------*
		 * Title of the floor goal panel
		 *------------------------------------------*/
		final JLabel lblFloorGoal = new JLabel("<html> Floors </html>");
		lblFloorGoal.setHorizontalAlignment(SwingConstants.CENTER);
		lblFloorGoal.setOpaque(false);
		lblFloorGoal.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblFloorGoal.setBounds(65, 22, 150, 56);
		lblFloorGoal.setForeground(white);
		lblFloorGoal.setForeground(white);
		floorsGoalPanel.add(lblFloorGoal);
		contentPane.repaint();
		
		/*-------------------------------------------------------------*
		 * information shown for Floor goals 
		 *-------------------------------------------------------------*/
		final JLabel lblFloorValue = new JLabel(goalTracker.getGoal(GoalsEnum.floorsClimbed));
		lblFloorValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblFloorValue.setOpaque(false);
		lblFloorValue.setForeground(white);
		lblFloorValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblFloorValue.setBounds(0, 97, 285, 29);
		floorsGoalPanel.add(lblFloorValue);
		contentPane.repaint();
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editTheFloor = new JButton("Edit Goals");
		editTheFloor.setBounds(85, 149, 117, 29);
		editTheFloor.setOpaque(false);
		editTheFloor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionPromp("How many floors will you climb?", lblFloorValue, GoalsEnum.floorsClimbed);

			}
		});
		floorsGoalPanel.add(editTheFloor);
		contentPane.repaint();
		
		
		/*------------------------------------------------------*
		 * the panel where active minute goals will be displayed
		 *------------------------------------------------------*/
		JPanel activeGoalPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		activeGoalPanel.setLayout(null);
		activeGoalPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		activeGoalPanel.setBackground(pannelColor);
		activeGoalPanel.setBounds(529, 466, 285, 201);
		contentPane.add(activeGoalPanel);
		contentPane.repaint();
		
		/*------------------------------------------*
		 * Title of the active minutes goal panel
		 *------------------------------------------*/
		final JLabel lblActiveMinutesGoalPanel = new JLabel("<html> Active Minutes </html>");
		lblActiveMinutesGoalPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblActiveMinutesGoalPanel.setOpaque(false);
		lblActiveMinutesGoalPanel.setForeground(white);
		lblActiveMinutesGoalPanel.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblActiveMinutesGoalPanel.setBounds(58, 23, 183, 56);
		activeGoalPanel.add(lblActiveMinutesGoalPanel);
		contentPane.repaint();
		/*-------------------------------------------------------------*
		 * information shown for Active Minutes goals 
		 *-------------------------------------------------------------*/
		final JLabel lblActiveGoals = new JLabel(goalTracker.getGoal(GoalsEnum.veryActiveMinutes));
		lblActiveGoals.setHorizontalAlignment(SwingConstants.CENTER);
		lblActiveGoals.setForeground(white);
		lblActiveGoals.setOpaque(false);
		lblActiveGoals.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblActiveGoals.setBounds(0, 97, 285, 29);
		activeGoalPanel.add(lblActiveGoals);
		contentPane.repaint();
		
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editTheActive = new JButton("Edit Goals");
		editTheActive.setBounds(88, 152, 117, 29);
		editTheActive.setOpaque(false);
		editTheActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// figure out how to edit goals here
				optionPromp("How many minutes are you going to be active?", lblActiveGoals, GoalsEnum.veryActiveMinutes);
			}
		});
		activeGoalPanel.add(editTheActive);
		activeGoalPanel.repaint();
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
		 contentPane.repaint();
	}

	public void home()
	{
		MainScreen main = new MainScreen(this.date, paramAPIData);
		main.setVisible(true);
	}

	/**
	 * a healper method used to set the goals
	 * @param target the value of the goal
	 * @param text a text value
	 * @param label the label to write the goal in
	 * @param type the type of goal
	 */
	private void helperMethod(String target, String text, JLabel label, GoalsEnum type) {

		try{
			goalTracker.setGoal(target, type);
		label.setText(target);
		}catch(Exception x){
			optionPromp(text, label, type);
		}
	}
	/**
	 * a method to create a pop-up for users to make their goals
	 * @param inputText a string values that store the goal the user inputed
	 * @param label the label to write out the goal
	 * @param type the type of goal
	 */
	private void optionPromp(String inputText, JLabel label, GoalsEnum type){
		String target = JOptionPane.showInputDialog(contentPane, inputText, null);

		if(target == null){
			return;
		}
		else {
			try {
				float start = Float.parseFloat(target);
				int check = (int) start;

				target = Integer.toString(check);

			if (check == 0)
				target = "0";
			else if (check < 0) {
				target = null;
			}
			}catch(Exception e){
					return;

			}
			helperMethod(target, inputText, label, type);
		}

	}


}
