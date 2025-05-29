package pages;

import employee.Employee;
import employee.EmployeeService;
import report.Report;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class ColleagueReportPage extends JPanel {

    private JComboBox<Employee> colleagueDropdown;
    private JTextArea reportDetailsArea;
    private JComboBox<String> reportTypeDropdown;

    public ColleagueReportPage(Employee employee, EmployeeService employeeService) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Αναφορά Ατόμου στο περιβάλλον εργασίας");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title);

        // Fetch and display colleagues
        List<Employee> colleagues = employeeService.getColleagues(employee.getEmployeeId());
        colleagueDropdown = new JComboBox<>(colleagues.toArray(new Employee[0]));
        add(new JLabel("Επιλέξτε Συνάδελφο:"));
        add(colleagueDropdown);

        // Report Type Dropdown
        String[] reportTypes = {"Παραβίαση Κανόνων", "Απρεπής Συμπεριφορά", "Άλλο"};
        reportTypeDropdown = new JComboBox<>(reportTypes);
        add(new JLabel("Τύπος Αναφοράς:"));
        add(reportTypeDropdown);

        // Details Text Area
        reportDetailsArea = new JTextArea(5, 30);
        add(new JLabel("Περιγραφή:"));
        add(new JScrollPane(reportDetailsArea));

        // Submit Button
        JButton submitButton = new JButton("Υποβολή");
        submitButton.addActionListener(e -> submitReport(employee));
        add(submitButton);
    }

    private void submitReport(Employee reporter) {
        Employee reported = (Employee) colleagueDropdown.getSelectedItem();
        String reportType = (String) reportTypeDropdown.getSelectedItem();
        String details = reportDetailsArea.getText();

        if (reported == null || details.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Συμπληρώστε όλα τα πεδία.");
            return;
        }

        if (reported.getJobPosition().getTitle().equalsIgnoreCase("Manager")) {
            JOptionPane.showMessageDialog(this, "Δεν μπορείτε να αναφέρετε τον υπεύθυνο.");
            return;
        }

        Report report = new Report(
                "R" + Instant.now().toEpochMilli(),
                Timestamp.from(Instant.now()),
                String.valueOf(reported.getEmployeeId()),
                String.valueOf(reporter.getEmployeeId())
        );
        JOptionPane.showMessageDialog(this, "Η αναφορά σας καταχωρήθηκε επιτυχώς.");
    }
}