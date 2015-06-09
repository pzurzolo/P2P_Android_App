package com.example.pzurzolo.p2p_android_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class MainActivity extends ActionBarActivity {

    private final IntentFilter mIntentFilter = new IntentFilter();
    private WifiP2pManager mManager;
    private Channel mChannel;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding various actions to the IntentFilter...
        // 1. Change in the Wi-Fi P2P status.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // 2. Change in the list of available Peers.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // 3. State of Wi-Fi P2P connectivity changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // 4. Device's details have changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);


        // Adding listeners to the buttons.
        Button mConnectToPeerButton = (Button) findViewById(R.id.connect_to_peer);
        mConnectToPeerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptConnectToPeer();
            }
        });

        Button mViewAllItemsButton = (Button) findViewById(R.id.view_all_items);
        mViewAllItemsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAllItems();
            }
        });


        // Now let's initiate Peer Discovery
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank.  Code for peer discovery goes in the
                // onReceive method, detailed below.
            }
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
                //TODO: Alert the user!
            }
        });
    }

    protected void onResume() {
        super.onResume();
        mReceiver = new WifiP2pBroadcastReceiver(mManager, mChannel, this);
        registerReceiver(mReceiver, mIntentFilter);
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void attemptConnectToPeer() {
        //TODO: Add code to search for and connect to a Peer.
        //TODO: Try to use Fragments
    }

    public void viewAllItems() {
        //TODO: Add code to view all held items
        //TODO: Try to use Fragments
    }
}
