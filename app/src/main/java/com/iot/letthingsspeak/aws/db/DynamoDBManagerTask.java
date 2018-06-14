package com.iot.letthingsspeak.aws.db;

import android.os.AsyncTask;
import android.util.Log;

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
        } else if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_ROOM) {
            if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                DynamoDBClient.insertRoom(types[0].getParameterList());
            }
        }
        return result;
    }

    protected void onPostExecute(DynamoDBManagerTaskResult result) {

        if (!result.getTableStatus(Constants.TEST_TABLE_NAME).equalsIgnoreCase("ACTIVE")) {

            Log.i("LetThingsSpeakMessages", "The test table is not ready yet.\nTable Status: " + result.getTableStatus(Constants.TEST_TABLE_NAME).toString());
        } else if (result.getTableStatus(Constants.TEST_TABLE_NAME).equalsIgnoreCase("ACTIVE")
                && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_USER) {
            Log.i("LetThingsSpeakMessages", "Users inserted successfully!");
        } else if (result.getTableStatus(Constants.ROOM_TABLE).equalsIgnoreCase("ACTIVE")
                && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_ROOM_DETAILS) {
            Log.i("LetThingsSpeakMessages", "Rooms details inserted successfully!");
        } else if (result.getTableStatus(Constants.USER_ROOM_TABLE).equalsIgnoreCase("ACTIVE")
                && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_ROOM) {
            Log.i("LetThingsSpeakMessages", "Rooms inserted successfully!");
        }
    }
}