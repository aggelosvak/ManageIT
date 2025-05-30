package pages;

import employee.Employee;
import notification.NotificationService;
import review.CreateReview;
import review.Review;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ReviewPage extends JPanel {

    private final CreateReview createReview;
    private final Employee employee;

    public ReviewPage(CreateReview createReview, Employee employee) {
        this.createReview = createReview;
        this.employee = employee;

        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel("Employee Reviews", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // Content Panel (Two Sections: View Reviews and Submit Reviews)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Section 1: Display Existing Reviews
        contentPanel.add(createViewReviewsPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing

        // Section 2: Submit New Review
        contentPanel.add(createSubmitReviewPanel());

        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
    }

    /**
     * Section to display existing reviews for the employee.
     */
    private JPanel createViewReviewsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Your Reviews"));

        List<Review> reviews = fetchReviews();

        if (reviews.isEmpty()) {
            panel.add(new JLabel("No reviews available."));
        } else {
            for (Review review : reviews) {
                JTextArea reviewText = new JTextArea(review.toString());
                reviewText.setEditable(false);
                reviewText.setLineWrap(true);
                reviewText.setWrapStyleWord(true);
                reviewText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                panel.add(reviewText);
                panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
            }
        }

        return panel;
    }

    /**
     * Section to allow the submission of a new review using CreateReview.
     */
    private JPanel createSubmitReviewPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Submit a New Review"));

        // Review Criteria Labels and Inputs
        List<String> reviewCriteria = createReview.getReviewCriteria(); // Get criteria for review
        Map<String, JSpinner> criteriaInputs = new HashMap<>();

        for (String criterion : reviewCriteria) {
            JPanel criterionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            criterionPanel.add(new JLabel(criterion + ":"));

            JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
            criteriaInputs.put(criterion, ratingSpinner);
            criterionPanel.add(ratingSpinner);

            panel.add(criterionPanel);
        }

        // Description Area
        JTextArea descriptionArea = new JTextArea(3, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createTitledBorder("Description (Optional):"));
        panel.add(descriptionArea);

        // Submit Button
        JButton submitButton = new JButton("Submit Review");
        submitButton.addActionListener(e -> {
            Map<String, Integer> ratings = new HashMap<>();
            for (Map.Entry<String, JSpinner> entry : criteriaInputs.entrySet()) {
                ratings.put(entry.getKey(), (Integer) entry.getValue().getValue());
            }
            String description = descriptionArea.getText();

            // Submit the review and handle response
            if (createReview.submitReview(String.valueOf(employee.getEmployeeId()), ratings, "SystemReviewer", description)) {
                JOptionPane.showMessageDialog(this, "Review submitted successfully!");
                descriptionArea.setText(""); // Clear description area
                for (JSpinner spinner : criteriaInputs.values()) {
                    spinner.setValue(1); // Reset all spinner values to 1
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit the review. Please try again.");
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        panel.add(submitButton);

        return panel;
    }

    /**
     * Fetches reviews for the current employee (mock implementation).
     */
    private List<Review> fetchReviews() {
        // Mock implementation: This simulates existing reviews.
        List<Review> existingReviews = new ArrayList<>();
        existingReviews.add(new Review(
                "Reviewer1",
                Map.of("Teamwork", 5, "Communication", 4, "Problem Solving", 5),
                "Outstanding performance in the last project."
        ));
        existingReviews.add(new Review(
                "Reviewer2",
                Map.of("Technical Skills", 4, "Adaptability", 5),
                "Great adaptability during tight deadlines."
        ));

        return existingReviews;
    }

}