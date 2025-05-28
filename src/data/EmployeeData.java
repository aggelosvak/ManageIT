package data;

import employee.Employee;
import company.Company;
import model.JobPosition;
import java.util.Arrays;
import java.util.List;

public class EmployeeData {

    // Get example companies and job positions from data classes
    private static final List<Company> companies = CompanyData.getCompanies();
    private static final List<JobPosition> jobPositions = JobPositionData.all();

    // Example static list of employees, now including job positions
    private static final List<Employee> employees = Arrays.asList(
            new Employee(1, "John Doe", 120, 15, companies.get(0), 20, 54000.0, jobPositions.get(0)), // Cafeteria Server
            new Employee(2, "Jane Smith", 90, 10, companies.get(1), 18, 47000.0, jobPositions.get(1)), // Cafeteria Cook
            new Employee(3, "Emily Davis", 150, 20, companies.get(0), 25, 60000.0, jobPositions.get(1)), // Cafeteria Cook
            new Employee(4, "Mike Brown", 80, 8, companies.get(2), 12, 45000.0, jobPositions.get(0)) // Cafeteria Server
    );

    // Get all employees
    public static List<Employee> getEmployees() {
        return employees;
    }

    // Find an employee by ID
    public static Employee getEmployeeById(int employeeId) {
        return employees.stream()
                .filter(e -> e.getEmployeeId() == employeeId)
                .findFirst()
                .orElse(null);
    }
}