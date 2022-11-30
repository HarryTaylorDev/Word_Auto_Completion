/*****************************************************************************

 File        : DictionaryFinder.java

 Date        : 19/01/2020

 Description : Java class to turn a csv of words into a dictionary of words
               with their frequency's

 Author      : Tony Bagnel & 100239986
 ******************************************************************************/
package AutoComplete.Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        sc.useDelimiter("\n|,");
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
            //Count will be null if there is no entry for the key so count is set to 0
            if (count==null){
                count=0;
            }
            //the key is added to the dictionary with its frequency incremented by one
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
        //Create dictionary finder and read in a list of words from a file
        DictionaryFinder df = new DictionaryFinder();
        ArrayList<String> in = readWordsFromCSV("csvFiles\\testDocument.csv");
        //Should print:
        System.out.println("Should print:\n");
        System.out.println("forty,nine,forty,nine,undefeated,forty,nine,forty,nine,I,say,forty,nine,forty,nine,undefeated,playing,football,the,arsenal,way ");
        System.out.println(in+"\n");
        //convert array of words in to dictionary of words and frequency's
        TreeMap<String,Integer> test = df.formDictionary(in);
        System.out.println("Should print:");
        System.out.println("arsenal=1,football=1,forty=6,i=1,nine=6,playing=1,say=1,the=1,undefeated=2,way=1");
        System.out.println(test+"\n");
        //save dictionary to file name test.csv
        df.saveToFile(test,"test.csv");
    }
}
