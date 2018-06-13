package com.iot.letthingsspeak.aws;


import android.content.Context;

import com.iot.letthingsspeak.aws.db.Constants;

public class DynamoDBManagerTaskResult {
    private Constants.DynamoDBManagerType taskType;
    private String tableStatus;
    private Context context;

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
}
