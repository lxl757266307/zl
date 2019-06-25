package cn.org.happyhome.nursing.activity.orderHistory.view;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.orderHistory.contract.IOrderHistoryContract;
import cn.org.happyhome.nursing.activity.orderHistory.presenter.OrderHistoryPresenter;
import cn.org.happyhome.nursing.fragment.orderHistory.view.OrderHistoryFragment;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/29  16:01
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderHistoryActivity extends BaseMvpActivity<IOrderHistoryContract.View, IOrderHistoryContract.Presenter> implements IOrderHistoryContract.View, View.OnClickListener {

    private android.support.v4.view.ViewPager viewpager;
    private com.astuetz.PagerSlidingTabStrip ptrheader;
    private TextView txtTitle;
    private ImageView imageBack;
    private List<Fragment> list;
    TextView txtBack;
    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);

        setContentView(R.layout.activity_order_history);
        this.ptrheader = (PagerSlidingTabStrip) findViewById(R.id.ptr_header);
        this.viewpager = (ViewPager) findViewById(R.id.view_pager);
        this.txtTitle = findViewById(R.id.txt_title);
        this.imageBack = findViewById(R.id.img_back);
        this.txtBack = findViewById(R.id.txt_back);
        this.imageBack.setOnClickListener(this);
        this.txtBack.setOnClickListener(this);
        this.txtTitle.setText("历史订单");
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
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        list = new ArrayList<>();
        /*订单状态 1 代表待完成    T 代表已完成   2代表已取消*/
        list.add(OrderHistoryFragment.newInstance("1"));
        list.add(OrderHistoryFragment.newInstance("T"));
        list.add(OrderHistoryFragment.newInstance("2"));
        OrderHistoryFragmentAdapter orderHistoryFragmentAdapter = new OrderHistoryFragmentAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(orderHistoryFragmentAdapter);
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
    public IOrderHistoryContract.Presenter createPresenter() {
        return new OrderHistoryPresenter();
    }

    @Override
    public IOrderHistoryContract.View createView() {
        return this;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    public class OrderHistoryFragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> list;
        String[] titles = {"待完成", "已完成", "已取消"};

        public OrderHistoryFragmentAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}

