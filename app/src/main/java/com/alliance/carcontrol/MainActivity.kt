package com.alliance.carcontrol

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.alliance.carcontrol.frags.*
import com.base.baselib.base.BaseActivity
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

    override fun initViews() {
        super.initViews()
        initFragment()
    }


    private fun initFragment() {
        rg_menu.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_phone -> setIndexSelected(0)
                R.id.rb_music -> setIndexSelected(1)
                R.id.rb_home -> setIndexSelected(2)
                R.id.rb_guide -> setIndexSelected(3)
                R.id.rb_setting -> setIndexSelected(4)
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

        rg_menu.check(R.id.rb_home)
        iv_nav.setImageResource(R.mipmap.menu_line_home)

    }

    private fun setIndexSelected(index: Int) {
        if (mIndex == index) {
            return
        }
        when(index){
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

}
