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

package com.iot.letthingsspeak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.bumptech.glide.Glide;
import com.iot.letthingsspeak.aws.AppHelper;
import com.iot.letthingsspeak.aws.db.Constants;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.aws.db.callbacks.DbDataListener;
import com.iot.letthingsspeak.common.views.BaseActivity;
import com.iot.letthingsspeak.device.configuration.views.ConfigDeviceActivity;
import com.iot.letthingsspeak.device.views.DeviceList;
import com.iot.letthingsspeak.identity.views.ChangePasswordActivity;
import com.iot.letthingsspeak.identity.views.UserProfileActivity;
import com.iot.letthingsspeak.room.model.RoomDO;
import com.iot.letthingsspeak.room.views.AddRoomActivity;
import com.iot.letthingsspeak.room.views.RoomAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserActivity extends BaseActivity implements DbDataListener {
    public static final int ACTIVITY_REQUEST_CODE = 201;
    // To track changes to user details
    List<RoomDO> room = new ArrayList<>();
    DynamoDBManager dynamoDBManager = LetThingsSpeakApplication.dynamoDBManager;
    private RecyclerView roomTypeRecyclerView;
    private NavigationView nDrawer;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private AlertDialog userDialog;
    private ProgressDialog waitDialog;
    // Cognito user objects
    private CognitoUser user;
    // User details
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Install a custom UncaughtExceptionHandler so we can debug crashes
        CrashHandler.installHandler(this);

        // Set toolbar for this screen
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        // Set navigation drawer for this screen
        mDrawer = findViewById(R.id.user_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        nDrawer = findViewById(R.id.nav_view);
        setNavDrawer();
        init();
        View navigationHeader = nDrawer.getHeaderView(0);
        TextView navHeaderSubTitle = navigationHeader.findViewById(R.id.textViewNavUserSub);
        navHeaderSubTitle.setText(username);

        roomTypeRecyclerView = findViewById(R.id.activity_main_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        roomTypeRecyclerView.setLayoutManager(mLayoutManager);
        roomTypeRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        roomTypeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        dynamoDBManager.getRoomsForUser(this);

        try {
            Glide.with(this).load(R.drawable.smart_home).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, AddRoomActivity.class);
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        dynamoDBManager.getRoomsForUser(this);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
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
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_user_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        exit();
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
        user.signOut();
        exit();
    }

    // Initialize this activity
    private void init() {
        // Get the user name
        Bundle extras = getIntent().getExtras();
        username = AppHelper.getCurrUser();
        user = AppHelper.getPool().getUser(username);
    }

    private void exit() {
        Intent intent = new Intent();
        if (username == null)
            username = "";
        intent.putExtra("name", username);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void insertUsers(View v) {
        dynamoDBManager.insertRoom(new HashMap<String, Object>() {{
            put("roomId", "1211");
            put("roomName", "BedRoom");
            put("imageId", (double) 2131492873);
        }});

        dynamoDBManager.insertUserRoom(new HashMap<String, Object>() {{
            put("roomId", "1211");
            put("isAdmin", true);
        }});

//        dynamoDBManager.insertDevice(new HashMap<String, Object>() {{
//            put("deviceId", (double) 1);
//            put("currentState", false);
//            put("delegatedIds", null);
//            put("gatewayId", (double) 101);
//            put("gatewayPin", (double) 1);
//            put("imageId", "bulbImage");
//            put("name", "Bulb");
//            put("roomId", (double) 1211);
//            put("tag", "Main light");
//        }});

        dynamoDBManager.insertGateway(new HashMap<String, Object>() {{
            put("gatewayId", (double) 1012);
            put("name", "bed Switch Hub");
            put("pinCount", (double) 3);
        }});
    }

    @Override
    public void publishResultsOnSuccess(Constants.DynamoDBManagerType type, Object data) {
        if (type == Constants.DynamoDBManagerType.GET_ROOMS_FOR_USER) {

            RoomAdapter roomAdapter;
            roomAdapter = new RoomAdapter(this, (List<RoomDO>) data);
            roomTypeRecyclerView.setAdapter(roomAdapter);
            //Log.i("room name", ((Map<String, RoomDO>) data).get("1211").getName());
            //Log.i("room name", ((Map<String, RoomDO>) data).get("1212").getName());
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
