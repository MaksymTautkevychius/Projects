/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */
package zad1;


import java.util.ArrayList;
import java.util.List;
public class ListCreator<T> {
    public static void main(String[] args) {
       List<Integer> g = new ArrayList<Integer>();
    }
    List<T> input;
    public ListCreator(List<T> list) {
        this.input = list;
    }
    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }
    public ListCreator<T> when(Selector<T> sel) {
        List<T> select = new ArrayList<>();
        for (T i : this.input) {
        if(sel.select(i)) select.add(i);}
        this.input = select;
        return this;
    }
    public <S> List<S> mapEvery(Mapper<T, S> map) {
        List<S> mapping = new ArrayList<>();
        for (T elem : this.input) {
            mapping.add(map.map(elem));}
        return mapping;
    }
}  
