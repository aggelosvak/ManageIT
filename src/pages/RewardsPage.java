package pages;

import coupon.Coupon;
import coupon.Reward;
import coupon.RewardService;
import employee.Employee;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RewardsPage extends JPanel {

    private final Employee employee;
    private final RewardService rewardService;
    private JLabel pointsLabel; // Displays available points

    public RewardsPage(Employee employee, RewardService rewardService) {
        this.employee = employee;
        this.rewardService = rewardService;

        setLayout(new BorderLayout());

        // Page Title and Points Label
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("Available Rewards and Coupons", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Available Points Label
        pointsLabel = new JLabel("Available Points: " + employee.getPoints(), SwingConstants.CENTER);
        pointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        pointsLabel.setForeground(Color.BLUE);

        headerPanel.add(titleLabel);
        headerPanel.add(pointsLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Rewards and Coupons
        JPanel rewardsPanel = new JPanel();
        rewardsPanel.setLayout(new BoxLayout(rewardsPanel, BoxLayout.Y_AXIS));

        List<Reward> rewards = rewardService.getAvailableRewards();
        for (Reward reward : rewards) {
            JButton redeemButton = new JButton(reward.toString());
            redeemButton.addActionListener(e -> {
                boolean success = rewardService.redeemReward(employee, reward);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Successfully redeemed: " + reward.getName());
                    updatePointsLabel();
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough points for: " + reward.getName());
                }
            });
            rewardsPanel.add(redeemButton);
        }

        List<Coupon> coupons = rewardService.getAvailableCoupons();
        for (Coupon coupon : coupons) {
            JButton redeemButton = new JButton(coupon.toString());
            redeemButton.addActionListener(e -> {
                boolean success = rewardService.redeemCoupon(employee, coupon);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Successfully redeemed coupon: " + coupon.getDescription());
                    updatePointsLabel();
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough points for coupon: " + coupon.getDescription());
                }
            });
            rewardsPanel.add(redeemButton);
        }

        add(new JScrollPane(rewardsPanel), BorderLayout.CENTER);
    }

    private void updatePointsLabel() {
        pointsLabel.setText("Available Points: " + employee.getPoints());
    }
}