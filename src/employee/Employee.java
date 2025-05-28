package employee;

import company.Company;

public class Employee {

    // Attributes
    private String employeeId;
    private String name;
    private int points;
    private int leaveBalance;
    private Company company;      // Use Company object instead of int companyId
    private int leaveSum;
    private double salary;

    // Constructor with employeeId and name
    public Employee(String employeeId, String name, Company company) {
        this.employeeId = employeeId;
        this.name = name;
        this.company = company;
    }

    // Full Constructor (add Company parameter)
    public Employee(String employeeId, String name, int points, int leaveBalance, Company company, int leaveSum, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.points = points;
        this.leaveBalance = leaveBalance;
        this.company = company;
        this.leaveSum = leaveSum;
        this.salary = salary;
    }

    public Employee() {}

    // ... other getters and setters

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