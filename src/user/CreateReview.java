import user.Review;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class CreateReview {

    private final com.yourproject.service.EmployeeService employeeService;
    private final NotificationService notificationService;

    // Constructor Dependency Injection
    public CreateReview(com.yourproject.service.EmployeeService employeeService, NotificationService notificationService) {
        this.employeeService = employeeService;
        this.notificationService = notificationService;
    }

    // Check if a review can be created
    public boolean canReview(String employeeId) {
        Review lastReview = getLastReview(employeeId); // Fetch from DB or repository

        if (lastReview != null) {
            LocalDate lastReviewDate = lastReview.getReviewDate().toLocalDateTime().toLocalDate();
            LocalDate today = LocalDate.now();
            long daysSinceLastReview = ChronoUnit.DAYS.between(lastReviewDate, today);
            return daysSinceLastReview >= 30; // 30-day restriction
        }

        return true; // No prior review
    }

    // Retrieve defined criteria for reviews
    public List<String> getReviewCriteria() {
        return List.of("Performance", "Cooperation", "Communication");
    }

    // Validate that criteria have been selected
    public boolean validateCriteriaSelection(List<String> selectedCriteria) {
        return selectedCriteria != null && !selectedCriteria.isEmpty();
    }

    // Submit a review
    public boolean submitReview(String employeeId, Map<String, Integer> ratings, String reviewerId) {
        try {
            // Create the review object
            Review review = new Review();
            review.setEmployeeId(employeeId);
            review.setReviewerId(reviewerId);
            review.setRatings(ratings);
            review.setReviewDate(new Timestamp(System.currentTimeMillis()));

            // Save the review (to a database, for example)
            saveReview(review);

            // Notify the employee
            notificationService.notifyEmployeeReview(employeeId, review);

            return true; // Successfully submitted
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mock method to get the last review (replace with DB/repository logic)
    private Review getLastReview(String employeeId) {
        return null; // Placeholder
    }

    // Mock saving function (replace with DB logic)
    private void saveReview(Review review) {
        System.out.println("Saving review: " + review);
    }
}