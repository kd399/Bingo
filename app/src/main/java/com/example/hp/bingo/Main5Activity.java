package com.example.hp.bingo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main5Activity extends AppCompatActivity {

    Button back;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        mp = MediaPlayer.create(this,R.raw.button_29);
        back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mp.start();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        mp.start();
        finish();
    }
}
