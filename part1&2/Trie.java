package com.company;

import java.util.*;

public class Trie {

    //Root node of the trie
    private TrieNode root;

    //default trie constructor
    public Trie(){
        root = new TrieNode();
    }

    //Constructs a trie from a given node
    public Trie(TrieNode root){
        this.root = root;
    }

    //Constructs a trie from a dictionary(map)
    public Trie(Map<String,Integer> dict){
        root = new TrieNode();
        for (Map.Entry<String,Integer> entry : dict.entrySet()) {
            this.add(entry.getKey());
        }
    }

    //Adds a single key(string) to the trie
    public boolean add(String key){
        TrieNode currentNode=root;
        boolean check = true;

        //Adds each character from the key to the trie if there is no node for it already
        for (int i = 0; i < key.length();i++){
            //creates a new node if there is not one already for the character
            if (currentNode.getOffSpring(key.charAt(i))==null){
                currentNode.addOffSpring(new TrieNode(Character.toString(key.charAt(i)), false));
            }
            //updates the current node
            currentNode=currentNode.getOffSpring(key.charAt(i));
        }

        //returns false if the key was already in the trie
        if (currentNode.getFlag()){
            return false;
        }

        //Sets the nodes flag to true to indicate the node is the end of a key
        currentNode.setFlag(true);
        //returns true to confirm the new word was added
        return true;
    }

    //Returns true if a word is contained within the trie
    public boolean contains(String key){
        TrieNode currentNode=root;
        //loops through the word
        for (int i = 0; i < key.length();i++){
            //returns false if the word is not in the trie
            if (currentNode.getOffSpring(key.charAt(i))==null){
                return false;
            }
            //updates the current node to the next character in the word
            currentNode=currentNode.getOffSpring(key.charAt(i));
        }
        //once the last character is reached its flag is returned,
        //true if the word is in the trie false if not
        return currentNode.getFlag();
    }

    //adds children of a node to a queue if they are non null values
    private static void addOffspring(Queue<TrieNode> queue,TrieNode node){
        //loops through all children
        for (int i = 0; i < 26; i++){
            TrieNode child = node.getOffSpring(i);
            //blocks null child entries from offSpring array
            if (child!=null){
                queue.add(child);
            }
        }
    }

    //Outputs a string of all the characters in the trie breadth first
    public String outputBreadthFirstSearch(){
        Queue<TrieNode> queue = new LinkedList<>();
        //adds the roots children to the queue
        Trie.addOffspring(queue,root);
        StringBuilder output = new StringBuilder();
        TrieNode current;
        //loops until there are no nodes left in the queue (all nodes in the trie have been looked at)
        while(queue.size()>0){
            current=queue.remove();
            //adds all children of the current node to the queue
            Trie.addOffspring(queue, current);
            //adds key to the end of the output string
            output.append(current.getKey());
        }
        return output.toString();
    }

    //Recursive function for depth first search
    private StringBuilder getNextChar(TrieNode node){
        StringBuilder output = new StringBuilder(node.getKey());
        TrieNode child;
        for (int i = 0; i<26; i++){
            child = node.getOffSpring(i);
            if (child!=null){
                output.append(getNextChar(child));
            }
        }
        return output;
    }

    //Outputs a string of all the characters in the trie depth first
    public String outputDepthFirstSearch(){
        StringBuilder output = new StringBuilder();
        TrieNode child;
        for (int i =0;i<26;i++){
            child = root.getOffSpring(i);
            if (child!=null){
                output.append(getNextChar(child));
            }
        }
        //StringBuilder output = getNextChar(root);
        return output.toString();
    }

    //returns a trie based at the prefix passed in
    public Trie getSubTrie(String prefix){
        TrieNode currentNode = root;
        TrieNode child;
        //loops through the prefix  until the last node is reached
        for (int i = 0; i<prefix.length();i++){
            child = currentNode.getOffSpring(prefix.charAt(i));
            //returns null if the prefix is not contained in the trie
            if (child==null){
                return null;
            }
            currentNode = child;
        }

        //generate new node so that it can be edited
        TrieNode outputNode = new TrieNode(prefix,false);
        for (int i =0; i < 26; i++){
            child = currentNode.getOffSpring(i);
            if (child != null){
                outputNode.addOffSpring(child);
            }
        }

        //a new trie is made from the root of the prefix
        return new Trie(outputNode);
    }


    //returns a list of words from a given node
    private ArrayList<String> getAllWordsRecursive(StringBuilder word,TrieNode node, ArrayList<String> list){
        word.append(node.getKey());
        TrieNode child;
        for (int i = 0; i<26; i++){
            child = node.getOffSpring(i);
            if (child!=null){
                getAllWordsRecursive(new StringBuilder(word),child,list);
            }
        }
        if (node.getFlag()){
            list.add(word.toString());
        }
        return list;
    }

    //returns all words that are in the trie
    public List getAllWords(){
        //List of all words to be returned
        ArrayList<String> list = new ArrayList<>();
        TrieNode child;

        //loops through the children of the root and adds the words of each letter recursively
        for (int i = 0; i<26; i++){
            child = root.getOffSpring(i);
            if (child!=null){
                list.addAll(getAllWordsRecursive(new StringBuilder(),child,new ArrayList<>()));
            }
        }
        return list;
    }
}
