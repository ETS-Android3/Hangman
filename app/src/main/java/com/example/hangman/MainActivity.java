package com.example.hangman;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //declare public static to access them into fetchdata class
    public static int dataSize;
    public static String data ="";
    public static LinearLayout mainLinearLayout,mainLinearLayout2,mainLinearTextLayout;
    public static TextView textView;
    public static LinearLayout.LayoutParams params;
    public static Context context;
    TextView charButton;
    int charCount = 0,life = 5;
    TextView lifeRemained;
    public static void addTextView()//creating all the textviews for each character of word
    {
        for (int id = 0;id<dataSize;++id) {
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView = new TextView(context);
            textView.setId(id);
            textView.setTextSize(20);
            textView.setText(" __ ");
            textView.setLayoutParams(params);
            mainLinearTextLayout.addView(textView);
        }
    }
    @SuppressLint("ResourceAsColor")
    public void clickCharachter(View view)
    {
        charButton = findViewById(view.getId());
        charButton.setBackgroundResource(R.drawable.shape_change);
        charButton.setTextColor(R.color.white);
        charButton.setClickable(false);

        editBlankSpace(data,charButton.getText().toString().charAt(0));
    }
// adding character on each click on submit

    public void editBlankSpace(String word, char ec)
    {
                if (data.indexOf(ec) != -1) {

                    charButton.setBackgroundResource(R.drawable.shape_change);
                    String underscore = "";
                    for (int i = 0; i < dataSize; ++i) {
                        TextView tv = findViewById(i);
                        if (word.charAt(i) == ec) { //setting correct character in textviews

                            tv.setText(String.valueOf(" " + ec + " "));
                            ++charCount;
                        }
                    }
                } else {    //decreasing life when wrong character entered
                    --life;

                    Toast.makeText(this, "You lost a life", Toast.LENGTH_SHORT).show();
                    charButton.setBackgroundResource(R.drawable.shape_change3);
                    lifeRemained.setText(String.valueOf(life));
                }


        if(life == 0)//creating dailog box to again try the game after game lost
        {
            final AlertDialog.Builder loseDailog = new AlertDialog.Builder(this);
            View alertLoseView = getLayoutInflater().inflate(R.layout.lose_dailog, null);
            TextView tryAgain = alertLoseView .findViewById(R.id.loseButton);
            loseDailog.setView(alertLoseView);
            final AlertDialog loseAlert = loseDailog.create();
            loseAlert.show();
            loseAlert.setCanceledOnTouchOutside(false);
            loseAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                }
            });
        }
        if(charCount == dataSize)//creating dailog box to start new game after game won
        {
            final AlertDialog.Builder winDailog = new AlertDialog.Builder(this);
            View alertWinView = getLayoutInflater().inflate(R.layout.win_dailog, null);
            TextView newGame = alertWinView .findViewById(R.id.winButton);
            winDailog.setView(alertWinView);
            final AlertDialog winAlert = winDailog.create();
            winAlert.show();
            winAlert.setCanceledOnTouchOutside(false);
            winAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            newGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                }
            });
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //calling all layouts
        mainLinearLayout = findViewById(R.id.mainLinearLayout);
        mainLinearLayout2 = findViewById(R.id.mainLinearLayout2);
        mainLinearTextLayout = findViewById(R.id.mainLinearTextLayout);
        lifeRemained = findViewById(R.id.lifeRemained);
        context = this;

        //executing background activity through fetchData class
        fetchData fd = new fetchData();
        fd.execute();
    }
}