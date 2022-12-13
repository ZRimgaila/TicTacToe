package com.example.gameconnect3;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // 0: empty, 1: gold, 2: silver
    int activePlayer = 1;
    int[] gameState = {0,0,0,0,0,0,0,0,0};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    boolean gameActive = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void dropIn(View view){
        ImageView place = (ImageView) view;
        int tappedPlace = Integer.parseInt(place.getTag().toString());
        if(gameState[tappedPlace] == 0 && gameActive) {
            gameState[tappedPlace] = activePlayer;
            place.setTranslationY(-1500);
            if (activePlayer == 2) {
                place.setImageResource(R.drawable.silver);
                activePlayer = 1;
            } else {
                place.setImageResource(R.drawable.gold);
                activePlayer = 2;
            }
            place.animate().rotation(360).translationYBy(1500).setDuration(500);

            for (int[] winPos : winningPositions) {
                if (gameState[winPos[0]] == gameState[winPos[1]] && gameState[winPos[1]] == gameState[winPos[2]] && gameState[winPos[1]] != 0) {
                    gameActive = false;
                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "Silver";
                    } else {
                        winner = "Gold";
                    }
                    TextView textView = (TextView) findViewById(R.id.textOfTheWinner);
                    Button button = (Button) findViewById(R.id.buttonPlayAgain);
                    textView.setText("Team " + winner + " has won!");
                    button.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view){
        TextView textView = (TextView) findViewById(R.id.textOfTheWinner);
        Button button = (Button) findViewById(R.id.buttonPlayAgain);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < grid.getChildCount(); i++) {
            ImageView imageView = (ImageView) grid.getChildAt(i);
            imageView.setImageDrawable(null);
        }

        for(int i=0; i < gameState.length; i++){
            gameState[i] = 0;
        }
        activePlayer = 1;
        gameActive = true;
    }
}