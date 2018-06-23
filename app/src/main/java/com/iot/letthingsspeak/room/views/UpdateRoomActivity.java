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
import com.iot.letthingsspeak.room.model.RoomDO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.iot.letthingsspeak.room.views.RoomAdapter.ROOM_DETAILS;


public class UpdateRoomActivity extends AppCompatActivity implements ClickItemListener {

    public static final String ROOM_NAME = "ROOM_NAME";
    public static final String ROOM_ICON = "ROOM_ICON";

    DynamoDBManager dynamoDBManager = LetThingsSpeakApplication.dynamoDBManager;
    ArrayList<Integer> imageRepo = new ArrayList<Integer>(Arrays.asList(R.mipmap.room_bed, R.mipmap.room_bed2,
            R.mipmap.room_bed3, R.mipmap.room_exterior, R.mipmap.room_kitchen,
            R.mipmap.room_kitchen2, R.mipmap.room_living, R.mipmap.room_living2,
            R.mipmap.room_living3, R.mipmap.room_workplace));
    RoomDO roomDO;
    private Integer roomImageId = 0;
    private EditText roomName;
    private RecyclerView mHorizontalRecyclerView;
    private AddRoomImageAdapter horizontalAdapter;
    private LinearLayoutManager horizontalLayoutManager;
    private String roomNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.update_room_toolbar);
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

        TextView main_title = findViewById(R.id.update_room_toolbar_title);
        main_title.setText("Update Room Config");

        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.updateRoomImageRecyclerView);
        horizontalAdapter = new AddRoomImageAdapter(imageRepo, getApplication(), this);

        horizontalLayoutManager = new LinearLayoutManager(UpdateRoomActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalRecyclerView.setAdapter(horizontalAdapter);

        roomDO = (RoomDO) getIntent().getSerializableExtra(ROOM_DETAILS);
        init();
    }

    private void init() {
        roomName = findViewById(R.id.update_room_name);
        roomName.setText(roomDO.getName());
        roomName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView label = findViewById(R.id.textViewUpdateRoomNameMessage);
                roomName.setBackground(null);
                label.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void updateRoom(View view) {
        roomName = findViewById(R.id.update_room_name);
        roomNameInput = roomName.getText().toString();
        if (roomNameInput == null || roomNameInput.isEmpty()) {
            TextView label = findViewById(R.id.textViewUpdateRoomNameMessage);
            label.setText("Room name cannot be empty");
            roomName.setBackground(getDrawable(R.drawable.text_border_error));
            return;
        }
        if (roomImageId == 0) {
            roomImageId = roomDO.getImageId().intValue();
        }

        dynamoDBManager.insertRoom(new HashMap<String, Object>() {{
            put("roomId", roomDO.getRoomId());
            put("roomName", roomName.getText().toString());
            put("imageId", (double) roomImageId);
            put("state", roomDO.getState());
        }});

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void publishResultsOnSuccess(Integer value) {
        roomImageId = value;
    }

}