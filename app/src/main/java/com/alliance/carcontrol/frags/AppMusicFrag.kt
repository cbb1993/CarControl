package com.alliance.carcontrol.frags

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.alliance.carcontrol.R
import com.alliance.carcontrol.music.AppMusicLocalFrag
import com.base.baselib.base.BaseFragment
import com.base.event.FragmentBackLast
import com.base.event.MusicFragmentBackLast
import kotlinx.android.synthetic.main.app_frag_home.*
import kotlinx.android.synthetic.main.app_frag_music.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 斌斌.
 * Date: 2019/4/8
 * Time: 16:36
 * describe: 主页展示音乐
 */

class AppMusicFrag : BaseFragment() {
    private lateinit var mAnimationlarge: Animation  //放大
    private lateinit var mAnimationOriginalSize: Animation //恢复原来大小

    override fun attachLayoutRes(): Int {
        return R.layout.app_frag_music
    }

    override fun initView(view: View) {
        mAnimationlarge = AnimationUtils.loadAnimation(activity, R.anim.app_scale_large_1_3)
        mAnimationOriginalSize = AnimationUtils.loadAnimation(activity, R.anim.app_scale_large_normal)
    }

    override fun lazyLoad() {
        rg_music_tab.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_radio -> {
                    iv_radio.visibility = View.VISIBLE
                    iv_music.visibility = View.INVISIBLE
                    iv_pic.visibility = View.INVISIBLE
                    iv_vedio.visibility = View.INVISIBLE
                    rb_radio.startAnimation(mAnimationlarge)
                    rb_music.startAnimation(mAnimationOriginalSize)
                    rb_pic.startAnimation(mAnimationOriginalSize)
                    rb_vedio.startAnimation(mAnimationOriginalSize)
                }
                R.id.rb_music -> {
                    iv_radio.visibility = View.INVISIBLE
                    iv_music.visibility = View.VISIBLE
                    iv_pic.visibility = View.INVISIBLE
                    iv_vedio.visibility = View.INVISIBLE
                    rb_radio.startAnimation(mAnimationOriginalSize)
                    rb_music.startAnimation(mAnimationlarge)
                    rb_pic.startAnimation(mAnimationOriginalSize)
                    rb_vedio.startAnimation(mAnimationOriginalSize)
                }
                R.id.rb_pic -> {
                    iv_radio.visibility = View.INVISIBLE
                    iv_music.visibility = View.INVISIBLE
                    iv_pic.visibility = View.VISIBLE
                    iv_vedio.visibility = View.INVISIBLE
                    rb_radio.startAnimation(mAnimationOriginalSize)
                    rb_music.startAnimation(mAnimationOriginalSize)
                    rb_pic.startAnimation(mAnimationlarge)
                    rb_vedio.startAnimation(mAnimationOriginalSize)
                }
                R.id.rb_vedio -> {
                    iv_radio.visibility = View.INVISIBLE
                    iv_music.visibility = View.INVISIBLE
                    iv_pic.visibility = View.INVISIBLE
                    iv_vedio.visibility = View.VISIBLE
                    rb_radio.startAnimation(mAnimationOriginalSize)
                    rb_music.startAnimation(mAnimationOriginalSize)
                    rb_pic.startAnimation(mAnimationOriginalSize)
                    rb_vedio.startAnimation(mAnimationlarge)
                }
            }
        }
        rg_music_tab.check(R.id.rb_music)
        ll_local_music.setOnClickListener {
            showChildfrag(AppMusicLocalFrag())
        }
    }

    private fun showChildfrag(fragment: BaseFragment) {
        childFragmentManager.beginTransaction().replace(R.id.fl_music_child, fragment).commit()
        fl_music_child.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFragBackHome(bean: MusicFragmentBackLast) {
        if (bean.isBacked) {
            fl_music_child.visibility = View.GONE
        }
    }

    override fun useEventBus(): Boolean {
        return true
    }
}