package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int hasSMSPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS );
        List<String> permissions = new ArrayList<String>();

        if( hasSMSPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add( Manifest.permission.SEND_SMS );
        }

        if( !permissions.isEmpty() ) {
            ActivityCompat.requestPermissions(this,permissions.toArray(new String[permissions.size()]), 1 );
        }
        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms =SmsManager.getDefault();
                sms.sendTextMessage("+916264248434", null, "hello", null, null);
            }
        });
    }
}
