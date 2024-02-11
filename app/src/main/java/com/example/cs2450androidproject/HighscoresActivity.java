
package com.example.cs2450androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class HighscoresActivity extends AppCompatActivity {

    private int currentNumCards;

    // method: onCreate
    // purpose: This method is called when the acitivty is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        currentNumCards = getIntent().getIntExtra("currentNumTiles", -1);


        TextView highscore1 = findViewById(R.id.highscore1);
        TextView highscore2 = findViewById(R.id.highscore2);
        TextView highscore3 = findViewById(R.id.highscore3);
        TextView highscore4 = findViewById(R.id.highscore4);
        TextView highscore5 = findViewById(R.id.highscore5);

        try {
            String[] topFive = getTopFive();
            highscore1.setText(topFive[0]);
            highscore2.setText(topFive[1]);
            highscore3.setText(topFive[2]);
            highscore4.setText(topFive[3]);
            highscore5.setText(topFive[4]);
        }catch(Exception e) {}

    }

    // method: getTopFive
    // purpose: This method gets the top five scores from the txt file
    // and places them into an array
    private String[] getTopFive() throws Exception{

        FileInputStream fi = null;
        FileOutputStream fo = null;
        String fileName = "Highscores" + currentNumCards + ".txt";
        byte[] buffer = null;
        try {
            fo = this.openFileOutput(fileName, Context.MODE_APPEND);
            fo.close();
            fi = this.openFileInput(fileName);
            buffer = new byte[fi.available()];
            fi.read(buffer);
            fi.close();
        } catch (Exception e) { }

        String scoresFile = new String(buffer);
        Scanner scnr = new Scanner(scoresFile);
        int counter = 0;
        String[] topFive = new String[5];
        while(scnr.hasNext()) {
            scnr.nextLine();
            counter++;
        }
        if(counter >= 5) {
            Scanner top5 = new Scanner(scoresFile);
            for(int i = 0; i < 5; i++) {
                String name = top5.next();
                if(name.length() < 5) {
                    String tempName = name;
                    for(int j = name.length(); j < 5; j++) {
                        tempName += ".";
                    }
                    name = tempName;
                }
                else if (name.length() > 5){
                    name = name.substring(0,5);
                }
                String score = top5.next() +"";
                int scoreLength = score.length();
                String tempString = name.toUpperCase() + ".....";
                while(scoreLength < 2) {
                    tempString += 0;
                    scoreLength++;
                }
                topFive[i] = tempString + score;
            }
        }
        else{
            Scanner top5 = new Scanner(scoresFile);
            for(int i = 0; i < counter; i++) {
                String name = top5.next();
                if(name.length() < 5) {
                    String tempName = name;
                    for(int j = name.length(); j < 5; j++) {
                        tempName += ".";
                    }
                    name = tempName;
                }
                else if (name.length() > 5){
                    name = name.substring(0,5);
                }
                String score = top5.next() + "" ;
                int scoreLength = score.length();
                String tempString = name.toUpperCase() + "...";
                while(scoreLength < 2) {
                    tempString += 0;
                    scoreLength++;
                }
                topFive[i] = tempString + score;
            }
            for(int i = counter; i < 5; i++) {
                topFive[i] = "ABC.....00";
            }
        }

        return topFive;
    }

    // method: goHome
    // purpose: This method switches the activity to the home screen
    public void goHome(View v){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}