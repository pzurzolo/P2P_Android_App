package com.example.pzurzolo.p2p_android_app;

/*
 * This class will do all the work of setting up and managing Bluetooth P2P
 * connections with other devices. It is made up of three threads:
 * 1. A thread that listens for incoming connections.
 * 2. A thread for connecting with a device.
 * 3. A thread for performing data transmissions when connected.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class BluetoothP2PService {

    // Name for the SDP record when creating a server socket
    private static final String NAME_SECURE = "BluetoothP2PSecure";
    private static final String NAME_INSECURE = "BluetoothP2PInsecure";

    // Unique UUID for this app.
    private static final UUID MY_UUID_SECURE =
            UUID.randomUUID();
    private static final UUID MY_UUID_INSECURE =
            UUID.randomUUID();


    // Member fields
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mSecureAcceptThread;
    private AcceptThread mInsecureAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;         // doing nothing
    public static final int STATE_LISTEN = 1;       // listening for incoming connection
    public static final int STATE_CONNECTING = 2;   // initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;    // connected to a remote device


    /*
     * Constructor: Prepares a new BluetoothP2P session
     */
    public BluetoothP2PService(Context context, Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }


    /*
     * Set the current state of the P2P connection.
     */
    private synchronized void setState(int state) {
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(Constants.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }


    /*
     * Return the current connection state.
     */
    public synchronized int getState() { return mState; }


    /*
     * Start the P2P service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
    public synchronized void start() {

        // Cancel any thread attempting to make a connection
        if(mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if(mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        setState(STATE_LISTEN);

        // Start the thread to listen on a BluetoothServerSocket
        if(mSecureAcceptThread == null) {
            mSecureAcceptThread = new AcceptThread(true);
            mSecureAcceptThread.start();
        }
        if(mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread(false);
            mInsecureAcceptThread.start();
        }
    }


    /*
     * Start the ConnectThread to initiate a connection to a Peer.
     */
    public synchronized void connect(BluetoothDevice device, boolean secure) {

        // Cancel any thread attempting to make a connection
        if(mState == STATE_CONNECTING) {
            if(mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if(mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread =  new ConnectThread(device, secure);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }


    /*
     * Start the ConnectedThread to begin managing a Bluetooth connection
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device,
                                       final String socketType) {

        // Cancel the thread that completed the connection
        if(mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if(mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Cancel the accept thread because we only want to connect to one device
        if(mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }
        if(mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket, socketType);
        mConnectedThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(Constants.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        setState(STATE_CONNECTED);
    }
}
