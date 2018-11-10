package com.example.hp.bingo;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    TextView y,o,hint,t2,t3;
    Button b1,b2,b3;
    BluetoothAdapter bluetoothAdapter;
    String[] arr;
    MediaPlayer mp,mpl,mpw;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        sharedpreferences = getSharedPreferences("game", Context.MODE_PRIVATE);

        mp = MediaPlayer.create(this,R.raw.button_29);
        mpw = MediaPlayer.create(this,R.raw.winner);
        mpl = MediaPlayer.create(this,R.raw.lose);

        arr = new String[6];
        String[] array = getIntent().getStringArrayExtra("kundan1");
        arr[0] = array[0];
        arr[1] = array[1];
        arr[2] = array[2];
        arr[3] = array[3];
        arr[4] = array[4];
        arr[5] = array[5];

        y = (TextView) findViewById(R.id.y_s);
        o = (TextView) findViewById(R.id.o_s);
        hint = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);

        if(arr[0].compareTo("1")==0)
        {
            mpw.start();
            hint.setText("\n\nCONGRATULATION "+arr[4]+".... \nYOU WIN....");
        }
        else
        {
            mpl.start();
            hint.setText("\n\nSORRY "+arr[4]+".... \nYOU LOSE....");
        }


        y.setText(arr[1]);
        t2.setText(arr[4]+"'s Score");

        o.setText(arr[2]);
        t3.setText(arr[5]+"'s Score");

        b1 = (Button) findViewById(R.id.b11);
        b2 = (Button) findViewById(R.id.b12);
        b3 = (Button) findViewById(R.id.b13);
        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        impleamentListeners();
    }


    private void impleamentListeners()
    {
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mp.start();
                String[] array1 = new String[1];

                if(arr[0].compareTo("1")==0)
                    array1[0] = "3";
                else
                    array1[0] = "4";

                Intent i = new Intent(Main4Activity.this, Main3Activity.class);
                i.putExtra("kundan", array1);
                startActivity(i);
                finish();
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mp.start();
                Intent i = new Intent(Main4Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        b3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mp.start();
                bluetoothAdapter.disable();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        mp.start();
        Intent i = new Intent(Main4Activity.this, Main2Activity.class);
        startActivity(i);
        finish();
    }

}

