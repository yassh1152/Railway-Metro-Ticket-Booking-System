import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgetPasswordAdmin extends JFrame {
    JLabel label1,welcomelabel;
    JTextField usernameField;
    JPasswordField newPasswordField, reEnterPasswordField;
    JButton updatePasswordButton;

    ForgetPasswordAdmin() {
        setTitle("Forget Password");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        welcomelabel = new JLabel("Maharashtra Information Railway Tourism Control Pune");
        welcomelabel.setForeground(Color.BLACK);
        welcomelabel.setFont(new Font("AvantFrade", Font.BOLD, 25));
        welcomelabel.setBounds(120, 50, 700, 40);
        imageLabel.add(welcomelabel);


        label1 = new JLabel("UserName:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(200, 100, 450, 40);
        imageLabel.add(label1);

        usernameField = new JTextField(15);
        usernameField.setBounds(380, 100, 230, 30);
        usernameField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(usernameField);

        label1 = new JLabel("New Password:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(200, 150, 450, 40);
        imageLabel.add(label1);

        newPasswordField = new JPasswordField(15);
        newPasswordField.setBounds(380, 150, 230, 30);
        newPasswordField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(newPasswordField);

        label1 = new JLabel("Re-Enter Password:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(200, 200, 450, 40);
        imageLabel.add(label1);

        reEnterPasswordField = new JPasswordField(15);
        reEnterPasswordField.setBounds(380, 200, 230, 30);
        reEnterPasswordField.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(reEnterPasswordField);

        updatePasswordButton = new JButton("Update Password");
        updatePasswordButton.setFont(new Font("Arial", Font.BOLD, 20));
        updatePasswordButton.setForeground(Color.BLACK);
        updatePasswordButton.setBounds(340, 250, 230, 30);
        imageLabel.add(updatePasswordButton);

        updatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassword();
            }
        });

        setLayout(null);
        setSize(850, 350);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void updatePassword() {
        String username = usernameField.getText();
        String newPassword = new String(newPasswordField.getPassword());
        String reEnterPassword = new String(reEnterPasswordField.getPassword());

        if (!newPassword.equals(reEnterPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/metro";
        String dbUsername = "root";
        String dbPassword = "root";
        String query = "UPDATE AdminNewAccount SET pass = ? WHERE user_name = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newPassword);
            stmt.setString(2, username);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
                dispose();
                new Signup();
            } else {
                JOptionPane.showMessageDialog(this, "User not found!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating password: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ForgetPasswordAdmin();
    }
}
