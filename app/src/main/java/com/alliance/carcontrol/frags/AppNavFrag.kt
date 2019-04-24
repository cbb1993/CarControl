package com.alliance.carcontrol.frags

import android.view.View
import com.alliance.carcontrol.R
import com.base.baselib.base.BaseFragment
import kotlinx.android.synthetic.main.app_frag_guide.*

/**
 * Created by 斌斌.
 * Date: 2019/4/8
 * Time: 16:36
 * describe: 主页展示车况
 */

class AppNavFrag : BaseFragment(){
    override fun attachLayoutRes(): Int {
        return R.layout.app_frag_guide
    }

    override fun initView(view: View) {


        btn_back.setOnClickListener {
            if(rl_content.isShown){
                rl_content.visibility = View.GONE
            }
        }

        btn_break.setOnClickListener {
            setIv1Show(R.mipmap.home_break)
        }
        btn_air.setOnClickListener {
            setIv1Show(R.mipmap.home_air)
        }
        btn_travel.setOnClickListener {
            setIv1Show(R.mipmap.home_travel)
        }
        btn_charging.setOnClickListener {
            setIv1Show(R.mipmap.home_charging)
        }
        btn_power.setOnClickListener {
            setIv1Show(R.mipmap.home_power)
        }



        btn_left.setOnClickListener {
            iv_1.visibility=View.VISIBLE
            iv_2.visibility=View.GONE
        }

        btn_right.setOnClickListener {
            iv_1.visibility=View.GONE
            iv_2.visibility=View.VISIBLE
        }


    }


    fun setIv1Show(res :Int){
        rl_content.visibility = View.VISIBLE
        iv_1.visibility=View.VISIBLE
        iv_2.visibility=View.GONE

        iv_1.setImageResource(res)
    }

    override fun lazyLoad() {

    }
}