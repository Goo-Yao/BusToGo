package com.rdc.bustogo.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.bustogo.R;
import com.rdc.bustogo.common.BaseSwipeBackActivity;
import com.rdc.bustogo.mvp.presenter.ClosestStationPresenter;
import com.rdc.bustogo.mvp.view.IVClosestStation;
import com.rdc.bustogo.ui.adapter.ClosestStationAdapter;
import com.rdc.bustogo.ui.customview.CustomToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Goo on 2017-2-7.
 */
public class ClosestStationActivity extends BaseSwipeBackActivity<IVClosestStation, ClosestStationPresenter> implements IVClosestStation {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_station)
    RecyclerView rvStation;

    ClosestStationAdapter rvAdapter;

    @Override
    protected ClosestStationPresenter createPresenter() {
        return new ClosestStationPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_closest_station;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        initTitleBar();
        initRvStation();
        mPresenter.loadClosestStation();

    }

    private void initTitleBar() {
        tvTitle.setText("最近公交站");
    }

    private void initRvStation() {
        rvStation.setLayoutManager(new LinearLayoutManager(this));
        rvStation.setHasFixedSize(true);
        rvAdapter = new ClosestStationAdapter();
        rvAdapter.setOnItemClickListener(new ClosestStationAdapter.StationListener() {
            @Override
            public void onItemClick(View view, int tag) {

            }
        });

    }


    @OnClick(R.id.iv_back)
    public void onClick() {
        finishActivityWithAnim();
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
    public void showStation(List<String> stations, List<String> distance, List<String> detail) {
        rvAdapter.setData(stations, distance, detail);
        rvStation.setAdapter(rvAdapter);
    }

    @Override
    public void showTips(String msg) {
        CustomToast.showToast(this, msg);
    }
}
