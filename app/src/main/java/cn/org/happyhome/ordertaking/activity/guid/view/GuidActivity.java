package cn.org.happyhome.ordertaking.activity.guid.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.nursing.activity.main.view.MainActivity;
import cn.org.happyhome.ordertaking.R;
import cn.org.happyhome.ordertaking.activity.guid.contract.IGuidContract;
import cn.org.happyhome.ordertaking.activity.guid.presenter.GuidPresenter;
import cn.org.happyhome.ordertaking.activity.login.view.LoginActivity;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/22 0022  11:04
 * - generate by MvpAutoCodePlus plugin.
 */

public class GuidActivity extends BaseMvpActivity<IGuidContract.View, IGuidContract.Presenter> implements IGuidContract.View {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    int[] imgs = {R.mipmap.g1, R.mipmap.g2, R.mipmap.g3, R.mipmap.g4};
    @BindView(R.id.txt_next)
    TextView txtNext;

    @Override
    public void initListener() {
    }

    @Override
    public IGuidContract.Presenter createPresenter() {
        return new GuidPresenter();
    }

    @Override
    public IGuidContract.View createView() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);
        ButterKnife.bind(this);
        ImgPagerAdapter imgPagerAdapter = new ImgPagerAdapter();
        viewPager.setAdapter(imgPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 3) {
                    txtNext.setVisibility(View.VISIBLE);
                } else {
                    txtNext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick(R.id.txt_next)
    public void next() {
        goOtherActivity(this, LoginActivity.class);
        finish();
    }


    public class ImgPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(imgs[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            object = null;
        }
    }


}

