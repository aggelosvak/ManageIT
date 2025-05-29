package pages;

import employee.Employee;
import employee.EmployeeService;
import notification.NotificationService;

import javax.swing.*;
import java.awt.*;

public class ReportsOptionsPage extends JPanel {

    public ReportsOptionsPage(Employee employee, EmployeeService employeeService, NotificationService notificationService) {
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton colleagueReportButton = new JButton("Αναφορά Ατόμου στο περιβάλλον εργασίας");
        colleagueReportButton.addActionListener(e -> showColleagueReportPage(employee, employeeService));
        add(colleagueReportButton);

        JButton errorReportButton = new JButton("Αναφορά Σφάλματος");
        errorReportButton.addActionListener(e -> showErrorReportPage(notificationService));
        add(errorReportButton);
    }

    private void showColleagueReportPage(Employee employee, EmployeeService employeeService) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ColleagueReportPage(employee, employeeService));
        frame.revalidate();
        frame.repaint();
    }

    private void showErrorReportPage(NotificationService notificationService) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ErrorReportPage(notificationService));
        frame.revalidate();
        frame.repaint();
    }
}