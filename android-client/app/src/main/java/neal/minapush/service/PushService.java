package neal.minapush.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import neal.minapush.push.PushEventListener;
import neal.minapush.push.PushManager;

public class PushService extends Service{

    PushManager pushManager=PushManager.getInstance();

    @Override
    public void onCreate() {
        System.out.println("service start"+android.os.Process.myPid()+'-'+android.os.Process.myTid());
        super.onCreate();
        pushManager.openPush();
        pushManager.setPushEventListener(new PushEventListener() {
            @Override
            public void onPushConnected() {
                System.out.println("service push open"+android.os.Process.myPid()+'-'+android.os.Process.myTid());

            }

            @Override
            public void onPushExceptionCaught() {
                System.out.println("service push exception"+android.os.Process.myPid()+'-'+android.os.Process.myTid());

            }

            @Override
            public void onPushMessageSent() {
                System.out.println("service push sent"+android.os.Process.myPid()+'-'+android.os.Process.myTid());

            }

            @Override
            public void onPushMessageReceived() {

            }

            @Override
            public void onPushDisConnected() {
                System.out.println("service push close"+android.os.Process.myPid()+'-'+android.os.Process.myTid());

            }
        });

        System.out.println(pushManager.Connect());
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("service start command"+android.os.Process.myPid()+'-'+android.os.Process.myTid());
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        pushManager.disConnect();
    }


}
