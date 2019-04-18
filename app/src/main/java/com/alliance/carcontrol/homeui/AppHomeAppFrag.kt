package com.alliance.carcontrol.homeui

import android.view.View
import com.alliance.carcontrol.R
import com.base.baselib.base.BaseFragment
import com.base.event.FragmentBackLast
import kotlinx.android.synthetic.main.app_frag_home_app.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by dhy
 * Date: 2019/4/18
 * Time: 14:38
 * describe:
 */
class AppHomeAppFrag : BaseFragment() {
    override fun attachLayoutRes(): Int {
        return R.layout.app_frag_home_app
    }

    override fun initView(view: View) {
        btn_back.setOnClickListener {
            EventBus.getDefault().post(FragmentBackLast(true))
        }
    }

    override fun lazyLoad() {
    }

}