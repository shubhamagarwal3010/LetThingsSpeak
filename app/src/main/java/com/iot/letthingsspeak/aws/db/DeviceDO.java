package com.iot.letthingsspeak.aws.db;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "letthingsspeak-mobilehub-849318221-device")

public class DeviceDO {
    private Double _id;
    private String _imageId;
    private String _name;
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

    @DynamoDBIndexHashKey(attributeName = "tag", globalSecondaryIndexName = "index_device_tag")
    public String getTag() {
        return _tag;
    }

    public void setTag(final String _tag) {
        this._tag = _tag;
    }

    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = "index_device_user_id")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

}
