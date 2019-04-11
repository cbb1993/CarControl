package com.alliance.carcontrol.frags

import android.support.annotation.DrawableRes
import android.support.annotation.RawRes
import android.view.View
import com.alliance.carcontrol.R
import com.base.baselib.base.BaseFragment
import kotlinx.android.synthetic.main.app_frag_music.*

/**
 * Created by 斌斌.
 * Date: 2019/4/8
 * Time: 16:36
 * describe: 主页展示音乐
 */

class AppMusicFrag : BaseFragment(){
    override fun attachLayoutRes(): Int {
        return R.layout.app_frag_music
    }

    override fun initView(view: View) {
        btn_mu_back.setOnClickListener {
            setIvMiMenubg(R.mipmap.music_menu)
        }
        btn_local_radio.setOnClickListener {
            setIvMibg(R.mipmap.music_local_radio)
        }
        btn_web_radio.setOnClickListener {
            setIvMibg(R.mipmap.music_web_radio)
        }
    }

    fun setIvMiMenubg(@DrawableRes int: Int){
        btn_local_radio.visibility = View.VISIBLE
        btn_web_radio.visibility = View.VISIBLE
        iv_mu_bg.setImageResource(int)
    }
    fun setIvMibg(@DrawableRes int: Int){
        btn_local_radio.visibility = View.GONE
        btn_web_radio.visibility = View.GONE
        iv_mu_bg.setImageResource(int)
    }

    override fun lazyLoad() {

    }
}