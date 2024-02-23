/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */

package zad1;


import java.util.*;

public class Main {


  static List<String> getPricesInPLN(List<String> destinations, double xrate) {
    return ListCreator.collectFrom(destinations)
            .when(n ->n.startsWith("WAW"))
            .mapEvery(  n ->{
                            String[] part = n.split(" ");
                            int price_Pln=(int)xrate* Integer.parseInt(part[2]);
                        return "to " + part[1] + " - price in PLN:\t" + price_Pln;
            });
  }

  public static void main(String[] args) {
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
    for (String r : result) System.out.println(r);
  }
}
