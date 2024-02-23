/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */

package zad1;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class XList<Inner> extends ArrayList<Inner> {

    public XList() {
        super();
    }

    public XList(Inner... elements) {
        super(Arrays.asList(elements));
    }

    public XList(Collection<Inner> collection) {
        super(collection);
    }
    
    public static <Inner> XList<Inner> of(Inner... elements) {
        return new XList<>(elements);
    }

    public static <Inner> XList<Inner> of(Collection<Inner> collection) {
        return new XList<>(collection);
    }

    public void forEachWithIndex(BiConsumer<Inner, Integer> Letter) {
        for (int i = 0; i < size(); i++) {
            Letter.accept(get(i), i);
        }
    }

    public  static XList<String> charsOf(String data) {
        char[] chars = data.toCharArray();
        String[] characters = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            characters[i] = String.valueOf(chars[i]);
        }
        return new XList<>(characters);
    }

    public static XList<String> tokensOf(String str, String... separ) {
        if (separ.length == 0) {
            return new XList<>(str.split("\\s+"));
        } else {
            String sep = String.join("|", separ);
            return new XList<>(str.split(sep));
        }
    }
    public XList<Inner> unique() {
        ArrayList<Inner> inputSymbols = new ArrayList<>(this);
        Set<Inner> uniqueSymbols = new HashSet<>();
        XList<Inner> result = new XList<>();

        for (Inner symbol : inputSymbols) {
            if (uniqueSymbols.add(symbol)) {
                result.add(symbol);
            }
        }

        return result;
    }
    public XList<Inner> union(Object collection) {
        XList<Inner> result = new XList<>(this);
        if (collection instanceof Collection<?>) {result.addAll((Collection<? extends Inner>) collection);
        } else if (collection.getClass().isArray()) {
            int stream = Array.getLength(collection);
            for (int i = 0; i < stream; i++) {result.add((Inner) Array.get(collection, i));}
        }
        return result;
    }

    public XList<Inner> diff(Collection<Inner> collection) {
        XList<Inner> result = new XList<>(this);
        result.removeAll(collection);
        return result;
    }
}
