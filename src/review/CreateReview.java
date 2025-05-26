package review;

import employee.EmployeeService; // Correctly referring to the EmployeeService package
import notification.NotificationService;
import review.Review;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateReview {

    private final EmployeeService employeeService;  // Correct package reference
    private final NotificationService notificationService;
    private static final Logger LOGGER = Logger.getLogger(CreateReview.class.getName());

    // Constructor Dependency Injection
    public CreateReview(EmployeeService employeeService, NotificationService notificationService) {
        this.employeeService = employeeService;
        this.notificationService = notificationService;
    }

    // Check if a review can be created for the specified employee
    public boolean canReview(String employeeId) {
        try {
            Review lastReview = getLastReview(employeeId); // Fetch from DB or repository

            if (lastReview != null) {
                Timestamp lastReviewTimestamp = lastReview.getReviewDate();
                LocalDate lastReviewDate = lastReviewTimestamp.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                LocalDate today = LocalDate.now();
                long daysSinceLastReview = ChronoUnit.DAYS.between(lastReviewDate, today);
                return daysSinceLastReview >= 30; // Ensure 30-day restriction
            }

            return true; // No prior review, so the review can proceed
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while checking last review date for employeeId: " + employeeId, e);
            return false;
        }
    }

    // Retrieve defined criteria for reviews
    public List<String> getReviewCriteria() {
        return List.of("Performance", "Cooperation", "Communication");
    }

    // Validate that at least one valid criterion is selected
    public boolean validateCriteriaSelection(List<String> selectedCriteria) {
        return selectedCriteria != null && !selectedCriteria.isEmpty();
    }

    // Submit a review
    public boolean submitReview(String employeeId, Map<String, Integer> ratings, String reviewerId, String description) {
        try {
            // Input validations
            if (!validateCriteriaSelection(List.copyOf(ratings.keySet()))) {
                throw new IllegalArgumentException("At least one review criterion must be selected");
            }

            // Create the review object
            Review review = new Review();
            review.setEmployeeId(employeeId);
            review.setReviewerId(reviewerId);
            review.setRatings(ratings);
            review.setDescription(description);
            review.setReviewDate(new Timestamp(System.currentTimeMillis()));

            // Save the review to persistence
            saveReview(review);

            // Notify the employee about the review submission
            Review Review;
            notificationService.notifyEmployeeReview(employeeId, review);

            return true; // Successfully submitted
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid review submission data for employeeId: " + employeeId, e);
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to submit review for employeeId: " + employeeId, e);
            return false;
        }
    }

    // Mock method to fetch the last review (replace with actual DB/repository logic)
    private Review getLastReview(String employeeId) {
        LOGGER.info("Fetching last review for employeeId: " + employeeId);
        // Placeholder logic. Implement actual DB/repository call.
        return null;
    }

    // Mock saving function (replace with actual DB logic)
    private void saveReview(Review review) {
        LOGGER.info("Saving review: " + review);
        // Placeholder logic. Implement actual DB call to persist the review.
    }
}