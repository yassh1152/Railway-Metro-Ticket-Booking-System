import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminNewAccount extends JFrame {
    JLabel label1, welcomelabel;
    JTextField firstNameField, lastNameField, userNameField, emailField, phoneField;
    JPasswordField passwordField;
    JButton registerButton;

    AdminNewAccount() {
        setTitle("New Account");

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 500, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);


        welcomelabel = new JLabel("Welcome to Information Railway Tourism Control Pune");
        welcomelabel.setForeground(Color.magenta);
        welcomelabel.setFont(new Font("AvantFrade", Font.BOLD, 24));
        welcomelabel.setBounds(100, 10, 700, 40);
        imageLabel.add(welcomelabel);

        label1 = new JLabel("First Name:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 90, 450, 40);
        imageLabel.add(label1);

        firstNameField = new JTextField(15);
        firstNameField.setBounds(370, 90, 230, 30);
        firstNameField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(firstNameField);

        // Last Name
        label1 = new JLabel("Last Name:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 140, 450, 40);
        imageLabel.add(label1);

        lastNameField = new JTextField(15);
        lastNameField.setBounds(370, 140, 230, 30);
        lastNameField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(lastNameField);

        // Username
        label1 = new JLabel("UserName:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 190, 450, 40);
        imageLabel.add(label1);

        userNameField = new JTextField(15);
        userNameField.setBounds(370, 190, 230, 30);
        userNameField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(userNameField);

        // Email
        label1 = new JLabel("Email:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 240, 450, 40);
        imageLabel.add(label1);

        emailField = new JTextField(15);
        emailField.setBounds(370, 240, 230, 30);
        emailField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(emailField);

        // Phone Number
        label1 = new JLabel("Phone No:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 290, 450, 40);
        imageLabel.add(label1);

        phoneField = new JTextField(15);
        phoneField.setBounds(370, 290, 230, 30);
        phoneField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(phoneField);

        // Password
        label1 = new JLabel("Password:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 340, 450, 40);
        imageLabel.add(label1);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(370, 340, 230, 30);
        passwordField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(passwordField);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 20));
        registerButton.setForeground(Color.BLACK);
        registerButton.setBounds(410, 390, 150, 30);
        imageLabel.add(registerButton);

        // Register button action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNewAccountDetails();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminSignUp();
            }
        });

        setLayout(null);
        setSize(850, 500);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void saveNewAccountDetails() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String userName = userNameField.getText();
        String email = emailField.getText();
        String phoneNo = phoneField.getText();
        String password = new String(passwordField.getPassword());

        String url = "jdbc:mysql://localhost:3306/metro";
        String dbUsername = "root";
        String dbPassword = "root";
        String query = "INSERT INTO AdminNewAccount (first_name, last_name, user_name, email, phone_no, pass) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, userName);
            stmt.setString(4, email);
            stmt.setString(5, phoneNo);
            stmt.setString(6, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "New account created successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving account details: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new AdminNewAccount();
    }
}
