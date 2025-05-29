import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import data.UserData;
import user.User;

public class UserLandingPage extends JPanel {
    public UserLandingPage() {
        setLayout(new BorderLayout());

        // Get the current user
        User currentUser = UserData.getCurrentUser();

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        JButton listingsButton = new JButton("Listings");
        listingsButton.addActionListener((ActionEvent e) -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll();
            topFrame.getContentPane().add(new JobApply.JobListingsPage());
            topFrame.revalidate();
            topFrame.repaint();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(listingsButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    // Optional: main method for easy testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Landing Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new UserLandingPage());
            frame.setVisible(true);
        });
    }
}