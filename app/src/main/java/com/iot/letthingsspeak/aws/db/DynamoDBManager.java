package com.iot.letthingsspeak.aws.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.iot.letthingsspeak.aws.db.callbacks.DbDataListener;

import java.util.List;
import java.util.Map;


public class DynamoDBManager {

    Object returnValue;

    public void insertRoom(final Map<String, Object> parameterList) {
        new DynamoDBManagerTask()
                .execute(new Task(null, null, Constants.DynamoDBManagerType.INSERT_ROOM, Constants.ROOM_TABLE, parameterList));
    }

    public void insertUserRoom(final Map<String, Object> parameterList) {
        new DynamoDBManagerTask()
                .execute(new Task(null, null, Constants.DynamoDBManagerType.INSERT_USER_ROOM, Constants.USER_ROOM_TABLE, parameterList));
    }

    public void insertDevice(final Map<String, Object> parameterList) {
        new DynamoDBManagerTask()
                .execute(new Task(null, null, Constants.DynamoDBManagerType.INSERT_DEVICE, Constants.DEVICE_TABLE, parameterList));
    }

    public void insertGateway(final Map<String, Object> parameterList) {
        new DynamoDBManagerTask()
                .execute(new Task(null, null, Constants.DynamoDBManagerType.INSERT_GATEWAY, Constants.GATEWAY_TABLE, parameterList));
    }

    public void getRoomsForUser(Context listener) {
        new DynamoDBManagerTask().execute(new Task((DbDataListener) listener, listener, Constants.DynamoDBManagerType.GET_ROOMS_FOR_USER, Constants.ROOM_TABLE, null));
    }


    private class DynamoDBManagerTask extends
            AsyncTask<Task, Void, DynamoDBManagerTaskResult> {

        DbDataListener listener;

        protected DynamoDBManagerTaskResult doInBackground(Task... types) {

            listener = types[0].getListener();
            String tableStatus = DynamoDBClient.getTestTableStatus(types[0].getTableName());

            DynamoDBManagerTaskResult result = new DynamoDBManagerTaskResult();
            result.setTableStatus(tableStatus);
            result.setTaskType(types[0].getDynamoDBManagerType());
            result.setContext(types[0].getContext());

            if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_ROOM) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    DynamoDBClient.insertRoom(types[0].getParameterList());
                }
            } else if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_USER_ROOM) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    DynamoDBClient.insertUserRoom(types[0].getParameterList());
                }
            } else if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_DEVICE) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    DynamoDBClient.insertDevice(types[0].getParameterList());
                }
            } else if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_GATEWAY) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    DynamoDBClient.insertGateway(types[0].getParameterList());
                }
            } else if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.GET_ROOMS_FOR_USER) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    List<RoomDO> roomDOMap = DynamoDBClient.getRoomsForUser();
                    result.setReturnValue(roomDOMap);
                }
            }
            return result;
        }

        protected void onPostExecute(DynamoDBManagerTaskResult result) {
            if (result.getTableStatus(Constants.ROOM_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_ROOM) {
                Log.i("LetThingsSpeakMessages", "Room details inserted successfully!");
            } else if (result.getTableStatus(Constants.USER_ROOM_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_USER_ROOM) {
                Log.i("LetThingsSpeakMessages", "Room inserted successfully!");
            } else if (result.getTableStatus(Constants.DEVICE_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_DEVICE) {
                Log.i("LetThingsSpeakMessages", "Device inserted successfully!");
            } else if (result.getTableStatus(Constants.GATEWAY_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_GATEWAY) {
                Log.i("LetThingsSpeakMessages", "Gateway inserted successfully!");
            } else if (result.getTableStatus(Constants.USER_ROOM_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.GET_ROOMS_FOR_USER) {
                returnValue = result.getReturnValue();
                ((DbDataListener) result.getContext()).publishResultsOnSuccess(Constants.DynamoDBManagerType.GET_ROOMS_FOR_USER, returnValue);
                Log.i("LetThingsSpeakMessages", "User Room retrieved successfully!");
            }
        }
    }
}
