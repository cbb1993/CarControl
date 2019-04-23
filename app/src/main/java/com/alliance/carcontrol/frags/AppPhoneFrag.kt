package com.alliance.carcontrol.frags

import android.support.v4.app.Fragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.alliance.carcontrol.R
import com.alliance.carcontrol.phone.PhoneCallFrag
import com.base.baselib.base.BaseFragment
import kotlinx.android.synthetic.main.app_frag_phone.*

/**
 * Created by 斌斌.
 * Date: 2019/4/8
 * Time: 16:36
 * describe: 主页展示其他app
 */

class AppPhoneFrag : BaseFragment(){
    private lateinit var mAnimationlarge: Animation  //放大
    private lateinit var mAnimationOriginalSize: Animation //恢复原来大小

    override fun attachLayoutRes(): Int {
        return R.layout.app_frag_phone
    }

    override fun initView(view: View) {

    }

    override fun lazyLoad() {
        mAnimationlarge = AnimationUtils.loadAnimation(activity, R.anim.app_scale_large_1_3)
        mAnimationOriginalSize = AnimationUtils.loadAnimation(activity, R.anim.app_scale_large_normal)

        rg_tab.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_call -> {
                    iv_tab_1.visibility = View.VISIBLE
                    iv_tab_2.visibility = View.INVISIBLE
                    iv_tab_3.visibility = View.INVISIBLE
                    rb_call.startAnimation(mAnimationlarge)
                    rb_contact.startAnimation(mAnimationOriginalSize)
                    rb_bluetooth.startAnimation(mAnimationOriginalSize)
                    setIndexSelected(0)
                }
                R.id.rb_contact ->{
                    iv_tab_2.visibility = View.VISIBLE
                    iv_tab_1.visibility = View.INVISIBLE
                    iv_tab_3.visibility = View.INVISIBLE
                    rb_call.startAnimation(mAnimationOriginalSize)
                    rb_contact.startAnimation(mAnimationlarge)
                    rb_bluetooth.startAnimation(mAnimationOriginalSize)
                    setIndexSelected(1)
                }
                R.id.rb_bluetooth ->{
                    iv_tab_2.visibility = View.INVISIBLE
                    iv_tab_1.visibility = View.INVISIBLE
                    iv_tab_3.visibility = View.VISIBLE
                    rb_call.startAnimation(mAnimationOriginalSize)
                    rb_contact.startAnimation(mAnimationOriginalSize)
                    rb_bluetooth.startAnimation(mAnimationlarge)
                    setIndexSelected(2)
                }
            }
        }

        rg_tab.check(R.id.rb_call)


        val phoneCallFrag = PhoneCallFrag()
        val phoneCallFrag2 = PhoneCallFrag()
        val phoneCallFrag3 = PhoneCallFrag()
        mFragments = listOf(phoneCallFrag, phoneCallFrag2, phoneCallFrag3)


        val ft = activity?.supportFragmentManager?.beginTransaction()
        ft?.add(R.id.phone_main_content, mFragments[mIndex])?.commit()
    }



    private var mIndex = 0
    private lateinit var mFragments: List<Fragment>
    private fun setIndexSelected(index: Int) {
        if (mIndex == index) {
            return
        }
        val ft = activity?.supportFragmentManager?.beginTransaction()
        ft?.hide(mFragments[mIndex])
//        //判断是否添加
        if (!mFragments[index].isAdded) {
            ft?.add(R.id.phone_main_content, mFragments[index])?.show(mFragments[index])
        } else {
            ft?.show(mFragments[index])
        }

        ft?.commit()
        //再次赋值
        mIndex = index

    }
}