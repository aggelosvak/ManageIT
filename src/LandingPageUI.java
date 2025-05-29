import JobApply.JobListingsPage;
import coupon.RewardService;
import data.EmployeeData;
import data.CouponData;
import data.RewardData;
import employee.Employee;
import employee.EmployeeService;
import notification.NotificationService;
import pages.WorkHoursPage;
import workhours.WorkHoursTrackingService;
import pages.RewardsPage;
import pages.ReportsOptionsPage;

import javax.swing.*;
import java.awt.*;

/**
 * Main Landing Page for employee users
 */
public class LandingPageUI extends JFrame {

    private final JPanel contentPanel; // For dynamic page switching
    private final RewardService rewardService;
    private final WorkHoursTrackingService workHoursTrackingService;
    private final NotificationService notificationService;
    private final Employee employee;
    private final EmployeeService employeeService; // Added reference for EmployeeService

    // Constructor
    public LandingPageUI(Employee employee, RewardService rewardService, WorkHoursTrackingService workHoursService, NotificationService notificationService, EmployeeService employeeService) {
        this.employee = employee;
        this.rewardService = rewardService;
        this.workHoursTrackingService = workHoursService;
        this.notificationService = notificationService;
        this.employeeService = employeeService;

        setTitle("Employee Dashboard");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPanel = new JPanel(new CardLayout());
        initComponents();

        add(contentPanel, BorderLayout.CENTER);
    }

    // Initializes UI components
    private void initComponents() {
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel footer = createFooter();
        add(footer, BorderLayout.SOUTH);

        // Initial page display (e.g., Rewards Page)
        showRewardsPage();
    }

    // Create the top navigation panel
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.LIGHT_GRAY);

        JButton rewardsButton = new JButton("Rewards");
        rewardsButton.addActionListener(e -> showRewardsPage());
        topPanel.add(rewardsButton);

        JButton workHoursButton = new JButton("Work Hours");
        workHoursButton.addActionListener(e -> showWorkHoursPage());
        topPanel.add(workHoursButton);

        // Part of createTopPanel()
        JButton reportButton = new JButton("Αναφορά");
        reportButton.addActionListener(e -> showReportsOptionsPage());
        topPanel.add(reportButton);


        JButton jobListingsButton = new JButton("Job Listings"); // New button for Job Listings
        jobListingsButton.addActionListener(e -> showJobListingsPage());
        topPanel.add(jobListingsButton);

        return topPanel;
    }

    // Creates the footer
    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(Color.DARK_GRAY);
        JLabel footerLabel = new JLabel("Employee Dashboard - All Rights Reserved");
        footerLabel.setForeground(Color.WHITE);
        footer.add(footerLabel);
        return footer;
    }

    // Show Rewards Page
    private void showRewardsPage() {
        contentPanel.removeAll();
        contentPanel.add(new RewardsPage(employee, rewardService));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Show Work Hours Page
    private void showWorkHoursPage() {
        contentPanel.removeAll();
        contentPanel.add(new WorkHoursPage(employee, workHoursTrackingService));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Show Job Listings Page
    private void showJobListingsPage() {
        contentPanel.removeAll(); // Remove existing content
        contentPanel.add(new JobListingsPage()); // Add JobListingsPage
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void showReportsOptionsPage() {
        contentPanel.removeAll();
        contentPanel.add(new ReportsOptionsPage(employee, employeeService, notificationService));
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    public static void main(String[] args) {
        // Example Employee, Services, and Initialization
        Employee employee = EmployeeData.getEmployees().get(0);
        RewardService rewardService = new RewardService(RewardData.all(), CouponData.all(), new NotificationService());
        WorkHoursTrackingService workHoursService = new WorkHoursTrackingService(new EmployeeService(), new NotificationService());
        NotificationService notificationService = new NotificationService();
        EmployeeService employeeService = new EmployeeService();

        SwingUtilities.invokeLater(() -> new LandingPageUI(employee, rewardService, workHoursService, notificationService, employeeService).setVisible(true));
    }
}