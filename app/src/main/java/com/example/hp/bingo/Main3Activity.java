package com.example.hp.bingo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = "Main3Activity";
    public static MediaPlayer mp,mpr,mpp,mps;
    ImageView image_gif,imageView;
    public static Button[] bng;
    int i_bng=0;
    int index=0;
    static Activity activity ;

    public BluetoothDevice mBTDevice;
    public DeviceListAdapter mDeviceListAdapter;

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btArray;

    public static Context context ;
    public static TextView status;
    public static Button[] b;

    TableLayout tableLayout;
    public static TextView your, oppnnt, y, o, hint;
    public static int stag = 0, your_point = 0, opponent_point = 0, carry=0, srvr_hlp=0, PLAYER = 2;
    int number_of_visit=0;

    public static int[] button_number;
    final int col = 7;

    public static boolean nm = false;
    public static boolean player_ready = false;
    public static boolean name_send = false, turn = false;
    public static boolean[] button_fill;
    public static boolean[] row;
    public static boolean[] coloum;
    public static boolean[] diaglo;


    public static String your_name = null,opponent_name = null;



    public static void revice_msg(String tempMsg)
    {
        if (mpr.isPlaying())
            mpr.seekTo(0);

        mpr.start();
        //Toast.makeText(context,tempMsg, Toast.LENGTH_SHORT).show();
        switch(1)
        {
            case 1:
            {

                if ( (!nm) && (tempMsg.charAt(0) == '9'))
                {
                    helper.op_nm = new String(tempMsg.substring(2));
                    nm = true;
                    break;
                }

                int[] arr = new int[3];
                int i = 0, n = tempMsg.length(), j = 0;
                for (i = 0; i < 3; i++)
                    arr[i] = 0;

                for (i = 0; i < n; i++)
                {
                    if (tempMsg.charAt(i) != ' ')
                        arr[j] = arr[j] * 10 + (tempMsg.charAt(i) - '0');
                    else
                        j++;
                }

                if (arr[0] == 51)
                {
                    player_ready = true;
                    break;
                }

                for (i = 0; i < 49; i++)
                {
                    if (arr[0] == button_number[i])
                        break;
                }

                if (arr[1] > 0)
                {
                    opponent_point += arr[1];
                    oppnnt.setText(String.valueOf(opponent_point));

                    if(opponent_point == 6)
                        mps.start();
                }

                if (arr[2] > 0)
                {
                    end_game(0, your_point, opponent_point);
                    break;
                }


                button_fill[i] = true;
                b[i].setBackgroundResource(R.drawable.tile3);
                b[i].setText("");

                int pt = point(i);

                if (pt > 0)
                {
                    String r_msg = String.valueOf(50);
                    your_point += pt;
                    your.setText(String.valueOf(your_point));
                    r_msg = r_msg + " " + String.valueOf(pt);

                    if (your_point >= 7)
                    {
                        r_msg = r_msg + " " + String.valueOf(1);
                        helper.sendRecieve.write(r_msg.getBytes());
                        end_game(1, your_point, opponent_point);
                        break;
                    }
                    else
                        carry = pt;
                }

                turn = true;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        context =  getApplicationContext();
        helper.context = getApplicationContext();

        mp = MediaPlayer.create(this,R.raw.button_29);
        mpr = MediaPlayer.create(this,R.raw.recve);
        mps = MediaPlayer.create(this,R.raw.suspens);
        mpp = MediaPlayer.create(this,R.raw.point);

        helper.serverClass = new ServerClass();
        helper.serverClass.start();

        initiaize();

        helper.yr_nm = helper.bluetoothAdapter.getName();
        String msg = String.valueOf(9);
        msg = msg+" "+helper.yr_nm+"\0";
        helper.sendRecieve.write(msg.getBytes());

        if(helper.user_pro == 1)
        {
            srvr_hlp = 1;
        }

        activity = this;
    }

    private void initiaize()
    {
        stag=1;
        your_point=0;
        opponent_point=0;
        carry=0;
        nm=false;

        image_gif =(ImageView) findViewById(R.id.image_gif);
        status = (TextView) findViewById(R.id.status);
        imageView = (ImageView) findViewById(R.id.matrix);
        your = (TextView) findViewById(R.id.yr_scr);
        oppnnt = (TextView) findViewById(R.id.opst_scr);
        y = (TextView) findViewById(R.id.you);
        o = (TextView) findViewById(R.id.opposite);

        hint = (TextView) findViewById(R.id.hint);
        hint.setText("\nFILL THIS METRIX\nRENDOMALLY..");

        b = new Button[49];

        bng = new Button[7];
        bng[0] = (Button) findViewById(R.id.b6);
        bng[1] = (Button) findViewById(R.id.b5);
        bng[2] = (Button) findViewById(R.id.b4);
        bng[3] = (Button) findViewById(R.id.b3);
        bng[4] = (Button) findViewById(R.id.b2);
        bng[5] = (Button) findViewById(R.id.b1);
        bng[6] = (Button) findViewById(R.id.b0);

        button_fill = new boolean[49];
        for(int i=0;i<49;i++)
            button_fill[i] = false;

        row = new boolean[7];
        coloum = new boolean[7];
        for(int i=0;i<7;i++)
        {
            row[i]=false;
            coloum[i]=false;
            bng[i].setVisibility(View.INVISIBLE);
        }

        button_number = new int[49];
        diaglo = new boolean[2];
        for(int i=0;i<2;i++)
            diaglo[i]=false;


        int k=0;
        tableLayout = (TableLayout)findViewById(R.id.inner_m);

        for(int i=0;i<col;i++)
        {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,1.0f
            ));
            for(int j=0;j<col;j++)
            {
                final Button button = new Button(this);
                button.setTextColor(getApplication().getResources().getColor(R.color.colorfrnt));
                button.setBackgroundResource(R.drawable.tile);

                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,1.0f
                ));

                b[k++] = button;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Onclick(v);
                    }
                });

                tableRow.addView(button);
            }
        }
    }

    public void Onclick(View v)
    {
        if(stag == 1)
        {
            for(int i=0;i<49;i++)
            {
                if(v == b[i] && (!button_fill[i]))
                {
                    if (mp.isPlaying())
                        mp.seekTo(0);

                    mp.start();

                    button_fill[i] = true;
                    ++number_of_visit;
                    b[i].setBackgroundResource(R.drawable.tile2);
                    b[i].setText(String.valueOf(number_of_visit));
                    button_number[i] = number_of_visit;

                    if(number_of_visit == 49)
                    {
                        stag=2;
                        for(int j=0;j<49;j++)
                            button_fill[j] = false;

                        hint.setVisibility(View.INVISIBLE);
                        your.setVisibility(View.VISIBLE);
                        oppnnt.setVisibility(View.VISIBLE);

                        y.setVisibility(View.VISIBLE);
                        y.setText("\n"+helper.yr_nm);
                        your_name = helper.yr_nm;

                        o.setVisibility(View.VISIBLE);
                        o.setText("\n"+helper.op_nm);
                        opponent_name = helper.op_nm;

                        for(int j=0;j<7;j++)
                        {
                            bng[j].setVisibility(View.VISIBLE);
                        }

                        if(helper.user_pro == 1)
                            turn=true;
                        else
                        {
                            String msg = String.valueOf(51);
                            msg = msg+" 0 0";
                            helper.sendRecieve.write(msg.getBytes());
                            player_ready = true;
                        }
                    }
                    break;
                }
            }
        }
        else
        {
            if(player_ready == false)
            {
                if (mp.isPlaying())
                    mp.seekTo(0);

                mp.start();
                Toast.makeText(Main3Activity.this, opponent_name+" NOT READY..", Toast.LENGTH_SHORT).show();
                return;
            }

            if((stag == 2) && (turn == true))
            {
                for(int i=0;i<49;i++)
                {
                    boolean FLAG1=false;
                    if((v==b[i]) && (!button_fill[i]))
                    {
                        if (mp.isPlaying())
                            mp.seekTo(0);

                        mp.start();
                        String msg = String.valueOf(button_number[i]);
                        b[i].setBackgroundResource(R.drawable.tile3);
                        button_fill[i] = true;
                        b[i].setText("");

                        int pt = point(i);

                        if((pt+carry) > 0)
                        {
                            your_point+=pt;
                            your.setText(String.valueOf(your_point));
                            msg = msg +" "+String.valueOf((pt+carry));
                            carry=0;
                            if(your_point >= 7)
                            {
                                msg = msg +" "+String.valueOf(1);
                                FLAG1=true;
                            }
                        }
                        helper.sendRecieve.write(msg.getBytes());
                        turn=false;

                        if(FLAG1)
                        {
                            end_game(1,your_point,opponent_point);
                        }
                    }
                }
            }
        }
    }

    public static void img(int posi)
    {
        mpp.start();
        switch(posi)
        {
            case 1:
                bng[0].setBackgroundResource(R.drawable.o_b);
                break;
            case 2:
                bng[1].setBackgroundResource(R.drawable.o_i);
                break;
            case 3:
                bng[2].setBackgroundResource(R.drawable.o_n);
                break;
            case 4:
                bng[3].setBackgroundResource(R.drawable.o_g);
                break;
            case 5:
                bng[4].setBackgroundResource(R.drawable.o_o);
                break;
            case 6:
                bng[5].setBackgroundResource(R.drawable.o_s);
                break;
            case 7:
                bng[6].setBackgroundResource(R.drawable.o_s);
                break;
        }
    }

    public static int point(int posi)
    {
        int pt=0;

        int tmp_row = posi/7;
        int tmp_clm = posi%7;
        int i;

        boolean flag = false;
        if(!row[tmp_row])
        {
            for(i=tmp_row*7; i<(tmp_row+1)*7 ;i++)
            {
                if(!button_fill[i])
                    break;
            }
            if(i == (tmp_row+1)*7)
            {
                pt++;
                row[tmp_row]=true;
                img(your_point+pt);
            }
        }

        if(!coloum[tmp_clm])
        {
            for(i=tmp_clm;i<49;i=i+7)
            {
                if(!button_fill[i])
                    break;
            }

            if(i>=49)
            {
                pt++;
                coloum[tmp_clm]=true;
                img(your_point+pt);
            }
        }

        if(!diaglo[0])
        {
            for(i=0;i<49;i=i+8)
            {
                if(!button_fill[i])
                    break;
            }

            if(i>48)
            {
                pt++;
                diaglo[0]=true;
                img(your_point+pt);
            }
        }

        if(!diaglo[1])
        {
            for(i=6;i<43;i=i+6)
            {
                if(!button_fill[i])
                    break;
            }

            if(i>42)
            {
                pt++;
                diaglo[1]=true;
                img(your_point+pt);
            }
        }
        return pt;
    }


    public static void end_game(int a,int b,int c)
    {
        if (mpr.isPlaying())
            mpr.seekTo(0);

        if (mps.isPlaying())
            mps.seekTo(0);

        mps.stop();
        mpr.stop();

        String[] array1 = new String[6];
        array1[0] = String.valueOf(a);
        array1[1] = String.valueOf(b);
        array1[2] = String.valueOf(c);
        array1[3] = String.valueOf(srvr_hlp);
        array1[4] = your_name;
        array1[5] = opponent_name;
        Intent in=new Intent(context,Main4Activity.class);
        in.putExtra("kundan1",array1);
        context.startActivity(in);
        activity.finish();
        //close_game();
    }

    @Override
    public void onBackPressed()
    {
        if (mp.isPlaying())
            mp.seekTo(0);

        mp.start();
        Intent i = new Intent(Main3Activity.this, Main2Activity.class);
        startActivity(i);
        finish();
    }
}
