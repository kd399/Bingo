package com.example.hp.project1;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class ServerClass extends Thread
{
    private BluetoothServerSocket serverSocket;
    private static final UUID MY_UUID = UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a67");
    private static final String APP_NAME = "Btchat";


    public ServerClass()
    {
        try {
            serverSocket = helper.bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME,MY_UUID);
        } catch (IOException e)
        {
            Toast.makeText(helper.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void run()
    {
        BluetoothSocket socket = null;
        while(socket == null)
        {
            try {
                Message message = Message.obtain();
                message.what = helper.STATE_CONNECTING;
                helper.handler.sendMessage(message);
                socket = serverSocket.accept();
            } catch (IOException e)
            {
                e.getStackTrace();
                //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                Message message = Message.obtain();
                message.what = helper.STATE_CONNECTION_FAILED;
                helper.handler.sendMessage(message);
            }

            if(socket != null)
            {
                Message message = Message.obtain();
                message.what = helper.STATE_CONNECTED;
                helper.handler.sendMessage(message);

                helper.sendRecieve = new SendRecieve(socket);
                helper.sendRecieve.start();

                break;
            }
        }
    }

    public void close()
    {
        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
            Toast.makeText(helper.context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

           /* try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
    }
}
