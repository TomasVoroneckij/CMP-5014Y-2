package dsacoursework2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author ngx16ybu
 */
public class DictionaryFinder {

    private SortedMap<String, Integer> treeMap;

    public DictionaryFinder() {
        treeMap = new TreeMap<>();
    }

    public void setTreeMap(SortedMap<String, Integer> treeMap) {
        this.treeMap = treeMap;
    }

    /**
     * Reads all the words in a comma separated text document into an Array
     *
     * @param f
     */
    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        sc.useDelimiter(" |,");
        ArrayList<String> words = new ArrayList<>();
        String str;
        while (sc.hasNext()) {
            str = sc.next();
            str = str.trim();
            str = str.toLowerCase();
            words.add(str);
        }
        //System.out.println(words.toString());
        return words;
    }

    public static void saveCollectionToFile(Collection<?> c, String file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Object w : c) {
            printWriter.println(w.toString());
        }
        printWriter.close();
    }

    public void formDictionary(List<String> words) {
        for (String word : words) {
            if (!(this.treeMap.containsKey(word))) {
                this.treeMap.put(word, 1);
            } else {
                this.treeMap.put(word, treeMap.get(word) + 1);
            }

        }
    }

    public void saveToFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (PrintWriter printWriter = new PrintWriter("output.csv")) {
            for (String w : treeMap.keySet()) {
                sb.append(w).append(" ").append(treeMap.get(w)).append("\n");
            }
            printWriter.write(sb.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        DictionaryFinder df = new DictionaryFinder();
        ArrayList<String> in = readWordsFromCSV("testDocument.txt");
        df.formDictionary(in);
        df.saveToFile();
    }
}
