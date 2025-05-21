package com.yourproject.service;

import java.util.List;

public class EmployeeService {

    // Get all available employees for review
    public List<Employee> getAvailableEmployees() {
        // This is just a mock response. Replace this with actual logic, like fetching from a database.
        return List.of(
                new Employee("E001", "John Doe"),
                new Employee("E002", "Jane Smith"),
                new Employee("E003", "Emily Davis")
        );
    }
}