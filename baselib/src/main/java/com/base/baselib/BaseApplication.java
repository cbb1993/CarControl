package com.base.baselib;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.baselib.utils.SystemHelper;
import com.base.data.dao.DaoMaster;
import com.base.data.dao.DaoSession;

public class BaseApplication extends Application {
    public static Application application;

    public static Context instance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initARouter();
        initGreenDao();
    }

    private void initARouter() {
        if (SystemHelper.isDebug(this)) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }


    private static final String DB_NAME = "app.db";
    private static DaoSession mDaoSession;
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        startStrictModeThreadPolicy();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }


    private void startStrictModeThreadPolicy(){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()/*磁盘读取操作检测*/
                .detectDiskWrites()/*检测磁盘写入操作*/
                .detectNetwork() /*检测网络操作*/
                .penaltyLog().build());

    }
}
