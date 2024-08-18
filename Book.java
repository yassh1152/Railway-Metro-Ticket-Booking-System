import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Book extends JFrame {
    private JLabel trainNameLabel, trainNumberLabel, fromStationLabel, toStationLabel, fareLabel;
    private JTextField trainNameField, trainNumberField, fromStationField, toStationField, fareField;
    private JButton confirmButton;

    public Book(String trainName, String trainNumber, String fromStation, String toStation, String fare) {
        setTitle("Book Ticket");
        setLayout(new GridLayout(6, 2));

        trainNameLabel = new JLabel("Train Name:");
        trainNumberLabel = new JLabel("Train Number:");
        fromStationLabel = new JLabel("From Station:");
        toStationLabel = new JLabel("To Station:");
        fareLabel = new JLabel("Fare:");

        trainNameField = new JTextField(trainName);
        trainNumberField = new JTextField(trainNumber);
        fromStationField = new JTextField(fromStation);
        toStationField = new JTextField(toStation);
        fareField = new JTextField(fare);

        confirmButton = new JButton("Confirm");

        add(trainNameLabel);
        add(trainNameField);
        add(trainNumberLabel);
        add(trainNumberField);
        add(fromStationLabel);
        add(fromStationField);
        add(toStationLabel);
        add(toStationField);
        add(fareLabel);
        add(fareField);
        add(new JLabel()); // Empty label for layout purposes
        add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String trainName = trainNameField.getText();
                String trainNumber = trainNumberField.getText();
                String fromStation = fromStationField.getText();
                String toStation = toStationField.getText();
                String fare = fareField.getText();

                saveBookingDetails(trainName, trainNumber, fromStation, toStation, fare);
            }
        });

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void saveBookingDetails(String trainName, String trainNumber, String fromStation, String toStation, String fare) {
        String url = "jdbc:mysql://localhost:3306/metro"; // Update with your database URL
        String user = "root"; // Update with your database username
        String password = "root"; // Update with your database password

        String query = "INSERT INTO bookings (train_name, train_number, from_station, to_station, fare) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, trainName);
            stmt.setString(2, trainNumber);
            stmt.setString(3, fromStation);
            stmt.setString(4, toStation);
            stmt.setBigDecimal(5, new java.math.BigDecimal(fare));

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Booking confirmed!");
            } else {
                JOptionPane.showMessageDialog(this, "No rows affected. Booking not confirmed.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving booking details: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Example data for testing
        new Book("Express 101", "E101", "Station A", "Station B", "250");
    }
}
