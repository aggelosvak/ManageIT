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

    public boolean redeemCoupon(Employee employee, Coupon coupon) {
        if (employee.getPoints() >= coupon.getRequiredPoints()) {
            employee.setPoints(employee.getPoints() - coupon.getRequiredPoints());
            notificationService.notify("Coupon Redeemed", "You redeemed: " + coupon.getDescription());
            return true;
        }
        return false;
    }


    public boolean redeemReward(Employee employee, Reward reward) {
        if (employee.getPoints() >= reward.getRequiredPoints()) {
            employee.setPoints(employee.getPoints() - reward.getRequiredPoints());
            notificationService.notify("Reward Redeemed", "You redeemed: " + reward.getName());
            return true;
        }
        return false;
    }

}