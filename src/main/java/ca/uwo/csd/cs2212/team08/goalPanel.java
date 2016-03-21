package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Calendar;

public class GoalPanel extends JFrame {

	private JPanel contentPane;
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(168, 219, 168);
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
		System.out.println("Best Steps:" + paramAPIData.getBestSteps());
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
		contentPane = new JPanel();
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
		contentPane.add(titleGoals);
		titleGoals.setForeground(white);
		
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
		JPanel stepGoalPanel = new JPanel();
		stepGoalPanel.setLocation(38, 172);
		stepGoalPanel.setSize(285, 201);
		contentPane.setBounds(50,50,400, 500);
		contentPane.add(stepGoalPanel);
		stepGoalPanel.setLayout(null);
		stepGoalPanel.setBackground(pannelColor);
		stepGoalPanel.setBorder(BorderFactory.createLineBorder(borderColor));
		
		/*------------------------------------------*
		 * Title of the step goal panel
		 *------------------------------------------*/
		final JLabel lblStepsGoal = new JLabel("<html> Steps </html>");
		lblStepsGoal.setBounds(66, 6, 150, 56);
		stepGoalPanel.add(lblStepsGoal);
		lblStepsGoal.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblStepsGoal.setHorizontalAlignment(SwingConstants.CENTER);

		/*--------------------------------------------*
		 * where the data for steps goal would be displayed
		 *--------------------------------------------*/
		final JLabel label = new JLabel(goalTracker.getGoal(GoalsEnum.steps));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		label.setBounds(105, 90, 68, 29);
		stepGoalPanel.add(label);
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editButton = new JButton("Edit Goals");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				optionPromp("How many steps will you take?", label, GoalsEnum.steps);



			}
		});
		editButton.setBounds(82, 147, 117, 29);
		stepGoalPanel.add(editButton);
		

		
		/*-------------------------------------------*
		 * the panel where distance goals will be displayed
		 *-------------------------------------------*/
		JPanel distanceGoalPanel = new JPanel();
		distanceGoalPanel.setLayout(null);
		distanceGoalPanel.setBorder(BorderFactory.createLineBorder(borderColor));
		distanceGoalPanel.setBackground(new Color(168, 219, 168));
		distanceGoalPanel.setBounds(368, 172, 285, 201);
		contentPane.add(distanceGoalPanel);
		
		/*------------------------------------------*
		 * Title of the distance goal panel
		 *------------------------------------------*/
		final JLabel distanceGoalLbl = new JLabel("<html> Distance </html>");
		distanceGoalLbl.setBounds(68, 6, 150, 56);
		distanceGoalPanel.add(distanceGoalLbl);
		distanceGoalLbl.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		distanceGoalLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*-------------------------------------------------------------*
		 * information shown for distance goals 
		 *-------------------------------------------------------------*/
		final JLabel lblDistanceValue = new JLabel(goalTracker.getGoal(GoalsEnum.distance));
		lblDistanceValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistanceValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblDistanceValue.setBounds(110, 87, 68, 29);
		distanceGoalPanel.add(lblDistanceValue);
		
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editTheDistance = new JButton("Edit Goals");
		editTheDistance.setBounds(88, 146, 117, 29);
		editTheDistance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {



				optionPromp("How much distance will you travel?", lblDistanceValue, GoalsEnum.distance);

			}
		});
		distanceGoalPanel.add(editTheDistance);
		
		
		/*-------------------------------------------*
		 * the panel where calories goals will be displayed
		 *-------------------------------------------*/
		final JPanel caloriesGoalPanel = new JPanel();
		caloriesGoalPanel.setLayout(null);
		caloriesGoalPanel.setBorder(BorderFactory.createLineBorder(borderColor));
		caloriesGoalPanel.setBackground(new Color(168, 219, 168));
		caloriesGoalPanel.setBounds(703, 172, 285, 201);
		contentPane.add(caloriesGoalPanel);
		
		/*------------------------------------------*
		 * Title of the calories goal panel
		 *------------------------------------------*/
		final JLabel lblCaloriesGoalPanel = new JLabel("<html> Calories </html>");
		lblCaloriesGoalPanel.setBounds(68,6, 150,56);
		caloriesGoalPanel.add(lblCaloriesGoalPanel);
		lblCaloriesGoalPanel.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblCaloriesGoalPanel.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*-------------------------------------------------------------*
		 * information shown for calories goals 
		 *-------------------------------------------------------------*/
		final JLabel lblCalorieValue = new JLabel(goalTracker.getGoal(GoalsEnum.calorieBurned));
		lblCalorieValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblCalorieValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblCalorieValue.setBounds(108, 88, 68, 29);
		caloriesGoalPanel.add(lblCalorieValue);
		
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editTheCalories = new JButton("Edit Goals");
		editTheCalories.setBounds(89, 149, 117, 29);
		editTheCalories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				optionPromp("How many calories are you going to burn?", lblCalorieValue, GoalsEnum.calorieBurned);

			}
		});
		caloriesGoalPanel.add(editTheCalories);
		

				
		/*-------------------------------------------*
		 * the panel where floor goals will be displayed
		 *-------------------------------------------*/
		JPanel floorsGoalPanel = new JPanel();
		floorsGoalPanel.setLayout(null);
		floorsGoalPanel.setBorder(BorderFactory.createLineBorder(borderColor));
		floorsGoalPanel.setBackground(new Color(168, 219, 168));
		floorsGoalPanel.setBounds(183, 466, 285, 201);
		contentPane.add(floorsGoalPanel);
		
		/*------------------------------------------*
		 * Title of the floor goal panel
		 *------------------------------------------*/
		final JLabel lblFloorGoal = new JLabel("<html> Floors </html>");
		lblFloorGoal.setHorizontalAlignment(SwingConstants.CENTER);
		lblFloorGoal.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblFloorGoal.setBounds(65, 22, 150, 56);
		floorsGoalPanel.add(lblFloorGoal);
		
		/*-------------------------------------------------------------*
		 * information shown for Floor goals 
		 *-------------------------------------------------------------*/
		final JLabel lblFloorValue = new JLabel(goalTracker.getGoal(GoalsEnum.floorsClimbed));
		lblFloorValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblFloorValue.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblFloorValue.setBounds(106, 97, 68, 29);
		floorsGoalPanel.add(lblFloorValue);
		
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editTheFloor = new JButton("Edit Goals");
		editTheFloor.setBounds(85, 149, 117, 29);
		editTheFloor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionPromp("How many floors will you climb?", lblFloorValue, GoalsEnum.floorsClimbed);

			}
		});
		floorsGoalPanel.add(editTheFloor);
		
		
		/*------------------------------------------------------*
		 * the panel where active minute goals will be displayed
		 *------------------------------------------------------*/
		JPanel activeGoalPanel = new JPanel();
		activeGoalPanel.setLayout(null);
		activeGoalPanel.setBorder(BorderFactory.createLineBorder(borderColor));
		activeGoalPanel.setBackground(new Color(168, 219, 168));
		activeGoalPanel.setBounds(529, 466, 285, 201);
		contentPane.add(activeGoalPanel);
		
		/*------------------------------------------*
		 * Title of the active minutes goal panel
		 *------------------------------------------*/
		final JLabel lblActiveMinutesGoalPanel = new JLabel("<html> Active Minutes </html>");
		lblActiveMinutesGoalPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblActiveMinutesGoalPanel.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblActiveMinutesGoalPanel.setBounds(58, 23, 183, 56);
		activeGoalPanel.add(lblActiveMinutesGoalPanel);
		
		/*-------------------------------------------------------------*
		 * information shown for Active Minutes goals 
		 *-------------------------------------------------------------*/
		final JLabel lblActiveGoals = new JLabel(goalTracker.getGoal(GoalsEnum.veryActiveMinutes));
		lblActiveGoals.setHorizontalAlignment(SwingConstants.CENTER);
		lblActiveGoals.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblActiveGoals.setBounds(108, 97, 68, 29);
		activeGoalPanel.add(lblActiveGoals);
		
		/*------------------------------------------*
		 * Edit Button
		 *------------------------------------------*/
		JButton editTheActive = new JButton("Edit Goals");
		editTheActive.setBounds(88, 152, 117, 29);
		editTheActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// figure out how to edit goals here
				optionPromp("How many minutes are you going to be active?", lblActiveGoals, GoalsEnum.veryActiveMinutes);

			}
		});
		activeGoalPanel.add(editTheActive);
	
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

	public void home()
	{
		MainScreen main = new MainScreen(this.date, paramAPIData);
		main.setVisible(true);
	}

	private void helperMethod(String target, String text, JLabel label, GoalsEnum type) {

		try{
			goalTracker.setGoal(target, type);
		label.setText(target);
		}catch(Exception x){
			optionPromp(text, label, type);
		}
	}
	private void optionPromp(String inputText, JLabel label, GoalsEnum type){
		String target = JOptionPane.showInputDialog(contentPane, inputText, null);
		if(target == null){
			return;
		}
		helperMethod(target, inputText, label, type);


	}


}
