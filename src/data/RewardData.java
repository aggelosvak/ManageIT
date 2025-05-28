package data;

import coupon.Reward;
import java.util.Arrays;
import java.util.List;

public class RewardData {
    public static final Reward REWARD_A = new Reward(
            1,
            "Gift Card",
            "A $25 gift card of your choice",
            200
    );

    public static final Reward REWARD_B = new Reward(
            2,
            "Movie Tickets",
            "A pair of movie tickets",
            150
    );

    public static final Reward REWARD_C = new Reward(
            3,
            "Coffee Mug",
            "A branded coffee mug",
            50
    );

    public static List<Reward> all() {
        return Arrays.asList(REWARD_A, REWARD_B, REWARD_C);
    }
}