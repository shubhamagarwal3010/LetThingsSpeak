package com.iot.letthingsspeak.device;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.iot.letthingsspeak.R;

import java.util.ArrayList;
import java.util.List;

import static com.iot.letthingsspeak.room.RoomAdapter.TITLE_KEY;

public class DeviceActivity extends AppCompatActivity {
    public static final int DEVICE_ACTIVITY_REQUEST_CODE = 202;
    private static final String KEY_INDEX = "device_index";
    List<DeviceDetails> device = new ArrayList<>();
    private RecyclerView deviceRecyclerView;

    public static void launch(Context context, int index) {
        Intent intent = new Intent(context, DeviceActivity.class);
        intent.putExtra(KEY_INDEX, index);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.device_toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        deviceRecyclerView = findViewById(R.id.device_recyclerview);
        deviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        device.add(new DeviceDetails("Bulb", "1"));

        device.add(new DeviceDetails("Fan", "0"));

        DeviceStore.setDeviceDetails(device);
        DeviceAdapter deviceAdapter = new DeviceAdapter(DeviceStore.getDeviceDetails());
        deviceRecyclerView.setAdapter(deviceAdapter);


        Bundle extras = getIntent().getExtras();
        String title = extras.getString(TITLE_KEY);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.show();

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeviceActivity.this, AddDevice.class);
                startActivityForResult(intent, DEVICE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DEVICE_ACTIVITY_REQUEST_CODE) {
                String message = data.getStringExtra(AddDevice.ADDED_DEVICE);

                device.add(new DeviceDetails(message, "1"));

                DeviceStore.setDeviceDetails(device);

                DeviceAdapter deviceAdapter = new DeviceAdapter(DeviceStore.getDeviceDetails());
                deviceRecyclerView.setAdapter(deviceAdapter);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
