package com.iot.letthingsspeak.room.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iot.letthingsspeak.LetThingsSpeakApplication;
import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.room.callbacks.ClickItemListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.iot.letthingsspeak.common.util.Util.generateRandomChars;


public class AddRoomActivity extends AppCompatActivity implements ClickItemListener {

    public static final String ROOM_NAME = "ROOM_NAME";
    public static final String ROOM_ICON = "ROOM_ICON";

    DynamoDBManager dynamoDBManager = LetThingsSpeakApplication.dynamoDBManager;
    ArrayList<Integer> imageRepo = new ArrayList<Integer>(Arrays.asList(R.mipmap.room_bed, R.mipmap.room_bed2,
            R.mipmap.room_bed3, R.mipmap.room_exterior, R.mipmap.room_kitchen,
            R.mipmap.room_kitchen2, R.mipmap.room_living, R.mipmap.room_living2,
            R.mipmap.room_living3, R.mipmap.room_workplace));
    private Integer roomImageId = 0;
    private EditText roomName;
    private RecyclerView mHorizontalRecyclerView;
    private AddRoomImageAdapter horizontalAdapter;
    private LinearLayoutManager horizontalLayoutManager;
    private String roomNameInput;

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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView main_title = findViewById(R.id.add_room_toolbar_title);
        main_title.setText("Room Config");

        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);
        horizontalAdapter = new AddRoomImageAdapter(imageRepo, getApplication(), this);

        horizontalLayoutManager = new LinearLayoutManager(AddRoomActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalRecyclerView.setAdapter(horizontalAdapter);
        init();
    }

    private void init() {
        roomName = findViewById(R.id.room_name);
        roomName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView label = findViewById(R.id.textViewRoomNameMessage);
                roomName.setBackground(null);
                label.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void addRoomToList(View view) {
        roomName = findViewById(R.id.room_name);
        roomNameInput = roomName.getText().toString();
        if (roomNameInput == null || roomNameInput.isEmpty()) {
            TextView label = findViewById(R.id.textViewRoomNameMessage);
            label.setText("Room name cannot be empty");
            roomName.setBackground(getDrawable(R.drawable.text_border_error));
            return;
        }
        final String roomId = generateRandomChars();
        dynamoDBManager.insertRoom(new HashMap<String, Object>() {{
            put("roomId", roomId);
            put("roomName", roomName.getText().toString());
            put("imageId", (double) roomImageId);
            put("state", false);
        }});

        dynamoDBManager.insertUserRoom(new HashMap<String, Object>() {{
            put("roomId", roomId);
            put("isAdmin", true);
        }});

        Intent intent = new Intent();
        intent.putExtra(ROOM_NAME, roomName.getText().toString());
        intent.putExtra(ROOM_ICON, roomImageId);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void publishResultsOnSuccess(Integer value) {
        roomImageId = value;
    }

}
