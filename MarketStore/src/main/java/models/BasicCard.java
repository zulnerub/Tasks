package models;

public abstract class BasicCard {
    private int ownerId;
    private double lastMonthTurnover;
    private double discount;

    public BasicCard(int ownerId, double lastMonthTurnover) {
        this.ownerId = ownerId;
        this.setLastMonthTurnover(lastMonthTurnover);
    }

    public double getLastMonthTurnover() {
        return lastMonthTurnover;
    }

    public void setLastMonthTurnover(double lastMonthTurnover) {
        if (lastMonthTurnover < 0){
            throw new IllegalArgumentException("Turnover can not be negative.");
        }
        this.lastMonthTurnover = lastMonthTurnover;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
