package com.rdc.bustogo.mvp.view;

import com.baidu.mapapi.map.MapStatusUpdate;

/**
 * Created by Goo on 2017-2-2.
 */

public interface IVMainMap {
    void showTips(String msg);

    void returnToStation(MapStatusUpdate msu);

    void fabMyLocationSelectable(boolean selectable);
}
