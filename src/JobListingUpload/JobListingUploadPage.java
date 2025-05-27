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
    private List<JobListing> jobListings = new ArrayList<>();
    private List<JobPosition> jobPositions = new ArrayList<>();
    private JobPosition selectedPosition = null;
    private Employee selectedEmployee = null;
    private Company currentCompany = null;

    // Panels
    private JPanel listingsPanel = new JPanel(new BorderLayout());
    private JPanel positionsPanel = new JPanel(new BorderLayout());
    private JPanel formPanel = new JPanel();
    private DefaultListModel<String> listingsModel = new DefaultListModel<>();
    private DefaultListModel<String> positionsModel = new DefaultListModel<>();
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField salaryField;
    private JTextField companyIdField;
    private JTextField companyNameField;

    // Services (mock)
    private EmployeeService employeeService = new EmployeeService();

    public JobListingUploadPage() {
        setTitle("Job Listings Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Initialize positions and listings using all() methods
        jobPositions.addAll(JobPositionData.all());
        jobListings.addAll(JobListingData.all());

        // Mock current company (in real usage, fetch from session or DB)
        currentCompany = new Company(1001, "Acme Inc.", "123 Main St", 1, 10, 1, 1);

        initListingsPanel();
        initPositionsPanel();
        initFormPanel();

        mainPanel.add(listingsPanel, "LISTINGS");
        mainPanel.add(positionsPanel, "POSITIONS");
        mainPanel.add(formPanel, "FORM");

        setContentPane(mainPanel);
        cardLayout.show(mainPanel, "LISTINGS");
    }

    private void initListingsPanel() {
        listingsModel.clear();
        for (JobListing jl : jobListings) listingsModel.addElement(jl.toString());
        JList<String> listingsList = new JList<>(listingsModel);
        listingsPanel.add(new JLabel("Διαθέσιμες Αγγελίες:"), BorderLayout.NORTH);
        listingsPanel.add(new JScrollPane(listingsList), BorderLayout.CENTER);

        JButton postBtn = new JButton("Ανάρτηση Αγγελίας");
        postBtn.addActionListener(e -> {
            positionsModel.clear();
            for (JobPosition jp : jobPositions)
                positionsModel.addElement(jp.getTitle() + ": " + jp.getDescription() + " (Salary: " + jp.getSalary() + ")");
            cardLayout.show(mainPanel, "POSITIONS");
        });
        listingsPanel.add(postBtn, BorderLayout.SOUTH);
    }

    private void initPositionsPanel() {
        JList<String> positionsList = new JList<>(positionsModel);
        positionsPanel.add(new JLabel("Επιλογή Θέσης Εργασίας:"), BorderLayout.NORTH);
        positionsPanel.add(new JScrollPane(positionsList), BorderLayout.CENTER);

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
                titleField.setText(selectedPosition.getTitle());
                descriptionArea.setText(selectedPosition.getDescription());
                double salaryFromEmployee = (selectedEmployee != null) ? 1200.00 : selectedPosition.getSalary();  // Example value; in real life you'd look it up
                salaryField.setText(String.valueOf(salaryFromEmployee));
                companyIdField.setText(String.valueOf(currentCompany.getId()));
                companyNameField.setText(currentCompany.getName());
                cardLayout.show(mainPanel, "FORM");
            } else {
                JOptionPane.showMessageDialog(this, "Επιλέξτε μία θέση.");
            }
        });
        positionsPanel.add(selectBtn, BorderLayout.SOUTH);
    }

    private void initFormPanel() {
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.add(new JLabel("Τίτλος Θέσης:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Περιγραφή:"));
        descriptionArea = new JTextArea(5, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(descriptionArea));

        formPanel.add(new JLabel("Μισθός (από Employee):"));
        salaryField = new JTextField();
        salaryField.setEditable(false); // not editable because it comes from Employee
        formPanel.add(salaryField);

        formPanel.add(new JLabel("ID Εταιρείας:"));
        companyIdField = new JTextField();
        companyIdField.setEditable(false);
        formPanel.add(companyIdField);

        formPanel.add(new JLabel("Όνομα Εταιρείας:"));
        companyNameField = new JTextField();
        companyNameField.setEditable(false);
        formPanel.add(companyNameField);

        JButton submitBtn = new JButton("Καταχώρηση Αγγελίας");
        submitBtn.addActionListener(e -> {
            // Prepare confirmation
            String title = titleField.getText();
            String desc = descriptionArea.getText();
            double salary = 0;
            try {
                salary = Double.parseDouble(salaryField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Παρακαλώ δώστε έγκυρο μισθό.");
                return;
            }
            // Confirmation panel
            StringBuilder sb = new StringBuilder();
            sb.append("Επιβεβαίωση Αγγελίας:\n\n");
            sb.append("Τίτλος: ").append(title).append('\n');
            sb.append("Περιγραφή: ").append(desc).append('\n');
            sb.append("Μισθός: ").append(salaryField.getText()).append('\n');
            sb.append("Εταιρεία: ").append(companyNameField.getText()).append(" (ID: ").append(companyIdField.getText()).append(")\n");
            int response = JOptionPane.showConfirmDialog(this, sb.toString(), "Επιβεβαίωση", JOptionPane.OK_CANCEL_OPTION);
            if (response == JOptionPane.OK_OPTION) {
                // Save to the "database" - in real life, call your database/service here!
                JobPosition newPosition = new JobPosition(
                        jobPositions.size() + 1,
                        title,
                        desc,
                        true,
                        salary
                );
                JobListing newListing = new JobListing(
                        jobListings.size() + 1,
                        newPosition,
                        LocalDate.now(),
                        true,
                        companyNameField.getText() // or other relevant company info
                );
                jobPositions.add(newPosition);
                jobListings.add(newListing);
                listingsModel.addElement(newListing.toString());
                cardLayout.show(mainPanel, "LISTINGS");
                selectedPosition = null;
                JOptionPane.showMessageDialog(this, "Η αγγελία καταχωρήθηκε!");
            }
        });
        formPanel.add(submitBtn);

        JButton backBtn = new JButton("Πίσω");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "LISTINGS"));
        formPanel.add(backBtn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JobListingUploadPage().setVisible(true));
    }
}