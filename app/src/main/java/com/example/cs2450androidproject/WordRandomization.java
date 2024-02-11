
package com.example.cs2450androidproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class WordRandomization
{
    private ArrayList<String> validWords = new ArrayList<>();
    private static String[] newWords;

    // method: WordRandomization constructor
    // purpose: This method takes in the number of cards requested as a param and defaults to max 20 cards.
    public WordRandomization(int n) //constructor
    {

        addWords();
        sortNames(n,validWords);

    }

    // method: sortNames
    // purpose: This method randomizes the array of words
    private static void sortNames(int numberOfCards, ArrayList<String> validWords)
    {
        newWords = new String[numberOfCards];
        Random rand = new Random();

        for(int i = 0; i < numberOfCards; i ++)
        {

            int selection = rand.nextInt(validWords.size());
            newWords[i] = validWords.get(selection);
            newWords[i+1] = newWords[i];
            i++;
            validWords.remove(selection);
        }

        Collections.shuffle(Arrays.asList(newWords));

    }

    // method: addWords
    // purpose: This method adds all 10 words to an arraylist
    private void addWords()
    {
        validWords.add("Cookie");
        validWords.add("Eggs");
        validWords.add("Cheese");
        validWords.add("Salami");
        validWords.add("Chocolate");
        validWords.add("Crackers");
        validWords.add("Cereal");
        validWords.add("Rice");
        validWords.add("Steak");
        validWords.add("Chicken");
    }

    // method: getNewWords
    // purpose: This method returns an array of the randomized words
    public static String[] getNewWords() {
        return newWords;
    }


}
