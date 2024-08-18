import javax.swing.*;import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchTrain extends JFrame {

    private JTextField fromStationField, toStationField;
    private JButton checkTrainButton, searchTrainButton, bookTicketButton, fareEnquiryButton, profileButton, logoutButton;
    private JTable resultsTable;
    private String loggedInUsername;

    // No-argument constructor
    public SearchTrain() {
        this("");  // Call the parameterized constructor with an empty username
    }

    // Parameterized constructor
    public SearchTrain(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;  // Store the logged-in username
        setTitle("Search Train");

        ImageIcon backgroundImageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundImageIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, 850, 480);
        add(backgroundLabel);

        JLabel headerLabel = new JLabel("Welcome to Information Railway Tourism Control Pune");
        headerLabel.setForeground(Color.magenta);
        headerLabel.setFont(new Font("AvantGarde", Font.BOLD, 24));
        headerLabel.setBounds(150, 20, 750, 40);
        backgroundLabel.add(headerLabel);

        JLabel fromStationLabel = new JLabel("From Station");
        fromStationLabel.setForeground(Color.BLACK);
        fromStationLabel.setFont(new Font("AvantGarde", Font.BOLD, 20));
        fromStationLabel.setBounds(320, 120, 180, 30);
        backgroundLabel.add(fromStationLabel);

        fromStationField = new JTextField();
        fromStationField.setBounds(500, 120, 230, 30);
        fromStationField.setFont(new Font("AvantGarde", Font.BOLD, 20));
        backgroundLabel.add(fromStationField);

        JLabel toStationLabel = new JLabel("To Station");
        toStationLabel.setForeground(Color.BLACK);
        toStationLabel.setFont(new Font("AvantGarde", Font.BOLD, 20));
        toStationLabel.setBounds(320, 170, 180, 30);
        backgroundLabel.add(toStationLabel);

        toStationField = new JTextField();
        toStationField.setBounds(500, 170, 230, 30);
        toStationField.setFont(new Font("AvantGarde", Font.BOLD, 20));
        backgroundLabel.add(toStationField);

        checkTrainButton = new JButton("Check Train");
        checkTrainButton.setFont(new Font("Arial", Font.BOLD, 20));
        checkTrainButton.setForeground(Color.BLACK);
        checkTrainButton.setBounds(530, 220, 180, 30);
        backgroundLabel.add(checkTrainButton);

        searchTrainButton = new JButton("Search Train");
        searchTrainButton.setFont(new Font("Arial", Font.BOLD, 20));
        searchTrainButton.setForeground(Color.BLACK);
        searchTrainButton.setBounds(50, 100, 150, 30);
        backgroundLabel.add(searchTrainButton);

        bookTicketButton = new JButton("Book Ticket");
        bookTicketButton.setFont(new Font("Arial", Font.BOLD, 20));
        bookTicketButton.setForeground(Color.BLACK);
        bookTicketButton.setBounds(50, 150, 150, 30);
        backgroundLabel.add(bookTicketButton);

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

        checkTrainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTrains();
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

        fareEnquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Fareenquiry();
            }
        });

        profileButton.addActionListener(e -> {
            dispose();
            new ProfileView(loggedInUsername); // Pass the logged-in username
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Signup();
            }
        });

        // Create table model and table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Train Name");
        model.addColumn("Train Number");
        model.addColumn("From Station");
        model.addColumn("To Station");
        model.addColumn("Fare (INR)");

        resultsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBounds(220, 270, 600, 150);
        backgroundLabel.add(scrollPane);

        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void searchTrains() {
        String fromStation = fromStationField.getText();
        String toStation = toStationField.getText();

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/Metro"; // Update with your database URL
        String user = "root"; // Update with your database username
        String password = "root"; // Update with your database password

        String query = "SELECT train_name, train_number, from_station, to_station, fare FROM train WHERE from_station = ? AND to_station = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, fromStation);
            stmt.setString(2, toStation);

            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
            model.setRowCount(0); // Clear previous results

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("train_name"),
                        rs.getString("train_number"),
                        rs.getString("from_station"),
                        rs.getString("to_station"),
                        rs.getBigDecimal("fare")
                });
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No trains available for the selected stations.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for trains: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new SearchTrain("defaultUser");  // Replace "defaultUser" with the actual username
    }
}
