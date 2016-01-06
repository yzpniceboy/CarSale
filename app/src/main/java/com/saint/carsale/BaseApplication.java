package com.saint.carsale;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.saint.carsale.utils.Constant;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by yyx on 16/1/5.
 */
public class BaseApplication extends Application {

    private Context appContext;
    /**
     * Activity集合
     */
    private static ArrayList<BaseActivity> activitys = new ArrayList<BaseActivity>();

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        JPushInterface.setDebugMode(Constant.SystemConstant.ISDEBUG);
        JPushInterface.init(appContext);
        CrashReport.initCrashReport(appContext, "900016946", false);
    }

    public Context getAppContext(){
        return appContext;
    }

    /**
     * 添加Activity到ArrayList<Activity>管理集合
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        String className = activity.getClass().getName();
        for (Activity at : activitys) {
            if (className.equals(at.getClass().getName())) {
                activitys.remove(at);
                break;
            }
        }
        activitys.add(activity);
    }

    /**
     * 退出应用程序的时候，手动调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        for (BaseActivity activity : activitys) {
            activity.defaultFinish();
        }
    }

}
