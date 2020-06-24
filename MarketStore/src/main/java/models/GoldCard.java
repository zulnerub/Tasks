package models;

public class GoldCard  extends BasicCard{

    private double discount;

    public GoldCard(int ownerId, double lastMonthTurnover) {
        super(ownerId, lastMonthTurnover);
        this.setDiscount(lastMonthTurnover);
    }

    @Override
    public double getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(double lastMonthTurnover) {
        double nGrowth = Math.round(lastMonthTurnover / 100);
        nGrowth /= 100;
        this.discount = Math.min((0.02 + nGrowth), 0.10);
    }
}
