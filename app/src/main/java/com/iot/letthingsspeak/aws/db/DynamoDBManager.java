package com.iot.letthingsspeak.aws.db;

import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.iot.letthingsspeak.UserActivity;
import com.iot.letthingsspeak.aws.AppHelper;
import com.iot.letthingsspeak.aws.LetThingsSpeakLaunch;

public class DynamoDBManager {

    private static final String TAG = "DynamoDBManager";
    AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonDynamoDBClient;

    /*
     * Retrieves the table description and returns the table status as a string.
     */
    public static String getTestTableStatus(String tableName) {

        try {
            AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager
                    .ddb();

            DescribeTableRequest request = new DescribeTableRequest()
                    .withTableName(tableName);
            DescribeTableResult result = ddb.describeTable(request);

            String status = result.getTable().getTableStatus();
            return status == null ? "" : status;

        } catch (AmazonServiceException ex) {
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }

        return "";
    }


    /*
     * Inserts ten users with userNo from 1 to 10 and random names.
     */
    public static void insertUsers() {
//        AmazonDynamoDBClient ddb = UserActivity.clientManager
//                .ddb();
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);


        try {
            //for (int i = 1; i <= 10; i++)
            {
                LetThingsSpeakDO letThingsSpeakDO = new LetThingsSpeakDO();
                letThingsSpeakDO.setUserId(AppHelper.getCurrUser());
                letThingsSpeakDO.setRoomName("Living-Room");
                letThingsSpeakDO.setDeviceName("Bulb");
                letThingsSpeakDO.setDeviceId(124.0);

                Log.d(TAG, "Inserting data");
                mapper.save(letThingsSpeakDO);
                Log.d(TAG, "Data inserted");
            }
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting users");
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }    /*
     * Inserts ten users with userNo from 1 to 10 and random names.
     */

    public static void insertRoom(String roomName) {
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);


        try {
            //for (int i = 1; i <= 10; i++)
            {
                LetThingsSpeakDO letThingsSpeakDO = new LetThingsSpeakDO();
                letThingsSpeakDO.setRoomName(roomName);

                Log.d(TAG, "Inserting room");
                mapper.save(letThingsSpeakDO);
                Log.d(TAG, "Room inserted");
            }
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting room");
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    /*
     * Retrieves all of the attribute/value pairs for the specified user.
     */
    public static LetThingsSpeakDO getUserPreference(int userNo) {

        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            LetThingsSpeakDO letThingsSpeakDO = mapper.load(LetThingsSpeakDO.class,
                    userNo);

            return letThingsSpeakDO;

        } catch (AmazonServiceException ex) {
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }

        return null;
    }

    /*
     * Updates one attribute/value pair for the specified user.
     */
    public static void updateUserPreference(LetThingsSpeakDO updateLetThingsSpeakDO) {

        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            mapper.save(updateLetThingsSpeakDO);

        } catch (AmazonServiceException ex) {
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }
}
