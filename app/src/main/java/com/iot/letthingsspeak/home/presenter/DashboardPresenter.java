package com.iot.letthingsspeak.home.presenter;

import android.arch.lifecycle.Lifecycle;
import android.os.AsyncTask;
import android.util.Log;

import com.iot.letthingsspeak.aws.AppHelper;
import com.iot.letthingsspeak.aws.db.Constants;
import com.iot.letthingsspeak.aws.db.DynamoDBClient;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.aws.db.DynamoDBManagerTaskResult;
import com.iot.letthingsspeak.aws.db.Task;
import com.iot.letthingsspeak.common.presenter.BasePresenter;
import com.iot.letthingsspeak.common.views.BaseView;
import com.iot.letthingsspeak.device.model.DeviceDO;
import com.iot.letthingsspeak.room.model.RoomDO;

import java.util.List;
import java.util.Map;

public class DashboardPresenter extends BasePresenter {

    DynamoDBManager dynamoDBManager;
    DashboardPresenter.RoomListingView mView;

    public DashboardPresenter(Lifecycle mLifecycle, DashboardPresenter.RoomListingView mView) {
        super(mLifecycle);
        this.mView = mView;
        dynamoDBManager = new DynamoDBManager();
    }

    public void getRoomsForUser()
    {
        new DynamoDBManagerTask().execute(null,null);
    }


    public interface RoomListingView extends BaseView {
        void onRoomsFetchingSuccess(Object data);
        void onRoomsFetchingFail();
    }




    private  class DynamoDBManagerTask extends
            AsyncTask<Void, Void, DynamoDBManagerTaskResult> {


        protected DynamoDBManagerTaskResult doInBackground(Void... types) {

            String tableStatus = DynamoDBClient.getTestTableStatus(Constants.ROOM_TABLE);

            DynamoDBManagerTaskResult result = new DynamoDBManagerTaskResult();
            result.setTableStatus(tableStatus);
            try
            {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    List<RoomDO> roomDOMap = DynamoDBClient.getRoomsForUser();
                    result.setReturnValue(roomDOMap);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return result;
        }

        protected void onPostExecute(DynamoDBManagerTaskResult result) {
            if (result.getTableStatus(Constants.USER_ROOM_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.GET_ROOMS_FOR_USER) {
                mView.onRoomsFetchingSuccess(result.getReturnValue());
                Log.i("LetThingsSpeakMessages", "User Room retrieved successfully!");
            }
        }
    }
}
