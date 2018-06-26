package com.iot.letthingsspeak;

import android.app.Application;

import com.iot.letthingsspeak.aws.db.AmazonClientManager;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.common.util.Prefs;

public class LetThingsSpeakApplication extends Application {
    public static AmazonClientManager amazonClientManager;
    public static DynamoDBManager dynamoDBManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.initPrefs(getApplicationContext());
        amazonClientManager = new AmazonClientManager(getApplicationContext());
        dynamoDBManager = new DynamoDBManager();
    }
}
