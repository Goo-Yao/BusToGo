package com.rdc.bustogo.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;

import com.rdc.bustogo.R;
import com.rdc.bustogo.swipeback.SwipeBackActivity;
import com.rdc.bustogo.ui.customview.CustomToast;


/**
 * 侧滑销毁Activity基类
 *
 * @param <V> View - 接口实现类
 * @param <P> Presenter - BasePresenter
 */
public abstract class BaseSwipeBackActivity<V, P extends BasePresenter<V>> extends SwipeBackActivity {

    protected P mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        //建立与Presenter联系
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        initVariables();
        initViews();
    }


    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 设置Activity内容布局id
     *
     * @return
     */
    protected abstract int setContentViewId();

    /**
     * 初始化变量
     */
    protected abstract void initVariables();

    /**
     * 初始化View
     */
    protected abstract void initViews();


    /**
     * 释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /**
     * 设置Activity切换动画类型
     *
     * @param TYPE 类型
     */
    public void setPendingTransition(int TYPE) {
        if (TYPE == AppConstants.OPEN_OVER_PENDING_TRANSITION_TAG) {
            overridePendingTransition(R.anim.translate_right_in,
                    R.anim.translate_not_move);
        } else if (TYPE == AppConstants.OUT_OVER_PENDING_TRANSITION_TAG) {
            overridePendingTransition(R.anim.translate_not_move,
                    R.anim.translate_right_out);
        }
    }

    /**
     * 显示等待对话框
     *
     * @param msg        提示
     * @param cancelable 是否可取消
     */
    protected void showProgressDialog(String msg, boolean cancelable) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    /**
     * 再按一次退出应用
     */
    protected void enableDoubleBackDestroy() {
        isDoubleBackDestroy = true;
    }

    protected long exitTime = 0;
    protected boolean isDoubleBackDestroy = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isDoubleBackDestroy) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    CustomToast.showToast(BaseSwipeBackActivity.this, "再按一次退出应用");
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);//结束整个进程
                }
            } else {
                finish();
                overridePendingTransition(R.anim.translate_not_move,
                        R.anim.translate_right_out);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    protected void finishActivityWithAnim() {
        finish();
        setPendingTransition(AppConstants.OUT_OVER_PENDING_TRANSITION_TAG);
    }
}
