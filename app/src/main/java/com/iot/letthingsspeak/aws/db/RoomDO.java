package com.iot.letthingsspeak.aws.db;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.io.Serializable;

@DynamoDBTable(tableName = Constants.ROOM_TABLE)

public class RoomDO implements Serializable {
    private String _userId;
    private String _roomId;
    private Double _imageId;
    private String _name;

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

    @DynamoDBAttribute(attributeName = "imageId")
    public Double getImageId() {
        return _imageId;
    }

    public void setImageId(final Double _imageId) {
        this._imageId = _imageId;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
}
