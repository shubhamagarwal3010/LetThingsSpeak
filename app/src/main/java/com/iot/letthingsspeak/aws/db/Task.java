package com.iot.letthingsspeak.aws.db;

import android.content.Context;

public class Task {
    private Context context;
    private Constants.DynamoDBManagerType dynamoDBManagerType;

    public Task(Context context, Constants.DynamoDBManagerType dynamoDBManagerType) {
        this.context = context;
        this.dynamoDBManagerType = dynamoDBManagerType;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Constants.DynamoDBManagerType getDynamoDBManagerType() {
        return dynamoDBManagerType;
    }

    public void setDynamoDBManagerType(Constants.DynamoDBManagerType dynamoDBManagerType) {
        this.dynamoDBManagerType = dynamoDBManagerType;
    }
}
