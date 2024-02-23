/**
 *
 *  @author Tautkevychius Maksym S26871
 *
 */

package zad2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Finder {
    String fname;

    public Finder(String fname) {
        this.fname=fname;
    }

    public int getIfCount() throws IOException {
        return FindDif("if");
    }

    public int getStringCount(String variant) throws IOException {
        return FindDif(variant);
    }

    public int FindDif(String keyword) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
            String row;
            while ((row = br.readLine()) != null) {
                if (LineIsOk(row) && row.contains(keyword)) {
                    count++;
                }
            }
        }
        return count;
    }
    public boolean LineIsOk(String line) {
        return !(line.contains("//") || line.contains("/*") || line.contains("\""));
    }
}    
