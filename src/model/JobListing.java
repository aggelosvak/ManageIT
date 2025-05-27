package model;

import java.time.LocalDate;

public class JobListing {
    private int id;
    private JobPosition jobPosition;
    private LocalDate postingDate;
    private boolean isOpen;
    private String notes;

    public JobListing(int id, JobPosition jobPosition, LocalDate postingDate, boolean isOpen, String notes) {
        this.id = id;
        this.jobPosition = jobPosition;
        this.postingDate = postingDate;
        this.isOpen = isOpen;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public LocalDate getPostingDate() {
        return postingDate;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getNotes() {
        return notes;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Open: %s, Date: %s)",
                jobPosition.getTitle(), id, isOpen ? "Ναι" : "Όχι", postingDate);
    }
}