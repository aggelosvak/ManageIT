package hiring;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Represents a Job Listing
class JobListing {
    private final int id;
    private final String title;
    private final List<UsersApplied> applicants;

    public JobListing(int id, String title) {
        this.id = id;
        this.title = title;
        this.applicants = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<UsersApplied> getApplicants() {
        return applicants;
    }

    public void addApplicant(UsersApplied applicant) {
        applicants.add(applicant);
    }
}

// Represents a User who applies to a Job
class UsersApplied {
    private final int id;
    private final String name;

    public UsersApplied(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

// Handles Request Approval/Rejection
class Request {
    private final JobRequest jobRequest;
    private boolean isApproved;

    public Request(JobRequest jobRequest) {
        this.jobRequest = jobRequest;
        this.isApproved = false;
    }

    public JobRequest getJobRequest() {
        return jobRequest;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void approve() {
        isApproved = true;
        System.out.println("Job request approved. User ID: " + jobRequest.getUserId());
    }

    public void deny() {
        isApproved = false;
        System.out.println("Job request denied. User ID: " + jobRequest.getUserId());
    }
}

// Handles Manager Notifications
class Notification {
    public void notifyManager(int managerId, String message) {
        System.out.println("Notification to Manager [ID: " + managerId + "]: " + message);
    }
}

// Centralized Hiring System
class HiringSystem {
    private final List<JobListing> jobListings = new ArrayList<>();
    private final Notification notification = new Notification();
    private int requestCounter = 1; // Unique counter for JobRequest IDs

    // Adds a Job Listing
    public void addJobListing(JobListing jobListing) {
        jobListings.add(jobListing);
    }

    // Finds a Job Listing by ID
    public Optional<JobListing> findJobListingById(int jobId) {
        return jobListings.stream()
                .filter(job -> job.getId() == jobId)
                .findFirst();
    }

    // Executes the Hiring Process
    public void hireUser(int jobId, int managerId) {
        // Step 1: Retrieve Job Listing
        Optional<JobListing> jobOptional = findJobListingById(jobId);
        if (jobOptional.isEmpty()) {
            System.out.println("No job found for ID: " + jobId);
            return;
        }

        JobListing jobListing = jobOptional.get();
        System.out.println("Job Selected: " + jobListing.getTitle());

        // Step 2: Verify Applicants
        List<UsersApplied> applicants = jobListing.getApplicants();
        if (applicants.isEmpty()) {
            System.out.println("No applicants for this job.");
            return;
        }

        // Step 3: Display Applicants
        System.out.println("Applicants for the Job:");
        for (UsersApplied applicant : applicants) {
            System.out.println("  * ID: " + applicant.getId() + " | Name: " + applicant.getName());
        }

        // Step 4: Select an Applicant
        UsersApplied selectedApplicant = applicants.get(0); // Selecting the first applicant
        System.out.println("Selected Applicant: " + selectedApplicant.getName());

        // Step 5: Create a Job Request
        JobRequest jobRequest = new JobRequest(
                requestCounter++,
                selectedApplicant.getId(),
                jobId,
                "Would you like to accept the " + jobListing.getTitle() + " position?"
        );

        // Step 6: Handle the Request
        handleRequest(jobRequest, selectedApplicant, managerId);
    }

    private void handleRequest(JobRequest jobRequest, UsersApplied applicant, int managerId) {
        // Create a Request Wrapper
        Request request = new Request(jobRequest);

        // Simulate Sending the Job Request
        System.out.println("Sending request to: " + applicant.getName());
        System.out.println(jobRequest.getMessage());

        // Simulate User Response (manually controlled or randomized)
        boolean userResponse = simulateUserResponse();
        if (userResponse) {
            request.approve();
            saveEmployee(applicant);
        } else {
            request.deny();
            notifyManager(managerId, applicant);
        }
    }

    private boolean simulateUserResponse() {
        // Simulate acceptance or rejection randomly
        return Math.random() < 0.5; // 50% chance of acceptance
    }

    private void saveEmployee(UsersApplied applicant) {
        System.out.println(applicant.getName() + " has been hired and added as an employee.");
    }

    private void notifyManager(int managerId, UsersApplied applicant) {
        notification.notifyManager(managerId, "Applicant " + applicant.getName() + " has rejected the offer.");
    }
}

// Demo Application
public class HiringSystemDemo {
    public static void main(String[] args) {
        // Step 1: Initialize the system
        HiringSystem hiringSystem = new HiringSystem();

        // Step 2: Add job listings
        JobListing job1 = new JobListing(1, "Software Developer");
        JobListing job2 = new JobListing(2, "Data Scientist");
        hiringSystem.addJobListing(job1);
        hiringSystem.addJobListing(job2);

        // Step 3: Add applicants
        UsersApplied applicant1 = new UsersApplied(101, "Alice");
        UsersApplied applicant2 = new UsersApplied(102, "Bob");
        job1.addApplicant(applicant1);
        job1.addApplicant(applicant2);

        // Step 4: Execute hiring process
        hiringSystem.hireUser(1, 1001); // Hiring for Job ID: 1, Manager ID: 1001
    }
}