package pages;

import employee.Employee;
import workhours.WorkHoursTrackingService;

import javax.swing.*;
import java.awt.*;

public class WorkHoursPage extends JPanel {

    public WorkHoursPage(Employee employee, WorkHoursTrackingService workHoursTrackingService) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Work Hours Tracking", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title);

        JButton arrivalButton = new JButton("Record Arrival");
        arrivalButton.addActionListener(e -> {
            workHoursTrackingService.recordArrival(employee.getEmployeeId());
            JOptionPane.showMessageDialog(this, "Arrival time recorded!");
        });

        JButton breakButton = new JButton("Start Break");
        breakButton.addActionListener(e -> {
            workHoursTrackingService.startBreak(employee.getEmployeeId());
            JOptionPane.showMessageDialog(this, "Break started!");
        });

        JButton resumeButton = new JButton("Resume Work");
        resumeButton.addActionListener(e -> {
            workHoursTrackingService.resumeWork(employee.getEmployeeId());
            JOptionPane.showMessageDialog(this, "Break stopped and work resumed!");
        });


        JButton exitButton = new JButton("Record Exit");
        exitButton.addActionListener(e -> {
            workHoursTrackingService.recordExit(employee.getEmployeeId());
            JOptionPane.showMessageDialog(this, "Exit time recorded!");
        });

        add(arrivalButton);
        add(Box.createRigidArea(new Dimension(0, 5))); // Add some spacing
        add(breakButton);
        add(Box.createRigidArea(new Dimension(0, 5))); // Add some spacing
        add(resumeButton);
        add(Box.createRigidArea(new Dimension(0, 5))); // Add some spacing
        add(exitButton);
    }
}