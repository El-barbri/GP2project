package com.example.gp2project;

public class DeviceData {

    String name;
    String mac;
    int rssi;
    String Distance;

    public DeviceData(String name, String mac, int rssi, String distance) {
        this.name = name;
        this.mac = mac;
        this.rssi = rssi;
        Distance = distance;
    }

    public String getDistance() {
        return Distance;
    }

    public DeviceData(String name, String mac, int rssi) {
        this.name = name;
        this.mac = mac;
        this.rssi = rssi;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}
