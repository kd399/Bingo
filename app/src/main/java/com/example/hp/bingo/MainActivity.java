package com.example.hp.bingo;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    Button btnStart;
    MediaPlayer mp;
    BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper.context = getApplicationContext();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mp = MediaPlayer.create(this,R.raw.button_29);
        initiaize();
        impleamentListeners();
    }

    private void initiaize()
    {
        btnStart = (Button) findViewById(R.id.b1);
    }

    private void impleamentListeners()
    {
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mp.isPlaying())
                    mp.seekTo(0);

                mp.start();
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (mp.isPlaying())
            mp.seekTo(0);
        mp.start();
        helper.bluetoothAdapter.disable();
        finish();
        System.exit(0);
    }
}
