package com.iot.letthingsspeak.device.presenter;

import android.arch.lifecycle.Lifecycle;
import android.os.AsyncTask;
import android.util.Log;

import com.iot.letthingsspeak.aws.db.Constants;
import com.iot.letthingsspeak.aws.db.DynamoDBClient;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.aws.db.DynamoDBManagerTaskResult;
import com.iot.letthingsspeak.common.presenter.BasePresenter;
import com.iot.letthingsspeak.common.views.BaseView;
import com.iot.letthingsspeak.device.model.DeviceDO;

import java.util.List;

public class DevicePresenter extends BasePresenter {
    DevicePresenter.DeviceListingView mView;
    DynamoDBManager dynamoDBManager;

    public DevicePresenter(Lifecycle mLifecycle, DevicePresenter.DeviceListingView mView) {
        super(mLifecycle);
        this.mView = mView;
        dynamoDBManager = new DynamoDBManager();
    }

    public void getDeviceForUser(String parameter) {
        new DynamoDBManagerTask().execute(parameter, null);
    }

    public interface DeviceListingView extends BaseView {
        void onDeviceFetchingSuccess(Object data);

        void onDeviceFetchingFail();
    }

    private class DynamoDBManagerTask extends
            AsyncTask<String, Void, DynamoDBManagerTaskResult> {


        protected DynamoDBManagerTaskResult doInBackground(String... parameter) {

            String tableStatus = DynamoDBClient.getTestTableStatus(Constants.DEVICE_TABLE);

            DynamoDBManagerTaskResult result = new DynamoDBManagerTaskResult();
            result.setTableStatus(tableStatus);
            try {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    List<DeviceDO> deviceDOMap = DynamoDBClient.getDeviceForRoom(parameter[0]);
                    result.setReturnValue(deviceDOMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        protected void onPostExecute(DynamoDBManagerTaskResult result) {
            mView.onDeviceFetchingSuccess(result.getReturnValue());
            Log.i("LetThingsSpeakMessages", "Room devices retrieved successfully!");
        }
    }
}
