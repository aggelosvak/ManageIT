package data;
import company.Company;

import java.util.List;
import java.util.Arrays;

public class CompanyData {

    // Example static list of companies
    private static final List<Company> companies = Arrays.asList(
            new Company(1, "OpenAI Ltd.",        "123 AI Street",      101, 10, 201, 301),
            new Company(2, "Tech Innovators",    "456 Future Blvd",    102, 8, 202, 302),
            new Company(3, "Green Energy LLC",   "789 Eco Road",       103, 12, 203, 303)
    );

    // Get all companies
    public static List<Company> getCompanies() {
        return companies;
    }

    // Get a single company by ID
    public static Company getCompanyById(int id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElse(null);
    }
}