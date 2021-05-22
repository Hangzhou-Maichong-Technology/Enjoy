package com.mc.enjoy;

import android.app.Application;
import android.util.Log;

import com.mc.enjoyutil.EnjoyUtil;

/**
 * @author Woong on 1/30/21
 * @website http://woong.cn
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("App", "is EnjoySDK support ? == " + EnjoyUtil.isEnjoySupport());
    }
}
