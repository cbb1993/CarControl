package com.base.baselib.mvvm

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
/**
*  为viewmodel提供数据
* */
open class BaseRepository{

    // 注册observer 到 CompositeDisposable 中统一管理
    private var mCompositeDisposable: CompositeDisposable? = null

    fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }

    fun unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable?.isDisposed!!) {
            mCompositeDisposable?.clear()
        }
    }

    protected fun sendData(eventKey: Any, t: Any?) {
        sendData(eventKey, null, t)
    }


    protected fun showPageState(eventKey: Any, state: String) {
        sendData(eventKey, state)
    }

    protected fun showPageState(eventKey: Any, tag: String, state: String) {
        sendData(eventKey, tag, state)
    }

     fun sendData(eventKey: Any, tag: String?, t: Any?) {
        LiveBus.getDefault().postEvent(eventKey, tag, t)
    }

}