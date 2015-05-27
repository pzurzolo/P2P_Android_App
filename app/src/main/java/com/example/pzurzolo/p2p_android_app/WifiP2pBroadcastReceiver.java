package com.example.pzurzolo.p2p_android_app;


import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

public class WifiP2pBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;

    public WifiP2pBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                    MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if the Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //TODO: mActivity.setIsWifiP2pEnabled(true);
            } else {
                //TODO: mActivity.setIsWifiP2pEnabled(false);
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed! Do something here!

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed! Do something here!

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
//TODO:            DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
//TODO:                    .findFragmentById(R.id.frag_list);
//TODO:            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
//TODO:                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
        }
    }
}
