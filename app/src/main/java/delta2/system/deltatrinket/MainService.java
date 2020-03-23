package delta2.system.deltatrinket;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MainService extends Service {
    static MainService instance;

    private BlutoothManager manager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        instance = this;

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        startForeground(R.drawable.ic_notify_proc, "delta2system", 1100);
        manager = new BlutoothManager();

    }

    @Override
    public void onDestroy() {
        manager.OnDestroy();
    }

    public static void stopApp(){
        if (instance != null)
            instance.stopSelf();
    }

    protected  void startForeground(int ico, String title, int notifyId) {


        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(ico)
                .setContentTitle(title)
                .setContentText("")
                .setOnlyAlertOnce(true)
                .setOngoing(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(createNotificationChannel("my_service", "My Background Service"));
        }

        Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        }
        else{
            notification = builder.getNotification();
        }


        notification.contentIntent = PendingIntent.getActivity(this,
                0, new Intent(getApplicationContext(), MainService.class)
                , PendingIntent.FLAG_UPDATE_CURRENT);


        startForeground(notifyId, notification);
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName){
        NotificationChannel chan = new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }
}
