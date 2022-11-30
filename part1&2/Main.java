package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        DictionaryFinder df = new DictionaryFinder();
        ArrayList<String> in = DictionaryFinder.readWordsFromCSV("csvFiles\\lotrQueries.csv");
        TreeMap<String,Integer> test = df.formDictionary(in);
        Trie trie = new Trie(test);
        System.out.println("done");
        System.out.println(trie.outputBreadthFirstSearch());
        System.out.println(trie.outputDepthFirstSearch());
        System.out.println(trie.contains("chee"));
        System.out.println(trie.contains("afc"));
        System.out.println(trie.contains("safs"));
        System.out.println(trie.contains("cheese"));
        //System.out.println(trie.getSubTrie("ch").outputDepthFirstSearch());
        System.out.println(trie.getAllWords());

    }
}
