package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    int minute;
    int seconds;
    boolean isClicked = false;
    CountDownTimer timer;
    SeekBar mySeekBar;
    public  void countDown(View view){
        mySeekBar = findViewById(R.id.mySeekBar);
        if(isClicked){
            isClicked = false;
            ((Button) view).setText("Start");
            timer.cancel();
            mySeekBar.setEnabled(true);

        }
        else{
            ((Button) view).setText("Stop");
            isClicked = true;
            mySeekBar.setEnabled(false);
            long totalTime = ((minute * 60) + seconds) * 1000;
            timer = new CountDownTimer(totalTime + 100, 1000) {
                @Override
                public void onTick(long l) {
                    minute = (int)(l / 60000);
                    seconds = (int)(l % 60000) / 1000;
                    TextView myTextView = (TextView) findViewById(R.id.myTextView);
                    myTextView.setText(String.format("%02d:%02d", minute,seconds));

                }

                @Override
                public void onFinish() {
                    ImageView myImage = (ImageView) findViewById(R.id.myImage);
                    myImage.setImageResource(R.drawable.eggafter);
                    ((Button) view).setText("Start");
                    mySeekBar.setEnabled(true);
                }
            }.start();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mySeekBar = (SeekBar) findViewById(R.id.mySeekBar);
        mySeekBar.setMax(600);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minute  = progress / 60;
                seconds = progress % 60;
                TextView myTextView = (TextView) findViewById(R.id.myTextView);
                myTextView.setText(String.format("%02d:%02d", minute,seconds));
                ImageView myImage = (ImageView) findViewById(R.id.myImage);
                myImage.setImageResource(R.drawable.eggbefore);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}