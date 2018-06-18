package com.iot.letthingsspeak.aws.db;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.iot.letthingsspeak.aws.LetThingsSpeakLaunch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DynamoDBManager {

    AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonDynamoDBClient;
    Object returnValue;

    public void insertRoom(final Double roomId, final Boolean isAdmin, final String roomName, final String imageId, final String tag) {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.INSERT_ROOM, Constants.ROOM_TABLE, new HashMap<String, Object>() {{
                    put("room_id", roomId);
                    put("isAdmin", isAdmin);
                    put("room_name", roomName);
                    put("image_id", imageId);
                    put("tag", tag);
                }}));
    }
    public void insertDevice(final Double deviceId, final Boolean currentState, final List<String> delegatedIds, final Double gatewayId,
                             final Double gatewayPin, final String imageId, final String name, final Double roomId, final String tag) {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.INSERT_DEVICE, Constants.DEVICE_TABLE, new HashMap<String, Object>() {{
                    put("deviceId", deviceId);
                    put("currentState", currentState);
                    put("delegatedIds", delegatedIds);
                    put("gatewayId", gatewayId);
                    put("gatewayPin", gatewayPin);
                    put("imageId", imageId);
                    put("name", name);
                    put("roomId", roomId);
                    put("tag", tag);
                }}));
    }

    public Object getRoomsForUser() {
        new DynamoDBManagerTask().execute(new Task(null, Constants.DynamoDBManagerType.GET_ROOMS_FOR_USER, Constants.ROOM_TABLE, null));
        return returnValue;
    }


    public class DynamoDBManagerTask extends
            AsyncTask<Task, Void, DynamoDBManagerTaskResult> {

        protected DynamoDBManagerTaskResult doInBackground(Task... types) {

            String tableStatus = DynamoDBClient.getTestTableStatus(types[0].getTableName());

            DynamoDBManagerTaskResult result = new DynamoDBManagerTaskResult();
            result.setTableStatus(tableStatus);
            result.setTaskType(types[0].getDynamoDBManagerType());

            if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_ROOM) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    DynamoDBClient.insertRoom(types[0].getParameterList());
                }
            } else if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_DEVICE) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    DynamoDBClient.insertDevice(types[0].getParameterList());
                }
            } else if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.GET_ROOMS_FOR_USER) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    Map<Double, RoomDO> roomDOMap = DynamoDBClient.getRoomsForUser();
                    result.setReturnValue(roomDOMap);
                }
            }
            return result;
        }

        protected void onPostExecute(DynamoDBManagerTaskResult result) {
            returnValue = result.getReturnValue();

            if (result.getTableStatus(Constants.ROOM_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_ROOM) {
                Log.i("LetThingsSpeakMessages", "Room Details inserted successfully!");
            } else if (result.getTableStatus(Constants.DEVICE_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_DEVICE) {
                Log.i("LetThingsSpeakMessages", "Device Details inserted successfully!");
            }
        }
    }
}
