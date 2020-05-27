package neal.minapush.applicaiton;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import neal.minapush.service.PushService;

/**
 * Created by neal on 2014/12/2.
 */
public class BaseApplication extends Application{
    private static Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("app start"+android.os.Process.myPid()+'-'+android.os.Process.myTid());
        applicationContext=getApplicationContext();
        Intent intent=new Intent(this, PushService.class);
        startService(intent);
    }

    public static Context getAppContext(){
        return applicationContext;
    }
}
