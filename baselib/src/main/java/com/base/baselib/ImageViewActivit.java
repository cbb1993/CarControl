package com.base.baselib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dhy
 * Date: 2019/4/18
 * Time: 16:41
 * describe:
 */
public class ImageViewActivit extends android.support.v7.widget.AppCompatImageView {

    public ImageViewActivit(Context context) {
        super(context);

        init();
    }

    private void init() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewActivit.this.setActivated(!ImageViewActivit.this.isActivated());
            }
        });
    }

    public ImageViewActivit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageViewActivit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
