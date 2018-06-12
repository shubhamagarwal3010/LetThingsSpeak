package com.iot.letthingsspeak.aws.db;

import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.iot.letthingsspeak.UserActivity;
import com.iot.letthingsspeak.aws.AppHelper;

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
                LetThingsSpeakDO letThingsSpeakDO = new LetThingsSpeakDO();
                letThingsSpeakDO.setUserId(AppHelper.getCurrUser());
                letThingsSpeakDO.setRoomName("Bed-Room");
                letThingsSpeakDO.setDeviceName("Fan");
                letThingsSpeakDO.setDeviceId(124.0);

                Log.d(TAG, "Inserting data");
                mapper.save(letThingsSpeakDO);
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
                LetThingsSpeakDO letThingsSpeakDO = new LetThingsSpeakDO();
                letThingsSpeakDO.setRoomName(roomName);

                Log.d(TAG, "Inserting room");
                mapper.save(letThingsSpeakDO);
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
    public static LetThingsSpeakDO getUserPreference(int userNo) {

        AmazonDynamoDBClient ddb = UserActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            LetThingsSpeakDO letThingsSpeakDO = mapper.load(LetThingsSpeakDO.class,
                    userNo);

            return letThingsSpeakDO;

        } catch (AmazonServiceException ex) {
            UserActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }

        return null;
    }

    /*
     * Updates one attribute/value pair for the specified user.
     */
    public static void updateUserPreference(LetThingsSpeakDO updateLetThingsSpeakDO) {

        AmazonDynamoDBClient ddb = UserActivity.clientManager
                .ddb();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);

        try {
            mapper.save(updateLetThingsSpeakDO);

        } catch (AmazonServiceException ex) {
            UserActivity.clientManager
                    .wipeCredentialsOnAuthError(ex);
        }
    }
}
