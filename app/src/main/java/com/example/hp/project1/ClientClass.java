package com.example.hp.project1;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class ClientClass  extends Thread
{
    private BluetoothSocket socket;
    private BluetoothDevice device;

    private static final UUID MY_UUID = UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a67");
    private static final String APP_NAME = "Btchat";

    public ClientClass(BluetoothDevice device1)
    {
        device = device1;

        try {
            socket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e)
        {
            Toast.makeText(helper.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void run()
    {
        try
        {
            socket.connect();
            Message message = Message.obtain();
            message.what = helper.STATE_CONNECTED;
            helper.handler.sendMessage(message);

            helper.sendRecieve = new SendRecieve(socket);
            helper.sendRecieve.start();
        }
        catch (IOException e)
        {
            //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            e.getStackTrace();
            Message message = Message.obtain();
            message.what = helper.STATE_CONNECTION_FAILED;
            helper.handler.sendMessage(message);
        }
    }

    public void close()
    {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            Toast.makeText(helper.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
