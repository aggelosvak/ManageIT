package user;

public class User {
    private int id;
    private String name;
    private String email;
    private String cvLink; // New field

    // Updated Constructor
    public User(int id, String name, String email, String cvLink) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cvLink = cvLink;
    }

    // Legacy constructor for backward compatibility
    public User(int id, String name, String email) {
        this(id, name, email, null);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCvLink() { return cvLink; }
    public void setCvLink(String cvLink) { this.cvLink = cvLink; }

    // Method to display user information
    public void displayInfo() {
        System.out.println("User ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("CV Link: " + (cvLink == null ? "Not uploaded" : cvLink));
    }
}