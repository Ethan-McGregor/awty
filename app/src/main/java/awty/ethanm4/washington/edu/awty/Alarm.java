package awty.ethanm4.washington.edu.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

public class Alarm extends BroadcastReceiver {

    private String text;
    private String number;
    private Context contexts;

    @Override
    public void onReceive(Context context, Intent intent) {

        contexts = context;
         text = intent.getStringExtra("text");
         number = intent.getStringExtra("phone");

//        Toast.makeText(context, number + ": " + text, Toast.LENGTH_SHORT).show();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, text, null, null);


        Log.v("ALARM","fired");
    }

}