
package com.example.cs2450androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SetTilesActivity extends AppCompatActivity {
    private RadioGroup rg;
    private RadioButton rb;
    private int currentNumTiles;

    // method: onCreate
    // purpose: This method is called when the acitivty is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tiles);

        boolean isHighscores = getIntent().getBooleanExtra("isHighscores", false);
        rg = findViewById(R.id.radioGroup);
        currentNumTiles = 4;

        if(isHighscores) {
            TextView title = findViewById(R.id.titleTxt);
            title.setText("Select\nHighscore LeaderBoard");
            title.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        }


        Button continueButton = findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = rg.getCheckedRadioButtonId();
                rb = findViewById(radioId);
                currentNumTiles = Integer.parseInt(rb.getText().toString());
                if(!isHighscores) {
                    Intent i = new Intent(getApplicationContext(), GameActivity.class);
                    i.putExtra("currentNumTiles", currentNumTiles);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(getApplicationContext(), HighscoresActivity.class);
                    i.putExtra("currentNumTiles", currentNumTiles);
                    startActivity(i);
                }
            }
        });
    }

}