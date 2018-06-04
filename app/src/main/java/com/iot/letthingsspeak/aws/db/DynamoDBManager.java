package com.iot.letthingsspeak.aws.db;

import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.iot.letthingsspeak.aws.AppHelper;
import com.iot.letthingsspeak.UserActivity;

public class DynamoDBManager {

    private static final String TAG = "DynamoDBManager";

    /*
     * Creates a table with the following attributes: Table name: testTableName
     * Hash key: userNo type N Read Capacity Units: 10 Write Capacity Units: 5
     */
    public static void createTable() {

        Log.d(TAG, "Create table called");

        AmazonDynamoDBClient ddb = UserActivity.clientManager
                .ddb();

        KeySchemaElement kse = new KeySchemaElement().withAttributeName(
                "_userId").withKeyType(KeyType.HASH);
        AttributeDefinition ad = new AttributeDefinition().withAttributeName(
                "_userId").withAttributeType(ScalarAttributeType.N);
        ProvisionedThroughput pt = new ProvisionedThroughput()
                .withReadCapacityUnits(10l).withWriteCapacityUnits(5l);

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(Constants.TEST_TABLE_NAME)
                .withKeySchema(kse).withAttributeDefinitions(ad)
                .withProvisionedThroughput(pt);

        try {
            Log.d(TAG, "Sending Create table request");
            ddb.createTable(request);
            Log.d(TAG, "Create request response successfully recieved");
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error sending create table request", ex);
            UserActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    /*
     * Retrieves the table description and returns the table status as a string.
     */
    public static String getTestTableStatus() {

        try {
            AmazonDynamoDBClient ddb = UserActivity.clientManager
                    .ddb();

            DescribeTableRequest request = new DescribeTableRequest()
                    .withTableName(Constants.TEST_TABLE_NAME);
            DescribeTableResult result = ddb.describeTable(request);

            String status = result.getTable().getTableStatus();
            return status == null ? "" : status;

        } catch (ResourceNotFoundException e) {
        } catch (AmazonServiceException ex) {
            UserActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }

        return "";
    }

    /*
     * Inserts ten users with userNo from 1 to 10 and random names.
     */
    public static void insertUsers() {
        AmazonDynamoDBClient ddb = UserActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);


        try {
            //for (int i = 1; i <= 10; i++)
            {
                UserPreference userPreference = new UserPreference();
                userPreference.setUserId(AppHelper.getCurrUser());
                userPreference.setRoomName("Bed-Room");
                userPreference.setDeviceName("Fan");
                userPreference.setDeviceId(124.0);

                Log.d(TAG, "Inserting data");
                mapper.save(userPreference);
                Log.d(TAG, "Data inserted");
            }
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting users");
            UserActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }    /*
     * Inserts ten users with userNo from 1 to 10 and random names.
     */
    public static void insertRoom(String roomName) {
        AmazonDynamoDBClient ddb = UserActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);


        try {
            //for (int i = 1; i <= 10; i++)
            {
                UserPreference userPreference = new UserPreference();
                userPreference.setRoomName(roomName);

                Log.d(TAG, "Inserting room");
                mapper.save(userPreference);
                Log.d(TAG, "Room inserted");
            }
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error inserting room");
            UserActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    /*
     * Retrieves all of the attribute/value pairs for the specified user.
     */
    public static UserPreference getUserPreference(int userNo) {

        AmazonDynamoDBClient ddb = UserActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            UserPreference userPreference = mapper.load(UserPreference.class,
                    userNo);

            return userPreference;

        } catch (AmazonServiceException ex) {
            UserActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }

        return null;
    }

    /*
     * Updates one attribute/value pair for the specified user.
     */
    public static void updateUserPreference(UserPreference updateUserPreference) {

        AmazonDynamoDBClient ddb = UserActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            mapper.save(updateUserPreference);

        } catch (AmazonServiceException ex) {
            UserActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }

    @DynamoDBTable(tableName = Constants.TEST_TABLE_NAME)
    public static class UserPreference {
        private String _userId;
        private Double _deviceId;
        private String _deviceName;
        private Double _pin;
        private String _roomName;
        private byte[] _status;

        @DynamoDBHashKey(attributeName = "userId")
        @DynamoDBAttribute(attributeName = "userId")
        public String getUserId() {
            return _userId;
        }

        public void setUserId(final String _userId) {
            this._userId = _userId;
        }
        @DynamoDBRangeKey(attributeName = "deviceId")
        @DynamoDBAttribute(attributeName = "deviceId")
        public Double getDeviceId() {
            return _deviceId;
        }

        public void setDeviceId(final Double _deviceId) {
            this._deviceId = _deviceId;
        }
        @DynamoDBAttribute(attributeName = "deviceName")
        public String getDeviceName() {
            return _deviceName;
        }

        public void setDeviceName(final String _deviceName) {
            this._deviceName = _deviceName;
        }
        @DynamoDBAttribute(attributeName = "pin")
        public Double getPin() {
            return _pin;
        }

        public void setPin(final Double _pin) {
            this._pin = _pin;
        }
        @DynamoDBAttribute(attributeName = "roomName")
        public String getRoomName() {
            return _roomName;
        }

        public void setRoomName(final String _roomName) {
            this._roomName = _roomName;
        }
        @DynamoDBAttribute(attributeName = "status")
        public byte[] getStatus() {
            return _status;
        }

        public void setStatus(final byte[] _status) {
            this._status = _status;
        }
    }
}
