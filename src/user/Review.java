package user;

public class Review {
    private int reviewId;       // Unique Review ID
    private int workerId;       // ID of the worker being reviewed
    private int managerId;      // ID of the manager who wrote the review
    private String description; // Review description
    private double rating;      // Rating given in the review (e.g., out of 5.0)

    // Constructor
    public Review(int reviewId, int workerId, int managerId, String description, double rating) {
        this.reviewId = reviewId;
        this.workerId = workerId;
        this.managerId = managerId;
        this.description = description;
        this.rating = rating;
    }

    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating >= 0.0 && rating <= 5.0) { // Ensure valid rating range
            this.rating = rating;
        } else {
            System.out.println("Invalid rating. Please provide a rating between 0.0 and 5.0.");
        }
    }

    // Method to display review details
    public void displayReview() {
        System.out.println("Review ID: " + reviewId);
        System.out.println("Worker ID: " + workerId);
        System.out.println("Manager ID: " + managerId);
        System.out.println("Description: " + description);
        System.out.println("Rating: " + rating);
    }
}