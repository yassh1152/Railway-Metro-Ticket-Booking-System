import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteTrain extends JFrame {
    JLabel trainnumberlabel, trainnamelabel;
    JTextField trainnumbertextfield, trainnametextfield;
    JButton removetrainbutton;
    JLabel label1;

    DeleteTrain() {
        setTitle("Delete Train");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        label1 = new JLabel("Welcome to Information Railway Tourism Control Pune");
        label1.setForeground(Color.magenta);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(100, 50, 700, 40);
        imageLabel.add(label1);

        trainnumberlabel = new JLabel("Train Number:");
        trainnumberlabel.setForeground(Color.BLACK);
        trainnumberlabel.setFont(new Font("AvantFrade", Font.BOLD, 22));
        trainnumberlabel.setBounds(200, 100, 450, 40);
        imageLabel.add(trainnumberlabel);

        trainnumbertextfield = new JTextField(15);
        trainnumbertextfield.setBounds(400, 100, 230, 30);
        trainnumbertextfield.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(trainnumbertextfield);

        trainnamelabel = new JLabel("Train Name:");
        trainnamelabel.setForeground(Color.BLACK);
        trainnamelabel.setFont(new Font("AvantFrade", Font.BOLD, 22));
        trainnamelabel.setBounds(200, 150, 450, 40);
        imageLabel.add(trainnamelabel);

        trainnametextfield = new JTextField(15);
        trainnametextfield.setBounds(400, 150, 230, 30);
        trainnametextfield.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(trainnametextfield);

        removetrainbutton = new JButton("REMOVE TRAIN");
        removetrainbutton.setFont(new Font("Arial", Font.BOLD, 20));
        removetrainbutton.setForeground(Color.BLACK);
        removetrainbutton.setBounds(440, 200, 150, 30);
        imageLabel.add(removetrainbutton);

        removetrainbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String trainNumber = trainnumbertextfield.getText().trim();
                String trainName = trainnametextfield.getText().trim();

                if (trainNumber.isEmpty() || trainName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in both Train Name and Train Number.");
                    return;
                }

                try {
                    // Database connection
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/metro", "root", "root");

                    // SQL Delete Query with BINARY to enforce case sensitivity
                    String query = "DELETE FROM train WHERE BINARY train_name = ? AND BINARY train_number = ?";

                    // Create a PreparedStatement
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, trainName);
                    pst.setString(2, trainNumber);

                    // Debug: Print the SQL query and values
                    System.out.println("Executing query: " + query);
                    System.out.println("With parameters: train_name = '" + trainName + "', train_number = '" + trainNumber + "'");

                    // Execute the delete command
                    int result = pst.executeUpdate();

                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Train removed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "No train found with the given details.");
                    }

                    // Close the connection
                    pst.close();
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
                }
            }
        });

        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new DeleteTrain();
    }
}
