package com.base.baselib.mvvm;

import android.arch.lifecycle.ViewModelProviders;
import com.base.baselib.base.BaseActivity;
import com.base.baselib.utils.TUtil;
import com.base.lib.mvvm.BaseViewModel;


public abstract class BaseLifecycleActivity<T extends BaseViewModel> extends BaseActivity {

    protected T mViewModel;

    @Override
    public void initViews() {
        super.initViews();
        mViewModel =  ViewModelProviders.of(this).get((Class<T>) TUtil.getInstance(this, 0));
    }

}
