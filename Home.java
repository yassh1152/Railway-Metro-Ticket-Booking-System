import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Home extends JFrame {
    private JButton viewTrainButton, fareEnquiryButton, profileButton, logoutButton, bookticketButton;
    private JTable trainTable;
    private DefaultTableModel tableModel;
    private String currentUsername;

    Home(String username) {
        this.currentUsername = username;

        setTitle("Home");

        // Set up the layout and background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        JLabel welcomeLabel = new JLabel("Welcome to Information Railway Tourism Control Pune");
        welcomeLabel.setForeground(Color.magenta);
        welcomeLabel.setFont(new Font("AvantFrade", Font.BOLD, 24));
        welcomeLabel.setBounds(130, 50, 800, 40);
        imageLabel.add(welcomeLabel);

        // Buttons
        viewTrainButton = new JButton("Search Train");
        viewTrainButton.setFont(new Font("Arial", Font.BOLD, 20));
        viewTrainButton.setForeground(Color.BLACK);
        viewTrainButton.setBounds(50, 120, 180, 30);
        imageLabel.add(viewTrainButton);

        bookticketButton = new JButton("Book Ticket");
        bookticketButton.setFont(new Font("Arial", Font.BOLD, 20));
        bookticketButton.setForeground(Color.BLACK);
        bookticketButton.setBounds(50, 180, 180, 30);
        imageLabel.add(bookticketButton);

        fareEnquiryButton = new JButton("Fare Enquiry");
        fareEnquiryButton.setFont(new Font("Arial", Font.BOLD, 20));
        fareEnquiryButton.setForeground(Color.BLACK);
        fareEnquiryButton.setBounds(50, 240, 180, 30);
        imageLabel.add(fareEnquiryButton);

        profileButton = new JButton("Profile");
        profileButton.setFont(new Font("Arial", Font.BOLD, 20));
        profileButton.setForeground(Color.BLACK);
        profileButton.setBounds(50, 300, 180, 30);
        imageLabel.add(profileButton);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 20));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBounds(50, 360, 180, 30);
        imageLabel.add(logoutButton);

        tableModel = new DefaultTableModel(new String[]{"Train Name", "Train Number", "From Station", "To Station", "Fare"}, 0);
        trainTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(trainTable);
        scrollPane.setBounds(300, 120, 500, 300);
        imageLabel.add(scrollPane);

        // Fetch train data from database and populate the table
        loadTrainData();

        // Button actions
        viewTrainButton.addActionListener(e -> {
            dispose();
            new SearchTrain(); // Implement SearchTrain class
        });

        bookticketButton.addActionListener(e -> {
            dispose();
            new BookTicket(currentUsername); // Pass the logged-in username
        });

        fareEnquiryButton.addActionListener(e -> {
            dispose();
            new Fareenquiry(); // Implement Fareenquiry class
        });

        profileButton.addActionListener(e -> {
            dispose();
            new ProfileView(currentUsername); // Pass the logged-in username
        });

        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Logged out successfully.");
            dispose();
            new Signup(); // Redirect to signup page or login screen, Implement Signup class
        });

        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadTrainData() {
        String url = "jdbc:mysql://localhost:3306/metro";
        String dbUsername = "root";
        String dbPassword = "root";
        String query = "SELECT * FROM train";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String trainName = rs.getString("train_name");
                String trainNumber = rs.getString("train_number");
                String fromStation = rs.getString("from_station");
                String toStation = rs.getString("to_station");
                String fare = rs.getString("fare");

                tableModel.addRow(new Object[]{trainName, trainNumber, fromStation, toStation, fare});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading train data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Assuming you have some logic to get the logged-in username
        // For testing, we'll just pass a hardcoded username
        new Home("testUser");
    }
}
