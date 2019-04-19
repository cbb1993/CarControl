package com.base.baselib.recycleview;

/**
 * author dhy
 * Created by test on 2019/4/19.
 */

public abstract class RVBaseCell<T> implements Cell {
    public T mData;

    //构造方法传入数据
    public RVBaseCell(T t) {
        mData = t;
    }

    @Override
    public void releaseResource() {
        //do something
        //如果有需要回收资源的，子类自己实现
    }
}
