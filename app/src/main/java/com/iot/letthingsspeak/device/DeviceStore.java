package com.iot.letthingsspeak.device;


import java.util.ArrayList;
import java.util.List;

public class DeviceStore {
    private static List<DeviceDetails> deviceDetails = new ArrayList<>();

    public static List<DeviceDetails> getDeviceDetails() {
        return deviceDetails;
    }

    public static void setDeviceDetails(List<DeviceDetails> deviceDetails) {
        DeviceStore.deviceDetails = deviceDetails;
    }
}
