package coupon;

public class Coupon {
    private int id;
    private String code;
    private String description;
    private int requiredPoints;

    public Coupon(int id, String code, String description, int requiredPoints) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.requiredPoints = requiredPoints;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    @Override
    public String toString() {
        return "[" + code + "] " + description + " (Points: " + requiredPoints + ")";
    }
}