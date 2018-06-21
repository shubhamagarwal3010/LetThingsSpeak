package com.iot.letthingsspeak.device.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.iot.letthingsspeak.aws.db.Constants;

@DynamoDBTable(tableName = Constants.GATEWAY_TABLE)

public class GatewayDO {
    private String _userId;
    private Double _gatewayId;
    private String _authToken;
    private String _name;
    private Double _pinCount;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

    @DynamoDBRangeKey(attributeName = "gatewayId")
    @DynamoDBAttribute(attributeName = "gatewayId")
    public Double getGatewayId() {
        return _gatewayId;
    }

    public void setGatewayId(final Double _gatewayId) {
        this._gatewayId = _gatewayId;
    }

    @DynamoDBAttribute(attributeName = "authToken")
    public String getAuthToken() {
        return _authToken;
    }

    public void setAuthToken(final String _authToken) {
        this._authToken = _authToken;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }

    @DynamoDBAttribute(attributeName = "pinCount")
    public Double getPinCount() {
        return _pinCount;
    }

    public void setPinCount(final Double _pinCount) {
        this._pinCount = _pinCount;
    }
}