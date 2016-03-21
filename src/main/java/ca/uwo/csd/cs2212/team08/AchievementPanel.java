package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

import java.util.Calendar;

public class AchievementPanel extends JFrame {

	private JPanel contentPane;
	private Color bgColor = Color.darkGray;
	private Color pannelColor = new Color(168, 219, 168);
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

	public AchievementPanel(final String date, APIData paramAPIData) {

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(bgColor);

		/*---------------------------------------------------*
		 * Title of the entire page
		 *---------------------------------------------------*/
		JLabel titleGoals = new JLabel("    Accolades  ");
		titleGoals.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		titleGoals.setHorizontalAlignment(SwingConstants.CENTER);
		titleGoals.setBounds(78, 6, 837, 84);
		contentPane.add(titleGoals);
		titleGoals.setForeground(white);

		/*------------------------------------------*/
		// create the calendar user can use to pick a date
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
		datePicker.setBounds(371, 79, 225, 27);
		contentPane.add(datePicker);
		datePicker.setBackground(Color.WHITE);
		/*-------------------------------------*/
		// the label to set the last updated time
		/*------------------------------------*/
		Calendar cal = Calendar.getInstance();
		final JLabel lblDataUpdate = new JLabel("Last updated: "
				+ cal.getTime().toString());
		lblDataUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataUpdate.setForeground(white);
		lblDataUpdate.setBounds(51, 660, 923, 37);
		contentPane.add(lblDataUpdate);

		/*------------------------------------------*/
		// create a refresh button to refresh the data
		/*------------------------------------------*/
		JLabel imgRefresh = new JLabel();
		imgRefresh.setIcon(new ImageIcon(picRefresh));
		imgRefresh.setBounds(596, 70, 36, 36);
		imgRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// what to do on button click
				lblDataUpdate.setText("refreshing...");
				updateDate();
				if (!apiData.refreshDailyDashBoardData(date)) {
					JOptionPane
							.showMessageDialog(contentPane,
									"An error has occured connecting to fitbit servers, please try again later.");
				}
				contentPane.repaint();
			}
		});
		contentPane.add(imgRefresh);

		/*-----------------------------------------*/
		// create a back button to return to dash board
		/*----------------------------------------*/
		JLabel backArrow = new JLabel();
		backArrow.setIcon(new ImageIcon(backImage));
		backArrow.setBounds(0, 0, 48, 48);
		// backArrow.setVisible(true);
		contentPane.add(backArrow);
		
		JLabel lblUhOhYou = new JLabel("Uh Oh! You haven't reached any of your goals yet! :( ");
		lblUhOhYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblUhOhYou.setForeground(Color.WHITE);
		lblUhOhYou.setFont(new Font("Trebuchet MS", Font.BOLD, 38));
		lblUhOhYou.setBounds(33, 313, 967, 84);
		contentPane.add(lblUhOhYou);
		
		JLabel lblGoDoSomething = new JLabel("Go do something! Your accolades will appear here when you acheive your goals :)");
		lblGoDoSomething.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoDoSomething.setForeground(Color.WHITE);
		lblGoDoSomething.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblGoDoSomething.setBounds(21, 356, 967, 84);
		contentPane.add(lblGoDoSomething);
		
		backArrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// what to do on button click
				home();
				dispose();
			}
		});
		
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