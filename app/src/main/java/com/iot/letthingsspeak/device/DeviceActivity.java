package com.iot.letthingsspeak.device;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
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
import com.iot.letthingsspeak.R;

import java.util.ArrayList;
import java.util.List;

import static com.iot.letthingsspeak.room.views.RoomAdapter.ROOM_IMAGE;
import static com.iot.letthingsspeak.room.views.RoomAdapter.ROOM_NAME;

public class DeviceActivity extends AppCompatActivity {
    public static final int DEVICE_ACTIVITY_REQUEST_CODE = 202;
    private static final String KEY_INDEX = "device_index";
    List<DeviceDetails> device = new ArrayList<>();
    private RecyclerView deviceRecyclerView;
    private String title;

    public static void launch(Context context, int index) {
        Intent intent = new Intent(context, DeviceActivity.class);
        intent.putExtra(KEY_INDEX, index);
        context.startActivity(intent);
    }

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

        device.add(new DeviceDetails("Bulb", "1"));

        device.add(new DeviceDetails("Fan", "0"));

        DeviceStore.setDeviceDetails(device);

        deviceRecyclerView = findViewById(R.id.device_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        deviceRecyclerView.setLayoutManager(mLayoutManager);
        deviceRecyclerView.addItemDecoration(new DeviceActivity.GridSpacingItemDecoration(3, dpToPx(10), true));
        deviceRecyclerView.setItemAnimator(new DefaultItemAnimator());

        DeviceAdapter deviceAdapter = new DeviceAdapter(DeviceStore.getDeviceDetails());
        deviceRecyclerView.setAdapter(deviceAdapter);

        TextView deviceViewTitle = findViewById(R.id.device_view_title);

        Bundle extras = getIntent().getExtras();
        title = extras.getString(ROOM_NAME);
        Integer roomImage = extras.getInt(ROOM_IMAGE);

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
                startActivityForResult(intent, DEVICE_ACTIVITY_REQUEST_CODE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DEVICE_ACTIVITY_REQUEST_CODE) {
                String message = data.getStringExtra(AddDeviceActivity.ADDED_DEVICE);

                device.add(new DeviceDetails(message, "1"));

                DeviceStore.setDeviceDetails(device);

                DeviceAdapter deviceAdapter = new DeviceAdapter(DeviceStore.getDeviceDetails());
                deviceRecyclerView.setAdapter(deviceAdapter);
            }
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
