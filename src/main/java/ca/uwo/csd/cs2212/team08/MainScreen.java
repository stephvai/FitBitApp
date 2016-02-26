package ca.uwo.csd.cs2212.team08;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.border.EmptyBorder;

import java.awt.Panel;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import java.awt.SystemColor;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


import java.sql.Date;


public class MainScreen extends JFrame {
	
	private JPanel contentPane;
	private int year;
	private int month;
	private int day; 
	private String date;
	//private APIData data;
	    
	     public MainScreen() {
	          this.initUI();
	     }
	    
	     private void initUI () {
	    	 Calendar cal = Calendar.getInstance();
	    	
	    	 year = cal.get(Calendar.YEAR);
	    	 month = cal.get(Calendar.MONTH)+1;
	    	 day = cal.get(Calendar.DAY_OF_MONTH);
	    	 String monthString;
	    	 if (month<10)
	    	 {
	    		 monthString = "0" + Integer.toString(month);
	    	 }
	    	 else
	    	 {
	    		monthString = Integer.toString(month); 
	    	 }
	    	 date = Integer.toString(cal.get(Calendar.YEAR)) +  "-" + monthString + "-" + Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
	    	 System.out.println(date);
	    	 //data = new APIData();
	    	 //data.refreshData(year, month, day);
	    	 this.setTitle("Team08 Fitbit");
	    	 this.setSize(1024, 768);
	    	 this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	 this.setLocationRelativeTo(null);
	    	 contentPane = new JPanel();
	    	 contentPane.setBorder(new EmptyBorder(5, 0, 5, 0));
	    	 this.setContentPane(contentPane);
	    	 contentPane.setLayout(null);

			 //create a label to display the title of the panel
	    	 /*	-----------------------------------------*/
			 JLabel lblTitle = new JLabel("Welcome! Here is your daily dashboard: ");
			 lblTitle.setFont(new Font("Noteworthy", Font.PLAIN, 46));
			 lblTitle.setBounds(159, 0, 732, 72);
			 contentPane.add(lblTitle);
			 /*------------------------------------------*/

			 // Header Panel 
			 JPanel headerPanel = new JPanel();
			 headerPanel.setBackground(Color.WHITE);
			 headerPanel.setBounds(0, 0, 1024, 63);
			 headerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			 contentPane.add(headerPanel);
			 
			 UtilDateModel model = new UtilDateModel();
			 model.setDate(year, month-1, day);
			 model.setSelected(true);
			 JDatePanelImpl datePanel = new JDatePanelImpl(model);
			 
			 stepsPanel();
			 stairsPanel();
			 caloriesPanel();
			 distancePanel();
			 activeMinutesPanel();
			 sedentaryMinutesPanel();
			 accoladesPanel();
			 heartRatePanel();
			 goalsPanel();
			 
			 JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
			 datePicker.setBounds(385, 69, 265, 29);
			 contentPane.add(datePicker);
			 datePicker.setBackground(Color.WHITE);
			 datePicker.setVisible(true);
			 
			 /*date = datePicker.getJFormattedTextField().getText();
			 System.out.println(date);
			 String reverse = new StringBuilder(date).reverse().toString();
			 System.out.println(reverse);*/
	     }
	     
	     /**
	      * creates the panel to display the steps taken
	      */
	     private void stepsPanel()
		 {
			//------------------------------------------
			 //create a dash board panel for the steps
			 //------------------------------------------//
	    	 DashBoardPanel pnlSteps = new DashBoardPanel(50, 191);
	    	 pnlSteps.setLocation(51, 99);
	    	 //pnlSteps.setBounds(50, 191, 308, 167);
	    	 //creates a mouse listener that tracks when it has been clicked
	    	 pnlSteps.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 StepsPanel steps = new StepsPanel();
	    			 steps.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
	    	 //set the layout to absolute
	    	 pnlSteps.setLayout(null);
	    	 //add panel to the content pane
	    	 contentPane.add(pnlSteps);

	    	 //create a progress bar for the steps panel
	    	 JProgressBar stepsProgress = new JProgressBar();
	    	 //this will be switched with a ratio between the daily goal and the current steps
	    	 stepsProgress.setValue(20);
	    	 stepsProgress.setToolTipText("Current progress towards your goal");
	    	 stepsProgress.setForeground(new Color(51, 153, 255));
	    	 stepsProgress.setBounds(17, 113, 231, 36);
	    	 pnlSteps.add(stepsProgress);

	    	 /*JLabel lblSteps = new JLabel(Integer.toString(data.getSteps()));
	    	 lblSteps.setHorizontalAlignment(SwingConstants.CENTER);
	    	 lblSteps.setBounds(102, 21, 92, 26);
	    	 pnlSteps.add(lblSteps);*/

	    	 JLabel lblStepsTtile = new JLabel("Steps");
	    	 lblStepsTtile.setFont(new Font("Noteworthy", Font.PLAIN, 25));
	    	 lblStepsTtile.setHorizontalAlignment(SwingConstants.CENTER);
	    	 lblStepsTtile.setBounds(86, 50, 92, 36);
	    	 pnlSteps.add(lblStepsTtile);
			 //------------------------------------------
			 //------------------------------------------//

			 /*-----------------------------------------*/
	    	 lblStepsTtile.setBounds(85, 62, 92, 26);
	    	 pnlSteps.add(lblStepsTtile);  
		 }
	     
	     /**
	      * creates the panel to display the the stairs climbed
	      */
	     private void stairsPanel()
	     {
	    	 /*-----------------------------------------*/

			 //stairs panel aka floors climbed
			 /*----------------------------------------*/
	    	 DashBoardPanel StairsPanel = new DashBoardPanel(413, 191);
	    	 StairsPanel.setLocation(385, 99);
	    	 StairsPanel.setLayout(null);
	    	 //StairsPanel.setBounds(413, 191, 308, 167);
	    	 StairsPanel.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 StairsPanel stairs = new StairsPanel();
	    			 stairs.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
	    	 contentPane.add(StairsPanel);

			 JLabel lblStairs = new JLabel("Stairs");
			 lblStairs.setFont(new Font("Noteworthy", Font.PLAIN, 25));
			 lblStairs.setHorizontalAlignment(SwingConstants.CENTER);
			 lblStairs.setBounds(79, 51, 92, 38);
			 StairsPanel.add(lblStairs);

			 // progress panel
			 JProgressBar stairsProgress = new JProgressBar();
			 stairsProgress.setValue(20);
			 stairsProgress.setToolTipText("Current progress towards your goal");
			 stairsProgress.setForeground(SystemColor.textHighlight);
			 stairsProgress.setBounds(17, 113, 231, 36);
			 StairsPanel.add(stairsProgress);
	     }
	     
	     /**
	      * creates the panel to display calories panel
	      */
	     private void caloriesPanel()
	     {
	    	 /*---------------------------------------*/
			 //calories panel
			 /*---------------------------------------*/
			 DashBoardPanel caloriesBurned = new DashBoardPanel(776, 191);
			 caloriesBurned.setLocation(709, 99);
			 caloriesBurned.setLayout(null);
			 caloriesBurned.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 CaloriesPanel calories = new CaloriesPanel();
	    			 calories.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
			 contentPane.add(caloriesBurned);

			 JLabel lblCalories = new JLabel("Calories Burned");
			 lblCalories.setFont(new Font("Noteworthy", Font.PLAIN, 25));
			 lblCalories.setHorizontalAlignment(SwingConstants.CENTER);
			 lblCalories.setBounds(53,45,165,53);
			 caloriesBurned.add(lblCalories);

			 JProgressBar caloriesProgress = new JProgressBar();
			 caloriesProgress.setValue(20);
			 caloriesProgress.setToolTipText("Current progress towards your goal");
			 caloriesProgress.setForeground(SystemColor.textHighlight);
			 caloriesProgress.setBounds(21, 110, 210, 36);
			 caloriesBurned.add(caloriesProgress);
	     }
	     
	     /**
	      * creates the panel to display user distance traveled
	      */
	     private void distancePanel()
	     {	 
			 DashBoardPanel distanceTraveled = new DashBoardPanel(50, 300);
			 distanceTraveled.setLayout(null);
			 distanceTraveled.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 DistancePanel distance = new DistancePanel();
	    			 distance.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
			 contentPane.add(distanceTraveled);
			 
			 JLabel lblDistance = new JLabel("Distance"); 
			 lblDistance.setFont(new Font("Noteworthy", Font.PLAIN, 25));
			 lblDistance.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDistance.setBounds(66,56,136,42);
			 
			 distanceTraveled.add(lblDistance);
			 
			 JProgressBar distanceProgress = new JProgressBar();
			 distanceProgress.setValue(20);
			 distanceProgress.setToolTipText("Current progress towards your goal");
			 distanceProgress.setForeground(SystemColor.textHighlight);
			 distanceProgress.setBounds(21,110,210,36);
			 distanceTraveled.add(distanceProgress);
	     }
	     
	     /**
	      * creates the panel to display user active minutes
	      */
	     private void activeMinutesPanel()
	     {
	    	 /*---------------------------------------*/
			 //Active Minutes panel
			 /*---------------------------------------*/
			 
			 DashBoardPanel activeMinutes = new DashBoardPanel(50, 300);
			 activeMinutes.setBounds(385, 300, 265, 155);
			 activeMinutes.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 activeMinutes.setLayout(null);
			 contentPane.add(activeMinutes);
			 
			 
			 JLabel lblActiveMin = new JLabel("Active Minutes");
			 lblActiveMin.setFont(new Font("Noteworthy", Font.PLAIN, 25));
			 lblActiveMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblActiveMin.setBounds(54, 53, 170, 47);
			 activeMinutes.add(lblActiveMin);
	     }
	     
	     /**
	      * creates the panel to display user sedentary minutes
	      */
	     private void sedentaryMinutesPanel()
	     {
	    	 /*---------------------------------------*/
			 //Sedentary Minutes panel
			 /*---------------------------------------*/
			 
			 DashBoardPanel sedentaryMinutes = new DashBoardPanel(50, 300);
			 sedentaryMinutes.setLayout(null);
			 sedentaryMinutes.setBounds(709, 300, 265, 155);
			 sedentaryMinutes.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 contentPane.add(sedentaryMinutes);
			 
			 JLabel lblSedentaryMin = new JLabel("Sedentary Minutes");
			 lblSedentaryMin.setFont(new Font("Noteworthy", Font.PLAIN, 25));
			 lblSedentaryMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblSedentaryMin.setBounds(25, 52, 215, 50);
			 sedentaryMinutes.add(lblSedentaryMin );
	     }
	     
	     /**
	      * creates the panel to display user accolades
	      */
	     private void accoladesPanel()
	     {
	    	 /*---------------------------------------*/
			 //Accolades panel
			 /*---------------------------------------*/
			 DashBoardPanel accoladesPanel = new DashBoardPanel(50, 300);
			 accoladesPanel.setLayout(null);
			 accoladesPanel.setBounds(51, 501, 265, 155);
			 accoladesPanel.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 contentPane.add(accoladesPanel);
			 
			 JLabel lblAccolades = new JLabel("Accolades");
			 lblAccolades.setFont(new Font("Noteworthy", Font.PLAIN, 25));
			 lblAccolades.setHorizontalAlignment(SwingConstants.CENTER);
			 lblAccolades.setBounds(65, 56, 136, 49);
			 accoladesPanel.add(lblAccolades);
	     }
	     
	     /**
	      * creates the panel to display user heart rate
	      */
	     private void heartRatePanel()
	     {
	    	 
			 /*---------------------------------------*/
			 //Heart Rate panel
			 /*---------------------------------------*/
			 DashBoardPanel heartRatePanel = new DashBoardPanel(50, 300);
			 heartRatePanel.setLayout(null);
			 heartRatePanel.setBounds(385, 501, 265, 155);
			 heartRatePanel.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 contentPane.add(heartRatePanel);
			 
			 JLabel lblHeart = new JLabel("Heart Rate Zones");
			 lblHeart.setFont(new Font("Noteworthy", Font.PLAIN, 25));
			 lblHeart.setHorizontalAlignment(SwingConstants.CENTER);
			 lblHeart.setBounds(38, 50, 195, 53);
			 heartRatePanel.add(lblHeart);
	     }
	     
	     /***
	      * creates the panel to display user goals
	      */
	     private void goalsPanel()
	     {
	    	 /*---------------------------------------*/
			 //Daily goals panel
			 /*---------------------------------------*/
			 DashBoardPanel dailyGoals = new DashBoardPanel(50, 300);
			 dailyGoals.setLayout(null);
			 dailyGoals.setBounds(709, 501, 265, 155);
			 dailyGoals.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 contentPane.add(dailyGoals);
			 
			 JLabel lblDaily = new JLabel("Daily Goals");
			 lblDaily.setFont(new Font("Noteworthy", Font.PLAIN, 25));
			 lblDaily.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDaily.setBounds(64, 62, 136, 41);
			 dailyGoals.add(lblDaily);
			 
		
	     }
}

