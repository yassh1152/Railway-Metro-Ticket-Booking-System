import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookTicket extends JFrame {
    JLabel label1;
    JButton searchTrainButton, bookTicketButton, fareenquirybutton, profileButton, logoutButton;
    String loggedInUsername;

    public BookTicket(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;  // Store the logged-in username
        setTitle("Book Ticket");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image i2 = i1.getImage().getScaledInstance(850, 480, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imageLabel = new JLabel(i3);
        imageLabel.setBounds(0, 0, 850, 480);
        add(imageLabel);

        label1 = new JLabel("Welcome to Information Railway Tourism Control Pune");
        label1.setForeground(Color.magenta);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 24));
        label1.setBounds(150, 50, 750, 40);
        imageLabel.add(label1);

        searchTrainButton = new JButton("Search Train");
        searchTrainButton.setFont(new Font("Arial", Font.BOLD, 20));
        searchTrainButton.setForeground(Color.BLACK);
        searchTrainButton.setBounds(50, 100, 150, 30);
        imageLabel.add(searchTrainButton);

        bookTicketButton = new JButton("Book Ticket");
        bookTicketButton.setFont(new Font("Arial", Font.BOLD, 20));
        bookTicketButton.setForeground(Color.BLACK);
        bookTicketButton.setBounds(50, 150, 150, 30);
        imageLabel.add(bookTicketButton);

        fareenquirybutton = new JButton("Fare Enquiry");
        fareenquirybutton.setFont(new Font("Arial", Font.BOLD, 20));
        fareenquirybutton.setForeground(Color.BLACK);
        fareenquirybutton.setBounds(50, 200, 150, 30);
        imageLabel.add(fareenquirybutton);

        profileButton = new JButton("Profile");
        profileButton.setFont(new Font("Arial", Font.BOLD, 20));
        profileButton.setForeground(Color.BLACK);
        profileButton.setBounds(50, 250, 150, 30);
        imageLabel.add(profileButton);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 20));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBounds(50, 300, 150, 30);
        imageLabel.add(logoutButton);

        // Add action listeners for buttons
        searchTrainButton.addActionListener(e -> {
            dispose();
            new SearchTrain();
        });

        bookTicketButton.addActionListener(e -> {
            dispose();
            new BookTicket(loggedInUsername);  // Pass the logged-in username
        });

        fareenquirybutton.addActionListener(e -> {
            dispose();
            new Fareenquiry();
        });

        profileButton.addActionListener(e -> {
            dispose();
            new ProfileView(loggedInUsername);  // Pass the logged-in username
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new Signup();
        });

        // Define column names
        String[] columnNames = {
                "Train Name",
                "Train Number",
                "From Station",
                "To Station",
                "Fare (INR)",
                "Booking"
        };

        // Fetch train data from the database
        Object[][] rowData = fetchTrainData();

        // Create the table model and table
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only the last column editable (where the button is)
                return column == 5;
            }
        };
        JTable table = new JTable(model);

        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(225, 100, 600, 250); // Adjusted position and size
        imageLabel.add(scrollPane); // Add the scrollPane to the imageLabel to make it visible on top of the background image

        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Fetch train data from the database
    private Object[][] fetchTrainData() {
        Object[][] data = null;
        String url = "jdbc:mysql://localhost:3306/metro";
        String user = "root";
        String password = "root";

        String query = "SELECT train_name, train_number, from_station, to_station, fare FROM train";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery(query)) {

            rs.last(); // Move to the last row
            int rowCount = rs.getRow(); // Get the row count
            rs.beforeFirst(); // Move back to the beginning
            data = new Object[rowCount][6];
            int i = 0;

            while (rs.next()) {
                data[i][0] = rs.getString("train_name");
                data[i][1] = rs.getString("train_number");
                data[i][2] = rs.getString("from_station");
                data[i][3] = rs.getString("to_station");
                data[i][4] = rs.getString("fare");
                data[i][5] = "Book Now"; // Default text for the button
                i++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching train data: " + ex.getMessage());
        }

        return data;
    }

    // Renderer for the "Book Now" button
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Book Now");
            setFont(new Font("Arial", Font.BOLD, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor for the "Book Now" button
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                if (isPushed) {
                    JTable table = (JTable) ((Component) e.getSource()).getParent();
                    int selectedRow = table.getSelectedRow();
                    String trainName = (String) table.getValueAt(selectedRow, 0);
                    String trainNumber = (String) table.getValueAt(selectedRow, 1);
                    String fromStation = (String) table.getValueAt(selectedRow, 2);
                    String toStation = (String) table.getValueAt(selectedRow, 3);
                    String fare = (String) table.getValueAt(selectedRow, 4);

                    new Book(trainName, trainNumber, fromStation, toStation, fare);
                }
                isPushed = false;
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        new BookTicket("defaultUser");  // Replace "defaultUser" with the actual username
    }
}
