import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import JobListingUpload.JobListingUploadPage;
import CreateSchedule.CreateSchedule;
import company.Company;
import data.CompanyData;

public class ManagerLandingPage extends JFrame {
    private Company currentCompany;

    public ManagerLandingPage(Company company) {
        this.currentCompany = company;

        setTitle("Manager Dashboard");
        setSize(400, 250);
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

        JButton createScheduleBtn = new JButton("Δημιουργία Προγράμματος");
        createScheduleBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

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
                new JobListingsManagerPage().setVisible(true);
            }
        });

        createScheduleBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CreateSchedule(currentCompany).setVisible(true);
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
        panel.add(createScheduleBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(logoutBtn);

        setContentPane(panel);
    }

    public static void main(String[] args) {
        // Fetch all companies using the correct CompanyData method.
        List<Company> companies = CompanyData.getCompanies();
        Company company = companies.isEmpty() ? null : companies.get(0);

        SwingUtilities.invokeLater(() -> new ManagerLandingPage(company).setVisible(true));
    }
}