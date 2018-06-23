package com.iot.letthingsspeak.device.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.iot.letthingsspeak.aws.db.Constants;

@DynamoDBTable(tableName = Constants.DEVICE_TABLE)

public class DeviceDO {
    private String _roomId;
    private String _deviceId;
    private String _deviceName;
    private Boolean _state;

    @DynamoDBHashKey(attributeName = "roomId")
    @DynamoDBAttribute(attributeName = "roomId")
    public String getRoomId() {
        return _roomId;
    }

    public void setRoomId(final String _roomId) {
        this._roomId = _roomId;
    }

    @DynamoDBRangeKey(attributeName = "deviceId")
    @DynamoDBAttribute(attributeName = "deviceId")
    public String getDeviceId() {
        return _deviceId;
    }

    public void setDeviceId(final String _deviceId) {
        this._deviceId = _deviceId;
    }

    @DynamoDBAttribute(attributeName = "deviceName")
    public String getDeviceName() {
        return _deviceName;
    }

    public void setDeviceName(final String _deviceName) {
        this._deviceName = _deviceName;
    }

    @DynamoDBAttribute(attributeName = "state")
    public Boolean getState() {
        return _state;
    }

    public void setState(final Boolean _state) {
        this._state = _state;
    }
}