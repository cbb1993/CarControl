package com.alliance.carcontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/**
 * Created by 坎坎.
 * Date: 2019/4/10
 * Time: 14:03
 * describe:
 */
public class MyRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs,0);
    }
    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private int res_normal ;
    private int res_check ;

    private void init(Context context, AttributeSet attrs , int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyRadioButton, defStyleAttr, 0);

         res_normal = a.getResourceId(R.styleable.MyRadioButton_src_normal,0);
         res_check = a.getResourceId(R.styleable.MyRadioButton_src_checked,0);
         setRes_normal();
        a.recycle();


        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setRes_check();
                }else {
                    setRes_normal();
                }
            }
        });

    }
    private void setRes_normal(){
        Drawable drawable_top = getResources().getDrawable(res_normal);
        drawable_top.setBounds(0, 0, 80, 80);
        setCompoundDrawables(null,null,drawable_top,null);
    }
    private void setRes_check(){
        Drawable drawable_top = getResources().getDrawable(res_check);
        drawable_top.setBounds(0, 0, 150, 150);
        setCompoundDrawables(null,null,drawable_top,null);

    }
}
