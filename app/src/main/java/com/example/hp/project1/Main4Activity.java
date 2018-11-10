package com.example.hp.project1;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity
{
    TextView y,o,hint,t2,t3;
    Button b1,b2,b3;
    String[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        helper.context = getApplicationContext();

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
            hint.setText("\n\nCONGRATULATION "+arr[4]+".... \nYOU WIN....");
        }
        else
        {
            hint.setText("\n\nSORRY "+arr[4]+".... \nYOU LOSE....");
        }


        y.setText(arr[1]);
        t2.setText(arr[4]+"'s Score");

        o.setText(arr[2]);
        t3.setText(arr[5]+"'s Score");

        b1 = (Button) findViewById(R.id.b11);
        b2 = (Button) findViewById(R.id.b12);
        b3 = (Button) findViewById(R.id.b13);
        helper.bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        impleamentListeners();
    }


    private void impleamentListeners()
    {
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Main4Activity.this, Main3Activity.class);
                startActivity(i);
                finish();
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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
                helper.bluetoothAdapter.disable();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Main4Activity.this, Main3Activity.class);
        startActivity(i);
        finish();
    }
}
