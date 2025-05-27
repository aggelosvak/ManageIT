package meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee {
    private int id;
    private String name;
    private boolean isAvailable;

    public Employee(int id, String name, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Room {
    private int id;
    private String name;
    private boolean isAvailable;

    public Room(int id, String name, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}




class MeetingScheduler {
    private List<Employee> employees;
    private List<Room> rooms;

    public MeetingScheduler() {
        this.employees = new ArrayList<>();
        this.rooms = new ArrayList<>();
        populateTestData();
    }

    private void populateTestData() {
        employees.add(new Employee(1, "John Doe", true));
        employees.add(new Employee(2, "Jane Smith", true));
        employees.add(new Employee(3, "Bob Brown", false));

        rooms.add(new Room(1, "Room A", true));
        rooms.add(new Room(2, "Room B", true));
    }

    public List<Employee> getAvailableEmployees() {
        List<Employee> availableEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.isAvailable()) {
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void scheduleMeeting() {
        System.out.println("Creating a new meeting...");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Title: ");
        String title = scanner.nextLine();

        System.out.println("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.println("Time (HH:MM): ");
        String time = scanner.nextLine();

        System.out.println("Available employees:");
        List<Employee> availableEmployees = getAvailableEmployees();
        for (Employee e : availableEmployees) {
            System.out.println(e.getId() + ": " + e.getName());
        }

        System.out.println("Enter IDs of participants (comma-separated): ");
        String[] participantIds = scanner.nextLine().split(",");
        List<Employee> participants = new ArrayList<>();
        for (String id : participantIds) {
            Employee employee = getEmployeeById(Integer.parseInt(id.trim()));
            if (employee != null && employee.isAvailable()) {
                participants.add(employee);
            }
        }

        System.out.println("Description: ");
        String description = scanner.nextLine();

        System.out.println("Choose location (1: Room, 2: Remote): ");
        int locationChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline character

        String location;
        if (locationChoice == 1) {
            System.out.println("Available rooms:");
            List<Room> availableRooms = getAvailableRooms();
            for (Room room : availableRooms) {
                System.out.println(room.getId() + ": " + room.getName());
            }

            System.out.println("Enter the room ID:");
            int roomId = scanner.nextInt();
            scanner.nextLine(); // consume newline character
            Room room = getRoomById(roomId);

            if (room != null && room.isAvailable()) {
                location = room.getName();
                room.setAvailable(false); // mark the room as unavailable
            } else {
                System.out.println("No available rooms! Switching to remote option.");
                location = "Remote";
            }
        } else {
            location = "Remote";
        }

        Meeting meeting = new Meeting(title, date, time, participants, description, location);
        System.out.println("Meeting created: " + meeting.getTitle());
        notifyParticipants(participants);
    }

    private Employee getEmployeeById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    private Room getRoomById(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    private void notifyParticipants(List<Employee> participants) {
        System.out.println("Notifying participants...");
        for (Employee e : participants) {
            System.out.println("Notification sent to: " + e.getName());
        }
    }
}

public class MeetingSystem {
    public static void main(String[] args) {
        MeetingScheduler scheduler = new MeetingScheduler();
        scheduler.scheduleMeeting();
    }
}