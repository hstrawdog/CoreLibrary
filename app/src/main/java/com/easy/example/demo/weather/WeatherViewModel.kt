package com.easy.example.demo.weather

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.easy.core.CoreConfig
import com.easy.example.demo.net.launch
import com.easy.core.permission.SysPermissionsUtils
import com.easy.core.ui.base.BaseViewModel
import com.easy.core.utils.log.LogUtils
import com.easy.core.utils.log.LogUtils.e
import com.easy.example.demo.net.HttpManager
import com.easy.example.demo.net.NetResponseBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.demo.weather
 * @FileName :   WeatherViewModel
 * @Date : 2020/8/4 0004  下午 3:10
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class WeatherViewModel : BaseViewModel() {

    var mWeather = MutableLiveData<com.easy.example.demo.weather.Weather>()

    //声明AMapLocationClient类对象
    var mLocationClient: AMapLocationClient? = null

    //声明定位回调监听器
    var mLocationListener = AMapLocationListener { aMapLocation ->
        e(aMapLocation.address)
        getWeather(aMapLocation.city)
    }

    //声明AMapLocationClientOption对象
    var mLocationOption: AMapLocationClientOption? = null
    var option = AMapLocationClientOption()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        e(" -------WeatherViewModel--------- onCrete ------")
        AMapLocationClient.updatePrivacyShow(CoreConfig.applicationContext, true, true)
        AMapLocationClient.updatePrivacyAgree(CoreConfig.applicationContext, true)
        //初始化定位
        mLocationClient = AMapLocationClient(CoreConfig.get().application)
        //设置定位回调监听
        mLocationClient!!.setLocationListener(mLocationListener)
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        if (null != mLocationClient) {
            mLocationClient!!.setLocationOption(option)
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient!!.stopLocation()
        }
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption!!.isOnceLocation = true
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption!!.isOnceLocationLatest = true
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption!!.isNeedAddress = true
        //给定位客户端对象设置定位参数
        mLocationClient!!.setLocationOption(mLocationOption)
        SysPermissionsUtils.requestLocal((CoreConfig.get().currActivity as AppCompatActivity) .supportFragmentManager,{ status ->
            if (status) {
                startLocation()
            }
        })


    }

    private fun getWeather(city: String) {
        launch({
            HttpManager.getWeather2(city.substring(0, city.length - 1))
        }, {

        })


        CoroutineScope(Dispatchers.IO).launch {

            flow<NetResponseBody<com.easy.example.demo.weather.Weather>> {
                var ben1 = HttpManager.getWeather2(city.substring(0, city.length - 1))
                    .let {
                        mWeather.postValue(it.result)
                        emit(it)

                    }
            }.zip(flow<NetResponseBody<com.easy.example.demo.weather.Weather>> {
                var ben1 = HttpManager.getWeather2(city.substring(0, city.length - 1))
                    .let {
                        mWeather.postValue(it.result)
                        emit(it)
                    }
            }) { i, s ->
                ArrayList<NetResponseBody<com.easy.example.demo.weather.Weather>>().apply {
                    add(s)
                    add(i)
                    LogUtils.e("")
                }
            }
                .collect() {
                    LogUtils.e("")
                }
        }

//        showLoading(true)
//        HttpManager.getWeather(city.substring(0, city.length - 1), object : NetCallback<Weather>() {
//            override fun onFail(code: Int, message: String) {
//                showToast(message!!)
//                showLoading(false)
//            }
//            override fun onSuccess(response: Weather) {
//                showLoading(false)
//                mWeather.value = response
//            }
//        })
    }

    fun startLocation() {
        //启动定位
        mLocationClient!!.startLocation()
    }
}