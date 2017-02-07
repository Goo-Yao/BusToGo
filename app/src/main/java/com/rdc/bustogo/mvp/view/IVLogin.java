package com.rdc.bustogo.mvp.view;

import android.content.Intent;

/**
 * Created by Goo on 2017-1-31.
 */
public interface IVLogin {
    void showProgressDialog(String msg);

    void dismissProgressDialog();

    void startActivityWithAnim(Intent intent);

    void destroyActivity();

    void showTips(String msg);
}
