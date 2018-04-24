/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsacoursework2;

/**
 *
 * @author ngx16ybu
 */
public class TrieNode {

    public char letter;
    public TrieNode[] offspring;
    public boolean complete;

    /**
     * Default constructor with special character used for root node
     */
    public TrieNode() {
        this.letter = '\0';
        this.offspring = new TrieNode[26];
        this.complete = false;
    }

    /**
     * Constructor for creating a TrieNode with a given character
     *
     * @param c
     */
    public TrieNode(char c) {
        this.letter = c;
        this.offspring = new TrieNode[26];
        this.complete = false;
    }

    /**
     * Method to check whether the node is a complete word
     *
     */
    public boolean isComplete() {
        return this.complete;
    }

    /**
     * method to set a node as a complete word
     *
     * @param set
     */
    public void setComplete(boolean set) {
        this.complete = set;
    }

    /**
     * Accessor Method to get the offspring of the node
     *
     */
    public TrieNode[] getOffspring() {
        return this.offspring;
    }

    /**
     * Method to get the index of the specified character
     *
     * @param c
     */
    public static int getCharIndex(char c) {
        return c - 'a';
    }

    /**
     * Accessor method to get the want node of an offspring
     *
     * @param c
     */
    public TrieNode getNode(char c) {
        return this.offspring[getCharIndex(c)];
    }

    /**
     * Method to set the wanted node of an offspring
     *
     * @param c
     */
    public void setNode(char c) {
        this.offspring[getCharIndex(c)] = new TrieNode(c);
    }

    /**
     * Accessor method to return the letter of a noda
     *
     * @param c
     */
    public char getChar() {
        return letter;
    }

}
