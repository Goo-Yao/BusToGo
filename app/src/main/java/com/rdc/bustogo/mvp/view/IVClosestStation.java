package com.rdc.bustogo.mvp.view;

import java.util.List;

/**
 * Created by Goo on 2017-2-7.
 */
public interface IVClosestStation {
    void showProgressDialog(String msg);

    void dismissProgressDialog();

    void showStation(List<String> stations, List<String> distance, List<String> detail);

    void showTips(String msg);
}
