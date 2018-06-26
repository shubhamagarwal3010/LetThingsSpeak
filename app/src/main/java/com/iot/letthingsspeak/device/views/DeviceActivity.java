package com.iot.letthingsspeak.device.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iot.letthingsspeak.LetThingsSpeakApplication;
import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.aws.db.Constants;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.aws.db.callbacks.DbDataListener;
import com.iot.letthingsspeak.common.util.views.GridSpacingItemDecoration;
import com.iot.letthingsspeak.device.model.DeviceDO;
import com.iot.letthingsspeak.room.model.RoomDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iot.letthingsspeak.room.views.RoomAdapter.ROOM_DETAILS;

public class DeviceActivity extends AppCompatActivity implements DbDataListener {
    public static final int DEVICE_ACTIVITY_REQUEST_CODE = 202;
    public static final String ROOM_ID_DEVICE = "ROOM_ID_DEVICE";
    DynamoDBManager dynamoDBManager = LetThingsSpeakApplication.dynamoDBManager;
    private RecyclerView deviceRecyclerView;
    private String title;
    private String roomId;
    private RoomDO roomDO;
    private Map<String, Object> roomData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_devices);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.device_toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initCollapsingToolbar();

        deviceRecyclerView = findViewById(R.id.device_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        deviceRecyclerView.setLayoutManager(mLayoutManager);
        deviceRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        deviceRecyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView deviceViewTitle = findViewById(R.id.device_view_title);

        roomDO = (RoomDO) getIntent().getSerializableExtra(ROOM_DETAILS);
        title = roomDO.getName();
        Integer roomImage = roomDO.getImageId().intValue();
        roomId = roomDO.getRoomId();
        roomData = new HashMap<String, Object>() {{
            put("roomId", roomId);
        }};

        dynamoDBManager.getDevicesForRoom(this, roomData);

        deviceViewTitle.setText(title);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.show();
        try {
            Glide.with(this).load(roomImage).into((ImageView) findViewById(R.id.device_backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeviceActivity.this, AddDeviceActivity.class);
                intent.putExtra(ROOM_ID_DEVICE, roomId);
                startActivityForResult(intent, DEVICE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dynamoDBManager.getDevicesForRoom(this, roomData);
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.device_collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.deviceAppbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void publishResultsOnSuccess(Constants.DynamoDBManagerType type, Object data) {
        if (type == Constants.DynamoDBManagerType.GET_DEVICES_FOR_ROOM) {

            DeviceAdapter deviceAdapter;
            deviceAdapter = new DeviceAdapter(this, (List<DeviceDO>) data, roomDO);
            deviceRecyclerView.setAdapter(deviceAdapter);
        }
    }


}
