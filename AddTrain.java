import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddTrain extends JFrame {
    JLabel trainnumberlabel, trainnamelabel, fromstationlabel, tostationlabel, farelabel;
    JTextField trainnumbertextfield, trainnametextfield, fromstationtextfield, tostationtextfield, faretextfield;
    JButton addtrainbutton;
    JLabel label1;

    AddTrain() {
        setTitle("Add Train");

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

        // Corrected Label: Train Number
        trainnumberlabel = new JLabel("Train Number:");
        trainnumberlabel.setForeground(Color.BLACK);
        trainnumberlabel.setFont(new Font("AvantFrade", Font.BOLD, 22));
        trainnumberlabel.setBounds(200, 100, 450, 40);
        imageLabel.add(trainnumberlabel);

        trainnumbertextfield = new JTextField(15);
        trainnumbertextfield.setBounds(400, 100, 230, 30);
        trainnumbertextfield.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(trainnumbertextfield);

        // Corrected Label: Train Name
        trainnamelabel = new JLabel("Train Name:");
        trainnamelabel.setForeground(Color.BLACK);
        trainnamelabel.setFont(new Font("AvantFrade", Font.BOLD, 22));
        trainnamelabel.setBounds(200, 150, 450, 40);
        imageLabel.add(trainnamelabel);

        trainnametextfield = new JTextField(15);
        trainnametextfield.setBounds(400, 150, 230, 30);
        trainnametextfield.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(trainnametextfield);

        // Corrected Label: From Station
        fromstationlabel = new JLabel("From Station:");
        fromstationlabel.setForeground(Color.BLACK);
        fromstationlabel.setFont(new Font("AvantFrade", Font.BOLD, 22));
        fromstationlabel.setBounds(200, 200, 450, 40);
        imageLabel.add(fromstationlabel);

        fromstationtextfield = new JTextField(15);
        fromstationtextfield.setBounds(400, 200, 230, 30);
        fromstationtextfield.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(fromstationtextfield);

        // Corrected Label: To Station
        tostationlabel = new JLabel("To Station:");
        tostationlabel.setForeground(Color.BLACK);
        tostationlabel.setFont(new Font("AvantFrade", Font.BOLD, 22));
        tostationlabel.setBounds(200, 250, 450, 40);
        imageLabel.add(tostationlabel);

        tostationtextfield = new JTextField(15);
        tostationtextfield.setBounds(400, 250, 230, 30);
        tostationtextfield.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(tostationtextfield);

        // Corrected Label: Fare
        farelabel = new JLabel("Fare:");
        farelabel.setForeground(Color.BLACK);
        farelabel.setFont(new Font("AvantFrade", Font.BOLD, 22));
        farelabel.setBounds(200, 300, 450, 40);
        imageLabel.add(farelabel);

        faretextfield = new JTextField(15);
        faretextfield.setBounds(400, 300, 230, 30);
        faretextfield.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(faretextfield);

        addtrainbutton = new JButton("ADD TRAIN");
        addtrainbutton.setFont(new Font("Arial", Font.BOLD, 20));
        addtrainbutton.setForeground(Color.BLACK);
        addtrainbutton.setBounds(440, 350, 150, 30);
        imageLabel.add(addtrainbutton);

        addtrainbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String trainNumber = trainnumbertextfield.getText().trim();
                String trainName = trainnametextfield.getText().trim();
                String fromStation = fromstationtextfield.getText().trim();
                String toStation = tostationtextfield.getText().trim();
                String fare = faretextfield.getText().trim();

                if (trainNumber.isEmpty() || trainName.isEmpty() || fromStation.isEmpty() || toStation.isEmpty() || fare.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }

                try {
                    // Database connection
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Metro", "root", "root");

                    // SQL Insert Query
                    String query = "INSERT INTO train (train_number, train_name, from_station, to_station, fare) VALUES (?, ?, ?, ?, ?)";

                    // Create a PreparedStatement
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, trainNumber);
                    pst.setString(2, trainName);
                    pst.setString(3, fromStation);
                    pst.setString(4, toStation);
                    pst.setString(5, fare);

                    // Execute the insert command
                    int result = pst.executeUpdate();

                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Train added successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add train. Please try again.");
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
        new AddTrain();
    }
}
