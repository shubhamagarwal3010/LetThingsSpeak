package com.iot.letthingsspeak.aws;

import android.app.Application;
import android.content.Context;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.iot.letthingsspeak.aws.db.AmazonClientManager;

public class LetThingsSpeakLaunch extends Application {
    public static AmazonDynamoDBClient amazonDynamoDBClient = null;
    private static Context sContext;
    public static AmazonClientManager amazonClientManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        amazonClientManager = new AmazonClientManager(sContext);
    }
}
