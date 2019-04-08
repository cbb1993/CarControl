package com.alliance.carcontrol

import android.os.Bundle
import android.support.v4.app.Fragment
import com.alliance.carcontrol.frags.AppAppsFrag
import com.alliance.carcontrol.frags.AppCarFrag
import com.alliance.carcontrol.frags.AppMusicFrag
import com.alliance.carcontrol.frags.AppSettingFrag
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
            when(checkedId){
                R.id.rb_app -> setIndexSelected(0)
                R.id.rb_music -> setIndexSelected(1)
                R.id.rb_car -> setIndexSelected(2)
                R.id.rb_setting -> setIndexSelected(3)
            }
        }

        val appAppsFrag = AppAppsFrag()
        val appMusicFrag = AppMusicFrag()
        val appCarFrag = AppCarFrag()
        val appSettingFrag = AppSettingFrag()

        mFragments = listOf(appAppsFrag, appMusicFrag, appCarFrag, appSettingFrag)

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.main_content, mFragments[mIndex]).commit()

        rg_menu.check(R.id.rb_car)

    }
    private fun setIndexSelected(index: Int) {
        if (mIndex == index) {
            return
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
