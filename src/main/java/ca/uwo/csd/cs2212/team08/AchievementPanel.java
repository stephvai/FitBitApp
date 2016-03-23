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
	private Color pannelColor = new Color(206, 206, 206);
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
	
	private static final String Testpic = "src/main/resources/images/pc2.jpg";
	
	/*
	 * default pictures shown. All corresponding pictures will go in order, so milesTen and milesTenNot
	 * will always correspond to each other (in terms of order) in this section of code. Sorry if that's confusing. 
	 */
	

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
		
		
		/*----------------------------------------------------*
		 * Title for the accolades badges
		 *----------------------------------------------------*/
		JLabel milesTitle = new JLabel("YOUR DISTANCE ACHIEVEMENTS");
		milesTitle.setSize(239, 50);
		milesTitle.setLocation(30, 81);
		milesTitle.setForeground(white);
		milesTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		milesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(milesTitle);
		/*------------------------------------------------*
		 * Labels for all the distance achievements. 
		 *------------------------------------------------*/
		//10 miles
		/*JLabel tenMiles = new JLabel();
		tenMiles.setSize(80, 240);
		tenMiles.setLocation(40, 125);
		//if achieved method returns true then
		// tenMiles.setIcon(new ImageIcon(milesTen));
		if(this.tracker.isAchieved(0)){
			tenMiles.setIcon(new ImageIcon(Testpic));
		}
		tenMiles.setIcon(new ImageIcon(Testpic));
		contentPane.add(tenMiles);
		
		//20 miles
		JLabel twentyMiles = new JLabel();
		twentyMiles.setSize(80, 240);
		twentyMiles.setLocation(130,125);
		if(this.tracker.isAchieved(1)){
			tenMiles.setIcon(new ImageIcon(milesTwenty));
		}
		twentyMiles.setIcon(new ImageIcon(milesTwentyNot));
		contentPane.add(twentyMiles);
		
		//30 miles
		JLabel thirtyMiles = new JLabel();
		thirtyMiles.setSize(80, 240);
		thirtyMiles.setLocation(221,125);
		if(this.tracker.isAchieved(2)){
			tenMiles.setIcon(new ImageIcon(milesThirty));
		}
		thirtyMiles.setIcon(new ImageIcon(milesThirtyNot));
		contentPane.add(thirtyMiles);
		
		//40 miles
		JLabel fortyMiles = new JLabel();
		fortyMiles.setSize(80, 240);
		fortyMiles.setLocation(311,125);
		if(this.tracker.isAchieved(3)){
			tenMiles.setIcon(new ImageIcon(milesFourty));
		}
		fortyMiles.setIcon(new ImageIcon(milesFourtyNot));
		contentPane.add(fortyMiles);
		
		//50 miles 
		JLabel fiftyMiles = new JLabel();
		fiftyMiles.setSize(80, 240);
		fiftyMiles.setLocation(402,125);
		if(this.tracker.isAchieved(4)){
			tenMiles.setIcon(new ImageIcon(milesFifty));
		}
		fiftyMiles.setIcon(new ImageIcon(milesFiftyNot));
		contentPane.add(fiftyMiles);
		/*---------------------------------------*
		 * End of distance achievements
		 *---------------------------------------/
		
		/----------------------------------------------------*
		 * Title for the steps badges
		 *----------------------------------------------------/
		JLabel stepsTitle = new JLabel("YOUR STEPS ACHIEVEMENTS");
		stepsTitle.setSize(219, 50);
		stepsTitle.setLocation(26, 375);
		stepsTitle.setForeground(white);
		stepsTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		stepsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(stepsTitle);
		/---------------------------------------*
		 * Start of steps achievements
		 *---------------------------------------/
		
		//10000 steps
		JLabel steps10000 = new JLabel();
		steps10000.setSize(80,240);
		steps10000.setLocation(39, 420);
		if(this.tracker.isAchieved(5)){
			tenMiles.setIcon(new ImageIcon(stepsTenThousand));
		}
		steps10000.setIcon(new ImageIcon(stepsTenThousandNot));
		contentPane.add(steps10000);
		
		//12500 steps
		JLabel steps12500 = new JLabel();
		steps12500.setSize(80,240);
		steps12500.setLocation(130,420);
		if(this.tracker.isAchieved(6)){
			tenMiles.setIcon(new ImageIcon(stepsTwelveThousandFiveHundred));
		}
		steps12500.setIcon(new ImageIcon(stepsTwelveThousandFiveHundredNot));
		contentPane.add(steps12500);
		
		//15000 steps
		JLabel steps15000 = new JLabel();
		steps15000.setSize(80,240);
		steps15000.setLocation(221, 420);
		if(this.tracker.isAchieved(7)){
			tenMiles.setIcon(new ImageIcon(stepsFifteenThousand));
		}
		steps15000.setIcon(new ImageIcon(stepsFifteenThousandNot));
		contentPane.add(steps15000);
		
		//17500 steps
		JLabel steps17500 = new JLabel();
		steps17500.setSize(80,240);
		steps17500.setLocation(311,420);
		if(this.tracker.isAchieved(8)){
			tenMiles.setIcon(new ImageIcon(stepsSevenTeenFiveHundredThousand));
		}
		steps17500.setIcon(new ImageIcon(stepsSevenTeenFiveHundredThousandNot));
		contentPane.add(steps17500);
		
		//20000 steps
		JLabel steps20000 = new JLabel();
		steps20000.setSize(80,240);
		steps20000.setLocation(402,420);
		if(this.tracker.isAchieved(9)){
			tenMiles.setIcon(new ImageIcon(stepsTwentyThousand));
		}
		steps20000.setIcon(new ImageIcon(stepsTwentyThousandNot));
		contentPane.add(steps20000);
		/---------------------------------------*
		 * End of steps achievements
		 *---------------------------------------/
		
		/----------------------------------------------------*
		 * Title for the floors "badges"
		 *----------------------------------------------------/
		JLabel floorsTitle = new JLabel("YOUR FLOORS ACHIEVEMENTS");
		floorsTitle.setSize(239, 50);
		floorsTitle.setLocation(525, 81);
		floorsTitle.setForeground(white);
		floorsTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		floorsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(floorsTitle);
		/*---------------------------------------*
		 * Start of floors achievements
		 *---------------------------------------/
		JLabel floorsOneHundred = new JLabel(); //100
		floorsOneHundred.setSize(80,240);
		floorsOneHundred.setLocation(540, 125);
		if(this.tracker.isAchieved(10)){
			tenMiles.setIcon(new ImageIcon(floorsHundred));
		}
		floorsOneHundred.setIcon(new ImageIcon(floorsHundredNot));
		contentPane.add(floorsOneHundred);
		
		JLabel floorsTwoHundred = new JLabel(); //200
		floorsTwoHundred.setSize(80,240);
		floorsTwoHundred.setLocation(631, 125);
		if(this.tracker.isAchieved(11)){
			tenMiles.setIcon(new ImageIcon(floorsTwoHundredImage));
		}
		floorsTwoHundred.setIcon(new ImageIcon(floorsTwoHundredNot));
		contentPane.add(floorsTwoHundred);
		
		JLabel floorsThreeHundred = new JLabel(); //300
		floorsThreeHundred.setSize(80,240);
		floorsThreeHundred.setLocation(722, 125);
		if(this.tracker.isAchieved(12)){
			tenMiles.setIcon(new ImageIcon(floorsThreeHundredImage));
		}
		floorsThreeHundred.setIcon(new ImageIcon(floorsThreeHundredNot));
		contentPane.add(floorsThreeHundred);
		
		JLabel floorsFourHundred = new JLabel(); //400
		floorsFourHundred.setSize(80,240);
		floorsFourHundred.setLocation(813, 125);
		if(this.tracker.isAchieved(13)){
			tenMiles.setIcon(new ImageIcon(floorsFourHundredImage));
		}
		floorsFourHundred.setIcon(new ImageIcon(floorFoursHundredNot));
		contentPane.add(floorsFourHundred);
		
		JLabel floorsFiveHundred = new JLabel(); //500
		floorsFiveHundred.setSize(80,240);
		floorsFiveHundred.setLocation(904, 125);
		if(this.tracker.isAchieved(14)){
			tenMiles.setIcon(new ImageIcon(floorsFiveHundredImage));
		}
		floorsFiveHundred.setIcon(new ImageIcon(floorsFiveHundredNot));
		contentPane.add(floorsFiveHundred);
		/---------------------------------------*
		 * End of steps achievements
		 *---------------------------------------/
		*/
		/*----------------------------------------------------*
		 * Title for the calories "badges"
		 *----------------------------------------------------/
		JLabel caloriesTitle = new JLabel("YOUR FLOORS ACHIEVEMENTS");
		caloriesTitle.setSize(239, 50);
		caloriesTitle.setLocation(525, 375);
		caloriesTitle.setForeground(white);
		caloriesTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		caloriesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(caloriesTitle);
		/*---------------------------------------*
		 * Start of Calories achievements
		 *---------------------------------------/
		JLabel cal2Thousand = new JLabel(); //2000
		cal2Thousand.setSize(80,240);
		cal2Thousand.setLocation(540,420);
		if(this.tracker.isAchieved(15)){
			tenMiles.setIcon(new ImageIcon(calTwoThousandImage));
		}
		cal2Thousand.setIcon(new ImageIcon(calTwoThousandNot));
		contentPane.add(cal2Thousand);
		
		JLabel cal2250 = new JLabel(); //2250
		cal2250.setSize(80,240);
		cal2250.setLocation(631,420);
		if(this.tracker.isAchieved(16)){
			tenMiles.setIcon(new ImageIcon(calTwoThousandTwoHundredFiftyImage));
		}
		cal2250.setIcon(new ImageIcon(calTwoThousandTwoHundredFiftyNot));
		contentPane.add(cal2250);
		
		JLabel cal2500 = new JLabel(); //2500
		cal2500.setSize(80, 240);
		cal2500.setLocation(722,420);
		if(this.tracker.isAchieved(17)){
			tenMiles.setIcon(new ImageIcon(calTwoThousandFiveHundredImage));
		}
		cal2500.setIcon(new ImageIcon(calTwoThousandFiveHundredNot));
		contentPane.add(cal2500);
		
		JLabel cal2750 = new JLabel(); //2750
		cal2750.setSize(80, 240);
		cal2750.setLocation(813,420);
		if(this.tracker.isAchieved(18)){
			tenMiles.setIcon(new ImageIcon(calTwoThousandSevenHundredFiftyImage));
		}
		cal2750.setIcon(new ImageIcon(calTwoThousandSevenHundredFiftyNot));
		contentPane.add(cal2750);
		
		JLabel cal3000 = new JLabel(); //3000
		cal3000.setSize(80, 240);
		cal3000.setLocation(904,420);
		if(this.tracker.isAchieved(19)){
			tenMiles.setIcon(new ImageIcon(calThreeThousandImage));
		}
		cal3000.setIcon(new ImageIcon(calThreeThousandNot));
		contentPane.add(cal3000);
		
		/*---------------------------------------*
		 * End of Calories achievements
		 *---------------------------------------*/
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