package workhours;

import employee.EmployeeService;
import notification.NotificationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkHoursTrackingPage extends JFrame {

    private final WorkHoursTrackingService workHoursTrackingService;
    private final String employeeId; // Current logged-in employee ID (replace with actual login logic)

    public WorkHoursTrackingPage(EmployeeService employeeService, NotificationService notificationService, String employeeId) {
        this.workHoursTrackingService = new WorkHoursTrackingService(employeeService, notificationService);
        this.employeeId = employeeId;

        initUI();
    }

    private void initUI() {
        setTitle("Καταγραφή Εργατοωρών");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Καταγραφή Εργατοωρών");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton arrivalButton = new JButton("Καταγραφή Ώρας Άφιξης");
        JButton breakButton = new JButton("Διάλειμμα");
        JButton resumeButton = new JButton("Συνέχιση Εργασίας");
        JButton exitButton = new JButton("Καταγραφή Ώρας Εξόδου");

        // Set alignment for buttons
        arrivalButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        breakButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Action Listeners for buttons
        arrivalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleArrivalTime();
            }
        });

        breakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStartBreak();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleResumeWork();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExitTime();
            }
        });

        // Add components to the main panel
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(arrivalButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(breakButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(resumeButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(exitButton);

        // Set main panel as the content pane
        setContentPane(mainPanel);
    }

    // Handlers for button actions
    private void handleArrivalTime() {
        try {
            workHoursTrackingService.recordArrival(employeeId);
            JOptionPane.showMessageDialog(this, "Η ώρα άφιξης καταγράφηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την καταγραφή ώρας άφιξης: " + e.getMessage(),
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleStartBreak() {
        try {
            workHoursTrackingService.startBreak(employeeId);
            JOptionPane.showMessageDialog(this, "Το διάλειμμα ξεκίνησε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, "Δεν υπάρχει διαθέσιμος χρόνος για διάλειμμα.",
                    "Σφάλμα", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την έναρξη διαλείμματος: " + e.getMessage(),
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleResumeWork() {
        try {
            workHoursTrackingService.resumeWork(employeeId);
            JOptionPane.showMessageDialog(this, "Η επιστροφή στην εργασία καταγράφηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την καταγραφή επιστροφής στην εργασία: " + e.getMessage(),
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleExitTime() {
        try {
            workHoursTrackingService.recordExit(employeeId);
            JOptionPane.showMessageDialog(this, "Η ώρα εξόδου καταγράφηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την καταγραφή ώρας εξόδου: " + e.getMessage(),
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeService employeeService = new EmployeeService(); // Mock EmployeeService
            NotificationService notificationService = new NotificationService(); // Mock NotificationService
            String employeeId = "E001"; // Hardcoded logged-in employee ID for testing
            new WorkHoursTrackingPage(employeeService, notificationService, employeeId).setVisible(true);
        });
    }
}