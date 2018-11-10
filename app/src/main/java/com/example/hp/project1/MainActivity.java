package com.example.hp.project1;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button b, scan,discvr;
    TextView t1;
    public Context context ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        helper.context = getApplicationContext();

        b = (Button)findViewById(R.id.bt1);
        scan = (Button)findViewById(R.id.scan);
        scan.setText("JOIN");
        discvr = (Button)findViewById(R.id.discvr);
        discvr.setText("CREATE");
        t1 = (TextView)findViewById(R.id.textView);
        t1.setText("Bluetooth Status");


        if(helper.bluetoothAdapter.isEnabled()== true)
        {
            b.setText("OFF");
            scan.setVisibility(View.VISIBLE);
            discvr.setVisibility(View.VISIBLE);
        }
        else
        {
            b.setText("ON");
            scan.setVisibility(View.INVISIBLE);
            discvr.setVisibility(View.INVISIBLE);
        }

        initiaize();
    }

    private void initiaize()
    {
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(helper.bluetoothAdapter.isEnabled() == true)
                {
                    b.setText("ON");
                    helper.bluetoothAdapter.disable();
                    scan.setVisibility(View.INVISIBLE);
                    discvr.setVisibility(View.INVISIBLE);
                    Toast.makeText(helper.context,"Bluetooth is off",Toast.LENGTH_LONG).show();
                }
                else
                {
                    b.setText("OFF");
                    helper.bluetoothAdapter.enable();
                    scan.setVisibility(View.VISIBLE);
                    discvr.setVisibility(View.VISIBLE);
                    Toast.makeText(helper.context,"Bluetooth is on",Toast.LENGTH_LONG).show();
                }
            }
        });

        scan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                helper.user_pro = 2;
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        discvr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                helper.user_pro = 1;
                Intent intent = new Intent(MainActivity.this, Main5Activity.class);
                startActivity(intent);


                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);

                IntentFilter intentFilter = new IntentFilter(helper.bluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
                registerReceiver(mBroadcastReceiver2,intentFilter);

                helper.serverClass = new ServerClass();
                helper.serverClass.start();

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
