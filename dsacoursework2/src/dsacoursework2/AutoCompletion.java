/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsacoursework2;

import static dsacoursework2.DictionaryFinder.readWordsFromCSV;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 *
 * @author ngx16ybu
 */
public class AutoCompletion {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        DictionaryFinder df = new DictionaryFinder();
        DictionaryFinder df1 = new DictionaryFinder();
        PrintWriter pw = new PrintWriter("lotrMatches.csv");
        ArrayList<String> in = readWordsFromCSV("lotr.csv");
        df.formDictionary(in);
        df.saveToFile();
        ArrayList<String> LotrQueries = new ArrayList();
        ArrayList<Integer> test = new ArrayList();
        LotrQueries.addAll(readWordsFromCSV("lotrQueries.csv"));
        df1.formDictionary(LotrQueries);
        Trie wordstrie = new Trie();
        HashMap<String, Integer> words = new HashMap<String, Integer>();
        NavigableMap<String, Integer> storeAuto = new TreeMap<String, Integer>();
        // NavigableMap<String, Integer> sortedMap = storeAuto.descendingMap();

        //TreeMap<String, Integer> sortedMap = new TreeMap<String,Integer>(storeAuto);
        int count = 0;
        int keyCount = 0;
        for (int i = 0; i < in.size(); i++) {
            wordstrie.add(in.get(i));

        }
        List<String> lotr = new ArrayList<>();
        Trie temp;
        String prefix;
        String auto;

        for (String str : in) {
            if (words.containsKey(str)) {
                words.put(str, words.get(str) + 1);
            } else {
                words.put(str, 1);
            }
        }
        for (int i = 0; i < LotrQueries.size(); i++) {
            //get all the prefixes and add them to the lotr List
            lotr.add(LotrQueries.get(i));
            //Using the getSubtrie method to get the sub trie of the words   
            temp = wordstrie.getSubTrie(lotr.get(i));
            //get all the words that start with associated prefixes
            List<String> list = temp.getAllWords();
            //get all the prefixes into prefix String
            prefix = lotr.get(i);
            //iterate through the list of words
            for (int j = 0; j < list.size(); j++) {
                //adds the prefix to the other part of the word
                auto = prefix.trim() + list.get(j).trim();
                //System.out.println(auto);
                for (Map.Entry<String, Integer> entry : words.entrySet()) {
                    //System.out.println(auto);
                    if (auto.equals(entry.getKey())) {
                        
                        storeAuto.put(entry.getKey(), entry.getValue());

                    }

                }

            }

        }

        String key = "  ";

        for (Map.Entry<String, Integer> entry : storeAuto.entrySet()) {
            if (!(key.substring(0, 2).equals(entry.getKey().substring(0, 2)))) {

                if (count != 0) {

                    test.add(count);

                }

                count = 0;
            }
            key = entry.getKey();
            Integer value = entry.getValue();
            count += value;

            if (entry.equals(storeAuto.lastEntry())) {
                test.add(count);
            }

        }
        key = "  ";
        double probability;
        int i = -1;
        ArrayList<String> arrlist = new ArrayList<String>();

        for (Map.Entry<String, Integer> entry : storeAuto.entrySet()) {

            keyCount++;

            if (!(key.substring(0, 2).equals(entry.getKey().substring(0, 2)))) {

                i++;

            }

            double total = test.get(i);

            key = entry.getKey();

            double value = entry.getValue();
            probability = value / total;
            System.out.println(key + " : " + probability);
            pw.print(key + " : " + probability);
            pw.print("\n");

        }
        pw.close();

    }

}
