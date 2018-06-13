package com.iot.letthingsspeak.aws;

import android.app.Application;
import android.content.Context;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.iot.letthingsspeak.aws.db.AmazonClientManager;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;

public class LetThingsSpeakLaunch extends Application {
    public static AmazonDynamoDBClient amazonDynamoDBClient = null;
    public static AmazonClientManager amazonClientManager;
    public static DynamoDBManager dynamoDBManager;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        amazonClientManager = new AmazonClientManager(sContext);
        dynamoDBManager = new DynamoDBManager();
    }
}
