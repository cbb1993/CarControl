package com.alliance.carcontrol.music


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.alliance.carcontrol.R
import com.base.baselib.adapter.CommonAdapter
import com.base.baselib.adapter.ViewHolder
import com.base.baselib.base.BaseFragment
import com.base.event.MusicFragmentBackLast
import kotlinx.android.synthetic.main.frag_phone_call.*
import kotlinx.android.synthetic.main.fragment_app_music_local.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by dhy
 * Date: 2019/4/24
 * Time: 14:38
 * describe:
 */
class AppMusicLocalFrag : BaseFragment() {
    override fun attachLayoutRes(): Int {
        return R.layout.fragment_app_music_local
    }

    private val list = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#")
    override fun initView(view: View) {
        btn_local_back.setOnClickListener {
            EventBus.getDefault().post(MusicFragmentBackLast(true))
        }
    }

    override fun lazyLoad() {
        rv_local_music_list.layoutManager = LinearLayoutManager(activity)
        rv_local_music_list.adapter = object : CommonAdapter<String>(activity, list, R.layout.item_music) {
            override fun convert(holder: ViewHolder?, t: MutableList<String>?) {
            }
        }
    }
}
