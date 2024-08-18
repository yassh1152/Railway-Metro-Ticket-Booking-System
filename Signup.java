import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Signup extends JFrame {
    JLabel label1, welcomelabel;
    JTextField usernameField;
    JPasswordField passwordField; // Use JPasswordField for password
    JButton signInButton, forgetPassButton, createAccountButton;

    Signup() {
        setTitle("Signup");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        welcomelabel = new JLabel("Welcome to Information Railway Tourism Control Pune");
        welcomelabel.setForeground(Color.magenta);
        welcomelabel.setFont(new Font("AvantFrade", Font.BOLD, 24));
        welcomelabel.setBounds(150, 60, 700, 40);
        imageLabel.add(welcomelabel);

        label1 = new JLabel("UserName:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(200, 150, 450, 40);
        imageLabel.add(label1);

        usernameField = new JTextField(15);
        usernameField.setBounds(380, 150, 230, 30);
        usernameField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(usernameField);

        label1 = new JLabel("Password:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(200, 200, 450, 40);
        imageLabel.add(label1);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(380, 200, 230, 30);
        passwordField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(passwordField);

        signInButton = new JButton("Sign in");
        signInButton.setFont(new Font("Arial", Font.BOLD, 20));
        signInButton.setForeground(Color.BLACK);
        signInButton.setBounds(410, 250, 150, 30);
        imageLabel.add(signInButton);

        forgetPassButton = new JButton("Forget Password?");
        forgetPassButton.setFont(new Font("Arial", Font.BOLD, 12));
        forgetPassButton.setForeground(Color.BLACK);
        forgetPassButton.setBounds(410, 300, 150, 18);
        imageLabel.add(forgetPassButton);

        createAccountButton = new JButton("Create New Account");
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 20));
        createAccountButton.setForeground(Color.BLACK);
        createAccountButton.setBounds(340, 330, 300, 30);
        imageLabel.add(createAccountButton);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLoginCredentials();
            }
        });

        forgetPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ForgetPassword();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new NewAccount();
            }
        });

        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void checkLoginCredentials() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String url = "jdbc:mysql://localhost:3306/metro";
        String dbUsername = "root";
        String dbPassword = "root";
        String query = "SELECT * FROM NewAccount WHERE user_name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Successful login
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new Home(username); // Pass the username to Home
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking login credentials: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
