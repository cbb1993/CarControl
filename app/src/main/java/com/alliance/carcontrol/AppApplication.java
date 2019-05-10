package com.alliance.carcontrol;

import com.base.baselib.BaseApplication;
import com.powershare.atccev.app.CommonApplication;

/**
 * Created by 坎坎.
 * Date: 2019/5/10
 * Time: 16:30
 * describe:
 */
public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonApplication.initATCCEV(this);
    }
}
