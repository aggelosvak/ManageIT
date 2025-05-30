package model;

public class JobPosition {
    public int id;
    private String title;
    private String description;
    private boolean available;
    private double salary;
    // Optional companyId field, based on how you want to associate this position with a company.
    private int companyId;

    public JobPosition(int id, String title, String description, boolean available, double salary) {
        this(id, title, description, available, salary, -1);
    }

    public JobPosition(int id, String title, String description, boolean available, double salary, int companyId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.available = available;
        this.salary = salary;
        this.companyId = companyId;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isAvailable() { return available; }
    public double getSalary() { return salary; }
    public int getCompanyId() { return companyId; }

    // Setters
    public void setAvailable(boolean available) { this.available = available; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    // Converts the job position to a CSV line
    public String toCSV() {
        // Include companyId in CSV for completeness
        return id + "," + title + "," + description + "," + available + "," + salary + "," + companyId;
    }

    // Creates a JobPosition object from a CSV line
    public static JobPosition fromCSV(String line) {
        String[] tokens = line.split(",", -1);
        int id = Integer.parseInt(tokens[0]);
        String title = tokens[1];
        String description = tokens[2];
        boolean available = Boolean.parseBoolean(tokens[3]);
        double salary = Double.parseDouble(tokens[4]);
        int companyId = -1;
        if (tokens.length > 5 && !tokens[5].isEmpty()) {
            try {
                companyId = Integer.parseInt(tokens[5]);
            } catch (NumberFormatException ignored) { }
        }
        return new JobPosition(id, title, description, available, salary, companyId);
    }
}