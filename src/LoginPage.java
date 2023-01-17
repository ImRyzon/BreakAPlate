/**
 * @author Mark Wang
 * 2022-12-13
 *
 * This class will act as the login page of the Break A Plate game
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class LoginPage extends JFrame implements ActionListener {

    /**
     * These are the objects that need to be used in order to create this login page.
     *
     * panel --> the container that stores all the needed components
     * frame --> the container that acts as the actual window of the login page
     * usernameLabel --> the label for the username field
     * usernameField --> the text box of the username field in which the user may input their username
     * passwordLabel --> the label for the password field
     * passwordField--> the text box of the password field in which the user may input their password
     * loginButton --> A button where the user may press if they wish to log in after they entered their credentials
     * registerButton --> A button where the user may press if they wish to register after they entered their credentials'
     * database --> the file in which the database that holds all previously entered usernames and passwords is associated with
     * statistics --> the file that stores the statistics of every user
     * userID --> the file that stores the userID of the current logged-in user
     * title --> the title of this page ("Please Login or Register")
     * icon --> holds the broken plate icon
     * brokenPlate --> the actual file containing the broken plate icon
     * loginIcon --> the file that contains the logo for the login page
     */
    JPanel panel = new JPanel();
    JLabel usernameLabel = new JLabel("Username");
    JTextField usernameField = new JTextField(20);
    JLabel passwordLabel = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField(20);
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");
    File database = new File("Database.txt");
    File statistics = new File("Statistics.txt");
    File userID = new File("userID.txt");
    JLabel title = new JLabel("Please Login or Register");
    JLabel icon = new JLabel();
    ImageIcon brokenPlate = new ImageIcon("brokenplate.png");
    ImageIcon loginIcon = new ImageIcon("login.jpeg");

    /**
     * This constructor of the class will allow for other classes to instantiate an object of this class. This
     * will be used in the LaunchGame class when first launching the game, as it redirects the user to the
     * login page.
     *
     * Apart from that, this constructor will also hold the necessary logic and operations needed in order to
     * create an effective and comprehensive login page for the user.
     */
    LoginPage() {
        // Set the appropriate size, default closing operation, color, icon, and title of the frame
        this.setSize(450, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Please Login or Register");
        this.setBackground(new Color(255, 254, 255));
        this.setIconImage(loginIcon.getImage());
        this.setVisible(true);
        this.add(panel);

        // Set the mode of the panel to absolute positioning and set the appropriate colors
        panel.setLayout(null);
        panel.setBackground(new Color(173, 216, 230));
        panel.setForeground(Color.WHITE);

        // Customize the title text field and add it to the panel
        title.setBounds(30, 10, 420, 50);
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        title.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        title.setVisible(true);
        panel.add(title);

        // Customize the icon and add it to the panel
        icon.setIcon(brokenPlate);
        icon.setBounds(315, 10, 100, 100);
        icon.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0));
        icon.setVisible(true);
        panel.add(icon);

        // Set the appropriate bounds for user label JLabel and append it to the panel
        usernameLabel.setBounds(30, 70, 80, 25);
        panel.add(usernameLabel);

        // Set the appropriate bounds for the user text JTextField and append it to the panel
        usernameField.setBounds(120, 70, 165, 25);
        panel.add(usernameField);

        // Set the appropriate bounds for the password label JLabel and append it to the panel
        passwordLabel.setBounds(30, 100, 80, 25);
        panel.add(passwordLabel);

        // Set the appropriate bounds for the password text JPasswordField and append it to the panel
        passwordField.setBounds(120, 100, 165, 25);
        panel.add(passwordField);

        // Set the appropriate bounds for the login button JButton, add an action listener, and append it to the panel
        loginButton.setBounds(80, 170, 100, 25);
        loginButton.setFont(new Font("Consolas", Font.BOLD, 12));
        loginButton.setBackground(new Color(255, 255, 255));
        loginButton.setBorder(BorderFactory.createEtchedBorder());
        loginButton.addActionListener(this);
        panel.add(loginButton);

        // Set the appropriate bounds for the register button JButton, add an action listener, and append it to the panel
        registerButton.setBounds(200, 170, 100, 25);
        registerButton.setFont(new Font("Consolas", Font.BOLD, 12));
        registerButton.setBackground(new Color(255, 255, 255));
        registerButton.setBorder(BorderFactory.createEtchedBorder());
        registerButton.addActionListener(this);
        panel.add(registerButton);

        // Make the frame visible to the user
        this.setVisible(true);
    }

    /**
     * This method is the actionPerformed() method that indicates the procedures and steps to take after
     * a certain button is pressed.
     *
     * If the login button is pressed, then search the database for the credentials. If it is found, then
     * the user may proceed with the actual game. If not, then tell the user to try again
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // Retrieve the username and password the user has entered into the text boxes when they pressed the login button
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (event.getSource() == loginButton) {
            // use a try-catch method to handle IOExceptions when retrieving data from the database file
            try {
                // call the checkValidity() method to see whether the entered credentials are valid or not
                if (checkValidity(username, password)) {
                    // If the credentials are valid, then the user may proceed to the actual game
                    int result = JOptionPane.showConfirmDialog(null, "Login Successful, Press OK To Proceed", "Success", JOptionPane.OK_CANCEL_OPTION);

                    // Check is the user pressed "OK" in the JOptionPane before playing, then create object of menu
                    if (result == JOptionPane.OK_OPTION) {
                        appendID(username, password);
                        this.dispose();
                        Menu menu = new Menu();
                    }
                } else {
                    // Otherwise, inform the user they entered invalid credentials and let them try again
                    JOptionPane.showMessageDialog(null, "Login Failed, No Such Name Exists.", "Failed", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception exception) {
                exception.printStackTrace(); // If an error occurred, then print the error
            }
        } else if (event.getSource() == registerButton) {
            // use a try-catch method to handle IOExceptions when retrieving data from the database file
            try {
                // call the checkValidity() method to see whether the entered credentials are valid or not
                if (checkValidity(username, password)) {
                    // If the name is found, then the registration failed since the credentials already exist
                    JOptionPane.showMessageDialog(null, "Register Failed, This User Already Exists.", "Failed", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Otherwise, call the appendDatabase() method to store the credentials in the database
                    appendDatabase(username, password);
                    JOptionPane.showMessageDialog(null, "Register Successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception exception) {
                exception.printStackTrace(); // If an error occurred, then print the error
            }
        }

        // Reset both the user text and password text after this operation for better integrity
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * This method will search the database using a Scanner to check whether a certain username and password
     * combination exists. If it does, then it will return true, else return false.
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    boolean checkValidity(String username, String password) throws Exception {
        Scanner readDatabase = new Scanner(database); // Declare scanner to read file

        // read the contents of the file
        while (readDatabase.hasNextLine()) {
            String currentUsername = readDatabase.nextLine();
            String currentPassword = readDatabase.nextLine();
            readDatabase.nextLine(); // to account for line breaks

            // If the current pair of username and password equals the one given in parameters, then it is found - return true
            if (currentUsername.equals(username) && currentPassword.equals(password)) {
                return true;
            }
        }

        // If the given credentials is not found in the database, then return false
        return false;
    }

    /**
     * This method will append credentials in the database using PrintWriter to append to a file
     * Furthermore, it will also append to the statistics database
     * @param username
     * @param password
     * @throws Exception
     */
    void appendDatabase(String username, String password) throws Exception {
        // Initialize a PrintWriter object to append to the file
        PrintWriter writeDatabase = new PrintWriter(new FileWriter(database, true));
        PrintWriter writeStatistics = new PrintWriter(new FileWriter(statistics, true));

        // Write the name and password on separate lines, and then insert a linebreak
        writeDatabase.println(username);
        writeDatabase.println(password);
        writeDatabase.println();
        writeDatabase.close();

        for (int i = 0; i < 4; i++) writeStatistics.println(0);
        writeStatistics.println();
        writeStatistics.close();
    }

    /**
     * This method will append the ID of the current logged-in user to another file to be used in profile page
     * @param username
     * @param password
     */
    void appendID(String username, String password) throws Exception {
        PrintWriter writeUserID = new PrintWriter(userID); // write to UserID file
        Scanner readDatabase = new Scanner(database); // Declare scanner to read database file
        int indexUser = 0;

        // check which index the user is in the database
        while (readDatabase.hasNextLine()) {
            String currentUser = readDatabase.nextLine();
            String currentPassword = readDatabase.nextLine();
            readDatabase.nextLine();

            // If user is found, then write the indices on the UserID file
            if (currentUser.equals(username) && currentPassword.equals(password)) {
                writeUserID.println(indexUser);
                writeUserID.close();
                break;
            }

            ++indexUser;
        }
    }
}
