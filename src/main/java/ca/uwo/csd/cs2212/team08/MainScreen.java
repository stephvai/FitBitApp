
package ca.uwo.csd.cs2212.team08;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

/**
 * a main screen class to act as the daily dash board for users, this is the first screen to launch
 *
 */
public class MainScreen extends JFrame {
	
	//a content pane to store all the components
	private JPanel contentPane;
	//a string to store the current date the user wants
	private String date;
	//a date picker the user can use to change dates
	private JDatePickerImpl datePicker;
	//constants to store the picture locations
	private static final String picRefresh = "src/main/resources/images/refresh.png";
	//use to get the API information
	private APIData apiData;
	
	//Color Scheme 
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(168,219,168);
	private Color borderColor = new Color(121,189,154);
	private Color titleColor = new Color(11,72,107);
	private Color white = Color.white;
	
		/**
		 * a constructor used to create a new dash board window
		 * @param date this is the date selected by the user 
		 * @param paramAPIData an instance of the APIData that passes in the fitbit information
		 */
	     public MainScreen(String date, APIData paramAPIData) {
	     	
	          try {
				this.initUI(date, paramAPIData);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	    /**
	     * A method to build the User Interface
	     * @param paramDate this is the date selected by the user 
	     * @param paramAPIData an instance of the APIData that passes in the fitbit information
	     * @throws ClassNotFoundException 
	     */
	     private void initUI(String paramDate, APIData paramAPIData) throws ClassNotFoundException 
	     { 
	    	 /*	-----------------------------------------*/
	    	 //create the main window for the daily dash board 
	    	 /*	-----------------------------------------*/
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
	    	 
	    	 /*------------------------------------------*/
			 //create a label to display the title of the panel
	    	 /*	-----------------------------------------*/
			 JLabel lblTitle = new JLabel("     Welcome! Here is your daily dashboard: ");
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
					 updateDate();
					 if (!apiData.refreshDailyDashBoardData(date)) {
			    		 JOptionPane.showMessageDialog(contentPane, "An error has occured connecting to fitbit servers, please try again later.");
			    	 }
					 try {
						initUI(date, apiData);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
	     private void stepsPanel() throws ClassNotFoundException
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
	    	 GoalTracker stepsGoal = new GoalTracker();
	    	 stepsGoal.setGoal("0", GoalsEnum.steps);
	    	 stepsGoal.updateProgress();
	    	 
	    
	    	 //set the layout to absolute
	    	 pnlSteps.setLayout(null);
	    	 pnlSteps.setToolTipText("click here to see more information!");
	    	 //add panel to the content pane
	    	 contentPane.add(pnlSteps);

	    	 /*------------------------------------------*/
	    	 //create a progress bar for the steps panel
	    	 /*------------------------------------------*/
	    	 JProgressBar stepsProgress = new JProgressBar();
	    	 //this will be switched with a ratio between the daily goal and the current steps
	    	 stepsProgress.setValue(Integer.parseInt(stepsGoal.getGoal(GoalsEnum.steps)));
	    	 
	    	 stepsProgress.setToolTipText("Current progress towards your goal");
	    	 stepsProgress.setForeground(new Color(51, 153, 255));
	    	 stepsProgress.setBounds(17, 113, 231, 36);
	    	 stepsProgress.repaint();
	    	 pnlSteps.add(stepsProgress);
	    	 
	    	 /*------------------------------------------*/
	    	 //create a label to display the steps
	    	 /*------------------------------------------*/
	    	 JLabel lblSteps = new JLabel(Float.toString(apiData.getSteps()));
	    	 lblSteps.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
	    	 lblSteps.setHorizontalAlignment(SwingConstants.CENTER);
	    	 lblSteps.setBounds(0, 10, 265, 33);
	    	 pnlSteps.add(lblSteps);

	    	 /*------------------------------------------*/
	    	 //create a label to display the steps title
	    	 /*------------------------------------------*/
	    	 JLabel lblStepsTtile = new JLabel("Steps");
	    	 lblStepsTtile.setBounds(0, 53, 265, 26);
	    	 pnlSteps.add(lblStepsTtile);
	    	 lblStepsTtile.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
	    	 lblStepsTtile.setHorizontalAlignment(SwingConstants.CENTER);
		 }

	     /**
	      * creates the panel to display the the stairs climbed
	     * @throws ClassNotFoundException 
	      */
	     private void stairsPanel() throws ClassNotFoundException
	     {
	    	 DashBoardPanel StairsPanel = new DashBoardPanel(413, 191);
	    	 StairsPanel.setLocation(385, 99);
	    	 StairsPanel.setToolTipText("click here to see more information!");
	    	 StairsPanel.setLayout(null);
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

	    	 /*------------------------------------------*/
	    	 //create a label to display the floors climbed title
	    	 /*------------------------------------------*/
			 JLabel lblStairs = new JLabel("Floors Climbed:");
			 lblStairs.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblStairs.setHorizontalAlignment(SwingConstants.CENTER);
			 lblStairs.setBounds(0, 51, 265, 38);
			 StairsPanel.add(lblStairs);

			 /*------------------------------------------*/
	    	 //create a label to display the floors climbed title
	    	 /*------------------------------------------*/
			 GoalTracker floorsGoal = new GoalTracker();
	    	 floorsGoal.setGoal("0", GoalsEnum.calorieBurned);
	    	 floorsGoal.updateProgress();
	    	 
			 JProgressBar stairsProgress = new JProgressBar();
			 stairsProgress.setValue(Integer.parseInt(floorsGoal.getGoal(GoalsEnum.floorsClimbed)));
			 stairsProgress.setToolTipText("Current progress towards your goal");
			 stairsProgress.setForeground(SystemColor.textHighlight);
			 stairsProgress.setBounds(17, 113, 231, 36);
			 StairsPanel.add(stairsProgress);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the floors climbed
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Float.toString(apiData.getFloorsClimbed()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 12, 265, 33);
			 StairsPanel.add(label);
	     }
	     
	     /**
	      * creates the panel to display calories panel
	     * @throws ClassNotFoundException 
	      */
	     private void caloriesPanel() throws ClassNotFoundException
	     {
	    	 /*---------------------------------------*/
			 //calories panel
			 /*---------------------------------------*/
	    	 
	    	 GoalTracker caloriesGoal = new GoalTracker();
	    	 caloriesGoal.setGoal("0", GoalsEnum.calorieBurned);
	    	 caloriesGoal.updateProgress();
	    	 
			 DashBoardPanel caloriesBurned = new DashBoardPanel(776, 191);
			 caloriesBurned.setLocation(709, 99);
			 caloriesBurned.setToolTipText("click here to see more information!");
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

			 /*------------------------------------------*/
	    	 //create a label to display the Calories burned title
	    	 /*------------------------------------------*/
			 JLabel lblCalories = new JLabel("Calories Burned");
			 lblCalories.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblCalories.setHorizontalAlignment(SwingConstants.CENTER);
			 lblCalories.setBounds(0,45,265,53);
			 caloriesBurned.add(lblCalories);

			 /*------------------------------------------*/
	    	 //create a label to create a new progress bar
	    	 /*------------------------------------------*/
			 JProgressBar caloriesProgress = new JProgressBar();
			 caloriesProgress.setValue(Integer.parseInt(caloriesGoal.getGoal(GoalsEnum.calorieBurned)));
			 caloriesProgress.setToolTipText("Current progress towards your goal");
			 caloriesProgress.setForeground(SystemColor.textHighlight);
			 caloriesProgress.setBounds(21, 110, 210, 36);
			 caloriesBurned.add(caloriesProgress);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Calories burned
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Float.toString(apiData.getCalories()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 10, 265, 33);
			 caloriesBurned.add(label);
	     }
	     
	     /**
	      * creates the panel to display user distance traveled
	     * @throws ClassNotFoundException 
	      */
	     private void distancePanel() throws ClassNotFoundException
	     {	 
	    	 /*------------------------------------------*/
	    	 //create a Distance traveled panel
	    	 /*------------------------------------------*/
			 DashBoardPanel distanceTraveled = new DashBoardPanel(50, 300);
			 distanceTraveled.setLayout(null);
			 distanceTraveled.setToolTipText("click here to see more information!");
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
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Distance title
	    	 /*------------------------------------------*/
			 JLabel lblDistance = new JLabel("Distance"); 
			 lblDistance.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblDistance.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDistance.setBounds(0,56,265,42);
			 distanceTraveled.add(lblDistance);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Distance progress bar
	    	 /*------------------------------------------*/
			 
			 GoalTracker distanceGoal = new GoalTracker();
	    	 distanceGoal.setGoal("0", GoalsEnum.calorieBurned);
	    	 distanceGoal.updateProgress();
	    	 
			 JProgressBar distanceProgress = new JProgressBar();
			 distanceProgress.setValue(Integer.parseInt(distanceGoal.getGoal(GoalsEnum.distance)));
			 distanceProgress.setToolTipText("Current progress towards your goal");
			 distanceProgress.setForeground(SystemColor.textHighlight);
			 distanceProgress.setBounds(21,110,210,36);
			 distanceTraveled.add(distanceProgress);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Distance
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Double.toString(apiData.getDistance())+"km");
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 10, 265, 33);
			 distanceTraveled.add(label);
	     }
	     
	     /**
	      * creates the panel to display user active minutes
	     * @throws ClassNotFoundException 
	      */
	     private void activeMinutesPanel() throws ClassNotFoundException
	     {
	    	 /*---------------------------------------*/
			 //Active Minutes panel
			 /*---------------------------------------*/
			 DashBoardPanel activeMinutes = new DashBoardPanel(50, 300);
			 activeMinutes.setBounds(385, 300, 265, 155);
			 activeMinutes.setToolTipText("click here to see more information!");
			 activeMinutes.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 Minutes activeMinutes = new Minutes(date, apiData);
	    			 activeMinutes.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
			 activeMinutes.setLayout(null);
			 contentPane.add(activeMinutes);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Active Minutes title
	    	 /*------------------------------------------*/
			 JLabel lblActiveMin = new JLabel("Active Minutes");
			 lblActiveMin.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblActiveMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblActiveMin.setBounds(0, 53, 265, 47);
			 activeMinutes.add(lblActiveMin);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Active minutes
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Float.toString(apiData.getLightlyActiveMin() + apiData.getFairlyActiveMin() + apiData.getVeryActiveMin()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 14, 265, 33);
			 activeMinutes.add(label);
			 
			 /*------------------------------------------*/
	    	 //create a label to create a new progress bar
	    	 /*------------------------------------------*/
			 GoalTracker activeMinutesGoal = new GoalTracker();
	    	 activeMinutesGoal.setGoal("0", GoalsEnum.calorieBurned);
	    	 activeMinutesGoal.updateProgress();
			 
			 JProgressBar activeMinutesProgress = new JProgressBar();
			 activeMinutesProgress.setValue(Integer.parseInt(activeMinutesGoal.getGoal(GoalsEnum.veryActiveMinutes)));
			 activeMinutesProgress.setToolTipText("Current progress towards your goal");
			 activeMinutesProgress.setForeground(SystemColor.textHighlight);
			 activeMinutesProgress.setBounds(21, 110, 210, 36);
			 activeMinutes.add(activeMinutesProgress);
	     }
	     
	     /**
	      * creates the panel to display user sedentary minutes
	      */
	     private void sedentaryMinutesPanel()
	     {	 
	    	 /*------------------------------------------*/
	    	 //create the Sedentary Minutes panel
	    	 /*------------------------------------------*/
			 DashBoardPanel sedentaryMinutes = new DashBoardPanel(50, 300);
			 sedentaryMinutes.setLayout(null);
			 sedentaryMinutes.setBounds(709, 300, 265, 155);
			 sedentaryMinutes.setToolTipText("click here to see more information!");
			 sedentaryMinutes.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 Minutes sedentMinutes = new Minutes(date, apiData);
	    			 sedentMinutes.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
			 contentPane.add(sedentaryMinutes);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the sedentary minutes title
	    	 /*------------------------------------------*/
			 JLabel lblSedentaryMin = new JLabel("Sedentary Minutes");
			 lblSedentaryMin.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblSedentaryMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblSedentaryMin.setBounds(0, 52, 265, 50);
			 sedentaryMinutes.add(lblSedentaryMin );
			 
			 /*------------------------------------------*/
	    	 //create a label to display the sedentary minutes
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Float.toString(apiData.getSendentaryMinutes()));
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
	    	 /*------------------------------------------*/
	    	 //create a panel for the accolades
	    	 /*------------------------------------------*/
			 DashBoardPanel accoladesPanel = new DashBoardPanel(50, 300);
			 accoladesPanel.setLayout(null);
			 accoladesPanel.setBounds(51, 501, 265, 155);
			 accoladesPanel.setToolTipText("click here to see more information!");
			 accoladesPanel.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 accoladesPanel panel = new accoladesPanel(date, apiData);
	    			 panel.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
			 contentPane.add(accoladesPanel);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the accolades title
	    	 /*------------------------------------------*/
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
	    	 /*------------------------------------------*/
	    	 //create the heart rate panel
	    	 /*------------------------------------------*/
			 DashBoardPanel heartRatePanel = new DashBoardPanel(50, 300);
			 heartRatePanel.setLayout(null);
			 heartRatePanel.setBounds(385, 501, 265, 155);
			 heartRatePanel.setToolTipText("click here to see more information!");
			 heartRatePanel.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 HeartRateZones panel = new HeartRateZones(date,apiData);
	    			 panel.setVisible(true);
	    			 dispose();
	    			 
	    		 }
	    	 });
			 contentPane.add(heartRatePanel);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the heart rate title
	    	 /*------------------------------------------*/
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
	    	 /*------------------------------------------*/
	    	 //create the daily Goals panel
	    	 /*------------------------------------------*/
			 DashBoardPanel dailyGoals = new DashBoardPanel(50, 300);
			 dailyGoals.setLayout(null);
			 dailyGoals.setBounds(709, 501, 265, 155);
			 dailyGoals.setToolTipText("click here to see more information!");
			 dailyGoals.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 goalPanel goals = new goalPanel(date,apiData);
	    			 goals.setVisible(true);
	    			 dispose();
	    			 
	    		 }
	    	 });
			 contentPane.add(dailyGoals);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the daily goals title
	    	 /*------------------------------------------*/
			 JLabel lblDaily = new JLabel("Daily Goals");
			 lblDaily.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblDaily.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDaily.setBounds(0, 62, 265, 41);
			 dailyGoals.add(lblDaily);
			 
		
	     }
}

