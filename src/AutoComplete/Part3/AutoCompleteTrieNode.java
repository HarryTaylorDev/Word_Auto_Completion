/*****************************************************************************

 File        : AutoCompleteTrieNode.java

 Date        : 20/02/2020

 Description : Java class defining the functionality for a single TrieNode with
               functionality for the word auto completion system eg frequency

 Author      : 100239986
 ******************************************************************************/
package AutoComplete.Part3;

public class AutoCompleteTrieNode {
    private boolean flag;
    private AutoCompleteTrieNode[] offSpring;
    private int frequency;

    //Constructs default node
    public AutoCompleteTrieNode() {
        flag = false;
        offSpring = new AutoCompleteTrieNode[26];
        frequency = 0;
    }

    //Constructs node from a character and a flag
    public AutoCompleteTrieNode(boolean flag,int frequency) {
        this.flag = flag;
        offSpring = new AutoCompleteTrieNode[26];
        this.frequency = frequency;
    }

    //sets nodes flag
    public void setFlag(boolean flag) { this.flag = flag; }

    //returns the nodes flag
    public boolean getFlag() {
        return flag;
    }

    //adds a child to the node
    public void addOffSpring(AutoCompleteTrieNode node,int index) {
        //sets the nodes position based on the characters value
        offSpring[index] = node;
    }

    //gets a child based on an integer
    public AutoCompleteTrieNode getOffSpring(int key) {
        return offSpring[key];
    }

    //gets a child based of a character
    public AutoCompleteTrieNode getOffSpring(char key) {
        return offSpring[key-97];
    }

    public int getFrequency() {
        return frequency;
    }

    //returns the amount of times a word has been added to the trie
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
