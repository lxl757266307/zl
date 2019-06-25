package cn.org.happyhome.nursing.activity.integral.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.integral.contract.IIntegrealContract;
import cn.org.happyhome.nursing.activity.integral.presenter.IntegrealPresenter;
import cn.org.happyhome.nursing.activity.orderDetail.view.OrderHistoryDetailActivity;
import cn.org.happyhome.nursing.adadpter.IntegralAdapter;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.TuserSumdetails;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/16  10:48
 * - generate by MvpAutoCodePlus plugin.
 */

public class IntegrealActivity extends BaseMvpActivity<IIntegrealContract.View, IIntegrealContract.Presenter> implements IIntegrealContract.View, View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private android.widget.TextView txtcurrtotalintegral;
    private android.widget.LinearLayout layoutintegral;
    private android.widget.LinearLayout layoutNoData;
    private android.support.v7.widget.RecyclerView recyclerView;
    private com.scwang.smartrefresh.layout.SmartRefreshLayout refreshLayout;
    private TextView txtTitle;
    private ImageView imageBack;
    private SharedPreferences sharedPreferences;
    private String userId;
    private int page=1;
    private final int pageSize = 10;
    private IntegralAdapter integralAdapter;
    private List<TuserSumdetails> list;
    private int isRefresh = 1;//1 代表刷新  2 代表加载更多
    TextView txtBack;
    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);
        setContentView(R.layout.activity_integral);
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        this.refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.layoutNoData = (LinearLayout) findViewById(R.id.layout_nodata);
        this.layoutintegral = (LinearLayout) findViewById(R.id.layout_integral);
        this.txtcurrtotalintegral = (TextView) findViewById(R.id.txt_curr_total_integral);
        this.txtBack = (TextView) findViewById(R.id.txt_back);
        this.txtTitle = findViewById(R.id.txt_title);
        this.imageBack = findViewById(R.id.img_back);
        this.imageBack.setOnClickListener(this);
        this.txtBack.setOnClickListener(this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) == list.size() - 1) {
                    outRect.bottom = 1;
                }
                outRect.top = 1;
            }
        });
        list = new ArrayList<>();
        this.txtTitle.setText("积分明细");
        if (userId != null) {
            mPresenter.showIntegral(page, pageSize, userId);
        } else {
            layoutNoData.setVisibility(View.VISIBLE);
        }

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = 1;
                page = 1;
                if (userId != null) {
                    mPresenter.showIntegral(page, pageSize, userId);
                }
                refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                isRefresh = 2;
                page++;
                if (BuildConfig.DEBUG) Log.d("IntegrealActivity", "page:-====" + page);
                if (userId != null) {
                    mPresenter.showIntegral(page, pageSize, userId);
                }
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public IIntegrealContract.Presenter createPresenter() {
        return new IntegrealPresenter();
    }

    @Override
    public IIntegrealContract.View createView() {
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        } if (v.getId() == R.id.txt_back) {
            finish();
        }
    }

    @Override
    public void showIntegral(ResultBean<List<TuserSumdetails>> resultBean) {
        if (BuildConfig.DEBUG) Log.d("IntegrealActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            List<TuserSumdetails> data = resultBean.getData();
            if (data != null) {
                if (data.size() > 0) {
                    if (integralAdapter == null) {
                        txtcurrtotalintegral.setText(resultBean.getMessage());
                        list.addAll(data);
                        integralAdapter = new IntegralAdapter(R.layout.item_integral, list);
                        integralAdapter.setOnItemClickListener(this);
                    } else {
                        if (isRefresh == 1) {
                            list.clear();
                            list.addAll(data);
                        } else if (isRefresh == 2) {
                            list.addAll(data);
                        }
                        integralAdapter.setNewData(list);
                    }
                    recyclerView.setAdapter(integralAdapter);
                }

                if (BuildConfig.DEBUG) Log.d("IntegrealActivity", "list.size():" + list.size());
                if (list.size() > 0) {
                    layoutNoData.setVisibility(View.GONE);
                } else {
                    layoutNoData.setVisibility(View.VISIBLE);
                }
            } else {
                layoutNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void showErr() {
        ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, OrderHistoryDetailActivity.class);
        intent.putExtra("orderId",list.get(position).getOriginate());
        startActivity(intent);
    }
}

