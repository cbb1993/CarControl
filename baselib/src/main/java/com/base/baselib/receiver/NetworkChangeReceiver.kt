package com.base.baselib.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.base.baselib.constant.Constant
import com.base.baselib.utils.NetWorkUtil
import com.base.baselib.utils.PreferenceUtil
import com.base.event.NetworkChangeEvent
import org.greenrobot.eventbus.EventBus

/**
 * 网络状态接受广播，监听网络状态
 */
class NetworkChangeReceiver : BroadcastReceiver() {

    /**
     * 缓存上一次的网络状态
     */
    private var hasNetwork: Boolean by PreferenceUtil(Constant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetWorkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
    }

}