package user;

public class Manager extends User {

    private String department;

    // Constructor
    public Manager(int id, String name, String email, String department) {
        super(id, name, email); // Call the superclass constructor
        this.department = department;
    }

    // Getter and Setter for department
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Overriding displayInfo() to include department
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call the displayInfo method from User
        System.out.println("Department: " + department);
    }
}