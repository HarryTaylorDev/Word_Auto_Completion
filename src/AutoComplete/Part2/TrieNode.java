/*****************************************************************************

 File        : TrieNode.java

 Date        : 20/02/2020

 Description : Java class defining the functionality for a single TrieNode of a
               trie data structure

 Author      : 100239986
 ******************************************************************************/
package AutoComplete.Part2;

public class TrieNode {
    private boolean flag;
    private TrieNode[] offSpring;

    //Constructs default node
    public TrieNode() {
        flag = false;
        offSpring = new TrieNode[26];
    }

    //Constructs node from a character and a flag
    public TrieNode(boolean flag) {
        this.flag = flag;
        offSpring = new TrieNode[26];
    }

    //sets nodes flag
    public void setFlag(boolean flag) { this.flag = flag; }

    //returns the nodes flag
    public boolean getFlag() {
        return flag;
    }

    //adds a child to the node
    public void addOffSpring(TrieNode node,int index) {
        //sets the nodes position based on the characters value
        offSpring[index] = node;
    }

    //gets a child based on an integer
    public TrieNode getOffSpring(int key) {
        return offSpring[key];
    }

    //gets a child based of a character
    public TrieNode getOffSpring(char key) {
        return offSpring[key-97];
    }

    public static void main(String[] args) {

    }
}
