package models;

public class SilverCard extends BasicCard{
    private double discount;

    public SilverCard(int ownerId, double lastMonthTurnover) {
        super(ownerId, lastMonthTurnover);
        this.setDiscount(lastMonthTurnover);
    }

    @Override
    public double getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(double lastMonthTurnover) {
        if (lastMonthTurnover > 300){
            this.discount = 0.035;
        }else {
            this.discount = 0.02;
        }
    }
}
