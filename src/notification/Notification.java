package notification;

import java.time.LocalDateTime;

public class Notification {
    private int id;
    private LocalDateTime timestamp;
    private int receiverId;
    private String title;
    private String message;
    private String type;
    private String priority;

    public Notification(int id, LocalDateTime timestamp, int receiverId, String title, String message, String type, String priority) {
        this.id = id;
        this.timestamp = timestamp;
        this.receiverId = receiverId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.priority = priority;
    }

    public void displayNotification() {
        System.out.println("### Notification ###");
        System.out.println("Notification ID: " + id);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Receiver ID: " + receiverId);
        System.out.println("Title: " + title);
        System.out.println("Message: " + message);
        System.out.println("Type: " + type);
        System.out.println("Priority: " + priority);
    }
}