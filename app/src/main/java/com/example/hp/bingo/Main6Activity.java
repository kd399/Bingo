package com.example.hp.bingo;

import android.bluetooth.BluetoothAdapter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main6Activity extends AppCompatActivity {

    Button close,ok;
    TextView c_name;
    EditText n_name;
    BluetoothAdapter bluetoothAdapter;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        mp = MediaPlayer.create(this,R.raw.button_29);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        close = (Button) findViewById(R.id.close);
        ok = (Button) findViewById(R.id.ok);
        c_name = (TextView) findViewById(R.id.c_name);
        n_name = (EditText) findViewById(R.id.n_name);

        c_name.setText(bluetoothAdapter.getName());
        initilization();
    }

    private void initilization()
    {
        close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mp.start();
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mp.start();
                if( n_name.getText().toString() != null )
                    bluetoothAdapter.setName(n_name.getText().toString());
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
