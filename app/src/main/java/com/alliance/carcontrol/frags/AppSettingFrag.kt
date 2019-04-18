package com.alliance.carcontrol.frags

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.alliance.carcontrol.R
import com.base.baselib.base.BaseFragment
import kotlinx.android.synthetic.main.app_frag_setting.*

/**
 * Created by 斌斌.
 * Date: 2019/4/8
 * Time: 16:36
 * describe: 设置
 */

class AppSettingFrag : BaseFragment() {

    private lateinit var mAnimationlarge: Animation  //放大
    private lateinit var mAnimationOriginalSize: Animation //恢复原来大小

    override fun attachLayoutRes(): Int {
        return R.layout.app_frag_setting
    }

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
        mAnimationlarge = AnimationUtils.loadAnimation(activity, R.anim.app_scale_large_1_3)
        mAnimationOriginalSize = AnimationUtils.loadAnimation(activity, R.anim.app_scale_large_normal)

        rg_tab.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_system -> {
                    iv_tab_1.visibility = View.VISIBLE
                    iv_tab_2.visibility = View.INVISIBLE
                    rb_system.startAnimation(mAnimationlarge)
                    rb_car.startAnimation(mAnimationOriginalSize)
                }
                R.id.rb_car ->{
                    iv_tab_2.visibility = View.VISIBLE
                    iv_tab_1.visibility = View.INVISIBLE
                    rb_car.startAnimation(mAnimationlarge)
                    rb_system.startAnimation(mAnimationOriginalSize)
                }
            }
        }

        rg_tab.check(R.id.rb_system)
    }
}