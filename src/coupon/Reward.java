package coupon;

public class Reward {
    private int id;
    private String name;
    private String description;
    private int requiredPoints;

    public Reward(int id, String name, String description, int requiredPoints) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredPoints = requiredPoints;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    @Override
    public String toString() {
        return name + ": " + description + " (Points: " + requiredPoints + ")";
    }
}