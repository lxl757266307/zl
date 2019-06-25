package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.library.utils.ScreenUtils;
import cn.org.happyhome.library.view.RoundImageView;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.NewsInfomationBean;

public class NewsAdapter extends BaseQuickAdapter<NewsInfomationBean, BaseViewHolder> {
    public NewsAdapter(int layoutResId, @Nullable List<NewsInfomationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsInfomationBean item) {
//        helper.setImageResource(R.id.img_news, item);
        RoundImageView roundImageView = helper.getView(R.id.img_news);
        int screenWidth = ScreenUtils.getScreenWidth(helper.itemView.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2 - 10, screenWidth / 2 - 10);
        roundImageView.setLayoutParams(layoutParams);
        if (helper.getAdapterPosition() % 2 == 0) {
            roundImageView.setCropCorners(false, false, true, true, 1, 0, 10);
        } else {
            roundImageView.setCropCorners(true, true, false, false, 1, 0, 10);
        }
        Glide.with(helper.itemView.getContext()).load(item.getAddressID()).into(roundImageView);
    }
}
