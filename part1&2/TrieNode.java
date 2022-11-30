package com.company;

public class TrieNode {
    private boolean flag;
    private TrieNode[] offSpring;
    private String key;

    //Constructs default node
    public TrieNode() {
        flag = false;
        offSpring = new TrieNode[26];
        key = "@";
    }

    //Constructs node from a character and a flag
    public TrieNode(String key, boolean flag) {
        this.flag = flag;
        offSpring = new TrieNode[26];
        this.key = key;
    }

    //sets nodes flag
    public void setFlag(boolean flag) { this.flag = flag; }

    //returns the nodes flag
    public boolean getFlag() {
        return flag;
    }

    //adds a child to the node
    public void addOffSpring(TrieNode node) {
        //sets the nodes position based on the character
        offSpring[node.getKey().charAt(0) - 97] = node;
    }

    //gets a child based on an integer
    public TrieNode getOffSpring(int key) {
        return offSpring[key];
    }

    //gets a child based of a character
    public TrieNode getOffSpring(char key) {
        return offSpring[key-97];
    }

    public void setKey(String key){
        this.key = key;
    }

    //returns the key of the node
    public String getKey() {
        return key;
    }
}
