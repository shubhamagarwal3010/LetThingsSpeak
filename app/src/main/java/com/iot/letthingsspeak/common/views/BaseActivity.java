package com.iot.letthingsspeak.common.views;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.iot.letthingsspeak.R;
import com.iot.letthingsspeak.common.callbacks.PermissionCallback;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity {

    public String TAG = getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Unbinder unbinder;
    private Dialog progressSend = null;
    private PermissionCallback permissionCallback;
    private int requestcode;

    private static Dialog create(Context context,
                                 String mesg) {
        final Dialog dialog = new Dialog(context, R.style.Base_Theme_AppCompat_Dialog);
        View progressview = LayoutInflater.from(context).inflate(R.layout.loading_view, null);
        TextView tv = (TextView) progressview.findViewById(R.id.loadingTxt);
        tv.setText(mesg);
        dialog.setContentView(progressview);
        return dialog;
    }

    public abstract int getView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        mCompositeDisposable.dispose();
    }

    public void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
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

    public void setUpToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setTitle("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolbar.setContentInsetsAbsolute(0, 0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        TextView txtHeader = findViewById(R.id.tv_heading);
        txtHeader.setText(title);
        // toolbar.setTitle(title);
    }

    public Toolbar getToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        return toolbar;
    }

    public void setUpToolbar(String title, boolean isBackEnable) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setTitle("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolbar.setContentInsetsAbsolute(0, 0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isBackEnable);
            getSupportActionBar().setDisplayShowHomeEnabled(isBackEnable);
        }
        TextView txtHeader = (TextView) findViewById(R.id.tv_heading);
        txtHeader.setText(title);
        //toolbar.setTitle(title);h
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialogProgress(String msg) {
        try {
            if (isShowing()) {
                dismissProgressDialog();
            }
            progressSend = create(this, msg);
            progressSend.setCancelable(false);
            WindowManager.LayoutParams params = progressSend.getWindow()
                    .getAttributes();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                params.y = 200;
                params.x = 0;
            } else {
                params.y = 120;
                params.x = 0;
            }

            progressSend.getWindow().setAttributes(params);
            progressSend.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        try {
            if (null != progressSend && progressSend.isShowing())
                progressSend.cancel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected boolean isShowing() {
        try {
            if (progressSend != null) {
                return progressSend.isShowing();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionCallback != null) {
            if (requestCode == this.requestcode) {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults.length > 0
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        permissionCallback.onPermissionStatus(true);
                    else {
                        permissionCallback.onPermissionStatus(false);
                        return;
                    }
                }
            } else {
                permissionCallback.onPermissionStatus(true);
            }
        }

    }

    public void requestPermission(String permission, int requestcode, PermissionCallback callback) {
        try {
            if (isPemissionAllowed(permission)) {
                callback.onPermissionStatus(true);
            } else {
                this.requestcode = requestcode;
                this.permissionCallback = callback;
                ActivityCompat.requestPermissions(this,
                        new String[]{permission},
                        requestcode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void requestPermission(String[] permission, int requestcode, PermissionCallback callback) {
        try {
            ArrayList<String> list = isPemissionAllowed(permission);
            if (list.size() == 0) {
                callback.onPermissionStatus(true);
            } else {
                String[] permissionList = new String[list.size()];
                for (int i = 0; i < permissionList.length; i++) {
                    permissionList[i] = list.get(i);
                }
                this.requestcode = requestcode;
                this.permissionCallback = callback;
                ActivityCompat.requestPermissions(this,
                        permissionList,
                        requestcode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> isPemissionAllowed(String[] permission) {
        ArrayList<String> list = new ArrayList<>();
        try {
            for (String permssion : permission) {
                boolean isGranted = ContextCompat.checkSelfPermission(getApplicationContext(),
                        permssion) == PackageManager.PERMISSION_GRANTED;
                if (!isGranted) {
                    list.add(permssion);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean isPemissionAllowed(String permission) {
        return ContextCompat.checkSelfPermission(getApplicationContext(),
                permission) == PackageManager.PERMISSION_GRANTED;
    }


}
