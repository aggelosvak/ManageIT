package model;

public class JobPosition {
    public int id;
    private String title;
    private String description;
    private boolean available;
    private double salary;

    public JobPosition(int id, String title, String description, boolean available, double salary) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.available = available;
        this.salary = salary;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isAvailable() { return available; }
    public double getSalary() { return salary; }

    // Setters
    public void setAvailable(boolean available) { this.available = available; }
    public void setSalary(double salary) { this.salary = salary; }

    // Converts the job position to a CSV line
    public String toCSV() {
        return id + "," + title + "," + description + "," + available + "," + salary;
    }

    // Creates a JobPosition object from a CSV line
    public static JobPosition fromCSV(String line) {
        String[] tokens = line.split(",", -1);
        int id = Integer.parseInt(tokens[0]);
        String title = tokens[1];
        String description = tokens[2];
        boolean available = Boolean.parseBoolean(tokens[3]);
        double salary = Double.parseDouble(tokens[4]);
        return new JobPosition(id, title, description, available, salary);
    }



}