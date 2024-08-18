import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewAccount extends JFrame {
    JLabel label1, welcomelabel;
    JTextField firstNameField, lastNameField, userNameField, emailField, phoneField, aadhaarField;
    JPasswordField passwordField;
    JButton registerButton;

    NewAccount() {
        setTitle("New Account");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 500, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        welcomelabel = new JLabel("Welcome to Information Railway Tourism Control Pune");
        welcomelabel.setForeground(Color.magenta);
        welcomelabel.setFont(new Font("AvantFrade", Font.BOLD, 24));
        welcomelabel.setBounds(120, 10, 700, 40);
        imageLabel.add(welcomelabel);

        label1 = new JLabel("First Name:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 60, 450, 40);
        imageLabel.add(label1);

        firstNameField = new JTextField(15);
        firstNameField.setBounds(380, 60, 230, 30);
        firstNameField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(firstNameField);

        label1 = new JLabel("Last Name:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 110, 450, 40);
        imageLabel.add(label1);

        lastNameField = new JTextField(15);
        lastNameField.setBounds(380, 110, 230, 30);
        lastNameField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(lastNameField);

        label1 = new JLabel("UserName:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 160, 450, 40);
        imageLabel.add(label1);

        userNameField = new JTextField(15);
        userNameField.setBounds(380, 160, 230, 30);
        userNameField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(userNameField);

        label1 = new JLabel("Email:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 210, 450, 40);
        imageLabel.add(label1);

        emailField = new JTextField(15);
        emailField.setBounds(380, 210, 230, 30);
        emailField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(emailField);

        label1 = new JLabel("Phone No:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 260, 450, 40);
        imageLabel.add(label1);

        phoneField = new JTextField(15);
        phoneField.setBounds(380, 260, 230, 30);
        phoneField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(phoneField);

        label1 = new JLabel("Aadhaar No:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 310, 450, 40);
        imageLabel.add(label1);

        aadhaarField = new JTextField(15);
        aadhaarField.setBounds(380, 310, 230, 30);
        aadhaarField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(aadhaarField);

        label1 = new JLabel("Password:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(180, 360, 450, 40);
        imageLabel.add(label1);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(380, 360, 230, 30);
        passwordField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(passwordField);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 20));
        registerButton.setForeground(Color.BLACK);
        registerButton.setBounds(410, 410, 150, 30);
        imageLabel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNewAccountDetails();
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
        String aadhaarNo = aadhaarField.getText();
        String password = new String(passwordField.getPassword());

        String url = "jdbc:mysql://localhost:3306/metro";
        String dbUsername = "root";
        String dbPassword = "root";
        String query = "INSERT INTO NewAccount (first_name, last_name, user_name, email, phone_no, aadhaar_no, pass) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, userName);
            stmt.setString(4, email);
            stmt.setString(5, phoneNo);
            stmt.setString(6, aadhaarNo);
            stmt.setString(7, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "New account created successfully!");
                dispose(); // Close the current window
                new Signup(); // Open the Signup window
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving account details: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new NewAccount();
    }
}
