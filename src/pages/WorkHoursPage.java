package pages;

import employee.Employee;
import model.Shift;
import workhours.WorkHoursTrackingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkHoursPage extends JPanel {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final Employee employee;
    private final WorkHoursTrackingService service;
    private Timer breakTimer;
    private JLabel timerLabel;
    private int breakSecondsLeft;

    public WorkHoursPage(Employee employee, WorkHoursTrackingService workHoursTrackingService) {
        this.employee = employee;
        this.service = workHoursTrackingService;
        setLayout(new BorderLayout());
        setupPages();
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupPages() {
        // Page 1: Arrival page
        JPanel arrivalPanel = new JPanel();
        arrivalPanel.setLayout(new BoxLayout(arrivalPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Work Hours Tracking", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        JButton arrivalButton = new JButton("Record Arrival");
        arrivalButton.addActionListener(e -> {
            service.recordArrival(employee.getEmployeeId());
            JOptionPane.showMessageDialog(this, "Arrival time recorded!");
            updateBreakExitPage();
            cardLayout.show(mainPanel, "breakExit");
        });
        arrivalPanel.add(title);
        arrivalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        arrivalPanel.add(arrivalButton);

        // Page 2: Break/Exit page
        JPanel breakExitPanel = new JPanel();
        breakExitPanel.setLayout(new BoxLayout(breakExitPanel, BoxLayout.Y_AXIS));
        JLabel readyLabel = new JLabel("You are clocked in.");
        JButton startBreakButton = new JButton("Start Break");
        JButton exitButton = new JButton("Record Exit");

        // Start Break action
        startBreakButton.addActionListener(e -> {
            Shift shift = service.getCurrentShift(employee.getEmployeeId());
            if (shift != null && shift.getRemainingBreakTimeMinutes() > 0) {
                service.startBreak(employee.getEmployeeId());
                this.breakSecondsLeft = shift.getRemainingBreakTimeMinutes() * 60;
                updateBreakTimerLabel();
                cardLayout.show(mainPanel, "breakTimer");
                startBreakCountdown();
            } else {
                JOptionPane.showMessageDialog(this, "No break time left.");
            }
        });
        exitButton.addActionListener(e -> {
            service.recordExit(employee.getEmployeeId());
            JOptionPane.showMessageDialog(this, "Exit time recorded!");
            cardLayout.show(mainPanel, "arrival");
        });

        breakExitPanel.add(readyLabel);
        breakExitPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        breakExitPanel.add(startBreakButton);
        breakExitPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        breakExitPanel.add(exitButton);

        // Page 3: Break timer page
        JPanel breakTimerPanel = new JPanel();
        breakTimerPanel.setLayout(new BoxLayout(breakTimerPanel, BoxLayout.Y_AXIS));
        JLabel breakLabel = new JLabel("On break");
        timerLabel = new JLabel("Time left: --:--");
        JButton endBreakButton = new JButton("End Break");
        endBreakButton.addActionListener(e -> {
            stopBreakCountdown();
            int usedSeconds = (service.getCurrentShift(employee.getEmployeeId()).getRemainingBreakTimeMinutes() * 60) - breakSecondsLeft;
            service.endBreak(employee.getEmployeeId(), usedSeconds / 60);
            JOptionPane.showMessageDialog(this, "Break ended.");
            updateBreakExitPage();
            cardLayout.show(mainPanel, "breakExit");
        });
        breakTimerPanel.add(breakLabel);
        breakTimerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        breakTimerPanel.add(timerLabel);
        breakTimerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        breakTimerPanel.add(endBreakButton);

        mainPanel.add(arrivalPanel, "arrival");
        mainPanel.add(breakExitPanel, "breakExit");
        mainPanel.add(breakTimerPanel, "breakTimer");

        cardLayout.show(mainPanel, "arrival");
    }

    // Updates for remaining break on the break/exit page if needed
    private void updateBreakExitPage() { }

    private void updateBreakTimerLabel() {
        int minutes = breakSecondsLeft / 60;
        int seconds = breakSecondsLeft % 60;
        timerLabel.setText(String.format("Time left: %02d:%02d", minutes, seconds));
    }

    private void startBreakCountdown() {
        breakTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (breakSecondsLeft > 0) {
                    breakSecondsLeft--;
                    updateBreakTimerLabel();
                } else {
                    stopBreakCountdown();
                    JOptionPane.showMessageDialog(WorkHoursPage.this, "Break time is over!");
                    service.endBreak(employee.getEmployeeId(), 0);
                    updateBreakExitPage();
                    cardLayout.show(mainPanel, "breakExit");
                }
            }
        });
        breakTimer.start();
    }

    private void stopBreakCountdown() {
        if (breakTimer != null) {
            breakTimer.stop();
            breakTimer = null;
        }
    }
}