package meeting;

import company.Company;
import employee.Employee;
import data.EmployeeData;
import data.RoomData;
import data.MeetingData;
import manager.Manager;
import data.ManagerData;
import model.Meeting;
import model.Room;
import notification.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MeetingPage extends JFrame {
    private final Company company;
    private final List<Employee> employees;

    public MeetingPage(Company company) {
        this.company = company;
        this.employees = EmployeeData.getEmployees().stream()
                .filter(emp -> emp.getCompany().getId() == company.getId())
                .collect(Collectors.toList());

        setTitle("Φόρμα Σύσκεψης");
        setSize(520, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Δημιουργία Σύσκεψης", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        // Meeting Title
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Τίτλος:"), gbc);
        JTextField meetingTitleField = new JTextField();
        gbc.gridx = 1;
        panel.add(meetingTitleField, gbc);

        // Date Picker using JSpinner
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Ημερομηνία:"), gbc);
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        gbc.gridx = 1;
        panel.add(dateSpinner, gbc);

        // Time Picker using JSpinner
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Ώρα:"), gbc);
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));
        gbc.gridx = 1;
        panel.add(timeSpinner, gbc);

        // Participants (with custom renderer for names)
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Συμμετέχοντες:"), gbc);

        DefaultListModel<Employee> empModel = new DefaultListModel<>();
        for (Employee emp : employees) empModel.addElement(emp);
        JList<Employee> employeeList = new JList<>(empModel);
        employeeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        employeeList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getName());
            if (isSelected) label.setBackground(list.getSelectionBackground());
            if (isSelected) label.setForeground(list.getSelectionForeground());
            label.setOpaque(true);
            return label;
        });
        JScrollPane scrollPane = new JScrollPane(employeeList);
        scrollPane.setPreferredSize(new Dimension(220, 80));
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Περιγραφή:"), gbc);
        JTextArea descriptionArea = new JTextArea(3, 20);
        gbc.gridx = 1;
        panel.add(new JScrollPane(descriptionArea), gbc);

        // Location
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Τοποθεσία:"), gbc);
        String[] locations = {"Αίθουσα", "Remote"};
        JComboBox<String> locationBox = new JComboBox<>(locations);
        gbc.gridx = 1;
        panel.add(locationBox, gbc);

        // Room components (hidden unless "Αίθουσα" is selected)
        JLabel roomLabel = new JLabel("Αίθουσα:");
        JComboBox<String> roomComboBox = new JComboBox<>();
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(roomLabel, gbc);
        gbc.gridx = 1;
        panel.add(roomComboBox, gbc);
        roomLabel.setVisible(false);
        roomComboBox.setVisible(false);

        // Submit button - declare before listeners to prevent reference errors!
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        JButton submitBtn = new JButton("Δημιουργία");
        panel.add(submitBtn, gbc);

        // Location selection logic
        locationBox.addActionListener(evt -> {
            String location = (String) locationBox.getSelectedItem();
            if ("Αίθουσα".equals(location)) {
                // Get available rooms for this company
                List<Room> availableRooms = RoomData.getAvailableRooms(company.getId());
                roomComboBox.removeAllItems();
                for (Room r : availableRooms) {
                    roomComboBox.addItem(r.getName() + " (ID:" + r.getId() + ")");
                }
                roomLabel.setVisible(true);
                roomComboBox.setVisible(true);
                submitBtn.setEnabled(!availableRooms.isEmpty());
                if (availableRooms.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Δεν υπάρχουν διαθέσιμες αίθουσες!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                roomLabel.setVisible(false);
                roomComboBox.setVisible(false);
                submitBtn.setEnabled(true);
            }
        });

        // Submit button logic
        submitBtn.addActionListener((ActionEvent e) -> {
            String title = meetingTitleField.getText().trim();
            Date date = (Date) dateSpinner.getValue();
            Date time = (Date) timeSpinner.getValue();
            String desc = descriptionArea.getText().trim();
            String location = (String) locationBox.getSelectedItem();
            List<Employee> selected = employeeList.getSelectedValuesList();

            if (title.isEmpty() || selected.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Συμπληρώστε όλα τα υποχρεωτικά πεδία!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Room selectedRoom = null;
            if ("Αίθουσα".equals(location)) {
                int roomIdx = roomComboBox.getSelectedIndex();
                List<Room> availableRooms = RoomData.getAvailableRooms(company.getId());
                if (roomIdx < 0 || availableRooms.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Δεν έχει επιλεγεί διαθέσιμη αίθουσα!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                selectedRoom = availableRooms.get(roomIdx);
            }

            // Find manager for this company (optional)
            Manager manager = null;
            List<Employee> empInCompany = EmployeeData.getEmployees().stream()
                    .filter(emp -> emp.getCompany().getId() == company.getId())
                    .collect(Collectors.toList());
            for (Employee emp : empInCompany) {
                if (emp.getJobPosition() != null && emp.getJobPosition().getTitle().equalsIgnoreCase("Manager")) {
                    manager = ManagerData.getById(emp.getEmployeeId());
                    break;
                }
            }

            // Create Meeting object
            int meetingId = MeetingData.getNextId();
            Meeting meeting = new Meeting(meetingId, title, date, time, desc, company, manager, selected, selectedRoom);

            // Room handling
            if (selectedRoom != null) {
                RoomData.setRoomAvailability(selectedRoom.getId(), false);
            }

            // Save meeting (to MeetingData, or similar persistent layer)
            MeetingData.saveMeeting(meeting);

            // Notify participants
            for (Employee participant : selected) {
                Notification notification = new Notification(
                        100000 + meetingId * 10 + participant.getEmployeeId(),
                        LocalDateTime.now(),
                        participant.getEmployeeId(),
                        "Πρόσκληση Σύσκεψης",
                        "Έχετε προστεθεί σε νέα σύσκεψη: " + title,
                        "Meeting",
                        "Normal"
                );
                notification.displayNotification(); // For real notification logic
                System.out.println(notification);   // Print notification object
            }

            // Print meeting object (as requested)
            System.out.println(meeting);

            // Confirmation dialog
            JOptionPane.showMessageDialog(this,
                    "Επιβεβαίωση Σύσκεψης!\n"
                            + "Τίτλος: " + title + "\n"
                            + "Ημερομηνία: " + new SimpleDateFormat("yyyy-MM-dd").format(date) + "\n"
                            + "Ώρα: " + new SimpleDateFormat("HH:mm").format(time),
                    "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);

            dispose();
        });

        // Ensure room selection is updated on first show
        locationBox.setSelectedIndex(0);

        add(panel);
    }
}