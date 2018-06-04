package com.iot.letthingsspeak.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.iot.letthingsspeak.R;


public class AddDevice extends AppCompatActivity {

    public static final String ADDED_DEVICE = "NEW_DEVICE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
    }
    public void addDeviceToList(View view) {
        TextView addTaskView = (TextView) findViewById(R.id.device);
        Intent intent = new Intent();
        intent.putExtra(this.ADDED_DEVICE, addTaskView.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
