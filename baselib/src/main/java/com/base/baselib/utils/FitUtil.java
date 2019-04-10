package com.base.baselib.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

public class FitUtil {
    private static float width = 1920;//手动设置为设计图的宽,适配将根据宽为基准,也可以设置高,但是推荐设 置宽,如果不需要px=dp则不设置也行
    private static int dpi = 480;//手动设置设计图的dpi,不知道可以设计图的宽除2测试一下
    private static float nativeWidth = 0;//真实屏幕的宽,不需要手动改
    private static float sRoncompatDennsity;
    private static float sRoncompatScaledDensity;

    /**
     * 在Activity的onCreate中调用,修改该Activity的density,即可完成适配,使用宽高直接使用设计图上px相等的dp值
     *
     * @param activity     需要改变的Activity
     * @param isPxEqualsDp 是否需要设置为设计图上的px直接在xml上写dp值(意思就是不需要自己计算dp值,直接写设计图上的px值,并改单位为dp),但开启后可能需要手动去设置ToolBar的大小,如果不用可以忽略
     */
    public static void autoFit(Activity activity, boolean isPxEqualsDp, final @NonNull Application application) {
        if (nativeWidth == 0) {
            nativeWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        }
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        if (sRoncompatDennsity == 0) {
            sRoncompatDennsity = displayMetrics.density;
            sRoncompatScaledDensity = displayMetrics.scaledDensity;
            //如果想要字体随系统设置字体大小就加上监听，否则不需要
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sRoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        displayMetrics.density = isPxEqualsDp ? nativeWidth / dpi / (width / dpi) : nativeWidth / dpi;
        displayMetrics.densityDpi = (int) (displayMetrics.density * 160);
        //适配字体sp
        displayMetrics.scaledDensity = (displayMetrics.density) * (sRoncompatScaledDensity / sRoncompatDennsity);
    }
}
