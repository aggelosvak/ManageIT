import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LandingPageUI {

    public static void main(String[] args) {
        // Launch the application
        SwingUtilities.invokeLater(() -> new LandingPageUI().createGUI());
    }

    private void createGUI() {
        // Main JFrame
        JFrame frame = new JFrame("Landing Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Top Panel with Logo and Menu
        JPanel topPanel = createTopPanel();
        frame.add(topPanel, BorderLayout.NORTH);

        // Middle Panel with User Information and Tabs
        JPanel middlePanel = createMiddlePanel();
        frame.add(middlePanel, BorderLayout.CENTER);

        // Footer Panel (if necessary)
        JPanel footerPanel = createFooterPanel();
        frame.add(footerPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }

    private JPanel createTopPanel() {
        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 220, 240)); // Light background color
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo and App Name (Left)
        JLabel logoLabel = new JLabel("🔗");
        logoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        JLabel appNameLabel = new JLabel("MyApp");
        appNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);
        logoPanel.add(logoLabel);
        logoPanel.add(appNameLabel);

        // Menu Button (Right) with Popup Menu
        JButton menuButton = new JButton("Menu");
        menuButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        JPopupMenu menuPopup = new JPopupMenu();
        menuPopup.add(new JMenuItem("Profile"));
        menuPopup.add(new JMenuItem("Settings"));
        menuPopup.add(new JMenuItem("Logout"));

        menuButton.addActionListener(e -> menuPopup.show(menuButton, 0, menuButton.getHeight()));

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        menuPanel.setOpaque(false);
        menuPanel.add(menuButton);

        // Add Logo Panel and Menu Panel to Top Panel
        topPanel.add(logoPanel, BorderLayout.WEST);
        topPanel.add(menuPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createMiddlePanel() {
        // Middle Panel
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Color.WHITE);

        // User Info Panel (Top of Middle Panel)
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userInfoPanel.setBackground(new Color(220, 240, 240));
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        JLabel userName = new JLabel("Welcome, John Doe");
        userName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        userInfoPanel.add(userIcon);
        userInfoPanel.add(userName);

        // Tabbed Pane for Activity and Notifications
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add "Activity" Tab
        JPanel activityTab = new JPanel();
        activityTab.setLayout(new BoxLayout(activityTab, BoxLayout.Y_AXIS));
        activityTab.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        activityTab.add(createActivityItem("Task Management"));
        activityTab.add(createActivityItem("Progress Tracker"));
        activityTab.add(createActivityItem("Team Collaboration"));

        tabbedPane.addTab("Activity", activityTab);

        // Add "Notifications" Tab
        JPanel notificationsTab = new JPanel(new BorderLayout());
        JLabel notificationsLabel = new JLabel("No new notifications", JLabel.CENTER);
        notificationsLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        notificationsTab.add(notificationsLabel, BorderLayout.CENTER);

        tabbedPane.addTab("Notifications", notificationsTab);

        // Add Components to Middle Panel
        middlePanel.add(userInfoPanel, BorderLayout.NORTH);
        middlePanel.add(tabbedPane, BorderLayout.CENTER);

        return middlePanel;
    }

    // Create Footer Panel
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(200, 220, 240));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel footerLabel = new JLabel("\u00a9 2023 MyApp. All Rights Reserved.");
        footerLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        footerPanel.add(footerLabel);
        return footerPanel;
    }

    // Helper Method to Create Activity Items in the Activity Tab
    private JPanel createActivityItem(String activityName) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(activityName);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        JButton button = new JButton("View");
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        button.addActionListener((ActionEvent e) -> JOptionPane.showMessageDialog(panel, "Viewing: " + activityName));

        panel.add(label);
        panel.add(button);
        return panel;
    }
}