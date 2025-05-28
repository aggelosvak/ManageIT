package coupon;

import data.CouponData;
import data.RewardData;
import employee.Employee;
import notification.NotificationService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RewardSystemUI extends JFrame {
    private final RewardService rewardService;
    private final Employee employee;

    public RewardSystemUI(RewardService rewardService, Employee employee) {
        this.rewardService = rewardService;
        this.employee = employee;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Reward and Coupon System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        List<Reward> rewards = RewardData.all();
        List<Coupon> coupons = CouponData.all();

        JPanel rewardPanel = new JPanel();
        rewardPanel.setLayout(new BoxLayout(rewardPanel, BoxLayout.Y_AXIS));
        rewardPanel.setBorder(BorderFactory.createTitledBorder("Available Rewards"));

        for (Reward reward : rewards) {
            JButton rewardButton = new JButton(reward.getName() + " - Points Required: " + reward.getRequiredPoints());
            rewardButton.addActionListener(e -> handleRewardRedemption(reward));
            rewardPanel.add(rewardButton);
        }

        JPanel couponPanel = new JPanel();
        couponPanel.setLayout(new BoxLayout(couponPanel, BoxLayout.Y_AXIS));
        couponPanel.setBorder(BorderFactory.createTitledBorder("Available Coupons"));

        for (Coupon coupon : coupons) {
            JButton couponButton = new JButton(coupon.getDescription() + " - Points Required: " + coupon.getRequiredPoints());
            couponButton.addActionListener(e -> handleCouponRedemption(coupon));
            couponPanel.add(couponButton);
        }

        add(rewardPanel, BorderLayout.WEST);
        add(couponPanel, BorderLayout.EAST);
    }

    private void handleRewardRedemption(Reward reward) {
        boolean success = rewardService.redeemReward(employee, reward);
        if (success) {
            JOptionPane.showMessageDialog(this, "You successfully redeemed: " + reward.getName());
        } else {
            JOptionPane.showMessageDialog(this, "You do not have enough points to redeem: " + reward.getName());
        }
    }

    private void handleCouponRedemption(Coupon coupon) {
        boolean success = rewardService.redeemCoupon(employee, coupon);
        if (success) {
            JOptionPane.showMessageDialog(this, "You successfully redeemed: " + coupon.getDescription());
        } else {
            JOptionPane.showMessageDialog(this, "You do not have enough points to redeem: " + coupon.getDescription());
        }
    }

    public static void main(String[] args) {
        Employee mockEmployee = new Employee(1, "John Doe", null, null); // Company & JobPosition are null
        NotificationService mockNotificationService = new NotificationService();
        RewardService mockRewardService = new RewardService(
                RewardData.all(),
                CouponData.all(),
                mockNotificationService
        );

        SwingUtilities.invokeLater(() -> {
            RewardSystemUI frame = new RewardSystemUI(mockRewardService, mockEmployee);
            frame.setVisible(true);
        });
    }
}