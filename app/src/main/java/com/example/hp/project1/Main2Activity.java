package com.example.hp.project1;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
{
    ListView list_d;
    Button listen;
    ArrayList<BluetoothDevice> mBTDevices;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    Context context ;
    public DeviceListAdapter mDeviceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context = getApplicationContext();
        helper.context = getApplicationContext();

        list_d = (ListView) findViewById(R.id.list_d);
        listen = (Button) findViewById(R.id.scan);
        listen.setText("Refresh");

        mBTDevices = new ArrayList<BluetoothDevice>();

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
        list_d.setAdapter(arrayAdapter);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);


        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                helper();
            }
        });

        list_d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                helper.op_nm = mBTDevices.get(i).getName();
                helper.clientClass = new ClientClass(mBTDevices.get(i));
                helper.clientClass.start();
            }
        });


    }

    public void helper()
    {
        int index=0;
        if(mBTDevices.size() > 0)
            mBTDevices.clear();

        if(helper.bluetoothAdapter.isDiscovering())
        {
            helper.bluetoothAdapter.cancelDiscovery();
            check();
            helper.bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(broadcastReceiver, discoverDevicesIntent);
        }

        if(!helper.bluetoothAdapter.isDiscovering())
        {
            check();
            helper.bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(broadcastReceiver, discoverDevicesIntent);
        }
    }

    private void check()
    {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        {
            int permn = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permn = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permn += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            }
            if(permn != 0)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1001);
                }
            }
        }
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                // Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                list_d.setAdapter(mDeviceListAdapter);
            } else {
                Toast.makeText(context, "NO NEW DEVICE FOUND", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
            {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Toast.makeText(context, "BOND_BONDED.", Toast.LENGTH_SHORT).show();
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Toast.makeText(context, "BOND_BONDING.", Toast.LENGTH_SHORT).show();
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Toast.makeText(context, "BOND_NONE", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
