package com.base.baselib.base

import android.content.Context
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import com.base.baselib.BaseApplication
import com.base.baselib.R
import com.base.baselib.constant.Constant
import com.base.baselib.utils.PreferenceUtil
import com.base.data.dao.DaoSession
import com.base.event.NetworkChangeEvent
import com.base.baselib.receiver.NetworkChangeReceiver
import com.readystatesoftware.systembartint.SystemBarTintManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class BaseActivity : AppCompatActivity() {

     /**
      * 缓存上一次的网络状态
      */
     protected var hasNetwork: Boolean by PreferenceUtil(Constant.HAS_NETWORK_KEY, true)

     /**
      * 网络状态变化的广播
      */
     var mNetworkChangeReceiver: NetworkChangeReceiver? = null

     /**
      * 提示View 有无网络
      */
     lateinit var mTipView: View
     lateinit var mWindowManager: WindowManager
     lateinit var mLayoutParams: WindowManager.LayoutParams


    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //状态栏
        initStatusBar(getStatusBarColor())
        // 获取布局id
        setContentView(getLayoutId())

        if(useEventBus()){
            EventBus.getDefault().register(this)
        }

        ARouter.getInstance().inject(this)

        initTipView()
        initViews()
    }

     override fun onResume() {
         // 动态注册网络变化广播
         val filter = IntentFilter()
         filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
         mNetworkChangeReceiver = NetworkChangeReceiver()
         registerReceiver(mNetworkChangeReceiver, filter)
         super.onResume()
     }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param colorId
     */
    private fun initStatusBar(colorId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(colorId)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            setTranslucentStatus()
            val tintManager = SystemBarTintManager(this)
            tintManager.isStatusBarTintEnabled = true
            tintManager.setStatusBarTintResource(colorId)
        }
    }

     /**
      * 初始化 TipView
      */
     private fun initTipView() {
         mTipView = layoutInflater.inflate(R.layout.layout_network_tip, null)
         mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
         mLayoutParams = WindowManager.LayoutParams(
             ViewGroup.LayoutParams.MATCH_PARENT,
             ViewGroup.LayoutParams.WRAP_CONTENT,
             WindowManager.LayoutParams.TYPE_APPLICATION,
             WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
             PixelFormat.TRANSLUCENT)
         mLayoutParams.gravity = Gravity.TOP
         mLayoutParams.x = 0
         mLayoutParams.y = 0
         mLayoutParams.windowAnimations = R.style.anim_float_view // add animations
     }



    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        hasNetwork = event.isConnected
        checkNetwork(event.isConnected)
    }

    private fun checkNetwork(isConnected: Boolean) {
            if (isConnected) {
                if (mTipView != null && mTipView.parent != null) {
                    mWindowManager.removeView(mTipView)
                }
            } else {
                if (mTipView.parent == null) {
                    mWindowManager.addView(mTipView, mLayoutParams)
                }
            }
    }


     /**
      * 初始化views
      *
      */
     open fun initViews(){}


    abstract fun getLayoutId(): Int

    open fun getStatusBarColor(): Int {
        return R.color.statusBar
    }



   private fun setTranslucentStatus() {
        val winParams = window.attributes;
        winParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        window.attributes = winParams
    }

    override fun onPause() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver)
            mNetworkChangeReceiver = null
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(useEventBus()){
            EventBus.getDefault().unregister(this)
        }
    }


    open fun navigation(path:String,v:View){
        var compat = ActivityOptionsCompat.
        makeScaleUpAnimation(v, v.width / 2, v.height / 2, 0, 0);

        ARouter.getInstance().build(path).withOptionsCompat(compat).navigation()
    }

    fun getDaoSession() :DaoSession{
       return BaseApplication.getDaoSession()
    }
}