import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Fareenquiry extends JFrame {

    private JLabel label1;
    private JTextField fromStationField, toStationField;
    private JButton fareEnquiryButton, searchTrainButton, bookTicketButton, profileButton, logoutButton, fareenquirybutton;
    private String loggedInUsername;

    // No-argument constructor
    public Fareenquiry() {
        this("");  // Call parameterized constructor with an empty username
    }

    // Parameterized constructor
    public Fareenquiry(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
        setTitle("Fare Enquiry");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        // Header Labels
        label1 = new JLabel("Welcome to Information Railway Tourism Control Pune");
        label1.setForeground(Color.magenta);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 24));
        label1.setBounds(150, 20, 750, 40);
        imageLabel.add(label1);

        label1 = new JLabel("Fare Enquiry for Trains Between Stations");
        label1.setForeground(Color.BLUE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 22));
        label1.setBounds(240, 60, 750, 40);
        imageLabel.add(label1);

        // From Station
        label1 = new JLabel("From Station");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 20));
        label1.setBounds(320, 120, 750, 40);
        imageLabel.add(label1);

        fromStationField = new JTextField(15);
        fromStationField.setBounds(500, 120, 230, 28);
        fromStationField.setFont(new Font("AvantGarde", Font.BOLD, 20));
        imageLabel.add(fromStationField);

        // To Station
        label1 = new JLabel("To Station");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 20));
        label1.setBounds(320, 170, 750, 40);
        imageLabel.add(label1);

        toStationField = new JTextField(15);
        toStationField.setBounds(500, 170, 230, 28);
        toStationField.setFont(new Font("AvantGarde", Font.BOLD, 20));
        imageLabel.add(toStationField);

        // Buttons
        fareEnquiryButton = new JButton("Fare Enquiry");
        fareEnquiryButton.setFont(new Font("Arial", Font.BOLD, 20));
        fareEnquiryButton.setForeground(Color.BLACK);
        fareEnquiryButton.setBounds(520, 220, 180, 30);
        imageLabel.add(fareEnquiryButton);

        searchTrainButton = new JButton("Search Train");
        searchTrainButton.setFont(new Font("Arial", Font.BOLD, 20));
        searchTrainButton.setForeground(Color.BLACK);
        searchTrainButton.setBounds(50, 100, 150, 30);
        imageLabel.add(searchTrainButton);

        bookTicketButton = new JButton("Book Ticket");
        bookTicketButton.setFont(new Font("Arial", Font.BOLD, 20));
        bookTicketButton.setForeground(Color.BLACK);
        bookTicketButton.setBounds(50, 150, 150, 30);
        imageLabel.add(bookTicketButton);

        fareenquirybutton = new JButton("Fare Enquiry");
        fareenquirybutton.setFont(new Font("Arial", Font.BOLD, 20));
        fareenquirybutton.setForeground(Color.BLACK);
        fareenquirybutton.setBounds(50, 200, 150, 30);
        imageLabel.add(fareenquirybutton);

        profileButton = new JButton("Profile");
        profileButton.setFont(new Font("Arial", Font.BOLD, 20));
        profileButton.setForeground(Color.BLACK);
        profileButton.setBounds(50, 250, 150, 30);
        imageLabel.add(profileButton);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 20));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBounds(50, 300, 150, 30);
        imageLabel.add(logoutButton);

        // Button Action Listeners
        fareEnquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performFareEnquiry();
            }
        });

        searchTrainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SearchTrain(loggedInUsername);  // Pass the logged-in username
            }
        });

        bookTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BookTicket(loggedInUsername);  // Pass the logged-in username
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

        // Frame properties
        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Perform Fare Enquiry
    private void performFareEnquiry() {
        String fromStation = fromStationField.getText().trim();
        String toStation = toStationField.getText().trim();

        if (fromStation.isEmpty() || toStation.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both stations.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/metro"; // Update with your database URL
        String user = "root"; // Update with your database username
        String password = "root"; // Update with your database password

        String query = "SELECT fare FROM bookings WHERE from_station = ? AND to_station = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, fromStation);
            stmt.setString(2, toStation);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double fare = rs.getDouble("fare");
                JOptionPane.showMessageDialog(this, "The fare from " + fromStation + " to " + toStation + " is ₹" + fare);
            } else {
                JOptionPane.showMessageDialog(this, "No trains available for the selected stations.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving fare: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Fareenquiry();
    }
}
