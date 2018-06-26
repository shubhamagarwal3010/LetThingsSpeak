package com.iot.letthingsspeak.aws.db;

import com.iot.letthingsspeak.aws.db.callbacks.DbDataListener;
import com.iot.letthingsspeak.common.views.BaseView;

import java.util.Map;

public class Task {
    private Constants.DynamoDBManagerType dynamoDBManagerType;
    private String tableName;
    private Map<String, Object> parameterList;


    public Task(Constants.DynamoDBManagerType dynamoDBManagerType, String tableName, Map<String, Object> parameterList) {
        this.dynamoDBManagerType = dynamoDBManagerType;
        this.tableName = tableName;
        this.parameterList = parameterList;
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
