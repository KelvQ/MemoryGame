
package com.example.cs2450androidproject;

public class GameCard  {

    private final String value;
    private boolean flipped;
    private boolean locked;
    private boolean isCorrect;
    private static boolean forceUnlock = false;
    private int tileNum;

    // method: GameCard constructor
    // purpose: This method sets the default values
    public GameCard( String value , int tileNum ) {
        this.value = value;
        this.tileNum = tileNum;
        flipped = false;
        locked = false;
    }

    // method: lock
    // purpose: This method locks the cards
    public void lock() {
        locked = true;
    }

    // method: unlock
    // purpose: This method unlocks the cards
    public void unlock() {
        locked = false;
    }

    // method: flip
    // purpose: This method flips the cards
    public void flip() {
        if(!locked || forceUnlock)
            flipped = true;
        lock();
    }

    // method: unFlip
    // purpose: This methods unFlips the cards
    public void unFlip() {
        if(!locked) {
            flipped = false;
            unlock();
        }
    }

    // method: isFlipped
    // purpose: This method checks if a card is flipped
    public boolean isFlipped() {
        return flipped;
    }

    // method: getValue
    // purpose: This method gets the value of the card
    public String getValue() {
        return value;
    }

    // method: setCorrect
    // purpose: This method sets isCorrect to true
    public void setCorrect() {
        isCorrect = true;
    }

    // method: isCorrect
    // purpose: This method returns the value of is correct
    public boolean isCorrect() {
        return isCorrect;
    }

    // method: forceUnlock
    // purpose: This method sets forceUnlock to true
    public static void forceUnlock() {forceUnlock = true;}

    // method: getTilesNum
    // purpose: This method returns the number of tiles
    public int getTileNum() {
        return tileNum;
    }

}
