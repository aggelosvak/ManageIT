package data;

import coupon.Coupon;

import java.util.Arrays;
import java.util.List;

public class CouponData {
    public static final Coupon COUPON_A = new Coupon(
            1,
            "10% Off",
            "10% off your next purchase",
            50
    );

    public static final Coupon COUPON_B = new Coupon(
            2,
            "20% Off",
            "20% off your next purchase",
            100
    );

    public static final Coupon COUPON_C = new Coupon(
            3,
            "Free Shipping",
            "Free shipping on your next order",
            75
    );

    public static List<Coupon> all() {
        return Arrays.asList(COUPON_A, COUPON_B, COUPON_C);
    }
}