package com.example.gyun_notebook.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothDevice bluetoothDevice = null;
    UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,100);
        }*/
       // new Th().start();

        checkConnected();


    }

    public void checkConnected()
    {
        // true == headset connected && connected headset is support hands free
        int state = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.HEADSET);
        if (state != BluetoothProfile.STATE_CONNECTED)
            return;

        try
        {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(getApplicationContext(), serviceListener, BluetoothProfile.HEADSET);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private BluetoothProfile.ServiceListener serviceListener = new BluetoothProfile.ServiceListener()
    {
        @Override
        public void onServiceDisconnected(int profile)
        {

        }

        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy)
        {
            for (BluetoothDevice device : proxy.getConnectedDevices())
            {
                Log.e("====================", "|" + device.getName() + " | " + device.getAddress() + " | " + proxy.getConnectionState(device) + "(connected = "
                        + BluetoothProfile.STATE_CONNECTED + ")");
                bluetoothDevice = device;
            }
            Log.e("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ","--------"+bluetoothDevice.getName());
            BluetoothAdapter.getDefaultAdapter().closeProfileProxy(profile, proxy);
            new AS(bluetoothDevice,uuid).start();
        }
    };
}
