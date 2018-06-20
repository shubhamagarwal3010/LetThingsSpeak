package com.iot.letthingsspeak.room;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.aws.LetThingsSpeakLaunch;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.room.callbacks.ClickItemListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class AddRoom extends AppCompatActivity implements View.OnClickListener, ClickItemListener {

    public static final String ADDED_ROOM = "NEW_ROOM";
    DynamoDBManager dynamoDBManager = LetThingsSpeakLaunch.dynamoDBManager;
    ArrayList<Integer> imageRepo = new ArrayList<Integer>(Arrays.asList(R.mipmap.room_bed, R.mipmap.room_bed2,
            R.mipmap.room_bed3, R.mipmap.room_exterior, R.mipmap.room_kitchen,
            R.mipmap.room_kitchen2, R.mipmap.room_living, R.mipmap.room_living2,
            R.mipmap.room_living3, R.mipmap.room_workplace));
    Integer roomImageId;
    private EditText roomName;
    private EditText roomTag;
    private RecyclerView mHorizontalRecyclerView;
    private AddRoomImageAdapter horizontalAdapter;
    private LinearLayoutManager horizontalLayoutManager;

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

        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);
        horizontalAdapter = new AddRoomImageAdapter(imageRepo, getApplication(), this);

        horizontalLayoutManager = new LinearLayoutManager(AddRoom.this, LinearLayoutManager.HORIZONTAL, false);
        mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalRecyclerView.setAdapter(horizontalAdapter);

    }

    public void addRoomToList(View view) {
        roomName = findViewById(R.id.room_name);
        roomTag = findViewById(R.id.room_tag);

        dynamoDBManager.insertRoom(new HashMap<String, Object>() {{
            put("roomId", "1212");
            put("roomName", roomName.getText().toString());
            put("imageId", (double) roomImageId);
            put("tag", roomTag.getText().toString());
        }});
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

    @Override
    public void publishResultsOnSuccess(Integer value) {
        roomImageId = value;
    }
}
