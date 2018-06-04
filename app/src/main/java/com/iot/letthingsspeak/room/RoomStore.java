package com.iot.letthingsspeak.room;


import java.util.ArrayList;
import java.util.List;

public class RoomStore {
    private static List<RoomDetails> roomDetails = new ArrayList<>();

    public static List<RoomDetails> getRoomDetails() {
        return roomDetails;
    }

    public static void setRoomDetails(List<RoomDetails> roomDetails) {
        RoomStore.roomDetails = roomDetails;
    }
}
