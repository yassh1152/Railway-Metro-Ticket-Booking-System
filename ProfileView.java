import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileView extends JFrame {
    private JButton viewTrainButton, fareEnquiryButton, profileButton, logoutButton, bookticketButton;
    private JTextField usernameField, emailField, phoneField, firstnametextfield, lastnametextfield;
    private String loggedInUsername;

    public ProfileView(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
        setTitle("Profile View");

        ImageIcon backgroundImageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundImageIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, 850, 480);
        add(backgroundLabel);

        JLabel headerLabel = new JLabel("Welcome to Information Railway Tourism Control Pune");
        headerLabel.setForeground(Color.magenta);
        headerLabel.setFont(new Font("AvantGarde", Font.BOLD, 24));
        headerLabel.setBounds(100, 20, 650, 40);  // Adjusted position
        backgroundLabel.add(headerLabel);

        viewTrainButton = new JButton("Search Train");
        viewTrainButton.setFont(new Font("Arial", Font.BOLD, 20));
        viewTrainButton.setForeground(Color.BLACK);
        viewTrainButton.setBounds(50, 100, 150, 30);
        backgroundLabel.add(viewTrainButton);

        bookticketButton = new JButton("Book Ticket");
        bookticketButton.setFont(new Font("Arial", Font.BOLD, 20));
        bookticketButton.setForeground(Color.BLACK);
        bookticketButton.setBounds(50, 150, 150, 30);
        backgroundLabel.add(bookticketButton);

        fareEnquiryButton = new JButton("Fare Enquiry");
        fareEnquiryButton.setFont(new Font("Arial", Font.BOLD, 20));
        fareEnquiryButton.setForeground(Color.BLACK);
        fareEnquiryButton.setBounds(50, 200, 150, 30);
        backgroundLabel.add(fareEnquiryButton);

        profileButton = new JButton("Profile");
        profileButton.setFont(new Font("Arial", Font.BOLD, 20));
        profileButton.setForeground(Color.BLACK);
        profileButton.setBounds(50, 250, 150, 30);
        backgroundLabel.add(profileButton);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 20));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBounds(50, 300, 150, 30);
        backgroundLabel.add(logoutButton);

        JLabel firstnamelabel = new JLabel("First Name:");
        firstnamelabel.setForeground(Color.BLACK);
        firstnamelabel.setFont(new Font("AvantGarde", Font.BOLD, 20));
        firstnamelabel.setBounds(300, 100, 120, 30);
        backgroundLabel.add(firstnamelabel);

        firstnametextfield = new JTextField();
        firstnametextfield.setBounds(420, 100, 200, 30);  // Adjusted position
        firstnametextfield.setFont(new Font("AvantGarde", Font.BOLD, 20));
        firstnametextfield.setEditable(false);
        backgroundLabel.add(firstnametextfield);

        JLabel lastnamelabel = new JLabel("Last Name:");
        lastnamelabel.setForeground(Color.BLACK);
        lastnamelabel.setFont(new Font("AvantGarde", Font.BOLD, 20));
        lastnamelabel.setBounds(300, 150, 120, 30);
        backgroundLabel.add(lastnamelabel);

        lastnametextfield = new JTextField();
        lastnametextfield.setBounds(420, 150, 200, 30);
        lastnametextfield.setFont(new Font("AvantGarde", Font.BOLD, 20));
        lastnametextfield.setEditable(false);
        backgroundLabel.add(lastnametextfield);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font("AvantGarde", Font.BOLD, 20));
        usernameLabel.setBounds(300, 200, 120, 30);
        backgroundLabel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(420, 200, 200, 30);
        usernameField.setFont(new Font("AvantGarde", Font.BOLD, 20));
        usernameField.setEditable(false);
        backgroundLabel.add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setFont(new Font("AvantGarde", Font.BOLD, 20));
        emailLabel.setBounds(300, 250, 120, 30);
        backgroundLabel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(420, 250, 200, 30);
        emailField.setFont(new Font("AvantGarde", Font.BOLD, 20));
        emailField.setEditable(false);
        backgroundLabel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(Color.BLACK);
        phoneLabel.setFont(new Font("AvantGarde", Font.BOLD, 20));
        phoneLabel.setBounds(300, 300, 120, 30);
        backgroundLabel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(420, 300, 200, 30);
        phoneField.setFont(new Font("AvantGarde", Font.BOLD, 20));
        phoneField.setEditable(false);
        backgroundLabel.add(phoneField);





        viewTrainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SearchTrain(loggedInUsername);  // Pass the logged-in username
            }
        });

        bookticketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BookTicket(loggedInUsername);  // Pass the logged-in username
            }
        });

        fareEnquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Fareenquiry(loggedInUsername);  // Pass the logged-in username
            }
        });

        profileButton.addActionListener(e -> {
            dispose();
            new ProfileView(loggedInUsername);  // Pass the logged-in username
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Signup();
            }
        });



        // Fetch user details from the database
        fetchUserDetails();

        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void fetchUserDetails() {
        String url = "jdbc:mysql://localhost:3306/Metro"; // Update with your database URL
        String user = "root"; // Update with your database username
        String password = "root"; // Update with your database password

        // SQL query to fetch details from NewAccount table
        String query = "SELECT first_name, last_name, user_name, email, phone_no FROM NewAccount WHERE user_name = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loggedInUsername);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                firstnametextfield.setText(rs.getString("first_name"));
                lastnametextfield.setText(rs.getString("last_name"));
                usernameField.setText(rs.getString("user_name"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone_no"));
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching user details: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ProfileView("defaultUser");  // Replace "defaultUser" with the actual username
    }
}
