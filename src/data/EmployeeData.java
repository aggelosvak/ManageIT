package data;

import employee.Employee;
import company.Company;
import java.util.Arrays;
import java.util.List;

public class EmployeeData {

    // Get example companies from CompanyData
    private static final List<Company> companies = CompanyData.getCompanies();

    // Example static list of employees
    private static final List<Employee> employees = Arrays.asList(
            new Employee("E001", "John Doe", 120, 15, companies.get(0), 20, 54000.0),
            new Employee("E002", "Jane Smith", 90, 10, companies.get(1), 18, 47000.0),
            new Employee("E003", "Emily Davis", 150, 20, companies.get(0), 25, 60000.0),
            new Employee("E004", "Mike Brown", 80, 8, companies.get(2), 12, 45000.0)
    );

    // Get all employees
    public static List<Employee> getEmployees() {
        return employees;
    }

    // Find an employee by ID
    public static Employee getEmployeeById(String employeeId) {
        return employees.stream()
                .filter(e -> e.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }
}