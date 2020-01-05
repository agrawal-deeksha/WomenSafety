package com.example.womensafety;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int hasSMSPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS );
        int hasLocationPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION );
        List<String> permissions = new ArrayList<String>();

        if( hasSMSPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add( Manifest.permission.SEND_SMS );
        }
        if( hasLocationPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add( Manifest.permission.ACCESS_FINE_LOCATION );
        }

        if( !permissions.isEmpty() ) {
            ActivityCompat.requestPermissions(this,permissions.toArray(new String[permissions.size()]), 1 );
        }
        Button send = findViewById(R.id.send);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms =SmsManager.getDefault();
                StringBuffer smsBody = new StringBuffer();
                smsBody.append(Uri.parse(uri));
                sms.sendTextMessage("+916264248434", null, smsBody.toString(), null, null);
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        uri = "http://maps.google.com/maps?saddr=" + location.getLatitude()+","+location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
