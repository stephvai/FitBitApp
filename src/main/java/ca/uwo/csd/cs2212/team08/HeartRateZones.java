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

		/*-----------------------------------------*/
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

		
		/*------------------------------------------*/
		// create calendar that the user can use to pick a date
		/*------------------------------------------*/
		UtilDateModel model = new UtilDateModel();
		// set the calendar date to the one given by java
		String[] dateArray = this.date.split("-");
		int year = Integer.parseInt(dateArray[0]);
		int month = Integer.parseInt(dateArray[1]);
		int day = Integer.parseInt(dateArray[2]);
		model.setDate(year, month - 1, day);
		model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		// create a date picker to allow the user to select the date
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setToolTipText("Please select the desired date");
		datePicker.setBounds(385, 105, 225, 27);
		contentPane.add(datePicker);
		datePicker.setBackground(Color.WHITE);
		
		/*-------------------------------------*/
		//the label to set the last updated time
		/*-------------------------------------*/
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
		 imgRefresh.setBounds(613, 95, 36, 36);
		 imgRefresh.addMouseListener(new MouseAdapter() {
			 @Override
			 public void mouseClicked(MouseEvent arg0) {
				 //what to do on button click
				 lblDataUpdate.setText("refreshing...");
				 updateDate();
				 if (!apiData.refreshDailyDashBoardData(date)) {
		    		 JOptionPane.showMessageDialog(contentPane, "An error has occured connecting to fitbit servers, please try again later.");
		    	 }
				 //initUI(date, apiData);
				 contentPane.repaint();
			 }
		 });
		 contentPane.add(imgRefresh);


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
