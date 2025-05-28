package CreateSchedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import company.Company;
import data.EmployeeData;
import employee.Employee;

public class CreateSchedule extends JFrame {
    private final Company currentCompany;
    private JPanel mainPanel;

    // Date selection controls
    private JComboBox<Integer> yearBox;
    private JComboBox<Integer> monthBox;
    private JComboBox<Integer> dayBox;
    private LocalDate selectedDate;

    public CreateSchedule(Company company) {
        this.currentCompany = company;

        setTitle("Δημιουργία Προγράμματος");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(buildDateSelectionPanel(), "DATE_SELECTION");

        setContentPane(mainPanel);
    }

    private JPanel buildDateSelectionPanel() {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel instruction = new JLabel("Επιλέξτε την ημέρα που επιθυμείτε:");
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        datePanel.add(instruction);
        datePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel comboPanel = new JPanel();
        int currentYear = LocalDate.now().getYear();
        yearBox = new JComboBox<>();
        for (int i = currentYear - 5; i <= currentYear + 5; i++) yearBox.addItem(i);
        monthBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) monthBox.addItem(i);
        dayBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) dayBox.addItem(i);

        comboPanel.add(new JLabel("Έτος:"));
        comboPanel.add(yearBox);
        comboPanel.add(new JLabel("Μήνας:"));
        comboPanel.add(monthBox);
        comboPanel.add(new JLabel("Ημέρα:"));
        comboPanel.add(dayBox);

        datePanel.add(comboPanel);

        datePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton nextButton = new JButton("Επόμενο");
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> {
            int year = (Integer) yearBox.getSelectedItem();
            int month = (Integer) monthBox.getSelectedItem();
            int day = (Integer) dayBox.getSelectedItem();
            try {
                selectedDate = LocalDate.of(year, month, day);
                // If OK, move to worker selection
                showWorkerSelectionPanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Μη έγκυρη ημερομηνία.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        datePanel.add(nextButton);

        return datePanel;
    }

    private void showWorkerSelectionPanel() {
        JPanel workerPanel = new JPanel();
        workerPanel.setLayout(new BorderLayout());

        JLabel dateLabel = new JLabel("Επιλεγμένη Ημερομηνία: " + selectedDate, SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        workerPanel.add(dateLabel, BorderLayout.NORTH);

        List<Employee> employees = new ArrayList<>();
        for (Employee emp : EmployeeData.getEmployees()) {
            if (emp.getCompany() != null && emp.getCompany().equals(currentCompany)) {
                employees.add(emp);
            }
        }

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.add(new JLabel("Επιλέξτε εργαζόμενους:"));
        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (Employee emp : employees) {
            JCheckBox cb = new JCheckBox(emp.getName());
            checkBoxes.add(cb);
            listPanel.add(cb);
        }
        JScrollPane scrollPane = new JScrollPane(listPanel);
        workerPanel.add(scrollPane, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Ολοκλήρωση");
        confirmButton.addActionListener(e -> {
            List<Employee> selectedEmployees = new ArrayList<>();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    selectedEmployees.add(employees.get(i));
                }
            }
            // You can implement schedule creation logic here using: selectedDate, currentCompany, selectedEmployees
            JOptionPane.showMessageDialog(this,
                    "Επιλέξατε: " + selectedDate + "\nΕργαζόμενοι: " +
                            (selectedEmployees.isEmpty() ? "Κανένας" :
                                    String.join(", ", selectedEmployees.stream().map(Employee::getName).toArray(String[]::new)))
            );
        });
        JPanel southPanel = new JPanel();
        southPanel.add(confirmButton);

        workerPanel.add(southPanel, BorderLayout.SOUTH);

        mainPanel.add(workerPanel, "WORKER_SELECTION");
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "WORKER_SELECTION");
        revalidate();
        repaint();
    }
}