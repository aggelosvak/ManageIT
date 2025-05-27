package notification;

import review.Review;

import java.time.LocalDateTime;

public class Notification {
    private int notificationId;  // Unique identifier for the notification
    private LocalDateTime timestamp; // Timestamp when the notification was created
    private int userId;          // ID of the user receiving the notification
    private String name;         // Name or title of the notification
    private String message;      // Main content or description of the notification
    private boolean isRead;      // Whether the notification has been read
    private String type;         // Type/category of the notification (e.g., "INFO", "ALERT")
    private String priority;     // Priority of the notification (e.g., "High", "Medium", "Low")

    // Constructor
    public Notification(int notificationId, LocalDateTime timestamp, int userId, String name, String message, String type, String priority) {
        this.notificationId = notificationId;
        this.timestamp = timestamp;
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.isRead = false; // Default value: not read
        this.type = type;
        this.priority = priority;
    }

    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void markAsRead() {
        this.isRead = true; // Mark the notification as read
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Method to display notification details
    public void displayNotification() {
        System.out.println("Notification ID: " + notificationId);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Message: " + message);
        System.out.println("Type: " + type);
        System.out.println("Priority: " + priority);
        System.out.println("Read Status: " + (isRead ? "Read" : "Unread"));
    }
}
