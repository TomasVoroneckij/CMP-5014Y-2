/*
 * Trie data structure
 */
package dsacoursework2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author ngx16ybu
 */
public class Trie extends TrieNode {

    TrieNode root, node;

    public Trie() {
        this.root = new TrieNode();
    }

    public void setRootNode() {
        this.root = node;
    }

    /**
     * Method to add a key to the trie
     *
     * @param key
     */
    public boolean add(String key) {
        //root trienode
        TrieNode currentNode = root;
        //iterate through all the keys 
        for (int i = 0; i < key.length(); i++) {
            //get their chars at i positions
            char current = key.charAt(i);
            if (currentNode != null) {
                
                TrieNode child = currentNode.getNode(current);
                if (child == null) {
                    currentNode.setNode(current);
                    child = currentNode.getNode(current);
                }
                currentNode = child;
            }
        }
        //if node is complete return false
        if (currentNode.isComplete()) {
            return false;
        }
        //set node as complete and return true
        currentNode.setComplete(true);
        return true;

    }

    /**
     * Method to check if the word passed in the trie is a whole word
     *
     * @param key
     */
    public boolean contains(String key) {
        // set current node to root
        TrieNode currentNode = root;
        //iterate through the key
        for (char c : key.toCharArray()) {
            //if next node is not null return false
            if (currentNode.getNode(c) == null) {
                return false;
            }
            //else if it exists, assign currentNode to it
            currentNode = currentNode.getNode(c);
        }
        //return whether the current Node is complete
        return currentNode.isComplete();
    }

    /**
     * Method to output the string by breadth first search
     *
     * @param key
     */
    public String outputBreadthFirstSearch() {
        //a string for the result
        String result = "";
        //initialiase a queue for the nodes
        Queue nodes = new LinkedList();
        //add a root node
        nodes.add(root);
        //iterate through nodes until they are empty
        while (!nodes.isEmpty()) {
            //get the first node
            TrieNode temp = (TrieNode) nodes.poll();
            //add all temp node letters to result
            result += temp.getChar();
            //iterate through every node
            for (TrieNode node : temp.getOffspring()) {
                //if node is not null
                if (node != null) {
                    //add a node to nodes linkedlist
                    nodes.add(node);
                }
            }
        }
        //return result
        return result.toLowerCase();
    }

    /**
     * Method to output the string by depth first search
     *
     * @param trienode
     * @return result
     */
    public String depthFirstSearch(TrieNode trienode) {
        //initialise an empty string
        String result = "";
        //iterate through every node
        for (TrieNode node : trienode.getOffspring()) {
            //if node is not null
            if (node != null) {
                //add all the nodes to result from depthfirstseach
                result += depthFirstSearch(node);
            }
        }
        //append all letters of trienode to result
        result += trienode.getChar();
        return result;
    }

    public String outputDepthFirstSearch() {
        //initialise an empty strin
        String result = "";
        //if root is not empty
        if (root != null) {
            //append result root from depthfirstsearch
            result += depthFirstSearch(root);
        }
        return result;
    }

    /**
     * Method to return a trie rooted at the prefix
     *
     * @param prefix
     */
    public Trie getSubTrie(String prefix) {
        //set current node to root
        TrieNode currentNode = root;
        Trie result = new Trie();
        //iterate through all the prefixes
        for (int i = 0; i < prefix.length(); i++) {
            
            int index = (int) prefix.charAt(i) - 'a';

            if (currentNode.getNode(prefix.charAt(i)) != null) {
                result.root = currentNode.getNode(prefix.charAt(i));
            }
            currentNode = currentNode.offspring[index];
        }
        return result;
    }

    public List<String> getAllWords() {
        List<String> output = new LinkedList<>();
        getAllWords("\0", root, output);
        return output;
    }
    /**
     * Method to get All the words out of the prefix
     *
     * @param prefix, trienode, nodes
     */
    private void getAllWords(String prefix, TrieNode trienode,
            List<String> nodes) {
        //iterate through every trienode 
        for (TrieNode temp : trienode.getOffspring()) {
            //if temp is not null
            if (temp != null) {
                //word from prefix  and temp char
                String prefix2 = prefix + temp.getChar();

                getAllWords(prefix2, temp, nodes);
            }
        }
       
        if (trienode.isComplete()) {
            nodes.add(prefix);
        }
    }

    public static void main(String[] args) {
        Trie test = new Trie();
        System.out.println(test.add("cheers"));
        System.out.println(test.add("cheese"));
        System.out.println(test.add("chat"));
        System.out.println(test.add("cat"));
        System.out.println(test.add("bat"));
        System.out.println("---------- ");
        System.out.println(test.contains("chee"));
        System.out.println(test.contains("afc"));
        System.out.println(test.contains("ba"));
        System.out.println(test.contains("cheese"));
        System.out.println(test.contains("bat"));
        System.out.println("---------- ");
        System.out.println(test.outputBreadthFirstSearch());
        System.out.println("---------- ");
        System.out.println(test.outputDepthFirstSearch());
        System.out.println("---------- ");
        Trie subtrie = test.getSubTrie("ch");
        System.out.println(subtrie.outputBreadthFirstSearch());
        System.out.println("---------- ");
        System.out.println(test.getAllWords());

    }
}
