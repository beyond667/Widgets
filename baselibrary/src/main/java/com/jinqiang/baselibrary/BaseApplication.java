package com.jinqiang.baselibrary;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jinqiang.baselibrary.module.ApplicationModule;

/**
 * @author: jinqiang
 * @time: 2018/3/1 10:48
 * @desc:
 */

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication instance;
    private ApplicationDelegate applicationDelegate;
    private ApplicationComponent applicationComponent;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        applicationDelegate = new ApplicationDelegate();
        applicationDelegate.attachBaseContext(base);
        MultiDex.install(this);


    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();
        ARouter.init(this);
        instance = this;
//        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationDelegate.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        applicationDelegate.onTerminate(this);
    }


}
