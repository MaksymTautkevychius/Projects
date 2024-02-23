/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */

package zad1;


public class Purchase {
    private String customerId;
    private String fullName;
    private String commodityName;
    private double price;
    private double purchasedQuantity;

    public Purchase(String customerId, String fullName, String commodityName, double price, double purchasedQuantity) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.commodityName = commodityName;
        this.price = price;
        this.purchasedQuantity = purchasedQuantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public double getPrice() {
        return price;
    }

    public double Purchaseamount() {
        return purchasedQuantity;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s", customerId, fullName, commodityName, price, purchasedQuantity);
    }
}
