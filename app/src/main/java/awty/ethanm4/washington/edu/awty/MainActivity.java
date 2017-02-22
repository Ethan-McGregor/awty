package awty.ethanm4.washington.edu.awty;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private boolean running = false;
    private  boolean alarmActive;
    private boolean validated;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button strButton = (Button)findViewById(R.id.strButton);
        Intent activeIntent = new Intent(MainActivity.this, Alarm.class);

        //checks if alarm is already running when app is opened
         alarmActive = (PendingIntent.getBroadcast(MainActivity.this, 0, activeIntent, PendingIntent.FLAG_NO_CREATE) != null);

        //Waits for start/stop button to be clicked
        strButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create alarm manager
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                //Find views
                EditText messageEntry = (EditText)findViewById(R.id.message);
                EditText phoneNumberEntry = (EditText)findViewById(R.id.phoneNumber);

                //Intents
                Intent intent = new Intent(MainActivity.this, Alarm.class);
                intent.putExtra("text", messageEntry.getText().toString());
                intent.putExtra("phone", phoneNumberEntry.getText().toString());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                validated = validate();

                //if alarm is not running, start it
                if (!running) {

                    //if all fields are validated
                    if (validated) {
                        running = true;

                        EditText intervalEntry = (EditText)findViewById(R.id.interval);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (Integer.parseInt(intervalEntry.getText().toString()) * 1000 * 60), pendingIntent);
                        strButton.setText("Stop");
                    }
                } else {
                    running = false;
                    strButton.setText("Start");
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                }
            }
        });

        // changes text of button if alarm is active
        if (alarmActive) {
            strButton.setText("Stop");
        }
    }

    //Validates all user inputs
    public boolean validate() {
        EditText message = (EditText)findViewById(R.id.message);
        EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        EditText interval = (EditText)findViewById(R.id.interval);
        
        //if message is larger then 0
        if (message.getText().length() == 0) {
            Toast.makeText(MainActivity.this,"Please enter a message", Toast.LENGTH_SHORT).show();
            return false;
            
            //if phone number is larger then 0
        } else if (phoneNumber.length() == 0) {
            Toast.makeText(MainActivity.this,"Please enter a Phone number", Toast.LENGTH_SHORT).show();
            return false;
            
            // if interval is larger then 0
        } else if (Integer.parseInt(interval.getText().toString()) < 0) {
            Toast.makeText(MainActivity.this,"Please enter a interval", Toast.LENGTH_SHORT).show();
            return false;

            // makes usre phone number is proper format
        } else if(!phoneNumber.getText().toString().matches("\\d{3}\\d{3}\\d{4}")) {
            Toast.makeText(MainActivity.this,"Please Enter a valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

            return true;
        }

}
