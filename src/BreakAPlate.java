/**
 * @author Mark Wang
 * 2022-12-13
 *
 * This class acts as the actual Break A Plate game and will be played by the user
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;

public class BreakAPlate extends JFrame implements ActionListener {

    /**
     * These are the objects that need to be used in order to make the game functional
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the game
     * plates --> an array storing three JLabels, all of which represent the plates
     * prize --> the JLabel that stores the prizes won after each round of the game
     * normalPlate --> image of the uncracked plate
     * brokenPlate --> image of the cracked plate
     * brokenPlateIcon --> the icon image used for this page
     * playButton --> the button in which the user presses to play a round of the game
     * menuButton --> button that redirects the user back to the menu
     * statistics --> file of the statistics
     * userID --> file of user ID of current logged-in user
     */
    JPanel platePanel = new JPanel();
    JPanel textPanel = new JPanel();
    JLabel[] plates = new JLabel[3];
    JLabel prize = new JLabel("");
    ImageIcon normalPlate = new ImageIcon("plate (1).jpeg");
    ImageIcon brokenPlate = new ImageIcon("brokenplate (1).jpeg");
    ImageIcon brokenPlateIcon = new ImageIcon("brokenplate.png");
    JButton playButton = new JButton();
    JButton menuButton = new JButton();
    File statistics = new File("Statistics.txt");
    File userID = new File("userID.txt");

    /**
     * This constructor will allow other classes to instantiate an object of this class in
     * order to run the game properly.
     *
     * Apart from that, this constructor will also implement the logic of the game.
     */
    BreakAPlate() {
        // Customize the frame
        this.setSize(750, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Break A Plate");
        this.setBackground(Color.WHITE);
        this.setIconImage(brokenPlateIcon.getImage());
        this.setLayout(new BorderLayout());

        // Customize both panels
        platePanel.setLayout(null);
        platePanel.setBackground(new Color(0, 0, 0));
        platePanel.setPreferredSize(new Dimension(375, 175));
        this.add(platePanel, BorderLayout.CENTER);
        textPanel.setLayout(null);
        textPanel.setBackground(Color.WHITE);
        textPanel.setPreferredSize(new Dimension(375, 250));
        this.add(textPanel, BorderLayout.SOUTH);

        // Set the default icon of the normal plate to all three labels
        for (int i = 0; i < 3; i++) {
            plates[i] = new JLabel();
            plates[i].setIcon(normalPlate);
        }

        // Set the bounds for all three labels
        plates[0].setBounds(20, 0, 300, 300);
        plates[1].setBounds(250, 0, 300, 300);
        plates[2].setBounds(480, 0, 300, 300);

        // Add all three labels to the panel
        for (int i = 0; i < 3; i++) {
            platePanel.add(plates[i]);
        }

        // Customize the play button and add it to the panel
        playButton.setText("Play");
        playButton.setBounds(260, 30, 180, 50);
        playButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        playButton.setBackground(Color.WHITE);
        playButton.addActionListener(this);
        textPanel.add(playButton);

        // Customize the menu button and add it to the panel
        menuButton.setText("Menu");
        menuButton.setBounds(260, 100, 180, 50);
        menuButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        menuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        menuButton.setBackground(Color.WHITE);
        menuButton.addActionListener(this);
        textPanel.add(menuButton);

        // Customize the prize label and add it to the panel
        prize.setBounds(245, 170, 500, 50);
        prize.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        prize.setVisible(true);
        textPanel.add(prize);

        // Make the frame visible
        this.setVisible(true);
    }

    /**
     * The action performed method will perform the necessary procedures after the user has
     * pressed the play button.
     *
     * 1. It will initialize the Random object and a counter for how  many broken plates there are
     * 2. It will use Random to generate an integer in the range [0, 1]
     *    - If it is 0, then the plate remains unbroken
     *    - Otherwise, the plate will be broken and the image will be set to the broken plate image
     * 3. If all plates are broken, they win the tiger plush. If two are broken, they win a sticker.
     *    If one is broken, they win a tattoo. If non are broken, they win nothing
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == playButton) {
            // Initialize Random object and broken plate counter
            Random random = new Random();
            int brokenPlateCount = 0;


            // User random to assign each plate to be broken or not broken
            for (int i = 0; i < 3; i++) {
                if (random.nextInt(2) == 1) {
                    plates[i].setIcon(brokenPlate);
                    ++brokenPlateCount;
                } else {
                    plates[i].setIcon(normalPlate);
                }
            }

            // Assign the prizes based on how many plates are broken
            if (brokenPlateCount == 3) {
                prize.setText("You Win: Tiger Plush");
            } else if (brokenPlateCount == 2) {
                prize.setText("You Win: Sticker");
            } else if (brokenPlateCount == 1) {
                prize.setText("You Win: Tattoo");
            } else {
                prize.setText("Sorry, You Lost.");
            }

            // Rename the button to be "Play Again" since this will not be the first time the user played the game
            playButton.setText("Play Again");

            // Update statistics of user
            try {
                updateStatistics(brokenPlateCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == menuButton) {
            this.dispose();
            Menu menu = new Menu();
        }
    }

    /**
     * This method will update the statistics of the current user depending on how many plates
     * they broke in this round
     * @param numBrokenPlates
     * @throws Exception
     */
    public void updateStatistics(int numBrokenPlates) throws Exception {
        // Get all lines of the file, store it into an ArrayList, and use scanner to get index of user ID
        ArrayList<String> statContent = new ArrayList<>(Files.readAllLines(statistics.toPath(), Charset.defaultCharset()));
        Scanner getUserID = new Scanner(userID);

        // Multiple index by 5 since each set of statistics take up 5 lines
        int index = Integer.parseInt(getUserID.nextLine()) * 5;
        getUserID.close();

        // Update the value to +1
        if (numBrokenPlates == 0) { // If there are no broken plates, then the current index is the correct one
            statContent.set(index, String.valueOf(Integer.parseInt(statContent.get(index)) + 1));
        } else if (numBrokenPlates == 1) { // If there is 1 broken plate, then the correct index to update is +1
            statContent.set(index + 1, String.valueOf(Integer.parseInt(statContent.get(index + 1)) + 1));
        } else if (numBrokenPlates == 2) { // If there is 2 broken plates, the correct index is +2
            statContent.set(index + 2, String.valueOf(Integer.parseInt(statContent.get(index + 2)) + 1));
        } else { // If all plates are broken in that round, the correct index is +3
            statContent.set(index + 3, String.valueOf(Integer.parseInt(statContent.get(index + 3)) + 1));
        }

        // Re-write to the file using Files.write() method
        Files.write(statistics.toPath(), statContent, Charset.defaultCharset());
    }
}
