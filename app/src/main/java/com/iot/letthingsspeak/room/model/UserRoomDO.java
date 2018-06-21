package com.iot.letthingsspeak.room.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.iot.letthingsspeak.aws.db.Constants;

@DynamoDBTable(tableName = Constants.USER_ROOM_TABLE)

public class UserRoomDO {
    private String _userId;
    private String _roomId;
    private Boolean _isAdmin;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

    @DynamoDBRangeKey(attributeName = "roomId")
    @DynamoDBAttribute(attributeName = "roomId")
    public String getRoomId() {
        return _roomId;
    }

    public void setRoomId(final String _roomId) {
        this._roomId = _roomId;
    }

    @DynamoDBAttribute(attributeName = "isAdmin")
    public Boolean getIsAdmin() {
        return _isAdmin;
    }

    public void setIsAdmin(final Boolean _isAdmin) {
        this._isAdmin = _isAdmin;
    }

}
