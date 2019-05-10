package com.alliance.carcontrol

import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.alliance.carcontrol.frags.*
import com.base.baselib.base.BaseActivity
import com.powershare.atccev.ui.main.fragment.MainMapFragment
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
    private var mChecked = 2
    private lateinit var mAnimationlarge: Animation  //放大
    private lateinit var mAnimationOriginalSize: Animation //恢复原来大小
    override fun initViews() {
        super.initViews()
        mAnimationlarge = AnimationUtils.loadAnimation(this, R.anim.app_scale_large)
        mAnimationOriginalSize = AnimationUtils.loadAnimation(this, R.anim.app_scale_original)
        initFragment()
        mAnimationlarge.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                setIndexSelected(mChecked)
            }

        })
//        startActivity(Intent(this, MapTestActivity::class.java))
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            ),
            1
        )
    }

    private fun initFragment() {

        val appPhoneFrag = AppPhoneFrag()
        val appMusicFrag = AppMusicFrag()
        val appHomeFrag = AppHomeFrag()
        val appGuideFrag = MainMapFragment().setmParentContainer(R.id.main_content) as MainMapFragment

        val appSettingFrag = AppSettingFrag()

        mFragments = listOf(appPhoneFrag, appMusicFrag, appHomeFrag, appGuideFrag, appSettingFrag)

        rb_phone.setOnClickListener {
            if (!rb_phone.isActivated) {
                rb_phone.isActivated = !rb_phone.isActivated
                rb_phone.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                if (rb_music.isActivated) {
                    rb_music.startAnimation(mAnimationOriginalSize)
                    rb_music.isActivated = false
                }
                if (rb_home.isActivated) {
                    rb_home.startAnimation(mAnimationOriginalSize)
                    rb_home.isActivated = false
                }
                if (rb_guide.isActivated) {
                    rb_guide.startAnimation(mAnimationOriginalSize)
                    rb_guide.isActivated = false
                }
                if (rb_setting.isActivated) {
                    rb_setting.startAnimation(mAnimationOriginalSize)
                    rb_setting.isActivated = false
                }
                mChecked = 0
            }
        }
        rb_music.setOnClickListener {
            if (!rb_music.isActivated) {
                rb_music.isActivated = !rb_music.isActivated
                rb_music.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                if (rb_phone.isActivated) {
                    rb_phone.startAnimation(mAnimationOriginalSize)
                    rb_phone.isActivated = false
                }
                if (rb_home.isActivated) {
                    rb_home.startAnimation(mAnimationOriginalSize)
                    rb_home.isActivated = false
                }
                if (rb_guide.isActivated) {
                    rb_guide.startAnimation(mAnimationOriginalSize)
                    rb_guide.isActivated = false
                }
                if (rb_setting.isActivated) {
                    rb_setting.startAnimation(mAnimationOriginalSize)
                    rb_setting.isActivated = false
                }
                mChecked = 1
            }
        }
        rb_home.setOnClickListener {
            if (!rb_home.isActivated) {
                rb_home.isActivated = !rb_home.isActivated
                rb_home.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                if (rb_phone.isActivated) {
                    rb_phone.startAnimation(mAnimationOriginalSize)
                    rb_phone.isActivated = false
                }
                if (rb_music.isActivated) {
                    rb_music.startAnimation(mAnimationOriginalSize)
                    rb_music.isActivated = false
                }
                if (rb_guide.isActivated) {
                    rb_guide.startAnimation(mAnimationOriginalSize)
                    rb_guide.isActivated = false
                }
                if (rb_setting.isActivated) {
                    rb_setting.startAnimation(mAnimationOriginalSize)
                    rb_setting.isActivated = false
                }
                mChecked = 2
            }
        }
        rb_guide.setOnClickListener {
            if (!rb_guide.isActivated) {
                rb_guide.isActivated = !rb_guide.isActivated
                rb_guide.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                if (rb_phone.isActivated) {
                    rb_phone.startAnimation(mAnimationOriginalSize)
                    rb_phone.isActivated = false
                }
                if (rb_music.isActivated) {
                    rb_music.startAnimation(mAnimationOriginalSize)
                    rb_music.isActivated = false
                }
                if (rb_home.isActivated) {
                    rb_home.startAnimation(mAnimationOriginalSize)
                    rb_home.isActivated = false
                }
                if (rb_setting.isActivated) {
                    rb_setting.startAnimation(mAnimationOriginalSize)
                    rb_setting.isActivated = false
                }
                mChecked = 3
            }
        }
        rb_setting.setOnClickListener {
            if (!rb_setting.isActivated) {
                rb_setting.isActivated = !rb_setting.isActivated
                rb_setting.startAnimation(mAnimationlarge)
                //关闭其他按钮动画
                if (rb_phone.isActivated) {
                    rb_phone.startAnimation(mAnimationOriginalSize)
                    rb_phone.isActivated = false
                }
                if (rb_music.isActivated) {
                    rb_music.startAnimation(mAnimationOriginalSize)
                    rb_music.isActivated = false
                }
                if (rb_home.isActivated) {
                    rb_home.startAnimation(mAnimationOriginalSize)
                    rb_home.isActivated = false
                }
                if (rb_guide.isActivated) {
                    rb_guide.startAnimation(mAnimationOriginalSize)
                    rb_guide.isActivated = false
                }
                mChecked = 4
            }
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.main_content, mFragments[mIndex]).commit()

        if (!rb_home.isActivated) {
            rb_home.isActivated = !rb_home.isActivated
            rb_home.startAnimation(mAnimationlarge)
            mChecked = 2
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
