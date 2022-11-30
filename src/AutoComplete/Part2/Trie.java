/*****************************************************************************

 File        : Trie.java

 Date        : 21/02/2020

 Description : Java class defining the functionality for a Trie data structure

 Author      : 100239986
 ******************************************************************************/
package AutoComplete.Part2;

import AutoComplete.Part1.DictionaryFinder;
import AutoComplete.Part3.AutoCompleteTrie;
import AutoComplete.Part3.AutoCompleteTrieNode;

import java.io.FileNotFoundException;
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

        //Adds each character from the key to the trie if there is no node for it already
        for (char c : key.toCharArray()){
            //creates a new node if there is not one already for the character
            if (currentNode.getOffSpring(c)==null){
                currentNode.addOffSpring(new TrieNode(false),c-97);
            }
            //updates the current node
            currentNode=currentNode.getOffSpring(c);
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
        for (char c:key.toCharArray()){
            //returns false if the word is not in the trie
            if (currentNode.getOffSpring(c)==null){
                return false;
            }
            //updates the current node to the next character in the word
            currentNode=currentNode.getOffSpring(c);
        }
        //once the last character is reached its flag is returned,
        //true if the word is in the trie false if not
        return currentNode.getFlag();
    }

    //Outputs a string of all the characters in the trie breadth first
    public String outputBreadthFirstSearch(){
        Queue<TrieNode> queue = new LinkedList<>();
        StringBuilder output = new StringBuilder();
        queue.add(root);
        TrieNode current;
        //loops until there are no nodes left in the queue (all nodes in the trie have been looked at)
        while(queue.size()>0){
            current=queue.remove();
            //adds all children of the current node to the queue
            for (int i = 0; i < 26; i++){
                TrieNode child = current.getOffSpring(i);
                //blocks null child entries from offSpring array
                if (child!=null){
                    //adds key to the end of the output string
                    output.append((char)(i+97));
                    queue.add(child);
                }
            }
        }
        return output.toString();
    }

    //Recursive function for depth first search
    private StringBuilder getNextChar(TrieNode node,StringBuilder output){
        TrieNode child;
        //loops through all the children of the current node
        for (int i = 0; i<26; i++){
            child = node.getOffSpring(i);
            if (child!=null){
                //adds key to the end of the output string
                output.append((char)(i+97));
                getNextChar(child,output);
            }
        }
        return output;
    }

    //Outputs a string of all the characters in the trie depth first
    public String outputDepthFirstSearch(){
        StringBuilder output = new StringBuilder();
        getNextChar(root,output);
        return output.toString();
    }

    //returns a trie based at the prefix passed in
    public Trie getSubTrie(String prefix){
        TrieNode currentNode = root;
        TrieNode child;
        //loops through the prefix  until the last node is reached
        for (char c : prefix.toCharArray()){
            child = currentNode.getOffSpring(c);
            //returns null if the prefix is not contained in the trie
            if (child==null){
                return null;
            }
            currentNode = child;
        }

        //a new trie is made from the root of the prefix
        return new Trie(currentNode);
    }

    //returns a list of words from a given node
    private void getAllWordsRecursive(StringBuilder word, TrieNode node, ArrayList<String> list){
        TrieNode child;
        //adds the current string if it is flagged as a word
        if (node.getFlag()){
            list.add(word.toString());
        }

        for (int i = 0; i<26; i++){
            child = node.getOffSpring(i);
            if (child!=null){
                StringBuilder currentWord = new StringBuilder(word);
                //adds key to the end of the output string
                currentWord.append((char)(i+97));
                //calls this function on the current node and current word
                getAllWordsRecursive(currentWord,child,list);
            }
        }
    }

    //returns all words that are in the trie
    public List getAllWords(){
        //List of all words to be returned
        ArrayList<String> list = new ArrayList<>();
        getAllWordsRecursive(new StringBuilder(),root,list);
        return list;
    }

    public static void main(String[] args) throws FileNotFoundException {
        //Create a trie from the test data:
        //cheers, cheese, chat, bat
        DictionaryFinder df = new DictionaryFinder();
        ArrayList<String> in = DictionaryFinder.readWordsFromCSV("csvFiles\\testData.csv");
        TreeMap<String,Integer> testDict = df.formDictionary(in);
        Trie testTrie = new Trie(testDict);
        //Testing output depth first
        System.out.println("Should output:\nbatchateersse\noutput:");
        System.out.println(testTrie.outputDepthFirstSearch()+"\n");

        //add cat to trie
        testTrie.add("cat");
        System.out.println("Should output:\nbatcathateersse\noutput:");
        System.out.println(testTrie.outputDepthFirstSearch()+"\n");

        //testing output breadth first
        System.out.println("Should output:\nbcaahttaetersse\noutput:");
        System.out.println(testTrie.outputBreadthFirstSearch()+"\n");

        //testing sub trie
        System.out.println("Should output:\nateersse\noutput:");
        Trie subTrie = testTrie.getSubTrie("ch");
        System.out.println(subTrie.outputDepthFirstSearch()+"\n");

        //testing get all words
        System.out.println("Should output:\nbat,cat,chat,cheers,cheese\noutput:");
        System.out.println(testTrie.getAllWords()+"\n");
        //testing on a sub trie
        System.out.println("Should output:\nat,eers,eese\noutput:");
        System.out.println(subTrie.getAllWords()+"\n");
    }
}
