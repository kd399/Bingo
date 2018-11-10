package com.example.hp.bingo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class helper
{
    public static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public static ServerClass serverClass;
    public static Context context;
    public static ClientClass clientClass;
    public static SendRecieve sendRecieve;
    static final int STATE_LISTENING=1;
    static final int STATE_CONNECTING=2;
    static final int STATE_CONNECTED=3;
    static final int STATE_CONNECTION_FAILED=4;
    static final int STATE_MESSAGE_RECEIVED=5;
    public static String yr_nm,op_nm;
    public static int user_pro=0;
    public static Activity activity ;

    public static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what)
            {
                case STATE_LISTENING :
                    //textstatus.setText("listening");
                    Toast.makeText(context,"listening", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_CONNECTING :
                    //textstatus.setText("connecting");
                    Toast.makeText(context,"connecting", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_CONNECTED :
                    //textstatus.setText("connected");
                    Toast.makeText(context,"connected", Toast.LENGTH_SHORT).show();
                    //----------------------------------------game_visible();---------------------------

                    Intent i = new Intent(context, Main3Activity.class);
                    context.startActivity(i);
                    activity.finish();

                    break;
                case STATE_CONNECTION_FAILED :
                    //textstatus.setText("connection failed");
                    Toast.makeText(context,"connection failed", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_MESSAGE_RECEIVED :
                    byte[] readBuffer = (byte[])msg.obj;
                    String tempMsg = new String(readBuffer,0,msg.arg1);
                    //message mil gaya
                    Main3Activity.revice_msg(tempMsg);
                    break;
            }
            return true;
        }
    });

}
