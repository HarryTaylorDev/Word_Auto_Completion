
package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.*;

/**
 * @author ajb
 */
public class DictionaryFinder {

    /**
     * Reads all the words in a comma separated text document into an Array
     *
     * @param file
     */
    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        sc.useDelimiter(" |,");
        ArrayList<String> words = new ArrayList<>();
        String str;
        while (sc.hasNext()) {
            str = sc.next();
            str = str.trim();
            str = str.toLowerCase();
            words.add(str);
        }
        return words;
    }

    //function to create a dictionary of words and their frequencies from a list of words
    public static TreeMap<String,Integer> formDictionary(ArrayList<String> words) {
        TreeMap<String,Integer> wordDict = new TreeMap<>();
        Integer count;
        //Loop through all words
        for (String key : words){
            count = wordDict.get(key);
            //Count will be null if there is no entry for the key
            if (count==null){
                count=0;
            }
            wordDict.put(key,++count);
        }
        return wordDict;
    }

    //Method to save a Map to file, a TreeMap in this case
    public static void saveToFile(Map<String,Integer> map,String file) throws IOException {
        FileWriter fileWriter = new FileWriter("csvFiles\\"+file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        //Iterate over all the entries in the map and add them to the file
        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            printWriter.println(entry.getKey()+","+entry.getValue());
        }
        printWriter.close();
    }

    public static void main(String[] args) throws Exception {
        DictionaryFinder df = new DictionaryFinder();
        ArrayList<String> in = readWordsFromCSV("csvFiles\\testDocument.csv");
        //DO STUFF TO df HERE in countFrequencies
        TreeMap<String,Integer> test = df.formDictionary(in);
        df.saveToFile(test,"test.csv");
        System.out.println(test.size());

    }

}
