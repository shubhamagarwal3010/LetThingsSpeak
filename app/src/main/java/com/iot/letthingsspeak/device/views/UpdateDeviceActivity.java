package com.iot.letthingsspeak.device.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iot.letthingsspeak.LetThingsSpeakApplication;
import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.device.model.DeviceDO;

import java.util.HashMap;

import static com.iot.letthingsspeak.util.Constants.DEVICE_DETAILS;

public class UpdateDeviceActivity extends AppCompatActivity {
    DynamoDBManager dynamoDBManager = LetThingsSpeakApplication.dynamoDBManager;
    DeviceDO deviceDO;
    private EditText deviceName;
    private String deviceNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_device);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.update_device_toolbar);
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

        TextView main_title = findViewById(R.id.update_device_toolbar_title);
        main_title.setText("Update Device Config");
        deviceDO = (DeviceDO) getIntent().getSerializableExtra(DEVICE_DETAILS);
        init();
    }

    private void init() {
        deviceName = findViewById(R.id.update_device_name);
        deviceName.setText(deviceDO.getDeviceName());
        deviceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView label = findViewById(R.id.textViewUpdateDeviceNameMessage);
                deviceName.setBackground(null);
                label.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void updateDevice(View view) {
        deviceName = findViewById(R.id.update_device_name);
        deviceNameInput = deviceName.getText().toString();

        if (deviceNameInput == null || deviceNameInput.isEmpty()) {
            TextView label = findViewById(R.id.textViewUpdateDeviceNameMessage);
            label.setText("Device name cannot be empty");
            deviceName.setBackground(getDrawable(R.drawable.text_border_error));
            return;
        }

        dynamoDBManager.insertDevice(new HashMap<String, Object>() {{
            put("roomId", deviceDO.getRoomId());
            put("deviceId", deviceDO.getDeviceId());
            put("name", deviceNameInput);
            put("state", deviceDO.getState());
        }});
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
