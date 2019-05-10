package com.alliance.carcontrol

import android.widget.Toast
import com.base.baselib.base.BaseActivity
import com.base.baselib.bean.LoginRequestBean
import com.base.baselib.net.ThrowableUtils
import com.base.baselib.net.httploader.LoginLoader
import com.base.baselib.utils.*
import com.google.gson.Gson
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_app_login.*

class AppLoginActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_app_login
    }

    override fun initViews() {
        iv_back.setOnClickListener {
            this@AppLoginActivity.finish()
        }
        btn_login.setOnClickListener {
            if (et_login_phone.text == null || et_login_phone.text.length <= 0) {
                Toast.makeText(this@AppLoginActivity, "用户名不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (et_login_password.text == null || et_login_password.text.length <= 0) {
                Toast.makeText(this@AppLoginActivity, "密码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //创建bean入参数据
            val loginRequestBean = LoginRequestBean()
            val dataBean = LoginRequestBean.DataBean()
            val attributesBean = LoginRequestBean.DataBean.AttributesBean()
            attributesBean.phoneNum = et_login_phone.text.toString()
            attributesBean.password = MD5Util.stringToMD5(et_login_password.text.toString())
            attributesBean.deviceNo = DeviceIdFactory.getInstance(this@AppLoginActivity).deviceUuid
            dataBean.attributes = attributesBean
            loginRequestBean.data = dataBean
            var dialog = DialogUtils.createLoadingDialog(this@AppLoginActivity, "登陆中")
            //调用方法请求
            LoginLoader().getLoginInFo(loginRequestBean).subscribe({
                dialog.cancel()
                SharedPreferencesUtils.addData("accessToken", it?.accessToken)
                SharedPreferencesUtils.addData("refreshToken", it?.refreshToken)
                this@AppLoginActivity.finish()
            }, {
                ThrowableUtils.ThrowableEnd(it, dialog)
            })
        }
    }

}
