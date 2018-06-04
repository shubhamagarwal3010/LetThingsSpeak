package com.iot.letthingsspeak.device;

public class DeviceDetails {

    public DeviceDetails(String deviceName, String status) {
        this.deviceName = deviceName;
        this.status = status;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String deviceName;
    private String status;
}
