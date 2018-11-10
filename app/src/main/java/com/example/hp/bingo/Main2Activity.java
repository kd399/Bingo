package com.example.hp.bingo;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
{
    Button listen;
    Button name;
    Button desc;
    Button listdevice;
    Button blbtn;
    int requestCodeForEnable;
    Intent btEnablingIntent;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        helper.context = getApplicationContext();

        mp = MediaPlayer.create(this,R.raw.button_29);
        initiaize();
        impleamentListeners();
    }

    private void initiaize()
    {
        listen = (Button) findViewById(R.id.server);
        listdevice = (Button) findViewById(R.id.client);
        name = (Button) findViewById(R.id.name);
        desc = (Button) findViewById(R.id.desc);
        blbtn = (Button) findViewById(R.id.blbtn);
        btEnablingIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        helper.bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        requestCodeForEnable = 1;

        if( helper.bluetoothAdapter.isEnabled() )
        {
            blbtn.setBackgroundResource(R.drawable.new_swtch2);
        }
        else
        {
            blbtn.setBackgroundResource(R.drawable.new_swtch1);
        }
    }

    @Override
    public void onBackPressed()
    {
        if (mp.isPlaying())
            mp.seekTo(0);
        mp.start();

        Intent i = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void impleamentListeners()
    {
        listen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mp.isPlaying())
                    mp.seekTo(0);
                mp.start();

                if(!helper.bluetoothAdapter.isEnabled())
                {
                    Toast.makeText(getApplicationContext(),"PLEASE SWITCH ON BLUETOOTH",Toast.LENGTH_SHORT).show();
                }
                else {
                    helper.user_pro = 1;
                    Intent intent = new Intent(Main2Activity.this, Main7Activity.class);
                    startActivity(intent);

                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivity(discoverableIntent);

                    IntentFilter intentFilter = new IntentFilter(helper.bluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
                    registerReceiver(mBroadcastReceiver2,intentFilter);

                    helper.serverClass = new ServerClass();
                    helper.serverClass.start();
                    finish();
                }
            }
        });

        desc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mp.isPlaying())
                    mp.seekTo(0);
                mp.start();

                Intent i = new Intent(Main2Activity.this, Main5Activity.class);
                startActivity(i);
            }
        });

        name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mp.isPlaying())
                    mp.seekTo(0);
                mp.start();

                Intent i = new Intent(Main2Activity.this, Main6Activity.class);
                startActivity(i);
            }
        });

        listdevice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mp.isPlaying())
                    mp.seekTo(0);
                mp.start();

                if(!helper.bluetoothAdapter.isEnabled())
                {
                    Toast.makeText(getApplicationContext(),"PLEASE SWITCH ON BLUETOOTH",Toast.LENGTH_SHORT).show();
                }
                else {

                    helper.user_pro = 2;
                    Intent intent = new Intent(Main2Activity.this, Main8Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        blbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mp.isPlaying())
                    mp.seekTo(0);
                mp.start();

                if(helper.bluetoothAdapter == null)
                {
                    Toast.makeText(getApplicationContext(),"BLUETOOTH IS NOT SUPPORTED",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!helper.bluetoothAdapter.isEnabled())
                    {
                        helper.bluetoothAdapter.enable();
                        blbtn.setBackgroundResource(R.drawable.new_swtch2);
                    }
                    else
                    {
                        helper.bluetoothAdapter.disable();
                        blbtn.setBackgroundResource(R.drawable.new_swtch1);
                    }
                }
            }
        });


    }

    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Toast.makeText(context,"Discoverability Enabled",Toast.LENGTH_SHORT).show();
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Toast.makeText(context,"Discoverability Disabled. Able to receive connections",Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Toast.makeText(context,"Discoverability Disabled. Not able to receive connections",Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Toast.makeText(context,"Connecting re.",Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Toast.makeText(context,"Connected re.",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };
}
