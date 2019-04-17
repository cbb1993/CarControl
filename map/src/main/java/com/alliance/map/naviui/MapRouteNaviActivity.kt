package com.alliance.map.naviui

import android.os.Bundle
import com.alliance.map.R
import com.amap.api.navi.AMapNavi
import com.amap.api.navi.AMapNaviViewListener
import com.amap.api.navi.enums.NaviType
import com.base.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_map_route_navi.*

class MapRouteNaviActivity : BaseActivity(), MapNaviListener, AMapNaviViewListener {


    private var mAMapNavi: AMapNavi? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_map_route_navi
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        map_route_navi.lazyNextTurnTipView = map_route_nttv
        map_route_navi.onCreate(savedInstanceState)
        map_route_navi.setAMapNaviViewListener(this)

        mAMapNavi = AMapNavi.getInstance(application)
        mAMapNavi?.addAMapNaviListener(this)
        mAMapNavi?.setUseInnerVoice(true)
        mAMapNavi?.setEmulatorNaviSpeed(60)
        val gps = intent.getBooleanExtra("gps", false)
        if (gps) {
            mAMapNavi?.startNavi(NaviType.GPS)
        } else {
            mAMapNavi?.startNavi(NaviType.EMULATOR)
        }
//        val naviViewOptions = map_route_navi.viewOptions
//        naviViewOptions.isLayoutVisible = false
//        map_route_navi.viewOptions = naviViewOptions
    }

    override fun onResume() {
        super.onResume()
        map_route_navi.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        map_route_navi.onDestroy()
        mAMapNavi?.stopNavi()
        mAMapNavi?.removeAMapNaviListener(this)
    }

    override fun onNaviTurnClick() {
    }

    override fun onScanViewButtonClick() {
    }

    override fun onLockMap(p0: Boolean) {
    }

    override fun onMapTypeChanged(p0: Int) {
    }

    override fun onNaviCancel() {
        finish()
    }

    override fun onNaviViewLoaded() {
    }

    override fun onNaviBackClick(): Boolean {
        return false
    }

    override fun onNaviMapMode(p0: Int) {
    }

    override fun onNextRoadClick() {
    }

    override fun onNaviViewShowMode(p0: Int) {
    }

    override fun onNaviSetting() {
    }
}
