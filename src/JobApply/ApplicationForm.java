package JobApply;

import data.ManagerData;
import model.JobListing;
import model.JobApplication;
import notification.Notification;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class ApplicationForm extends JPanel {
    private final JTextArea coverLetterArea;
    private final JTextArea moreInfoArea;

    private final User user;
    private final JobListing jobListing;

    public ApplicationForm(User user, JobListing jobListing) {
        this.user = user;
        this.jobListing = jobListing;

        setLayout(new BorderLayout(8, 8));

        JLabel jobInfo = new JLabel("Applying for: " + jobListing.getJobPosition().getTitle());
        add(jobInfo, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel coverPanel = new JPanel(new BorderLayout(5, 5));
        coverPanel.add(new JLabel("Cover Letter:"), BorderLayout.NORTH);
        coverLetterArea = new JTextArea(6, 30);
        coverPanel.add(new JScrollPane(coverLetterArea), BorderLayout.CENTER);

        JPanel morePanel = new JPanel(new BorderLayout(5, 5));
        morePanel.add(new JLabel("More Info:"), BorderLayout.NORTH);
        moreInfoArea = new JTextArea(4, 30);
        morePanel.add(new JScrollPane(moreInfoArea), BorderLayout.CENTER);

        centerPanel.add(coverPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(morePanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    // Call this method when the form is submitted to process the application
    public void submitApplication() {
        String coverLetter = getCoverLetter();
        String moreInfo = getMoreInfo();

        // Create JobApplication object
        JobApplication jobApplication = new JobApplication(
                0, // Use real ID if you have one
                user,
                jobListing,
                coverLetter,
                moreInfo
        );
        jobApplication.setCompleted(true);

        // Print application info
        System.out.println("### Job Application Created ###");
        System.out.println(jobApplication);

        // Create Notification for the manager
        int managerId = jobListing.getManagerId();
        String notificationTitle = "Νέα αίτηση για τη θέση " + jobListing.getJobPosition().getTitle();
        String notificationMessage = "Ο χρήστης " + user.getName() + " υπέβαλε αίτηση.";

        Notification notification = new Notification(
                0, // Use real notification ID if available
                LocalDateTime.now(),
                managerId,
                notificationTitle,
                notificationMessage,
                "job_application",
                "normal"
        );

        // Print notification info
        notification.displayNotification();

        // Optionally: print info about the receiving manager
        var manager = ManagerData.getById(managerId);
        if (manager != null) {
            System.out.println("Notification sent to manager: " + manager.getName() +
                    " (" + manager.getEmail() + ", " + manager.getDepartment() + ")");
        }
    }

    public String getCoverLetter() {
        return coverLetterArea.getText();
    }

    public String getMoreInfo() {
        return moreInfoArea.getText();
    }
}