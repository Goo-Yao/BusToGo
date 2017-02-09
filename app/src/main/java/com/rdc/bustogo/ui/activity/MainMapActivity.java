package com.rdc.bustogo.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.rdc.bustogo.R;
import com.rdc.bustogo.common.AppConstants;
import com.rdc.bustogo.common.BaseActivity;
import com.rdc.bustogo.mvp.presenter.MainMapPresenter;
import com.rdc.bustogo.mvp.view.IVMainMap;
import com.rdc.bustogo.ui.customview.CustomToast;
import com.rdc.bustogo.utils.AnimationManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainMapActivity extends BaseActivity<IVMainMap, MainMapPresenter> implements IVMainMap {

    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bd_map_view)
    MapView bdMapView;
    @BindView(R.id.fab_MyLocation)
    FloatingActionButton fabMyLocation;
    @BindView(R.id.fab_slide_up)
    FloatingActionButton fabSlideUp;
    @BindView(R.id.fab_route_main)
    FloatingActionButton fabRouteMain;
    @BindView(R.id.fab_closest_station)
    FloatingActionButton fabClosestStation;
    @BindView(R.id.fab_search_route)
    FloatingActionButton fabSearchRoute;
    @BindView(R.id.cv_search_route_tag)
    CardView cvSearchRouteTag;
    @BindView(R.id.cv_closest_station_tag)
    CardView cvClosestStationTag;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    private boolean mIsRouteMenuShowing = false;

    @Override
    protected MainMapPresenter createPresenter() {
        return new MainMapPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main_map;
    }

    @Override
    protected void initVariables() {
        SDKInitializer.initialize(getApplicationContext());
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        initDrawer();
        initMapView();
    }

    /**
     * 初始化地图
     */
    private void initMapView() {
        BaiduMap map = bdMapView.getMap();
        map.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                fabMyLocation.setSelected(false);
                if (mIsRouteMenuShowing) {
                    mIsRouteMenuShowing = false;
                    hideRouteMenu();
                }
            }
        });
        removeUnnecessaryViewOnMap(map);
        mPresenter.initLocationClient(map);
    }

    /**
     * 除去地图logo、缩放按钮、比例尺、指南针
     *
     * @param map
     */
    private void removeUnnecessaryViewOnMap(BaiduMap map) {
        bdMapView.removeViewAt(1);
        View scale = bdMapView.getChildAt(1);
        scale.setVisibility(View.GONE);
        View rule = bdMapView.getChildAt(2);
        rule.setVisibility(View.GONE);
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setCompassEnabled(false);
    }

    /**
     * 加载抽屉
     */
    private void initDrawer() {

    }


    @OnClick({R.id.iv_menu, R.id.fab_MyLocation, R.id.fab_slide_up, R.id.fab_route_main, R.id.fab_closest_station, R.id.fab_search_route, R.id.cv_search_route_tag, R.id.cv_closest_station_tag})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.fab_MyLocation:
                mPresenter.returnToMyLocation();
                break;
            case R.id.fab_slide_up:

                break;
            case R.id.fab_route_main:
                if (mIsRouteMenuShowing) {
                    hideRouteMenu();
                } else {
                    showRouteMenu();
                }
                break;
            case R.id.fab_closest_station:
            case R.id.cv_closest_station_tag:
                openClosestStation();
                break;
            case R.id.cv_search_route_tag:
            case R.id.fab_search_route:
                break;
        }
    }

    private void openClosestStation() {
        startActivity(new Intent(MainMapActivity.this, ClosestStationActivity.class));
        setPendingTransition(AppConstants.OPEN_OVER_PENDING_TRANSITION_TAG);
    }


    /**
     * 显示路径菜单
     */
    private void showRouteMenu() {
        // 缩放动画显示
        Animation scaleAnim = AnimationManager.getScaleToShowAnim(this);
        Animation scaleAnim_2 = AnimationManager.getScaleToShowAnim(this);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fabRouteMain.setEnabled(false);
                fabSearchRoute.setVisibility(View.VISIBLE);
                fabClosestStation.setVisibility(View.VISIBLE);
                cvSearchRouteTag.setVisibility(View.VISIBLE);
                cvClosestStationTag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fabRouteMain.setEnabled(true);
                mIsRouteMenuShowing = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fabSearchRoute.startAnimation(scaleAnim);
        fabClosestStation.startAnimation(scaleAnim_2);
        cvSearchRouteTag.startAnimation(scaleAnim_2);
        cvClosestStationTag.startAnimation(scaleAnim_2);

        // 大按钮的旋转动画
        fabRouteMain.startAnimation(AnimationManager.getFABRotateToOpenAnim(fabRouteMain));
    }

    /**
     * 隐藏路径菜单
     */
    private void hideRouteMenu() {
        Animation scaleAnim = AnimationManager.getScaleToHideAnim(this);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 设置不可点击
                fabRouteMain.setEnabled(false);
                mIsRouteMenuShowing = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fabRouteMain.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Animation scaleAnim_2 = AnimationManager.getScaleToHideAnim(this);
        fabSearchRoute.startAnimation(scaleAnim);
        fabClosestStation.startAnimation(scaleAnim_2);
        cvSearchRouteTag.startAnimation(scaleAnim_2);
        cvClosestStationTag.startAnimation(scaleAnim_2);

        fabRouteMain.startAnimation(AnimationManager.getFABRotateToCloseAnim(fabRouteMain));

    }

    @Override
    public void showTips(String msg) {
        CustomToast.showToast(this, msg);
    }

    @Override
    public void returnToStation(MapStatusUpdate msu) {
        bdMapView.getMap().animateMapStatus(msu);
    }

    @Override
    public void fabMyLocationSelectable(boolean selectable) {
        fabMyLocation.setSelected(selectable);
    }

}
