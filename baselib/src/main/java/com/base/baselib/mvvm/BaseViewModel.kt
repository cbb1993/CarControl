package com.base.lib.mvvm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.base.baselib.mvvm.BaseRepository
import com.base.baselib.utils.TUtil

open class BaseViewModel< T : BaseRepository>(app:Application) : AndroidViewModel(app){
    var mRepository: T? = null

    init {
        mRepository = TUtil.getNewInstance<T>(this, 0)
    }

    override fun onCleared() {
        super.onCleared()
        if (mRepository != null) {
            mRepository?.unDisposable()
        }
    }
}