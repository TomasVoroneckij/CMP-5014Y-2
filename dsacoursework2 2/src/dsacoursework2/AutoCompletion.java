/*
 * Word completion program
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

    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        DictionaryFinder df = new DictionaryFinder();
        DictionaryFinder df1 = new DictionaryFinder();
        //printwriter to save the output to "lotrMatches.csv"
        PrintWriter pw = new PrintWriter("lotrMatches.csv");
        //ArrayList in that reads all the words from "lotr.csv"
        ArrayList<String> in = readWordsFromCSV("lotr.csv");
        //form a dictionary of ArrayList in
        df.formDictionary(in);
        ArrayList<String> LotrQueries = new ArrayList();
        ArrayList<Integer> test = new ArrayList();
        //add all the prefixes LotrQueries ArrayList
        LotrQueries.addAll(readWordsFromCSV("lotrQueries.csv"));
        //form a dictionary of prefixes 
        df1.formDictionary(LotrQueries);
        Trie wordstrie = new Trie();
        HashMap<String, Integer> words = new HashMap<String, Integer>();
        NavigableMap<String, Integer> storeAuto
                = new TreeMap<String, Integer>();
        NavigableMap<String, Integer> storeAutoTemp
                = new TreeMap<String, Integer>();
        //used to count frequency
        int count = 0;
        //iterate through all the words in ArrayList in from "lotr.csv"
        for (int i = 0; i < in.size(); i++) {
            //add those words to the trie
            wordstrie.add(in.get(i));

        }
        //
        List<String> lotr = new ArrayList<>();
        Trie temp;
        String prefix;
        String auto;
        //iterate through arraylist in to put the words and their frequencies
        //to hashmap words
        for (String str : in) {
            if (words.containsKey(str)) {
                words.put(str, words.get(str) + 1);
            } else {
                words.put(str, 1);
            }
        }
        //iterate through all the prefixes
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
                //iterate through all the words from entry set
                for (Map.Entry<String, Integer> entry : words.entrySet()) {
                    //if words that are in auto equal to the words in entry map
                    if (auto.equals(entry.getKey())) {
                        //store those words in a storeAuto map
                        storeAuto.put(entry.getKey(), entry.getValue());

                    }

                }

            }

        }

        int counter = 0;
        int numberOfPrefixes = 2;
        //iterate through all the words from storeAutp
        for (Map.Entry<String, Integer> entry : storeAuto.entrySet()) {
            //if words are equal to the first entry of StoreAuto
            if (entry.equals(storeAuto.firstEntry())) {
                //store them into storeAuto temp
                storeAutoTemp.put(entry.getKey(), entry.getValue());
            } //if prefixes are equal and count is less than 3 words
            else if (storeAutoTemp.lastEntry().getKey().substring(0, 2).equals
            (entry.getKey().substring(0, 2)) && counter < numberOfPrefixes) {
                //store words into storeAuto temp
                storeAutoTemp.put(entry.getKey(), entry.getValue());
                //increase counter by 1
                counter++;
            } //if prefixes are not equal to each other
            else if (!(storeAutoTemp.lastEntry().getKey().substring(0, 2).equals
            (entry.getKey().substring(0, 2)))) {
                //set counter to 0
                counter = 0;
                //store words into storeAuto temp
                storeAutoTemp.put(entry.getKey(), entry.getValue());
            }

        }
        //create an empty string
        String key = "  ";
        //iterate through all the words that are in the storeAutoTemp map 
        for (Map.Entry<String, Integer> entry : storeAutoTemp.entrySet()) {
            //if the prefix of key is not equal to entry map prefix,which store
            //storeAuto words
            if (!(key.substring(0, 2).equals(entry.getKey().substring(0, 2)))) {
                //if frequency is not equal 0
                if (count != 0) {
                    //add the frequency to test arraylist
                    test.add(count);

                }
                count = 0;
            }
            //get all the keys from entry map that store storeAuto words
            key = entry.getKey();
            //get all the values of keys from entry map
            Integer value = entry.getValue();
            //counts the frequency of each prefix 
            count += value;
            //if entry map equals last entry of th, add it
            if (entry.equals(storeAutoTemp.lastEntry())) {
                test.add(count);
            }

        }
        //attempt to sort
        Map.Entry<String, Integer> previousEntry = null;
        Map.Entry<String, Integer> tempEntry;
        for (Map.Entry<String, Integer> entry : storeAutoTemp.entrySet()) {

            if (!entry.equals(storeAutoTemp.firstEntry())) {
                if (entry.getKey().substring(0, 2).equals(previousEntry.getKey()
                        .substring(0, 2)) && entry.getValue() > 
                        previousEntry.getValue()) {
                    tempEntry = previousEntry;
                    previousEntry = entry;
                    entry = tempEntry;

                }
            } else {
                previousEntry = entry;
            }

        }
        //initialise an empty string
        key = "  ";
        //used to count for probability
        double probability;
        //i is used to switch between prefixes
        int i = -1;
        //iterate through all the words that are in the storeAutoTemp map 
        for (Map.Entry<String, Integer> entry : storeAutoTemp.entrySet()) {
            //if the prefix of key is not equal to entry map prefix,which store
            //storeAuto words
            if (!(key.substring(0, 2).equals(entry.getKey().substring(0, 2)))) {
                //used to separate words with different prefixes
                i++;
                //System.out.println(keyCount);
            }
            //frequency of each prefixes
            double total = test.get(i);
            //get the key value
            key = entry.getKey();
            //get value of key
            double value = entry.getValue();
            //count the probability
            probability = value / total;
            //print out key and their probability
            System.out.println(key + " : " + probability);
            //print to "lotrMatches.csv" using printwriter
            pw.print(key + " : " + probability);
            pw.print("\n");

        }
        pw.close();

    }

}
