package zad1;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListCreator<T>  {
   // public static void main(String[] args) {
   //     char[]a =new char[2];
   //     int b = a.length();
   // }

    public List<T> list;

    public ListCreator(List<T> list) {
        this.list = list;
    }

    public static <T> ListCreator<T> collectFrom(List<T> destinations) {
        return  new ListCreator<>(destinations);
    }
   public ListCreator<T> when(Predicate<T> rev)
   {
       List<T> filtered = new ArrayList<>();
       for (T i: list)
       {
          if (rev.test(i))
          {
              filtered.add(i);
          }
       }
       list=filtered;
       return this;
   }
    public <R> List<R> mapEvery(Function<T, R> mapper) {
        List<R> mappedList = new ArrayList<>();
        for (T item : list) {
            mappedList.add(mapper.apply(item));
        }

        return mappedList;
    }

}
