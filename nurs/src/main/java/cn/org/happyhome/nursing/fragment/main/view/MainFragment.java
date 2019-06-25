package cn.org.happyhome.nursing.fragment.main.view;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpFragment;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.StatusBarUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.library.view.BannerViewPager;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.newContent.NewContentActivity;
import cn.org.happyhome.nursing.adadpter.NewsAdapter;
import cn.org.happyhome.nursing.bean.NewsInfomationBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.fragment.main.contract.IMainFragmentContract;
import cn.org.happyhome.nursing.fragment.main.presenter.MainFragmentPresenter;
import cn.org.happyhome.nursing.view.GlideImageLoader;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/4 0004  9:35
 * - generate by MvpAutoCodePlus plugin.
 */

public class MainFragment extends BaseMvpFragment<IMainFragmentContract.View, IMainFragmentContract.Presenter> implements IMainFragmentContract.View, BaseQuickAdapter.OnItemClickListener, OnLoadMoreListener, OnRefreshListener, OnRefreshLoadMoreListener {

    private android.support.v7.widget.RecyclerView recycleview;
    private NewsAdapter newsAdapter;
    private ImageView imgZX;
    private TextView txtTitle;
    private android.widget.LinearLayout layoutnodata;
    private com.scwang.smartrefresh.layout.SmartRefreshLayout refreshLayout;
    private int isRefresh = 1;//1 代表刷新  2 代表加载更多
    private int page = 1;
    private int pageSize = 10;
    private List<NewsInfomationBean> list;
    private RecyclerView recyclerView;
    private Banner banner;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void viewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        StatusBarUtils.setStatusBar(getActivity(), false, true);
        this.txtTitle.setText("主页");
        this.imgZX.setVisibility(View.INVISIBLE);
        this.imgZX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class clazz = null;
                try {
                    clazz = Class.forName("cn.org.happyhome.ordertaking.activity.zxing.ZXingActivity");
                    Intent intent = new Intent(getActivity(), clazz);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        this.recycleview.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        this.recycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) % 2 != 0) {
                    outRect.left = 5;
                }
                if (parent.getChildAdapterPosition(view) % 2 == 0) {
                    outRect.right = 5;
                    outRect.left = 2;
                }
                outRect.top = 5;
                outRect.bottom = 5;


            }
        });

        list = new ArrayList<>();
        refreshLayout.setOnRefreshLoadMoreListener(this);
        mPresenter.showLooperImage();
        mPresenter.showNewsList();


    }

    @Override
    public View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.banner = (Banner) view.findViewById(R.id.view_pager);
        this.refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        this.layoutnodata = (LinearLayout) view.findViewById(R.id.layout_nodata);
        this.recycleview = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.imgZX = (ImageView) view.findViewById(R.id.img_camera);
        this.txtTitle = (TextView) view.findViewById(R.id.txt_title);
        return view;
    }

    @Override
    public IMainFragmentContract.Presenter createPresenter() {
        return new MainFragmentPresenter();
    }

    @Override
    public IMainFragmentContract.View createView() {
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void showLooperImage(ResultBean<List<NewsInfomationBean>> listResultBean) {
        if (BuildConfig.DEBUG) Log.d("MainFragment", "listResultBean:" + listResultBean);
        if (listResultBean.getCode() == Const.RESULT_OK) {
            List<NewsInfomationBean> data = listResultBean.getData();
            if (data != null) {
                if (data.size() > 0) {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        list.add(data.get(i).getAddressID());
                    }
                    banner.setImageLoader(new GlideImageLoader());
                    banner.setBannerAnimation(Transformer.ZoomOutSlide);
                    banner.isAutoPlay(true);
                    banner.setDelayTime(2000);
                    banner.setIndicatorGravity(BannerConfig.CENTER);
                    banner.setImages(list);
                    banner.start();
                } else {
                    banner.setVisibility(View.GONE);
                }
            } else {
                banner.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showNewsList(ResultBean<List<NewsInfomationBean>> newsList) {
        if (BuildConfig.DEBUG) Log.d("MainFragment", "newsList:" + newsList);
        if (newsList.getCode() == Const.RESULT_OK) {
            List<NewsInfomationBean> data = newsList.getData();
            if (data != null) {
                list.addAll(data);
                if (list.size() == 0) {
                    layoutnodata.setVisibility(View.VISIBLE);
                    return;
                }
                if (newsAdapter == null) {
                    newsAdapter = new NewsAdapter(R.layout.item_news, list);
                    newsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                    newsAdapter.setOnItemClickListener(this);
                } else {
                    newsAdapter.setNewData(list);
                }
                recycleview.setAdapter(newsAdapter);
            }

        }

    }

    @Override
    public void showErr() {
        ToolUitls.toast(getActivity(), "服务器繁忙，请稍后再试！");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NewsInfomationBean newsInfomationBean = (NewsInfomationBean) adapter.getData().get(position);
        Intent intent = new Intent(getActivity(), NewContentActivity.class);
        intent.putExtra("content", newsInfomationBean.getContent());
        startActivity(intent);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefresh = 2;
        page++;
        mPresenter.showNewsList();
        refreshLayout.finishLoadMore(2000);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        if (BuildConfig.DEBUG) Log.d("MainFragment", "刷新");
        isRefresh = 1;
        page = 1;
        list.clear();
        layoutnodata.setVisibility(View.GONE);
        mPresenter.showNewsList();
        refreshLayout.finishRefresh(2000);
    }
}

