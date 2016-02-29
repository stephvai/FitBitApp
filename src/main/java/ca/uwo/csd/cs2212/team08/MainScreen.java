package ca.uwo.csd.cs2212.team08;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import java.awt.Label;


public class MainScreen extends JFrame {
	private JPanel contentPane;
	private String date;
	private JDatePickerImpl datePicker;
	private static final String placeholder = "src/main/resources/Placeholder.png";
	private static final String picRefresh = "src/main/resources/images/refresh.png";
	private APIData apiData;
	
	//Color Scheme
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(168,219,168);
	private Color borderColor = new Color(121,189,154);
	private Color titleColor = new Color(11,72,107);
	private Color white = Color.white;
	
	
	     public MainScreen(String date, APIData paramAPIData) {
	     	
	          this.initUI(date, paramAPIData);
	     }
	    
	     private void initUI(String paramDate, APIData paramAPIData) 
	     { 
	    	 //create the main window for the daily dash board 
	    	 this.setTitle("Team08 Fitbit");
	    	 this.setSize(1024, 768);
	    	 this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	 this.setLocationRelativeTo(null);
	    	 this.setResizable(false);
	    	 contentPane = new JPanel();
	    	 contentPane.setBorder(new EmptyBorder(5, 0, 5, 0));
	    	 this.setContentPane(contentPane);
	    	 contentPane.setLayout(null);
	    	 contentPane.setBackground(bgColor);
	    	 
	    	 
	    	 date = paramDate;
	    	 apiData = paramAPIData;
	    	 //Refresh the API DATA
	    	 apiData = new APIData();
	    	 
	    	 
	    	 
	    	 /*------------------------------------------*/
			 //create a label to display the title of the panel
	    	 /*	-----------------------------------------*/
			 JLabel lblTitle = new JLabel("Welcome! Here is your daily dashboard: ");
			 lblTitle.setFont(new Font("Trebuchet MS", Font.PLAIN, 35));
			 contentPane.setForeground(titleColor);
			 lblTitle.setBounds(159, 0, 732, 72);
			 contentPane.add(lblTitle);
			 
			 
			 /*------------------------------------------*/
			 // creates a Header Panel 
			 /*------------------------------------------*/
			 JPanel headerPanel = new JPanel();
			 headerPanel.setBackground(white);
			 headerPanel.setBounds(0, 0, 1024, 63);
			 headerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			 contentPane.add(headerPanel);
			 
			 
			 /*------------------------------------------*/
			 //create the calendar user can use to pick a date
			 /*------------------------------------------*/
			 UtilDateModel model = new UtilDateModel();
			 //set the calendar date to the one given by java
			 String[] dateArray = this.date.split("-");
			 int year = Integer.parseInt(dateArray[0]);
			 int month = Integer.parseInt(dateArray[1]);
			 int day = Integer.parseInt(dateArray[2]);
			 model.setDate(year, month-1, day);
			 model.setSelected(true);
			 JDatePanelImpl datePanel = new JDatePanelImpl(model);
			 //create a date picker to allow the user to select the date
			 datePicker = new JDatePickerImpl(datePanel);
			 datePicker.setToolTipText("Please select the desired date");
			 datePicker.setBounds(385, 69, 225, 27);
			 contentPane.add(datePicker);
			 datePicker.setBackground(Color.WHITE);
			 
			 /*------------------------------------------*/
			 //create each panel used for the daily dash board
			 /*------------------------------------------*/
			 stepsPanel();
			 stairsPanel();
			 caloriesPanel();
			 distancePanel();
			 activeMinutesPanel();
			 sedentaryMinutesPanel();
			 accoladesPanel();
			 heartRatePanel();
			 goalsPanel();
			 
			/*-------------------------------------*/
			//the label to set the last updated time
			 /*------------------------------------*/
			 Calendar cal = Calendar.getInstance();
			 final JLabel lblDataUpdate = new JLabel("Last updated: " + cal.getTime().toString());
			 lblDataUpdate.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDataUpdate.setForeground(white);
			 lblDataUpdate.setBounds(51, 660, 923, 37);
			 contentPane.add(lblDataUpdate);

			 /*------------------------------------------*/
			 //create a refresh button to refresh the data
			 /*------------------------------------------*/
			 JLabel imgRefresh = new JLabel();
			 imgRefresh.setIcon(new ImageIcon(picRefresh));
			 imgRefresh.setBounds(613, 63, 36, 36);
			 imgRefresh.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mouseClicked(MouseEvent arg0) {
					 //what to do on button click
					 lblDataUpdate.setText("refreshing...");
					 //lblDataUpdate.repaint();
					 updateDate();
					 if (!apiData.refreshDailyDashBoardData(date)) {
			    		 //DISPLAY ERROR CONNECTING TO FITBIT SERVERS
			    		 JOptionPane.showMessageDialog(contentPane, "An error has occured connecting to fitbit servers, please try again later.");
			    	 }
					 //initUI(date);
					 contentPane.repaint();
				 }
			 });
			 contentPane.add(imgRefresh);

	     }

	     /**
	      * a method that can be used to pull a new date from the JDatePicker
	      */
	     private void updateDate()
	     {
	    	 //get the new date from the JDatePicker
	    	 String tempDate = datePicker.getJFormattedTextField().getText();
	    	 if(tempDate.equals("") || tempDate.equals(null))
	    	 {
	    		 return;
	    	 }
	    	 //save the date in a array of strings
	    	 String[] dateArray = tempDate.split("-");
	    	 //set the day
	    	 String day = dateArray[0];
	    	 //set the month
	    	 String month = dateArray[1];
	    	 
	    	 //switch the month from letters to numbers
	    	 if(month.equals("Jan")){
	    	 month = "01";	 
	    	 }
	    	 else if(month.equals("Feb")){
	    		 month = "02";
	    	 }
	    	 else if(month.equals("Mar")){
	    		 month = "03";
	    	 }
	    	 else if(month.equals("Apr")){
	    		 month = "04";
	    	 }
	    	 else if(month.equals("May")){
	    		 month = "05";
	    	 }
	    	 else if(month.equals("Jun")){
	    		 month = "06";
	    	 }
	    	 else if(month.equals("Jul")){
	    		 month = "07";
	    	 }
	    	 else if(month.equals("Aug")){
	    		 month = "08";
	    	 }
	    	 else if(month.equals("Sep")){
	    		 month = "09";
	    	 }
	    	 else if(month.equals("Oct")){
	    		 month = "10";
	    	 }
	    	 else if(month.equals("Nov")){
	    		 month = "11";
	    	 }
	    	 else if(month.equals("Dec")){
	    		 month = "12";
	    	 }
	    	 //set the year
	    	 String year = dateArray[2];
	    	 //save the date all in one string
	    	 this.date = year+"-"+month+"-"+day;
	     }
	     
	     /**
	      * creates the panel to display the steps taken
	      */
	     private void stepsPanel()
		 {
			//creates a new steps panel
	    	 DashBoardPanel pnlSteps = new DashBoardPanel(50, 191);
	    	 pnlSteps.setLocation(51, 99);
	    	 pnlSteps.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 StepsPanel steps = new StepsPanel(date, apiData);
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
	    	 
	    	 JLabel lblSteps = new JLabel(Integer.toString(apiData.getSteps()));
	    	 lblSteps.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
	    	 lblSteps.setHorizontalAlignment(SwingConstants.CENTER);
	    	 lblSteps.setBounds(0, 10, 265, 33);
	    	 pnlSteps.add(lblSteps);

	    	 JLabel lblStepsTtile = new JLabel("Steps");
	    	 lblStepsTtile.setBounds(0, 53, 265, 26);
	    	 pnlSteps.add(lblStepsTtile);
	    	 lblStepsTtile.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
	    	 lblStepsTtile.setHorizontalAlignment(SwingConstants.CENTER);
		 }

	     /**
	      * creates the panel to display the the stairs climbed
	      */
	     private void stairsPanel()
	     {
	    	 DashBoardPanel StairsPanel = new DashBoardPanel(413, 191);
	    	 StairsPanel.setLocation(385, 99);
	    	 StairsPanel.setLayout(null);
	    	 //StairsPanel.setBounds(413, 191, 308, 167);
	    	 StairsPanel.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 StairsPanel stairs = new StairsPanel(date, apiData);
	    			 stairs.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
	    	 contentPane.add(StairsPanel);

			 JLabel lblStairs = new JLabel("Floors Climbed:");
			 lblStairs.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblStairs.setHorizontalAlignment(SwingConstants.CENTER);
			 lblStairs.setBounds(0, 51, 265, 38);
			 StairsPanel.add(lblStairs);

			 // progress panel
			 JProgressBar stairsProgress = new JProgressBar();
			 stairsProgress.setValue(20);
			 stairsProgress.setToolTipText("Current progress towards your goal");
			 stairsProgress.setForeground(SystemColor.textHighlight);
			 stairsProgress.setBounds(17, 113, 231, 36);
			 StairsPanel.add(stairsProgress);
			 
			 JLabel label = new JLabel(Integer.toString(apiData.getFloorsClimbed()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 12, 265, 33);
			 StairsPanel.add(label);
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
	    			 CaloriesPanel calories = new CaloriesPanel(date, apiData);
	    			 calories.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
			 contentPane.add(caloriesBurned);

			 JLabel lblCalories = new JLabel("Calories Burned");
			 lblCalories.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblCalories.setHorizontalAlignment(SwingConstants.CENTER);
			 lblCalories.setBounds(0,45,265,53);
			 caloriesBurned.add(lblCalories);

			 JProgressBar caloriesProgress = new JProgressBar();
			 caloriesProgress.setValue(20);
			 caloriesProgress.setToolTipText("Current progress towards your goal");
			 caloriesProgress.setForeground(SystemColor.textHighlight);
			 caloriesProgress.setBounds(21, 110, 210, 36);
			 caloriesBurned.add(caloriesProgress);
			 
			 JLabel label = new JLabel(Integer.toString(apiData.getCalories()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 10, 265, 33);
			 caloriesBurned.add(label);
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
	    			 DistancePanel distance = new DistancePanel(date, apiData);
	    			 distance.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
			 contentPane.add(distanceTraveled);
			 
			 JLabel lblDistance = new JLabel("Distance"); 
			 lblDistance.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblDistance.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDistance.setBounds(0,56,265,42);
			 
			 distanceTraveled.add(lblDistance);
			 
			 JProgressBar distanceProgress = new JProgressBar();
			 distanceProgress.setValue(20);
			 distanceProgress.setToolTipText("Current progress towards your goal");
			 distanceProgress.setForeground(SystemColor.textHighlight);
			 distanceProgress.setBounds(21,110,210,36);
			 distanceTraveled.add(distanceProgress);
			 
			 JLabel label = new JLabel(Double.toString(apiData.getDistance()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 10, 265, 33);
			 distanceTraveled.add(label);
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
			 lblActiveMin.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblActiveMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblActiveMin.setBounds(0, 53, 265, 47);
			 activeMinutes.add(lblActiveMin);
			 
			 JLabel label = new JLabel(Integer.toString(apiData.getLightlyActiveMin() + apiData.getFairlyActiveMin() + apiData.getVeryActiveMin()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 14, 265, 33);
			 activeMinutes.add(label);
	     }
	     
	     /**
	      * creates the panel to display user sedentary minutes
	      */
	     private void sedentaryMinutesPanel()
	     {	 
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
			 lblSedentaryMin.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblSedentaryMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblSedentaryMin.setBounds(0, 52, 265, 50);
			 sedentaryMinutes.add(lblSedentaryMin );
			 
			 JLabel label = new JLabel(Integer.toString(apiData.getSendentaryMinutes()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 13, 265, 33);
			 sedentaryMinutes.add(label);
	     }
	     
	     /**
	      * creates the panel to display user accolades
	      */
	     private void accoladesPanel()
	     {
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
			 lblAccolades.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblAccolades.setHorizontalAlignment(SwingConstants.CENTER);
			 lblAccolades.setBounds(0, 56, 265, 49);
			 accoladesPanel.add(lblAccolades);
	     }
	     
	     /**
	      * creates the panel to display user heart rate
	      */
	     private void heartRatePanel()
	     {
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
			 lblHeart.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblHeart.setHorizontalAlignment(SwingConstants.CENTER);
			 lblHeart.setBounds(0, 50, 265, 53);
			 heartRatePanel.add(lblHeart);
	     }
	     
	     /***
	      * creates the panel to display user goals
	      */
	     private void goalsPanel()
	     {
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
			 lblDaily.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblDaily.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDaily.setBounds(0, 62, 265, 41);
			 dailyGoals.add(lblDaily);
			 
		
	     }
}

