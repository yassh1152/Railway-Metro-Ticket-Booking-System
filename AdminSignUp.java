import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminSignUp extends JFrame {
    JLabel label1, welcomelabel, adminlabel;
    JTextField usernameField;
    JPasswordField passwordField; // Use JPasswordField for password
    JButton signInButton, forgetPassButton, createAccountButton;

    AdminSignUp() {
        setTitle("Signup");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        welcomelabel = new JLabel("Welcome to Information Railway Tourism Control Pune");
        welcomelabel.setForeground(Color.magenta);
        welcomelabel.setFont(new Font("AvantFrade", Font.BOLD, 25));
        welcomelabel.setBounds(120, 50, 700, 40);
        imageLabel.add(welcomelabel);

        adminlabel = new JLabel("Admin Panel");
        adminlabel.setForeground(Color.BLUE);
        adminlabel.setFont(new Font("AvantFrade", Font.BOLD, 25));
        adminlabel.setBounds(350, 90, 700, 40);
        imageLabel.add(adminlabel);

        label1 = new JLabel("UserName:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(200, 150, 450, 40);
        imageLabel.add(label1);

        usernameField = new JTextField(15);
        usernameField.setBounds(360, 150, 230, 30);
        usernameField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(usernameField);

        label1 = new JLabel("Password:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(200, 200, 450, 40);
        imageLabel.add(label1);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(360, 200, 230, 30);
        passwordField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(passwordField);

        signInButton = new JButton("Sign in");
        signInButton.setFont(new Font("Arial", Font.BOLD, 20));
        signInButton.setForeground(Color.BLACK);
        signInButton.setBounds(380, 250, 180, 30);
        imageLabel.add(signInButton);

        forgetPassButton = new JButton("Forget Password?");
        forgetPassButton.setFont(new Font("Arial", Font.BOLD, 12));
        forgetPassButton.setForeground(Color.BLACK);
        forgetPassButton.setBounds(390, 300, 150, 17);
        imageLabel.add(forgetPassButton);

        createAccountButton = new JButton("Create New Account");
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 20));
        createAccountButton.setForeground(Color.BLACK);
        createAccountButton.setBounds(320, 330, 300, 30);
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
                new AdminNewAccount();
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
        String password = new String(passwordField.getPassword()); // Convert char array to String

        String url = "jdbc:mysql://localhost:3306/metro";
        String dbUsername = "root";
        String dbPassword = "root";
        String query = "SELECT * FROM AdminNewAccount WHERE user_name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose(); // Close the login window
                new Adminmain(); // Open the admin main page
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking login credentials: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new AdminSignUp();
    }
}
