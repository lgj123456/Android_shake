package yhdj.example.com.shake;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by yhdj on 2017/6/12.
 */

public class ShakeSensor implements SensorEventListener {
    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mAccelerometerSensor;

    private long last_time;
    private float last_x;
    private float last_y;
    private float last_z;
    private OnShakeListener mOnShakeListener;

    public ShakeSensor(Context context) {
        this.mContext = context;
    }

    public void init() {
        mSensorManager = ((SensorManager) mContext.getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }


    public void unRegister() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mAccelerometerSensor);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.e("aaaaaaaa", "onSensorChanged: "+event.values[0]);
        long currentTime = System.currentTimeMillis();
        if (currentTime - last_time > 10) {

            long timeDistance = currentTime - last_time;
            last_time = currentTime;
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            double speed;
            double absvalue = Math.abs(x + y + z - last_x - last_y - last_z);

            speed = absvalue / timeDistance * 10000;
            if (speed > 4800) {
                if (null != mOnShakeListener) {
                    mOnShakeListener.onShake();
                }
            }
            last_x = x;
            last_z = z;
            last_y = y;
        }
    }

    public void setOnShakeListener(OnShakeListener onShakeListener) {
        this.mOnShakeListener = onShakeListener;
    }

    public interface OnShakeListener {
        void onShake();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
