package com.iot.letthingsspeak.aws.db;

import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.iot.letthingsspeak.aws.AppHelper;
import com.iot.letthingsspeak.aws.LetThingsSpeakLaunch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamoDBClient {
    private static final String TAG = "DynamoDBClient";


    /*
     * Add User details
     */

    public static void insertUser(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);


        try {
            //for (int i = 1; i <= 10; i++)
            {
                UserDO userDO = new UserDO();
                userDO.setUserId(AppHelper.getCurrUser());
                //userDO.setDetails(AppHelper.getUserDetails();

                Log.d(TAG, "Inserting user");
                mapper.save(userDO);
                Log.d(TAG, "User inserted");
            }
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting user");
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }
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
                letThingsSpeakDO.setRoomName("Kitchen");
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

    public static void insertRoomDetails(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);


        try {
            //for (int i = 1; i <= 10; i++)
            {
                RoomDO roomDO = new RoomDO();
                roomDO.setId((double) 1211);
                roomDO.setName((String) parameterList.get("room_name"));

                Log.d(TAG, "Inserting room details");
                mapper.save(roomDO);
                Log.d(TAG, "Room details inserted");
            }
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting room details");
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }
    /*
     * Inserts ten users with userNo from 1 to 10 and random names.
     */

    public static void insertRoom(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);


        try {
            //for (int i = 1; i <= 10; i++)
            {
                UserRoomDO userRoomDO = new UserRoomDO();
                userRoomDO.setUserId(AppHelper.getCurrUser());
                userRoomDO.setRoomId((Double) parameterList.get("room_id"));
                userRoomDO.setIsAdmin((Boolean) parameterList.get("isAdmin"));
                userRoomDO.setIsAdmin(true);

                Log.d(TAG, "Inserting room");
                mapper.save(userRoomDO);
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


    public static Map<Double, RoomDO> getRoomsForUser() {
        String userId = AppHelper.getCurrUser();
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        Map<Double, RoomDO> roomDOMap = new HashMap<>();

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(userId));

        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        dynamoDBScanExpression.withFilterExpression("userId = :val1").withExpressionAttributeValues(eav);

        try {
            List<UserRoomDO> userRoomDOList = mapper.scan(UserRoomDO.class, dynamoDBScanExpression);
            for (UserRoomDO userRoomDO : userRoomDOList) {
                Double roomId = userRoomDO.getRoomId();
                RoomDO roomDO = mapper.load(RoomDO.class, roomId);
                roomDOMap.put(roomId, roomDO);
            }
            return roomDOMap;
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
