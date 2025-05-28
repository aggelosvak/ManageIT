package CreateSchedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import company.Company;
import data.EmployeeData;
import employee.Employee;

public class CreateSchedule extends JFrame {
    private final Company currentCompany;
    private final JTextArea employeesArea = new JTextArea(10, 35);

    public CreateSchedule(Company company) {
        this.currentCompany = company;

        setTitle("Δημιουργία Προγράμματος");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel instruction = new JLabel("Επιλέξτε την ημέρα που επιθυμείτε:");
        instruction.setHorizontalAlignment(SwingConstants.CENTER);

        int currentYear = LocalDate.now().getYear();
        JComboBox<Integer> yearBox = new JComboBox<>();
        for (int i = currentYear - 5; i <= currentYear + 5; i++) yearBox.addItem(i);
        JComboBox<Integer> monthBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) monthBox.addItem(i);
        JComboBox<Integer> dayBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) dayBox.addItem(i);

        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Έτος:"));
        datePanel.add(yearBox);
        datePanel.add(new JLabel("Μήνας:"));
        datePanel.add(monthBox);
        datePanel.add(new JLabel("Ημέρα:"));
        datePanel.add(dayBox);

        employeesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(employeesArea);

        JButton confirmButton = new JButton("Επιβεβαίωση");
        confirmButton.addActionListener(e -> {
            int year = (Integer) yearBox.getSelectedItem();
            int month = (Integer) monthBox.getSelectedItem();
            int day = (Integer) dayBox.getSelectedItem();

            try {
                LocalDate selectedDate = LocalDate.of(year, month, day);
                JOptionPane.showMessageDialog(this, "Επιλέξατε: " + selectedDate);
                // Implement further scheduling logic here
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Μη έγκυρη ημερομηνία.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        ActionListener updateListener = e -> showEmployees();
        yearBox.addActionListener(updateListener);
        monthBox.addActionListener(updateListener);
        dayBox.addActionListener(updateListener);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        centerPanel.add(instruction);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(datePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(new JLabel("Εργαζόμενοι της εταιρείας:"));
        centerPanel.add(scrollPane);

        JPanel southPanel = new JPanel();
        southPanel.add(confirmButton);

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        showEmployees();
    }

    private void showEmployees() {
        StringBuilder sb = new StringBuilder();
        for (Employee emp : EmployeeData.getEmployees()) {
            if (emp.getCompany() != null && emp.getCompany().equals(currentCompany)) {
                sb.append(emp.getName());
                // sb.append(" - ").append(emp.getJobPosition()); // if available
                sb.append("\n");
            }
        }
        if (sb.isEmpty()) {
            employeesArea.setText("Δεν βρέθηκαν εργαζόμενοι για αυτήν την εταιρεία.");
        } else {
            employeesArea.setText(sb.toString());
        }
    }
}