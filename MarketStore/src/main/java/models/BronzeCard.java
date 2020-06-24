package models;

public class BronzeCard extends BasicCard {
    private double discount;

    public BronzeCard(int ownerId, double lastMonthTurnover) {
        super(ownerId, lastMonthTurnover);
        this.setDiscount(lastMonthTurnover);
    }

    @Override
    public double getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(double lastMonthTurnover) {
        if (lastMonthTurnover >= 100 && lastMonthTurnover <= 300){
            this.discount = 0.01;
        }else if (lastMonthTurnover > 300){
            this.discount = 0.025;
        }else{
            this.discount = 0;
        }
    }
}
