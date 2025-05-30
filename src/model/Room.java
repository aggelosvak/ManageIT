package model;

public class Room {
    private int id;
    private String name;
    private int companyId;
    private boolean available;

    public Room(int id, String name, int companyId, boolean available) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.available = available;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCompanyId() { return companyId; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}