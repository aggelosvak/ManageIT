import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import JobListingUpload.JobListingUploadPage;
import CreateSchedule.CreateSchedule;
import company.Company;
import data.CompanyData;
import meeting.MeetingPage;
import review.CreateReviewPage;      // <-- Add this import

public class ManagerLandingPage extends JFrame {
    private Company currentCompany;

    public ManagerLandingPage(Company company) {
        this.currentCompany = company;

        setTitle("Manager Dashboard");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Καλωσορίσατε, Υπεύθυνε!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createMeetingBtn = new JButton("Δημιουργία Σύσκεψης");
        createMeetingBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton jobListingsBtn = new JButton("Αγγελίες");
        jobListingsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createScheduleBtn = new JButton("Δημιουργία Προγράμματος");
        createScheduleBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createReviewBtn = new JButton("Δημιουργία Αξιολόγησης"); // NEW BUTTON
        createReviewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton logoutBtn = new JButton("Αποσύνδεση");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action listeners
        createMeetingBtn.addActionListener(e -> new MeetingPage(currentCompany).setVisible(true));
        jobListingsBtn.addActionListener(e -> new JobListingsManagerPage().setVisible(true));
        createScheduleBtn.addActionListener(e -> new CreateSchedule(currentCompany).setVisible(true));
        logoutBtn.addActionListener(e -> {
            // TODO: Handle logout and return to login page
        });

        createReviewBtn.addActionListener(e -> {
            new CreateReviewPage().setVisible(true);
        });

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(createMeetingBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(jobListingsBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createScheduleBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createReviewBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(logoutBtn);

        setContentPane(panel);
    }

    public static void main(String[] args) {
        List<Company> companies = CompanyData.getCompanies();
        Company company = companies.isEmpty() ? null : companies.get(0);

        SwingUtilities.invokeLater(() -> new ManagerLandingPage(company).setVisible(true));
    }
}