package com.alliance.carcontrol

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.DebugUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.alliance.carcontrol.frags.*
import com.alliance.map.MapTestActivity
import com.base.baselib.base.BaseActivity
import com.base.baselib.utils.DensityUtil
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by 斌斌.
 * Date: 2019/4/8
 * Time: 16:20
 * describe: 主页
 */
class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private lateinit var mFragments: List<Fragment>
    private var mIndex = 2
    private lateinit var mAnimationlarge: Animation  //放大
    private lateinit var mAnimationOriginalSize: Animation //恢复原来大小
    override fun initViews() {
        super.initViews()
        mAnimationlarge = AnimationUtils.loadAnimation(this, R.anim.app_scale_large)
        mAnimationOriginalSize = AnimationUtils.loadAnimation(this, R.anim.app_scale_original)
        initFragment()
//        startActivity(Intent(this, MapTestActivity::class.java))
    }

    private fun initFragment() {
        rb_phone.setOnClickListener {
            if (!rb_phone.isActivated) {
                rb_phone.isActivated = !rb_phone.isActivated
                rb_phone.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                rb_music.isActivated = false
                rb_music.startAnimation(mAnimationOriginalSize)

                rb_home.isActivated = false
                rb_home.startAnimation(mAnimationOriginalSize)

                rb_guide.isActivated = false
                rb_guide.startAnimation(mAnimationOriginalSize)

                rb_setting.isActivated = false
                rb_setting.startAnimation(mAnimationOriginalSize)
                setIndexSelected(0)
            }
        }
        rb_music.setOnClickListener {
            if (!rb_music.isActivated) {
                rb_music.isActivated = !rb_music.isActivated
                rb_music.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                rb_phone.isActivated = false
                rb_phone.startAnimation(mAnimationOriginalSize)

                rb_home.isActivated = false
                rb_home.startAnimation(mAnimationOriginalSize)

                rb_guide.isActivated = false
                rb_guide.startAnimation(mAnimationOriginalSize)

                rb_setting.isActivated = false
                rb_setting.startAnimation(mAnimationOriginalSize)
                setIndexSelected(1)
            }
        }
        rb_home.setOnClickListener {
            if (!rb_home.isActivated) {
                rb_home.isActivated = !rb_home.isActivated
                rb_home.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                rb_phone.isActivated = false
                rb_phone.startAnimation(mAnimationOriginalSize)

                rb_music.isActivated = false
                rb_music.startAnimation(mAnimationOriginalSize)

                rb_guide.isActivated = false
                rb_guide.startAnimation(mAnimationOriginalSize)

                rb_setting.isActivated = false
                rb_setting.startAnimation(mAnimationOriginalSize)
                setIndexSelected(2)
            }
        }
        rb_guide.setOnClickListener {
            if (!rb_guide.isActivated) {
                rb_guide.isActivated = !rb_guide.isActivated
                rb_guide.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                rb_phone.isActivated = false
                rb_phone.startAnimation(mAnimationOriginalSize)

                rb_music.isActivated = false
                rb_music.startAnimation(mAnimationOriginalSize)

                rb_home.isActivated = false
                rb_home.startAnimation(mAnimationOriginalSize)

                rb_setting.isActivated = false
                rb_setting.startAnimation(mAnimationOriginalSize)
                setIndexSelected(3)
            }
        }
        rb_setting.setOnClickListener {
            if (!rb_setting.isActivated) {
                rb_setting.isActivated = !rb_setting.isActivated
                rb_setting.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                rb_phone.isActivated = false
                rb_phone.startAnimation(mAnimationOriginalSize)

                rb_music.isActivated = false
                rb_music.startAnimation(mAnimationOriginalSize)

                rb_home.isActivated = false
                rb_home.startAnimation(mAnimationOriginalSize)

                rb_guide.isActivated = false
                rb_guide.startAnimation(mAnimationOriginalSize)
                setIndexSelected(4)
            }
        }

        val appPhoneFrag = AppPhoneFrag()
        val appMusicFrag = AppMusicFrag()
        val appHomeFrag = AppHomeFrag()
        val appGuideFrag = AppGuideFrag()
        val appSettingFrag = AppSettingFrag()

        mFragments = listOf(appPhoneFrag, appMusicFrag, appHomeFrag, appGuideFrag, appSettingFrag)

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.main_content, mFragments[mIndex]).commit()

        if (!rb_home.isActivated) {
            rb_home.isActivated = !rb_home.isActivated
            rb_home.startAnimation(mAnimationlarge)
        }
        iv_nav.setImageResource(R.mipmap.menu_line_home)

    }

    private fun setIndexSelected(index: Int) {
        if (mIndex == index) {
            return
        }
        when (index) {
            0 -> iv_nav.setImageResource(R.mipmap.menu_line_phone)
            1 -> iv_nav.setImageResource(R.mipmap.menu_line_music)
            2 -> iv_nav.setImageResource(R.mipmap.menu_line_home)
            3 -> iv_nav.setImageResource(R.mipmap.menu_line_nav)
            4 -> iv_nav.setImageResource(R.mipmap.menu_line_setting)
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.hide(mFragments[mIndex])
//        //判断是否添加
        if (!mFragments[index].isAdded) {
            ft.add(R.id.main_content, mFragments[index]).show(mFragments[index])
        } else {
            ft.show(mFragments[index])
        }

        ft.commit()
        //再次赋值
        mIndex = index

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return true
    }

}
