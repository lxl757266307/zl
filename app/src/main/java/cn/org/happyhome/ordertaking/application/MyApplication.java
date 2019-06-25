package cn.org.happyhome.ordertaking.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import cn.jpush.android.api.JPushInterface;
import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ToolUitls;

public class MyApplication extends Application implements AMapLocationListener {
    public static IWXAPI iwxapi;

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    SharedPreferences sharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);

        initLocation();

    }


    private void initLocation() {
        mlocationClient = new AMapLocationClient(getApplicationContext());
//        mlocationClient.setApiKey("15a66526066fd50d45fd0b2e24f7dfc7");
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(3000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
//        if (BuildConfig.DEBUG) Log.d("MyApplication", amapLocation.toString());
//        ToolUitls.toast(getApplicationContext(), "amapLocation=" + amapLocation);
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(Const.LATITUDE, String.valueOf(amapLocation.getLatitude()));
                edit.putString(Const.LONGITUDE, String.valueOf(amapLocation.getLongitude()));
                edit.putString(Const.ADCODE, String.valueOf(amapLocation.getAdCode()));
                edit.putString(Const.PROVINCE, String.valueOf(amapLocation.getProvince()));
                edit.putString(Const.CITY, String.valueOf(amapLocation.getCity()));
                edit.putString(Const.DISTRICT, String.valueOf(amapLocation.getDistrict()));
//                Log.d("MyApplication", "ac====" + String.valueOf(amapLocation.getAdCode()));
//                ToolUitls.toast(this, "applicatiuon==" + amapLocation.getAdCode());
                edit.apply();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
