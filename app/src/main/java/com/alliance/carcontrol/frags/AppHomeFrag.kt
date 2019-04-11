package com.alliance.carcontrol.frags

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.alliance.carcontrol.R
import com.base.baselib.base.BaseFragment
import kotlinx.android.synthetic.main.app_frag_home.*

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
        btn_home_app.setOnClickListener {
            val intentForPackage = this@AppHomeFrag.context?.packageManager?.getLaunchIntentForPackage("com.neusoft.weixindemo")
            if (intentForPackage != null) {
                intentForPackage.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intentForPackage)
            }
        }
    }

    override fun lazyLoad() {

    }
}