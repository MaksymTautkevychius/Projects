/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */

package zad1;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Calc {
    private Map<String, BiFunction<BigDecimal, BigDecimal, BigDecimal>> operations;
        public Calc() {
        operations = new HashMap<>();
        operations.put("+", BigDecimal::add);
        operations.put("-", BigDecimal::subtract);
        operations.put("*", BigDecimal::multiply);
        operations.put("/", (a, b) -> a.divide(b, 10, BigDecimal.ROUND_HALF_UP));
    }
    public String doCalc(String arg) {
            try {
                String[] tokens = arg.split("\\s+");
                BigDecimal num1 = new BigDecimal(tokens[0]);
                BigDecimal num2 = new BigDecimal(tokens[2]);

                String op = tokens[1];
                BiFunction<BigDecimal, BigDecimal, BigDecimal> operation = operations.getOrDefault(op, (a, b) -> BigDecimal.ZERO);
                String result = String.valueOf(operation.apply(num1, num2));
                return result;
            }
    catch (Exception e) {
        return "Invalid command to calc";
    }
    }
}  
