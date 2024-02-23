/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */

package zad1;


/*<--
 *  necessary imports
 */
public class Main {
  public static void main(String[] args) {
    /*<--
     *  definition of operations in the form of lambda expressions:
     *  - flines - returns a list of lines from a text file
     *  - join - combines a string list (returns a string of interconnected elements of the list)
     *  - collectInts - returns a list of integers contained in the string
     *  - sum - returns the sum of elements of a list of integers
     */

    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);  
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

  }
}
