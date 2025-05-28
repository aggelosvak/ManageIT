package workhours;

import employee.Employee;
import employee.EmployeeService;
import notification.NotificationService;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class WorkHoursTrackingService {

    private final EmployeeService employeeService;
    private final NotificationService notificationService;
    private final Map<Integer, Timestamp> arrivalTimestamps = new HashMap<>();
    private final Map<Integer, Duration> breakDurations = new HashMap<>();

    public WorkHoursTrackingService(EmployeeService employeeService, NotificationService notificationService) {
        this.employeeService = employeeService;
        this.notificationService = notificationService;
    }

    // 1. Record Arrival Time
    public void recordArrival(int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        arrivalTimestamps.put(employeeId, currentTime);

        // Notify the manager about the employee's arrival
        String notificationContent = "The employee " + employee.getName() + " has arrived at " + currentTime;
        notifyManager(employee.getEmployeeId(), "Employee Arrival Notification", notificationContent);
        System.out.println("Arrival recorded for employee: " + employeeId);
    }

    // 2. Start Break Timer
    public void startBreak(int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        // Check remaining break time
        Duration remainingBreakTime = getRemainingBreakTime(employeeId);

        if (remainingBreakTime.isNegative() || remainingBreakTime.isZero()) {
            System.out.println("No remaining break time for employee: " + employeeId);
            throw new IllegalStateException("No remaining break time available.");
        }

        System.out.println("Break started for " + employee.getName() + ". Remaining time: " + remainingBreakTime);
        breakDurations.put(employeeId, remainingBreakTime);
    }

    // 3. Resume Work (Save Remaining Break Time)
    public void resumeWork(int employeeId) {
        Duration remainingBreakTime = breakDurations.getOrDefault(employeeId, Duration.ZERO);

        if (!remainingBreakTime.isZero()) {
            System.out.println("Remaining break time (" + remainingBreakTime + ") saved for employee: " + employeeId);
        }

        breakDurations.put(employeeId, remainingBreakTime);
    }

    // 4. Record Exit Time
    public void recordExit(int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        Timestamp arrivalTime = arrivalTimestamps.get(employeeId);
        if (arrivalTime == null) {
            throw new IllegalStateException("Arrival time not found for " + employeeId);
        }

        Duration workedHours = Duration.between(arrivalTime.toLocalDateTime(), LocalDateTime.now())
                .minus(breakDurations.getOrDefault(employeeId, Duration.ZERO));

        System.out.println("Exit time recorded for " + employee.getName() + ".");
        System.out.println("Total Worked Hours (excluding breaks): " + workedHours);

        // Notify Manager
        String notificationContent = "Worked Hours Report: Employee " + employee.getName() + " worked for " + workedHours.toHours() + " hours today.";
        notifyManager(employee.getEmployeeId(), "Worked Hours Notification", notificationContent);
    }

    // Utility methods
    private Employee findEmployeeById(int employeeId) {
        return employeeService.getAvailableEmployees().stream()
                .filter(e -> e.getEmployeeId() == employeeId)
                .findFirst()
                .orElse(null);
    }

    private Duration getRemainingBreakTime(int employeeId) {
        // Mock logic: Suppose every employee has 1 hour of break time
        Duration totalBreakTime = Duration.ofMinutes(60);
        Duration usedBreakTime = breakDurations.getOrDefault(employeeId, Duration.ZERO);
        return totalBreakTime.minus(usedBreakTime);
    }

    private void notifyManager(int employeeId, String notificationTitle, String notificationMessage) {
        notificationService.notifyManager(employeeId, notificationTitle, notificationMessage);
    }

}