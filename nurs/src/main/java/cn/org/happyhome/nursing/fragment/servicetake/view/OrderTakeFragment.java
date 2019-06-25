package cn.org.happyhome.nursing.fragment.servicetake.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.utils.StatusBarUtils;
import cn.org.happyhome.library.view.NoScrollViewPager;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.fragment.service.view.ManyToManyFragment;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/27 0027  11:58
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderTakeFragment extends Fragment {


    private PagerSlidingTabStrip tabs;
    private NoScrollViewPager viewpager;
    private TextView txtTitle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        this.viewpager = (NoScrollViewPager) rootView.findViewById(R.id.view_pager);
        this.tabs = rootView.findViewById(R.id.tabs);
        this.viewpager.setNoScroll(true);
        this.txtTitle = rootView.findViewById(R.id.txt_title);
        this.txtTitle.setText("服务大厅");
        return rootView;
    }

    //全天
    public static final String ORDER_O6 = "06";
    //日间
    public static final String ORDER_O1 = "01";
    //夜间
    public static final String ORDER_O2 = "02";
    //临时
    public static final String ORDER_O4 = "04";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtils.setStatusBar(getActivity(), false, true);
        List<Fragment> list = new ArrayList<>();
        list.add(ManyToManyFragment.newInstance(ORDER_O6, R.mipmap.allday));
        list.add(ManyToManyFragment.newInstance(ORDER_O1, R.mipmap.light));
        list.add(ManyToManyFragment.newInstance(ORDER_O2, R.mipmap.night));
        list.add(ManyToManyFragment.newInstance(ORDER_O4, R.mipmap.temporary));

        OrderPagerFrafmentAdapter orderPagerFrafmentAdapter = new OrderPagerFrafmentAdapter(getChildFragmentManager(), list);
        viewpager.setAdapter(orderPagerFrafmentAdapter);
        tabs.setShouldExpand(true);
        tabs.setTextColor(Color.BLACK);
        tabs.setIndicatorHeight(7);
        tabs.setDividerColor(Color.WHITE);
        tabs.setUnderlineColor(Color.WHITE);
        tabs.setIndicatorColor(getResources().getColor(R.color.customer_blue));
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        tabs.setViewPager(viewpager);
        updateTextStyle(0);
    }

    private void updateTextStyle(int position) {
        LinearLayout tabsContainer = (LinearLayout) tabs.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            TextView textView = (TextView) tabsContainer.getChildAt(i);
            if (position == i) {
                textView.setTextSize(16);
                textView.setTextColor(getResources().getColor(R.color.customer_blue));
            } else {
                textView.setTextSize(14);
                textView.setTextColor(Color.BLACK);
            }
        }
    }


    public class OrderPagerFrafmentAdapter extends FragmentPagerAdapter {

        String[] title = {"全天", "日间", "夜间", "临时"};
        List<Fragment> list;

        public OrderPagerFrafmentAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
            if (BuildConfig.DEBUG) Log.d("OrderPagerFrafmentAdapt", "list.size():" + list.size());
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }


    }

}

