package employee;

import data.EmployeeData;
import company.Company;
import model.JobPosition;
import data.CompanyData;
import data.JobPositionData;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    // Get all employees who are available along with their companies and job positions
    public List<Employee> getAvailableEmployees() {
        // Fetch the companies list (does not create new companies)
        List<Company> companies = CompanyData.getCompanies();
        List<JobPosition> jobPositions = JobPositionData.all();

        // Assign companies and job positions to employees
        return List.of(
                new Employee(1, "John Doe", companies.get(0), jobPositions.get(0)),
                new Employee(2, "Jane Smith", companies.get(1), jobPositions.get(1)),
                new Employee(3, "Emily Davis", companies.get(2), jobPositions.get(1))
        );
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        // Fetch all employees directly from EmployeeData
        return EmployeeData.getEmployees();
    }

    // Get the manager for a specific company by company ID
    public Employee getManager(int companyId) {
        List<Employee> employees = EmployeeData.getEmployees();
        for (Employee employee : employees) {
            if (employee.getCompanyId() == companyId &&
                    employee.getJobPosition() != null &&
                    employee.getJobPosition().getTitle().equalsIgnoreCase("Manager")) {
                return employee;
            }
        }
        return null; // No manager found for the given companyId
    }

    // Get colleagues of a specific employee within the same company
    public List<Employee> getColleagues(int employeeId) {
        Employee currentEmployee = EmployeeData.getEmployeeById(employeeId);
        if (currentEmployee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }

        int companyId = currentEmployee.getCompanyId(); // Use the updated method
        List<Employee> colleagues = new ArrayList<>();
        for (Employee employee : EmployeeData.getEmployees()) {
            if (employee.getCompanyId() == companyId && employee.getEmployeeId() != employeeId) {
                colleagues.add(employee);
            }
        }
        return colleagues;
    }

    // Check if a given employee exists in the system
    public boolean isEmployee(Employee employee) {
        return EmployeeData.getEmployees().stream()
                .anyMatch(emp -> emp.getEmployeeId() == employee.getEmployeeId());
    }
}