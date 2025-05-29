package pages;

import notification.NotificationService;

import javax.swing.*;
import java.awt.*;

public class ErrorReportPage extends JPanel {

    private JTextArea errorDetailsArea;

    public ErrorReportPage(NotificationService notificationService) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Αναφορά Σφάλματος");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title);

        // Details Text Area
        errorDetailsArea = new JTextArea(5, 30);
        add(new JLabel("Περιγράψτε το σφάλμα:"));
        add(new JScrollPane(errorDetailsArea));

        // Submit Button
        JButton submitButton = new JButton("Υποβολή");
        submitButton.addActionListener(e -> submitError(notificationService));
        add(submitButton);
    }

    private void submitError(NotificationService notificationService) {
        String details = errorDetailsArea.getText();
        if (details.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Περιγράψτε το σφάλμα.");
            return;
        }

        notificationService.notify("Αναφορά Σφάλματος", details);
        JOptionPane.showMessageDialog(this, "Η αναφορά σας καταχωρήθηκε.");
    }
}