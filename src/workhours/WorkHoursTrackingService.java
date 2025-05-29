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
    private final Map<Integer, Timestamp> activeBreakStartTimes = new HashMap<>();

    public WorkHoursTrackingService(EmployeeService employeeService, NotificationService notificationService) {
        this.employeeService = employeeService;
        this.notificationService = notificationService;
    }

    // 1. Record Arrival Time
    public void recordArrival(int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        arrivalTimestamps.put(employeeId, Timestamp.valueOf(LocalDateTime.now()));
        System.out.println("Arrival time recorded for Employee ID: " + employeeId);
    }

    // 2. Start Break Timer
    public void startBreak(int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee == null || activeBreakStartTimes.containsKey(employeeId)) {
            System.out.println("Employee not found or already on a break!");
            return;
        }

        activeBreakStartTimes.put(employeeId, Timestamp.valueOf(LocalDateTime.now()));
        System.out.println("Break started for Employee ID: " + employeeId);
    }

    // 3. Resume Work (Save Break Time)
    public void resumeWork(int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee == null || !activeBreakStartTimes.containsKey(employeeId)) {
            System.out.println("Employee not found or not on a break!");
            return;
        }

        // Calculate break duration
        Timestamp breakStartTime = activeBreakStartTimes.get(employeeId);
        Duration breakDuration = Duration.between(breakStartTime.toLocalDateTime(), LocalDateTime.now());

        // Add to existing break duration if any
        breakDurations.put(employeeId, breakDurations.getOrDefault(employeeId, Duration.ZERO).plus(breakDuration));

        // Remove active break entry after resuming
        activeBreakStartTimes.remove(employeeId);

        System.out.println("Break stopped and work resumed for Employee ID: " + employeeId);
    }

    // 4. Record Exit Time
    public void recordExit(int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee == null || !arrivalTimestamps.containsKey(employeeId)) {
            System.out.println("Employee not found or did not record arrival!");
            return;
        }

        Timestamp arrivalTime = arrivalTimestamps.get(employeeId);
        Duration workDuration = Duration.between(arrivalTime.toLocalDateTime(), LocalDateTime.now());

        // Subtract break durations from work hours
        Duration breaks = breakDurations.getOrDefault(employeeId, Duration.ZERO);
        workDuration = workDuration.minus(breaks);

        System.out.println("Workday complete for Employee Name: " + employee.getName());
        System.out.println("Work Duration: " + workDuration.toHours() + " hours");
        System.out.println("Break Duration: " + breaks.toMinutes() + " minutes");

        arrivalTimestamps.remove(employeeId);
        breakDurations.remove(employeeId);
    }

    // Utility methods
    private Employee findEmployeeById(int employeeId) {
        return employeeService.getAllEmployees().stream()
                .filter(e -> e.getEmployeeId() == employeeId)
                .findFirst()
                .orElse(null);
    }
}