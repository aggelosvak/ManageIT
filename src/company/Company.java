package company;

import employee.Employee;

public class Company {
    private int id;
    private String name;
    private String address;
    private int workerId;     // Unique worker/employee ID
    private int roomQuantity; // Number of rooms in the company
    private int scheduleId;   // Unique schedule ID
    private int postId;       // Unique post ID

    // Constructor
    public Company(int id, String name, String address, int workerId, int roomQuantity, int scheduleId, int postId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.workerId = workerId;
        this.roomQuantity = roomQuantity;
        this.scheduleId = scheduleId;
        this.postId = postId;
    }

    // Getters and Setters for all attributes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    // Method to display company information
    public void displayInfo() {
        System.out.println("Company ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Worker ID: " + workerId);
        System.out.println("Room Quantity: " + roomQuantity);
        System.out.println("Schedule ID: " + scheduleId);
        System.out.println("Post ID: " + postId);
    }
    // Updated getCompanyId method
    public int getCompanyId() {
        return this.id;
    }

}