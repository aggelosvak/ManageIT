package pages;

import employee.Employee;
import notification.NotificationService;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LeaveRequestPage extends JPanel {

    private final Employee employee;
    private final NotificationService notificationService;

    private JComboBox<String> leaveTypeDropdown;
    private JTextArea reasonInput;
    private JLabel leaveBalanceLabel;

    public LeaveRequestPage(Employee employee, NotificationService notificationService) {
        this.employee = employee;
        this.notificationService = notificationService;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Αίτημα Άδειας");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        // Leave Balance Section
        leaveBalanceLabel = new JLabel("Υπόλοιπο Άδειας: " + employee.getLeaveBalance() + " μέρες");
        leaveBalanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(leaveBalanceLabel);

        // Leave Type Dropdown
        leaveTypeDropdown = new JComboBox<>(getLeaveTypes());
        leaveTypeDropdown.setSelectedIndex(-1); // No default selection
        add(new JLabel("Επιλέξτε Τύπο Άδειας:"));
        add(leaveTypeDropdown);

        // Reason Input
        add(new JLabel("Συμπληρώστε τον λόγο της άδειας:"));
        reasonInput = new JTextArea(5, 30);
        add(new JScrollPane(reasonInput));

        // Submit Button
        JButton submitButton = new JButton("Υποβολή Αιτήματος");
        submitButton.addActionListener(e -> handleSubmitLeaveRequest());
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(submitButton);
    }

    private String[] getLeaveTypes() {
        // Mocked leave types. Replace this with dynamic retrieval if needed.
        return new String[]{"Κανονική Άδεια", "Αναρρωτική Άδεια", "Άδεια Άνευ Αποδοχών"};
    }

    private void handleSubmitLeaveRequest() {
        String selectedLeaveType = (String) leaveTypeDropdown.getSelectedItem();
        String reason = reasonInput.getText().trim();

        if (selectedLeaveType == null) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε τύπο άδειας.");
            return;
        }

        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ συμπληρώστε τον λόγο της άδειας.");
            return;
        }

        // Check leave balance
        if (employee.getLeaveBalance() <= 0) {
            JOptionPane.showMessageDialog(this, "Δεν έχετε αρκετές μέρες άδειας για να υποβάλετε αίτημα.");
            return;
        }

        // Update leave balance and notify
        employee.setLeaveBalance(employee.getLeaveBalance() - 1);
        leaveBalanceLabel.setText("Υπόλοιπο Άδειας: " + employee.getLeaveBalance() + " μέρες");

        // Simulate notification to a manager
        Map<String, String> leaveRequestDetails = new HashMap<>();
        leaveRequestDetails.put("Εργαζόμενος", employee.getName());
        leaveRequestDetails.put("Τύπος Άδειας", selectedLeaveType);
        leaveRequestDetails.put("Λόγος", reason);

        notificationService.notify("Νέο Αίτημα Άδειας", leaveRequestDetails.toString());
        JOptionPane.showMessageDialog(this, "Το αίτημα σας καταχωρήθηκε.");
    }
}