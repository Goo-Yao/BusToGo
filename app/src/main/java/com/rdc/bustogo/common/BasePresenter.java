package com.rdc.bustogo.common;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Presenter基类
 *
 * @param <V>
 */
public abstract class BasePresenter<V> {

    /**
     * View引用
     */
    protected Reference<V> mViewRef;

    protected V view;


    public BasePresenter(V view) {
        this.view = view;
    }

    /**
     * 与View建立关联
     *
     * @param view
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    /**
     * 判断关联
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 解除引用
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
