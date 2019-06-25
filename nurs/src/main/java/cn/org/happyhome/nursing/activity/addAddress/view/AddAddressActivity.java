package cn.org.happyhome.nursing.activity.addAddress.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.addAddress.contract.IAddAddressContract;
import cn.org.happyhome.nursing.activity.addAddress.presenter.AddAddressPresenter;
import cn.org.happyhome.nursing.adadpter.AddAddressAdapter;
import cn.org.happyhome.nursing.bean.AddAddressBean;
import cn.org.happyhome.nursing.bean.Address;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/29  18:04
 * - generate by MvpAutoCodePlus plugin.
 */

public class AddAddressActivity extends BaseMvpActivity<IAddAddressContract.View, IAddAddressContract.Presenter> implements IAddAddressContract.View, View.OnClickListener {

    private android.support.v7.widget.RecyclerView recycleview;
    TextView txtTitle;
    TextView txtBack;
    TextView txtAddressCode;
    ImageView imgBack;
    SharedPreferences sharedPreferences;
    //申明对象
    CityPickerView mPicker = new CityPickerView();

    //    已绑定地址数量
    int hasNumber;
    //    地区编码
    String code;
    //    checkbox 计数器
    int count;

    AddAddressAdapter addAddressAdapter;

    private TextView txtSubmit;

    //    地址列表
    List<AddAddressBean> data;

    String userId;

    String userType;
    String province;
    String city;
    String district;

    Handler handler;

    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);
        setContentView(R.layout.activity_nusring_add_address);
        this.txtSubmit = (TextView) findViewById(R.id.txt_submit);
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        this.recycleview = (RecyclerView) findViewById(R.id.recycle_view);
        this.txtTitle = findViewById(R.id.txt_title);
        this.txtAddressCode = findViewById(R.id.txt_address_code);
        this.imgBack = findViewById(R.id.img_back);
        this.txtBack = findViewById(R.id.txt_back);
        txtTitle.setText("地址列表");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        handler = new Handler();
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
        hasNumber = sharedPreferences.getInt(Const.NURS_HAD_ADDRESS_NUMBER, 0);
        userType = sharedPreferences.getString(Const.USER_TYPE, null);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        code = sharedPreferences.getString(Const.ADCODE, null);
        province = sharedPreferences.getString(Const.PROVINCE, null);
        city = sharedPreferences.getString(Const.CITY, null);
        district = sharedPreferences.getString(Const.DISTRICT, null);
        txtSubmit.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        txtAddressCode.setOnClickListener(this);
        recycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (province != null && city != null)
            txtAddressCode.setText(province + city + district);
        if (code != null && userId != null) {
            mPresenter.getAddressList(code, userId);
        } else {
            code = "610100";
            txtAddressCode.setText("陕西省西安市");
            if (userId != null)
                mPresenter.getAddressList(code, userId);
        }

    }

    @Override
    public IAddAddressContract.Presenter createPresenter() {
        return new AddAddressPresenter();
    }

    @Override
    public IAddAddressContract.View createView() {
        return this;
    }


    @Override
    public void showAddressList(ResultBean<List<AddAddressBean>> resultBean) {
        if (BuildConfig.DEBUG) Log.d("AddAddressActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            data = resultBean.getData();

            if (data != null) {

                addAddressAdapter = new AddAddressAdapter(R.layout.item_add_address, data);
                recycleview.setAdapter(addAddressAdapter);
                addAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_check);
                        if (checkBox.isChecked()) {
                            count--;
                            data.get(position).setCheck(false);
                            checkBox.setChecked(false);
                        } else {
                            if (count < (5 - hasNumber)) {
                                count++;
                                data.get(position).setCheck(true);
                                checkBox.setChecked(true);
                            } else {
                                checkBox.setChecked(false);
                                ToolUitls.toast(AddAddressActivity.this, "最多选择" + (5 - hasNumber) + "个地址");
                            }
                        }
                        addAddressAdapter.notifyDataSetChanged();

                    }
                });
            }else {
                ToolUitls.toast(this,"当前区域暂无数据");
            }
        }else if(Const.RESULT_NODATA==resultBean.getCode()){
            ToolUitls.toast(this,"当前区域暂无数据");
        }
    }

    @Override
    public void bindAddress(ResultBean resultBean) {
        if (BuildConfig.DEBUG) Log.d("AddAddressActivity", "resultBean1111:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            ToolUitls.toast(AddAddressActivity.this, "添加地址成功");
            Intent intent = new Intent(Const.BIND_ADDRESS_OK);
            sendBroadcast(intent);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
        } else if (resultBean.getCode() == Const.RESULT_ERR) {
            ToolUitls.toast(AddAddressActivity.this, "服务器繁忙，请稍后再试!");
        } else if (Const.RESULT_NODATA == resultBean.getCode()) {
            ToolUitls.toast(this, "当前区域无数据");
        }

    }

    @Override
    public void showErr(String msg) {
        ToolUitls.toast(this, msg);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        }
        if (v.getId() == R.id.txt_back) {
            finish();
        }
        if (v.getId() == R.id.txt_address_code) {
            CityConfig cityConfig = new CityConfig.Builder().build();
            mPicker.setConfig(cityConfig);

            //监听选择点击事件及返回结果
            mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                @Override
                public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                    if (BuildConfig.DEBUG)
                        Log.d("AddAddressActivity", "xx==" + province.getId() + "--" + city.getId() + "--" + district.getId());
                    txtAddressCode.setText(province.getName() + city.getName() + district.getName());
                    //省份province
                    //城市city
                    //地区district
                    code = district.getId();
                    if (BuildConfig.DEBUG) Log.d("AddAddressActivity", "userId=" + userId);
                    if (userId != null) {
                        mPresenter.getAddressList(code, userId);
                    }
                }

                @Override
                public void onCancel() {
                }
            });

            //显示
            mPicker.showCityPicker();
        }
        if (v.getId() == R.id.txt_submit) {

            if (data != null && data.size() > 0) {

                List<AddAddressBean> checkData = new ArrayList<>();

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).isCheck()) {
                        checkData.add(data.get(i));
                    }
                }
                mPresenter.bindAddress(userId, userType, checkData);
            } else {
                ToolUitls.toast(this, "未选中任何地址");
            }


        }
    }
}

