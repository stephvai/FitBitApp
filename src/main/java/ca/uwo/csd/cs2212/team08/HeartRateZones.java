package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class HeartRateZones extends JFrame {

	// private JPanel contentPane;

	// a content pane to store all the components
	private JPanel contentPane;
	// a string to store the current date the user wants
	private String date;
	//a date picker the user can use to change dates
	private JDatePickerImpl datePicker;
	// constants to store the picture locations
	private static final String backImage = "src/main/resources/images/arrowLeft4.png";
	// use to get the API data
	private APIData apiData;

	// Color Scheme
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(168, 219, 168);
	private Color borderColor = new Color(121, 189, 154);
	private Color titleColor = new Color(11, 72, 107);
	private Color white = Color.white;
	private static final String picRefresh = "src/main/resources/images/refresh.png";
	
	/**
	 * Create the frame.
	 */
	public HeartRateZones(final String date, APIData paramAPIData) {

		/*-----------------------23------------------*/
		// create the main window for the heart rate zones panel
		/*-----------------------------------------*/
		setResizable(false);
		this.date = date;
		this.apiData = paramAPIData;
		setTitle("team08-Fitbit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 768);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(bgColor);
		
		/*-----------------------------------------*/
		// create a title for the current pane
		/*-----------------------------------------*/
		JLabel lblTitle = new JLabel("HEART RATE ZONES");
		lblTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(80, 21, 837, 84);
		contentPane.add(lblTitle);
		lblTitle.setForeground(white);
		
		/*-----------------------------------------*/
		//create a back button to return to dash board
		/*-----------------------------------------*/
		JLabel imgBack = new JLabel();
		imgBack.setIcon(new ImageIcon(backImage));
		imgBack.setBounds(0, 0, 48, 48);
		imgBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//what to do on button click
				home();
				dispose();
			}
		});
		contentPane.add(imgBack);

		
		/*-------------------------------------*/
		//the label to set the last updated time
		/*-------------------------------------*/
		 Calendar cal = Calendar.getInstance();
		 final JLabel lblDataUpdate = new JLabel("Last updated: " + cal.getTime().toString());
		 lblDataUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		 lblDataUpdate.setForeground(white);
		 lblDataUpdate.setBounds(51, 660, 923, 37);
		 contentPane.add(lblDataUpdate);
		 
		 LGraph lineGraph = new LGraph("Heart Rate", "Heart Rate",apiData.getHrTimeSeries());

		 lineGraph.setBounds(80, 409, 637, 239);

		 contentPane.add(lineGraph);
		 
		 /*--------------------------------------------*/
			// create a panel to display floors climbed information for today
			/*--------------------------------------------*/
			JPanel pnlRestingHR = new JPanel();
			pnlRestingHR.setBounds(740, 409, 170, 239);
			contentPane.add(pnlRestingHR);
			pnlRestingHR.setLayout(null);
			pnlRestingHR.setBackground(pannelColor);
			pnlRestingHR.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			
			/*--------------------------------------------*/
			// add a label to display the floors climbed today
			/*--------------------------------------------*/
			JLabel lblRestingHR = new JLabel("<html> Resting heart rate: <strong>"+ apiData.getRestingHeartRate() +"</strong> </html>");
			lblRestingHR.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			lblRestingHR.setHorizontalAlignment(SwingConstants.CENTER);
			lblRestingHR.setBounds(21, 21, 110, 200);
			pnlRestingHR.add(lblRestingHR);
		 
		 	/*--------------------------------------------*/
			// create a panel to display floors climbed information for today
			/*--------------------------------------------*/
			JPanel pnlOutOfRange = new JPanel();
			pnlOutOfRange.setBounds(80, 138, 170, 224);
			contentPane.add(pnlOutOfRange);
			pnlOutOfRange.setLayout(null);
			pnlOutOfRange.setBackground(pannelColor);
			pnlOutOfRange.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			
			/*--------------------------------------------*/
			// add a label to display the floors climbed today
			/*--------------------------------------------*/
			JLabel lblOutOfRange = new JLabel("<html> Today you were in the out of range zone for <strong>"+ apiData.getOutOfRange().getValue() +"</strong> minutes. </html>");
			lblOutOfRange.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			lblOutOfRange.setHorizontalAlignment(SwingConstants.CENTER);
			lblOutOfRange.setBounds(21, 21, 130, 182);
			pnlOutOfRange.add(lblOutOfRange);

			/*--------------------------------------------*/
			// create a panel to display floors climbed information for today
			/*--------------------------------------------*/
			JPanel pnlFatBurn = new JPanel();
			pnlFatBurn.setBounds(300, 138, 170, 224);
			contentPane.add(pnlFatBurn);
			pnlFatBurn.setLayout(null);
			pnlFatBurn.setBackground(pannelColor);
			pnlFatBurn.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			
			/*--------------------------------------------*/
			// add a label to display the floors climbed today
			/*--------------------------------------------*/
			JLabel lblFatBurn = new JLabel("<html> Today you were in the fat burn zone for <strong>"+ apiData.getFatBurn().getValue() +"</strong> minutes. </html>");
			lblFatBurn.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			lblFatBurn.setHorizontalAlignment(SwingConstants.CENTER);
			lblFatBurn.setBounds(21, 21, 130, 182);
			pnlFatBurn.add(lblFatBurn);
			
			/*--------------------------------------------*/
			// create a panel to display floors climbed information for today
			/*--------------------------------------------*/
			JPanel pnlCardio = new JPanel();
			pnlCardio.setBounds(520, 138, 170, 224);
			contentPane.add(pnlCardio);
			pnlCardio.setLayout(null);
			pnlCardio.setBackground(pannelColor);
			pnlCardio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			
			/*--------------------------------------------*/
			// add a label to display the floors climbed today
			/*--------------------------------------------*/
			JLabel lblCardio = new JLabel("<html> Today you were in the cardio zone for <strong>"+ apiData.getCardio().getValue() +"</strong> minutes. </html>");
			lblCardio.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			lblCardio.setHorizontalAlignment(SwingConstants.CENTER);
			lblCardio.setBounds(21, 21, 130, 182);
			pnlCardio.add(lblCardio);
			
			/*--------------------------------------------*/
			// create a panel to display floors climbed information for today
			/*--------------------------------------------*/
			JPanel pnlPeak = new JPanel();
			pnlPeak.setBounds(740, 138, 170, 224);
			contentPane.add(pnlPeak);
			pnlPeak.setLayout(null);
			pnlPeak.setBackground(pannelColor);
			pnlPeak.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			
			/*--------------------------------------------*/
			// add a label to display the floors climbed today
			/*--------------------------------------------*/
			JLabel lblPeak = new JLabel("<html> Today you were at your peak zone for <strong>"+ apiData.getPeak().getValue() +"</strong> minutes. </html>");
			lblPeak.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			lblPeak.setHorizontalAlignment(SwingConstants.CENTER);
			lblPeak.setBounds(21, 21, 130, 182);
			pnlPeak.add(lblPeak);
			


	}
	
	/**
	 * returns the user to their daily dash board
	 */
	public void home()
	{
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
