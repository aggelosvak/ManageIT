package model;

public class Candidate {
    private String id; // Unique identifier for the candidate
    private String name; // Candidate's full name
    private String email; // Candidate's email address
    private String phoneNumber; // Candidate's phone number
    private String cv; // Candidate's CV or application details (optional)
    private JobListing jobListing; // NEW: The job listing the candidate applied to

    // Updated Constructor
    public Candidate(String id, String name, String email, String phoneNumber, String cv, JobListing jobListing) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cv = cv;
        this.jobListing = jobListing;
    }

    // OLD Constructor for backward compatibility
    public Candidate(String id, String name, String email, String phoneNumber, String cv) {
        this(id, name, email, phoneNumber, cv, null);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public JobListing getJobListing() {
        return jobListing;
    }

    public void setJobListing(JobListing jobListing) {
        this.jobListing = jobListing;
    }

    // Method to update the CV
    public void updateCv(String newCv) {
        if (newCv == null || newCv.isEmpty()) {
            System.out.println("Invalid CV provided. Update failed.");
            return;
        }
        this.cv = newCv;
        System.out.println("CV updated successfully.");
    }

    // Method to check if a CV is provided
    public boolean hasCv() {
        return this.cv != null && !this.cv.isEmpty();
    }

    // Method to display the CV
    public void displayCv() {
        if (hasCv()) {
            System.out.println("CV for " + name + ": " + cv);
        } else {
            System.out.println("No CV provided for " + name + ".");
        }
    }

    // toString() to display candidate summary
    @Override
    public String toString() {
        return "Candidate{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", jobListingId=" + (jobListing != null ? jobListing.getId() : "null") +
                '}';
    }

    // Optional: Method to display full candidate details, including the CV status
    public String displayFullDetails() {
        return "Candidate Details:\n" +
                "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phoneNumber + "\n" +
                "CV: " + (hasCv() ? cv : "No CV provided") + "\n" +
                "JobListing ID: " + (jobListing != null ? jobListing.getId() : "none");
    }
}