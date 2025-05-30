package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Shift {
    private int employeeId;
    private LocalDateTime arrivalTime;
    private LocalDateTime endTime;
    private Duration duration;
    private int remainingBreakTimeMinutes;
    private boolean onBreak;
    private LocalDateTime breakStartTime;

    public Shift(int employeeId, int initialBreakMinutes) {
        this.employeeId = employeeId;
        this.remainingBreakTimeMinutes = initialBreakMinutes;
        this.onBreak = false;
    }

    public void recordArrival() {
        this.arrivalTime = LocalDateTime.now();
    }

    public void startBreak() {
        if (remainingBreakTimeMinutes > 0 && !onBreak) {
            this.onBreak = true;
            this.breakStartTime = LocalDateTime.now();
        }
    }

    public void endBreak(int usedMinutes) {
        if (onBreak) {
            this.remainingBreakTimeMinutes = Math.max(0, remainingBreakTimeMinutes - usedMinutes);
            this.onBreak = false;
        }
    }

    public void recordExit() {
        this.endTime = LocalDateTime.now();
        if (arrivalTime != null && endTime != null) {
            this.duration = Duration.between(arrivalTime, endTime);
        }
    }

    // --- Getters and setters ---

    public int getEmployeeId() { return employeeId; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Duration getDuration() { return duration; }
    public int getRemainingBreakTimeMinutes() { return remainingBreakTimeMinutes; }
    public void setRemainingBreakTimeMinutes(int min) { this.remainingBreakTimeMinutes = min; }
    public boolean isOnBreak() { return onBreak; }
    public void setOnBreak(boolean onBreak) { this.onBreak = onBreak; }
    public LocalDateTime getBreakStartTime() { return breakStartTime; }
}