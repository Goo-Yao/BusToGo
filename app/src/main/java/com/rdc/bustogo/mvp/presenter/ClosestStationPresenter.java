package com.rdc.bustogo.mvp.presenter;

import android.support.annotation.NonNull;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.rdc.bustogo.common.AppConstants;
import com.rdc.bustogo.common.BasePresenter;
import com.rdc.bustogo.mvp.view.IVClosestStation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Goo on 2017-2-7.
 */

public class ClosestStationPresenter extends BasePresenter<IVClosestStation> {

    private List<String> stations = new ArrayList<>();
    private List<String> distance = new ArrayList<>();
    private List<String> detail = new ArrayList<>();
    private List<LatLng> location = new ArrayList<>();//存储公交站的经纬度

    public ClosestStationPresenter(IVClosestStation view) {
        super(view);
    }

    public void loadClosestStation() {
        view.showProgressDialog("搜寻最近公交站");
        PoiSearch poi = PoiSearch.newInstance();
        poi.setOnGetPoiSearchResultListener(getOnGetPoiSearchResultListener());
        poi.searchNearby(getPoiOption());
    }

    private PoiNearbySearchOption getPoiOption() {
        return new PoiNearbySearchOption()
                .keyword("公交站")
                .location(new LatLng(AppConstants.latitude, AppConstants.longitude))
                .radius(1000)
                .pageCapacity(50)
                .sortType(PoiSortType.distance_from_near_to_far);
    }

    @NonNull
    private OnGetPoiSearchResultListener getOnGetPoiSearchResultListener() {
        return new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult == null
                        || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    view.showTips("未找到结果");
                    return;
                }
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    DecimalFormat df = new DecimalFormat("#0.00");
                    for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                        PoiInfo poiInfo = poiResult.getAllPoi().get(i);
                        if (stations.contains(poiInfo.name)) { //有相同的公交站，距离相差很小,只存第一个
                            continue;
                        }
                        stations.add(poiInfo.name);
                        distance.add(df.format(DistanceUtil.getDistance(new LatLng(AppConstants.latitude, AppConstants.longitude), poiInfo.location)));//计算两点间的距离,保留小数点后两位
                        location.add(poiInfo.location);
                        detail.add(poiInfo.address);
                    }
                    view.dismissProgressDialog();
                    view.showStation(stations, distance, detail);
                    view.showTips("加载完毕");
                    return;
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    view.showTips("查找失败");
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
    }
}
