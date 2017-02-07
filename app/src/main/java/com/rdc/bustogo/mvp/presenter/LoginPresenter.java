package com.rdc.bustogo.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import com.rdc.bustogo.common.BasePresenter;
import com.rdc.bustogo.mvp.view.IVLogin;
import com.rdc.bustogo.ui.activity.MainMapActivity;
import com.rdc.bustogo.ui.activity.RegisterActivity;
import com.rdc.bustogo.ui.activity.WelcomeActivity;

/**
 * Created by Goo on 2017-2-1.
 */
public class LoginPresenter extends BasePresenter<IVLogin> {

    public LoginPresenter(IVLogin view) {
        super(view);
    }

    public void login(String strAccount, String strPsw) {
        view.startActivityWithAnim(new Intent((Context) view, MainMapActivity.class));
        view.destroyActivity();
        if (WelcomeActivity.welcomeActivity != null) {
            WelcomeActivity.welcomeActivity.finish();
        }

        //联网验证用户密码逻辑 - 待重构加入
//        if (strAccount.isEmpty() || strPsw.isEmpty()) {
//            view.showTips("请完整填写用户名及密码");
//        } else {

//        }
    }

    public void register() {
        view.startActivityWithAnim(new Intent((Context) view, RegisterActivity.class));
    }
}
