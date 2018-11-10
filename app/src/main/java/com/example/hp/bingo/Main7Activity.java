package com.example.hp.bingo;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main7Activity extends AppCompatActivity {

    Context context ;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        context = getApplicationContext();
        helper.context = getApplicationContext();
        helper.activity = this;
        mp = MediaPlayer.create(this,R.raw.button_29);
    }

    @Override
    public void onBackPressed()
    {
        mp.start();
        Intent i = new Intent(Main7Activity.this, Main2Activity.class);
        startActivity(i);
        finish();
    }
}
