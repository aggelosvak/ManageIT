package data;

import manager.Manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerData {

    // Example managers (IDs must match what you use in JobListingData)
    public static final Manager MANAGER_JOHN = new Manager(
            101, // This ID matches JobListingData.SERVER_LISTING
            "John Doe",
            "john.doe@company.com",
            "Cafeteria"
    );

    public static final Manager MANAGER_MARIA = new Manager(
            102, // This ID matches JobListingData.COOK_LISTING
            "Maria Papadopoulou",
            "maria.papadopoulou@company.com",
            "Kitchen"
    );

    private static final Map<Integer, Manager> managersMap = new HashMap<>();

    static {
        List<Manager> managers = Arrays.asList(MANAGER_JOHN, MANAGER_MARIA);
        for (Manager manager : managers) {
            managersMap.put(manager.getId(), manager);
        }
    }

    public static List<Manager> all() {
        return Arrays.asList(MANAGER_JOHN, MANAGER_MARIA);
    }

    public static Manager getById(int id) {
        return managersMap.get(id);
    }
}