
package com.example.cs2450androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class EndScreenActivity extends AppCompatActivity {

    private int gameState;
    private int highscoreIndex;
    private int score;
    private int numOfCards;
    private String fileName;
    private String savedNickname;

    // method: onCreate
    // purpose: This method is called when the acitivty is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        gameState = -1;
        if(savedInstanceState != null)
            gameState = savedInstanceState.getInt("gameState", -1);
        score = getIntent().getIntExtra("endScore", -1);
        numOfCards = getIntent().getIntExtra("currentNumTiles", -1);
        fileName = "Highscores" + numOfCards + ".txt";

        if(gameState == -1) {
            if (score < 0 || !isHighscore()) {
                if (score < 0)
                    score = 0;
                showEndSequence();
            } else {
                showHighscoreSequence(true);
            }
        }
        else if (gameState == 1) {
            showHighscoreSequence(true);
        }
        else if(gameState == 2) {
            savedNickname = savedInstanceState.getString("savedNickname", "");
            showHighscoreSequence(false);
        }
        else {
            showEndSequence();
        }


    }

    // method: onSaveInstanceState
    // purpose: This method is for screen orientation changes
    protected void onSaveInstanceState(@NonNull Bundle outstate) {
        outstate.putInt("gameState", gameState);
        if(gameState == 2) {
            EditText enterName = findViewById(R.id.enterName);
            outstate.putString("savedNickname", enterName.getText().toString());
        }
        super.onSaveInstanceState(outstate);
    }

    // method: isHighscore
    // purpose: This method checks if a user score is a highscore
    private boolean isHighscore(){
        boolean result = false;
        Scanner scnr = null;
        FileInputStream fi = null;
        FileOutputStream fo = null;
        File dir = getFilesDir();
        File fileChecker = new File(dir,fileName);
        byte[] buffer = null;
        try {
            if(fileChecker.exists()) {
                fo = this.openFileOutput(fileName, Context.MODE_APPEND);
            }
            else {
                fo = this.openFileOutput(fileName, Context.MODE_PRIVATE);
            }
            fo.close();
            fi = this.openFileInput(fileName);
            buffer = new byte[fi.available()];
            fi.read(buffer);
            fi.close();
        } catch (Exception e) { }

        try {
            scnr = new Scanner(new String(buffer));
        } catch (Exception e) {
            System.out.println("Error");
        }

        highscoreIndex = 0;

        while(scnr.hasNextInt() && highscoreIndex < 5 && !result) {
            highscoreIndex++;
            scnr.next();
            int oldScore = scnr.nextInt();
            if(oldScore < score ) {
                result = true;
            }
        }

        if(highscoreIndex < 5 && !result) {
            result = true;
            highscoreIndex++;
        }
        return result;
    }

    // method: setNewHighscore
    // purpose: This method places highscore in a txt file
    private void setNewHighscore(String name) {


        Scanner scnr = null;
        PrintWriter pw = null;

        FileInputStream fi = null;
        FileOutputStream fo = null;
        OutputStreamWriter ow = null;

        byte[] buffer = null;
        try {
            fi = this.openFileInput(fileName);
            buffer = new byte[fi.available()];
            fi.read(buffer);
            fo = this.openFileOutput(fileName, Context.MODE_PRIVATE);
            ow = new OutputStreamWriter(fo);
            fi.close();
        } catch (Exception e) { }

        try {
            scnr = new Scanner(new String(buffer));
        } catch (Exception e) { System.out.println("Error"); }

        ArrayList<String> highscoresList = new ArrayList<String>();

        int highscoreCount = 0;
        while(scnr.hasNextLine()) {
            highscoreCount++;
            highscoresList.add(scnr.nextLine());
        }

        try {
            pw = new PrintWriter(ow);
        } catch (Exception e) { System.out.println("Error"); }

        if(highscoreIndex == 0 ) {
            pw.write(name + " " + score);
        }
        else {
            for(int i = 1; i < highscoreIndex; i++) {
                pw.println(highscoresList.get(i - 1));
            }
            pw.println(name + " " + score);
            for(int i = highscoreIndex; i <= highscoreCount && i < 5; i++) {
                pw.println(highscoresList.get(i - 1));
            }
        }
        try {
            ow.close();
            fo.close();
            pw.close();
        } catch (Exception e) {}
    }

    // method: showEndSequence
    // purpose: This method makes widgets visible on game end screen
    private void showEndSequence() {
        gameState = 3;
        TextView gameFinished = findViewById(R.id.gameFinished);
        TextView scoreTile = findViewById(R.id.endScore);
        Button endGame = findViewById(R.id.endGameEndScreen);
        Button tryAgain = findViewById(R.id.tryAgainEndScreen);

        gameFinished.setVisibility(TextView.VISIBLE);
        scoreTile.setVisibility(TextView.VISIBLE);
        endGame.setVisibility(Button.VISIBLE);
        tryAgain.setVisibility(Button.VISIBLE);

        scoreTile.setText("Score: " + score);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SetTilesActivity.class);
                startActivity(i);
            }
        });

        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });


    }

    // method: showHighscoreSequence
    // purpose: This method displays the highscore widgets if user scores a highscore
    private void showHighscoreSequence(boolean fromStart) {
        TextView newHeader = findViewById(R.id.newHeader);
        TextView highscoreHeader = findViewById(R.id.highscoreHeader);
        TextView endScore = findViewById(R.id.endScoreHighscore);
        TextView cardGameNumber = findViewById(R.id.cardGameNumber);
        TextView highscoreQuestion = findViewById(R.id.highscoreQuestion);
        Button yes = findViewById(R.id.yesButton);
        Button no = findViewById(R.id.noButton);
        gameState = 1;

        newHeader.setVisibility(TextView.VISIBLE);
        highscoreHeader.setVisibility(TextView.VISIBLE);
        endScore.setVisibility(TextView.VISIBLE);
        cardGameNumber.setVisibility(TextView.VISIBLE);
        highscoreQuestion.setVisibility(TextView.VISIBLE);
        yes.setVisibility(Button.VISIBLE);
        no.setVisibility(Button.VISIBLE);

        cardGameNumber.setText(numOfCards + " Card");
        endScore.setText("Score: " + score);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameState = 2;
                EditText enterName = findViewById(R.id.enterName);
                Button submitButton = findViewById(R.id.SubmitButton);
                yes.setVisibility(Button.INVISIBLE);
                no.setVisibility(Button.INVISIBLE);
                highscoreQuestion.setVisibility(TextView.INVISIBLE);
                submitButton.setVisibility(Button.VISIBLE);
                enterName.setVisibility(EditText.VISIBLE);
                if(!fromStart)
                    enterName.setText(savedNickname);

                enterName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        submitButton.performClick();
                        return true;
                    }
                });

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String nickName = enterName.getText() + "";
                        if(nickName.equals("") ) {
                            return;
                        }
                        setNewHighscore(nickName);
                        newHeader.setVisibility(TextView.INVISIBLE);
                        highscoreHeader.setVisibility(TextView.INVISIBLE);
                        endScore.setVisibility(Button.INVISIBLE);
                        enterName.setVisibility(EditText.INVISIBLE);
                        submitButton.setVisibility(Button.INVISIBLE);
                        cardGameNumber.setVisibility(TextView.INVISIBLE);
                        highscoreQuestion.setVisibility(TextView.INVISIBLE);
                        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        showEndSequence();
                    }
                });

            }
        });

        if(!fromStart) {
            yes.performClick();
        }

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newHeader.setVisibility(TextView.GONE);
                highscoreHeader.setVisibility(TextView.GONE);
                endScore.setVisibility(TextView.GONE);
                highscoreQuestion.setVisibility(TextView.GONE);
                cardGameNumber.setVisibility(TextView.GONE);
                yes.setVisibility(Button.GONE);
                no.setVisibility(Button.GONE);
                showEndSequence();
            }
        });


    }

}