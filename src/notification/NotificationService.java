package notification;

import review.Review;

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

    // Simulate sending a notification (this could later involve an email system, notification queue, etc.)
    public void sendNotification(Notification notification) {
        // Here you might send the notification to a database, an API, or a messaging system.
        // For now, we'll just print it to simulate the sending process.
        notification.displayNotification(); // Print the notification details
    }

    // Generate a unique Notification ID (for simplicity, just using a random number in this example)
    private int generateNotificationId() {
        return (int) (Math.random() * 100000); // Example random ID generator
    }

    public void notifyManager(int employeeId, String notificationTitle, String notificationMessage) {
        // Example logic for sending a notification to the manager
        System.out.println("Notification sent to manager:");
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Title: " + notificationTitle);
        System.out.println("Message: " + notificationMessage);

        // Logic to send the notification (e.g., email, system alert, etc.)
    }
}

