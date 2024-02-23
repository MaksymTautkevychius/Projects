/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */

package zad1;


public class Main {

  public static void main(String[] args)  {
    CustomersPurchaseSortFind cpsf = new CustomersPurchaseSortFind();
    String fname = System.getProperty("user.home") + "/customers_en.txt";
    cpsf.readFile(fname);
    cpsf.showSortedBy("names");
    cpsf.showSortedBy("costs");

    String[] custSearch = { "c00001", "c00002" };

    for (String id : custSearch) {
      cpsf.showPurchaseFor(id);
    }
  }

}
