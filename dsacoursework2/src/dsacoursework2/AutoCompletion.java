/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsacoursework2;

import static dsacoursework2.DictionaryFinder.readWordsFromCSV;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author ngx16ybu
 */
public class AutoCompletion {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        DictionaryFinder df = new DictionaryFinder();
        DictionaryFinder df1 = new DictionaryFinder();
        PrintWriter printWriter = new PrintWriter("lotrMatches.csv");
        ArrayList<String> in = readWordsFromCSV("lotr.csv");
        df.formDictionary(in);
        //df.saveToFile();
        ArrayList<String> LotrQueries = new ArrayList();
        ArrayList<String> test = new ArrayList();
        LotrQueries.addAll(readWordsFromCSV("lotrQueries.csv"));
        df1.formDictionary(LotrQueries);
        Trie wordstrie = new Trie();
        HashMap<String, Integer> words = new HashMap<String, Integer>();
        int count = 0;
        for (int i = 0; i < in.size(); i++) {
            wordstrie.add(in.get(i));
        }
        List<String> lotr = new ArrayList<String>();
        Trie temp = new Trie();
        String prefix = new String();
        String auto = new String();

        //System.out.println(wordstrie.getAllWords());
        for (int i = 0; i < LotrQueries.size(); i++) {
            //get all the prefixes and add them to the lotr List
            lotr.add(LotrQueries.get(i));
            //Using the getSubtrie method to get the sub trie of the words   
            temp = wordstrie.getSubTrie(lotr.get(i));
            //get all the words that start with associated prefixes
            List list = temp.getAllWords();

            //get all the prefixes into prefix String
            prefix = lotr.get(i);
            //iterate through the list of words
            for (int j = 0; j < list.size(); j++) {
                //adds the prefix to the other part of the word
                auto = prefix + list.get(j);
                
                test.add(auto);
                //System.out.println(test);
                //System.out.println(auto);
                // System.out.println(words.get());
                // words.put(auto, count);

//            List<String> myList = new ArrayList<String>(Arrays.asList(auto.split("\\s")));
//           // System.out.println("-------");
//            System.out.println(auto);
//            Set<String> uniqueSet = new HashSet<String>(myList);
//	for (String temp2: uniqueSet) {
//		//System.out.println(temp2 + ": " + Collections.frequency(in, temp2));
//	}
            }
        }
        for (String str : in) {
            if (words.containsKey(str)) {
                words.put(str, words.get(str) + 1);
            } else {
                words.put(str, 1);
            }
        }
        
        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            //System.out.println(entry.getKey() + " = " + entry.getValue());
            String key = entry.getKey();
            for(int i =0;i<test.size();i++){
            if(key.equals(test.get(i))){
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }
        }
//      for(String temp2:in){
//    Integer count1 = words.get(temp);
//		words.put(temp2, (count1 == null) ? 1 : count1 + 1);
//}
//            System.out.println(words);
    }

}
