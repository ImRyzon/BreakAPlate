/**
 * @author Mark Wang
 * 2022-12-14
 *
 * This class will display the Profile of the current logged-in user
 */

import java.awt.*;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class Profile extends JFrame implements ActionListener {

    /**
     * These are the necessary objects for the profile page
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the profile page
     * numBroken --> statistics holder of the current user
     * title --> the title of this window ("Profile")
     * userID --> the userID filepath to get the current user ID
     * statistics --> the statistics filepath to retrieve and fill the numBroken array
     * backToMenu --> the button that will re-direct the user back to the main menu when they wish
     * profile --> the image icon used for this frame
     */
    JPanel panel = new JPanel();
    JLabel[] numBroken = new JLabel[4];
    JLabel title = new JLabel("Profile");
    File userID = new File("UserID.txt");
    File statistics = new File("Statistics.txt");
    JButton backToMenu = new JButton("Menu");
    ImageIcon profile = new ImageIcon("profile.png");

    Profile() throws Exception {
        // Set the frame size, close operation, visibility, title, background, and icon, and add the panel
        this.setSize(525, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
        this.setTitle("Profile");
        this.setBackground(Color.WHITE);
        this.setIconImage(profile.getImage());

        // Set the mode of the panel to absolute positioning and set the appropriate colors
        panel.setLayout(null);
        panel.setBackground(new Color(255, 213, 128));

        // Customize the title text field and add it to the panel
        title.setBounds(200, 10, 420, 50);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        title.setBorder(BorderFactory.createLineBorder(new Color(255, 213, 128), 0));
        title.setBackground(new Color(255, 213, 128));
        title.setVisible(true);
        panel.add(title);

        // Fill and customize the numBroken array, then add it to the panel
        fillStatistics();
        int y = 100;
        for (int i = 0; i < 4; i++, y += 50) {
            numBroken[i].setBounds(75, y, 420, 55);
            numBroken[i].setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            numBroken[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
            numBroken[i].setVisible(true);
            panel.add(numBroken[i]);
        }

        // Customize the menu button and add it to the panel
        backToMenu.setText("Menu");
        backToMenu.setBounds(195, 375, 100, 50);
        backToMenu.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        backToMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        backToMenu.setBackground(Color.WHITE);
        backToMenu.addActionListener(this);
        panel.add(backToMenu);

        // Set the panel to be visible
        panel.setVisible(true);
    }

    /**
     * This method will fill the statistics array so that it can output all the statistics of the user.
     * It will consist of the total number of rounds in which each number of possible broken plates are the results
     * @throws Exception
     */
    public void fillStatistics() throws Exception {
        // Declare scanner to get the user ID index and a scanner to get all the statistics numbers
        Scanner readUserID = new Scanner(userID);
        Scanner readStatistics = new Scanner(statistics);

        // Get the index of the current logged-in user
        int index = Integer.parseInt(readUserID.nextLine());
        readUserID.close();

        // Skip the correct number of lines until the right set of statistics are next in line
        while (index --> 0) {
            for (int i = 0; i < 5; i++) {
                readStatistics.nextLine();
            }
        }

        // Fill the array accordingly
        numBroken[0] = new JLabel("Number of losses: " + readStatistics.nextLine());
        numBroken[1] = new JLabel("Number of tattoos won: " + readStatistics.nextLine());
        numBroken[2] = new JLabel("Number of stickers won: " + readStatistics.nextLine());
        numBroken[3] = new JLabel( "Number of Tiger Plushes won: " + readStatistics.nextLine());

        for (int i = 0; i < 3; i++) {
            numBroken[i].setBackground(new Color(255, 213, 128));
        }

        readStatistics.close();
    }

    /**
     * The action performed method will re-direct the user to the menu when they wish
     * @param event
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == backToMenu) {
            this.dispose();
            Menu menu = new Menu();
        }
    }
}
