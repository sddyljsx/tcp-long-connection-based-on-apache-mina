package neal.minapush.util;

import android.content.Context;
import android.net.ConnectivityManager;

import neal.minapush.applicaiton.BaseApplication;

/**
 * Created by neal on 2014/12/2.
 */
public class NetworkUtil {
    public static boolean isNetworkConnect(){
        if(BaseApplication.getAppContext()==null){
            return false;
        }
        ConnectivityManager connectivityManager= (ConnectivityManager)BaseApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null) {
            if(connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnected()){
                return true;
            }
        }
        return false;
    }
}
