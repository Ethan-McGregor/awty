package awty.ethanm4.washington.edu.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getStringExtra("text");
        String number = intent.getStringExtra("phone");
        Toast.makeText(context, number + ": " + text, Toast.LENGTH_SHORT).show();
        Log.v("ALARM","fired");
    }
}