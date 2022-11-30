/*****************************************************************************

 File        : AutoCompletion.java

 Date        : 25/02/2020

 Description : Java class defining the functionality for an auto completion system
               that can output the results to the console or to a file

 Author      : 100239986
 ******************************************************************************/
package AutoComplete.Part3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.*;
import AutoComplete.Part1.DictionaryFinder;


public class AutoCompletion {
    private ArrayList<String> queries;
    private AutoCompleteTrie trie;

    //constructor that loads the appropriate files
    public AutoCompletion(String documentFilename,String queriesFilename) throws FileNotFoundException {
        //create trie from a list of words in a document
        ArrayList<String> wordList = DictionaryFinder.readWordsFromCSV(documentFilename);
        TreeMap<String, Integer> dict = DictionaryFinder.formDictionary(wordList);
        trie = new AutoCompleteTrie(dict);

        //load the list of queries
        queries = DictionaryFinder.readWordsFromCSV(queriesFilename);
    }

    //private inner wrapper class for a word and its corresponding frequency
    private static class WordFrequencyPair implements Comparable<WordFrequencyPair>{
        String word;
        int frequency;

        //constructor that simply sets fields
        public WordFrequencyPair(String word, int frequency){
            this.word = word;
            this.frequency = frequency;
        }

        @Override
        public String toString(){
            return word + " = " + frequency;
        }

        //compare method to allow for sorting
        //compared in breadth first order
        @Override
        public int compareTo(WordFrequencyPair pair2) {
            //compared first by frequency
            int difference = pair2.frequency - this.frequency;
            if (difference!=0){
                return difference;
            }
            //if frequencies are equal then by key length
            difference = pair2.word.length() - this.word.length();
            if(difference!=0){
                return difference;
            }
            //if key lengths are equal sort by alphabetical
            return this.word.compareTo(pair2.word);
        }
    }

    //returns a string in the format(as one line):
    //Prefix , Best matching word, Best Matching words Frequency,
    //         Second best matching word, Second best matching frequency,
    //         Third best matching word, Third best matching frequency
    public StringBuilder findBestQueryMatches(String prefix){
        //generates a sub trie of the main trie from the prefix passed in
        AutoCompleteTrie prefixTrie = trie.getSubTrie(prefix);
        ArrayList<WordFrequencyPair> wordsFrequencyPairs = new ArrayList<>();
        int frequency;
        int count = 0;
        //checks if the sub trie is null as the prefix might not be in the dictionary
        if (prefixTrie!=null) {
            //creates a list of all words in the sub trie
            ArrayList<String> words = (ArrayList<String>) prefixTrie.getAllWords();

            //adds the prefix to the list of word and frequency pairs
            //only added if the word is flagged as a key
            if (trie.contains(prefix)){
                int tempFrequency = trie.getFrequency(prefix);
                wordsFrequencyPairs.add(new WordFrequencyPair(prefix,tempFrequency));
                count+=tempFrequency;
            }

            //Loops through all the words found in the sub trie and adds the complete word (with the prefix)
            //and its corresponding frequency to a list of pairs
            for (String word : words) {
                frequency =  prefixTrie.getFrequency(word);
                count += frequency;
                wordsFrequencyPairs.add(new WordFrequencyPair((prefix+word),frequency));
            }
        } else {
            //returns just the prefix as there
            return new StringBuilder(prefix);
        }

        //The array of pairs is sorted in frequency order and then by breadth first order if keys are the same length
        Collections.sort(wordsFrequencyPairs);
        StringBuilder output = new StringBuilder(prefix);
        output.append(",");
        WordFrequencyPair temp;
        //pattern to format the probability to 6 decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        //Adds upto the top three results to the output string
        //add less only if there are less than three matches
        for (int i =0; i<3 && i<wordsFrequencyPairs.size();i++ ){
            temp=wordsFrequencyPairs.get(i);
            output.append(temp.word).append(",").append(decimalFormat.format( (double)temp.frequency/count)).append(",");
        }
        //last comma is removed
        output.deleteCharAt(output.length()-1);
        return output;
    }

    //prints the output string for each query
    public void printQueriesToConsole(){
        for(String prefix: queries) {
            System.out.println(this.findBestQueryMatches(prefix));
        }
    }

    //Writes the output string for each query to a file by redirecting the output stream
    public void writeQueriesToFile(String filename) throws FileNotFoundException {
        PrintStream o = new PrintStream(new File(filename));
        System.setOut(o);
        this.printQueriesToConsole();
    }

    public static void main(String[] args) throws FileNotFoundException {
        AutoCompletion ac = new AutoCompletion("csvFiles\\lotr.csv","csvFiles\\lotrQueries.csv");
        ac.printQueriesToConsole();
        ac.writeQueriesToFile("matches.csv");
    }

}




