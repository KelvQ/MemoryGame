

package com.example.cs2450androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private int currentNumTiles;
    private static GameBoardState game;
    private static boolean tryAgainState;
    private GameCardView[] cards;

    // method: onCreate
    // purpose: This method is called when the acitivty is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        currentNumTiles = getIntent().getIntExtra("currentNumTiles", -1);
        Button newGame = findViewById(R.id.newGameButton);
        Button endGame = findViewById(R.id.endGameButton);
        TextView scoreView = findViewById(R.id.realscore);

        cards = new GameCardView[currentNumTiles];

        if (game == null) {
            game = new GameBoardState(currentNumTiles);
        }
        if(tryAgainState)
            tryAgainButton();

        scoreView.setText(game.getScore() + "");

        for(int i = 0; i < currentNumTiles; i++) {
            GameCard[] gameBoard = game.getGameBoard();
            cards[i] = new GameCardView(getApplicationContext(), this, gameBoard[i]);
        }


        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game = null;
                tryAgainState = false;
                Intent i = new Intent(getApplicationContext(),SetTilesActivity.class);
                startActivity(i);
            }
        });

        endGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                disableButtons();
                game.flipAll();
                resetTiles(false);
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        finishGame(false);
                    }
                };
                timer.schedule(task, 1000);
            }

        });


    }

    // method: updateScore
    // purpose: This method updates the score that is displayed
    private void updateScore() {
        TextView scoreView = findViewById(R.id.realscore);
        scoreView.setText(game.getScore() + "");
    }

    // method: updateCard
    // purpose: This method updates the game tiles
    public void updateCard(int tileNum) {
        cards[tileNum - 1].updateCard();
    }

    // method: flipCard
    // purpose: This method flips the cards and updates the score
    public void flipCard(int tileNum) {
        int flipCondition = game.flip(tileNum);
        updateCard(tileNum);

        if(flipCondition == 2) {
            updateScore();
            tryAgainButton();
        }
        else if(flipCondition == 1) {
            updateScore();
            resetTiles(true);
        }
        else if(flipCondition == 0) {
            finishGame(true);
        }
    }

    // method: resetTiles
    // purpose: This method flips the cards back
    private void resetTiles(boolean isUnlock) {
        for(int i = 0; i < cards.length; i++) {
            updateCard(i + 1);
        }
        if(isUnlock) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    game.unlockBoard();
                }
            };
            timer.schedule(task, 1000);
        }

    }

    // method: tryAgainButton
    // purpose: This method is the onclick listener for the try again button
    private void tryAgainButton() {
        Button tryAgain = findViewById(R.id.tryAgainButton);
        tryAgain.setVisibility(Button.VISIBLE);
        tryAgainState = true;
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryAgain.setVisibility(Button.INVISIBLE);
                game.tryAgain();
                resetTiles(false);
                tryAgainState = false;
            }
        });

    }

    // method: finishGame
    // purpose: This method switches to the end screen activity if the game is finished
    private void finishGame(boolean isFinished) {
        Intent i = new Intent(getApplicationContext(), EndScreenActivity.class);
        if(isFinished)
            i.putExtra("endScore", game.getScore());
        i.putExtra("currentNumTiles", currentNumTiles);

        game = null;
        tryAgainState = false;
        startActivity(i);

    }

    // method: disableButtons
    // purpose: This method disables the new game and end game buttons
    private void disableButtons() {
        Button newGame = findViewById(R.id.newGameButton);
        Button endGame = findViewById(R.id.endGameButton);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

}