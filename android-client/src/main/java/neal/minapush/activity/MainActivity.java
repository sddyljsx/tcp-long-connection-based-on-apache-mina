package neal.minapush.activity;

import android.app.Activity;
import android.os.Bundle;

import neal.minapush.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("activity start"+android.os.Process.myPid()+'-'+android.os.Process.myTid());
    }



}
