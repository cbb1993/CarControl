package com.base.baselib.mvvm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.view.View;
import com.base.baselib.base.BaseFragment;
import com.base.baselib.utils.TUtil;
import com.base.lib.mvvm.BaseViewModel;
import org.jetbrains.annotations.NotNull;


public abstract class BaseLifecycleFragment<T extends BaseViewModel> extends BaseFragment {

    protected T mViewModel;

    @Override
    public void initView(@NotNull View view) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
    }

    protected <T extends ViewModel> T VMProviders(BaseFragment
                                                          fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);

    }

}
