
package ca.uwo.csd.cs2212.team08;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
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


	private GoalTracker goalTracker;

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
	//private Color pannelColor = new Color(168,219,168);
	private Color pannelHoverColor = new Color(255,255,255);
	//private Color pannelHoverColor = new Color(188, 240, 188);
	private Color pannelColor = new Color(206,206,206);
	private Color borderColor = new Color(121,189,154);
	
	private Color transparentColor = new Color(0,0,0,60);
	private Color transparentHoverColor = new Color(0,0,0,60);
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
	    	 contentPane = new JPanel() {
	    		 /*
	    		  * background image
	    		  * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	    		  */
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

			 
			/*-------------------------------------*/
			//the label to set the last updated time
			 /*------------------------------------*/
			 Calendar cal = Calendar.getInstance();
			 final JLabel lblDataUpdate = new JLabel("Last updated: " + cal.getTime().toString());
			 lblDataUpdate.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDataUpdate.setForeground(white);
			 lblDataUpdate.setBounds(51, 660, 923, 37);
			 contentPane.add(lblDataUpdate);
			 
			 /*-------------------------------------*
			  * label to show legal disclaimer
			  *-------------------------------------*/
			 JLabel legalLabel = new JLabel("Designed for use with the FITBIT® platform");
			 legalLabel.setHorizontalAlignment(SwingConstants.CENTER);
			 legalLabel.setForeground(white);
			 legalLabel.setBounds(51,680, 923,37);
			 contentPane.add(legalLabel);
			 
			 /*------------------------------------*
			  * Legal information button
			  *------------------------------------*/
			 //JButton legalButton = new JButton();

			 goalTracker = new GoalTracker(apiData);
			 goalTracker.updateProgress();

			 /*
			 /*------------------------------------------*/
			 //create each panel used for the daily dash board
			 /*------------------------------------------/
			 stepsPanel();
			 stairsPanel();
			 caloriesPanel();
			 distancePanel();
			 activeMinutesPanel();
			 sedentaryMinutesPanel();
			 accoladesPanel();
			 heartRatePanel();
			 goalsPanel();
			  */
			 /*------------------------------------------*/
			 //create a refresh button to refresh the data
			 /*------------------------------------------*/
			 final JLabel imgRefresh = new JLabel();
			 imgRefresh.setIcon(new ImageIcon(picRefresh));
			 imgRefresh.setBounds(613, 63, 36, 36);
			 imgRefresh.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mouseClicked(MouseEvent arg0) {
					 imgRefresh.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					 //what to do on button click
					 lblDataUpdate.setText("refreshing...");
					 //lblDataUpdate.repaint();
					 Boolean validDate = updateDate();
					 if(validDate && !apiData.refreshDailyDashBoardData(date)) {
			    		 JOptionPane.showMessageDialog(contentPane, "An error has occured connecting to fitbit servers, please try again later.");
			    	 }

					 try {
						initUI(date, apiData);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					 contentPane.repaint();
					 try {
						initUI(date, apiData);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 imgRefresh.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				 }
				 @Override
	    		 public void mouseEntered(MouseEvent e) {
					 imgRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 imgRefresh.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			 final JLabel imgEdit = new JLabel();
			 imgEdit.setIcon(new ImageIcon(picEdit));
			 imgEdit.setBounds(966, 5, 48, 48);
			 imgEdit.setToolTipText("Click here to edit your daily dashboard");
			 imgEdit.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mouseClicked(MouseEvent arg0) {
					 //what to do on button click
					 EditMode edit = new EditMode(date, apiData);
					 edit.setVisible(true);
					 dispose();
				 }
				 @Override
	    		 public void mouseEntered(MouseEvent e) {
					 imgEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));

	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 imgEdit.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
	     private Boolean updateDate()
	     {
	    	 //get the new date from the JDatePicker
	    	 String tempDate = datePicker.getJFormattedTextField().getText();
	    	 //datePicker.getJFormattedTextField().get
	    	 
	    	 if(tempDate.equals("") || tempDate.equals(null))
	    	 {
	    		 JOptionPane.showMessageDialog(contentPane, "Invalid Date: You must select a date");
	    		 return false;
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
	    			 JOptionPane.showMessageDialog(contentPane, "Invalid Date: Cannot select a date in the future");
	    			 return false;
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
	    			 return true;
	    		 }
	    	 } catch (ParseException e) {
	    		 // TODO Auto-generated catch block
	    		 JOptionPane.showMessageDialog(contentPane, "Invalid date format.");
	    		 return false;
			}
	    	 
	     }
	   
	     /**
	      * creates the panel to display the steps taken
	      */
	     private void stepsPanel() throws ClassNotFoundException
		 {
			//creates a new steps panel
	    	 pnlSteps = new DashBoardPanel(50, 196);
	    	 //pnlSteps.setLocation(51, 99);
	    	 pnlSteps.setVisible(false);
	    	 pnlSteps.setBackground(transparentColor);
	    	 pnlSteps.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 StepsPanel steps = new StepsPanel(date, apiData);
	    			 steps.setVisible(true);
	    			 dispose();
	    		 }
	    		 @Override
	    		 public void mouseEntered(MouseEvent e) {
	    			 pnlSteps.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    			 pnlSteps.setOpaque(false);
	    			 pnlSteps.setBackground(transparentColor);
	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 pnlSteps.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    			 pnlSteps.setOpaque(false);
	    			 pnlSteps.setBackground(transparentColor);
	    		 }
	    	 });
	    	
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
	    	 stepsProgress.setValue((int)goalTracker.getStepsProgress());
			// System.out.println(goalTracker.getStepsProgress());
			// System.out.println((int)goalTracker.getStepsProgress());

	    	 stepsProgress.setToolTipText("Current progress towards your goal");
	    	 stepsProgress.setForeground(new Color(51, 153, 255));
	    	 stepsProgress.setBounds(17, 118, 231, 36);
	    	 stepsProgress.repaint();
	    	 pnlSteps.add(stepsProgress);
	    	 
	    	 /*------------------------------------------*/
	    	 //create a label to display the steps
	    	 /*------------------------------------------*/
	    	 JLabel lblSteps = new JLabel(Integer.toString((int)apiData.getSteps()));
	    	 lblSteps.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
	    	 lblSteps.setHorizontalAlignment(SwingConstants.CENTER);
	    	 lblSteps.setForeground(white);
	    	 lblSteps.setBounds(0, 26, 265, 33);
	    	 pnlSteps.add(lblSteps);

	    	 /*------------------------------------------*/
	    	 //create a label to display the steps title
	    	 /*------------------------------------------*/
	    	 JLabel lblStepsTtile = new JLabel("Steps");
	    	 lblStepsTtile.setBounds(0, 58, 265, 26);
	    	 lblStepsTtile.setForeground(white);
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

	    	 pnlStairs = new DashBoardPanel(413, 196);
	    	 //pnlStairs.setLocation(385, 99);
	    	 pnlStairs.setLayout(null);
	    	 pnlStairs.setVisible(false);
	    	 pnlStairs.setBackground(transparentColor);
	    	 pnlStairs.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 StairsPanel stairs = new StairsPanel(date, apiData);
	    			 stairs.setVisible(true);
	    			 dispose();
	    		 }
	    		 @Override
	    		 public void mouseEntered(MouseEvent e) {
	    			 pnlStairs.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    			 pnlStairs.setBackground(transparentHoverColor);

	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 pnlStairs.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    			 pnlStairs.setBackground(transparentColor);
	    		 }
	    	 });
	    	 contentPane.add(pnlStairs);

	    	 /*------------------------------------------*/
	    	 //create a label to display the floors climbed title
	    	 /*------------------------------------------*/
			 JLabel lblStairs = new JLabel("Floors Climbed:");
			 lblStairs.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblStairs.setHorizontalAlignment(SwingConstants.CENTER);
			 lblStairs.setForeground(white);
			 lblStairs.setBounds(0, 56, 265, 38);
			 pnlStairs.add(lblStairs);

			 /*------------------------------------------*/
	    	 //create a label to display the floors climbed title
	    	 /*------------------------------------------*/

			 JProgressBar stairsProgress = new JProgressBar();
			 stairsProgress.setValue((int)goalTracker.getFloorsClimbedProgress());
			 stairsProgress.setToolTipText("Current progress towards your goal");
			 stairsProgress.setForeground(SystemColor.textHighlight);
			 stairsProgress.setBounds(17, 118, 231, 36);
			 pnlStairs.add(stairsProgress);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the floors climbed
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Integer.toString((int)apiData.getFloorsClimbed()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setForeground(white);
			 label.setBounds(0, 17, 265, 33);
			 pnlStairs.add(label);
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

			 pnlCalories = new DashBoardPanel(776, 196);
			 //pnlCalories.setLocation(709, 99);
			 pnlCalories.setLayout(null);
			 pnlCalories.setVisible(false);
			 pnlCalories.setBackground(transparentColor);
			 pnlCalories.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 CaloriesPanel calories = new CaloriesPanel(date, apiData);
	    			 calories.setVisible(true);
	    			 dispose();
	    		 }
	    		 @Override
	    		 public void mouseEntered(MouseEvent e) {
	    			 pnlCalories.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    			 pnlCalories.setBackground(transparentHoverColor);

	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 pnlCalories.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    			 pnlCalories.setBackground(transparentColor);

	    		 }
	    	 });
			 contentPane.add(pnlCalories);

			 /*------------------------------------------*/
	    	 //create a label to display the Calories burned title
	    	 /*------------------------------------------*/
			 JLabel lblCalories = new JLabel("Calories Burned");
			 lblCalories.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblCalories.setHorizontalAlignment(SwingConstants.CENTER);
			 lblCalories.setBounds(0,51,265,53);
			 lblCalories.setForeground(white);
			 pnlCalories.add(lblCalories);

			 /*------------------------------------------*/
	    	 //create a label to create a new progress bar
	    	 /*------------------------------------------*/
			 JProgressBar caloriesProgress = new JProgressBar();
			// caloriesProgress.setValue(Integer.parseInt(caloriesGoal.getGoal(GoalsEnum.calorieBurned)));
			 caloriesProgress.setValue((int)goalTracker.getCaloriesProgress());
			 caloriesProgress.setToolTipText("Current progress towards your goal");
			 caloriesProgress.setForeground(SystemColor.textHighlight);
			 caloriesProgress.setBounds(21, 115, 210, 36);
			 pnlCalories.add(caloriesProgress);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Calories burned
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Integer.toString((int)apiData.getCalories()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 15, 265, 33);
			 label.setForeground(white);
			 pnlCalories.add(label);
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

			 pnlDistance = new DashBoardPanel(50, 305);
			 pnlDistance.setLayout(null);
			 pnlDistance.setVisible(false);
			 pnlDistance.setBackground(transparentColor);
			 pnlDistance.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 DistancePanel distance = new DistancePanel(date, apiData);
	    			 distance.setVisible(true);
	    			 dispose();
	    		 }
	    		 @Override
	    		 public void mouseEntered(MouseEvent e) {
	    			 pnlDistance.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    			 pnlDistance.setBackground(transparentHoverColor);

	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 pnlDistance.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    			 pnlDistance.setBackground(transparentColor);

	    		 }
	    	 });
			 contentPane.add(pnlDistance);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Distance title
	    	 /*------------------------------------------*/
			 JLabel lblDistance = new JLabel("Distance"); 
			 lblDistance.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblDistance.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDistance.setBounds(0,61,265,42);
			 lblDistance.setForeground(white);
			 pnlDistance.add(lblDistance);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Distance progress bar
	    	 /*------------------------------------------*/
			 
	    	 
			 JProgressBar distanceProgress = new JProgressBar();
			 distanceProgress.setValue((int)goalTracker.getDistanceProgress());
			 distanceProgress.setToolTipText("Current progress towards your goal");
			 distanceProgress.setForeground(SystemColor.textHighlight);
			 distanceProgress.setBounds(21,115,210,36);
			 distanceProgress.setForeground(white);
			 pnlDistance.add(distanceProgress);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Distance
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Float.toString(apiData.getDistance()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 15, 265, 33);
			 label.setForeground(white);
			 pnlDistance.add(label);
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
			 pnlActiveMin = new DashBoardPanel(50, 305);
			 pnlActiveMin.setLayout(null);
			 pnlActiveMin.setVisible(false);
			 pnlActiveMin.setBackground(transparentColor);
			 //pnlActiveMin.setBounds(385, 300, 265, 155);
			 pnlActiveMin.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 Minutes activeMinutes = new Minutes(date, apiData);
	    			 activeMinutes.setVisible(true);
	    			 dispose();
	    		 }
	    		 @Override
	    		 public void mouseEntered(MouseEvent e) {
	    			 pnlActiveMin.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    			 pnlActiveMin.setBackground(transparentHoverColor);

	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 pnlActiveMin.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    			 pnlActiveMin.setBackground(transparentColor);

	    		 }
	    	 });
			 contentPane.add(pnlActiveMin);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Active Minutes title
	    	 /*------------------------------------------*/
			 JLabel lblActiveMin = new JLabel("Active Minutes");
			 lblActiveMin.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblActiveMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblActiveMin.setBounds(0, 58, 265, 47);
			 lblActiveMin.setForeground(white);
			 pnlActiveMin.add(lblActiveMin);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the Active minutes
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Integer.toString((int)apiData.getLightlyActiveMin() + (int)apiData.getFairlyActiveMin() + (int)apiData.getVeryActiveMin()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 19, 265, 33);
			 label.setForeground(white);
			 pnlActiveMin.add(label);
			 
			 /*------------------------------------------*/
	    	 //create a label to create a new progress bar
	    	 /*------------------------------------------*/			 
			 JProgressBar activeMinutesProgress = new JProgressBar();
			 activeMinutesProgress.setValue((int)goalTracker.getVeryActiveMinutesProgress());
			 activeMinutesProgress.setToolTipText("Current progress towards your goal");
			 activeMinutesProgress.setForeground(SystemColor.textHighlight);
			 activeMinutesProgress.setBounds(21, 115, 210, 36);
			 pnlActiveMin.add(activeMinutesProgress);
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
			 pnlSedentaryMin = new DashBoardPanel(50, 305);
			 pnlSedentaryMin.setLayout(null);
			 pnlSedentaryMin.setVisible(false);
			 pnlSedentaryMin.setBackground(transparentColor);
			 //pnlSedentaryMin.setBounds(709, 300, 265, 155);
			 pnlSedentaryMin.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 Minutes sedentMinutes = new Minutes(date, apiData);
	    			 sedentMinutes.setVisible(true);
	    			 dispose();
	    		 }
	    		 @Override
	    		 public void mouseEntered(MouseEvent e) {
	    			 pnlSedentaryMin.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    			 pnlSedentaryMin.setBackground(transparentHoverColor);

	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 pnlSedentaryMin.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    			 pnlSedentaryMin.setBackground(transparentColor);

	    		 }
	    	 });
			 contentPane.add(pnlSedentaryMin);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the sedentary minutes title
	    	 /*------------------------------------------*/
			 JLabel lblSedentaryMin = new JLabel("Sedentary Minutes");
			 lblSedentaryMin.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblSedentaryMin.setHorizontalAlignment(SwingConstants.CENTER);
			 lblSedentaryMin.setBounds(0, 57, 265, 50);
			 lblSedentaryMin.setForeground(white);
			 pnlSedentaryMin.add(lblSedentaryMin );
			 
			 /*------------------------------------------*/
	    	 //create a label to display the sedentary minutes
	    	 /*------------------------------------------*/
			 JLabel label = new JLabel(Integer.toString((int)apiData.getSendentaryMinutes()));
			 label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 label.setBounds(0, 18, 265, 33);
			 label.setForeground(white);
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

			 pnlAccolades = new DashBoardPanel(50, 305);
			 pnlAccolades.setLayout(null);
			 pnlAccolades.setVisible(false);
			 pnlAccolades.setBackground(transparentColor);
			 //pnlAccolades.setBounds(51, 501, 265, 155);
			 pnlAccolades.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 AchievementPanel panel = new AchievementPanel(date, apiData);
	    			 panel.setVisible(true);
	    			 dispose();
	    		 }
	    		 @Override
	    		 public void mouseEntered(MouseEvent e) {
	    			 pnlAccolades.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    			 pnlAccolades.setBackground(transparentHoverColor);

	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 pnlAccolades.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    			 pnlAccolades.setBackground(transparentColor);
	    		 }
	    	 });
			 contentPane.add(pnlAccolades);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the accolades title
	    	 /*------------------------------------------*/
			 JLabel lblAccolades = new JLabel("Achievements");
			 lblAccolades.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblAccolades.setHorizontalAlignment(SwingConstants.CENTER);
			 lblAccolades.setBounds(0, 61, 265, 49);
			 lblAccolades.setForeground(white);
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

			 pnlHeartRate = new DashBoardPanel(50, 305);
			 pnlHeartRate.setLayout(null);
			 pnlHeartRate.setVisible(false);
			 pnlHeartRate.setBackground(transparentColor);
			 //pnlHeartRate.setBounds(385, 501, 265, 155);
			 pnlHeartRate.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 HeartRateZones panel = new HeartRateZones(date,apiData);
	    			 panel.setVisible(true);
	    			 dispose();
	    			 
	    		 }
	    		 @Override
	    		 public void mouseEntered(MouseEvent e) {
	    			 pnlHeartRate.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    			 pnlHeartRate.setBackground(transparentHoverColor);

	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    			 pnlHeartRate.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    			 pnlHeartRate.setBackground(transparentColor);

	    		 }
	    	 });
			 contentPane.add(pnlHeartRate);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the heart rate title
	    	 /*------------------------------------------*/
			 JLabel lblHeart = new JLabel("Heart Rate Zones");
			 lblHeart.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblHeart.setHorizontalAlignment(SwingConstants.CENTER);
			 lblHeart.setBounds(0, 55, 265, 53);
			 lblHeart.setForeground(white);
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
			 pnlGoals = new DashBoardPanel(50, 305);
			 pnlGoals.setLayout(null);
			 pnlGoals.setVisible(false);
			 pnlGoals.setBackground(transparentColor);
			 //pnlGoals.setBounds(709, 501, 265, 155);
			 pnlGoals.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 GoalPanel goals = new GoalPanel(date,apiData);
	    			 goals.setVisible(true);
	    			 dispose();
	    			 
	    		 }
	    		 @Override
	    		 public void mouseEntered(MouseEvent e) {
	    		        pnlGoals.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    		        pnlGoals.setBackground(transparentHoverColor);

	    		 }
	    		 @Override
	    		 public void mouseExited(MouseEvent e) {
	    		        pnlGoals.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    		        pnlGoals.setBackground(transparentColor);

	    		 }
	    	 });
			 
			 contentPane.add(pnlGoals);
			 
			 /*------------------------------------------*/
	    	 //create a label to display the daily goals title
	    	 /*------------------------------------------*/
			 JLabel lblDaily = new JLabel("Daily Goals");
			 lblDaily.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			 lblDaily.setHorizontalAlignment(SwingConstants.CENTER);
			 lblDaily.setBounds(0, 67, 265, 41);
			 lblDaily.setForeground(white);
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
				//JOptionPane.showMessageDialog(contentPane, "The program cannot find dashboardPanel.txt, please create a new blank text file ");
				String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "dashboardPanel.txt";
				// Use relative path for Unix systems
				File f = new File(path);
				try {
					f.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
	    	
	     }
}

