package com.irobotlabs.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class CommThread extends Thread {
    private BluetoothSocket mSocket;
    private InputStream mIstream;
    private OutputStream mOstream;
    private Handler mHandler;
    private String mMac;
    private String mDeviceName;    
    private BluetoothAdapter mBluetoothAdapter;

    public CommThread(BluetoothAdapter adapter,  Handler handler,String Mac,String deviceName) {
        this.mHandler = handler;
        this.mMac = Mac;
        this.mDeviceName = deviceName;
        this.mBluetoothAdapter = adapter;
    }

    public void run() {
                if (mBluetoothAdapter == null)
                        return;

                Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
                BluetoothDevice device = null;
                for (BluetoothDevice curDevice : devices) {
                        if (curDevice.getName().matches(mDeviceName)) {
                                device = curDevice;
                                break;
                        }
                }
                if (device == null)
                        device = mBluetoothAdapter.getRemoteDevice(mMac);

                try {
                        mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        mSocket.connect();
                } catch (IOException e) {
                        mSocket = null;
                }
                if (mSocket == null) return;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = mSocket.getInputStream();
            tmpOut = mSocket.getOutputStream();
        } catch (IOException e) { }

        mIstream = tmpIn;
        mOstream = tmpOut;
       
       

        StringBuffer sb = new StringBuffer();
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        String message;
        HashMap<String, String> hm;
               
        while (true) {
            try {
                // Read from the InputStream
                bytes = mIstream.read(buffer);
                Log.d("iSTREAM", "...Data from arduino");
                sb.append(new String(buffer, 0, bytes));
                int startLineIndex = sb.indexOf("HE");
            	int endOfLineIndex = sb.indexOf("/HE");	
                if (endOfLineIndex > 1 && startLineIndex < endOfLineIndex) {
                    message = sb.substring(startLineIndex+2	, endOfLineIndex);
                       sb.replace(0 ,endOfLineIndex+2, "");
                       hm = new HashMap<String, String>();
                       hm.put("state", message);
                       mHandler.obtainMessage(0x2a, hm).sendToTarget();
                }
            } catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main Activity to send data to the remote device */
    public void write(String message) {
    	Log.d("WRITE", "...Data to send: " + message + "...");
    	byte[] msgBuffer = message.getBytes();
    	try {
            mOstream.write(msgBuffer);
        } catch (IOException e) {
            Log.d("WRITE", "...Error data send: " + e.getMessage() + "...");     
          }
    }

    /* Call this from the main Activity to shutdown the connection */
    public void cancel() {
        try {
                if (mSocket != null)
                        mSocket.close();
        } catch (IOException e) { }
    }
}

