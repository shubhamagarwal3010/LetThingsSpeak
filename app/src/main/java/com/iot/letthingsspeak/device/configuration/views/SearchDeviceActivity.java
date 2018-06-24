package com.iot.letthingsspeak.device.configuration.views;

import android.content.pm.ActivityInfo;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.intentfilter.androidpermissions.PermissionManager;
import com.intentfilter.wificonnect.ScanResultsListener;
import com.intentfilter.wificonnect.WifiConnectionManager;
import com.iot.letthingsspeak.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class SearchDeviceActivity extends AppCompatActivity {
    Spinner wifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_device);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar_scan_wifi);
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

        TextView main_title = findViewById(R.id.scan_wifi_toolbar_title);
        main_title.setText("Configure Device");
    }

    public void scanWifi (View view) {
        PermissionManager permissionManager = PermissionManager.getInstance(getApplicationContext());
        permissionManager.checkPermissions(Collections.singletonList(ACCESS_COARSE_LOCATION),
                new PermissionManager.PermissionRequestListener() {
                    @Override
                    public void onPermissionGranted() {
                        scanForAvailableNetworks();
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(getApplicationContext(), "Please provide permission to scan networks", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void scanForAvailableNetworks() {
        WifiConnectionManager wifiConnectionManager = new WifiConnectionManager(getApplicationContext());
        wifiConnectionManager.scanForNetworks(new ScanResultsListener() {
            @Override
            public void onScanResultsAvailable(List<ScanResult> scanResults) {
                showAvailableNetworks(scanResults);
            }
        });
    }

    private void showAvailableNetworks(List<ScanResult> scanResults) {

        wifiList = findViewById(R.id.wifi_list);// Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        for (ScanResult scanResult : scanResults) {
            categories.add(scanResult.SSID);
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);// Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        wifiList.setAdapter(dataAdapter);

    }
    public void connectWifi (View view) {
        String ssid = String.valueOf(wifiList.getSelectedItem());
        String psk = ((EditText)findViewById(R.id.device_password)).getText().toString();
    }
}
