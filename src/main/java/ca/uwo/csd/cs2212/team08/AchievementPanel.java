package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class AchievementPanel extends JFrame {

	private JPanel contentPane;
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(0,0,0,60);
	private Color borderColor = new Color(121, 189, 154);
	private Color titleColor = new Color(11, 72, 107);
	private Color white = Color.white;
	private static final String backImage = "src/main/resources/images/arrowLeft4.png";
	private APIData apiData;
	private String date;
	private JTextField textField;
	private JDatePickerImpl datePicker;
	private static final String picRefresh = "src/main/resources/images/refresh.png";
	private static final String imgTest = "src/main/resouces/images/fitbit.png";
	private AchievementTracker tracker;
	/*
	 * Many static final string variables for all accolades pictures that will be used as "badges"
	 * This section of the code will be badges users will be able to see if they have acheived goals
	 */
	//in one day 
	private static final String thirtyFloors = "src/main/resources/New accolades/30 floors in 1 day.png";
	private static final String fiftyFloors = "src/main/resources/New accolades/50 floors in 1 day.png";
	private static final String hundredFloors = "src/main/resources/New accolades/100 floors in 1 day.png";
	//in lifetime
	private static final String fiveHundredFloors = "src/main/resources/New accolades/500 floors.png";
	private static final String twoKFloors = "src/main/resources/New accolades/2k floors.png";
	private static final String oneKFloors = "src/main/resources/New accolades/1k floors.png";
	//in one day
	private static final String tenKSteps = "src/main/resources/New accolades/10k steps in 1 day.png";
	private static final String fifteenKSteps = "src/main/resources/New accolades/15k steps in 1 day.png";
	private static final String twentyKSteps = "src/main/resources/New accolades/20k steps in 1 day.png";
	//in lifetime
	private static final String fiftyKSteps = "src/main/resources/New accolades/50k steps.png";
	private static final String hundredKSteps = "src/main/resources/New accolades/100k steps.png";
	private static final String twoHundredFiftyKSteps = "src/main/resources/New accolades/250k steps.png";
	
	//in one day 
	private static final String tenKWalked = "src/main/resources/New accolades/10km walked.png";
	private static final String fifteenKWalked ="src/main/resources/New accolades/15km walked.png";
	private static final String twentyKWalked = "src/main/resources/New accolades/20km walked.png";
	
	//in lifetime
	private static final String twoHundredKWalked = "src/main/resources/New accolades/200km walked.png";
	private static final String threeHundredKWalked = "src/main/resources/New accolades/300km walked.png";
	private static final String fiveHundredKWalked = "src/main/resources/New accolades/500km walked.png";

	//in one day
	private static final String twoKCalories = "src/main/resources/New accolades/2k cals in 1 day.png";
	private static final String threeKCalories = "src/main/resources/New accolades/3k cals in 1 day.png";
	private static final String fiveKCalories = "src/main/resources/New accolades/5k cals in 1 day.png";
	/*
	 * default pictures shown. All corresponding pictures will go in order, so milesTen and milesTenNot
	 * will always correspond to each other (in terms of order) in this section of code. Sorry if that's confusing. 
	 */
	
	//in one day 
		private static final String thirtyFloorsNot = "src/main/resources/New accolades/30 floors NOT.png";
		private static final String fiftyFloorsNot = "src/main/resources/New accolades/50 floors NOT.png";
		private static final String hundredFloorsNot = "src/main/resources/New accolades/100 floors NOT.png";
		//in lifetime
		private static final String fiveHundredFloorsNot = "src/main/resources/New accolades/500 floors NOT.png";
		private static final String twoKFloorsNot = "src/main/resources/New accolades/2k floors NOT.png";
		private static final String oneKFloorsNot = "src/main/resources/New accolades/1k floors NOT.png";
		//in one day
		private static final String tenKStepsNot = "src/main/resources/New accolades/10k steps NOT.png";
		private static final String fifteenKStepsNot = "src/main/resources/New accolades/15k steps NOT.png";
		private static final String twentyKStepsNot = "src/main/resources/New accolades/20k steps NOT.png";
		//in lifetime
		private static final String fiftyKStepsNot = "src/main/resources/New accolades/50k steps NOT.png";
		private static final String hundredKStepsNot = "src/main/resources/New accolades/100k steps NOT.png";
		private static final String twoHundredFiftyKStepsNot = "src/main/resources/New accolades/250k steps NOT.png";
		
		//in one day 
		private static final String tenKWalkedNot = "src/main/resources/New accolades/10km walked NOT.png";
		private static final String fifteenKWalkedNot ="src/main/resources/New accolades/15km walked NOT.png";
		private static final String twentyKWalkedNot = "src/main/resources/New accolades/20km walked NOT.png";
		
		//in lifetime
		private static final String twoHundredKWalkedNot = "src/main/resources/New accolades/200km walked NOT.png";
		private static final String threeHundredKWalkedNot = "src/main/resources/New accolades/300km walked NOT.png";
		private static final String fiveHundredKWalkedNot = "src/main/resources/New accolades/500km walked NOT.png";

		//in one day
		private static final String twoKCaloriesNot = "src/main/resources/New accolades/2k cals in 1 day NOT.png";
		private static final String threeKCaloriesNot = "src/main/resources/New accolades/3k cals in 1 day NOT.png";
		private static final String fiveKCaloriesNot = "src/main/resources/New accolades/5k cals in 1 day NOT.png";

	public AchievementPanel(final String date, APIData paramAPIData) {

		/*----------------------------------------------------*
		 * create achievementTracker object
		 * will use this down in the code to check if user
		 * has reached their goal
		 * if yes, the program will give them a different picture
		 * than the one that's shown to them by default
		 *---------------------------------------------------*/

		try {
			this.tracker = new AchievementTracker(paramAPIData);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		this.tracker.updateProgress();
		//System.out.println(track.getAchieved());
		
		
		/*----------------------------------------------------*
		 * Create MainWindow
		 *----------------------------------------------------*/
		setResizable(false);
		this.date = date;
		this.apiData = paramAPIData;
		setTitle("team08-Fitbit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 768);
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
		JLabel titleGoals = new JLabel("    ACHIEVEMENTS  ");
		titleGoals.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		titleGoals.setHorizontalAlignment(SwingConstants.CENTER);
		titleGoals.setBounds(78, 6, 837, 59);
		contentPane.add(titleGoals);
		titleGoals.setForeground(white);

		/*-------------------------------------*/
		// the label to set the last updated time
		/*------------------------------------*/
		Calendar cal = Calendar.getInstance();
		final JLabel lblDataUpdate = new JLabel("Last updated: "
				+ cal.getTime().toString());
		lblDataUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataUpdate.setForeground(white);
		lblDataUpdate.setBounds(51, 680, 923, 37);
		contentPane.add(lblDataUpdate);

		/*-----------------------------------------*/
		// create a back button to return to dash board
		/*----------------------------------------*/
		JLabel backArrow = new JLabel();
		backArrow.setIcon(new ImageIcon(backImage));
		backArrow.setBounds(0, 0, 48, 48);
		// backArrow.setVisible(true);
		contentPane.add(backArrow);
		backArrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// what to do on button click
				home();
				dispose();
			}
		});
		
		/*---------------------------------------------------*
		 * labels for all the floors in one day achievements
		 *---------------------------------------------------*/
		//30 floors in one day
		JLabel floors30 = new JLabel();
		floors30.setLocation(96, 77);
		floors30.setSize(112,112);
		if(this.tracker.isAchieved(6)){
			floors30.setIcon(new ImageIcon(thirtyFloors));
		}
		floors30.setIcon(new ImageIcon(thirtyFloorsNot));
		contentPane.add(floors30);
		
		//50 floors in one day 
		JLabel floors50 = new JLabel();
		floors50.setLocation(96,219);
		floors50.setSize(112,112);
		if(this.tracker.isAchieved(7)){
			floors50.setIcon(new ImageIcon(fiftyFloors));
		}
			floors50.setIcon(new ImageIcon(fiftyFloorsNot));
		contentPane.add(floors50);
		
		JLabel floors100 = new JLabel();
		floors100.setLocation(96,361);
		floors100.setSize(112,112);
		if(this.tracker.isAchieved(8)){
			floors100.setIcon(new ImageIcon(hundredFloors));
		}
			floors100.setIcon(new ImageIcon(hundredFloorsNot));
		contentPane.add(floors100);
		/*-----------------------------------------*
		 * End of floors in a day achievements
		 *-----------------------------------------*/
		/*-----------------------------------------*
		 * start of floors in lifetime achievements
		 *-----------------------------------------*/
		//500 floors
		JLabel floors500 = new JLabel();
		floors500.setLocation(237,77);
		floors500.setSize(112,112);
		if(this.tracker.isAchieved(9)){
			floors500.setIcon(new ImageIcon(fiveHundredFloors));
		}
		else{
			floors500.setIcon(new ImageIcon(fiveHundredFloorsNot));
		}
		contentPane.add(floors500);
		
		//1000 floors
		JLabel floors1000 = new JLabel();
		floors1000.setLocation(237, 219);
		floors1000.setSize(112,112);
		if(this.tracker.isAchieved(10)){
			floors1000.setIcon(new ImageIcon(oneKFloors));
		}
		else{
			floors1000.setIcon(new ImageIcon(oneKFloorsNot));
		}
		contentPane.add(floors1000);
	
		//2000 floors climbed
		JLabel floors2000 = new JLabel();
		floors2000.setLocation(237, 361);
		floors2000.setSize(112,112);
		if(this.tracker.isAchieved(11)){
			floors2000.setIcon(new ImageIcon(twoKFloors));
		}
		else{
			floors2000.setIcon(new ImageIcon(twoKFloorsNot));
		}
		contentPane.add(floors2000);
		/*------------------------------------*
		 * End of lifetime Floor achievements
		 *------------------------------------*/
		/*---------------------------------------------*
		 * Start of steps taken in one day achievements
		 *---------------------------------------------*/
		
		JLabel steps10K = new JLabel();
		steps10K.setLocation(379, 77);
		steps10K.setSize(112,112);
		if(this.tracker.isAchieved(0)){
			steps10K.setIcon(new ImageIcon(tenKSteps));
		}
		else{
			steps10K.setIcon(new ImageIcon(tenKStepsNot));
		}
		contentPane.add(steps10K);
		
		JLabel steps15K = new JLabel();
		steps15K.setLocation(379,219);
		steps15K.setSize(112,112);
		if(this.tracker.isAchieved(1)){
			steps15K.setIcon(new ImageIcon(fifteenKSteps));
		}
		else{
			steps15K.setIcon(new ImageIcon(fifteenKStepsNot));
		}
		contentPane.add(steps15K);
		
		JLabel steps20K = new JLabel();
		steps20K.setLocation(379,361);
		steps20K.setSize(112,112);
		if(this.tracker.isAchieved(2)){
			steps20K.setIcon(new ImageIcon(twentyKSteps));
		}
		else{
			steps20K.setIcon(new ImageIcon(twentyKStepsNot));
		}
		contentPane.add(steps20K);
		/*-----------------------------------*
		 * End of steps in a day achievements
		 *-----------------------------------*/
		/*-------------------------------------------*
		 * start of steps lifetime total achievements
		 *-------------------------------------------*/
		JLabel steps50K = new JLabel();
		steps50K.setLocation(521,77);
		steps50K.setSize(112,112);
		if(this.tracker.isAchieved(3)){
			steps50K.setIcon(new ImageIcon(fiftyKSteps));
		}
		else{
			steps50K.setIcon(new ImageIcon(fiftyKStepsNot));
		}
		contentPane.add(steps50K);
		
		JLabel steps100K = new JLabel();
		steps100K.setLocation(521,219);
		steps100K.setSize(112,112);
		if(this.tracker.isAchieved(4)){
			steps100K.setIcon(new ImageIcon(hundredKSteps));
		}
		else{
			steps100K.setIcon(new ImageIcon(hundredKStepsNot));
		}
		contentPane.add(steps100K);
		
		JLabel steps250K = new JLabel();
		steps250K.setLocation(521,361);
		steps250K.setSize(112,112);
		if(this.tracker.isAchieved(5)){
			steps250K.setIcon(new ImageIcon(twoHundredFiftyKSteps));
		}
		else{
			steps250K.setIcon(new ImageIcon(twoHundredFiftyKStepsNot));
		}
		contentPane.add(steps250K);
		/*-------------------------------------------*
		 * End of steps lifetime total achievements
		 *-------------------------------------------*/
		/*-------------------------------------------*
		 * start of distance walked in a day achievements
		 *-------------------------------------------*/

		JLabel walked10K = new JLabel();
		walked10K.setLocation(663, 77);
		walked10K.setSize(112,112);
		if(this.tracker.isAchieved(12)){
			walked10K.setIcon(new ImageIcon(tenKWalked));
		}
		else{
			walked10K.setIcon(new ImageIcon(tenKWalkedNot));
		}
		contentPane.add(walked10K);
		
		JLabel walked15K = new JLabel();
		walked15K.setLocation(663,219);
		walked15K.setSize(112,112);
		if(this.tracker.isAchieved(13)){
			walked15K.setIcon(new ImageIcon(fifteenKWalked));
		}
		else{
			walked15K.setIcon(new ImageIcon(fifteenKWalkedNot));
		}
		contentPane.add(walked15K);
		
		JLabel walked20K = new JLabel();
		walked20K.setLocation(663,361);
		walked20K.setSize(112,112);
		if(this.tracker.isAchieved(14)){
			walked20K.setIcon(new ImageIcon(twentyKWalked));
		}
		else{
			walked20K.setIcon(new ImageIcon(twentyKWalkedNot));
		}
		contentPane.add(walked20K);
		/*-------------------------------------------*
		 * End of distance walked in a day achievements
		 *-------------------------------------------*/
		/*-------------------------------------------*
		 * Start of distance walked in total achievements
		 *-------------------------------------------*/
		JLabel walked200K = new JLabel();
		walked200K.setLocation(805, 77);
		walked200K.setSize(112,112);
		if(this.tracker.isAchieved(15)){
			walked200K.setIcon(new ImageIcon(twoHundredKWalked));
		}
		else{
			walked200K.setIcon(new ImageIcon(twoHundredKWalkedNot));
		}
		contentPane.add(walked200K);
		
		JLabel walked300K = new JLabel();
		walked300K.setLocation(805, 219);
		walked300K.setSize(112,112);
		if(this.tracker.isAchieved(16)){
			walked300K.setIcon(new ImageIcon(threeHundredKWalked));
		}
		else{
			walked300K.setIcon(new ImageIcon(threeHundredKWalkedNot));
		}
		contentPane.add(walked300K);
		
		JLabel walked500K = new JLabel();
		walked500K.setLocation(805,361);
		walked500K.setSize(112,112);
		if(this.tracker.isAchieved(17)){
			walked500K.setIcon(new ImageIcon(fiveHundredKWalked));
		}
		else{
			walked500K.setIcon(new ImageIcon(fiveHundredKWalkedNot));
		}
		contentPane.add(walked500K);
		/*-------------------------------------------*
		 * End of distance walked in total achievements
		 *-------------------------------------------*/
		/*-----------------------------------------*
		 * Start of Calories Achievements
		 *-----------------------------------------*/
		JLabel burned2K = new JLabel();
		burned2K.setLocation(947,77);
		burned2K.setSize(112,112);
		if(this.tracker.isAchieved(18)){
			burned2K.setIcon(new ImageIcon(twoKCalories));
		}
		else{
			burned2K.setIcon(new ImageIcon(twoKCaloriesNot));
		}
		contentPane.add(burned2K);
		
		JLabel burned3K = new JLabel();
		burned3K.setLocation(947, 219);
		burned3K.setSize(112,112);
		if(this.tracker.isAchieved(19)){
			burned3K.setIcon(new ImageIcon(threeKCalories));
		}
		else{
			burned3K.setIcon(new ImageIcon(threeKCaloriesNot));
		}
		contentPane.add(burned3K);
		
		JLabel burned5K = new JLabel();
		burned5K.setLocation(947, 361);
		burned5K.setSize(112,112);
		if(this.tracker.isAchieved(20)){
			burned5K.setIcon(new ImageIcon(fiveKCalories));
		}
		else{
			burned5K.setIcon(new ImageIcon(fiveKCaloriesNot));
		}
		contentPane.add(burned5K);
		/*-------------------------------------------*
		 * End of calories achievements
		 *-------------------------------------------*/
	
		JPanel informationPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		//informationPanel.setOpaque(false);
		informationPanel.setBackground(pannelColor);
		informationPanel.setSize(966,160);
		informationPanel.setLocation(34,523);
		contentPane.add(informationPanel);
		contentPane.repaint();
		
		JLabel inform1 = new JLabel("Hey there! Welcome to your Achievements Page!");
		inform1.setOpaque(false);
		inform1.setForeground(white);
		inform1.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		inform1.setHorizontalAlignment(SwingConstants.CENTER);
		informationPanel.add(inform1);
		contentPane.repaint();
		
		JLabel inform2 = new JLabel("In here, you can see all of the achievements that you can achieve!");
		inform2.setOpaque(false);
		inform2.setForeground(white);
		inform2.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		inform2.setHorizontalAlignment(SwingConstants.CENTER);
		informationPanel.add(inform2);
		contentPane.repaint();
		
		JLabel inform3 = new JLabel("Once you achieve a goal, you will see these beauty badges come alive with Color!");
		inform3.setOpaque(false);
		inform3.setForeground(white);
		inform3.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		inform3.setHorizontalAlignment(SwingConstants.CENTER);
		informationPanel.add(inform3);
		contentPane.repaint();
		
		JLabel inform4 = new JLabel("What are you waiting for?");
		inform4.setOpaque(false);
		inform4.setForeground(white);
		inform4.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		inform4.setHorizontalAlignment(SwingConstants.CENTER);
		informationPanel.add(inform4);
		
		JLabel inform5 = new JLabel("Get moving to get those achievements! ");
		informationPanel.add(inform5);
		inform5.setOpaque(false);
		inform5.setForeground(white);
		inform5.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		inform5.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.repaint();
		contentPane.repaint();
		/*
		JTextArea information = new JTextArea(){
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		
		information.setOpaque(false);
		information.setBackground(pannelColor);
		information.setSize(966,191);
		information.setLocation(34,499);
		information.setForeground(new Color(255,255,255));
		information.setText("                         Hey there! Welcome to the Achievements page!\n                Here you can see all the possible achievements you can get!\n  Once you achieve a goal, you will see these beautful badges filled in with color!  \n\t\t       Well?\n\t              what are you waiting for? \n                          Get Moving to get those Achievements!");
		information.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		contentPane.add(information);
		contentPane.repaint();
		*/
	}

	public void home() {
		MainScreen main = new MainScreen(this.date, apiData);
		main.setVisible(true);
	}
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
}