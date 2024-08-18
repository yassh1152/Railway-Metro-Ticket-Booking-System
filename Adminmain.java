import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class Adminmain extends JFrame {

    JButton addTrainButton, deleteTrainButton;
    JLabel label1;
    JTable userTable;
    JScrollPane scrollPane;

    Adminmain() {
        super("Admin Panel");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        label1 = new JLabel("Welcome to Information Railway Tourism Control Pune");
        label1.setForeground(Color.MAGENTA);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(100, 70, 700, 40);
        imageLabel.add(label1);

        addTrainButton = new JButton("ADD TRAIN");
        addTrainButton.setFont(new Font("Arial", Font.BOLD, 20));
        addTrainButton.setForeground(Color.BLACK);
        addTrainButton.setBounds(200, 150, 150, 30);
        imageLabel.add(addTrainButton);

        deleteTrainButton = new JButton("REMOVE TRAIN");
        deleteTrainButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteTrainButton.setForeground(Color.BLACK);
        deleteTrainButton.setBounds(400, 150, 250, 30);
        imageLabel.add(deleteTrainButton);

        // Table to display user details
        String[] columnNames = {"User ID", "Username", "Email"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(100, 250, 650, 150);
        imageLabel.add(scrollPane);

        // Fetch and display user details from the database
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Metro", "root", "root");
            System.out.println("Database connected successfully!");

            String query = "SELECT id, user_name, email FROM NewAccount";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String userID = rs.getString("id");
                String username = rs.getString("user_name");
                String email = rs.getString("email");

                tableModel.addRow(new Object[]{userID, username, email});
            }

            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ensure the table is refreshed
        tableModel.fireTableDataChanged();

        addTrainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddTrain();
            }
        });

        deleteTrainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DeleteTrain();
            }
        });

        setLayout(null);
        setSize(850, 500);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Adminmain();
    }
}
