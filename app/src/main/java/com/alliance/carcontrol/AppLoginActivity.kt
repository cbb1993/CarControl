package com.alliance.carcontrol

import com.base.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_app_login.*

class AppLoginActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_app_login
    }

    override fun initViews() {
        iv_back.setOnClickListener {
            this@AppLoginActivity.finish()
        }
    }

}
