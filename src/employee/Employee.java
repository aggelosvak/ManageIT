package employee;

public class Employee {

    // Attributes
    private String employeeId;
    private String name;
    private int points;
    private int leaveBalance;
    private String companyId;
    private int leaveSum;
    private double salary; // <-- Add this line

    // Constructor with employeeId and name
    public Employee(String employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    // Full Constructor (add salary parameter)
    public Employee(String employeeId, String name, int points, int leaveBalance, String companyId, int leaveSum, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.points = points;
        this.leaveBalance = leaveBalance;
        this.companyId = companyId;
        this.leaveSum = leaveSum;
        this.salary = salary; // <-- Set here
    }

    public Employee() {}

    // ... other getters and setters

    public double getSalary() { // <-- Getter
        return salary;
    }

    public void setSalary(double salary) { // <-- Setter
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public int getPoints() {

        return points;
    }

    public void setPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative.");
        }
        this.points = points;
    }

}