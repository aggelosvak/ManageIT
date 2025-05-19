import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeManagementApp {

    public static void main(String[] args) {
        // Launch Login Page
        new LoginPage();
    }
}

class LoginPage {
    JFrame loginFrame;

    LoginPage() {
        loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(3, 2));

        // Create labels and fields
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            // Validate (change with real logic)
            if (userField.getText().equals("admin") && new String(passField.getPassword()).equals("123")) {
                loginFrame.dispose();
                new HomePage();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials");
            }
        });

        loginFrame.add(userLabel);
        loginFrame.add(userField);
        loginFrame.add(passLabel);
        loginFrame.add(passField);
        loginFrame.add(new JLabel(""));
        loginFrame.add(loginButton);

        loginFrame.setVisible(true);
    }
}

class HomePage {
    JFrame homeFrame;

    HomePage() {
        homeFrame = new JFrame("Home");
        homeFrame.setSize(800, 600);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create Navigation Bar
        JPanel navBar = new JPanel();
        navBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        String[] navOptions = {
                "Δημιουργία Κριτικής Υπαλλήλου",
                "Πρόσληψη Υπαλλήλου",
                "Αίτημα Άδειας",
                "Καταχώρηση Αγγελίας",
                "Καταγραφή Εργατοωρών",
                "Αναφορά"
        };

        for (String option : navOptions) {
            JButton navButton = new JButton(option);
            navButton.addActionListener(e -> JOptionPane.showMessageDialog(homeFrame, "Clicked: " + option));
            navBar.add(navButton);
        }

        // Main Content Area
        JTextArea contentArea = new JTextArea("Select an action from the navigation bar above.");
        contentArea.setEditable(false);

        homeFrame.add(navBar, BorderLayout.NORTH);
        homeFrame.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        homeFrame.setVisible(true);
    }
}