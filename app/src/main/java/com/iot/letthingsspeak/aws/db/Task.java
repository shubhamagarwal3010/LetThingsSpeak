package com.iot.letthingsspeak.aws.db;

import com.iot.letthingsspeak.aws.db.callbacks.DbDataListener;

import java.util.Map;

public class Task {
    private Constants.DynamoDBManagerType dynamoDBManagerType;
    private String tableName;
    private Map<String, Object> parameterList;
    private DbDataListener listener;

    public Task(DbDataListener listener, Constants.DynamoDBManagerType dynamoDBManagerType, String tableName, Map<String, Object> parameterList) {
        this.listener = listener;
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
