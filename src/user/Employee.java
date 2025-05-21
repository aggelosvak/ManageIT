package com.yourproject.model;

public class Employee {

    // Attributes
    private String employeeId;       // Unique identifier for the employee
    private int points;              // Points accrued by the employee
    private int leaveBalance;        // Remaining leave balance for the employee
    private String companyId;        // ID of the company the employee belongs to
    private int leaveSum;            // Total number of leaves taken by the employee over time

    // Constructor
    public Employee(String employeeId, int points, int leaveBalance, String companyId, int leaveSum) {
        this.employeeId = employeeId;
        this.points = points;
        this.leaveBalance = leaveBalance;
        this.companyId = companyId;
        this.leaveSum = leaveSum;
    }

    // Default Constructor
    public Employee() {
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(int leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getLeaveSum() {
        return leaveSum;
    }

    public void setLeaveSum(int leaveSum) {
        this.leaveSum = leaveSum;
    }

    // Override toString for debugging and logging purposes
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", points=" + points +
                ", leaveBalance=" + leaveBalance +
                ", companyId='" + companyId + '\'' +
                ", leaveSum=" + leaveSum +
                '}';
    }
}