
package com.example.cs2450androidproject;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class GameCardView extends View {

    private ImageView tile;
    private GameActivity ga;
    private String value;
    private GameCard currCard;

    // method: GameCardView constructor
    // purpose: This method sets the default values
    public GameCardView(Context context, GameActivity ga, GameCard currCard) {
        super(context);

        this.ga = ga;
        this.value = currCard.getValue();
        int tileNum = currCard.getTileNum();
        this.currCard = currCard;

        setImageId(tileNum);

        if (currCard.isFlipped())
            setTileImage(value);
        else
            setBackImage();

        if (currCard.isCorrect())
            setInvisible();
        else {
            tile.setVisibility(ImageView.VISIBLE);
        }

        tile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ga.flipCard(tileNum);
            }
        });

    }

    // method: setImageId
    // purpose: This method assigns the image id for each tile
    private void setImageId(int tileNum) {
        switch (tileNum) {
            case 1:
                tile = ga.findViewById(R.id.tile1);
                break;
            case 2:
                tile = ga.findViewById(R.id.tile2);
                break;
            case 3:
                tile = ga.findViewById(R.id.tile3);
                break;
            case 4:
                tile = ga.findViewById(R.id.tile4);
                break;
            case 5:
                tile = ga.findViewById(R.id.tile5);
                break;
            case 6:
                tile = ga.findViewById(R.id.tile6);
                break;
            case 7:
                tile = ga.findViewById(R.id.tile7);
                break;
            case 8:
                tile = ga.findViewById(R.id.tile8);
                break;
            case 9:
                tile = ga.findViewById(R.id.tile9);
                break;
            case 10:
                tile = ga.findViewById(R.id.tile10);
                break;
            case 11:
                tile = ga.findViewById(R.id.tile11);
                break;
            case 12:
                tile = ga.findViewById(R.id.tile12);
                break;
            case 13:
                tile = ga.findViewById(R.id.tile13);
                break;
            case 14:
                tile = ga.findViewById(R.id.tile14);
                break;
            case 15:
                tile = ga.findViewById(R.id.tile15);
                break;
            case 16:
                tile = ga.findViewById(R.id.tile16);
                break;
            case 17:
                tile = ga.findViewById(R.id.tile17);
                break;
            case 18:
                tile = ga.findViewById(R.id.tile18);
                break;
            case 19:
                tile = ga.findViewById(R.id.tile19);
                break;
            default:
                tile = ga.findViewById(R.id.tile20);

        }
    }

    // method: setTilesImage
    // purpose: This method assigns an image for each tile
    public void setTileImage(String value) {

        switch(value) {
            case "Cookie":
                tile.setImageResource(R.drawable.cookie);
                break;
            case "Eggs":
                tile.setImageResource(R.drawable.eggs);
                break;
            case "Cheese":
                tile.setImageResource(R.drawable.cheese);
                break;
            case "Salami":
                tile.setImageResource(R.drawable.salami);
                break;
            case "Chocolate":
                tile.setImageResource(R.drawable.chocolate);
                break;
            case "Crackers":
                tile.setImageResource(R.drawable.crackers);
                break;
            case "Cereal":
                tile.setImageResource(R.drawable.cereal);
                break;
            case "Rice":
                tile.setImageResource(R.drawable.rice);
                break;
            case "Steak":
                tile.setImageResource(R.drawable.steak);
                break;
            case "Chicken":
                tile.setImageResource(R.drawable.chicken);
                break;

        }

    }

    // method: updateCard
    // purpose: This method updates the tile after it is clicked
    public void updateCard(){
        if (currCard.isCorrect()) {
            setTileImage(value);
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    setInvisible();
                }
            };
            timer.schedule(task, 1000);
        }
        else if(currCard.isFlipped()) {
            setTileImage(value);
        }
        else
            setBackImage();
    }

    // method: setInvisible
    // purpose: This method makes the tile go invisible
    private void setInvisible() {
        tile.setVisibility(ImageView.INVISIBLE);
    }

    // method: setBackImage
    // purpose: This method unflips a card
    private void setBackImage() {
        tile.setImageResource(R.drawable.back);
    }

}
