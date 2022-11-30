/*****************************************************************************

 File        : AutoCompleteTrie.java

 Date        : 21/02/2020

 Description : Java class defining the functionality for a Trie with
               functionality for the word auto completion system eg frequency

 Author      : 100239986
 ******************************************************************************/
package AutoComplete.Part3;

import java.util.*;


public class AutoCompleteTrie {

    //Root node of the trie
    private AutoCompleteTrieNode root;

    //default trie constructor
    public AutoCompleteTrie(){
        root = new AutoCompleteTrieNode();
    }

    //Constructs a trie from a given node
    public AutoCompleteTrie(AutoCompleteTrieNode root){
        this.root = root;
    }

    //Constructs a trie from a dictionary(map)
    public AutoCompleteTrie(Map<String,Integer> dict){
        root = new AutoCompleteTrieNode();
        for (Map.Entry<String,Integer> entry : dict.entrySet()) {
            this.add(entry.getKey(),entry.getValue());
        }
    }

    //Adds a single key(string) to the trie
    public boolean add(String key,int freq){
        AutoCompleteTrieNode currentNode=root;

        //Adds each character from the key to the trie if there is no node for it already
        for (int i = 0; i < key.length();i++){
            //creates a new node if there is not one already for the character
            if (currentNode.getOffSpring(key.charAt(i))==null){
                currentNode.addOffSpring(new AutoCompleteTrieNode(false,0),key.charAt(i)-97);
            }
            //updates the current node
            currentNode=currentNode.getOffSpring(key.charAt(i));
        }

        //returns false if the key was already in the trie
        if (currentNode.getFlag()){
            //increment the frequency
            currentNode.setFrequency(currentNode.getFrequency()+1);
            return false;
        }

        //Sets the nodes flag to true to indicate the node is the end of a key
        currentNode.setFlag(true);
        currentNode.setFrequency(freq);
        //returns true to confirm the new word was added
        return true;
    }

    //Returns true if a word is contained within the trie
    public boolean contains(String key){
        AutoCompleteTrieNode currentNode=root;
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

    //Outputs a string of all the characters in the trie breadth first
    public String outputBreadthFirstSearch(){
        Queue<AutoCompleteTrieNode> queue = new LinkedList<>();
        StringBuilder output = new StringBuilder();
        queue.add(root);
        AutoCompleteTrieNode current;
        //loops until there are no nodes left in the queue (all nodes in the trie have been looked at)
        while(queue.size()>0){
            current=queue.remove();
            //adds all children of the current node to the queue
            for (int i = 0; i < 26; i++){
                AutoCompleteTrieNode child = current.getOffSpring(i);
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
    private StringBuilder getNextChar(AutoCompleteTrieNode node,StringBuilder output){
        AutoCompleteTrieNode child;
        for (int i = 0; i<26; i++){
            child = node.getOffSpring(i);
            if (child!=null){
                //adds key to the end of the output string
                output.append((char)(i+97));
                //calls the function on the next child of the current node
                getNextChar(child,output);
            }
        }
        return output;
    }

    //Outputs a string of all the characters in the trie depth first
    public String outputDepthFirstSearch(){
        StringBuilder output = new StringBuilder();
        output = getNextChar(root,output);
        return output.toString();
    }

    //returns a trie based at the prefix passed in
    public AutoCompleteTrie getSubTrie(String prefix){
        AutoCompleteTrieNode currentNode = root;
        AutoCompleteTrieNode child;
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
        AutoCompleteTrieNode outputNode = new AutoCompleteTrieNode(false,currentNode.getFrequency());
        for (int i =0; i < 26; i++){
            child = currentNode.getOffSpring(i);
            if (child != null){
                outputNode.addOffSpring(child,i);
            }
        }
        //a new trie is made from the root of the prefix
        return new AutoCompleteTrie(outputNode);
    }

    //returns a list of words from a given node
    private void getAllWordsRecursive(StringBuilder word,AutoCompleteTrieNode node, ArrayList<String> list){
        AutoCompleteTrieNode child;
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

    public int getFrequency(String prefix){
        AutoCompleteTrieNode currentNode=root;
        //loops through the word
        for (int i = 0; i < prefix.length();i++){
            //returns false if the word is not in the trie
            if (currentNode.getOffSpring(prefix.charAt(i))==null){
                return 0;
            }
            //updates the current node to the next character in the word
            currentNode=currentNode.getOffSpring(prefix.charAt(i));
        }
        //once the last character is reached its frequency is returned,
        return currentNode.getFrequency();
    }

}
