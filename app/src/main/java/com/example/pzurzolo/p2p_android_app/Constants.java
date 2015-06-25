package com.example.pzurzolo.p2p_android_app;

public interface Constants {

    // Message types sent from BluetoothP2PService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key name received from the BluetoothP2PService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
}
