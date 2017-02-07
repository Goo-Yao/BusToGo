package com.rdc.bustogo.mvp.presenter;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.rdc.bustogo.common.AppConstants;
import com.rdc.bustogo.common.BasePresenter;
import com.rdc.bustogo.mvp.view.IVMainMap;
import com.rdc.bustogo.ui.activity.MainMapActivity;
import com.rdc.bustogo.utils.OrientationUtil;

/**
 * Created by Goo on 2017-2-2.
 */
public class MainMapPresenter extends BasePresenter<IVMainMap> {
    private boolean isFirstLoc = true;
    private float direction = 0;
    private LatLng latLng;
    private double mLatitude, mLongitude;

    public MainMapPresenter(IVMainMap view) {
        super(view);
    }


    public void initLocationClient(final BaiduMap baiduMap) {
        // 一开始显示的放大倍数在500m左右
        baiduMap.setMyLocationEnabled(true);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null);
        baiduMap.setMyLocationConfigeration(config);
        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));
        LocationClient locationClient = new LocationClient((MainMapActivity) view);
        locationClient.registerLocationListener(getBDLocationListener(baiduMap));//定位监听
        locationClient.setLocOption(getLocationClientOption());
        OrientationUtil orientationUtil = new OrientationUtil((MainMapActivity) view);//方向监听
        orientationUtil.setOnOrientationListener(getOnOrientationListener());
        orientationUtil.start();
        locationClient.start();
    }

    private OrientationUtil.OnOrientationListener getOnOrientationListener() {
        return new OrientationUtil.OnOrientationListener() {
            @Override
            public void onOrientationChange(float x) {
                direction = x;
            }
        };
    }

    private LocationClientOption getLocationClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        option.setScanSpan(1000);// 每1秒请求一次
        return option;
    }

    private BDLocationListener getBDLocationListener(final BaiduMap baiduMap) {
        return new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.e("TEST", "onReceiveLocation - ");
                setCurrentCity(bdLocation);
                mLatitude = bdLocation.getLatitude();
                mLongitude = bdLocation.getLongitude();
                AppConstants.latitude = mLatitude;
                AppConstants.longitude = mLongitude;
                MyLocationData data = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())
                        .direction(direction)
                        .latitude(mLatitude)
                        .longitude(mLongitude).build();
                baiduMap.setMyLocationData(data);

                // 第一次定位把地图转移到中心点
                if (isFirstLoc) {
                    isFirstLoc = false;
                    // 经度、纬度
                    LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                    MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
                    baiduMap.animateMapStatus(msu);
                    view.showTips(bdLocation.getAddrStr());
                }

                //如果设置了闹钟，需要实时计算位置
//        if (AppConstants.isAlarming)
//            EventBus.getDefault().post(location);
            }
        };
    }

    private void setCurrentCity(BDLocation bdLocation) {
        // 初始化搜索模块，注册事件监听
        GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    AppConstants.CURRENT_CITY = null;
                    return;
                }
                String address = result.getAddress();
                if (address != null && !address.equals("")) {
                    AppConstants.CURRENT_CITY = address.substring(address.indexOf("省") + 1, address.indexOf("市"));
                }
            }
        });
        LatLng ptCenter = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(ptCenter));
    }

    /**
     * 返回我的位置
     */
    public void returnToMyLocation() {
        if (latLng == null) {
            latLng = new LatLng(mLatitude, mLongitude);
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        view.returnToStation(msu);
        view.fabMyLocationSelectable(true);
    }
}
