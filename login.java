import javax.swing.*;
import java.awt.*;

public class login extends JFrame {
    JLabel label1;
    JTextField textField1;

    JButton button1;
    login(){
        super("Login Frame");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850 ,500, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        label1 = new JLabel("Welcome to  Information Railway Tourism Control Pune");
        label1.setForeground(Color.magenta);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(100, 70, 700, 40);
        imageLabel.add(label1);

        label1 = new JLabel("UserName:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(200, 150, 450, 40);
        imageLabel.add(label1);

        textField1 = new JTextField(15);
        textField1.setBounds(380, 150, 230, 30);
        textField1.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(textField1);

        label1 = new JLabel("Password:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 22));
        label1.setBounds(200, 200, 450, 40);
        imageLabel.add(label1);

        textField1 = new JTextField(15);
        textField1.setBounds(380, 200, 230, 30);
        textField1.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        imageLabel.add(textField1);

        button1 = new JButton("Sign in");
        button1.setFont(new Font("Arial", Font.BOLD, 20));
        button1.setForeground(Color.BLACK);
        button1.setBounds(415, 250, 150, 30);
        imageLabel.add(button1);

        setLayout(null);
        setSize(850, 500);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new login();
    }
}
