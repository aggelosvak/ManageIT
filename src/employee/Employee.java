public class Employee {
    private String employeeId;
    private String name;
    private String email;
    private boolean isActive;

    // Constructor
    public Employee(String employeeId, String name, String email, boolean isActive) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}