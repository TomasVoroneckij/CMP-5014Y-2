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

    public boolean isComplete() {
        return this.complete;
    }

    public void setComplete(boolean set) {
        this.complete = set;
    }

    public TrieNode[] getOffspring() {
        return this.offspring;
    }

    public static int getCharIndex(char c) {
        return c - 'a';
    }

    public TrieNode getNode(char c) {
        return this.offspring[getCharIndex(c)];
    }

    public void setNode(char c) {
        this.offspring[getCharIndex(c)] = new TrieNode(c);
    }

    public char getChar() {
        return letter;
    }

}
