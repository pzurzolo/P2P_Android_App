package com.example.pzurzolo.p2p_android_app;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/*
 * This fragment controls Bluetooth to communicate with other devices.
 */
public class BluetoothP2PFragment extends Fragment {

    private static final String TAG = "BluetoothP2PFragment";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Layout Views


    // Name of the connected device
    private String mConnectedDeviceName = null;

    // Array adapter for the ItemPackage array
    private ArrayAdapter<ItemPackage> mItemPackageArrayAdapter;

    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;

    // Member object for the P2P service
    private BluetoothP2PService mP2PService = null;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if(mBluetoothAdapter == null) {
            FragmentActivity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

    public void onStart() {

        super.onStart();
        // If bluetooth is not on, request that it be enabled.
        // setupP2P() will then be called during onActivityResult
        if(!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the P2P session
        } else if(mP2PService == null) {
            setupP2P();
        }
    }

    public void onDestroy() {

        super.onDestroy();
        if(mP2PService != null) {
            mP2PService.stop();
        }
    }

    public void onResume() {

        super.onResume();

        // Performing this check in onResume() covers the case in which bluetooth was
        // not enabled during onStart(), so we were paused to enable it.
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if(mP2PService != null) {
            // Only if the state is STATE_NONE do we know that we haven't started already
            if(mP2PService.getState() == BluetoothP2PService.STATE_NONE) {
                // Start the Bluetooth P2P service
                mP2PService.start();
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_bt_p2p, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    private void setupP2P() {

    }
}
