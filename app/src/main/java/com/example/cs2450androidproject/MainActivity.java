
package com.example.cs2450androidproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static MediaPlayer songPlayer;
    private static boolean musicOn;

    // method: onCreate
    // purpose: This method is called when the acitivty is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (songPlayer == null) {
            songPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
            songPlayer.setLooping(true);
            songPlayer.start();
            musicOn = true;
        }
        if(!musicOn) {
            ImageButton musicButton = findViewById(R.id.musicButton);
            musicButton.setImageResource(R.drawable.loudspeakeroff);
        }

    }

    // method: playGame
    // purpose: This method switches the activity to the select tiles screen when clicked
    public void playGame(View v) {
        Intent i = new Intent(this, SetTilesActivity.class);
        startActivity(i);

    }

    // method: startHighscore
    // purpose: This method switches the activity to the select tiles highscore screen when clicked
    public void startHighscore(View v) {
        Intent i = new Intent( this, SetTilesActivity.class);
        i.putExtra("isHighscores",true);
        startActivity(i);
    }

    // method: toggleMusic
    // purpose: This mehtod pauses/plays the music
    public void toggleMusic(View v) {
        //creates MediaPlayer to play song
        ImageButton musicButton = findViewById(R.id.musicButton);
        if(musicOn) {
            songPlayer.pause();
            musicOn = false;
            musicButton.setImageResource(R.drawable.loudspeakeroff);
        }
        else {
            songPlayer.start();
            musicOn = true;
            musicButton.setImageResource(R.drawable.loudspeakeron);
        }
    }

}