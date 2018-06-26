package com.iot.letthingsspeak.device.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iot.letthingsspeak.LetThingsSpeakApplication;
import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.common.util.Util;
import com.iot.letthingsspeak.common.util.views.GridSpacingItemDecoration;
import com.iot.letthingsspeak.common.views.BaseActivity;
import com.iot.letthingsspeak.device.model.DeviceDO;
import com.iot.letthingsspeak.device.presenter.DevicePresenter;
import com.iot.letthingsspeak.room.model.RoomDO;

import java.util.List;
import java.util.Map;

import static com.iot.letthingsspeak.room.views.RoomAdapter.ROOM_DETAILS;

public class DeviceActivity extends BaseActivity implements DevicePresenter.DeviceListingView {
    public static final int DEVICE_ACTIVITY_REQUEST_CODE = 202;
    public static final String ROOM_ID_DEVICE = "ROOM_ID_DEVICE";
    DynamoDBManager dynamoDBManager = LetThingsSpeakApplication.dynamoDBManager;
    DevicePresenter mPresenter;
    private RecyclerView deviceRecyclerView;
    private String title;
    private String roomId;
    private RoomDO roomDO;
    private Map<String, Object> roomData;

    @Override
    public int getView() {
        return R.layout.activity_room_devices;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        roomDO = (RoomDO) getIntent().getSerializableExtra(ROOM_DETAILS);
        title = roomDO.getName();
        Integer roomImage = roomDO.getImageId().intValue();
        roomId = roomDO.getRoomId();

        mPresenter = new DevicePresenter(getLifecycle(), this);
        setUpToolbar(title, true);
        initCollapsingToolbar();

        deviceRecyclerView = findViewById(R.id.device_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        deviceRecyclerView.setLayoutManager(mLayoutManager);
        deviceRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, Util.dpToPx(DeviceActivity.this, 10), true));
        deviceRecyclerView.setItemAnimator(new DefaultItemAnimator());



        mPresenter.getDeviceForUser(roomId);

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
        mPresenter.getDeviceForUser(roomId);
    }


    @Override
    public void onDeviceFetchingSuccess(Object data) {
        DeviceAdapter deviceAdapter;
        deviceAdapter = new DeviceAdapter(this, (List<DeviceDO>) data, roomDO);
        deviceRecyclerView.setAdapter(deviceAdapter);
    }

    @Override
    public void onDeviceFetchingFail() {

    }

    @Override
    public void onShowProgress(String msg) {

    }

    @Override
    public void onDismissProgress() {

    }
}
