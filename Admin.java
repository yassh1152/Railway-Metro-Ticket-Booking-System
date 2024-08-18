import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class Admin extends JFrame {

    JLabel label1;
    JButton button1;
    JTextField textField1;
    JTable userTable;
    JScrollPane scrollPane;

    Admin() {
        setTitle("Admin");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        label1 = new JLabel("WELCOME TO ADMIN DASHBOARD");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(225, 50, 450, 40);
        imageLabel.add(label1);

        // Table to display user details
        String[] columnNames = {"User ID", "Username", "Email"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(100, 400, 650, 100);
        imageLabel.add(scrollPane);

        // Fetch and display user details from the database
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Metro", "root", "root");
            String query = "SELECT user_id, username, email FROM NewAccount";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String userID = rs.getString("user_id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                tableModel.addRow(new Object[]{userID, username, email});
            }

            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Other components
        label1 = new JLabel("Location");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(100, 100, 450, 40);
        imageLabel.add(label1);

        textField1 = new JTextField(15);
        textField1.setBounds(400, 100, 230, 30);
        textField1.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(textField1);

        button1 = new JButton("Add");
        button1.setFont(new Font("Arial", Font.BOLD, 20));
        button1.setForeground(Color.BLACK);
        button1.setBounds(680, 100, 150, 30);
        imageLabel.add(button1);

        label1 = new JLabel("Select Location to Delete");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(100, 150, 450, 40);
        imageLabel.add(label1);

        button1 = new JButton("Remove");
        button1.setFont(new Font("Arial", Font.BOLD, 20));
        button1.setForeground(Color.BLACK);
        button1.setBounds(680, 150, 150, 30);
        imageLabel.add(button1);

        label1 = new JLabel("Add Train");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(100, 200, 450, 40);
        imageLabel.add(label1);

        textField1 = new JTextField(15);
        textField1.setBounds(400, 200, 230, 30);
        textField1.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(textField1);

        button1 = new JButton("Add");
        button1.setFont(new Font("Arial", Font.BOLD, 20));
        button1.setForeground(Color.BLACK);
        button1.setBounds(680, 200, 150, 30);
        imageLabel.add(button1);

        label1 = new JLabel("Select Train to Remove");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(100, 250, 450, 40);
        imageLabel.add(label1);

        textField1 = new JTextField(15);
        textField1.setBounds(400, 250, 230, 30);
        textField1.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(textField1);

        button1 = new JButton("Remove");
        button1.setFont(new Font("Arial", Font.BOLD, 20));
        button1.setForeground(Color.BLACK);
        button1.setBounds(680, 250, 150, 30);
        imageLabel.add(button1);

        label1 = new JLabel("Choose Train");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(100, 300, 450, 40);
        imageLabel.add(label1);

        textField1 = new JTextField(15);
        textField1.setBounds(400, 300, 230, 30);
        textField1.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(textField1);

        label1 = new JLabel("Update Fare");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(100, 350, 450, 40);
        imageLabel.add(label1);

        textField1 = new JTextField(15);
        textField1.setBounds(400, 350, 230, 30);
        textField1.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(textField1);

        button1 = new JButton("Update");
        button1.setFont(new Font("Arial", Font.BOLD, 20));
        button1.setForeground(Color.BLACK);
        button1.setBounds(680, 350, 150, 30);
        imageLabel.add(button1);

        setLayout(null);
        setSize(850, 600);  // Adjusted size to accommodate the table
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Admin();
    }
}
