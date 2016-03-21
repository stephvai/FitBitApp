package ca.uwo.csd.cs2212.team08;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * creates a JFrame that displays the users achievements information
 *
 */
public class AchievementPanel extends JFrame {

    //a content pane to store all the components
    private JPanel contentPane;
    //a string to store the current date the user wants
    private String date;
    //constants to store the picture locations
    private static final String backImage = "src/main/resources/images/arrowLeft4.png";
    //use to get the API data
    private APIData apiData;

    //Color Scheme
    private Color bgColor = Color.darkGray;
    private Color pannelColor = new Color(168,219,168);
    private Color borderColor = new Color(121,189,154);
    private Color titleColor = new Color(11,72,107);
    private Color white = Color.white;

    /**
     * create a achievement panel to show user information on their floors climbed
     * @param date this is the date selected by the user
     * @param paramAPIData an instance of the APIData that passes in the fitbit information
     */
    public AchievementPanel(String date, APIData paramAPIData) {

		/*-----------------------------------------*/
        //create the main window for the achievement panel
		/*-----------------------------------------*/
        setResizable(false);
        this.date = date;
        setTitle("team08-Fitbit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024,768);
        this.setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(bgColor);

        //create a new instance of the api data
        apiData = paramAPIData;

		/*	-----------------------------------------*/
        //create a title for the current panel
		/*	-----------------------------------------*/
        JLabel lblTitle = new JLabel("ACHIEVEMENTS");
        lblTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(80, 21, 837, 84);
        contentPane.add(lblTitle);
        lblTitle.setForeground(white);

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
                home();
                dispose();
            }
        });
        contentPane.add(imgBack);



    }

    /**
     * returns the user to their daily dash board
     */
    public void home()
    {
        MainScreen main = new MainScreen(this.date, apiData);
        main.setVisible(true);
    }

}

