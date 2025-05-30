package model;

import java.time.LocalDate;

public class JobListing {
    private int id;
    private JobPosition jobPosition;
    private LocalDate postingDate;
    private boolean isOpen;
    private String notes;
    private int managerId; // <-- New attribute

    public JobListing(int id, JobPosition jobPosition, LocalDate postingDate, boolean isOpen, String notes, int managerId) {
        this.id = id;
        this.jobPosition = jobPosition;
        this.postingDate = postingDate;
        this.isOpen = isOpen;
        this.notes = notes;
        this.managerId = managerId;
    }

    // For backward compatibility, keep the old constructor
    public JobListing(int id, JobPosition jobPosition, LocalDate postingDate, boolean isOpen, String notes) {
        this(id, jobPosition, postingDate, isOpen, notes, -1);
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

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    /**
     * Returns the title of the job by delegating to the JobPosition.
     * @return the job title
     */
    public String getTitle() {
        return jobPosition != null ? jobPosition.getTitle() : null;
    }

    /**
     * Returns the description of the job by delegating to the JobPosition.
     * @return the job description
     */
    public String getDescription() {
        return jobPosition != null ? jobPosition.getDescription() : null;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Open: %s, Date: %s, Manager ID: %d)",
                jobPosition.getTitle(), id, isOpen ? "Ναι" : "Όχι", postingDate, managerId);
    }
}