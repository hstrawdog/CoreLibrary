package com.hqq.example.demo.weather;

import androidx.lifecycle.MutableLiveData;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hqq.core.CoreBuildConfig;
import com.hqq.core.permission.PermissionsResult;
import com.hqq.core.permission.PermissionsUtils;
import com.hqq.core.ui.vm.BaseViewModel;
import com.hqq.core.utils.log.LogUtils;
import com.hqq.example.APP;
import com.hqq.example.demo.net.HttpManager;
import com.hqq.example.demo.net.NetCallback;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.weather
 * @FileName :   WeatherViewModel
 * @Date : 2020/8/4 0004  下午 3:10
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class WeatherViewModel extends BaseViewModel {

    public MutableLiveData<Weather> mWeather = new MutableLiveData<>();

    @Override
    public void onCrete() {
        super.onCrete();
        LogUtils.e(" -------WeatherViewModel--------- onCrete ------");

//初始化定位
        mLocationClient = new AMapLocationClient(CoreBuildConfig.getInstance().getApplication());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        PermissionsUtils.requestLocationPermission(new PermissionsResult() {
            @Override
            public void onPermissionsResult(boolean status) {
                startLocation();
            }
        });
    }

    private void getWeather(String city) {
        setShowLoading(true);
        HttpManager.getWeather(city.substring(0, city.length() - 1), new NetCallback<Weather>() {
            @Override
            public void onSuccess(Weather response) {
                setShowLoading(false);
                mWeather.setValue(response);
            }

            @Override
            public void onFail(int code, String message) {
                setShowToast(message);
                setShowLoading(false);

            }
        });
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            LogUtils.e(aMapLocation.getAddress());
            getWeather(aMapLocation.getCity());

        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    AMapLocationClientOption option = new AMapLocationClientOption();


    public void startLocation() {
        //启动定位
        mLocationClient.startLocation();
    }
}
