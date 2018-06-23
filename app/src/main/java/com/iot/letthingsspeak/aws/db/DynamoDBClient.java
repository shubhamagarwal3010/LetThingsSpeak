package com.iot.letthingsspeak.aws.db;

import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.iot.letthingsspeak.LetThingsSpeakApplication;
import com.iot.letthingsspeak.aws.AppHelper;
import com.iot.letthingsspeak.device.model.DeviceDO;
import com.iot.letthingsspeak.device.model.GatewayDO;
import com.iot.letthingsspeak.identity.model.UserDO;
import com.iot.letthingsspeak.room.model.RoomDO;
import com.iot.letthingsspeak.room.model.UserRoomDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamoDBClient {
    private static final String TAG = "DynamoDBClient";


    /*
     * Add User details
     */

    public static void insertUser(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakApplication.amazonClientManager.ddb();
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
            LetThingsSpeakApplication.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    /*
     * Retrieves the table description and returns the table status as a string.
     */
    public static String getTestTableStatus(String tableName) {

        try {
            AmazonDynamoDBClient ddb = LetThingsSpeakApplication.amazonClientManager
                    .ddb();

            DescribeTableRequest request = new DescribeTableRequest()
                    .withTableName(tableName);
            DescribeTableResult result = ddb.describeTable(request);

            String status = result.getTable().getTableStatus();
            return status == null ? "" : status;

        } catch (AmazonServiceException ex) {
            LetThingsSpeakApplication.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }

        return "";
    }


    public static void insertRoom(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakApplication.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            RoomDO roomDO = new RoomDO();
            roomDO.setRoomId((String) parameterList.get("roomId"));
            roomDO.setUserId(AppHelper.getCurrUser());
            roomDO.setName((String) parameterList.get("roomName"));
            roomDO.setImageId((double) parameterList.get("imageId"));
            roomDO.setState((boolean) parameterList.get("state"));

            Log.d(TAG, "Inserting room details");
            mapper.save(roomDO);
            Log.d(TAG, "Room details inserted");
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting room details");
            LetThingsSpeakApplication.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    public static void insertUserRoom(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakApplication.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            UserRoomDO userRoomDO = new UserRoomDO();
            userRoomDO.setRoomId((String) parameterList.get("roomId"));
            userRoomDO.setUserId(AppHelper.getCurrUser());
            userRoomDO.setIsAdmin((Boolean) parameterList.get("isAdmin"));

            Log.d(TAG, "Inserting room");
            mapper.save(userRoomDO);
            Log.d(TAG, "Room inserted");
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting room");
            LetThingsSpeakApplication.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    public static void insertDevice(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakApplication.amazonClientManager.ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        //IdentityManager.getDefaultIdentityManager().getCachedUserID();

        try {
            DeviceDO deviceDO = new DeviceDO();
            deviceDO.setRoomId((String) parameterList.get("roomId"));
            deviceDO.setDeviceId((String) parameterList.get("deviceId"));
            deviceDO.setDeviceName((String) parameterList.get("name"));
            deviceDO.setState((boolean) parameterList.get("state"));

            Log.d(TAG, "Inserting device");
            mapper.save(deviceDO);
            Log.d(TAG, "Device details inserted");
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting device");
            LetThingsSpeakApplication.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    public static void insertGateway(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakApplication.amazonClientManager.ddb();
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
            LetThingsSpeakApplication.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }


    public static List<RoomDO> getRoomsForUser() {
        String userId = AppHelper.getCurrUser();
        AmazonDynamoDBClient ddb = LetThingsSpeakApplication.amazonClientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        List<RoomDO> roomDOMap = new ArrayList<RoomDO>();

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
                roomDOMap.add(roomDOList.get(0));
            }
            return roomDOMap;
        } catch (AmazonServiceException ex) {
            LetThingsSpeakApplication.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
        return null;
    }


    public static List<DeviceDO> getDevicesForRoom(Map<String, Object> parameterList) {
        AmazonDynamoDBClient ddb = LetThingsSpeakApplication.amazonClientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        String roomId = (String) parameterList.get("roomId");
        Map<String, AttributeValue> eav1 = new HashMap<String, AttributeValue>();
        eav1.put(":val1", new AttributeValue().withS(roomId));

        DynamoDBScanExpression dynamoDBScanExpression1 = new DynamoDBScanExpression();
        dynamoDBScanExpression1.withFilterExpression("roomId = :val1").withExpressionAttributeValues(eav1);

        try {
            List<DeviceDO> deviceDOList = mapper.scan(DeviceDO.class, dynamoDBScanExpression1);
            return deviceDOList;
        } catch (AmazonServiceException ex) {
            LetThingsSpeakApplication.amazonClientManager
                    .wipeCredentialsOnAuthError(ex);
        }
        return null;
    }


//    public static void updateRoom(Map<String, Object> attributeValues) {
//        AmazonDynamoDBClient ddb = LetThingsSpeakApplication.amazonClientManager
//                .ddb();
//        DynamoDB dynamoDB = new DynamoDB(ddb);
//        //attributeValues.get("")
//        Table table = dynamoDB.getTable(Constants.ROOM_TABLE);
//        //UpdateItemSpec updateItemSpec = new UpdateItemSpec().withUpdateExpression("set #na = :val").withNameMap(new NameMap().with("#na",))
//
//    }
}
