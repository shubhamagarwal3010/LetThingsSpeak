package com.iot.letthingsspeak.aws.db;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.iot.letthingsspeak.aws.LetThingsSpeakLaunch;

import java.util.HashMap;

public class DynamoDBManager {

    AmazonDynamoDBClient ddb = LetThingsSpeakLaunch.amazonDynamoDBClient;


    public void createTable() {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.CREATE_TABLE, Constants.TEST_TABLE_NAME, new HashMap<String, Object>()));
    }

    public void insertUsers() {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.INSERT_USER, Constants.TEST_TABLE_NAME, new HashMap<String, Object>()));
    }

    public void insertRoom(final String roomName) {
        new DynamoDBManagerTask()
                .execute(new Task(null, Constants.DynamoDBManagerType.INSERT_ROOM, Constants.ROOM_TABLE, new HashMap<String, Object>() {{
                    put("room_name", roomName);
                }}));
    }
}
