package coupon;

import employee.Employee;
import notification.NotificationService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RewardSystemUI extends JFrame {
    private final coupon.RewardService rewardService;
    private final Employee employee;

    public RewardSystemUI(coupon.RewardService rewardService, Employee employee) {
        this.rewardService = rewardService;
        this.employee = employee;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Reward System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel pointsLabel = new JLabel("Your Points: " + employee.getPoints());
        pointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(pointsLabel);

        // Rewards Section
        JLabel rewardsLabel = new JLabel("Available Rewards:");
        rewardsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(rewardsLabel);

        JPanel rewardsPanel = new JPanel(new GridLayout(0, 1));
        for (Reward reward : rewardService.getAvailableRewards()) {
            JButton rewardButton = new JButton(reward.toString());
            rewardButton.setEnabled(rewardService.redeemReward(employee, reward));
            rewardButton.addActionListener(e -> handleRewardRedemption(reward));
            rewardsPanel.add(rewardButton);
        }
        panel.add(new JScrollPane(rewardsPanel));

        // Coupons Section
        JLabel couponsLabel = new JLabel("Available Coupons:");
        couponsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(couponsLabel);

        JPanel couponsPanel = new JPanel(new GridLayout(0, 1));
        for (Coupon coupon : rewardService.getAvailableCoupons()) {
            JButton couponButton = new JButton(coupon.toString());
            couponButton.setEnabled(rewardService.canRedeemCoupon(employee, coupon));
            couponButton.addActionListener(e -> handleCouponRedemption(coupon));
            couponsPanel.add(couponButton);
        }
        panel.add(new JScrollPane(couponsPanel));

        add(panel);
    }

    private void handleRewardRedemption(Reward reward) {
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Do you want to redeem the reward: " + reward.getName() + "?",
                "Confirm Redemption",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = rewardService.redeemReward(employee, reward);
            if (success) {
                JOptionPane.showMessageDialog(this, "Reward redeemed successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient points for this reward.");
            }
        }
    }

    private void handleCouponRedemption(Coupon coupon) {
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Do you want to redeem the coupon: " + coupon.getDescription() +
                        " (Code: " + coupon.getCode() + ")?",
                "Confirm Redemption",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = rewardService.redeemCoupon(employee, coupon);
            if (success) {
                JOptionPane.showMessageDialog(this, "Coupon redeemed successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient points for this coupon.");
            }
        }
    }
    }

//    public static void main(String[] args) {
//        // Mock data
//        Employee employee = new Employee("1", "Jane Doe", 1200, 0, "1", 0, 50000);
//
//        // Rewards and Coupons
//        List<Reward> rewards = List.of(
//                new Reward(1, "Gift Card", "A prepaid gift card you can use anywhere.", 800),
//                new Reward(2, "Extra Leave Day", "Get an additional day of paid leave.", 1000)
//        );
//
//
//        List<Coupon> coupons = List.of(
//                new Coupon(1, "LUNCH50", "50% off on lunch", 500),
//                new Coupon(2, "MOVIE20", "20% off on movie tickets", 700)
//        );
//
//// Instantiate RewardService with the lists
//        RewardService rewardService = new RewardService(rewards, coupons, new NotificationService());
//
//// Launch the User Interface
//        SwingUtilities.invokeLater(() -> new RewardSystemUI(rewardService, employee).setVisible(true));
//
//    }
//}