/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */

package zad1;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomersPurchaseSortFind {
    private List<Purchase> purchases;

    public void readFile(String filename) {
        purchases = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                Purchase purchase = new Purchase(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), Double.parseDouble(parts[4]));
                purchases.add(purchase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSortedBy(String sortBy) {
        List<Purchase> sortedList = new ArrayList<>(purchases);

        switch (sortBy) {
            case "names":
                sortedList.sort(Comparator.comparing(Purchase::getFullName).thenComparing(Purchase::getCustomerId));
                break;
            case "costs":
                sortedList.sort(Comparator.comparing((Purchase p) -> p.getPrice() * p.Purchaseamount())
                        .reversed()
                        .thenComparing(Purchase::getCustomerId));
                break;
            default:
                throw new IllegalArgumentException("Invalid sort option");
        }

        for (Purchase purchase : sortedList) {
            System.out.println(purchase);
        }
        System.out.println();
    }

    public void showPurchaseFor(String customerId) {
        System.out.println("customer " + customerId);
        for (Purchase purchase : purchases) {
            if (purchase.getCustomerId().equals(customerId)) {
                System.out.println(purchase);
            }
        }
        System.out.println();
    }
}
