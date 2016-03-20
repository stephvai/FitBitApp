package ca.uwo.csd.cs2212.team08;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument.Iterator;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class EditMode extends JFrame {

	private JPanel contentPane;
	private APIData apiData;
	private String date;
	public LinkedList<Integer> dashboardPanels;
	//constants to store the picture locations
	private static final String backImage = "src/main/resources/images/arrowLeft4.png";
	
	
	public JRadioButton btnSteps;
	private JRadioButton btnFloorsClimbed;
	private JRadioButton btnCalories;
	private JRadioButton btnDistance;
	private JRadioButton btnActive;
	private JRadioButton btnSedentary;
	private JRadioButton btnAccolades;
	private JRadioButton btnHeartRate;
	private JRadioButton btnGoals;
	
	private final int steps = 0;
	private final int stairs = 1;
	private final int calories = 2;
	private final int distance = 3;
	private final int activeMin = 4;
	private final int sedentaryMin = 5;
	private final int accolades = 6;
	private final int heartRate = 7;
	private final int goals = 8;
	
	/**
	 * Create the frame.
	 */
	public EditMode(String paramDate, APIData paramAPIData) {
		this.setTitle("Team08 Fitbit");
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//contentPane.setBackground(bgColor);
		date = paramDate;
		apiData = paramAPIData;
		
		/*	-----------------------------------------*/
		//create a title for the current pane
		/*	-----------------------------------------*/
		contentPane.setLayout(null);
		JLabel lblTitle = new JLabel("Editor Mode");
		lblTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(80, 21, 837, 84);
		contentPane.add(lblTitle);
		//lblTitle.setForeground(white);
		
		/*	-----------------------------------------*/
		//create a back button to return to dash board
		/*	-----------------------------------------*/
		JLabel imgBack = new JLabel();
		imgBack.setIcon(new ImageIcon(backImage));
		imgBack.setBounds(0, 0, 48, 48);
		imgBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//what to do on button click
				serialize();
				home();
				dispose();
			}
		});
		contentPane.add(imgBack);
		deSerialize();
		
		
		JLabel lblSelectWhichPanels = new JLabel("<html>Select which Panels you want to display on the dashboard </html>");
		lblSelectWhichPanels.setBounds(21, 126, 298, 84);
		contentPane.add(lblSelectWhichPanels);
		
		btnSteps = new JRadioButton("Steps");
		btnSteps.setBounds(41, 225, 201, 35);
		contentPane.add(btnSteps);
		btnSteps.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(btnSteps.isSelected())
                {
                	dashboardPanels.add(steps);
                }
                else
                {
                	dashboardPanels.remove(new Integer(steps));
                	if(checkEmpty())
                	{
                		dashboardPanels.add(steps);
                		btnSteps.setSelected(true);
                		JOptionPane.showMessageDialog(contentPane, "You must have at least one panel displayed");
                	}
                }
            }
        });
 
		btnFloorsClimbed = new JRadioButton("Floors Climbed");
		btnFloorsClimbed.setBounds(41, 275, 201, 35);
		contentPane.add(btnFloorsClimbed);
		btnFloorsClimbed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(btnFloorsClimbed.isSelected())
                {
                	dashboardPanels.add(stairs);
                }
                else
                {
                	dashboardPanels.remove(new Integer(stairs));
                	if(checkEmpty())
                	{
                		dashboardPanels.add(stairs);
                		btnFloorsClimbed.setSelected(true);
                		JOptionPane.showMessageDialog(contentPane, "You must have at least one panel displayed");
                	}
                }
            }
        });
		
		
		
		btnCalories = new JRadioButton("Calories Burned");
		btnCalories.setBounds(41, 325, 201, 35);
		contentPane.add(btnCalories);
		btnCalories.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(btnCalories.isSelected())
                {
                	dashboardPanels.add(calories);
                }
                else
                {
                	dashboardPanels.remove(new Integer(calories));
                	if(checkEmpty())
                	{
                		dashboardPanels.add(calories);
                		btnCalories.setSelected(true);
                		JOptionPane.showMessageDialog(contentPane, "You must have at least one panel displayed");
                	}
                }
            }
        });
		
		
		
		btnDistance = new JRadioButton("Distance Traveled");
		btnDistance.setBounds(41, 375, 201, 35);
		contentPane.add(btnDistance);
		btnDistance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(btnDistance.isSelected())
                {
                	dashboardPanels.add(distance);
                }
                else
                {
                	dashboardPanels.remove(new Integer(distance));
                	if(checkEmpty())
                	{
                		dashboardPanels.add(distance);
                		btnDistance.setSelected(true);
                		JOptionPane.showMessageDialog(contentPane, "You must have at least one panel displayed");
                	}
                }
            }
        });
		
		
		btnActive = new JRadioButton("Active Minutes");
		btnActive.setBounds(41, 425, 201, 35);
		contentPane.add(btnActive);
		btnActive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(btnActive.isSelected())
                {
                	dashboardPanels.add(activeMin);
                }
                else
                {
                	dashboardPanels.remove(new Integer(activeMin));
                	if(checkEmpty())
                	{
                		dashboardPanels.add(activeMin);
                		btnActive.setSelected(true);
                		JOptionPane.showMessageDialog(contentPane, "You must have at least one panel displayed");
                	}
                }
            }
        });
		
		
		btnSedentary = new JRadioButton("Sedentary Minutes");
		btnSedentary.setBounds(41, 475, 201, 35);
		contentPane.add(btnSedentary);
		btnSedentary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(btnSedentary.isSelected())
                {
                	dashboardPanels.add(sedentaryMin);
                }
                else
                {
                	dashboardPanels.remove(new Integer(sedentaryMin));
                	if(checkEmpty())
                	{
                		dashboardPanels.add(sedentaryMin);
                		btnSedentary.setSelected(true);
                		JOptionPane.showMessageDialog(contentPane, "You must have at least one panel displayed");
                	}
                }
            }
        });
		
		
		btnAccolades = new JRadioButton("Acheivements");
		btnAccolades.setBounds(41, 525, 201, 35);
		contentPane.add(btnAccolades);
		btnAccolades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(btnAccolades.isSelected())
                {
                	dashboardPanels.add(accolades);
                }
                else
                {
                	dashboardPanels.remove(new Integer(accolades));
                	if(checkEmpty())
                	{
                		dashboardPanels.add(accolades);
                		btnAccolades.setSelected(true);
                		JOptionPane.showMessageDialog(contentPane, "You must have at least one panel displayed");
                	}
                }
            }
        });
		
		
		btnHeartRate = new JRadioButton("Heart Rate Zones");
		btnHeartRate.setBounds(41, 575, 201, 35);
		contentPane.add(btnHeartRate);
		btnHeartRate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(btnHeartRate.isSelected())
                {
                	dashboardPanels.add(heartRate);
                }
                else
                {
                	dashboardPanels.remove(new Integer(heartRate));
                	if(checkEmpty())
                	{
                		dashboardPanels.add(heartRate);
                		btnHeartRate.setSelected(true);
                		JOptionPane.showMessageDialog(contentPane, "You must have at least one panel displayed");
                	}
                }
            }
        });
		
		
		btnGoals = new JRadioButton("Daily Goals");
		btnGoals.setBounds(41, 625, 201, 35);
		contentPane.add(btnGoals);
		btnGoals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(btnGoals.isSelected())
                {
                	dashboardPanels.add(goals);
                }
                else
                {
                	dashboardPanels.remove(new Integer(goals));
                	if(checkEmpty())
                	{
                		dashboardPanels.add(goals);
                		btnGoals.setSelected(true);
                		JOptionPane.showMessageDialog(contentPane, "You must have at least one panel displayed");
                	}
                }
            }
        });
		
		
		if(dashboardPanels != null)
		{
		java.util.Iterator<Integer> iter = dashboardPanels.iterator();
		while(iter.hasNext())
		{
			int temp = iter.next();
			if(temp == 0)
			{
				btnSteps.setSelected(true);
			}
			else if(temp == 1)
			{
				btnFloorsClimbed.setSelected(true);
			}
			else if(temp == 2)
			{
				btnCalories.setSelected(true);
			}
			else if(temp == 3)
			{
				btnDistance.setSelected(true);
			}
			else if(temp == 4)
			{
				btnActive.setSelected(true);
			}
			else if(temp == 5)
			{
				btnSedentary.setSelected(true);
			}
			else if(temp == 6)
			{
				btnAccolades.setSelected(true);
			}
			else if(temp == 7)
			{
				btnHeartRate.setSelected(true);
			}
			else if(temp == 8)
			{
				btnGoals.setSelected(true);
			}
			
		}
		}

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				serialize();
				System.exit(0);
			}
		});	
			
		
	}
    public void serialize()
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
    
	public void home()
	{
		MainScreen main = new MainScreen(this.date, apiData);
		main.setVisible(true);
	}
	
	private Boolean checkEmpty()
	{
		if(btnSteps.isSelected())
		{
			return false;
		}
		if(btnFloorsClimbed.isSelected())
		{
			return false;
		}
		if(btnCalories.isSelected())
		{
			return false;
		}
		if(btnDistance.isSelected())
		{
			return false;
		}
		if(btnActive.isSelected())
		{
			return false;
		}
		if(btnSedentary.isSelected())
		{
			return false;
		}
		if(btnAccolades.isSelected())
		{
			return false;
		}
		if(btnHeartRate.isSelected())
		{
			return false;
		}
		if(btnGoals.isSelected())
		{
			return false;
		}
		return true;
	}
}
