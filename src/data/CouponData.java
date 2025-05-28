package data;

import coupon.Coupon;
import java.util.Arrays;
import java.util.List;

public class CouponData {
    public static final Coupon COUPON_A = new Coupon(
            1,
            "SAVE20",
            "20% discount on your next purchase",
            100
    );

    public static final Coupon COUPON_B = new Coupon(
            2,
            "FREESHIP",
            "Free shipping on your next order",
            50
    );

    public static final Coupon COUPON_C = new Coupon(
            3,
            "BOGO",
            "Buy one, get one free",
            200
    );

    public static List<Coupon> all() {
        return Arrays.asList(COUPON_A, COUPON_B, COUPON_C);
    }
}