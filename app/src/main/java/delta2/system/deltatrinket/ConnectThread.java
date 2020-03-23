package delta2.system.deltatrinket;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;


public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final BluetoothAdapter bluetoothAdapter;

    public ConnectThread(BluetoothDevice device, BluetoothAdapter a) {
        BluetoothSocket tmp = null;
        mmDevice = device;
        bluetoothAdapter = a;

        try {
            tmp = device.createRfcommSocketToServiceRecord(Common._BLUETOOTH_UID);
        } catch (IOException e) {
         //   L.log.error("", e);
        }
        mmSocket = tmp;
    }

    public void run() {
        bluetoothAdapter.cancelDiscovery();

        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                //L.log.error("", closeException);
            }
            return;
        }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            //L.log.error("", e);
        }
    }
}