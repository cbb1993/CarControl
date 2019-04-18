package com.alliance.carcontrol.frags

import android.content.Intent
import android.view.View
import com.alliance.carcontrol.AppLoginActivity
import com.alliance.carcontrol.R
import com.alliance.carcontrol.homeui.AppHomeAirActivity
import com.alliance.carcontrol.homeui.AppHomeAppFrag
import com.alliance.carcontrol.homeui.AppHomeCarFrag
import com.base.baselib.base.BaseFragment
import com.base.event.FragmentBackLast
import kotlinx.android.synthetic.main.app_frag_home.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 斌斌.
 * Date: 2019/4/8
 * Time: 16:36
 * describe: 主页展示车况
 */

class AppHomeFrag : BaseFragment() {

    override fun attachLayoutRes(): Int {
        return R.layout.app_frag_home
    }

    override fun initView(view: View) {
        ll_home_app.setOnClickListener {
            showChildfrag(AppHomeAppFrag())
        }
        ll_home_login.setOnClickListener {
            startActivity(Intent(this@AppHomeFrag.context, AppLoginActivity::class.java))
        }
        ll_home_car.setOnClickListener {
            showChildfrag(AppHomeCarFrag())
        }
        ll_home_air.setOnClickListener {
            startActivity(Intent(this@AppHomeFrag.context, AppHomeAirActivity::class.java))
        }
    }

    private fun showChildfrag(fragment: BaseFragment) {
        childFragmentManager.beginTransaction().replace(R.id.frag_home_child, fragment).commit()
        ll_home_body.visibility = View.GONE
        frag_home_child.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFragBackHome(bean: FragmentBackLast) {
        if (bean.isBacked) {
            ll_home_body.visibility = View.VISIBLE
            frag_home_child.visibility = View.GONE
        }
    }

    override fun lazyLoad() {

    }

    override fun useEventBus(): Boolean {
        return true
    }
}