package model;

import user.User;
import model.JobListing;

public class JobApplication {
    private int applicationId;
    private User applicant;          // Links to User table
    private JobListing jobListing;   // Links to JobListing table, which links JobPosition
    private String coverLetter;
    private String moreInfo;         // Additional field for more information
    private boolean completed;

    public JobApplication(int applicationId, User applicant, JobListing jobListing, String coverLetter, String moreInfo) {
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.jobListing = jobListing;
        this.coverLetter = coverLetter;
        this.moreInfo = moreInfo;
        this.completed = false;
    }

    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }

    public User getApplicant() { return applicant; }
    public void setApplicant(User applicant) { this.applicant = applicant; }

    public JobListing getJobListing() { return jobListing; }
    public void setJobListing(JobListing jobListing) { this.jobListing = jobListing; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public String getMoreInfo() { return moreInfo; }
    public void setMoreInfo(String moreInfo) { this.moreInfo = moreInfo; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    // Convenience: Get JobPosition for this application, if needed
    public JobPosition getJobPosition() {
        return jobListing != null ? jobListing.getJobPosition() : null;
    }

    // Convenience: Get applicant's CV link (will return null if not set)
    public String getApplicantCvLink() {
        return applicant != null ? applicant.getCvLink() : null;
    }
}