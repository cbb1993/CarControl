package com.alliance.carcontrol.setting

import android.view.View
import com.alliance.carcontrol.R
import com.base.baselib.base.BaseFragment
import com.base.event.FragmentSettingBack
import kotlinx.android.synthetic.main.frag_connect.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by 坎坎.
 * Date: 2019/5/6
 * Time: 9:52
 * describe:
 */
class CommonFragment : BaseFragment(){
    override fun attachLayoutRes(): Int {
        return R.layout.frag_common
    }

    override fun initView(view: View) {

        tv_back.setOnClickListener {
            EventBus.getDefault().post(FragmentSettingBack())
        }
    }

    override fun lazyLoad() {
    }
}