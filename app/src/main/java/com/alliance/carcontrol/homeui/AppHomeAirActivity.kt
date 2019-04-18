package com.alliance.carcontrol.homeui

import com.alliance.carcontrol.R
import com.base.baselib.base.BaseActivity
import com.base.event.FragmentBackLast
import kotlinx.android.synthetic.main.activity_frag_home_air.*
import kotlinx.android.synthetic.main.app_frag_home_app.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by dhy
 * Date: 2019/4/18
 * Time: 14:38
 * describe:
 */
class AppHomeAirActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_frag_home_air
    }

    override fun initViews() {
        super.initViews()

        btn_home_air_back.setOnClickListener {
            this@AppHomeAirActivity.finish()
        }
        iv_home_air_volum.drawable.level = 50
    }

}