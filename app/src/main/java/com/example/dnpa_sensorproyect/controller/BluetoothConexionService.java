package com.example.dnpa_sensorproyect.controller;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConexionService {
    private static final String TAG="BluetoothConexionService";
    private static final String appName="myAPP";
    private static final UUID MY_UUID_INSECURE= UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private final BluetoothAdapter mBluetoothAdapter;
    Context context;
    private AcceptedThread mInsecureAcceptThread;
    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;

    public BluetoothConexionService(Context context) {
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.context=context;
    }

    private class AcceptedThread extends Thread{
        private final BluetoothServerSocket mmServerSocket;
        @SuppressLint("LongLogTag")
        public AcceptedThread(){
            BluetoothServerSocket tmp = null;
            try{
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName,MY_UUID_INSECURE);
                Log.d(TAG,"Accept thread: Setting up server using: "+MY_UUID_INSECURE);

            }catch (Exception e){

            }
            mmServerSocket=tmp;
        }

        @SuppressLint("LongLogTag")
        public void run(){
            Log.d(TAG, "run: Accept treath running");
            BluetoothSocket socket = null;
            try {
                Log.d(TAG, "run: RFCOM server socket start......");
                socket = mmServerSocket.accept();
                Log.d(TAG, "run: RFCOM server socket accepted conexion");
            }catch (Exception e){
                Log.e(TAG, "AcceptThread: IOExeption: "+e.getMessage());
            }
            if(socket != null){
                connected(socket,device);
            }
            Log.d(TAG, "END AcceptedThread");

        }

        @SuppressLint("LongLogTag")
        public void cancel(){
            Log.d(TAG, "cancel: Canceling AcceptedThread");
            try{
                mmServerSocket.close();
            }catch (Exception e){
                Log.e(TAG, "cancel: close of AcceptedThread serversocket failed"+ e.getMessage());
            }
        }
    }

    private class ConnectThread{
        private BluetoothSocket mmSocket;
        @SuppressLint("LongLogTag")
        public ConnectThread(BluetoothDevice device, UUID uuid){
            Log.d(TAG,"ConnectThread: started");
            mmDevice=device;
            deviceUUID=uuid;
        }
        @SuppressLint("LongLogTag")
        public void run(){
            BluetoothSocket tmp =null;
            Log.i(TAG,"run mConnectThread");
            try {
                Log.d(TAG, "ConnectThread: Trying to create InsecureRFSocket usint UIDD: "+ MY_UUID_INSECURE);
                tmp=mmDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            }catch (Exception e){
                Log.e(TAG, "ConnectThread: No se pudo crear InSecureSocket"+e.getMessage());
            }
            mmSocket=tmp;
            mBluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
                Log.d(TAG, "ConnectThread: conectado");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    mmSocket.close();
                    Log.d(TAG,"run: Close socket");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Log.e(TAG, "mConnectedThread: run: Unable to close conection socket"+ ex.getMessage());
                }
            }
            connected(mmSocket,mmDevice);
        }

        public void cancel(){
            try{
                mmSocket.close();
            }catch (Exception e){

            }
        }
    }

    public synchronized void start(){
        if(mConnectThread!=null){
            mConnectThread.cancel();
            mConnectThread=null;
        }
        if(mInsecureAcceptThread==null){
            mInsecureAcceptThread=new AcceptedThread();
            mInsecureAcceptThread.start();
        }
    }

    
}
