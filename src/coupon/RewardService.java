package coupon;

import employee.Employee;
import notification.Notification;
import notification.NotificationService;

import java.util.List;

public class RewardService {

    private final List<Reward> rewards;
    private final List<Coupon> coupons;
    private final NotificationService notificationService;

    public RewardService(List<Reward> rewards, List<Coupon> coupons, NotificationService notificationService) {
        this.rewards = rewards;
        this.coupons = coupons;
        this.notificationService = notificationService;
    }

    // Retrieve available rewards
    public List<Reward> getAvailableRewards() {
        return rewards;
    }

    // Retrieve available coupons
    public List<Coupon> getAvailableCoupons() {
        return coupons;
    }

    // Check if an employee has enough points to redeem a coupon
    public boolean canRedeemCoupon(Employee employee, Coupon coupon) {
        // Check if the employee has enough points to redeem the given coupon
        return employee.getPoints() >= coupon.getRequiredPoints();
    }

    // Redeem a coupon
    public boolean redeemCoupon(Employee employee, Coupon coupon) {
        if (canRedeemCoupon(employee, coupon)) {
            // Deduct the required points from the employee's points
            employee.setPoints(employee.getPoints() - coupon.getRequiredPoints());

            // Create a success message
            String message = "You have successfully redeemed the coupon: " + coupon.getDescription() +
                    " (Code: " + coupon.getCode() + ").";

            // Notify the employee
            Notification notification = new Notification(
                    (int) (Math.random() * 1000),  // Generate a random ID
                    java.time.LocalDateTime.now(),
                    employee.getEmployeeId(),      // Use int directly
                    "Coupon Redeemed",
                    message,
                    "INFO",
                    "High"
            );
            notificationService.sendNotification(notification);

            return true;
        }
        return false;
    }

    // Redeem a reward
    public boolean redeemReward(Employee employee, Reward reward) {
        if (employee.getPoints() >= reward.getRequiredPoints()) {

            employee.setPoints(employee.getPoints() - reward.getRequiredPoints());


            Notification notification = new Notification(
                    (int) (Math.random() * 1000),
                    java.time.LocalDateTime.now(),
                    employee.getEmployeeId(),
                    "Reward Redeemed",
                    "You have successfully redeemed the reward: " + reward.getName(),
                    "INFO",
                    "High"
            );
            notificationService.sendNotification(notification);
            return true;
        }
        return false;
    }
}