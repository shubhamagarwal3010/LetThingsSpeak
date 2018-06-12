package com.iot.letthingsspeak.aws.db;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = Constants.DEVICE_MAP_TABLE)

public class DeviceMapDO {
    private Double _deviceId;
    private String _authToken;
    private Boolean _currentState;
    private Boolean _defaultState;
    private List<String> _delegatedIds;
    private Double _gatewayId;
    private Double _gatewayPin;
    private String _ownerId;
    private Double _roomId;
    private String _tag;

    @DynamoDBHashKey(attributeName = "deviceId")
    @DynamoDBIndexHashKey(attributeName = "deviceId", globalSecondaryIndexName = "index_device_map_device_id")
    public Double getDeviceId() {
        return _deviceId;
    }

    public void setDeviceId(final Double _deviceId) {
        this._deviceId = _deviceId;
    }

    @DynamoDBIndexHashKey(attributeName = "auth_token", globalSecondaryIndexName = "index_device_map_auth_token")
    public String getAuthToken() {
        return _authToken;
    }

    public void setAuthToken(final String _authToken) {
        this._authToken = _authToken;
    }

    @DynamoDBAttribute(attributeName = "current_state")
    public Boolean getCurrentState() {
        return _currentState;
    }

    public void setCurrentState(final Boolean _currentState) {
        this._currentState = _currentState;
    }

    @DynamoDBAttribute(attributeName = "default_state")
    public Boolean getDefaultState() {
        return _defaultState;
    }

    public void setDefaultState(final Boolean _defaultState) {
        this._defaultState = _defaultState;
    }

    @DynamoDBAttribute(attributeName = "delegatedIds")
    public List<String> getDelegatedIds() {
        return _delegatedIds;
    }

    public void setDelegatedIds(final List<String> _delegatedIds) {
        this._delegatedIds = _delegatedIds;
    }

    @DynamoDBIndexHashKey(attributeName = "gatewayId", globalSecondaryIndexName = "index_device_map_gateway_id")
    public Double getGatewayId() {
        return _gatewayId;
    }

    public void setGatewayId(final Double _gatewayId) {
        this._gatewayId = _gatewayId;
    }

    @DynamoDBAttribute(attributeName = "gateway_pin")
    public Double getGatewayPin() {
        return _gatewayPin;
    }

    public void setGatewayPin(final Double _gatewayPin) {
        this._gatewayPin = _gatewayPin;
    }

    @DynamoDBIndexHashKey(attributeName = "ownerId", globalSecondaryIndexName = "index_device_map_ownerId")
    public String getOwnerId() {
        return _ownerId;
    }

    public void setOwnerId(final String _ownerId) {
        this._ownerId = _ownerId;
    }

    @DynamoDBIndexHashKey(attributeName = "roomId", globalSecondaryIndexName = "index_device_map_room_id")
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
