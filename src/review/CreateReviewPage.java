package review;

import employee.Employee;
import manager.Manager;
import model.Review;
import data.EmployeeData;
import data.ManagerData;
import notification.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateReviewPage extends JFrame {
    private JComboBox<Employee> employeeComboBox;
    private JTextArea descriptionArea;
    private Map<String, JComboBox<Integer>> ratingsBoxes;
    private JPanel criteriaPanel, descriptionPanel, submitPanel;

    // Assume the first manager as the reviewer
    private final Manager assumedManager;

    public CreateReviewPage() {
        setTitle("Δημιουργία Αξιολόγησης");
        setSize(420, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Manager> managers = ManagerData.all();
        this.assumedManager = managers.isEmpty() ? null : managers.get(0);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Employee selection
        panel.add(new JLabel("Επιλέξτε Εργαζόμενο:"));
        List<Employee> employees = EmployeeData.getEmployees();
        employeeComboBox = new JComboBox<>(employees.toArray(new Employee[0]));
        employeeComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Employee) {
                    setText(((Employee) value).getName());
                }
                return this;
            }
        });
        panel.add(employeeComboBox);

        // Criteria (initially disabled)
        criteriaPanel = new JPanel();
        criteriaPanel.setLayout(new BoxLayout(criteriaPanel, BoxLayout.Y_AXIS));
        criteriaPanel.add(new JLabel("Κριτήρια:"));
        ratingsBoxes = new HashMap<>();
        for (String criterion : getReviewCriteria()) {
            JPanel critPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel(criterion + ":");
            JComboBox<Integer> box = new JComboBox<>(new Integer[]{1,2,3,4,5});
            box.setEnabled(false);
            critPanel.add(label);
            critPanel.add(box);
            ratingsBoxes.put(criterion, box);
            criteriaPanel.add(critPanel);
        }
        panel.add(criteriaPanel);

        // Description (initially disabled)
        descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.add(new JLabel("Περιγραφή:"));
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        descriptionPanel.add(scrollPane);
        panel.add(descriptionPanel);

        // Submit button (initially disabled)
        submitPanel = new JPanel();
        JButton submitBtn = new JButton("Υποβολή Κριτικής");
        submitBtn.setEnabled(false);
        submitBtn.addActionListener(this::handleSubmit);
        submitPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        submitPanel.add(submitBtn);
        panel.add(submitPanel);

        setContentPane(panel);

        // Enable controls after employee selected
        employeeComboBox.addActionListener(ae -> {
            boolean enabled = employeeComboBox.getSelectedItem() != null;
            for (JComboBox<Integer> box : ratingsBoxes.values()) box.setEnabled(enabled);
            descriptionArea.setEnabled(enabled);
            submitBtn.setEnabled(enabled);
        });
    }

    private void handleSubmit(ActionEvent e) {
        Employee employee = (Employee) employeeComboBox.getSelectedItem();
        Manager manager = this.assumedManager;
        String description = descriptionArea.getText().trim();

        if (employee == null || manager == null) {
            JOptionPane.showMessageDialog(this,
                    "Πρέπει να επιλέξετε εργαζόμενο. Ο διαχειριστής δεν βρέθηκε.",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, Integer> ratings = new HashMap<>();
        for (Map.Entry<String, JComboBox<Integer>> entry : ratingsBoxes.entrySet()) {
            ratings.put(entry.getKey(), (Integer) entry.getValue().getSelectedItem());
        }

        if (ratings.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Πρέπει να εισαγάγετε τουλάχιστον ένα κριτήριο.",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Η περιγραφή δεν μπορεί να είναι κενή.",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Review review = new Review(
                employee,
                manager,
                new Timestamp(System.currentTimeMillis()),
                ratings,
                description
        );
        System.out.println("=== ΝΕΑ ΑΞΙΟΛΟΓΗΣΗ ===");
        System.out.println("Employee: " + employee.getName() + " (ID: " + employee.getEmployeeId() + ")");
        System.out.println("Manager: " + manager.getName() + " (ID: " + manager.getId() + ")");
        System.out.println("Ratings: " + ratings);
        System.out.println("Description: " + description);
        System.out.println("Time: " + review.getReviewDate());
        System.out.println("=====================");

        Notification notification = new Notification(
                (int) (Math.random()*100000),
                LocalDateTime.now(),
                employee.getEmployeeId(),
                "Νέα Αξιολόγηση",
                "Μια νέα αξιολόγηση προστέθηκε από τον υπεύθυνο " + manager.getName(),
                "Review",
                "Normal"
        );
        notification.displayNotification();

        JOptionPane.showMessageDialog(this,
                "Η κριτική υποβλήθηκε και ειδοποιήθηκε ο εργαζόμενος!",
                "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private List<String> getReviewCriteria() {
        return java.util.Arrays.asList("Απόδοση", "Συνεργασία", "Επικοινωνία");
    }
}