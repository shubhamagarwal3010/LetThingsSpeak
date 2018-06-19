package com.iot.letthingsspeak.aws.db;

import android.content.Context;

import com.iot.letthingsspeak.aws.db.callbacks.DbDataListener;

import java.util.Map;

public class Task {
    private Context context;
    private Constants.DynamoDBManagerType dynamoDBManagerType;
    private String tableName;
    private Map<String, Object> parameterList;
    private DbDataListener listener;

    public Task(DbDataListener listener, Context context, Constants.DynamoDBManagerType dynamoDBManagerType, String tableName, Map<String, Object> parameterList) {
        this.listener = listener;
        this.context = context;
        this.dynamoDBManagerType = dynamoDBManagerType;
        this.tableName = tableName;
        this.parameterList = parameterList;
    }

    public DbDataListener getListener() {
        return listener;
    }

    public void setListener(DbDataListener listener) {
        this.listener = listener;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Object> getParameterList() {
        return parameterList;
    }

    public void setParameterList(Map<String, Object> parameterList) {
        this.parameterList = parameterList;
    }
}
