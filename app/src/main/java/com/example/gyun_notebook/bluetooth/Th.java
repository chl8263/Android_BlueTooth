package com.example.gyun_notebook.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Th extends Thread {
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;

    public Th(BluetoothDevice device){
        BluetoothSocket tmp = null;
        bluetoothDevice = device;

        try{
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"));
        } catch (IOException e) {
            bluetoothSocket = tmp;
        }
    }

    @Override
    public void run() {
        super.run();
        try{
            bluetoothSocket.connect();

        } catch (IOException e) {
            e.printStackTrace();
            try {
                bluetoothSocket.close();
            } catch (IOException e1) {
                return;
            }
        }
        byte [] buffer = new byte[1024];
        int bytes;
        OutputStream outputStream = null;
        InputStream inputStream=null;
        byte[] bibi = "hello".getBytes();
        try {
             outputStream= bluetoothSocket.getOutputStream();
             inputStream= bluetoothSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try{
                outputStream.write(bibi);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
