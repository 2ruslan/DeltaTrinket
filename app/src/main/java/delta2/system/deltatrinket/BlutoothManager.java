package delta2.system.deltatrinket;

import android.bluetooth.BluetoothAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class BlutoothManager {
    AcceptThread acceptThread;

    public BlutoothManager()
    {
        acceptThread = new AcceptThread(BluetoothAdapter.getDefaultAdapter());

        initTimertask();
    }

    public void OnDestroy(){
        acceptThread.cancel();
    }

    private void chekConnection(){
        if (!acceptThread.isInProc)
            acceptThread.run();
    }

    OnlineTimertask _TimerTask ;
    Timer _Timer;

    private  void initTimertask(){
        _TimerTask = new OnlineTimertask();
        _Timer = new Timer();

        _Timer.schedule(_TimerTask, 0,  10 *1000);
    }

    //----------------------------------------------------------------

    private class OnlineTimertask extends TimerTask {

        @Override
        public void run() {
            try {
                chekConnection();
            }catch (Exception ex)
            {

            }
        }
    }

}
