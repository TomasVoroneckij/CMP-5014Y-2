/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        //set current node to root
        TrieNode currentNode = root;
        //iterate through all the key 
        for (int i = 0; i < key.length(); i++) {
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
        if (currentNode.isComplete()) {
            return false;
        }
        currentNode.setComplete(true);
        return true;

    }

    /**
     * Method to check if the word passed in the trie is a whole word
     *
     * @param key
     */
    public boolean contains(String key) {
        TrieNode currentNode = root;

        for (char c : key.toCharArray()) {
            if (currentNode.getNode(c) == null) {
                return false;
            }
            currentNode = currentNode.getNode(c);
        }
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
        while (!nodes.isEmpty()) {
            TrieNode temp = (TrieNode) nodes.poll();
            result += temp.getChar();
            for (TrieNode node : temp.getOffspring()) {
                if (node != null) {
                    nodes.add(node);
                }
            }
        }
        return result.toLowerCase();
    }

    /**
     * Method to output the string by depth first search
     *
     * @param trienode
     * @return result
     */
    public String depthFirstSearch(TrieNode trienode) {
        String result = "";
        for (TrieNode node : trienode.getOffspring()) {
            if (node != null) {
                result += depthFirstSearch(node);
            }
        }
        result += trienode.getChar();
        return result;
    }

    public String outputDepthFirstSearch() {
        String result = "";
        if (root != null) {
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
        TrieNode currentNode = root;
        Trie result = new Trie();
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

    private void getAllWords(String prefix, TrieNode trienode,
            List<String> nodes) {

        for (TrieNode temp : trienode.getOffspring()) {

            if (temp != null) {
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
