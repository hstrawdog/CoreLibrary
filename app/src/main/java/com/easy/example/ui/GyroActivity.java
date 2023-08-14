package com.easy.example.ui;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.easy.core.ui.base.BaseViewBindingActivity;
import com.easy.core.utils.log.LogUtils;
import com.easy.example.databinding.ActivityGyroBinding;

import java.text.DecimalFormat;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @Date : 10:52
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class GyroActivity extends BaseViewBindingActivity<ActivityGyroBinding> implements SensorEventListener {


    @Override
    public void initView() {
        // 传感器
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 陀螺仪
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        // 回调与规则
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {  //
            LogUtils.e("onSensorChanged");
            float X_lateral = event.values[0];
            float Y_longitudinal = event.values[1];
            float Z_vertical = event.values[2];
            LogUtils.e("\n X " + X_lateral);
            LogUtils.e("\n Y " + Y_longitudinal);
            LogUtils.e("\n Z " + Z_vertical);
        } else if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) { // compass
            float X_lateral = event.values[0];
            float Y_longitudinal = event.values[1];
            float Z_vertical = event.values[2];
            LogUtils.e("\n send heading " + X_lateral);
            LogUtils.e("\n send pitch " + Y_longitudinal);
            LogUtils.e("\n send roll " + Z_vertical);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            double X_lateral = Double.parseDouble(decimalFormat.format(event.values[0]));
            double Y_longitudinal = Double.parseDouble(decimalFormat.format(event.values[1]));
            double Z_vertical = Double.parseDouble(decimalFormat.format(event.values[2]));
            if (Math.abs(X_lateral) > 0.2 || Math.abs(Y_longitudinal) > 0.2 || Math.abs(Z_vertical) > 0.2) {
                LogUtils.e("scop X " + X_lateral + "            scop Y " + Y_longitudinal + "                       scop Z " + Z_vertical);
                binding.textView35.setText("scop X " + X_lateral + "            scop Y " + Y_longitudinal + "                       scop Z " + Z_vertical);
            }
        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
