/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */

package zad2;


import java.io.*;
import java.util.*;

public class Anagrams {
    private List<String> Words;
    private Map<String, List<String>> toMap;

    public Anagrams(String nameOfFile) throws FileNotFoundException {
        this.Words = getWords(nameOfFile);
        this.toMap = ListToMap();
    }

    public List<List<String>> getSortedByAnQty() {
        List<List<String>> sortedAnagrams = new ArrayList<>(toMap.values());
        sortedAnagrams.sort((list1, list2) -> {
            if (list1.size() != list2.size()) {
                return Integer.compare(list2.size(), list1.size());
            } else {
                return list1.get(0).compareTo(list2.get(0));
            }
        });
        return sortedAnagrams;
    }

    public String getAnagramsFor(String word) {
        List<String> anagrams = toMap.getOrDefault(word, Collections.emptyList());
        return word + ": " + anagrams;
    }

    private List<String> getWords(String Found) throws FileNotFoundException {
        List<String> words = new ArrayList<>();
        Scanner scanner = new Scanner(new File(Found));
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        scanner.close();
        return words;
    }

    private Map<String, List<String>> ListToMap() {
        Map<String, List<String>> map = new HashMap<>();
        for (String word : Words) {
            char[] byOne = word.toCharArray();
            Arrays.sort(byOne);
            String sortedWord = new String(byOne);
            map.computeIfAbsent(sortedWord, k -> new ArrayList<>()).add(word);
        }
        return map;
    }
}

