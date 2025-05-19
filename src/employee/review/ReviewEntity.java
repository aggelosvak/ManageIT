//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//
//public class Review {
//    private String reviewId;
//    private String employeeId;
//    private List<String> selectedCriteria;
//    private Map<String, Integer> scores; // Maps criteria name to score
//    private String reviewerId;
//    private LocalDateTime reviewDate;
//
//    // Constructor
//    public Review(String reviewId, String employeeId, List<String> selectedCriteria, Map<String, Integer> scores, String reviewerId, LocalDateTime reviewDate) {
//        this.reviewId = reviewId;
//        this.employeeId = employeeId;
//        this.selectedCriteria = selectedCriteria;
//        this.scores = scores;
//        this.reviewerId = reviewerId;
//        this.reviewDate = reviewDate;
//    }
//
//    // Getters and Setters
//    public String getReviewId() {
//        return reviewId;
//    }
//
//    public void setReviewId(String reviewId) {
//        this.reviewId = reviewId;
//    }
//
//    public String getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(String employeeId) {
//        this.employeeId = employeeId;
//    }
//
//    public List<String> getSelectedCriteria() {
//        return selectedCriteria;
//    }
//
//    public void setSelectedCriteria(List<String> selectedCriteria) {
//        this.selectedCriteria = selectedCriteria;
//    }
//
//    public Map<String, Integer> getScores() {
//        return scores;
//    }
//
//    public void setScores(Map<String, Integer> scores) {
//        this.scores = scores;
//    }
//
//    public String getReviewerId() {
//        return reviewerId;
//    }
//
//    public void setReviewerId(String reviewerId) {
//        this.reviewerId = reviewerId;
//    }
//
//    public LocalDateTime getReviewDate() {
//        return reviewDate;
//    }
//
//    public void setReviewDate(LocalDateTime reviewDate) {
//        this.reviewDate = reviewDate;
//    }
//}