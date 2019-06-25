package cn.org.happyhome.nursing.activity.chooseServiceContent.view;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.chooseServiceContent.contract.IChooseServiceContentContract;
import cn.org.happyhome.nursing.activity.chooseServiceContent.presenter.ChooseServiceContentPresenter;
import cn.org.happyhome.nursing.adadpter.ChooseServiceContentAdapter;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceContentBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/12/4  15:23
 * - generate by MvpAutoCodePlus plugin.
 */

public class ChooseServiceContentActivity extends BaseMvpActivity<IChooseServiceContentContract.View, IChooseServiceContentContract.Presenter> implements IChooseServiceContentContract.View, View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private android.support.v7.widget.RecyclerView recycleview;
    private Button btnchooseservice;
    private TextView txtTiltle;
    private ImageView imgBack;
    private SharedPreferences sharedPreferences;
    private String userId;
    private List<ServiceContentBean> list;
    private ChooseServiceContentAdapter chooseServiceContentAdapter;
    public static final String CHECKED = "1"; //选中
    public static final String UNCHECKED = "0";// 未选中
    TextView txtBack;

    @Override
    public void initListener() {
        setContentView(R.layout.activity_choose_service_content);
        this.btnchooseservice = (Button) findViewById(R.id.btn_submit);
        this.recycleview = (RecyclerView) findViewById(R.id.recyclerView);
        this.txtTiltle = findViewById(R.id.txt_title);
        this.imgBack = findViewById(R.id.img_back);
        this.txtBack = findViewById(R.id.txt_back);
        this.txtTiltle.setText("选择服务内容");
        this.imgBack.setOnClickListener(this);
        this.txtBack.setOnClickListener(this);
        this.btnchooseservice.setOnClickListener(this);
        this.recycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        this.userId = sharedPreferences.getString(Const.USER_ID, null);
        if (userId != null) {
            mPresenter.showServiceontent(userId);
        }

    }

    @Override
    public IChooseServiceContentContract.Presenter createPresenter() {
        return new ChooseServiceContentPresenter();
    }

    @Override
    public IChooseServiceContentContract.View createView() {
        return this;
    }

    @Override
    public void showServiceContent(ResultBean<List<ServiceContentBean>> resultBean) {
        if (BuildConfig.DEBUG) Log.d("ChooseServiceContentAct", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            list = resultBean.getData();
            if (list != null) {
                if (list.size() > 0) {
                    chooseServiceContentAdapter = new ChooseServiceContentAdapter(R.layout.item_servie_content, list);
                    chooseServiceContentAdapter.openLoadAnimation();
                    chooseServiceContentAdapter.setOnItemClickListener(this);
                    this.recycleview.setAdapter(chooseServiceContentAdapter);
                }
            } else {
                ToolUitls.toast(this, "暂无服务内容！");
            }

        } else {
            ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
        }
    }

    @Override
    public void saveServiceContent(ResultBean<String> resultBean) {
        if (BuildConfig.DEBUG) Log.d("ChooseServiceContentAct", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            ToolUitls.toast(this, "提交成功！");
            new Handler().postDelayed(new Runnable() {
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
    public void showErr() {
        ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        } else if (v.getId() == R.id.txt_back) {
            finish();
        } else if (v.getId() == R.id.btn_submit) {
            List<ServiceContentBean> checkData = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (CHECKED.equals(list.get(i).getNote1())) {
                    checkData.add(list.get(i));
                }
            }
            if (!(checkData.size() > 0)) {
                ToolUitls.toast(this, "未选中任何服务");
                return;
            }
            mPresenter.saveServiceContent(list);

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CheckBox checkBox = view.findViewById(R.id.cb_check);

        if (checkBox.isChecked()) {
            list.get(position).setNote1(UNCHECKED);
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
            list.get(position).setNote1(CHECKED);
        }
        this.chooseServiceContentAdapter.notifyDataSetChanged();


    }
}

