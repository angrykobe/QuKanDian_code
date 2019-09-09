package com.zhangku.qukandian.utils;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GyroInfoProtocol;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.SpConstants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.SpUtils;

import java.util.Map;

/**
 * 陀螺仪数据
 */
public class MySensorManager implements SensorEventListener {
    private Activity activity;
    // 传感器
    private SensorManager sensorManager = null;
    private Sensor gyroSensor = null;
    private float[] X = new float[2];
    private float[] Y = new float[2];
    private float[] Z = new float[2];

    private int mTimes = 5;
    private float mLimit = 0.01f;

    public MySensorManager(Activity act) {
        activity = act;
        sensorManager = (SensorManager) activity.getSystemService(Activity.SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void oneTimesAction() {
        LogUtils.LogW("oneTimesAction:start");
        float xoffset = X[1] - X[0];
        float yoffset = Y[1] - Y[0];
        float zoffset = Z[1] - Z[0];

        LogUtils.LogW("oneTimesAction:change:" + xoffset + ":" + yoffset + ":" + zoffset);
        if (Math.abs(xoffset) > mLimit || Math.abs(yoffset) > mLimit || Math.abs(zoffset) > mLimit) {
            // 有变化，重新开始
            LogUtils.LogW("oneTimesAction:change");
            X[0] = X[1];
            Y[0] = Y[1];
            Z[0] = Z[1];
            return;
        }
        // 没有变化
        // 未登录
        if (!UserManager.getInst().hadLogin()) {
            return;
        }
        // 已经上报次数
        int todayTimes = SpUtils.getInstance().getInt(SpConstants.SENSOR_TIMES, 0);
        if (todayTimes >= mTimes) {
            LogUtils.LogW("oneTimesAction:times");
            return;
        }

        new GyroInfoProtocol(activity, X[1], Y[1], Z[1], new BaseModel.OnResultListener<Map>() {
            @Override
            public void onResultListener(Map response) {
                LogUtils.LogW("oneTimesAction:success");
                int todayTimes = SpUtils.getInstance().getInt(SpConstants.SENSOR_TIMES, 0);
                SpUtils.getInstance().putInt(SpConstants.SENSOR_TIMES, todayTimes + 1);
                X[0] = X[1];
                Y[0] = Y[1];
                Z[0] = Z[1];
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        X[1] = event.values[0];
        Y[1] = event.values[1];
        Z[1] = event.values[2];
        if (X[0] == 0 || Y[0] == 0 || Z[0] == 0) {
            X[0] = event.values[0];
            Y[0] = event.values[1];
            Z[0] = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onPause() {
        // 解除监听器注册
        sensorManager.unregisterListener(this);
    }

    public void onResume() {
        // 为传感器注册监听器
        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}