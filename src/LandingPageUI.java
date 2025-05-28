import coupon.*;
import data.CouponData;
import data.RewardData;
import employee.Employee;
import employee.EmployeeService;
import notification.NotificationService;
import report.Report;
import workhours.WorkHoursTrackingService;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class LandingPageUI {

    private final RewardService rewardService;
    private final Employee employee;
    private final WorkHoursTrackingService workHoursTrackingService;
    private final EmployeeService employeeService;
    private final NotificationService notificationService;

    // The main content panel—used for switching dynamic views
    private JPanel contentPanel;

    public LandingPageUI(RewardService rewardService, Employee employee, WorkHoursTrackingService workHoursTrackingService,
                         EmployeeService employeeService, NotificationService notificationService) {
        this.rewardService = rewardService;
        this.employee = employee;
        this.workHoursTrackingService = workHoursTrackingService;
        this.employeeService = employeeService;
        this.notificationService = notificationService;

        initialize();
    }

    private void initialize() {
        JFrame frame = new JFrame();
        frame.setTitle("ManageIT");
        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the main layout
        frame.setLayout(new BorderLayout());

        // Create the top navigation bar
        frame.add(createTopPanel(), BorderLayout.NORTH);

        // Create the dynamic content panel (switch views here)
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(contentPanel, BorderLayout.CENTER);

        // Add the footer panel
        frame.add(createFooterPanel(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Top Panel (Title "ManageIT" and Navigation Bar)
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.LIGHT_GRAY);

        // Title on the left
        JLabel titleLabel = new JLabel("ManageIT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Navigation bar on the right
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        navigationPanel.setOpaque(false);

        // Notifications Button
        JButton notificationsButton = new JButton("Notifications");
        notificationsButton.addActionListener(e -> showNotifications());

        // Dropdown Menu for navigation
        String[] menuOptions = {"Profile", "Log Out", "Rewards", "Work Hours", "Reports"};
        JComboBox<String> dropdownMenu = new JComboBox<>(menuOptions);
        dropdownMenu.addActionListener(e -> handleDropdownSelection((String) dropdownMenu.getSelectedItem()));

        // Add components to the navigation panel
        navigationPanel.add(notificationsButton);
        navigationPanel.add(dropdownMenu);

        // Add navigation panel to the top panel
        topPanel.add(navigationPanel, BorderLayout.EAST);

        return topPanel;
    }

    // Footer Panel
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        JLabel footerLabel = new JLabel("Powered by ManageIT");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerPanel.add(footerLabel);
        return footerPanel;
    }

    // Show Notifications
    private void showNotifications() {
        JOptionPane.showMessageDialog(null, "No new notifications at the moment.", "Notifications", JOptionPane.INFORMATION_MESSAGE);
    }

    // Handle Dropdown Menu Selection
    private void handleDropdownSelection(String selectedItem) {
        switch (selectedItem) {
            case "Profile":
                JOptionPane.showMessageDialog(null, "Navigating to Profile Page...", "Profile", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Log Out":
                JOptionPane.showMessageDialog(null, "Logging out...", "Log Out", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "Rewards":
                displayRewardsUI();
                break;
            case "Work Hours":
                displayWorkHoursTrackingUI();
                break;
            case "Reports":
                displayReportSubmissionUI();
                break;
            default:
                // No action needed for invalid selection
                break;
        }
    }

    // Display Rewards and Coupons UI in the content panel
    private void displayRewardsUI() {
        contentPanel.removeAll();

        JPanel rewardsAndCouponsPanel = new JPanel(new GridLayout(1, 2)); // Two sections: Rewards and Coupons

        List<Reward> rewards = RewardData.all();
        List<Coupon> coupons = CouponData.all();

        JPanel rewardPanel = new JPanel();
        rewardPanel.setLayout(new BoxLayout(rewardPanel, BoxLayout.Y_AXIS));
        rewardPanel.setBorder(BorderFactory.createTitledBorder("Available Rewards"));

        for (Reward reward : rewards) {
            JButton rewardButton = new JButton(reward.getName() + " - Points Required: " + reward.getRequiredPoints());
            rewardButton.addActionListener(e -> handleRewardRedemption(reward));
            rewardPanel.add(rewardButton);
        }

        JPanel couponPanel = new JPanel();
        couponPanel.setLayout(new BoxLayout(couponPanel, BoxLayout.Y_AXIS));
        couponPanel.setBorder(BorderFactory.createTitledBorder("Available Coupons"));

        for (Coupon coupon : coupons) {
            JButton couponButton = new JButton(coupon.getDescription() + " - Points Required: " + coupon.getRequiredPoints());
            couponButton.addActionListener(e -> handleCouponRedemption(coupon));
            couponPanel.add(couponButton);
        }

        rewardsAndCouponsPanel.add(rewardPanel);
        rewardsAndCouponsPanel.add(couponPanel);
        contentPanel.add(rewardsAndCouponsPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void handleCouponRedemption(Coupon coupon) {
        // Check if the employee has enough points to redeem the coupon
        if (employee.getPoints() >= coupon.getRequiredPoints()) {
            // Deduct the required points
            int updatedPoints = employee.getPoints() - coupon.getRequiredPoints();
            employee.setPoints(updatedPoints);

            // Notify the user about successful coupon redemption
            JOptionPane.showMessageDialog(
                    null,
                    "You have successfully redeemed: " + coupon.getDescription() +
                            "\nRemaining Points: " + updatedPoints,
                    "Redemption Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Optionally: Add logic here to mark the coupon as redeemed or notify a relevant service
        } else {
            // Notify the user about insufficient points
            JOptionPane.showMessageDialog(
                    null,
                    "You don't have enough points to redeem this coupon: " + coupon.getDescription() +
                            "\nPoints Required: " + coupon.getRequiredPoints() +
                            "\nYour Points: " + employee.getPoints(),
                    "Redemption Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Display Work Hours Tracking UI in the content panel
    private void displayWorkHoursTrackingUI() {
        contentPanel.removeAll();

        JPanel workHoursPanel = new JPanel();
        workHoursPanel.setLayout(new BoxLayout(workHoursPanel, BoxLayout.Y_AXIS));
        workHoursPanel.setBorder(BorderFactory.createTitledBorder("Work Hours Tracking"));

        JButton arrivalButton = new JButton("Record Arrival Time");
        arrivalButton.addActionListener(e -> workHoursTrackingService.recordArrival(employee.getEmployeeId()));

        JButton startBreakButton = new JButton("Start Break");
        startBreakButton.addActionListener(e -> workHoursTrackingService.startBreak(employee.getEmployeeId()));

        JButton resumeWorkButton = new JButton("Resume Work");
        resumeWorkButton.addActionListener(e -> workHoursTrackingService.resumeWork(employee.getEmployeeId()));

        JButton exitButton = new JButton("Record Exit Time");
        exitButton.addActionListener(e -> workHoursTrackingService.recordExit(employee.getEmployeeId()));

        workHoursPanel.add(arrivalButton);
        workHoursPanel.add(startBreakButton);
        workHoursPanel.add(resumeWorkButton);
        workHoursPanel.add(exitButton);
        contentPanel.add(workHoursPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Display Report Submission UI in the content panel
    private void displayReportSubmissionUI() {
        contentPanel.removeAll();

        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new GridLayout(2, 1));
        reportPanel.setBorder(BorderFactory.createTitledBorder("Report Submission"));

        JButton reportEmployeeButton = new JButton("Report an Employee");
        reportEmployeeButton.addActionListener(e -> displayEmployeeSelectionPanel());

        JButton reportErrorButton = new JButton("Report a System Error");
        reportErrorButton.addActionListener(e -> displayErrorReportForm());

        reportPanel.add(reportEmployeeButton);
        reportPanel.add(reportErrorButton);

        contentPanel.add(reportPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Display Employee Selection for Reporting
    private void displayEmployeeSelectionPanel() {
        List<Employee> employees = employeeService.getAllEmployees(); // Fetch employees
        String[] employeeNames = employees.stream().map(Employee::getName).toArray(String[]::new);

        String selectedEmployee = (String) JOptionPane.showInputDialog(
                null, "Select an employee to report:", "Report an Employee",
                JOptionPane.QUESTION_MESSAGE, null, employeeNames, employeeNames[0]
        );

        if (selectedEmployee != null) {
            handleReportSubmission(selectedEmployee);
        }
    }

    // Display Error Report Form
    private void displayErrorReportForm() {
        String errorDescription = JOptionPane.showInputDialog(
                null, "Describe the system error:", "Report a System Error",
                JOptionPane.QUESTION_MESSAGE
        );

        if (errorDescription != null && !errorDescription.trim().isEmpty()) {
            saveErrorReport(errorDescription);
        } else {
            JOptionPane.showMessageDialog(null, "Error description cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Handle Report Submission
    private void handleReportSubmission(String selectedEmployee) {
        saveReport(selectedEmployee, true); // Notify manager by default
    }

    // Save Employee Report
    private void saveReport(String reportedEmployeeId, boolean notifyManager) {
        String reportId = UUID.randomUUID().toString();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Create the report object
        Report report = new Report(reportId, timestamp, reportedEmployeeId, String.valueOf(employee.getEmployeeId()));
        JOptionPane.showMessageDialog(null, "Report saved: " + report, "Success", JOptionPane.INFORMATION_MESSAGE);

        // Send notification to the manager if required
        if (notifyManager) {
            String notificationTitle = "New Employee Report";
            String notificationMessage = "A new report was submitted for Employee ID: " + reportedEmployeeId;
            notificationService.notify(notificationTitle, notificationMessage);
        }
    }


    // Save Error Report
    private void saveErrorReport(String errorDescription) {
        String reportId = UUID.randomUUID().toString();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Create the error report object
        Report report = new Report(reportId, timestamp, "System Error", String.valueOf(employee.getEmployeeId()));
        JOptionPane.showMessageDialog(null, "Error report saved: " + report, "Success", JOptionPane.INFORMATION_MESSAGE);

        // Notify system admin regarding the error
        String notificationTitle = "System Error Report";
        String notificationMessage = "An error report was submitted: " + errorDescription;
        notificationService.notify(notificationTitle, notificationMessage);
    }
    private void handleRewardRedemption(Reward reward) {
        // Check if the employee has enough points to redeem the reward
        if (employee.getPoints() >= reward.getRequiredPoints()) {
            // Deduct the points
            int updatedPoints = employee.getPoints() - reward.getRequiredPoints();
            employee.setPoints(updatedPoints);

            // Notify the user about successful redemption
            JOptionPane.showMessageDialog(
                    null,
                    "You have successfully redeemed: " + reward.getName() +
                            "\nRemaining Points: " + updatedPoints,
                    "Redemption Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Optionally: Notify rewards or admin services (if required in your application logic)
        } else {
            // Notify the user about insufficient points
            JOptionPane.showMessageDialog(
                    null,
                    "You don't have enough points to redeem: " + reward.getName() +
                            "\nPoints Required: " + reward.getRequiredPoints() +
                            "\nYour Points: " + employee.getPoints(),
                    "Redemption Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    public static void main(String[] args) {
        EmployeeService mockEmployeeService = new EmployeeService();
        NotificationService mockNotificationService = new NotificationService();
        WorkHoursTrackingService mockWorkHoursService = new WorkHoursTrackingService(mockEmployeeService, mockNotificationService);
        Employee mockEmployee = new Employee(1, "John Doe", null, null);
        RewardService rewardService = new RewardService(RewardData.all(), CouponData.all(), mockNotificationService);

        SwingUtilities.invokeLater(() -> new LandingPageUI(rewardService, mockEmployee, mockWorkHoursService, mockEmployeeService, mockNotificationService));
    }
}