package com.kurobarabenjamingeorge.simplealarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton alarmToggleButton;

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;

    private static final String ALARM_NOTIFICATION = "com.kurobarabenjamingeorge.simplealarm.ALARM_NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent alarmIntent = new Intent(ALARM_NOTIFICATION);
        final PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID,
                alarmIntent,
                PendingIntent.FLAG_NO_CREATE);

        boolean alarmExists = (PendingIntent.getBroadcast(this,
                NOTIFICATION_ID,
                alarmIntent,
                PendingIntent.FLAG_NO_CREATE) != null);

        alarmToggleButton.setChecked(alarmExists);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final AlarmManager  alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmToggleButton = (ToggleButton) findViewById(R.id.alarmToggleButton);

        alarmToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String toastMessage = "";
                long trigerTime = SystemClock.elapsedRealtime() ;
                long repeatInterval = 10000;
                if(b){
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            trigerTime,
                            repeatInterval,
                            alarmPendingIntent);
                    toastMessage = "The alarm has been set";
//                    deliverNotification(MainActivity.this);
                }else{
                    toastMessage = "The alarm has been put off";
                    mNotificationManager.cancelAll();
                    alarmManager.cancel(alarmPendingIntent);
                }

                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

  /*  private void deliverNotification(Context context){


    }*/
}
