package cn.org.happyhome.nursing.fragment.orderHistory.view;

import android.content.Context;
import android.content.Intent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpFragment;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.appeal.view.AppealActivity;
import cn.org.happyhome.nursing.activity.orderDetail.view.OrderHistoryDetailActivity;
import cn.org.happyhome.nursing.adadpter.ManyToManyAdapter;
import cn.org.happyhome.nursing.adadpter.OrderHistoryAdapter;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.VOrderMm;
import cn.org.happyhome.nursing.fragment.orderHistory.contract.IOrderHistoryContract;
import cn.org.happyhome.nursing.fragment.orderHistory.presenter.OrderHistoryPresenter;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/29  16:12
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderHistoryFragment extends BaseMvpFragment<IOrderHistoryContract.View, IOrderHistoryContract.Presenter> implements IOrderHistoryContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private static final String TYPE = "TYPE";
    private RecyclerView recyclerView;
    private com.scwang.smartrefresh.layout.SmartRefreshLayout refreshLayout;
    private OrderHistoryAdapter orderHistoryAdapter;
    private int isRefresh = 1;//1 代表刷新  2 代表加载更多
    private int page = 1;
    private int pageSize = 10;
    private List<VOrderMm> list;
    private String userId;
    private SharedPreferences sharedPreferences;
    private String type;
    private LinearLayout layoutNoData;

    public static OrderHistoryFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putSerializable(TYPE, type);
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void viewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        type = (String) getArguments().getSerializable(TYPE);
        if (BuildConfig.DEBUG) Log.d("OrderHistoryFragment", "type==" + type);
        sharedPreferences = getActivity().getSharedPreferences(Const.USER_INFO, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        if (BuildConfig.DEBUG) Log.d("OrderHistoryFragment", "userId==" + userId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) == list.size() - 1) {
                    outRect.bottom = 10;
                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = 1;
                page = 1;
                if (userId != null) {
                    getInfo(type);
                }
                refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                isRefresh = 2;
                page++;
                if (BuildConfig.DEBUG) Log.d("OrderHistoryFragment", "page:-====" + page);
                if (userId != null) {
                    getInfo(type);
                }
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


        if (BuildConfig.DEBUG)
            Log.d("OrderHistoryFragment", "new Date():==" + new Date().getTime());

        list = new ArrayList<>();

        getInfo(type);


    }

    public void getInfo(String type) {
        if (userId != null) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", userId);
            map.put("status", type);
            map.put("page", String.valueOf(page));
            map.put("pageSize", String.valueOf(pageSize));
            mPresenter.showOrderList(map);
        } else {
            layoutNoData.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        this.refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.layoutNoData = view.findViewById(R.id.layout_nodata);
        return view;
    }


    @Override
    public void showOrderList(ResultBean<List<VOrderMm>> resultBean) {
        if (BuildConfig.DEBUG)
            Log.d("OrderHistoryFragment", "resultBean" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            List<VOrderMm> data = resultBean.getData();
            if (data != null) {
                if (data.size() > 0) {
                    if (orderHistoryAdapter == null) {
                        list.addAll(data);
                        orderHistoryAdapter = new OrderHistoryAdapter(R.layout.item_order_history, list, type);
                        orderHistoryAdapter.setOnItemClickListener(this);
                        orderHistoryAdapter.setOnItemChildClickListener(this);
                    } else {
                        if (isRefresh == 1) {
                            list.clear();
                            list.addAll(data);
                        } else if (isRefresh == 2) {
                            list.addAll(data);
                        }
                        orderHistoryAdapter.setNewData(list);
                    }
                    recyclerView.setAdapter(orderHistoryAdapter);
                }
                if (!(list.size() > 0)) {
                    layoutNoData.setVisibility(View.VISIBLE);
                } else {
                    layoutNoData.setVisibility(View.GONE);
                }
            } else {
                layoutNoData.setVisibility(View.VISIBLE);
            }
        }else {
            layoutNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showErr() {
        ToolUitls.toast(getActivity(), "服务器繁忙，请稍后再试！");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (BuildConfig.DEBUG)
            Log.d("OrderHistoryFragment", "orderId=" + list.get(position).getOrderid());
        Intent intent = new Intent(getActivity(), OrderHistoryDetailActivity.class);
        intent.putExtra("orderId", list.get(position).getOrderid());
        startActivity(intent);
    }

    @Override
    public IOrderHistoryContract.Presenter createPresenter() {
        return new OrderHistoryPresenter();
    }

    @Override
    public IOrderHistoryContract.View createView() {
        return this;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        VOrderMm vOrderMm = list.get(position);
        Intent intent = new Intent(getActivity(), AppealActivity.class);
        intent.putExtra("orderId", vOrderMm.getOrderid());
        startActivity(intent);
    }
}

