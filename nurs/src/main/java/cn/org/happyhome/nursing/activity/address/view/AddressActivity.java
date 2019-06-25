package cn.org.happyhome.nursing.activity.address.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.addAddress.view.AddAddressActivity;
import cn.org.happyhome.nursing.activity.address.contract.IAddressContract;
import cn.org.happyhome.nursing.activity.address.presenter.AddressPresenter;
import cn.org.happyhome.nursing.adadpter.AddressAdapter;
import cn.org.happyhome.nursing.bean.Address;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/29  15:24
 * - generate by MvpAutoCodePlus plugin.
 */

public class AddressActivity extends BaseMvpActivity<IAddressContract.View, IAddressContract.Presenter> implements IAddressContract.View, View.OnClickListener {

    RecyclerView recyclerView;
    ImageView imgBack;
    TextView txtTitle;
    TextView txtBack;
    SharedPreferences sharedPreferences;
    AddressAdapter addressAdapter;
    String userId;
    String userType;
    int index;
    List<Address> list;
    TextView txtAddAddress;
    AddAddressReciver addAddressReciver;

    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);
        initView();
        initReciver();
        if (userId != null) {
            if (BuildConfig.DEBUG) Log.d("AddressActivity", "mPresenter:" + mPresenter);
            mPresenter.getAddressList(userId);
        }
    }

    private void initReciver() {
        addAddressReciver = new AddAddressReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.BIND_ADDRESS_OK);
        this.registerReceiver(addAddressReciver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(addAddressReciver);
    }

    public void initView() {
        setContentView(R.layout.activity_address);
        txtAddAddress = findViewById(R.id.txt_add);
        recyclerView = findViewById(R.id.recycle_view);
        imgBack = findViewById(R.id.img_back);
        txtTitle = findViewById(R.id.txt_title);
        txtBack = findViewById(R.id.txt_back);
        txtBack.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtTitle.setText("地址");
        txtAddAddress.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        userType = sharedPreferences.getString(Const.USER_TYPE, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public IAddressContract.Presenter createPresenter() {
        return new AddressPresenter();
    }

    @Override
    public IAddressContract.View createView() {
        return this;
    }


    @Override
    public void showAddressList(ResultBean<List<Address>> resultBean) {
        if (BuildConfig.DEBUG) Log.d("AddressActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == 0) {
            list = resultBean.getData();
            SharedPreferences.Editor edit = sharedPreferences.edit();
//            本地存储护工已绑定地址数量方便后续代码使用
            edit.putInt(Const.NURS_HAD_ADDRESS_NUMBER, list.size());
            edit.apply();
            addressAdapter = new AddressAdapter(R.layout.item_address, list);
            addressAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    index = position;
                    Address address = (Address) adapter.getItem(position);
                    if (BuildConfig.DEBUG) Log.d("AddressActivity", "address:" + address);
                    String addressId = address.getAddressid();
                    mPresenter.deleteAddressList(userType, userId, addressId);
//                    mPresenter.deleteAddressList("1", "1", addressId);

                }
            });
            recyclerView.setAdapter(addressAdapter);
        }
    }

    @Override
    public void deleteAddress(ResultBean resultBean) {
        if (resultBean.getCode() == 0) {
            ToolUitls.toast(this, "删除成功！");
            list.remove(index);
//            删除已绑定地址成功后 及时更新本地数据
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt(Const.NURS_HAD_ADDRESS_NUMBER, list.size());
            edit.apply();
            addressAdapter.setNewData(list);
            addressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showErr(String msg) {
        ToolUitls.toast(this, msg);
    }


    public static final int REQUEST_CODE = 1;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.txt_back) {
            finish();
        } else if (view.getId() == R.id.txt_add) {

            Intent intent = new Intent();
            try {
                Class<?> clazz = Class.forName("cn.org.happyhome.ordertaking.activity.addAddress2.AddAddress2Activity");
                intent.setClass(this, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivityForResult(intent, REQUEST_CODE);
//            goOtherActivity(this, AddAddressActivity.class);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.getAddressList(userId);
    }

    public class AddAddressReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BuildConfig.DEBUG) Log.d("AddressActivity", "收到广播");
            if (Const.BIND_ADDRESS_OK.equals(intent.getAction())) {
                mPresenter.getAddressList(userId);
            }
        }
    }
}

