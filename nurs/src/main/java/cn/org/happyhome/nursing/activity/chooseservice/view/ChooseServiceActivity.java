package cn.org.happyhome.nursing.activity.chooseservice.view;

import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.chooseservice.contract.IChooseServiceContract;
import cn.org.happyhome.nursing.activity.chooseservice.presenter.ChooseServicePresenter;
import cn.org.happyhome.nursing.adadpter.ChooseServiceTypeAdapter;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceTypeBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/12  14:08
 * - generate by MvpAutoCodePlus plugin.
 */

public class ChooseServiceActivity extends BaseMvpActivity<IChooseServiceContract.View, IChooseServiceContract.Presenter> implements IChooseServiceContract.View, View.OnClickListener {

    private android.support.v7.widget.RecyclerView recycleview;
    private android.widget.TextView txtchooseservice;
    private ChooseServiceTypeAdapter chooseServiceTypeAdapter;
    private List<ServiceTypeBean> data;
    private SharedPreferences sharedPreferences;
    private String userType;
    private String userId;
    TextView txtTitle;
    ImageView imgback;
    TextView txtBack;
    private LinearLayout layoutNoData;

    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);
        setContentView(R.layout.activity_choose_service);
        txtchooseservice = (TextView) findViewById(R.id.txt_choose_service);
        recycleview = (RecyclerView) findViewById(R.id.recycle_view);
        layoutNoData = findViewById(R.id.layout_nodata);
        txtchooseservice.setOnClickListener(this);
        txtTitle = findViewById(R.id.txt_title);
        imgback = findViewById(R.id.img_back);
        txtBack = findViewById(R.id.txt_back);

        imgback.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        this.txtTitle.setText("服务类型选择");
        recycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        userType = sharedPreferences.getString(Const.USER_TYPE, null);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        if (userType != null && userId != null) {
            mPresenter.selectServiceType(userType, userId);
        }
    }

    @Override
    public IChooseServiceContract.Presenter createPresenter() {
        return new ChooseServicePresenter();
    }

    @Override
    public IChooseServiceContract.View createView() {
        return this;
    }


    @Override
    public void showServiceList(ResultBean<List<ServiceTypeBean>> resultBean) {
        if (BuildConfig.DEBUG) Log.d("ChooseServiceActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            data = resultBean.getData();
            if (data != null && data.size() > 0) {
                chooseServiceTypeAdapter = new ChooseServiceTypeAdapter(R.layout.item_chooser_service, data);
                recycleview.setAdapter(chooseServiceTypeAdapter);
                chooseServiceTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CheckBox checkBox = view.findViewById(R.id.cb_check);
                        if (checkBox.isChecked()) {
                            data.get(position).setNote1("0");
                            checkBox.setChecked(false);
                        } else {
                            data.get(position).setNote1("1");
                            checkBox.setChecked(true);
                        }
                        chooseServiceTypeAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                layoutNoData.setVisibility(View.VISIBLE);
                txtchooseservice.setVisibility(View.GONE);

            }

        } else {
            layoutNoData.setVisibility(View.VISIBLE);
            txtchooseservice.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAddCallBack(ResultBean resultBean) {
        if (BuildConfig.DEBUG) Log.d("ChooseServiceActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            ToolUitls.toast(this, "设置成功！");
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1500);
        } else {
            ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
        }
    }

    @Override
    public void showErr(String err) {
        ToolUitls.toast(this, err);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_choose_service) {
            if (data != null) {
                if (data.size() > 0) {
                    List<ServiceTypeBean> checkList = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getNote1().equals("1")) {
                            data.get(i).setTusId(userId);
                            checkList.add(data.get(i));
                        }
                    }
                    mPresenter.addServiceType(userType, userId, checkList);
                }
            }
        } else if (v.getId() == R.id.img_back) {
            finish();
        } else if (v.getId() == R.id.txt_back) {
            finish();
        }
    }
}

