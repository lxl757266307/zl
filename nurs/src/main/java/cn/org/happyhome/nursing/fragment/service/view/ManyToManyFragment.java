package cn.org.happyhome.nursing.fragment.service.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpFragment;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.orderContent.view.OrderContentActivity;
import cn.org.happyhome.nursing.activity.realName.view.RealNameActivity;
import cn.org.happyhome.nursing.adadpter.ManyToManyAdapter;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.VOrderMm;
import cn.org.happyhome.nursing.fragment.service.contract.IOrderByStatusContract;
import cn.org.happyhome.nursing.fragment.service.presenter.ManyToMantPresenter;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/25  14:29
 * - generate by MvpAutoCodePlus plugin.
 */

@SuppressLint("ValidFragment")
public class ManyToManyFragment extends BaseMvpFragment<IOrderByStatusContract.View, IOrderByStatusContract.Presenter> implements IOrderByStatusContract.View, BaseQuickAdapter.OnItemClickListener {

    private static final String TYPE = "TYPE";
    private static final String RES_ID = "RES_ID";
    private RecyclerView recyclerView;
    private com.scwang.smartrefresh.layout.SmartRefreshLayout refreshLayout;
    private ManyToManyAdapter manyToManyAdapter;
    private int isRefresh = 1;//1 代表刷新  2 代表加载更多
    private int page = 0;
    private int pageSize = 1000;
    private LinearLayout layoutNoData;
    List<VOrderMm> list;
    int resId;
    String type;
    public static final int REFRESH = 1;
    public static final int LOADMORE = 2;
    UpdateOrderStateReciver updateOrderStateReciver;

    private SharedPreferences sharedPreferences;

    public static ManyToManyFragment newInstance(String type, int resId) {
        Bundle args = new Bundle();
        args.putSerializable(TYPE, type);
        args.putSerializable(RES_ID, resId);
        ManyToManyFragment fragment = new ManyToManyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void viewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initData();
        initView();
        initReciver();
        initNet();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (updateOrderStateReciver != null) {
            getActivity().unregisterReceiver(updateOrderStateReciver);
        }
    }

    private void initNet() {
        list = new ArrayList<>();
        if (type != null) {
            mPresenter.selectOrderByType(type, page, pageSize);
        } else {
            layoutNoData.setVisibility(View.VISIBLE);
        }
    }

    private void initReciver() {
        updateOrderStateReciver = new UpdateOrderStateReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.UPDATE_ORDER_STATE);
        getActivity().registerReceiver(updateOrderStateReciver, intentFilter);
    }

    private String realState;

    private void initData() {
        type = (String) getArguments().getSerializable(TYPE);
        resId = (int) getArguments().getSerializable(RES_ID);
        sharedPreferences = getActivity().getSharedPreferences(Const.USER_INFO, Context.MODE_PRIVATE);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) == list.size() - 1) {
                    outRect.bottom = 2;
                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = REFRESH;
                page = 1;
                if (type != null) {
                    mPresenter.selectOrderByType(type, page, pageSize);
                }
                refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                isRefresh = LOADMORE;
                page++;
                if (BuildConfig.DEBUG) Log.d("ManyToManyFragment", "page:-====" + page);
                if (type != null) {
                    mPresenter.selectOrderByType(type, page, pageSize);
                }
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_many_to_many, container, false);
        this.refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.layoutNoData = view.findViewById(R.id.layout_nodata);
        return view;
    }

    @Override
    public IOrderByStatusContract.Presenter createPresenter() {
        return new ManyToMantPresenter();
    }

    @Override
    public IOrderByStatusContract.View createView() {
        return this;
    }

    @Override
    public void showOrderList(ResultBean<List<VOrderMm>> resultBean) {
        if (BuildConfig.DEBUG)
            Log.d("ManyToManyFragment", "resultBean.getData():" + resultBean.getData());
        if (resultBean.getCode() == Const.RESULT_OK) {
            List<VOrderMm> data = resultBean.getData();
            if (data != null) {
                if (data.size() > 0) {
                    if (manyToManyAdapter == null) {
                        list.addAll(data);
                        manyToManyAdapter = new ManyToManyAdapter(R.layout.item_many_to_many, list, resId);
                        manyToManyAdapter.setOnItemClickListener(this);
                    } else {
                        if (isRefresh == REFRESH) {
                            list.clear();
                            list.addAll(data);
                        } else if (isRefresh == LOADMORE) {
                            list.addAll(data);
                        }
                        manyToManyAdapter.setNewData(list);
                    }
                    recyclerView.setAdapter(manyToManyAdapter);
                }

                if (list.size() > 0) {
                    layoutNoData.setVisibility(View.GONE);
                } else {
                    layoutNoData.setVisibility(View.VISIBLE);
                }
            } else {
                layoutNoData.setVisibility(View.VISIBLE);
            }
        } else {
            layoutNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showErr() {
        ToolUitls.toast(getActivity(), "服务器繁忙，请稍后再试！");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        realState = sharedPreferences.getString(Const.REAL_NAME_STATE, null);

        if (Const.REAL_NAME_STATE_9981.equals(realState)) {
            ToolUitls.toast(getActivity(), "实名认证未通过，请重新认证！");
            startActivity(new Intent(getActivity(), RealNameActivity.class));
        } else if (Const.REAL_NAME_STATE_9982.equals(realState)) {
            ToolUitls.toast(getActivity(), "接单前，请先实名认证！");
            startActivity(new Intent(getActivity(), RealNameActivity.class));
        } else if (Const.REAL_NAME_STATE_9983.equals(realState)) {
            ToolUitls.toast(getActivity(), "实名认证审核中，请耐心等待！");
        } else if (Const.REAL_NAME_STATE_9987.equals(realState)) {
            Intent intent = new Intent(getActivity(), OrderContentActivity.class);
            intent.putExtra("orderId", list.get(position).getOrderid());
            startActivity(intent);
        } else {
            ToolUitls.toast(getActivity(), "实名认证未通过，请重新认证！");
            startActivity(new Intent(getActivity(), RealNameActivity.class));
        }


    }

    public class UpdateOrderStateReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Const.UPDATE_ORDER_STATE.equals(intent.getAction())) {
                page = 1;
                isRefresh = REFRESH;
                mPresenter.selectOrderByType(type, page, pageSize);
            }
        }
    }


}

