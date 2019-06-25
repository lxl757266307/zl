package cn.org.happyhome.nursing.activity.orderContent.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.library.view.CustomerDialog;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.orderContent.contract.IOrderContentContract;
import cn.org.happyhome.nursing.activity.orderContent.presenter.OrderContentPresenter;
import cn.org.happyhome.nursing.activity.realName.view.RealNameActivity;
import cn.org.happyhome.nursing.adadpter.OrderContentServiceAdapter;
import cn.org.happyhome.nursing.bean.OrderContentBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceItemBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/26  11:02
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderContentActivity extends BaseMvpActivity<IOrderContentContract.View, IOrderContentContract.Presenter> implements IOrderContentContract.View, View.OnClickListener, BaseQuickAdapter.OnItemClickListener, CustomerDialog.OnDialogChildViewClickListener {


    private String orderId;
    private android.widget.TextView txtAge;
    private android.widget.TextView txtTitle;
    private android.widget.ImageView imgBack;
    private android.widget.TextView txtSex;
    private android.widget.TextView txtWeight;
    private android.widget.TextView txtIsAccept;
    private android.widget.TextView txtPhone;
    private android.widget.TextView txtOrderTime;
    private android.widget.TextView txtUserName;
    private android.widget.TextView txtOrderType;
    private android.widget.TextView txtInsanity;
    private android.widget.TextView txtContagion;
    private android.widget.TextView txtAddress;
    private android.widget.Button btnSubmit;
    private android.support.v7.widget.RecyclerView recycleview;
    private OrderContentServiceAdapter orderContentServiceAdapter;
    private SharedPreferences sharedPreferences;
    private String userId;
    List<OrderContentBean> data;
    private List<ServiceItemBean> checkList;
    private String orderTpye;
    public static final String UNCHECK = "0";
    public static final String CHECK = "1";
    TextView txtBack;

    @Override
    public void initListener() {
        orderId = getIntent().getStringExtra("orderId");
        ActivityStack.getInstance().pushActivity(this);

        setContentView(R.layout.activity_service_content);
        this.recycleview = (RecyclerView) findViewById(R.id.recycle_view);
        this.txtOrderTime = (TextView) findViewById(R.id.txt_order_time);
        this.txtUserName = (TextView) findViewById(R.id.txt_name);
        this.txtPhone = (TextView) findViewById(R.id.txt_phone);
        this.txtIsAccept = (TextView) findViewById(R.id.txt_is_accept);
        this.txtWeight = (TextView) findViewById(R.id.txt_weight);
        this.txtOrderType = (TextView) findViewById(R.id.txt_order_type);
        this.txtSex = (TextView) findViewById(R.id.txt_sex);
        this.txtAge = (TextView) findViewById(R.id.txt_age);
        this.txtBack = (TextView) findViewById(R.id.txt_back);
        this.txtTitle = findViewById(R.id.txt_title);
        this.imgBack = findViewById(R.id.img_back);
        this.btnSubmit = findViewById(R.id.btn_submit);
        this.txtInsanity = findViewById(R.id.txt_insanity);
        this.txtContagion = findViewById(R.id.txt_contagion);
        this.txtAddress = findViewById(R.id.txt_address);
        this.txtTitle.setText("服务详情");
        this.imgBack.setOnClickListener(this);
        this.txtBack.setOnClickListener(this);
        this.btnSubmit.setOnClickListener(this);
//        this.btnSubmit.setVisibility(View.GONE);
        this.sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        this.userId = sharedPreferences.getString(Const.USER_ID, null);
        this.recycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.recycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 5;
            }
        });
        if (orderId != null && userId != null) {
            mPresenter.selectOrderContent(orderId, userId);
        }

    }

    @Override
    public IOrderContentContract.Presenter createPresenter() {
        return new OrderContentPresenter();
    }

    @Override
    public IOrderContentContract.View createView() {
        return this;
    }

    List<OrderContentBean> xlist;

    @SuppressLint("SetTextI18n")
    @Override
    public void showOrderContent(ResultBean<List<OrderContentBean>> resultBean) {
        if (BuildConfig.DEBUG) Log.d("OrderContentActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            data = resultBean.getData();
            if (data != null) {
                if (data.size() > 0) {

                    OrderContentBean orderContentBean = data.get(0);
                    String patientname = orderContentBean.getPatientname();
                    this.txtAge.setText(orderContentBean.getWeightage());
                    this.txtUserName.setText(patientname);
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


                    this.txtSex.setText(orderContentBean.getPatientsex() != null ? (orderContentBean.getPatientsex().endsWith("1") ? "男" : "女") : "");
                    this.txtWeight.setText(orderContentBean.getPatientweight() != null ? (orderContentBean.getPatientweight() + "kg") : "");
                    this.txtOrderTime.setText(ToolUitls.timeFormart(orderContentBean.getBegindate()) + "~" + ToolUitls.timeFormart(orderContentBean.getEnddate()));
                    this.txtPhone.setText(orderContentBean.getPhone());

                    xlist = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        if (UNCHECK.equals(data.get(i).getUserid())) {
                            xlist.add(data.get(i));
                        }
                    }

                    if (BuildConfig.DEBUG) Log.d("OrderContentActivity", "xlist:" + xlist);
                    for (int i = 0; i < xlist.size(); i++) {
                        xlist.get(i).setCheck(true);
                    }

                    if (xlist.size() > 0) {

                        this.orderContentServiceAdapter = new OrderContentServiceAdapter(R.layout.item_order_content_service, xlist);
                        this.orderContentServiceAdapter.openLoadAnimation();
                        this.recycleview.setAdapter(orderContentServiceAdapter);
                        this.orderContentServiceAdapter.setOnItemClickListener(this);
                    }
                }
            }
        }
    }

    @Override
    public void showTakeOrderResult(ResultBean<String> resultBean) {
        if (BuildConfig.DEBUG) Log.d("OrderContentActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            ToolUitls.toast(this, "接单成功");
            Intent intent = new Intent();
            intent.setAction(Const.UPDATE_ORDER_STATE);
            sendBroadcast(intent);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1500);
        } else if (resultBean.getCode() == Const.RESULT_9999) {
            ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
        }
    }

    @Override
    public void showErr() {
        ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
    }

    List<OrderContentBean> list;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        }
        if (v.getId() == R.id.txt_back) {
            finish();
        }
        if (v.getId() == R.id.btn_submit) {
            String state = sharedPreferences.getString(Const.REAL_NAME_STATE, null);
            if (BuildConfig.DEBUG) Log.d("OrderContentActivity", "state == =" + state);
            String manyToMany = sharedPreferences.getString(Const.USER_MANY_TO_MANY, null);
            String oneToOne = sharedPreferences.getString(Const.USER_ONE_TO_ONE, null);
            String leave = sharedPreferences.getString(Const.USER_LEAVE, null);
            if (Const.USER_IS_LEAVE.equals(leave)) {
                ToolUitls.toast(this, "当前请假状态，不得接单！");
                return;
            }
            if (!(Const.USER_IS_MANY.equals(manyToMany) && Const.USER_NOT_ONE.equals(oneToOne))) {
                ToolUitls.toast(this, "请前往设置，开启多对多，才可接单！");
                return;
            }

            if (Const.REAL_NAME_STATE_9981.equals(state)) {
                ToolUitls.toast(this, "实名认证未通过，请重新认证！");
                goOtherActivity(this, RealNameActivity.class);
                return;
            } else if (Const.REAL_NAME_STATE_9982.equals(state)) {
                ToolUitls.toast(this, "还未实名认证，无法接单，请先实名认证！");
                goOtherActivity(this, RealNameActivity.class);
                return;
            } else if (Const.REAL_NAME_STATE_9983.equals(state)) {
                ToolUitls.toast(this, "审核中无法接单！");
                goOtherActivity(this, RealNameActivity.class);
                return;
            }


            if (orderContentServiceAdapter == null) {
                return;
            }

            list = new ArrayList<>();
            for (int i = 0; i < xlist.size(); i++) {
                if (xlist.get(i).isCheck()) {
                    if (xlist.get(i).isCheck()) {
                        list.add(xlist.get(i));
                    }
                }
            }
            if (list.size() <= 0) {
                ToolUitls.toast(this, "未选中任何服务项");
                return;
            }

            checkList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                checkList.add(new ServiceItemBean(list.get(i).getSeq(), list.get(i).getOrderid(), userId));
            }


            showDialog();
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CheckBox checkBox = view.findViewById(R.id.cb_check);
//        OrderContentBean orderContentBean = (OrderContentBean) xlist.get(position);
//        if (!orderContentBean.getUserid().equals("0")) {
//            ToolUitls.toast(this, "当前服务项已被接取");
//        } else {
//
//        }

        if (Const.ORDER_TYPE_1.equals(orderTpye) || Const.ORDER_TYPE_3.equals(orderTpye) || Const.ORDER_TYPE_3.equals(orderTpye)) {
            ToolUitls.toast(this, "此订单服务项不得取消");
        } else {
            if (BuildConfig.DEBUG) Log.d("OrderContentActivity", "++++++++++++++++");
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
                xlist.get(position).setCheck(false);
            } else {
                checkBox.setChecked(true);
                xlist.get(position).setCheck(true);
            }
            orderContentServiceAdapter.notifyDataSetChanged();
        }


    }

    CustomerDialog customerDialog;

    public void showDialog() {

        customerDialog = new CustomerDialog(this, "确认接单？");
        customerDialog.setOnDialogChildViewClickListener(this);
        customerDialog.show();

    }

    @Override
    public void onCancle() {
        if (customerDialog != null) {
            if (customerDialog.isShowing()) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setUserid("0");
                }
                customerDialog.dismiss();
            }
        }
    }

    @Override
    public void onSure() {
        if (customerDialog != null) {
            if (customerDialog.isShowing()) {
                customerDialog.dismiss();
                mPresenter.takeOrder(checkList);
            }
        }
    }
}

