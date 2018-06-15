package com.iot.letthingsspeak.aws.db;


import android.content.Context;

public class DynamoDBManagerTaskResult {
    private Constants.DynamoDBManagerType taskType;
    private String tableStatus;
    private Context context;
    private Object returnValue;

    public Constants.DynamoDBManagerType getTaskType() {
        return taskType;
    }

    public void setTaskType(Constants.DynamoDBManagerType taskType) {
        this.taskType = taskType;
    }

    public String getTableStatus(String tableName) {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }
}
