package cn.org.happyhome.nursing.activity.realName.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.realName.contract.IRealNameContract;
import cn.org.happyhome.nursing.activity.realName.presenter.RealNamePresenter;
import cn.org.happyhome.nursing.adadpter.MyFramentAdapter;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.fragment.realName.view.RealNameFragment;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/11/22  14:25
 * - generate by MvpAutoCodePlus plugin.
 */

public class RealNameActivity extends BaseMvpActivity<IRealNameContract.View, IRealNameContract.Presenter> implements IRealNameContract.View, View.OnClickListener, ViewPager.OnPageChangeListener {


    public static final int PICK_PHOTO_ONE = 1;
    public static final int PICK_PHOTO_TWO = 2;
    private EditText editcardid;
    //    private RecyclerView recycleview;
    private ImageView imgBack;
    private TextView txtTitle;
    private Button btnSubmit;
    private ProgressBar progressBar;
    private PagerSlidingTabStrip ptrheader;
    TextView txtBack;
    List<String> list;
    List<File> filePaths;

    SharedPreferences sharedPreferences;
    String userId;

    String cardA;
    String cardB;

    //  1代表上传省份证  2 代表 健康证
    private int type = 1;

    String token;

    //图片 地址
    private Map<String, String> map;

    private MyFramentAdapter myFramentAdapter;
    private android.support.v4.view.ViewPager viewpager;
    private Button btnsubmit;
    private ProgressBar progress;
    private OnPhotoPickReciver onPhotoPickReciver;


    @Override
    public void initListener() {
        setContentView(R.layout.activity_real_name);
        initView();
        initReciver();
        map = new HashMap<>();

    }

    private void initReciver() {
        onPhotoPickReciver = new OnPhotoPickReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ONPHOTOPICK);
        this.registerReceiver(onPhotoPickReciver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(onPhotoPickReciver);
    }

    private void initView() {

        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        this.ptrheader = findViewById(R.id.ptr_header);
        this.token = sharedPreferences.getString(Const.QN_TOKEN, null);
        this.viewpager = (ViewPager) findViewById(R.id.view_pager);
        this.editcardid = (EditText) findViewById(R.id.edit_card_id);
        this.btnSubmit = (Button) findViewById(R.id.btn_submit);
        this.progressBar = (ProgressBar) findViewById(R.id.progress);
        this.imgBack = findViewById(R.id.img_back);
        this.txtTitle = findViewById(R.id.txt_title);
        this.btnSubmit.setOnClickListener(this);
        this.txtTitle.setText("实名认证");
        this.imgBack.setOnClickListener(this);
        list = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(RealNameFragment.getInstance(1));
        fragments.add(RealNameFragment.getInstance(2));

        ptrheader.setShouldExpand(true);
        ptrheader.setTextColor(Color.BLACK);
        ptrheader.setIndicatorHeight(7);
        ptrheader.setDividerColor(Color.WHITE);
        ptrheader.setUnderlineColor(Color.WHITE);
        ptrheader.setIndicatorColor(getResources().getColor(R.color.customer_blue));
        ptrheader.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                updateTextStyle(i);
                if (i == 0) {
                    type = 1;
                } else if (i == 1) {
                    type = 2;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        myFramentAdapter = new MyFramentAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(myFramentAdapter);
        viewpager.addOnPageChangeListener(this);
        ptrheader.setViewPager(viewpager);
        updateTextStyle(0);
    }

    private void updateTextStyle(int position) {
        LinearLayout tabsContainer = (LinearLayout) ptrheader.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            TextView textView = (TextView) tabsContainer.getChildAt(i);
            if (position == i) {
                textView.setTextSize(15);
                textView.setTextColor(getResources().getColor(R.color.customer_blue));
            } else {
                textView.setTextSize(12);
                textView.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public IRealNameContract.Presenter createPresenter() {
        return new RealNamePresenter();
    }

    @Override
    public IRealNameContract.View createView() {
        return this;
    }

    @Override
    public void showRealName(ResultBean<String> resultBean) {
        if (BuildConfig.DEBUG) Log.d("RealNameActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            ToolUitls.toast(this, "上传成功，请等待审核！");
            Intent intent = new Intent(Const.UPDATE_REAL_NAME_STATE);
            sendBroadcast(intent);
            finish();
        } else {
            list.clear();
            ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
        }
        this.progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showErr() {
        ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
        list.clear();
        this.progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        } else if (v.getId() == R.id.txt_back) {
            finish();
        } else if (v.getId() == R.id.btn_submit) {
            String cardId = editcardid.getText().toString();
            if (TextUtils.isEmpty(cardId)) {
                ToolUitls.toast(this, "请务必填写身份证号！");
                return;
            }

            if (cardId.length() != 18) {
                ToolUitls.toast(this, "请填写正确的身份证号！");
                return;
            }




            if (TextUtils.isEmpty(map.get("img1"))) {
                ToolUitls.toast(this, "请上传身份证正面！");
                return;
            }
            if (TextUtils.isEmpty(map.get("img1"))) {
                ToolUitls.toast(this, "请上传身份证反面！");
                return;
            }
            if (TextUtils.isEmpty(map.get("img3"))) {
                ToolUitls.toast(this, "请上传健康证！");
                return;
            }
            if (TextUtils.isEmpty(map.get("img4"))) {
                ToolUitls.toast(this, "请上传护士证！");
                return;
            }

            list.add(map.get("img1"));
            list.add(map.get("img2"));
            list.add(map.get("img3"));
            list.add(map.get("img4"));


            this.progressBar.setVisibility(View.VISIBLE);
            mPresenter.showRealName(userId, cardId, token, list);
        }
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {
            type = 1;
        } else if (i == 1) {
            type = 2;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public static final String ONPHOTOPICK = "ONPHOTOPICK";


    public class OnPhotoPickReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ONPHOTOPICK)) {
                String img1 = intent.getStringExtra("img1");
                String img2 = intent.getStringExtra("img2");


                if (type == 1) {
                    map.put("img1", img1);
                    map.put("img2", img2);
                } else if (type == 2) {
                    map.put("img3", img1);
                    map.put("img4", img2);
                }

            }

        }
    }
}

