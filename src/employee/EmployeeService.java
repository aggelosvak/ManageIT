package employee;

import employee.Employee;
import company.Company;
import model.JobPosition;
import data.CompanyData;
import data.JobPositionData;

import java.util.List;

public class EmployeeService {
    public List<Employee> getAvailableEmployees() {
        List<Company> companies = CompanyData.getCompanies();
        List<JobPosition> jobPositions = JobPositionData.all();

        // Assign companies and job positions to employees
        return List.of(
                new Employee(1, "John Doe", companies.get(0), jobPositions.get(0)),
                new Employee(2, "Jane Smith", companies.get(1), jobPositions.get(1)),
                new Employee(3, "Emily Davis", companies.get(2), jobPositions.get(1))
        );
    }
}