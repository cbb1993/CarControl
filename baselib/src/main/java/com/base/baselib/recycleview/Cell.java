package com.base.baselib.recycleview;

import android.view.ViewGroup;

/**
 * author dhy
 * Created by test on 2019/4/19.
 */

public interface Cell {
    /**
     * 资源回收
     */
    void releaseResource();

    /**
     * 获取viewType
     *
     * @return
     */
    int getItemType();

    /**
     * 创建viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */
    RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 数据绑定
     *
     * @param holder   viewHolder对象
     * @param position
     */
    void onBindViewHolder(RVBaseViewHolder holder, int position);

}
