package com.rdc.bustogo.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 地图方向工具类
 */
public class OrientationUtil implements SensorEventListener {

    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mSensor;

    private float lastX;

    public OrientationUtil(Context context) {
        mContext = context;
    }

    @SuppressWarnings("deprecation")
    public void start() {
        mSensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            // 获得方向传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if (mSensor != null) {
            // 判空，说明手机支持该传感器
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    /**
     * 方向发生改变时，even会携带一个传感器类型，需要Orientation这以类型，有三个数据（x、y、z）
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if ((event.sensor.getType() == Sensor.TYPE_ORIENTATION)) {
            float x = event.values[SensorManager.DATA_X];
            if (Math.abs(x - lastX) > 1.0) {
                if (mOnOrientationListener != null) {
                    mOnOrientationListener.onOrientationChange(x);
                }
            }
            lastX = x;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setOnOrientationListener(
            OnOrientationListener mOnOrientationListener) {
        this.mOnOrientationListener = mOnOrientationListener;
    }

    private OnOrientationListener mOnOrientationListener;

    public interface OnOrientationListener {
        void onOrientationChange(float x);
    }
}
