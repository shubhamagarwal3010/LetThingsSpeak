package com.iot.letthingsspeak.room;

public class RoomDetails {

    public RoomDetails(String roomType, String status) {
        this.roomType = roomType;
        this.status = status;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String roomType;
    private String status;
}
