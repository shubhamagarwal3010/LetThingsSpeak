package com.iot.letthingsspeak.aws;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "letthingsspeak-mobilehub-849318221-LetThingsSpeak")

public class LetThingsSpeakDO {
    private String _userId;
    private Double _deviceId;
    private String _deviceName;
    private Double _pin;
    private String _roomName;
    private byte[] _status;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "deviceId")
    @DynamoDBAttribute(attributeName = "deviceId")
    public Double getDeviceId() {
        return _deviceId;
    }

    public void setDeviceId(final Double _deviceId) {
        this._deviceId = _deviceId;
    }
    @DynamoDBAttribute(attributeName = "deviceName")
    public String getDeviceName() {
        return _deviceName;
    }

    public void setDeviceName(final String _deviceName) {
        this._deviceName = _deviceName;
    }
    @DynamoDBAttribute(attributeName = "pin")
    public Double getPin() {
        return _pin;
    }

    public void setPin(final Double _pin) {
        this._pin = _pin;
    }
    @DynamoDBAttribute(attributeName = "roomName")
    public String getRoomName() {
        return _roomName;
    }

    public void setRoomName(final String _roomName) {
        this._roomName = _roomName;
    }
    @DynamoDBAttribute(attributeName = "status")
    public byte[] getStatus() {
        return _status;
    }

    public void setStatus(final byte[] _status) {
        this._status = _status;
    }

}
