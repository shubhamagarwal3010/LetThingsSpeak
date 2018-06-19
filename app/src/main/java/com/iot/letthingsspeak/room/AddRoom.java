package com.iot.letthingsspeak.room;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iot.letthingsspeak.R;


public class AddRoom extends AppCompatActivity implements View.OnClickListener {

    private EditText roomName;
    private EditText codeInput;
    public static final String ADDED_ROOM = "NEW_ROOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.add_room_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView main_title = findViewById(R.id.add_room_toolbar_title);
        main_title.setText("Room Config");

        RelativeLayout background_activity_main = (RelativeLayout) findViewById(R.id.background_activity_add_room);
        background_activity_main.setOnClickListener(this);

    }

    public void addRoomToList(View view) {
        roomName = findViewById(R.id.room_name);
        Intent intent = new Intent();
        intent.putExtra(ADDED_ROOM, roomName.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.background_activity_add_room) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
