package com.example.womensafety;


import androidx.annotation.NonNull;
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
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editemail, editpassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {


    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.textviewsingupcall).setOnClickListener(this);

        editemail = (EditText) findViewById(R.id.loginemail1);
        editpassword = (EditText) findViewById(R.id.loginpassword);
        progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.textviewsingupcall).setOnClickListener(this);

    }

    private void UserLogin() {//before entering user info into the db first check it is valid or not
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();
        if (email.isEmpty()) {
            editemail.setError("Email cannot be empty");
            editemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editpassword.setError("Password cannot be empty");
            editpassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("Enter valid email id");
            editemail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editpassword.setError("Password length must be greater than or equal to 6");
            editpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {   /*TOAST HTA K NAYI ACTIVITY KO DALNA HAI JO HMARI MAIN ACTIVITY HOGI
                    Intent intent=new Intent(MainActivity.this,newActivity.this);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    */
                    Toast.makeText(getApplicationContext(), "login hogya", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG);
                }


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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textviewsingupcall:
                startActivity(new Intent(this, SignupActivity.class));
                break;

            case R.id.loginButton:
                UserLogin();
                break;

        }

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
