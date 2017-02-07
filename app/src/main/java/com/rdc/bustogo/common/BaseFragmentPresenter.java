package com.rdc.bustogo.common;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Fragment数据处理中介
 */
public class BaseFragmentPresenter<T> {
    protected Reference<T> mViewRef;

    /**
     * 建立关联
     *
     * @param view
     */
    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    protected T getView() {
        return mViewRef.get();
    }

    /**
     * 解除关联
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
