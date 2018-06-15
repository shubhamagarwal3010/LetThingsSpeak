package com.iot.letthingsspeak.configdevice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.iot.letthingsspeak.R;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ConfigDevice extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_device);

        Toolbar toolbar = findViewById(R.id.toolbarConfigDevice);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
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
        Toast.makeText(ConfigDevice.this, "Unable to scan right now", Toast.LENGTH_SHORT).show();
    }

    public void scanQRCode(View v) {

        Intent scanIntent = new Intent(this, ScannerActivity.class);
        startActivity(scanIntent);
    }


}
