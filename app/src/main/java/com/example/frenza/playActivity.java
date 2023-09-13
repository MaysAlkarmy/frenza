package com.example.frenza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class playActivity extends AppCompatActivity {

    ImageView rock, scissor, hand;
    Random random = new Random();
    String computer;
    TextView win;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Random random = new Random();

        rock = findViewById(R.id.rock);
        scissor = findViewById(R.id.scissor);
        hand = findViewById(R.id.hand);
        win = findViewById(R.id.win);




        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computerChosen();
                if (computer.equals("p")) {
                    win.setText("frenza win, frenza chooses paper");
                } else if (computer.equals("s")) {
                    win.setText("you win, frenza chooses scissor");
                } else if (computer.equals("r")) {
                    win.setText("Draw, frenza chooses rock");
                }
            }
        });
        hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computerChosen();
                if (computer.equals("s")) {
                    win.setText("frenza win, frenza chooses scissor");
                } else if (computer.equals("r")) {
                    win.setText("you win, frenza chooses rock");
                } else if (computer.equals("p")) {
                    win.setText("Draw, frenza chooses paper");
                }
            }
        });
        scissor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computerChosen();
                if (computer.equals("r")) {
                    win.setText("frenza win, frenza chooses rock");
                } else if (computer.equals("p")) {
                    win.setText("you win, frenza chooses paper");
                } else if (computer.equals("s")) {
                    win.setText("Draw, frenza chooses scissor");
                }
            }


        });


    }
    String computerChosen(){
        int chosen = random.nextInt(3) + 1;

        if (chosen == 1) {
            computer = "r";
        } else if (chosen == 2) {
            computer = "p";
        } else {
            computer = "s";
        }
        return computer;
    }
}