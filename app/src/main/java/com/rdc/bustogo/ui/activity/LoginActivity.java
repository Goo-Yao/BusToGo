package com.rdc.bustogo.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MaterialEditText;
import android.widget.TextView;

import com.rdc.bustogo.R;
import com.rdc.bustogo.common.AppConstants;
import com.rdc.bustogo.common.BaseSwipeBackActivity;
import com.rdc.bustogo.mvp.presenter.LoginPresenter;
import com.rdc.bustogo.mvp.view.IVLogin;
import com.rdc.bustogo.ui.customview.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Goo on 2017-1-31.
 */
public class LoginActivity extends BaseSwipeBackActivity<IVLogin, LoginPresenter> implements IVLogin {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_account)
    MaterialEditText etAccount;
    @BindView(R.id.et_psw)
    MaterialEditText etPsw;
    @BindView(R.id.tv_btn_login)
    TextView tvBtnLogin;
    @BindView(R.id.tv_btn_register)
    TextView tvBtnRegister;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        setTitle();
        showTips("服务器已过期失效，本应用已重构为单机体验版\n请直接点击登录进入应用");
    }

    private void setTitle() {
        tvTitle.setText("登录");
    }


    @OnClick({R.id.iv_back, R.id.tv_btn_login, R.id.tv_btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivityWithAnim();
                break;
            case R.id.tv_btn_login:
                mPresenter.login(etAccount.getText().toString(), etPsw.getText().toString());
                break;
            case R.id.tv_btn_register:
                mPresenter.register();
                break;
            default:
                break;
        }
    }


    @Override
    public void showProgressDialog(String msg) {
        super.showProgressDialog(msg, false);
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }

    @Override
    public void startActivityWithAnim(Intent intent) {
        startActivity(intent);
        setPendingTransition(AppConstants.OPEN_OVER_PENDING_TRANSITION_TAG);
    }

    @Override
    public void destroyActivity() {
        finish();
    }

    @Override
    public void showTips(String msg) {
        CustomToast.showToast(this, msg);
    }
}
