package com.iot.letthingsspeak.aws;

import android.os.AsyncTask;
import android.widget.Toast;

import com.iot.letthingsspeak.aws.db.Constants;
import com.iot.letthingsspeak.aws.db.DynamoDBManager;
import com.iot.letthingsspeak.aws.db.Task;

public class DynamoDBManagerTask extends
        AsyncTask<Task, Void, DynamoDBManagerTaskResult> {

    protected DynamoDBManagerTaskResult doInBackground(Task... types) {

        String tableStatus = DynamoDBManager.getTestTableStatus(Constants.TEST_TABLE_NAME);

        DynamoDBManagerTaskResult result = new DynamoDBManagerTaskResult();
        result.setTableStatus(tableStatus);
        result.setTaskType(types[0].getDynamoDBManagerType());

        if (types[0].getDynamoDBManagerType() == Constants.DynamoDBManagerType.INSERT_USER) {
            if (tableStatus.equalsIgnoreCase("ACTIVE")) {
                DynamoDBManager.insertUsers();
            }
        }
        result.setContext(types[0].getContext());
        return result;
    }

    protected void onPostExecute(DynamoDBManagerTaskResult result) {

        if (!result.getTableStatus(Constants.TEST_TABLE_NAME).equalsIgnoreCase("ACTIVE")) {

            Toast.makeText(
                    result.getContext(),
                    "The test table is not ready yet.\nTable Status: "
                            + result.getTableStatus(Constants.TEST_TABLE_NAME), Toast.LENGTH_LONG)
                    .show();
        } else if (result.getTableStatus(Constants.TEST_TABLE_NAME).equalsIgnoreCase("ACTIVE")
                && result.getTaskType() == Constants.DynamoDBManagerType.INSERT_USER) {
            Toast.makeText(result.getContext(),
                    "Users inserted successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}