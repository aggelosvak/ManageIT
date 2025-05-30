package JobListingUpload;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.JobListing;
import model.JobPosition;
import data.JobPositionData;
import data.JobListingData;
import employee.Employee;
import employee.EmployeeService;
import company.Company;

public class JobListingUploadPage extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // Models
    private List<JobPosition> jobPositions = new ArrayList<>();
    private JobPosition selectedPosition = null;
    private Employee selectedEmployee = null;
    private Company currentCompany = null;

    // Panels
    private JPanel positionsPanel = new JPanel(new BorderLayout());
    private JPanel formPanel = new JPanel();
    private DefaultListModel<String> positionsModel = new DefaultListModel<>();
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField salaryField;
    private JTextField companyIdField;
    private JTextField companyNameField;

    // Services (mock)
    private EmployeeService employeeService = new EmployeeService();

    public JobListingUploadPage(Company company) {
        setTitle("Ανάρτηση Αγγελίας");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // So it does NOT close your manager window
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Initialize positions using all() method
        jobPositions.addAll(JobPositionData.all());

        // Get current company passed to constructor
        currentCompany = company;

        initPositionsPanel();
        initFormPanel();

        mainPanel.add(positionsPanel, "POSITIONS");
        mainPanel.add(formPanel, "FORM");

        setContentPane(mainPanel);
        cardLayout.show(mainPanel, "POSITIONS");
    }

    private void initPositionsPanel() {
        positionsModel.clear();
        for (JobPosition jp : jobPositions)
            positionsModel.addElement(jp.getTitle() + ": " + jp.getDescription() + " (Salary: " + jp.getSalary() + ")");

        JList<String> positionsList = new JList<>(positionsModel);
        positionsPanel.add(new JLabel("Επιλογή Θέσης Εργασίας:"), BorderLayout.NORTH);
        positionsPanel.add(new JScrollPane(positionsList), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton selectBtn = new JButton("Επόμενο");
        selectBtn.addActionListener(e -> {
            int idx = positionsList.getSelectedIndex();
            if (idx >= 0) {
                selectedPosition = jobPositions.get(idx);
                // Simulate fetching the "employee" for this job position
                List<Employee> employees = employeeService.getAvailableEmployees();
                if (!employees.isEmpty()) {
                    selectedEmployee = employees.get(0); // For demo, always pick first
                } else {
                    selectedEmployee = null;
                }
                // Fill fields
                formPanelFields();
                cardLayout.show(mainPanel, "FORM");
            } else {
                JOptionPane.showMessageDialog(this, "Επιλέξτε μία θέση.");
            }
        });

        JButton cancelBtn = new JButton("Άκυρο");
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(cancelBtn);
        btnPanel.add(selectBtn);
        positionsPanel.add(btnPanel, BorderLayout.SOUTH);
    }

    private void initFormPanel() {
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        titleField = new JTextField();
        descriptionArea = new JTextArea(5, 30);
        salaryField = new JTextField();
        salaryField.setEditable(false);
        companyIdField = new JTextField();
        companyIdField.setEditable(false);
        companyNameField = new JTextField();
        companyNameField.setEditable(false);

        formPanel.add(new JLabel("Τίτλος Θέσης:"));
        formPanel.add(titleField);

        formPanel.add(new JLabel("Περιγραφή:"));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(descriptionArea));

        formPanel.add(new JLabel("Μισθός (από Employee):"));
        formPanel.add(salaryField);

        formPanel.add(new JLabel("ID Εταιρείας:"));
        formPanel.add(companyIdField);

        formPanel.add(new JLabel("Όνομα Εταιρείας:"));
        formPanel.add(companyNameField);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitBtn = new JButton("Καταχώρηση Αγγελίας");
        submitBtn.addActionListener(e -> handleSubmit());
        JButton backBtn = new JButton("Πίσω");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "POSITIONS"));

        btnPanel.add(backBtn);
        btnPanel.add(submitBtn);
        formPanel.add(btnPanel);
    }

    private void formPanelFields() {
        titleField.setText(selectedPosition.getTitle());
        descriptionArea.setText(selectedPosition.getDescription());
        double salaryFromEmployee = (selectedEmployee != null) ? 1200.00 : selectedPosition.getSalary(); // Example value
        salaryField.setText(String.valueOf(salaryFromEmployee));
        companyIdField.setText(String.valueOf(currentCompany.getId()));
        companyNameField.setText(currentCompany.getName());
    }

    private void handleSubmit() {
        // Prepare confirmation
        String title = titleField.getText();
        String desc = descriptionArea.getText();
        double salary;
        try {
            salary = Double.parseDouble(salaryField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ δώστε έγκυρο μισθό.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Επιβεβαίωση Αγγελίας:\n\n");
        sb.append("Τίτλος: ").append(title).append('\n');
        sb.append("Περιγραφή: ").append(desc).append('\n');
        sb.append("Μισθός: ").append(salaryField.getText()).append('\n');
        sb.append("Εταιρεία: ").append(companyNameField.getText())
                .append(" (ID: ").append(companyIdField.getText()).append(")\n");
        int response = JOptionPane.showConfirmDialog(this, sb.toString(), "Επιβεβαίωση", JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION) {
            // Save to the "database" - in real life, call your database/service here!
            JobPosition newPosition = new JobPosition(
                    JobPositionData.all().size() + 1,
                    title,
                    desc,
                    true,
                    salary
            );
            JobListing newListing = new JobListing(
                    JobListingData.all().size() + 1,
                    newPosition,
                    LocalDate.now(),
                    true,
                    companyNameField.getText()
            );
            // Optionally add to your mock db here, or refresh manager page if needed
            JOptionPane.showMessageDialog(this, "Η αγγελία καταχωρήθηκε!");
            dispose();
        }
    }
}