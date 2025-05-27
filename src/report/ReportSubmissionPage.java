package report;

import employee.Employee;
import employee.EmployeeService;
import notification.NotificationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


public class ReportSubmissionPage extends JFrame {

    private final EmployeeService employeeService;
    private final NotificationService notificationService;
    private int userId; // Assume logged-in user ID is passed here

    public ReportSubmissionPage(EmployeeService employeeService, NotificationService notificationService, int userId) {
        this.employeeService = employeeService;
        this.notificationService = notificationService;
        this.userId = userId;

        setTitle("Υποβολή Αναφοράς");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel infoLabel = new JLabel("Επιλογή Τύπου Αναφοράς:");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> reportTypeComboBox = new JComboBox<>(new String[] {"Αναφορά Ατόμου στο περιβάλλον εργασίας", "Αναφορά Σφάλματος"});
        reportTypeComboBox.setMinimumSize(new Dimension(200, 30));

        JButton proceedButton = new JButton("Συνέχεια");
        proceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (reportTypeComboBox.getSelectedIndex()) {
                    case 0 -> displayEmployeeSelection(); // Report against a colleague
                    case 1 -> displayErrorReportForm();   // Error report flow
                    default -> JOptionPane.showMessageDialog(ReportSubmissionPage.this, "Παρακαλώ επιλέξτε έγκυρη επιλογή.");
                }
            }
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(reportTypeComboBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(proceedButton);

        setContentPane(mainPanel);
    }

    private void displayEmployeeSelection() {
        List<Employee> collaborators = employeeService.getAvailableEmployees();

        if (collaborators.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Δεν υπάρχουν διαθέσιμοι συνεργάτες για αναφορά.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame employeeFormFrame = new JFrame("Επιλογή Συνεργάτη");
        employeeFormFrame.setSize(400, 300);
        employeeFormFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Επιλέξτε συνεργάτη για αναφορά:");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JComboBox<String> employeeComboBox = new JComboBox<>();
        for (Employee employee : collaborators) {
            employeeComboBox.addItem(employee.getName() + " (ID: " + employee.getEmployeeId() + ")");
        }

        JButton submitButton = new JButton("Υποβολή Αναφοράς");
        submitButton.addActionListener(e -> {
            String selectedEmployee = (String) employeeComboBox.getSelectedItem();
            if (selectedEmployee != null) {
                handleReportSubmission(selectedEmployee);
                employeeFormFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(employeeFormFrame, "Παρακαλώ επιλέξτε έγκυρο συνεργάτη.");
            }
        });

        panel.add(label, BorderLayout.NORTH);
        panel.add(employeeComboBox, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);
        employeeFormFrame.add(panel);
        employeeFormFrame.setVisible(true);
    }

    private void displayErrorReportForm() {
        JFrame errorReportFrame = new JFrame("Αναφορά Σφάλματος");
        errorReportFrame.setSize(400, 250);
        errorReportFrame.setLocationRelativeTo(null);

        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));

        JLabel errorLabel = new JLabel("Περιγράψτε το σφάλμα:");
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea errorDescription = new JTextArea(5, 20);
        errorDescription.setLineWrap(true);
        errorDescription.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(errorDescription);

        JButton submitErrorButton = new JButton("Υποβολή");
        submitErrorButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitErrorButton.addActionListener(e -> {
            String errorText = errorDescription.getText().trim();
            if (errorText.isEmpty()) {
                JOptionPane.showMessageDialog(errorReportFrame, "Η περιγραφή δεν μπορεί να είναι κενή.");
                return;
            }
            saveErrorReport(errorText);
            errorReportFrame.dispose();
        });

        errorPanel.add(errorLabel);
        errorPanel.add(scrollPane);
        errorPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        errorPanel.add(submitErrorButton);

        errorReportFrame.setContentPane(errorPanel);
        errorReportFrame.setVisible(true);
    }

    private void handleReportSubmission(String selectedEmployee) {
        String[] parts = selectedEmployee.split("ID: ");
        String reportedEmployeeId = parts[1].replace(")", "").trim();

        if (isManager(reportedEmployeeId)) {
            saveReport(reportedEmployeeId, false);
            JOptionPane.showMessageDialog(this, "Η αναφορά καταχωρήθηκε και εστάλη μόνο στην Υποστήριξη Πελατών.");
        } else {
            saveReport(reportedEmployeeId, true);
            JOptionPane.showMessageDialog(this, "Η αναφορά καταχωρήθηκε επιτυχώς.");
        }
    }

    private boolean isManager(String employeeId) {
        // Logic to check if the employee ID corresponds to a manager (mocked for now)
        return employeeId.equals("E001"); // Example: "E001" is the manager
    }

    private void saveReport(String reportedEmployeeId, boolean notifyManager) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String reportId = UUID.randomUUID().toString();

        Report report = new Report(reportId, timestamp, reportedEmployeeId, String.valueOf(userId));

        // Simulate saving the report (actual logic involves database service calls)
        System.out.println("Saving report: " + report);

        if (notifyManager) {
            notificationService.notifyEmployeeReview(reportedEmployeeId, null); // Send notification (adjusted)
        }
    }

    private void saveErrorReport(String errorDescription) {
        System.out.println("Saving error report: " + errorDescription);
        JOptionPane.showMessageDialog(this, "Η αναφορά σφάλματος καταχωρήθηκε.");
    }

    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        NotificationService notificationService = new NotificationService();
        SwingUtilities.invokeLater(() -> new ReportSubmissionPage(employeeService, notificationService, 1001).setVisible(true));
    }
}