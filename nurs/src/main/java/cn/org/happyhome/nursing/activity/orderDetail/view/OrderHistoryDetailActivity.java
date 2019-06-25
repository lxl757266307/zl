package cn.org.happyhome.nursing.activity.orderDetail.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.orderDetail.contract.IOrderDetailContract;
import cn.org.happyhome.nursing.activity.orderDetail.presenter.OrderDetailPresenter;
import cn.org.happyhome.nursing.adadpter.HistoryOrderServiceAdapter;
import cn.org.happyhome.nursing.bean.OrderContentBean;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/29  16:01
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderHistoryDetailActivity extends BaseMvpActivity<IOrderDetailContract.View, IOrderDetailContract.Presenter> implements IOrderDetailContract.View, View.OnClickListener {

    private android.widget.TextView txtAge;
    private android.widget.TextView txtsex;
    private android.widget.TextView txtWeight;
    private android.widget.TextView txtIsAccept;
    private android.widget.TextView txtPhone;
    private android.widget.TextView txtOrderTime;
    private android.support.v7.widget.RecyclerView recycleview;
    private android.widget.TextView txtOrderType;
    private android.widget.TextView txtInsanity;
    private android.widget.TextView txtContagion;
    private android.widget.TextView txtUserName;
    private android.widget.TextView txtAddress;
    private TextView txtTitle;
    private ImageView imgBack;
    private String orderId;
    private HistoryOrderServiceAdapter historyOrderServiceAdapter;
    TextView txtBack;
    private String orderTpye;

    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);
        setContentView(R.layout.activity_order_history_detail);
        orderId = getIntent().getStringExtra("orderId");
        SharedPreferences sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        String userId = sharedPreferences.getString(Const.USER_ID, null);
        if (BuildConfig.DEBUG) Log.d("OrderHistoryDetailActiv", "orderId==" + orderId);
        this.recycleview = (RecyclerView) findViewById(R.id.recycle_view);
        this.txtOrderTime = (TextView) findViewById(R.id.txt_order_time);
        this.txtPhone = (TextView) findViewById(R.id.txt_phone);
        this.txtUserName = (TextView) findViewById(R.id.txt_name);
        this.txtOrderType = (TextView) findViewById(R.id.txt_order_type);
        this.txtInsanity = findViewById(R.id.txt_insanity);
        this.txtContagion = findViewById(R.id.txt_contagion);
        this.txtAddress = findViewById(R.id.txt_address);
        this.txtIsAccept = (TextView) findViewById(R.id.txt_is_accept);
        this.txtWeight = (TextView) findViewById(R.id.txt_weight);
        this.txtsex = (TextView) findViewById(R.id.txt_sex);
        this.txtBack = (TextView) findViewById(R.id.txt_back);
        this.imgBack = findViewById(R.id.img_back);
        this.txtTitle = findViewById(R.id.txt_title);
        this.txtAge = (TextView) findViewById(R.id.txt_age);
        this.imgBack.setOnClickListener(this);
        this.txtBack.setOnClickListener(this);
        this.txtTitle.setText("订单详情");
        this.recycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.recycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 5;
            }
        });
        if (orderId != null && userId != null) {
            mPresenter.showOrderDetail(orderId, userId);
        }
    }

    @Override
    public IOrderDetailContract.Presenter createPresenter() {
        return new OrderDetailPresenter();
    }

    @Override
    public IOrderDetailContract.View createView() {
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        }
        if (v.getId() == R.id.txt_back) {
            finish();
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void showOrderDetail(ResultBean<List<OrderContentBean>> resultBean) {
        if (BuildConfig.DEBUG) Log.d("OrderHistoryDetailActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            List<OrderContentBean> data = resultBean.getData();
            if (data != null) {
                if (data.size() > 0) {
                    OrderContentBean orderContentBean = data.get(0);
                    String patientname = orderContentBean.getPatientname();
                    this.txtUserName.setText(patientname);
                    this.txtAge.setText(orderContentBean.getWeightage());
                    if ("0".equals(orderContentBean.getPsychosis())) {
                        this.txtIsAccept.setText("男女不限");
                    } else if ("1".equals(orderContentBean.getPsychosis())) {
                        this.txtIsAccept.setText("限男护工");
                    } else if ("2".equals(orderContentBean.getPsychosis())) {
                        this.txtIsAccept.setText("限女护工");
                    }

                    orderTpye = orderContentBean.getTpye();
                    if (Const.ORDER_TYPE_1.equals(orderTpye)) {
                        this.txtOrderType.setText("一对二");
                    } else if (Const.ORDER_TYPE_2.equals(orderTpye)) {
                        this.txtOrderType.setText("二对三");
                    } else if (Const.ORDER_TYPE_3.equals(orderTpye)) {
                        this.txtOrderType.setText("一对三");
                    } else if (Const.ORDER_TYPE_4.equals(orderTpye)) {
                        this.txtOrderType.setText("一对一  临时单");
                    }
                    String isolation = orderContentBean.getIsolation();
                    if (!TextUtils.isEmpty(isolation)) {
                        if ("1".equals(isolation)) {
                            this.txtContagion.setText("无");
                        } else if ("2".equals(isolation)) {
                            this.txtContagion.setText("有");
                        }
                    }

                    String psychosis = orderContentBean.getPsychosis();
                    if (!TextUtils.isEmpty(psychosis)) {
                        if ("1".equals(psychosis)) {
                            this.txtInsanity.setText("无");
                        } else if ("2".equals(psychosis)) {
                            this.txtInsanity.setText("有");
                        }
                    }
                    this.txtsex.setText(orderContentBean.getPatientsex() != null ? (orderContentBean.getPatientsex().endsWith("1") ? "男" : "女") : "");
                    this.txtWeight.setText(orderContentBean.getPatientweight() != null ? (orderContentBean.getPatientweight() + "kg") : "");
                    this.txtOrderTime.setText(ToolUitls.timeFormart(orderContentBean.getBegindate()) + "~" + ToolUitls.timeFormart(orderContentBean.getEnddate()));
                    this.txtPhone.setText(orderContentBean.getPhone());

                    historyOrderServiceAdapter = new HistoryOrderServiceAdapter(R.layout.item_history_order_service, data);
                    historyOrderServiceAdapter.openLoadAnimation();
                    recycleview.setAdapter(historyOrderServiceAdapter);
                }
            }
        }
    }

    @Override
    public void showErr() {

    }
}

