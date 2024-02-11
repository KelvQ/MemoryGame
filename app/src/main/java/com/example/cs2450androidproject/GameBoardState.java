
package com.example.cs2450androidproject;

public class GameBoardState {

    private GameCard[] gameBoard;
    private GameCard[] flippedCards = new GameCard[2];
    private int numOfCards;
    private int score;
    private int numOfFlippedCards;
    private int numOfCorrectCards;
    private boolean isBoardLocked;

    // method: GameBoardState constructor
    // purpose: This method sets the default values of this class
    public GameBoardState(int numOfCards) {
        this.numOfCards = numOfCards;
        score = 0;
        numOfFlippedCards = 0;
        numOfCorrectCards = 0;
        isBoardLocked = false;
        gameBoard = new GameCard[numOfCards];
        createGameBoard();
    }

    // method: createGameBoard
    // purpose: This method creates the game board depending on how many tiles are selected
    private void createGameBoard() {
        WordRandomization randomWords = new WordRandomization(numOfCards);
        String[] words = randomWords.getNewWords();

        for(int i = 0; i < numOfCards; i++) {

            gameBoard[i] = new GameCard(words[i] , (i+1));

        }

    }

    // method: tryAgain
    // purpose: This method flips the cards back and enables all cards again
    public void tryAgain() {
        for(int i = 0; i < 2; i++) {
            flippedCards[i].unlock();
            flippedCards[i].unFlip();
        }
        flippedCards = new GameCard[2];
        unlockBoard();
    }

    // method: flipAll
    // purpose: This method flips all cards to reveal the answers
    public void flipAll() {
        GameCard.forceUnlock();
        lockBoard();
        for(int i = 0; i < numOfCards; i++) {
            gameBoard[i].flip();
        }
    }

    // method: lockBoard
    // purpose: This method locks all cards so they can not be clicked
    public void lockBoard() {
        isBoardLocked = true;
        System.out.println("locked");
        for(int i = 0; i < numOfCards; i++) {
            gameBoard[i].lock();
        }
    }

    // method: unlockBaord
    // purpose: This method unlocks all cards so they can be clicked
    public void unlockBoard() {
        isBoardLocked = false;
        System.out.println("Unlocked");
        for(int i = 0; i < numOfCards; i++) {
            gameBoard[i].unlock();
        }
    }

    // method: flip
    // purpose: This method flips a card that is clicked
    public int flip(int cardNum) {
        if(!isBoardLocked && !gameBoard[cardNum - 1].isFlipped()) {
            numOfFlippedCards++;
            gameBoard[cardNum - 1].flip();
            flippedCards[numOfFlippedCards - 1] = gameBoard[cardNum - 1];

            if (numOfFlippedCards == 2) {
                lockBoard();
                if (flippedCards[0].getValue().equals(flippedCards[1].getValue())) {
                    score += 2;
                    numOfCorrectCards += 2;
                    numOfFlippedCards = 0;
                    correctFlip();
                    if(numOfCorrectCards == numOfCards) {
                        return 0;
                    }
                    return 1;
                } else {
                    if(score > 0)
                        score--;
                    numOfFlippedCards = 0;
                    return 2;
                }
            }
            return 3;
        }
        return -1;
    }

    // method: correctFlip
    // purpose: This method sets cards to correct if they match
    public void correctFlip() {
        flippedCards[0].setCorrect();
        flippedCards[1].setCorrect();
    }

    // method: getGameBoard
    // purpose: This method retruns gameBoard
    public GameCard[] getGameBoard() {
        return gameBoard;
    }

    // method: getScore
    // purpose: This method returns the score
    public int getScore() {
        return score;
    }

}
