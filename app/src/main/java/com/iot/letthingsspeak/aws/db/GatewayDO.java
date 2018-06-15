package com.iot.letthingsspeak.aws.db;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = Constants.GATEWAY_TABLE)

public class GatewayDO {
    private Double _id;
    private String _authToken;
    private String _name;
    private Double _pinCount;
    private String _tag;
    private String _userId;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAttribute(attributeName = "id")
    public Double getId() {
        return _id;
    }

    public void setId(final Double _id) {
        this._id = _id;
    }

    @DynamoDBIndexHashKey(attributeName = "auth_token", globalSecondaryIndexName = "index_gateway_auth_token")
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

    @DynamoDBAttribute(attributeName = "pin_count")
    public Double getPinCount() {
        return _pinCount;
    }

    public void setPinCount(final Double _pinCount) {
        this._pinCount = _pinCount;
    }

    @DynamoDBIndexHashKey(attributeName = "tag", globalSecondaryIndexName = "index_gateway_tag")
    public String getTag() {
        return _tag;
    }

    public void setTag(final String _tag) {
        this._tag = _tag;
    }

    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = "index_room_gateway_user_id")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

}
