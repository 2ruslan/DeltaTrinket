package delta2.system.deltatrinket;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

public class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private final BluetoothAdapter bluetoothAdapter;
    private final String _NAME = "DeltaTrinket";
    private final String TAG = "AcceptThread";

    public boolean isInProc = false;

    BluetoothSocket socket = null;

    public AcceptThread(BluetoothAdapter a) {
        bluetoothAdapter = a;

        BluetoothServerSocket tmp = null;
        try {
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(_NAME, Common._BLUETOOTH_UID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        if (socket.isConnected())
            return;

        isInProc = true;
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }

            if (socket != null) {
                break;
            }
        }
        isInProc = false;
    }

    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}