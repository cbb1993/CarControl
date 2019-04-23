package com.alliance.carcontrol.homeui

import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.alliance.carcontrol.R
import com.base.baselib.adapter.CommonAdapter
import com.base.baselib.adapter.ViewHolder
import com.base.baselib.base.BaseFragment
import com.base.event.FragmentBackLast
import com.loc.co.H
import kotlinx.android.synthetic.main.app_frag_home_app.*
import kotlinx.android.synthetic.main.app_frag_home_car.*
import kotlinx.android.synthetic.main.app_frag_home_car_flow.*
import kotlinx.android.synthetic.main.app_frag_home_car_history.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by dhy
 * Date: 2019/4/18
 * Time: 14:38
 * describe:
 */
class AppHomeCarFrag : BaseFragment() {
    private lateinit var mAnimationlarge: Animation  //放大
    private lateinit var mAnimationOriginalSize: Animation //恢复原来大小

    override fun attachLayoutRes(): Int {
        return R.layout.app_frag_home_car
    }

    override fun initView(view: View) {
        car_btn_back.setOnClickListener {
            EventBus.getDefault().post(FragmentBackLast(true))
        }
    }

    override fun lazyLoad() {
        mAnimationlarge = AnimationUtils.loadAnimation(activity, R.anim.app_scale_large_1_3)
        mAnimationOriginalSize = AnimationUtils.loadAnimation(activity, R.anim.app_scale_large_normal)
        rg_car_tab.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_flow -> {
                    iv_flow.visibility = View.VISIBLE
                    iv_history.visibility = View.INVISIBLE
                    rb_flow.startAnimation(mAnimationlarge)
                    rb_history.startAnimation(mAnimationOriginalSize)
                    ll_flow.visibility = View.VISIBLE
                    ll_history.visibility = View.GONE
                }
                R.id.rb_history -> {
                    iv_flow.visibility = View.INVISIBLE
                    iv_history.visibility = View.VISIBLE
                    rb_flow.startAnimation(mAnimationOriginalSize)
                    rb_history.startAnimation(mAnimationlarge)
                    ll_flow.visibility = View.GONE
                    ll_history.visibility = View.VISIBLE
                }
            }
        }
        rg_car_tab.check(R.id.rb_flow)
        val list = listOf(1, 2, 3, 4, 5)
        rv_history.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        rv_history.adapter = object : CommonAdapter<Int>(activity, list, R.layout.app_frag_home_car_item) {
            override fun convert(holder: ViewHolder?, t: MutableList<Int>?) {

            }

        }
    }

}