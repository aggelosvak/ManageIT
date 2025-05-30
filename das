[1mdiff --git a/src/coupon/RewardSystemUI.java b/src/coupon/RewardSystemUI.java[m
[1mindex b2472e1..373f5e2 100644[m
[1m--- a/src/coupon/RewardSystemUI.java[m
[1m+++ b/src/coupon/RewardSystemUI.java[m
[36m@@ -1,126 +1,97 @@[m
 package coupon;[m
 [m
[31m-import employee.Employee;[m
[31m-import notification.NotificationService;[m
[31m-[m
 import javax.swing.*;[m
 import java.awt.*;[m
[31m-import java.util.List;[m
[31m-[m
[31m-public class RewardSystemUI extends JFrame {[m
[31m-    private final coupon.RewardService rewardService;[m
[31m-    private final Employee employee;[m
[31m-[m
[31m-    public RewardSystemUI(coupon.RewardService rewardService, Employee employee) {[m
[31m-        this.rewardService = rewardService;[m
[31m-        this.employee = employee;[m
[31m-        initializeUI();[m
[31m-    }[m
[31m-[m
[31m-    private void initializeUI() {[m
[31m-        setTitle("Reward System");[m
[31m-        setSize(500, 400);[m
[31m-        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);[m
[31m-        setLocationRelativeTo(null);[m
[31m-[m
[31m-        JPanel panel = new JPanel();[m
[31m-        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));[m
[31m-[m
[31m-        JLabel pointsLabel = new JLabel("Your Points: " + employee.getPoints());[m
[31m-        pointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);[m
[31m-        panel.add(pointsLabel);[m
[31m-[m
[31m-        // Rewards Section[m
[31m-        JLabel rewardsLabel = new JLabel("Available Rewards:");[m
[31m-        rewardsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);[m
[31m-        panel.add(rewardsLabel);[m
[31m-[m
[31m-        JPanel rewardsPanel = new JPanel(new GridLayout(0, 1));[m
[31m-        for (Reward reward : rewardService.getAvailableRewards()) {[m
[31m-            JButton rewardButton = new JButton(reward.toString());[m
[31m-            rewardButton.setEnabled(rewardService.redeemReward(employee, reward));[m
[31m-            rewardButton.addActionListener(e -> handleRewardRedemption(reward));[m
[31m-            rewardsPanel.add(rewardButton);[m
[31m-        }[m
[31m-        panel.add(new JScrollPane(rewardsPanel));[m
[31m-[m
[31m-        // Coupons Section[m
[31m-        JLabel couponsLabel = new JLabel("Available Coupons:");[m
[31m-        couponsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);[m
[31m-        panel.add(couponsLabel);[m
[32m+[m[32mimport java.awt.event.ActionEvent;[m
[32m+[m[32mimport java.awt.event.ActionListener;[m
[32m+[m[32mimport employee.Employee;[m
[32m+[m[32mimport mainapp.MainApplicationUI;[m
 [m
[31m-        JPanel couponsPanel = new JPanel(new GridLayout(0, 1));[m
[31m-        for (Coupon coupon : rewardService.getAvailableCoupons()) {[m
[31m-            JButton couponButton = new JButton(coupon.toString());[m
[31m-            couponButton.setEnabled(rewardService.canRedeemCoupon(employee, coupon));[m
[31m-            couponButton.addActionListener(e -> handleCouponRedemption(coupon));[m
[31m-            couponsPanel.add(couponButton);[m
[31m-        }[m
[31m-        panel.add(new JScrollPane(couponsPanel));[m
[32m+[m[32mpublic class RewardSystemUI extends JPanel {[m
 [m
[31m-        add(panel);[m
[31m-    }[m
[31m-[m
[31m-    private void handleRewardRedemption(Reward reward) {[m
[31m-        int confirmation = JOptionPane.showConfirmDialog([m
[31m-                this,[m
[31m-                "Do you want to redeem the reward: " + reward.getName() + "?",[m
[31m-                "Confirm Redemption",[m
[31m-                JOptionPane.YES_NO_OPTION[m
[31m-        );[m
[32m+[m[32m    private final RewardService rewardService;[m
[32m+[m[32m    private final Employee employee;[m
 [m
[31m-        if (confirmation == JOptionPane.YES_OPTION) {[m
[31m-            boolean success = rewardService.redeemReward(employee, reward);[m
[31m-            if (success) {[m
[31m-                JOptionPane.showMessageDialog(this, "Reward redeemed successfully!");[m
[31m-                dispose();[m
[31m-            } else {[m
[31m-                JOptionPane.showMessageDialog(this, "Insufficient points for this reward.");[m
[32m+[m[32m    public RewardSystemUI(RewardService rewardService, Employee employee) {[m
[32m+[m[32m        this.rewardService = rewardService; // Inject RewardService instance[m
[32m+[m[32m        this.employee = employee;           // Inject logged-in Employee instance[m
[32m+[m
[32m+[m[32m        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));[m
[32m+[m
[32m+[m[32m        JLabel titleLabel = new JLabel("Reward System", SwingConstants.CENTER);[m
[32m+[m[32m        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));[m
[32m+[m[32m        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);[m
[32m+[m
[32m+[m[32m        JButton redeemRewardBtn = new JButton("Redeem Reward");[m
[32m+[m[32m        redeemRewardBtn.setAlignmentX(Component.CENTER_ALIGNMENT);[m
[32m+[m[32m        redeemRewardBtn.addActionListener(new ActionListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void actionPerformed(ActionEvent e) {[m
[32m+[m[32m                if (rewardService != null && employee != null) {[m
[32m+[m[32m                    java.util.List<Reward> rewards = rewardService.getAvailableRewards();[m
[32m+[m
[32m+[m[32m                    if (rewards.isEmpty()) {[m
[32m+[m[32m                        JOptionPane.showMessageDialog([m
[32m+[m[32m                                RewardSystemUI.this,[m
[32m+[m[32m                                "No rewards available for redemption at this time.",[m
[32m+[m[32m                                "No Rewards",[m
[32m+[m[32m                                JOptionPane.WARNING_MESSAGE[m
[32m+[m[32m                        );[m
[32m+[m[32m                        return;[m
[32m+[m[32m                    }[m
[32m+[m
[32m+[m[32m                    Reward selectedReward = (Reward) JOptionPane.showInputDialog([m
[32m+[m[32m                            RewardSystemUI.this,[m
[32m+[m[32m                            "Select a reward to redeem:",[m
[32m+[m[32m                            "Reward Redemption",[m
[32m+[m[32m                            JOptionPane.PLAIN_MESSAGE,[m
[32m+[m[32m                            null,[m
[32m+[m[32m                            rewards.toArray(),[m
[32m+[m[32m                            null[m
[32m+[m[32m                    );[m
[32m+[m
[32m+[m[32m                    if (selectedReward == null) return;[m
[32m+[m
[32m+[m[32m                    if (rewardService.redeemReward(employee, selectedReward)) {[m
[32m+[m[32m                        JOptionPane.showMessageDialog([m
[32m+[m[32m                                RewardSystemUI.this,[m
[32m+[m[32m                                "Successfully redeemed the reward: " + selectedReward.getName(),[m
[32m+[m[32m                                "Redemption Successful",[m
[32m+[m[32m                                JOptionPane.INFORMATION_MESSAGE[m
[32m+[m[32m                        );[m
[32m+[m[32m                    } else {[m
[32m+[m[32m                        JOptionPane.showMessageDialog([m
[32m+[m[32m                                RewardSystemUI.this,[m
[32m+[m[32m                                "You do not have enough points to redeem this reward.",[m
[32m+[m[32m                                "Redemption Failed",[m
[32m+[m[32m                                JOptionPane.ERROR_MESSAGE[m
[32m+[m[32m                        );[m
[32m+[m[32m                    }[m
[32m+[m[32m                } else {[m
[32m+[m[32m                    JOptionPane.showMessageDialog([m
[32m+[m[32m                            RewardSystemUI.this,[m
[32m+[m[32m                            "Reward service or employee data is not available.",[m
[32m+[m[32m                            "Error",[m
[32m+[m[32m                            JOptionPane.ERROR_MESSAGE[m
[32m+[m[32m                    );[m
[32m+[m[32m                }[m
             }[m
[31m-        }[m
[31m-    }[m
[31m-[m
[31m-    private void handleCouponRedemption(Coupon coupon) {[m
[31m-        int confirmation = JOptionPane.showConfirmDialog([m
[31m-                this,[m
[31m-                "Do you want to redeem the coupon: " + coupon.getDescription() +[m
[31m-                        " (Code: " + coupon.getCode() + ")?",[m
[31m-                "Confirm Redemption",[m
[31m-                JOptionPane.YES_NO_OPTION[m
[31m-        );[m
[31m-[m
[31m-        if (confirmation == JOptionPane.YES_OPTION) {[m
[31m-            boolean success = rewardService.redeemCoupon(employee, coupon);[m
[31m-            if (success) {[m
[31m-                JOptionPane.showMessageDialog(this, "Coupon redeemed successfully!");[m
[31m-                dispose();[m
[31m-            } else {[m
[31m-                JOptionPane.showMessageDialog(this, "Insufficient points for this coupon.");[m
[32m+[m[32m        });[m
[32m+[m
[32m+[m[32m        JButton backButton = new JButton("Back to Dashboard");[m
[32m+[m[32m        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);[m
[32m+[m[32m        backButton.addActionListener(e -> {[m
[32m+[m[32m            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(RewardSystemUI.this);[m
[32m+[m[32m            if (parentFrame instanceof MainApplicationUI) {[m
[32m+[m[32m                ((MainApplicationUI) parentFrame).switchScreen("ManagerLandingPage");[m
             }[m
[31m-        }[m
[31m-    }[m
[31m-[m
[31m-    public static void main(String[] args) {[m
[31m-        // Mock data[m
[31m-        Employee employee = new Employee("1", "Jane Doe", 1200, 0, "ABC", 0, 50000);[m
[31m-[m
[31m-        // Rewards and Coupons[m
[31m-        List<Reward> rewards = List.of([m
[31m-                new Reward(1, "Gift Card", "A prepaid gift card you can use anywhere.", 800),[m
[31m-                new Reward(2, "Extra Leave Day", "Get an additional day of paid leave.", 1000)[m
[31m-        );[m
[31m-[m
[31m-[m
[31m-        List<Coupon> coupons = List.of([m
[31m-                new Coupon(1, "LUNCH50", "50% off on lunch", 500),[m
[31m-                new Coupon(2, "MOVIE20", "20% off on movie tickets", 700)[m
[31m-        );[m
[31m-[m
[31m-// Instantiate RewardService with the lists[m
[31m-        RewardService rewardService = new RewardService(rewards, coupons, new NotificationService());[m
[31m-[m
[31m-// Launch the User Interface[m
[31m-        SwingUtilities.invokeLater(() -> new RewardSystemUI(rewardService, employee).setVisible(true));[m
[31m-[m
[32m+[m[32m        });[m
[32m+[m
[32m+[m[32m        add(Box.createRigidArea(new Dimension(0, 20)));[m
[32m+[m[32m        add(titleLabel);[m
[32m+[m[32m        add(Box.createRigidArea(new Dimension(0, 20)));[m
[32m+[m[32m        add(redeemRewardBtn);[m
[32m+[m[32m        add(Box.createRigidArea(new Dimension(0, 20)));[m
[32m+[m[32m        add(backButton);[m
     }[m
[31m-}[m
\ No newline at end of file[m
[32m+[m[32m}[m
[1mdiff --git a/src/mainapp/MainApplicationUI.java b/src/mainapp/MainApplicationUI.java[m
[1mindex 0c6088d..3afb803 100644[m
[1m--- a/src/mainapp/MainApplicationUI.java[m
[1m+++ b/src/mainapp/MainApplicationUI.java[m
[36m@@ -1,4 +1,54 @@[m
 package mainapp;[m
 [m
[31m-public class MainApplicationUI {[m
[31m-}[m
[32m+[m[32mimport javax.swing.*;[m
[32m+[m[32mimport java.awt.*;[m
[32m+[m[32mimport coupon.RewardService;[m
[32m+[m[32mimport coupon.RewardSystemUI;[m
[32m+[m[32mimport employee.Employee;[m
[32m+[m[32mimport manager.ManagerLandingPage;[m
[32m+[m
[32m+[m[32mpublic class MainApplicationUI extends JFrame {[m
[32m+[m
[32m+[m[32m    private CardLayout cardLayout;[m
[32m+[m[32m    private JPanel contentPanel;[m
[32m+[m
[32m+[m[32m    public MainApplicationUI() {[m
[32m+[m[32m        setTitle("Main Application");[m
[32m+[m[32m        setSize(600, 400);[m
[32m+[m[32m        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);[m
[32m+[m
[32m+[m[32m        // Initialize CardLayout and Main Content Panel[m
[32m+[m[32m        cardLayout = new CardLayout();[m
[32m+[m[32m        contentPanel = new JPanel(cardLayout);[m
[32m+[m
[32m+[m[32m        // Create instances of RewardService and Employee[m
[32m+[m[32m        RewardService rewardService = new RewardService(); // Assuming RewardService has a no-arg constructor[m
[32m+[m[32m        Employee loggedInEmployee = new Employee("E001", "John Doe"); // Replace with actual employee data[m
[32m+[m
[32m+[m[32m        // Add panels/pages to the content panel[m
[32m+[m[32m        JPanel managerLandingPage = new ManagerLandingPage(this); // Pass MainApplicationUI reference[m
[32m+[m[32m        JPanel rewardSystemPage = new RewardSystemUI(rewardService, loggedInEmployee); // Properly pass data[m
[32m+[m
[32m+[m[32m        // Add the panels to CardLayout[m
[32m+[m[32m        contentPanel.add(managerLandingPage, "ManagerLandingPage");[m
[32m+[m[32m        contentPanel.add(rewardSystemPage, "RewardSystemPage");[m
[32m+[m
[32m+[m[32m        // Add content panel to the JFrame[m
[32m+[m[32m        add(contentPanel);[m
[32m+[m
[32m+[m[32m        // Set the initial screen to ManagerLandingPage[m
[32m+[m[32m        cardLayout.show(contentPanel, "ManagerLandingPage");[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    // Method to switch between screens[m
[32m+[m[32m    public void switchScreen(String screenName) {[m
[32m+[m[32m        cardLayout.show(contentPanel, screenName);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public static void main(String[] args) {[m
[32m+[m[32m        SwingUtilities.invokeLater(() -> {[m
[32m+[m[32m            MainApplicationUI app = new MainApplicationUI();[m
[32m+[m[32m            app.setVisible(true);[m
[32m+[m[32m        });[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/src/manager/ManagerLandingPage.java b/src/manager/ManagerLandingPage.java[m
[1mindex ef26f03..c6ca482 100644[m
[1m--- a/src/manager/ManagerLandingPage.java[m
[1m+++ b/src/manager/ManagerLandingPage.java[m
[36m@@ -1,7 +1,9 @@[m
[32m+[m[32mpackage manager;[m
[32m+[m
 import javax.swing.*;[m
 import java.awt.*;[m
[31m-import java.awt.event.ActionEvent;[m
[31m-import java.awt.event.ActionListener;[m
[32m+[m
[32m+[m[32mimport mainapp.MainApplicationUI;[m
 [m
 public class ManagerLandingPage extends JPanel {[m
 [m
[36m@@ -32,9 +34,9 @@[m [mpublic class ManagerLandingPage extends JPanel {[m
         JButton logoutBtn = new JButton("Logout");[m
         logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);[m
         logoutBtn.addActionListener(e -> {[m
[31m-            // Example logout action (return to ManagerLandingPage)[m
[32m+[m[32m            // Example logout action (return to manager.ManagerLandingPage)[m
             JOptionPane.showMessageDialog(mainApp, "Logged out successfully.");[m
[31m-            mainApp.switchScreen("ManagerLandingPage");[m
[32m+[m[32m            mainApp.switchScreen("manager.ManagerLandingPage");[m
         });[m
 [m
         // Add components to the panel[m
