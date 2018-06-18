package com.iot.letthingsspeak.aws.db;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.iot.letthingsspeak.aws.LetThingsSpeakLaunch;

import java.util.HashMap;
import java.util.Map;


public class DynamoDBManager {

    AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonDynamoDBClient;
    Object returnValue;


    public void createTable() {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.CREATE_TABLE, Constants.TEST_TABLE_NAME, new HashMap<String, Object>()));
    }

    public void insertUsers() {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.INSERT_USER, Constants.TEST_TABLE_NAME, new HashMap<String, Object>()));
    }

    public void insertRoomDetails(final Double roomId, final Boolean isAdmin, final String roomName, final String imageId, final String tag) {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.INSERT_ROOM_DETAILS, Constants.ROOM_TABLE, new HashMap<String, Object>() {{
                    put("room_id", roomId);
                    put("isAdmin", isAdmin);
                    put("room_name", roomName);
                    put("image_id", imageId);
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

            if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_USER) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    DynamoDBClient.insertUsers();
                }
            } else if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_ROOM_DETAILS) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    DynamoDBClient.insertRoomDetails(types[0].getParameterList());
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

            if (!result.getTableStatus(Constants.TEST_TABLE_NAME).equalsIgnoreCase("ACTIVE")) {

                Log.i("LetThingsSpeakMessages", "The test table is not ready yet.\nTable Status: " + result.getTableStatus(Constants.TEST_TABLE_NAME).toString());
            } else if (result.getTableStatus(Constants.TEST_TABLE_NAME).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_USER) {
                Log.i("LetThingsSpeakMessages", "Users inserted successfully!");
            } else if (result.getTableStatus(Constants.ROOM_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_ROOM_DETAILS) {
                Log.i("LetThingsSpeakMessages", "Room Details inserted successfully!");
            }
        }
    }
}
