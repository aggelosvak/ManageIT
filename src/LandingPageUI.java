import JobApply.JobListingsPage;
import coupon.RewardService;
import data.CouponData;
import data.EmployeeData;
import data.RewardData;
import employee.Employee;
import employee.EmployeeService;
import notification.NotificationService;
import pages.*;
import workhours.WorkHoursTrackingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LandingPageUI extends JFrame {

    private final JPanel contentPanel;
    private final RewardService rewardService;
    private final WorkHoursTrackingService workHoursTrackingService;
    private final NotificationService notificationService;
    private final Employee employee;
    private final EmployeeService employeeService;

    // Constructor
    public LandingPageUI(Employee employee,
                         RewardService rewardService,
                         WorkHoursTrackingService workHoursService,
                         NotificationService notificationService,
                         EmployeeService employeeService) {
        this.employee = employee;
        this.rewardService = rewardService;
        this.workHoursTrackingService = workHoursService;
        this.notificationService = notificationService;
        this.employeeService = employeeService;

        setTitle("Landing Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        contentPanel = new JPanel(new CardLayout());
        mainPanel.add(createTopPanel(), BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    // Top navigation bar
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JButton rewardsButton = new JButton("Rewards");
        rewardsButton.addActionListener(e -> showRewardsPage());
        topPanel.add(rewardsButton);

        JButton workHoursButton = new JButton("Work Hours");
        workHoursButton.addActionListener(e -> showWorkHoursPage());
        topPanel.add(workHoursButton);

        JButton jobListingsButton = new JButton("Job Listings");
        jobListingsButton.addActionListener(e -> showJobListingsPage());
        topPanel.add(jobListingsButton);

        JButton leaveRequestButton = new JButton("Leave Request");
        leaveRequestButton.addActionListener(e -> showLeaveRequestPage());
        topPanel.add(leaveRequestButton);

        JButton reportsButton = new JButton("Reports");
        reportsButton.addActionListener(e -> showReportsOptionsPage());
        topPanel.add(reportsButton);

        // Removed Review Button from the navigation bar

        return topPanel;
    }

    // Rewards Page
    private void showRewardsPage() {
        contentPanel.removeAll();
        RewardsPage rewardsPage = new RewardsPage(employee, rewardService);
        contentPanel.add(rewardsPage);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Work Hours Page
    private void showWorkHoursPage() {
        contentPanel.removeAll();
        WorkHoursPage workHoursPage = new WorkHoursPage(employee, workHoursTrackingService);
        contentPanel.add(workHoursPage);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Job Listings Page
    private void showJobListingsPage() {
        contentPanel.removeAll();
        JobListingsPage jobListingsPage = new JobListingsPage();
        contentPanel.add(jobListingsPage);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Reports Options Page
    private void showReportsOptionsPage() {
        contentPanel.removeAll();
        ReportsOptionsPage reportsOptionsPage = new ReportsOptionsPage(employee, employeeService, notificationService);
        contentPanel.add(reportsOptionsPage);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Leave Request Page
    private void showLeaveRequestPage() {
        contentPanel.removeAll();
        LeaveRequestPage leaveRequestPage = new LeaveRequestPage(employee, notificationService);
        contentPanel.add(leaveRequestPage);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Footer with logout functionality
    private JPanel createFooter() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener((ActionEvent e) -> {
            int confirmed = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                dispose();
                // You can navigate to the login page or exit
                System.out.println("Logged out successfully!");
            }
        });
        footerPanel.add(logoutButton);

        return footerPanel;
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Employee employee = EmployeeData.getEmployeeById(1); // Example employee
            RewardService rewardService = new RewardService(RewardData.all(), CouponData.all(), new NotificationService());
            WorkHoursTrackingService workHoursService = new WorkHoursTrackingService(
                    new EmployeeService(),
                    new NotificationService()
            );
            new LandingPageUI(employee, rewardService, workHoursService, new NotificationService(), new EmployeeService()).setVisible(true);
        });
    }
}