package com.example.hp.bingo;

import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SendRecieve extends Thread
{
    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SendRecieve(BluetoothSocket socket)
    {
        bluetoothSocket = socket;
        InputStream tempIn= null;
        OutputStream tempOut= null;

        try
        {
            tempIn = bluetoothSocket.getInputStream();
            tempOut = bluetoothSocket.getOutputStream();
        }
        catch (IOException e)
        {
            Toast.makeText(helper.context,"Sendrecieve "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        inputStream = tempIn;
        outputStream = tempOut;

    }

    public void run()
    {
        byte[] buffer = new byte[1024];
        int bytes;

        while(true)
        {
            try {
                bytes = inputStream.read(buffer);
                helper.handler.obtainMessage(helper.STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();

            } catch (IOException e) {
                //Toast.makeText(getApplicationContext(),"Sendrecieve "+e.getMessage(),Toast.LENGTH_SHORT).show();
                e.getStackTrace();
            }
        }
    }

    public void write(byte[] bytes)
    {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            Toast.makeText(helper.context,"Sendrecieve "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void close()
    {
        if (inputStream != null) {
            try {inputStream.close();} catch (Exception e) {}
            inputStream = null;
        }

        if (outputStream != null) {
            try {outputStream.close();} catch (Exception e) {}
            outputStream = null;
        }

        if (bluetoothSocket.isConnected()) {
            try {bluetoothSocket.close();} catch (Exception e) {}
            bluetoothSocket = null;
        }
    }
}
