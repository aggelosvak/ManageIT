package model;

import employee.Employee;
import manager.Manager;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

public class Review {
    private String employeeId;
    private String reviewerId;
    private Timestamp reviewDate;
    private Map<String, Integer> ratings;
    private String description;

    // Direct references to related domain objects
    private Employee employee;
    private Manager manager;

    public Review() {
        // Empty default constructor
    }

    public Review(Employee employee, Manager manager, Timestamp reviewDate, Map<String, Integer> ratings, String description) {
        setEmployee(employee);
        setManager(manager);
        setEmployeeId(String.valueOf(employee.getEmployeeId()));
        setReviewerId(String.valueOf(manager.getId()));
        setReviewDate(reviewDate);
        setRatings(ratings);
        setDescription(description);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }
        this.employeeId = employeeId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        if (reviewerId == null || reviewerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Reviewer ID cannot be null or empty");
        }
        this.reviewerId = reviewerId;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        if (reviewDate == null) {
            throw new IllegalArgumentException("Review date cannot be null");
        }
        this.reviewDate = reviewDate;
    }

    public Map<String, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Integer> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            throw new IllegalArgumentException("Ratings cannot be null or empty");
        }
        for (Map.Entry<String, Integer> entry : ratings.entrySet()) {
            if (entry.getValue() < 1 || entry.getValue() > 5) {
                throw new IllegalArgumentException("Each rating must be between 1 and 5");
            }
            if (entry.getKey() == null || entry.getKey().isEmpty()) {
                throw new IllegalArgumentException("Criterion name cannot be null or empty");
            }
        }
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        this.employee = employee;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("Manager cannot be null");
        }
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Review{" +
                "employeeId='" + employeeId + '\'' +
                ", reviewerId='" + reviewerId + '\'' +
                ", reviewDate=" + reviewDate +
                ", ratings=" + ratings +
                ", description='" + description + '\'' +
                ", employee=" + (employee != null ? employee.getName() : "null") +
                ", manager=" + (manager != null ? manager.getName() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(employeeId, review.employeeId) &&
                Objects.equals(reviewerId, review.reviewerId) &&
                Objects.equals(reviewDate, review.reviewDate) &&
                Objects.equals(ratings, review.ratings) &&
                Objects.equals(description, review.description) &&
                Objects.equals(employee, review.employee) &&
                Objects.equals(manager, review.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, reviewerId, reviewDate, ratings, description, employee, manager);
    }
}