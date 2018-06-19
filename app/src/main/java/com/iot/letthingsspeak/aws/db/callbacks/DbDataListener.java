package com.iot.letthingsspeak.aws.db.callbacks;

import com.iot.letthingsspeak.aws.db.Constants;

public interface DbDataListener {
    void publishResultsOnSuccess(Constants.DynamoDBManagerType type, Object data);
}
