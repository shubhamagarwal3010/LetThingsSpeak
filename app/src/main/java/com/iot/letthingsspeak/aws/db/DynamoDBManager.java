package com.iot.letthingsspeak.aws.db;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.iot.letthingsspeak.aws.LetThingsSpeakLaunch;

import java.util.Map;


public class DynamoDBManager {

    AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonDynamoDBClient;
    Object returnValue;

    public void insertRoom(final Map<String, Object> parameterList) {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.INSERT_ROOM, Constants.ROOM_TABLE, parameterList));
    }

    public void insertDevice(final Map<String, Object> parameterList) {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.INSERT_DEVICE, Constants.DEVICE_TABLE, parameterList));
    }

    public void insertGateway(final Map<String, Object> parameterList) {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.INSERT_GATEWAY, Constants.GATEWAY_TABLE, parameterList));
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
            } else if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_GATEWAY) {
                if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                    DynamoDBClient.insertGateway(types[0].getParameterList());
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
                Log.i("LetThingsSpeakMessages", "Room inserted successfully!");
            } else if (result.getTableStatus(Constants.DEVICE_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_DEVICE) {
                Log.i("LetThingsSpeakMessages", "Device inserted successfully!");
            } else if (result.getTableStatus(Constants.GATEWAY_TABLE).equalsIgnoreCase("ACTIVE")
                    && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_GATEWAY) {
                Log.i("LetThingsSpeakMessages", "Gateway inserted successfully!");
            }
        }
    }
}
