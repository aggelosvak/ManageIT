package review;

import java.sql.Timestamp;
import java.util.Map;

public class Review {

    // Attributes
    private String employeeId;           // ID of the employee being reviewed
    private String reviewerId;           // ID of the reviewer
    private Timestamp reviewDate;        // Date/time of the review
    private Map<String, Integer> ratings; // Ratings for specific criteria
    private String description;          // A description or summary of the review

    // Default Constructor
    public Review() {
    }

    // Parameterized Constructor
    public Review(String employeeId, String reviewerId, Timestamp reviewDate, Map<String, Integer> ratings, String description) {
        this.employeeId = employeeId;
        this.reviewerId = reviewerId;
        this.reviewDate = reviewDate;
        this.ratings = ratings;
        this.setDescription(description); // Use setter for validation
    }

    // Setter for employeeId
    public void setEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }
        this.employeeId = employeeId;
    }

    // Getter for employeeId
    public String getEmployeeId() {
        return employeeId;
    }

    // Setter for reviewerId
    public void setReviewerId(String reviewerId) {
        if (reviewerId == null || reviewerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Reviewer ID cannot be null or empty");
        }
        this.reviewerId = reviewerId;
    }

    // Getter for reviewerId
    public String getReviewerId() {
        return reviewerId;
    }

    // Setter for reviewDate
    public void setReviewDate(Timestamp reviewDate) {
        if (reviewDate == null) {
            throw new IllegalArgumentException("Review date cannot be null");
        }
        this.reviewDate = reviewDate;
    }

    // Getter for reviewDate
    public Timestamp getReviewDate() {
        return reviewDate;
    }

    // Setter for ratings
    public void setRatings(Map<String, Integer> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            throw new IllegalArgumentException("Ratings cannot be null or empty");
        }
        for (Map.Entry<String, Integer> entry : ratings.entrySet()) {
            if (entry.getValue() < 1 || entry.getValue() > 5) {
                throw new IllegalArgumentException("Rating for " + entry.getKey() + " must be between 1 and 5");
            }
        }
        this.ratings = ratings;
    }

    // Getter for ratings
    public Map<String, Integer> getRatings() {
        return ratings;
    }

    // Setter for description
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Override toString() for debugging/logging purposes
    @Override
    public String toString() {
        return "Review{" +
                "employeeId='" + employeeId + '\'' +
                ", reviewerId='" + reviewerId + '\'' +
                ", reviewDate=" + reviewDate +
                ", ratings=" + ratings +
                ", description='" + description + '\'' +
                '}';
    }
}