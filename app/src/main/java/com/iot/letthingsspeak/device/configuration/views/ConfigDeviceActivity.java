package com.iot.letthingsspeak.device.configuration.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iot.letthingsspeak.R;

public class ConfigDeviceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_device);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbarConfigDevice);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView main_title = findViewById(R.id.config_device_toolbar_title);
        main_title.setText("Configure Device");
    }


    public void scanWifiNear(View v) {
        Toast.makeText(ConfigDeviceActivity.this, "Unable to scan right now", Toast.LENGTH_SHORT).show();
    }

    public void scanQRCode(View v) {

        Intent scanIntent = new Intent(this, ScannerActivity.class);
        startActivity(scanIntent);
    }


}
