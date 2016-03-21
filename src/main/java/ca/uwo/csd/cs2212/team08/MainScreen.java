
package ca.uwo.csd.cs2212.team08;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.Label;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * a main screen class to act as the daily dash board for users, this is the first screen to launch
 *
 */
public class MainScreen extends JFrame implements Serializable {
	
	//a content pane to store all the components
	private JPanel contentPane;
	//a string to store the current date the user wants
	private String date;
	//a date picker the user can use to change dates
	private JDatePickerImpl datePicker;
	//constants to store the picture locations
	private static final String picRefresh = "src/main/resources/images/refresh.png";
	private static final String picEdit = "src/main/resources/images/EditMode.png";
	//use to get the API information
	private APIData apiData;
	//create a linked list for each dash board panel
	private LinkedList<Integer> dashboardPanels;
	private DashBoardPanel pnlSteps;
	private DashBoardPanel pnlStairs;
	private DashBoardPanel pnlCalories;
	private DashBoardPanel pnlDistance;
	private DashBoardPanel pnlActiveMin;
	private DashBoardPanel pnlSedentaryMin;
	private DashBoardPanel pnlAccolades;
	private DashBoardPanel pnlHeartRate;
	private DashBoardPanel pnlGoals;
	
	private final int Steps = 0;
	private final int Stairs = 1;
	private final int Calories = 2;
	private final int Distance = 3;
	private final int ActiveMin = 4;
	private final int SedentaryMin = 5;
	private final int Accolades = 6;
	private final int HeartRate = 7;
	private final int Goals = 8;
	
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
	     	
	          this.initUI(date, paramAPIData);
	     }
	    /**
	     * A method to build the User Interface
	     * @param paramDate this is the date selected by the user 
	     * @param paramAPIData an instance of the APIData that passes in the fitbit information
	     */
	     private void initUI(String paramDate, APIData paramAPIData) 
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
					 if(!apiData.refreshDailyDashBoardData(date)) {
			    		 JOptionPane.showMessageDialog(contentPane, "An error has occured connecting to fitbit servers, please try again later.");
			    	 }
					 contentPane.repaint();
					 initUI(date, apiData);
				 }
			 });
			 contentPane.add(imgRefresh);

			 
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
			 
			 /*------------------------------------------*/
			 //create a panel to customize the dash board
			 /*------------------------------------------*/
			 
			 headerPanel.setLayout(null);
			 JLabel imgEdit = new JLabel();
			 imgEdit.setIcon(new ImageIcon(picEdit));
			 imgEdit.setBounds(966, 5, 48, 48);
			 imgEdit.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mouseClicked(MouseEvent arg0) {
					 //what to do on button click
					 EditMode edit = new EditMode(date, apiData);
					 edit.setVisible(true);
					 dispose();
				 }
			 });
			 headerPanel.add(imgEdit);
			 //contentPane.add(imgEdit);

			 //put steps last
			 dashboardPanels = new LinkedList<Integer>();
			 deSerialize();
			 if(this.dashboardPanels.isEmpty())
			 {
				 dashboardPanels.add(Steps);
				 dashboardPanels.add(Stairs);
				 dashboardPanels.add(Calories);
				 dashboardPanels.add(Distance);
				 dashboardPanels.add(ActiveMin);
				 dashboardPanels.add(SedentaryMin);
				 dashboardPanels.add(Accolades);
				 dashboardPanels.add(HeartRate);
				 dashboardPanels.add(Goals);
				 serialize();
			 }
			 int x =65;
			 int y =100;
			 Iterator<Integer> iter = dashboardPanels.iterator();
			 DashBoardPanel tempPanel;
			 while(iter.hasNext())
			 {
				 int temp = iter.next();
				 if(temp == 0)
					{
						pnlSteps.setLocation(x, y);
						pnlSteps.setVisible(true);
					}
					else if(temp == 1)
					{
						pnlStairs.setLocation(x, y);
						pnlStairs.setVisible(true);
					}
					else if(temp == 2)
					{
						pnlCalories.setLocation(x, y);
						pnlCalories.setVisible(true);
					}
					else if(temp == 3)
					{
						pnlDistance.setLocation(x, y);
						pnlDistance.setVisible(true);
					}
					else if(temp == 4)
					{
						pnlActiveMin.setLocation(x, y);
						pnlActiveMin.setVisible(true);
					}
					else if(temp == 5)
					{
						pnlSedentaryMin.setLocation(x, y);
						pnlSedentaryMin.setVisible(true);
					}
					else if(temp == 6)
					{
						pnlAccolades.setLocation(x, y);
						pnlAccolades.setVisible(true);
					}
					else if(temp == 7)
					{
						pnlHeartRate.setLocation(x, y);
						pnlHeartRate.setVisible(true);
					}
					else if(temp == 8)
					{
						pnlGoals.setLocation(x, y);
						pnlGoals.setVisible(true);
					}
					
				 //iter.next().setLocation(x, y);
				 //DashBoardPanel temp = iter.next();
				 //temp.setLocation(x, y);
				 //System.out.println(temp.getName());
				 //System.out.println(dashboardPanels.toString());
				 x = x + 308;
				 if(x >= 974)
				 {
					 y = y + 200;
					 x = 65;
				 }
				 if(y >= 656)
				 {
					 y = 100;
				 }
			 }
	     }

	     /**
	      * a method that can be used to pull a new date from the JDatePicker
	      */
	     private void updateDate()
	     {
	    	 //get the new date from the JDatePicker
	    	 String tempDate = datePicker.getJFormattedTextField().getText();
	    	 //datePicker.getJFormattedTextField().get
	    	 
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
	    	 String temp = year+"-"+month+"-"+day;
	    	 //this.date = year+"-"+month+"-"+day;
	    	 try {
	    		 java.util.Date chosenDate = new SimpleDateFormat("yyy-MM-dd").parse(temp);
	    		 Calendar cal = Calendar.getInstance();
	    		 if(cal.getTime().compareTo(chosenDate)<0)
	    		 {
	    			 JOptionPane.showMessageDialog(contentPane, "That is not a valid date");
	    			 return;
	    			 /*cal = Calendar.getInstance();
	    			 int yearTemp = cal.get(Calendar.YEAR);
	    			 int monthTemp = cal.get(Calendar.MONTH)+1;
	    			 int dayTemp = cal.get(Calendar.DAY_OF_MONTH);
	    			 //add an extra zero to the month if it is needed
	    			 String monthString;
	    			 if (monthTemp<10)
	    			 {
	    					monthString = "0" + Integer.toString(monthTemp);
	    				}
	    				else
	    				{
	    					monthString = Integer.toString(monthTemp); 
	    				}
	    				//save the date to a string
	    				this.date = Integer.toString(yearTemp) +  "-" + monthString + "-" + Integer.toString(dayTemp);
	    				JOptionPane.showMessageDialog(contentPane, "That is not a valid date");*/
	    		 }
	    		 else{
	    			 this.date = temp;
	    		 }
	    	 } catch (ParseException e) {
	    		 // TODO Auto-generated catch block
	    		 JOptionPane.showMessageDialog(contentPane, "Invalid date format.");
			}
	    	 
	     }
	   
	     /**
	      * creates the panel to display the steps taken
	      */
	     private void stepsPanel()
		 {
			//creates a new steps panel
	    	 pnlSteps = new DashBoardPanel(50, 191);
	    	 //pnlSteps.setLocation(51, 99);
	    	 pnlSteps.setVisible(false);
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

	    	 /*------------------------------------------*/
	    	 //create a progress bar for the steps panel
	    	 /*------------------------------------------*/
	    	 JProgressBar stepsProgress = new JProgressBar();
	    	 //this will be switched with a ratio between the daily goal and the current steps
	    	 stepsProgress.setValue(20);
	    	 stepsProgress.setToolTipText("Current progress towards your goal");
	    	 stepsProgress.setForeground(new Color(51, 153, 255));
	    	 stepsProgress.setBounds(17, 113, 231, 36);
	    	 pnlSteps.add(stepsProgress);
	    	 
	    	 /*------------------------------------------*/
	    	 //create a label to display the steps
	    	 /*------------------------------------------*/
	    	 JLabel lblSteps = new JLabel(Integer.toString((int)apiData.getSteps()));
	    	 lblSteps.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
	    	 lblSteps.setHorizontalAlignment(SwingConstants.CENTER);
	    	 lblSteps.setBounds(0, 21, 265, 33);
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
	      */
	     private void stairsPanel()
	     {
	    	 pnlStairs = new DashBoardPanel(413, 191);
	    	 //pnlStairs.setLocation(385, 99);
	    	 pnlStairs.setLayout(null);
	    	 pnlStairs.setVisible(false);
	    	 pnlStairs.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 StairsPanel stairs = new StairsPanel(date, apiData);
	    			 stairs.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
	    	 contentPane.add(pnlStairs);

	    	 /*------------------------------------------*/
	    	 //create a label to display the floors climbed title
	    	 /*------------------------------------------*/
			 JLabel lblStairs = new JLabel("Floors Climbed:");
			 lblStairs.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblStairs.setHorizontalAlignment(SwingConstants.CENTER);
			 lblStairs.setBounds(0, 51, 265, 38);
			 pnlStairs.add(lblStairs);

			 /*------------------------------------------*/
	    	 //create a label to display the floors climbed title
	    	 /*------------------------------------------*/
			 JProgressBar stairsProgress = new JProgressBar();
			 stairsProgress.setValue(20);
			 stairsProgress.setToolTipText("Current progress towards your goal");
			 stairsProgress.setForeground(SystemColor.textHighlight);
			 stairsProgress.setBounds(17, 113, 231, 36);
			 pnlStairs.add(stairsProgress);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the floors climbed
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Integer.toString((int)apiData.getFloorsClimbed()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 12, 265, 33);
			 pnlStairs.add(label);
	     }
	     
	     /**
	      * creates the panel to display calories panel
	      */
	     private void caloriesPanel()
	     {
	    	 /*---------------------------------------*/
			 //calories panel
			 /*---------------------------------------*/
			 pnlCalories = new DashBoardPanel(776, 191);
			 //pnlCalories.setLocation(709, 99);
			 pnlCalories.setLayout(null);
			 pnlCalories.setVisible(false);
			 pnlCalories.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 CaloriesPanel calories = new CaloriesPanel(date, apiData);
	    			 calories.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
			 contentPane.add(pnlCalories);

			 /*------------------------------------------*/
	    	 //create a label to display the Calories burned title
	    	 /*------------------------------------------*/
			 JLabel lblCalories = new JLabel("Calories Burned");
			 lblCalories.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblCalories.setHorizontalAlignment(SwingConstants.CENTER);
			 lblCalories.setBounds(0,45,265,53);
			 pnlCalories.add(lblCalories);

			 /*------------------------------------------*/
	    	 //create a label to create a new progress bar
	    	 /*------------------------------------------*/
			 JProgressBar caloriesProgress = new JProgressBar();
			 caloriesProgress.setValue(20);
			 caloriesProgress.setToolTipText("Current progress towards your goal");
			 caloriesProgress.setForeground(SystemColor.textHighlight);
			 caloriesProgress.setBounds(21, 110, 210, 36);
			 pnlCalories.add(caloriesProgress);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Calories burned
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Integer.toString((int)apiData.getCalories()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 10, 265, 33);
			 pnlCalories.add(label);
	     }
	     
	     /**
	      * creates the panel to display user distance traveled
	      */
	     private void distancePanel()
	     {	 
	    	 /*------------------------------------------*/
	    	 //create a Distance traveled panel
	    	 /*------------------------------------------*/
			 pnlDistance = new DashBoardPanel(50, 300);
			 pnlDistance.setLayout(null);
			 pnlDistance.setVisible(false);
			 pnlDistance.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 DistancePanel distance = new DistancePanel(date, apiData);
	    			 distance.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
			 contentPane.add(pnlDistance);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Distance title
	    	 /*------------------------------------------*/
			 JLabel lblDistance = new JLabel("Distance"); 
			 lblDistance.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblDistance.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDistance.setBounds(0,56,265,42);
			 pnlDistance.add(lblDistance);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Distance progress bar
	    	 /*------------------------------------------*/
			 JProgressBar distanceProgress = new JProgressBar();
			 distanceProgress.setValue(20);
			 distanceProgress.setToolTipText("Current progress towards your goal");
			 distanceProgress.setForeground(SystemColor.textHighlight);
			 distanceProgress.setBounds(21,110,210,36);
			 pnlDistance.add(distanceProgress);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Distance
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Float.toString(apiData.getDistance()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 10, 265, 33);
			 pnlDistance.add(label);
	     }
	     
	     /**
	      * creates the panel to display user active minutes
	      */
	     private void activeMinutesPanel()
	     {
	    	 /*---------------------------------------*/
			 //Active Minutes panel
			 /*---------------------------------------*/
			 pnlActiveMin = new DashBoardPanel(50, 300);
			 pnlActiveMin.setLayout(null);
			 pnlActiveMin.setVisible(false);
			 //pnlActiveMin.setBounds(385, 300, 265, 155);
			 pnlActiveMin.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 contentPane.add(pnlActiveMin);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Active Minutes title
	    	 /*------------------------------------------*/
			 JLabel lblActiveMin = new JLabel("Active Minutes");
			 lblActiveMin.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblActiveMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblActiveMin.setBounds(0, 53, 265, 47);
			 pnlActiveMin.add(lblActiveMin);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Active minutes
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Integer.toString((int)apiData.getLightlyActiveMin() + (int)apiData.getFairlyActiveMin() + (int)apiData.getVeryActiveMin()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 14, 265, 33);
			 pnlActiveMin.add(label);
	     }
	     
	     /**
	      * creates the panel to display user sedentary minutes
	      */
	     private void sedentaryMinutesPanel()
	     {	 
	    	 /*------------------------------------------*/
	    	 //create the Sedentary Minutes panel
	    	 /*------------------------------------------*/
			 pnlSedentaryMin = new DashBoardPanel(50, 300);
			 pnlSedentaryMin.setLayout(null);
			 pnlSedentaryMin.setVisible(false);
			 //pnlSedentaryMin.setBounds(709, 300, 265, 155);
			 pnlSedentaryMin.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 contentPane.add(pnlSedentaryMin);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the sedentary minutes title
	    	 /*------------------------------------------*/
			 JLabel lblSedentaryMin = new JLabel("Sedentary Minutes");
			 lblSedentaryMin.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblSedentaryMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblSedentaryMin.setBounds(0, 52, 265, 50);
			 pnlSedentaryMin.add(lblSedentaryMin );
			 
			 /*------------------------------------------*/
	    	 //create a label to display the sedentary minutes
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Integer.toString((int)apiData.getSendentaryMinutes()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 13, 265, 33);
			 pnlSedentaryMin.add(label);
	     }
	     
	     /**
	      * creates the panel to display user accolades
	      */
	     private void accoladesPanel()
	     {
	    	 /*------------------------------------------*/
	    	 //create a panel for the accolades
	    	 /*------------------------------------------*/
			 pnlAccolades = new DashBoardPanel(50, 300);
			 pnlAccolades.setLayout(null);
			 pnlAccolades.setVisible(false);
			 //pnlAccolades.setBounds(51, 501, 265, 155);
			 pnlAccolades.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 contentPane.add(pnlAccolades);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the accolades title
	    	 /*------------------------------------------*/
			 JLabel lblAccolades = new JLabel("Accolades");
			 lblAccolades.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblAccolades.setHorizontalAlignment(SwingConstants.CENTER);
			 lblAccolades.setBounds(0, 56, 265, 49);
			 pnlAccolades.add(lblAccolades);
	     }
	     
	     /**
	      * creates the panel to display user heart rate
	      */
	     private void heartRatePanel()
	     {
	    	 /*------------------------------------------*/
	    	 //create the heart rate panel
	    	 /*------------------------------------------*/
			 pnlHeartRate = new DashBoardPanel(50, 300);
			 pnlHeartRate.setLayout(null);
			 pnlHeartRate.setVisible(false);
			 //pnlHeartRate.setBounds(385, 501, 265, 155);
			 pnlHeartRate.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 contentPane.add(pnlHeartRate);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the heart rate title
	    	 /*------------------------------------------*/
			 JLabel lblHeart = new JLabel("Heart Rate Zones");
			 lblHeart.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblHeart.setHorizontalAlignment(SwingConstants.CENTER);
			 lblHeart.setBounds(0, 50, 265, 53);
			 pnlHeartRate.add(lblHeart);
	     }
	     
	     /***
	      * creates the panel to display user goals
	      */
	     private void goalsPanel()
	     {
	    	 /*------------------------------------------*/
	    	 //create the daily Goals panel
	    	 /*------------------------------------------*/
			 pnlGoals = new DashBoardPanel(50, 300);
			 pnlGoals.setLayout(null);
			 pnlGoals.setVisible(false);
			 //pnlGoals.setBounds(709, 501, 265, 155);
			 pnlGoals.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 
	    		 }
	    	 });
			 contentPane.add(pnlGoals);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the daily goals title
	    	 /*------------------------------------------*/
			 JLabel lblDaily = new JLabel("Daily Goals");
			 lblDaily.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblDaily.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDaily.setBounds(0, 62, 265, 41);
			 pnlGoals.add(lblDaily);
			 
		
	     }
	     
	     private void serialize()
	     {
	    	 ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(new FileOutputStream("src/main/resources/dashboardPanel.txt"));
				 out.writeObject(dashboardPanels);
				 out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	     }
	     
		private void deSerialize()
	     {
	    	 ObjectInputStream in;
			try {
				in = new ObjectInputStream(new FileInputStream("src/main/resources/dashboardPanel.txt"));
				this.dashboardPanels = (LinkedList<Integer>)in.readObject();
				 in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	     }
}

