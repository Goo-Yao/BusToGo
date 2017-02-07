package com.rdc.bustogo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class LocationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//    private LocationClient mLocationClient;
//    private LocationClientOption option;
//    private boolean isFirstLoc = true;
//    private GeoCoder mSearch = null;
//    private BaiduMap mBaiduMap;
//    private MyLocationConfiguration.LocationMode mCurrentMode;
//    private OrientationUtil orientationUtil;
//    private BitmapDescriptor mBusMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_bus);
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//
//
//    private void setCurrentCity() {
//        // 初始化搜索模块，注册事件监听
//        mSearch = GeoCoder.newInstance();
//        mSearch.setOnGetGeoCodeResultListener(this);
//        LatLng ptCenter = new LatLng(AppConstants.latitude, AppConstants.longiTude);
//        // 反Geo搜索
//        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
//                .location(ptCenter));
//    }
//
//    //公车覆盖物
//    public void addBusMarkers(double lat, double lng) {
//        if (AppConstants.mMapView != null) {
//
//            mBaiduMap.clear();
//            // 经纬度
//            LatLng latlng = new LatLng(lat, lng);
//            // Marker图标,图层高度
//            OverlayOptions options = new MarkerOptions().position(latlng).icon(mBusMarker).zIndex(5);
//            Marker marker = (Marker) mBaiduMap.addOverlay(options);
//        }
//    /*    Bundle bundle = new Bundle();
//        bundle.putSerializable("bus", bus);
//        marker.setExtraInfo(bundle);
//*/
//    }
//
//    @Override
//    public void onReceiveLocation(BDLocation location) {
//        setLongitude(location.getLongitude());
//        setLatitude(location.getLatitude());
//        setAccuracy(location.getRadius());
//        setCurrentCity();
//
//        //如果设置了闹钟，则发送广播计算位置
//        if (AppConstants.isAlarming)
//            EventBus.getDefault().post(location);
//    }
//
//    private void setAccuracy(float accuracy) {
//        AppConstants.accuracy = accuracy;
//    }
//
//    @Override
//    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//
//    }
//
//    @Override
//    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            AppConstants.city = null;
//            return;
//        }
//        String address = result.getAddress();
//        if (address != null && !address.equals("")) {
//            AppConstants.city = address.substring(address.indexOf("省") + 1, address.indexOf("市"));
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // 退出时销毁定位
//        if (mLocationClient != null)
//            mLocationClient.stop();
//        if (orientationUtil != null)
//            orientationUtil.stop();
//    }
}
