package CreateSchedule;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import company.Company;
import data.EmployeeData;
import employee.Employee;
import notification.Notification;

public class CreateSchedule extends JFrame {
    private final Company currentCompany;
    private JPanel mainPanel;
    private JComboBox<Integer> yearBox, monthBox, dayBox;
    private LocalDate selectedDate;

    private List<Employee> employees;
    private List<JCheckBox> checkBoxes;
    private List<Employee> selectedEmployees;

    public CreateSchedule(Company company) {
        this.currentCompany = company;
        selectedEmployees = new ArrayList<>();

        setTitle("Δημιουργία Προγράμματος");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(buildDateSelectionPanel(), "DATE");
        setContentPane(mainPanel);
    }

    private JPanel buildDateSelectionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel instruction = new JLabel("Επιλέξτε την ημέρα που επιθυμείτε:");
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instruction);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel datePanel = new JPanel();
        int currentYear = LocalDate.now().getYear();
        yearBox = new JComboBox<>();
        for (int i = currentYear - 5; i <= currentYear + 5; i++) yearBox.addItem(i);
        monthBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) monthBox.addItem(i);
        dayBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) dayBox.addItem(i);

        datePanel.add(new JLabel("Έτος:"));
        datePanel.add(yearBox);
        datePanel.add(new JLabel("Μήνας:"));
        datePanel.add(monthBox);
        datePanel.add(new JLabel("Ημέρα:"));
        datePanel.add(dayBox);

        panel.add(datePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton nextBtn = new JButton("Επόμενο");
        nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextBtn.addActionListener(e -> {
            int year = (Integer) yearBox.getSelectedItem();
            int month = (Integer) monthBox.getSelectedItem();
            int day = (Integer) dayBox.getSelectedItem();
            try {
                selectedDate = LocalDate.of(year, month, day);
                showEmployeeSelectionPanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Μη έγκυρη ημερομηνία.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(nextBtn);
        return panel;
    }

    private void showEmployeeSelectionPanel() {
        JPanel employPanel = new JPanel(new BorderLayout());
        JLabel dateLabel = new JLabel("Επιλεγμένη Ημερομηνία: " + selectedDate, SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        employPanel.add(dateLabel, BorderLayout.NORTH);

        employees = new ArrayList<>();
        for (Employee emp : EmployeeData.getEmployees()) {
            if (emp.getCompany() != null && emp.getCompany().equals(currentCompany)) {
                employees.add(emp);
            }
        }

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.add(new JLabel("Επιλέξτε εργαζόμενους:"));
        checkBoxes = new ArrayList<>();
        for (Employee emp : employees) {
            String pos = emp.getJobPosition() != null && emp.getJobPosition().getTitle() != null
                    ? emp.getJobPosition().getTitle() : "Χωρίς θέση";
            JCheckBox cb = new JCheckBox(emp.getName() + " (" + pos + ")");
            checkBoxes.add(cb);
            listPanel.add(cb);
        }
        JScrollPane scrollPane = new JScrollPane(listPanel);
        employPanel.add(scrollPane, BorderLayout.CENTER);

        JButton preSaveBtn = new JButton("Ολοκλήρωση");
        preSaveBtn.addActionListener(e -> onPreSaveCheck());

        JPanel southPanel = new JPanel();
        southPanel.add(preSaveBtn);
        employPanel.add(southPanel, BorderLayout.SOUTH);

        mainPanel.add(employPanel, "EMPLOYEES");
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "EMPLOYEES");
        revalidate();
        repaint();
    }

    private void onPreSaveCheck() {
        selectedEmployees.clear();
        Set<String> coveredPositions = new HashSet<>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                Employee emp = employees.get(i);
                selectedEmployees.add(emp);
                if (emp.getJobPosition() != null && emp.getJobPosition().getTitle() != null) {
                    coveredPositions.add(emp.getJobPosition().getTitle());
                }
            }
        }

        Set<String> requiredPositions = new HashSet<>();
        for (Employee emp : employees) {
            if (emp.getJobPosition() != null && emp.getJobPosition().getTitle() != null)
                requiredPositions.add(emp.getJobPosition().getTitle());
        }
        requiredPositions.remove("Χωρίς θέση");

        Set<String> uncovered = new HashSet<>(requiredPositions);
        uncovered.removeAll(coveredPositions);

        if (!uncovered.isEmpty()) {
            String message = "Προσοχή!\nΔεν έχουν επιλεγεί εργαζόμενοι για τα εξής πόστα:\n" +
                    String.join(", ", uncovered) +
                    "\n\nΘέλετε να συνεχίσετε ή να επιστρέψετε;";
            int result = JOptionPane.showOptionDialog(this, message, "Προειδοποίηση",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, new String[]{"Συνέχεια", "Επιστροφή"}, "Επιστροφή");
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }
        showSavePanel();
    }

    private void showSavePanel() {
        JPanel savePanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Έτοιμο για αποθήκευση. Επιβεβαιώστε για να προχωρήσετε.", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        savePanel.add(label, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Αποθήκευση");
        saveBtn.addActionListener(e -> onSaveSchedule());
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveBtn);
        savePanel.add(btnPanel, BorderLayout.SOUTH);

        mainPanel.add(savePanel, "SAVE");
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "SAVE");
        revalidate();
        repaint();
    }

    private void onSaveSchedule() {
        // TODO: Replace with actual DB save if needed

        int notifIdBase = (int) (System.currentTimeMillis() / 1000);
        for (int i = 0; i < selectedEmployees.size(); i++) {
            Employee emp = selectedEmployees.get(i);
            Notification notif = new Notification(
                    notifIdBase + i,
                    LocalDateTime.now(),
                    emp.getEmployeeId(),
                    "Νέα Βάρδια",
                    "Έχετε προγραμματιστεί για την ημέρα: " + selectedDate,
                    "Schedule",
                    "High"
            );
            notif.displayNotification();
        }
        JOptionPane.showMessageDialog(this, "Το πρόγραμμα αποθηκεύτηκε και εστάλησαν ειδοποιήσεις!");
        dispose();
    }
}