import models.BasicCard;
import models.BronzeCard;
import models.GoldCard;
import models.SilverCard;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<BasicCard, Double> sales = new LinkedHashMap<>();

        try {
            GoldCard bronzeCardNegativeTurnover = new GoldCard(222, -150);
            double purchaseValueNegTurnover = 222.20;
            sales.put(bronzeCardNegativeTurnover, purchaseValueNegTurnover);

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        BronzeCard bronzeCard = new BronzeCard(0, 0);
        double purchaseValue = 150.00;
        sales.put(bronzeCard, purchaseValue);

        SilverCard silverCard = new SilverCard(10, 600);
        double purchaseValue1 = 850;
        sales.put(silverCard, purchaseValue1);

        GoldCard goldCard = new GoldCard(100, 1500);
        double purchaseValue2 = 1300;
        sales.put(goldCard, purchaseValue2);

        sales.forEach((key, value) -> {
            double discountAmount = value * key.getDiscount();

            System.out.printf("Purchase value: $%.2f%n", value);
            System.out.printf("Discount rate: %.1f%%%n", key.getDiscount() * 100);
            System.out.printf("Discount: $%.2f%n", discountAmount);
            System.out.printf("Total: $%.2f%n", value - discountAmount);
            System.out.println();
        });


    }
}
