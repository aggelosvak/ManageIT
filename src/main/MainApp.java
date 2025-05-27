package main;

import coupon.RewardSystemUI;
import employee.EmployeeService;
import notification.NotificationService;
import report.ReportSubmissionPage;
import workhours.WorkHoursTrackingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainApp extends JFrame {

    private final EmployeeService employeeService;
    private final NotificationService notificationService;

    public MainApp(EmployeeService employeeService, NotificationService notificationService) {
        this.employeeService = employeeService;
        this.notificationService = notificationService;
        setTitle("Unified Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Create the navigation panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayout(5, 1, 10, 10));
        navigationPanel.setPreferredSize(new Dimension(200, 300));

        // Add buttons for each module
        JButton jobListingButton = new JButton("Job Listings");
        JButton reportsButton = new JButton("Reports");
        JButton rewardsButton = new JButton("Rewards");
        JButton workHoursButton = new JButton("Work Hours");
        JButton exitButton = new JButton("Exit");

        jobListingButton.addActionListener(this::openJobListings);
        reportsButton.addActionListener(this::openReports);
        rewardsButton.addActionListener(this::openRewards);
        workHoursButton.addActionListener(this::openWorkHours);
        exitButton.addActionListener(e -> System.exit(0)); // Exit application

        navigationPanel.add(jobListingButton);
        navigationPanel.add(reportsButton);
        navigationPanel.add(rewardsButton);
        navigationPanel.add(workHoursButton);
        navigationPanel.add(exitButton);

        // Add a central welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Unified Management System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        // Add everything to the frame
        add(navigationPanel, BorderLayout.WEST);
        add(welcomePanel, BorderLayout.CENTER);
    }

    private void openJobListings(ActionEvent e) {
        // Launch Job Listing Page (mock example)
        JOptionPane.showMessageDialog(this, "This will launch Job Listings UI.");
    }

    private void openReports(ActionEvent e) {
        // Launch Report Submission Page
        SwingUtilities.invokeLater(() -> {
            ReportSubmissionPage reportPage = new ReportSubmissionPage(employeeService, notificationService, 1);
            reportPage.setVisible(true);
        });
    }

    private void openRewards(ActionEvent e) {
        // Launch Reward System UI
        SwingUtilities.invokeLater(() -> {
            RewardSystemUI rewardsPage = new RewardSystemUI(
                    new coupon.RewardService(/* mock rewards, coupons */ null, null, notificationService),
                    new employee.Employee("E001", "John Doe")
            );
            rewardsPage.setVisible(true);
        });
    }

    private void openWorkHours(ActionEvent e) {
        // Launch Work Hours Tracking Page
        SwingUtilities.invokeLater(() -> {
            WorkHoursTrackingPage workHoursPage = new WorkHoursTrackingPage(employeeService, notificationService, "E001");
            workHoursPage.setVisible(true);
        });
    }

    public static void main(String[] args) {
        // Initialize common services
        EmployeeService employeeService = new EmployeeService();
        NotificationService notificationService = new NotificationService();

        // Launch the main window
        SwingUtilities.invokeLater(() -> {
            MainApp mainWindow = new MainApp(employeeService, notificationService);
            mainWindow.setVisible(true);
        });
    }
}