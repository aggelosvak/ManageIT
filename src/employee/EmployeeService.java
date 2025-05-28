package employee;

import employee.Employee;
import company.Company;
import data.CompanyData;
import java.util.List;

public class EmployeeService {
    public List<Employee> getAvailableEmployees() {
        // Fetch the companies list (does not create new companies)
        List<Company> companies = CompanyData.getCompanies();

        // Assign companies to employees as needed
        return List.of(
                new Employee("E001", "John Doe", companies.get(0)),
                new Employee("E002", "Jane Smith", companies.get(1)),
                new Employee("E003", "Emily Davis", companies.get(2))
        );
    }
}