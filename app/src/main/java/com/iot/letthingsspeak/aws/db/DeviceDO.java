package com.iot.letthingsspeak.aws.db;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = Constants.DEVICE_TABLE)

public class DeviceDO {
    private String _userId;
    private Double _deviceId;
    private String _authToken;
    private Boolean _currentState;
    private List<String> _delegatedIds;
    private Double _gatewayId;
    private Double _gatewayPin;
    private String _imageId;
    private String _name;
    private Double _roomId;
    private String _tag;

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

    @DynamoDBAttribute(attributeName = "authToken")
    public String getAuthToken() {
        return _authToken;
    }

    public void setAuthToken(final String _authToken) {
        this._authToken = _authToken;
    }

    @DynamoDBAttribute(attributeName = "currentState")
    public Boolean getCurrentState() {
        return _currentState;
    }

    public void setCurrentState(final Boolean _currentState) {
        this._currentState = _currentState;
    }

    @DynamoDBAttribute(attributeName = "delegatedIds")
    public List<String> getDelegatedIds() {
        return _delegatedIds;
    }

    public void setDelegatedIds(final List<String> _delegatedIds) {
        this._delegatedIds = _delegatedIds;
    }

    @DynamoDBAttribute(attributeName = "gatewayId")
    public Double getGatewayId() {
        return _gatewayId;
    }

    public void setGatewayId(final Double _gatewayId) {
        this._gatewayId = _gatewayId;
    }

    @DynamoDBAttribute(attributeName = "gatewayPin")
    public Double getGatewayPin() {
        return _gatewayPin;
    }

    public void setGatewayPin(final Double _gatewayPin) {
        this._gatewayPin = _gatewayPin;
    }

    @DynamoDBAttribute(attributeName = "imageId")
    public String getImageId() {
        return _imageId;
    }

    public void setImageId(final String _imageId) {
        this._imageId = _imageId;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }

    @DynamoDBAttribute(attributeName = "roomId")
    public Double getRoomId() {
        return _roomId;
    }

    public void setRoomId(final Double _roomId) {
        this._roomId = _roomId;
    }

    @DynamoDBAttribute(attributeName = "tag")
    public String getTag() {
        return _tag;
    }

    public void setTag(final String _tag) {
        this._tag = _tag;
    }

}
