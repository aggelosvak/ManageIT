import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageITLandingPage {

    public static void main(String[] args) {
        // Launch the application
        SwingUtilities.invokeLater(() -> new ManageITLandingPage().createGUI());
    }

    private void createGUI() {
        // Main frame
        JFrame frame = new JFrame("ManageIT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Top Panel (Logo, App Name, and Menu)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 220, 240)); // Light background color

        // Logo and App Name (Left Side)
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel logoLabel = new JLabel("🔗"); // Placeholder for a logo; replace with image if needed
        logoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        JLabel appNameLabel = new JLabel("ManageIT");
        appNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        logoPanel.add(logoLabel);
        logoPanel.add(appNameLabel);

        // Drop-down Menu (Right Side)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton menuButton = new JButton("Menu");
        menuButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        // Drop-down Options
        JPopupMenu menuPopup = new JPopupMenu();
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenuItem jobListingsItem = new JMenuItem("Job Listings");
        menuPopup.add(logoutItem);
        menuPopup.add(jobListingsItem);

        menuButton.addActionListener(e -> menuPopup.show(menuButton, menuButton.getWidth(), menuButton.getHeight()));
        menuPanel.add(menuButton);

        // Add Logo Panel and Menu Panel to Top Panel
        topPanel.add(logoPanel, BorderLayout.WEST);
        topPanel.add(menuPanel, BorderLayout.EAST);

        // Middle Panel (User Info and Tabs)
        JPanel middlePanel = new JPanel(new BorderLayout());

        // User Info at the Top of Middle Panel
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userInfoPanel.setBackground(new Color(220, 240, 240)); // Light background color
        JLabel userIconLabel = new JLabel("👤"); // Placeholder for user icon
        userIconLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        JLabel userNameLabel = new JLabel("John Doe");
        userNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        userInfoPanel.add(userIconLabel);
        userInfoPanel.add(userNameLabel);

        // Tabbed Pane (Activity and Notifications)
        JTabbedPane tabbedPane = new JTabbedPane();

        // Activity Tab Content
        JPanel activityTab = new JPanel();
        activityTab.setLayout(new BoxLayout(activityTab, BoxLayout.Y_AXIS));
        activityTab.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        activityTab.add(createLeaveOption("Sick Leave Days"));
        activityTab.add(createLeaveOption("Parental Leave"));
        activityTab.add(createLeaveOption("Vacation Leave"));

        // Notifications Tab Content
        JPanel notificationsTab = new JPanel();
        notificationsTab.setLayout(new BorderLayout());
        JLabel notificationsLabel = new JLabel("No new notifications", JLabel.CENTER);
        notificationsLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        notificationsTab.add(notificationsLabel, BorderLayout.CENTER);

        // Add Tabs
        tabbedPane.addTab("Activity", activityTab);
        tabbedPane.addTab("Notifications", notificationsTab);

        // Add User Info and Tabs to Middle Panel
        middlePanel.add(userInfoPanel, BorderLayout.NORTH);
        middlePanel.add(tabbedPane, BorderLayout.CENTER);

        // Add Panels to Frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);

        // Make Frame Visible
        frame.setVisible(true);
    }

    // Helper Method to Create Leave Options
    private JPanel createLeaveOption(String leaveType) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(leaveType);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        JButton selectButton = new JButton("Select");
        selectButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        panel.add(label);
        panel.add(selectButton);

        // Add an action for the button (currently just displays a message)
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(panel, "You selected: " + leaveType);
            }
        });

        return panel;
    }
}