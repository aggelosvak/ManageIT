import JobApply.JobListingsPage;
import coupon.RewardService;
import data.CouponData;
import data.EmployeeData;
import data.RewardData;
import employee.Employee;
import employee.EmployeeService;
import notification.NotificationService;
import pages.ReportsOptionsPage;
import pages.RewardsPage;
import pages.WorkHoursPage;
import review.CreateReview;
import pages.ReviewPage;
import workhours.WorkHoursTrackingService;

import javax.swing.*;
import java.awt.*;

// Main Landing Page for employee users
public class LandingPageUI extends JFrame {

    private final JPanel contentPanel;
    private final RewardService rewardService;
    private final WorkHoursTrackingService workHoursTrackingService;
    private final NotificationService notificationService;
    private final Employee employee;
    private final EmployeeService employeeService; // EmployeeService reference

    private final CreateReview createReview; // Add CreateReview reference

    // Constructor
    public LandingPageUI(Employee employee, RewardService rewardService, WorkHoursTrackingService workHoursService, NotificationService notificationService, EmployeeService employeeService) {
        this.employee = employee;
        this.rewardService = rewardService;
        this.workHoursTrackingService = workHoursService;
        this.notificationService = notificationService;
        this.employeeService = employeeService;
        this.createReview = new CreateReview(employeeService, notificationService);

        setTitle("Landing Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        contentPanel = new JPanel(new CardLayout());

        add(createTopPanel(), BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        showRewardsPage(); // Default page display
    }

    // Top panel with navigation options
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topPanel.setBackground(Color.LIGHT_GRAY);

        JButton rewardsButton = new JButton("Rewards");
        rewardsButton.addActionListener(e -> showRewardsPage());

        JButton workHoursButton = new JButton("Work Hours");
        workHoursButton.addActionListener(e -> showWorkHoursPage());

        JButton jobListingsButton = new JButton("Job Listings");
        jobListingsButton.addActionListener(e -> showJobListingsPage());

        JButton reportsButton = new JButton("Reports");
        reportsButton.addActionListener(e -> showReportsOptionsPage());

        JButton reviewsButton = new JButton("Reviews"); // New Review button
        reviewsButton.addActionListener(e -> showReviewPage());

        topPanel.add(rewardsButton);
        topPanel.add(workHoursButton);
        topPanel.add(jobListingsButton);
        topPanel.add(reportsButton);
        topPanel.add(reviewsButton); // Add the reviews button

        return topPanel;
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
        contentPanel.removeAll();
        contentPanel.add(new JobListingsPage());
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Show Reports Options Page
    private void showReportsOptionsPage() {
        contentPanel.removeAll();
        contentPanel.add(new ReportsOptionsPage(employee, employeeService, notificationService));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Show Review Page (new functionality)
    private void showReviewPage() {
        contentPanel.removeAll();
        contentPanel.add(new ReviewPage(createReview, employee));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Footer
    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.DARK_GRAY);

        JLabel footerLabel = new JLabel("© 2023 Company Name");
        footerLabel.setForeground(Color.WHITE);

        footer.add(footerLabel);
        return footer;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Employee employee = EmployeeData.getEmployees().get(0); // Example employee
            RewardService rewardService = new RewardService(RewardData.all(), CouponData.all(), new NotificationService());
            WorkHoursTrackingService workHoursService = new WorkHoursTrackingService(new EmployeeService(), new NotificationService());
            NotificationService notificationService = new NotificationService();
            EmployeeService employeeService = new EmployeeService();

            new LandingPageUI(employee, rewardService, workHoursService, notificationService, employeeService).setVisible(true);
        });
    }
}