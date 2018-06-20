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


    public static void insertRoom(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);


        try {
            RoomDO roomDO = new RoomDO();
            roomDO.setRoomId((String) parameterList.get("roomId"));
            roomDO.setUserId(AppHelper.getCurrUser());
            roomDO.setName((String) parameterList.get("roomName"));
            roomDO.setImageId((double) parameterList.get("imageId"));

            Log.d(TAG, "Inserting room");
            mapper.save(roomDO);
            Log.d(TAG, "Room details inserted");
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting room");
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    public static void insertDevice(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        //IdentityManager.getDefaultIdentityManager().getCachedUserID();

        try {
            DeviceDO deviceDO = new DeviceDO();
            deviceDO.setUserId(AppHelper.getCurrUser());
            deviceDO.setDeviceId((double) parameterList.get("deviceId"));
            deviceDO.setCurrentState((Boolean) parameterList.get("currentState"));
            deviceDO.setDelegatedIds((List<String>) parameterList.get("delegatedIds"));
            deviceDO.setGatewayId((double) parameterList.get("gatewayId"));
            deviceDO.setGatewayPin((double) parameterList.get("gatewayPin"));
            deviceDO.setImageId((String) parameterList.get("image_id"));
            deviceDO.setName((String) parameterList.get("name"));
            deviceDO.setRoomId((double) parameterList.get("roomId"));
            deviceDO.setTag((String) parameterList.get("tag"));

            Log.d(TAG, "Inserting device");
            mapper.save(deviceDO);
            Log.d(TAG, "Device details inserted");
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting device");
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    public static void insertGateway(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);


        try {
            GatewayDO gatewayDO = new GatewayDO();
            gatewayDO.setUserId(AppHelper.getCurrUser());
            gatewayDO.setGatewayId((double) parameterList.get("gatewayId"));
            gatewayDO.setName((String) parameterList.get("name"));
            gatewayDO.setPinCount((double) parameterList.get("pinCount"));

            Log.d(TAG, "Inserting gateway");
            mapper.save(gatewayDO);
            Log.d(TAG, "Device gateway inserted");
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error gateway device");
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }


    public static Map<String, RoomDO> getRoomsForUser() {
        String userId = AppHelper.getCurrUser();
        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        Map<String, RoomDO> roomDOMap = new HashMap<>();

        Map<String, AttributeValue> eav1 = new HashMap<String, AttributeValue>();
        eav1.put(":val1", new AttributeValue().withS(userId));

        DynamoDBScanExpression dynamoDBScanExpression1 = new DynamoDBScanExpression();
        dynamoDBScanExpression1.withFilterExpression("userId = :val1").withExpressionAttributeValues(eav1);

        try {
            List<UserRoomDO> userRoomDOList = mapper.scan(UserRoomDO.class, dynamoDBScanExpression1);
            for (UserRoomDO userRoomDO : userRoomDOList) {
                String roomId = userRoomDO.getRoomId();

                Map<String, AttributeValue> eav2 = new HashMap<String, AttributeValue>();
                eav2.put(":val1", new AttributeValue().withS(roomId));

                DynamoDBScanExpression dynamoDBScanExpression2 = new DynamoDBScanExpression();
                dynamoDBScanExpression2.withFilterExpression("roomId = :val1").withExpressionAttributeValues(eav2);

                List<RoomDO> roomDOList = mapper.scan(RoomDO.class, dynamoDBScanExpression2);
                roomDOMap.put(roomId, roomDOList.get(0));
            }
            return roomDOMap;
        } catch (AmazonServiceException ex) {
            LetThingsSpeakLaunch.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
        return null;
    }

//    public static void updateRoom(Map<String, Object> attributeValues) {
//        AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonClientManager
//                .ddb();
//        DynamoDB dynamoDB = new DynamoDB(ddb);
//        //attributeValues.get("")
//        Table table = dynamoDB.getTable(Constants.ROOM_TABLE);
//        //UpdateItemSpec updateItemSpec = new UpdateItemSpec().withUpdateExpression("set #na = :val").withNameMap(new NameMap().with("#na",))
//
//    }
}
