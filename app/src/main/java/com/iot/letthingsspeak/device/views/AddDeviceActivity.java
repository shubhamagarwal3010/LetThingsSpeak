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

import java.util.HashMap;

import static com.iot.letthingsspeak.device.views.DeviceActivity.ROOM_ID_DEVICE;
import static com.iot.letthingsspeak.util.Util.generateRandomChars;


public class AddDeviceActivity extends AppCompatActivity {

    public static final String ADDED_DEVICE = "NEW_DEVICE";
    DynamoDBManager dynamoDBManager = LetThingsSpeakApplication.dynamoDBManager;
    private EditText deviceName;
    private String deviceNameInput;
    private String roomId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.add_device_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView main_title = findViewById(R.id.add_device_toolbar_title);
        main_title.setText("Device Config");
        Bundle extras = getIntent().getExtras();
        roomId = extras.getString(ROOM_ID_DEVICE);

        init();
    }

    private void init() {
        deviceName = findViewById(R.id.device_name);
        deviceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView label = findViewById(R.id.textViewDeviceNameMessage);
                deviceName.setBackground(null);
                label.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void addDeviceToList(View view) {
        deviceName = findViewById(R.id.device_name);
        deviceNameInput = deviceName.getText().toString();

        if (deviceNameInput == null || deviceNameInput.isEmpty()) {
            TextView label = findViewById(R.id.textViewDeviceNameMessage);
            label.setText("Device name cannot be empty");
            deviceName.setBackground(getDrawable(R.drawable.text_border_error));
            return;
        }
        final String deviceId = generateRandomChars();
        dynamoDBManager.insertDevice(new HashMap<String, Object>() {{
            put("roomId", roomId);
            put("deviceId", deviceId);
            put("name", deviceNameInput);
            put("state", false);
        }});
        Intent intent = new Intent();
        intent.putExtra(ADDED_DEVICE, deviceName.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
