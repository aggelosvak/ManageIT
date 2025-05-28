package employee;

import company.Company;
import model.JobPosition;

public class Employee {

    // Attributes
    private int employeeId;
    private String name;
    private int points;
    private int leaveBalance;
    private Company company;
    private int leaveSum;
    private double salary;
    private JobPosition jobPosition; // New attribute

    // Constructor with employeeId, name and jobPosition
    public Employee(int employeeId, String name, Company company, JobPosition jobPosition) {
        this.employeeId = employeeId;
        this.name = name;
        this.company = company;
        this.jobPosition = jobPosition;
    }

    // Full Constructor with jobPosition
    public Employee(int employeeId, String name, int points, int leaveBalance, Company company, int leaveSum, double salary, JobPosition jobPosition) {
        this.employeeId = employeeId;
        this.name = name;
        this.points = points;
        this.leaveBalance = leaveBalance;
        this.company = company;
        this.leaveSum = leaveSum;
        this.salary = salary;
        this.jobPosition = jobPosition;
    }

    // Constructor without jobPosition (for backward compatibility)
    public Employee(int employeeId, String name, int points, int leaveBalance, Company company, int leaveSum, double salary) {
        this(employeeId, name, points, leaveBalance, company, leaveSum, salary, null);
    }

    public Employee() {}

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }
}