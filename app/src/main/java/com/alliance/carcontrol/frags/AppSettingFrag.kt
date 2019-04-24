package com.alliance.carcontrol.frags

import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.alliance.carcontrol.R
import com.base.baselib.adapter.CommonAdapter
import com.base.baselib.adapter.ViewHolder
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

    private val list_icon_system = listOf(
        R.mipmap.list_icon_connect, R.mipmap.list_icon_general_purpose,
        R.mipmap.list_icon_common, R.mipmap.list_icon_music
    )
    private val list_text_system = listOf(
        R.string.connect, R.string.common,
        R.string.mobile_connect, R.string.audio
    )
    private val list_icon_car = listOf(
        R.mipmap.door_and_window_control, R.mipmap.ambient_light, R.mipmap.driving_assistance,
        R.mipmap.door_tail_control, R.mipmap.seat,R.mipmap.mobile_phone_wireless_charging,
        R.mipmap.outside_video, R.mipmap.outside_light,R.mipmap.more
    )
    private val list_text_car = listOf(
        R.string.doors_and_windows_control, R.string.atmosphere_lamp, R.string.driving_assistance_system_control,
        R.string.tailgate_control, R.string.chair_setting, R.string.wireless_charging_of_Mobile_setting,
        R.string.out_mirror_light_setting, R.string.out_light_setting,R.string.others
    )

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
                    recycler_setting_system.visibility = View.VISIBLE
                    recycler_setting_car.visibility = View.GONE
                    iv_tab_1.visibility = View.VISIBLE
                    iv_tab_2.visibility = View.INVISIBLE
                    rb_system.startAnimation(mAnimationlarge)
                    rb_car.startAnimation(mAnimationOriginalSize)
                }
                R.id.rb_car -> {
                    recycler_setting_system.visibility = View.GONE
                    recycler_setting_car.visibility = View.VISIBLE
                    iv_tab_2.visibility = View.VISIBLE
                    iv_tab_1.visibility = View.INVISIBLE
                    rb_car.startAnimation(mAnimationlarge)
                    rb_system.startAnimation(mAnimationOriginalSize)
                }
            }
        }

        rg_tab.check(R.id.rb_system)


        recycler_setting_system.layoutManager = GridLayoutManager(activity, 2)

        recycler_setting_system.adapter =
            object : CommonAdapter<Int>(activity, list_text_system, R.layout.item_setting) {
                override fun convert(holder: ViewHolder, t: MutableList<Int>) {
                    val iv_icon = holder.getView<ImageView>(R.id.iv_icon)
                    val tv_text = holder.getView<TextView>(R.id.tv_text)

                    iv_icon.setImageResource(list_icon_system[holder.realPosition])
                    tv_text.setText(list_text_system[holder.realPosition])
                }
            }

        recycler_setting_car.layoutManager = GridLayoutManager(activity, 3)

        recycler_setting_car.adapter = object : CommonAdapter<Int>(activity, list_text_car, R.layout.item_setting) {
            override fun convert(holder: ViewHolder, t: MutableList<Int>) {
                val iv_icon = holder.getView<ImageView>(R.id.iv_icon)
                val tv_text = holder.getView<TextView>(R.id.tv_text)

                iv_icon.setImageResource(list_icon_car[holder.realPosition])
                tv_text.setText(list_text_car[holder.realPosition])
            }
        }

    }
}