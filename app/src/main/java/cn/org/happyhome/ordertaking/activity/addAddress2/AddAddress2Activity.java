package cn.org.happyhome.ordertaking.activity.addAddress2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.IndoorBuildingInfo;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.StatusBarUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.BuildConfig;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.ordertaking.adapter.LocationAdapter;
import cn.org.happyhome.ordertaking.api.ApiManager;
import cn.org.happyhome.ordertaking.bean.Address;
import cn.org.happyhome.ordertaking.bean.LocationBean;
import cn.org.happyhome.ordertaking.bean.ResultBean;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddAddress2Activity extends Activity implements LocationSource, AMapLocationListener, AMap.OnIndoorBuildingActiveListener, AMap.OnPOIClickListener, AMap.OnMapClickListener, SearchView.OnQueryTextListener, PoiSearch.OnPoiSearchListener, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    private com.amap.api.maps.MapView mMapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private ImageView layoutBack;
    private TextView txtBack;
    private TextView txtTitle;
    private MapView map;
    private android.support.v7.widget.SearchView searchview;
    private android.support.v7.widget.RecyclerView recyclerView;
    private android.widget.Button btnsure;
    private InputMethodManager imm;
    private SharedPreferences sharedPreferences;
    private String userType;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setStatusBar(this, false, true);
        setContentView(R.layout.activity_map);
        this.btnsure = (Button) findViewById(R.id.btn_sure);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.searchview = (SearchView) findViewById(R.id.search_view);
        this.map = (MapView) findViewById(R.id.map);
        layoutBack = findViewById(R.id.img_back);
        txtBack = findViewById(R.id.txt_back);
        layoutBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);

        userType = sharedPreferences.getString(Const.USER_TYPE, null);
        userId = sharedPreferences.getString(Const.USER_ID, null);

        this.mMapView = (MapView) findViewById(R.id.map);
        this.layoutBack = findViewById(R.id.img_back);
        this.txtTitle = findViewById(R.id.txt_title);
        txtTitle.setText("地图");
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        aMap.getUiSettings().setZoomControlsEnabled(true);
        aMap.getUiSettings().setZoomGesturesEnabled(true);
        aMap.getUiSettings().setGestureScaleByMapCenter(true);
        aMap.getUiSettings().setScrollGesturesEnabled(true);
        aMap.getUiSettings().setRotateGesturesEnabled(true);

        aMap.setMinZoomLevel(1);
        aMap.setMaxZoomLevel(30);

        aMap.showIndoorMap(true);

        aMap.moveCamera(CameraUpdateFactory.zoomBy(7));

//        aMap.setOnMarkerClickListener(this);
        btnsure.setOnClickListener(this);

        aMap.setOnIndoorBuildingActiveListener(this);

        aMap.setOnPOIClickListener(this);

        aMap.setOnMapClickListener(this);

        searchview.setOnQueryTextListener(this);

        searchview.setIconified(false);
        searchview.onActionViewExpanded();
        searchview.setIconifiedByDefault(true);
        searchview.setSubmitButtonEnabled(false);
        searchview.setOnSearchClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 1;
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位

        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    private double latitude;
    private double longitude;
    private String cityCode;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点

                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
                cityCode = aMapLocation.getCityCode();
                if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", aMapLocation.toString());


//                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//                AMapOptions aMapOptions = new AMapOptions();
//                aMapOptions.camera(new CameraPosition(latLng, 10f, 0, 0));
//
//                MapView mapView=new MapView(this,aMapOptions);


            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }


    @Override
    public void OnIndoorBuilding(IndoorBuildingInfo indoorBuildingInfo) {
        if (BuildConfig.DEBUG)
            Log.d("AddAddress2Activity", "indoorBuildingInfo:" + indoorBuildingInfo);
    }

    @Override
    public void onPOIClick(Poi poi) {
        if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", "poi:" + poi);
//        aMap.clear();
//        aMap.addMarker(new MarkerOptions().draggable(true).position(poi.getCoordinate()));
    }


    @Override
    public void onMapClick(LatLng latLng) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", "sssss=" + s);
        if (s != null) {
            recyclerView.setVisibility(View.VISIBLE);
            PoiSearch.Query query = new PoiSearch.Query(s, "医院,社区", cityCode);
            query.setPageSize(20);
            PoiSearch search = new PoiSearch(this, query);
//            search.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 10000));
            search.setOnPoiSearchListener(this);
            search.searchPOIAsyn();
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        if (s != null && !"".equals(s)) {
            PoiSearch.Query query = new PoiSearch.Query(s, "", cityCode);
            query.setPageSize(20);
            PoiSearch search = new PoiSearch(this, query);
//            search.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 10000));
            search.setOnPoiSearchListener(this);
            search.searchPOIAsyn();
        }
        return true;
    }

    private LocationAdapter locationAdapter;

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

        ArrayList<PoiItem> pois = poiResult.getPois();

        List<LocationBean> locationBeans = new ArrayList<>();
        for (int y = 0; y < pois.size(); y++) {
            PoiItem poiItem = pois.get(y);
            if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", poiItem.toString());

            String cityName = poiItem.getProvinceName() + poiItem.getDirection() + poiItem.getCityName() + poiItem.toString();
            LatLonPoint latLonPoint = pois.get(y).getLatLonPoint();
            if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", "getEmail==" + poiItem.getAdCode());
            locationBeans.add(new LocationBean(poiItem.getPoiId(), cityName, poiItem.getAdCode(), latLonPoint));
        }

        if (locationBeans.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            locationAdapter = new LocationAdapter(locationBeans);
            recyclerView.setAdapter(locationAdapter);
            locationAdapter.notifyDataSetChanged();
            locationAdapter.setOnItemClickListener(this);
        } else {
            recyclerView.setVisibility(View.GONE);
        }


    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

        if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", "poiItem:" + poiItem);
    }

    private LocationBean locationBean;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        recyclerView.setVisibility(View.GONE);
        LocationBean locationBean = locationAdapter.getData().get(position);
        this.locationBean = locationBean;
        double latitude = locationBean.getLatLonPoint().getLatitude();
        double longitude = locationBean.getLatLonPoint().getLongitude();

        if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", locationBean.getEmail());
        if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", "latitude:" + latitude);
        if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", "longitude:" + longitude);

        aMap.clear();
        aMap.addMarker(new MarkerOptions().draggable(true).position(new LatLng(latitude, longitude)).title(locationBean.getName()).snippet(locationBean.getName()));
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude, longitude), 16, 30, 0)));
    }


    @SuppressLint("CheckResult")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
            case R.id.txt_back:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btn_sure:
                Address address = new Address();
                address.setAddress(this.locationBean.getName());
                address.setNote1(this.locationBean.getEmail());
                address.setLatitude(this.locationBean.getLatLonPoint().getLatitude() + "");
                address.setLongitude(this.locationBean.getLatLonPoint().getLongitude() + "");

                ApiManager.getInstence().getDailyService().bindAddress(userType, userId, address)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResultBean resultBean) {
                                if (resultBean.getCode() == Const.RESULT_OK) {
                                    ToolUitls.toast(AddAddress2Activity.this, "添加地址成功！");
                                    Intent intent = new Intent(Const.BIND_ADDRESS_OK);
                                    sendBroadcast(intent);
                                } else {
                                    ToolUitls.toast(AddAddress2Activity.this, "添加失败，请重试！");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (BuildConfig.DEBUG) Log.d("AddAddress2Activity", "e:" + e);
                                ToolUitls.toast(AddAddress2Activity.this, "添加失败，请重试！");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
