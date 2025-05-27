import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import the JobListingUploadPage from the JobListingUpload package
import JobListingUpload.JobListingUploadPage;

public class ManagerLandingPage extends JFrame {
    public ManagerLandingPage() {
        setTitle("Manager Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Καλωσορίσατε, Υπεύθυνε!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton searchEmployeesBtn = new JButton("Αναζήτηση Εργαζομένων");
        searchEmployeesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton jobListingsBtn = new JButton("Αγγελίες");
        jobListingsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton logoutBtn = new JButton("Αποσύνδεση");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action listeners
        searchEmployeesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement employee search functionality
            }
        });

        jobListingsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new JobListingUploadPage().setVisible(true);
                // Optionally hide the landing page:
                // ManagerLandingPage.this.setVisible(false);
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Handle logout and return to login page
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(searchEmployeesBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(jobListingsBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(logoutBtn);

        setContentPane(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagerLandingPage().setVisible(true));
    }
}