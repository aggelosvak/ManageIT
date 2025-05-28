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
                new Employee(1, "John Doe", companies.get(0)),
                new Employee(2, "Jane Smith", companies.get(1)),
                new Employee(3, "Emily Davis", companies.get(2))
        );
    }
}