package workhours;

import employee.EmployeeService;
import notification.NotificationService;
import model.Shift;
import java.util.HashMap;
import java.util.Map;

public class WorkHoursTrackingService {
    private final Map<Integer, Shift> employeeIdToShift = new HashMap<>();
    private static final int INITIAL_BREAK_MINUTES = 15;

    // Add references to services
    private final EmployeeService employeeService;
    private final NotificationService notificationService;

    // Constructor that accepts the two services
    public WorkHoursTrackingService(EmployeeService employeeService, NotificationService notificationService) {
        this.employeeService = employeeService;
        this.notificationService = notificationService;
    }

    public void recordArrival(int employeeId) {
        Shift shift = new Shift(employeeId, INITIAL_BREAK_MINUTES);
        shift.recordArrival();
        employeeIdToShift.put(employeeId, shift);
    }

    public void startBreak(int employeeId) {
        Shift shift = employeeIdToShift.get(employeeId);
        if (shift != null) shift.startBreak();
    }

    public void endBreak(int employeeId, int usedMinutes) {
        Shift shift = employeeIdToShift.get(employeeId);
        if (shift != null && shift.isOnBreak()) shift.endBreak(usedMinutes);
    }

    public void recordExit(int employeeId) {
        Shift shift = employeeIdToShift.get(employeeId);
        if (shift != null) shift.recordExit();
        // Optionally send notifications or interact using notificationService here
        // notificationService.sendNotification(...)
    }

    public Shift getCurrentShift(int employeeId) {
        return employeeIdToShift.get(employeeId);
    }
}