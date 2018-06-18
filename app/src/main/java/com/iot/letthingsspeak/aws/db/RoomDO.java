package com.iot.letthingsspeak.aws.db;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = Constants.ROOM_TABLE)

public class RoomDO {
    private String _userId;
    private Double _roomId;
    private String _imageId;
    private Boolean _isAdmin;
    private String _name;
    private String _tag;

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
    public Double getRoomId() {
        return _roomId;
    }

    public void setRoomId(final Double _roomId) {
        this._roomId = _roomId;
    }
    @DynamoDBAttribute(attributeName = "imageId")
    public String getImageId() {
        return _imageId;
    }

    public void setImageId(final String _imageId) {
        this._imageId = _imageId;
    }
    @DynamoDBAttribute(attributeName = "isAdmin")
    public Boolean getIsAdmin() {
        return _isAdmin;
    }

    public void setIsAdmin(final Boolean _isAdmin) {
        this._isAdmin = _isAdmin;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
    @DynamoDBAttribute(attributeName = "tag")
    public String getTag() {
        return _tag;
    }

    public void setTag(final String _tag) {
        this._tag = _tag;
    }

}
