import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JFrame {
    JLabel welcomelabel,hellolabel,herelabel,manylabel,jsutlabel,thanklabel;
    JButton userbutton, adminbutton;

    Welcome() {
        setTitle("Welcome");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 400);
        add(imageLabel);

        welcomelabel = new JLabel("Welcome to, Information Railway Tourism Control Pune");
        welcomelabel.setForeground(Color.MAGENTA);
        welcomelabel.setFont(new Font("AvantFrade", Font.BOLD, 20));
        welcomelabel.setBounds(140, 20, 750, 40);
        imageLabel.add(welcomelabel);

        hellolabel = new JLabel("Hello ! Good to See You Here.");
        hellolabel.setForeground(Color.BLACK);
        hellolabel.setFont(new Font("AvantFrade", Font.BOLD, 20));
        hellolabel.setBounds(250, 70, 750, 40);
        imageLabel.add(hellolabel);

        herelabel= new JLabel("Here you can check up the Train Details, Fare Enquiry and");
        herelabel.setForeground(Color.BLACK);
        herelabel.setFont(new Font("AvantFrade", Font.BOLD, 20));
        herelabel.setBounds(110, 90, 750, 40);
        imageLabel.add(herelabel);


        manylabel = new JLabel("many more information");
        manylabel.setForeground(Color.BLACK);
        manylabel.setFont(new Font("AvantFrade", Font.BOLD, 20));
        manylabel.setBounds(280, 110, 750, 40);
        imageLabel.add(manylabel);

        jsutlabel = new JLabel("Just go to the Side Menu Links and Explore the Advantages.");
        jsutlabel.setForeground(Color.BLACK);
        jsutlabel.setFont(new Font("AvantFrade", Font.BOLD, 20));
        jsutlabel.setBounds(130, 130, 750, 40);
        imageLabel.add(jsutlabel);

        thanklabel = new JLabel("Thanks For Being Connected with us!");
        thanklabel.setForeground(Color.BLACK);
        thanklabel.setFont(new Font("AvantFrade", Font.BOLD, 20));
        thanklabel.setBounds(230, 170, 750, 40);
        imageLabel.add(thanklabel);

        userbutton = new JButton("User");
        userbutton.setFont(new Font("Arial", Font.BOLD, 20));
        userbutton.setForeground(Color.BLACK);
        userbutton.setBounds(230, 230, 150, 30);
        imageLabel.add(userbutton);

        adminbutton = new JButton("Admin");
        adminbutton.setFont(new Font("Arial", Font.BOLD, 20));
        adminbutton.setForeground(Color.BLACK);
        adminbutton.setBounds(430, 230, 150, 30);
        imageLabel.add(adminbutton);

        userbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Signup();  // Assuming Signup is a valid class
            }
        });

        adminbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminSignUp();  // Assuming AdminSignUp is a valid class
            }
        });

        setLayout(null);
        setSize(850, 400);
        setLocation(450, 150);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Welcome();
    }
}
