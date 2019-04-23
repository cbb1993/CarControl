package com.alliance.carcontrol.phone

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.alliance.carcontrol.R
import com.base.baselib.adapter.CommonAdapter
import com.base.baselib.adapter.ViewHolder
import com.base.baselib.base.BaseFragment
import kotlinx.android.synthetic.main.frag_phone_call.*


/**
 * Created by 斌斌.
 * Date: 2019/4/8
 * Time: 16:36
 * describe: 拨号页面
 */

class PhoneCallFrag : BaseFragment() {
    override fun attachLayoutRes(): Int {
        return R.layout.frag_phone_call
    }

    override fun initView(view: View) {

    }


    private val list = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#")
    private val buffer: StringBuffer = StringBuffer()
    override fun lazyLoad() {
        disableShowSoftInput(ev_number)
        ev_number.requestFocus()
        ev_number.isLongClickable = false

        recycler_number.layoutManager = GridLayoutManager(activity, 3)
        recycler_number.adapter = object : CommonAdapter<String>(activity, list, R.layout.item_number) {
            override fun convert(holder: ViewHolder, t: MutableList<String>) {
                val info = t[holder.realPosition]
                val tv_number = holder.getView<TextView>(R.id.tv_number)
                tv_number.text = info
                holder.getView<View>(R.id.root).setOnClickListener {
                    buffer.append(info)
                    ev_number.setText(buffer.toString())
                    ev_number.setSelection(ev_number.length())
                }
            }
        }
        iv_delete.setOnClickListener {
            if (buffer.isNotEmpty()) {
                var index = ev_number.selectionStart
                if (index > 0) {
                    index -= 1
                    buffer.deleteCharAt(index)
                    ev_number.setText(buffer.toString())
                    ev_number.setSelection(index)
                }
            }
        }


        recycler_people.layoutManager = LinearLayoutManager(activity)
        recycler_people.adapter = object :CommonAdapter<String>(activity, list,R.layout.item_people){
            override fun convert(holder: ViewHolder?, t: MutableList<String>?) {
            }
        }

    }

    private fun disableShowSoftInput(editText: EditText) {
        try {
            val cls: Class<EditText> = EditText::class.java
            val method = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.java)
            method.isAccessible = true
            method.invoke(editText, false)
        } catch (e: Exception) {
        }
    }


}