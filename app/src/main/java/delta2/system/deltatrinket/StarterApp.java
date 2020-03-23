package delta2.system.deltatrinket;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class StarterApp extends Activity {

    public final static String _ONLY_SERVICE = "_ONLY_SERVICE";

    private boolean startOnlyService = false;

    private ArrayList<String> allPermission = new ArrayList<>();

    public static void StartApp(Context c){
        Intent i = new Intent(c, StarterApp.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(i);
    }

    public static void StartService(Context c){
        Intent i = new Intent(c, StarterApp.class);
        i.putExtra(_ONLY_SERVICE, true);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter_app);
        startOnlyService = getIntent().getBooleanExtra(_ONLY_SERVICE, false);

        allPermission.add(Manifest.permission.FOREGROUND_SERVICE);
        allPermission.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);
        allPermission.add(Manifest.permission.BLUETOOTH);
        allPermission.add(Manifest.permission.BLUETOOTH_ADMIN);
   //     allPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
   //     allPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);

        checkAllPermission();

    }

    public void checkAllPermission()
    {
        if (allPermission != null && !allPermission.isEmpty()) {
            do {
                String permission = allPermission.get(0);
                allPermission.remove(0);


                int res = this.checkCallingOrSelfPermission(permission);
                if (res != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
                    return;
                }

            } while (!allPermission.isEmpty());
        }

        if (allPermission == null || allPermission.isEmpty())
            go(startOnlyService);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        checkAllPermission();
                    else
                        finish();
                }
            }
        }
    }

    private void go(boolean isService){
        if (isService){
            this.startService(new Intent(this, MainService.class));
        }
        else {
            MainService.stopApp();

            startService(new Intent(this, MainService.class));

            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }
    }
}