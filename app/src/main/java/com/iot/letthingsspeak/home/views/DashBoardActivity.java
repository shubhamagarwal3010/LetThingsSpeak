/*
 * Copyright 2013-2017 Amazon.com,
 * Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the
 * License. A copy of the License is located at
 *
 *      http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, express or implied. See the License
 * for the specific language governing permissions and
 * limitations under the License.
 */

package com.iot.letthingsspeak.home.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iot.letthingsspeak.AboutAppActivity;
import com.iot.letthingsspeak.CrashHandler;
import com.iot.letthingsspeak.LetThingsSpeakApplication;
import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.aws.AppHelper;
import com.iot.letthingsspeak.aws.db.Constants;
import com.iot.letthingsspeak.common.util.Util;
import com.iot.letthingsspeak.common.util.views.GridSpacingItemDecoration;
import com.iot.letthingsspeak.common.views.BaseActivity;
import com.iot.letthingsspeak.device.model.DeviceDO;
import com.iot.letthingsspeak.device.views.ConfigDeviceActivity;
import com.iot.letthingsspeak.device.views.DeviceList;
import com.iot.letthingsspeak.home.presenter.DashboardPresenter;
import com.iot.letthingsspeak.identity.views.ChangePasswordActivity;
import com.iot.letthingsspeak.identity.views.UserProfileActivity;
import com.iot.letthingsspeak.room.model.RoomDO;
import com.iot.letthingsspeak.room.views.AddRoomActivity;
import com.iot.letthingsspeak.room.views.RoomAdapter;

import java.util.HashMap;
import java.util.List;

public class DashBoardActivity extends BaseActivity implements DashboardPresenter.RoomListingView {
    public static final int ACTIVITY_REQUEST_CODE = 201;
    // To track changes to user details
    private RecyclerView roomTypeRecyclerView;
    private NavigationView nDrawer;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    DashboardPresenter mPresenter;

    @Override
    public int getView() {
        return R.layout.activity_user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Install a custom UncaughtExceptionHandler so we can debug crashes
        CrashHandler.installHandler(this);

        mPresenter=new DashboardPresenter(getLifecycle(), this);
        setUpToolbar("");
        initCollapsingToolbar();

        // Set navigation drawer for this screen
        mDrawer = findViewById(R.id.user_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, getToolBar(), R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        nDrawer = findViewById(R.id.nav_view);
        setNavDrawer();
        View navigationHeader = nDrawer.getHeaderView(0);
        TextView navHeaderSubTitle = navigationHeader.findViewById(R.id.textViewNavUserSub);
        navHeaderSubTitle.setText(AppHelper.getCurrUser());

        roomTypeRecyclerView = findViewById(R.id.activity_main_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        roomTypeRecyclerView.setLayoutManager(mLayoutManager);
        roomTypeRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(DashBoardActivity.this, 10), true));
        roomTypeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mPresenter.getRoomsForUser();

        try {
            Glide.with(this).load(R.drawable.smart_home).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoardActivity.this, AddRoomActivity.class);
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getRoomsForUser();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_user_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        signOut();
    }


    // Handle when the a navigation item is selected
    private void setNavDrawer() {
        nDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                performAction(item);
                return true;
            }
        });
    }

    // Perform the action for the selected navigation item
    private void performAction(MenuItem item) {
        // Close the navigation drawer
        mDrawer.closeDrawers();

        // Find which item was selected
        switch (item.getItemId()) {
            case R.id.nav_user_profile:
                userProfile();
                break;
            case R.id.nav_config_device:
                configDevice();
                break;
            case R.id.nav_device_list:
                showDeviceList();
                break;
            case R.id.nav_user_change_password:
                // Change password
                changePassword();
                break;
            case R.id.nav_user_sign_out:
                // Sign out from this account
                signOut();
                break;
            case R.id.nav_user_about:
                // For the inquisitive
                Intent aboutAppActivity = new Intent(this, AboutAppActivity.class);
                startActivity(aboutAppActivity);
                break;
        }
    }

    // Change user password
    private void changePassword() {
        Intent changePssActivity = new Intent(this, ChangePasswordActivity.class);
        startActivity(changePssActivity);
    }

    // Show User Profile
    private void userProfile() {
        Intent userProfileActivity = new Intent(this, UserProfileActivity.class);
        startActivity(userProfileActivity);
    }

    // Configure Device
    private void configDevice() {
        Intent configDeviceActivity = new Intent(this, ConfigDeviceActivity.class);
        startActivity(configDeviceActivity);
    }

    // Configure Device
    private void showDeviceList() {
        Intent configDeviceActivity = new Intent(this, DeviceList.class);
        startActivity(configDeviceActivity);
    }

    // Sign out user
    private void signOut() {
        AppHelper.getPool().getUser(AppHelper.getCurrUser()).signOut();

    }


    @Override
    public void onRoomsFetchingSuccess(Object data) {


            RoomAdapter roomAdapter;
            roomAdapter = new RoomAdapter(this, (List<RoomDO>) data);
            roomTypeRecyclerView.setAdapter(roomAdapter);

        /*if (type == Constants.DynamoDBManagerType.GET_DEVICES_FOR_ROOM) {

            List<DeviceDO> deviceDOS = (List<DeviceDO>) data;
            for (final DeviceDO deviceDO : deviceDOS) {
                if (deviceDO.getState()) {
                    dynamoDBManager.insertDevice(new HashMap<String, Object>() {{
                        put("roomId", deviceDO.getRoomId());
                        put("deviceId", deviceDO.getDeviceId());
                        put("name", deviceDO.getDeviceName());
                        put("state", false);
                    }});
                }
            }
        }*/
    }

    @Override
    public void onRoomsFetchingFail() {

    }

    @Override
    public void onShowProgress(String msg) {

    }

    @Override
    public void onDismissProgress() {

    }

}
