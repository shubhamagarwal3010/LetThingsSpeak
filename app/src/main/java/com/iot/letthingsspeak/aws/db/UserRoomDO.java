package com.iot.letthingsspeak.aws.db;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "letthingsspeak-mobilehub-849318221-user_room")

public class UserRoomDO {
    private String _userId;
    private Boolean _isAdmin;
    private Double _roomId;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

    @DynamoDBAttribute(attributeName = "isAdmin")
    public Boolean getIsAdmin() {
        return _isAdmin;
    }

    public void setIsAdmin(final Boolean _isAdmin) {
        this._isAdmin = _isAdmin;
    }

    @DynamoDBAttribute(attributeName = "roomId")
    public Double getRoomId() {
        return _roomId;
    }

    public void setRoomId(final Double _roomId) {
        this._roomId = _roomId;
    }

}
