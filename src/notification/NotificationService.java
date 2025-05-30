package notification;

import model.Review; // <-- Add this import
import java.time.LocalDateTime;

public class NotificationService {

    // Method to notify an employee about their review submission
    public void notifyEmployeeReview(String employeeId, Review review) {
        // Create a notification message for the employee
        String notificationMessage = generateNotificationMessage(review);

        // Create a Notification object
        Notification notification = new Notification(
                generateNotificationId(),            // Unique Notification ID
                LocalDateTime.now(),                 // Current timestamp
                Integer.parseInt(employeeId),        // Assuming employeeId is numeric
                "New Review Submitted",              // Notification title
                notificationMessage,                 // Notification content
                "INFO",                              // Type of notification
                "High"                               // Priority
        );

        // Simulate sending the notification
        sendNotification(notification);

        System.out.println("Notification sent to Employee (ID: " + employeeId + "): " + notificationMessage);
    }

    // Helper method to generate a notification message
    private String generateNotificationMessage(Review review) {
        return "Your performance review has been submitted with the following details: " +
                "\nReviewer ID: " + review.getReviewerId() +
                "\nDate: " + review.getReviewDate() +
                "\nRatings: " + review.getRatings();
    }

    public void sendNotification(Notification notification) {
        // Implementation for sending notifications, e.g., save to database, log, or display.
        notification.displayNotification(); // Simple print simulation
    }

    private int generateNotificationId() {
        return (int) (Math.random() * 100000);
    }

    public void notifyManager(int employeeId, String notificationTitle, String notificationMessage) {
        System.out.println("Notification sent to manager:");
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Title: " + notificationTitle);
        System.out.println("Message: " + notificationMessage);
    }

    public void notify(String title, String message) {
        System.out.println("Notification Title: " + title);
        System.out.println("Message: " + message);
    }
}