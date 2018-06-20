package com.iot.letthingsspeak.room;

public class RoomDetails {

    private String roomType;
    private String status;
    private Integer imageId;

    public RoomDetails(String roomType, Integer imageId, String status) {
        this.roomType = roomType;
        this.imageId = imageId;
        this.status = status;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
