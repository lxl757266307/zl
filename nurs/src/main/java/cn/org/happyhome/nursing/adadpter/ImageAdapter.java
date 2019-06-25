package cn.org.happyhome.nursing.adadpter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.library.utils.ScreenUtils;
import cn.org.happyhome.nursing.R;

public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageView imageView = helper.getView(R.id.img_appeal);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ScreenUtils.getScreenWidth(helper.itemView.getContext()) / 3, ScreenUtils.getScreenWidth(helper.itemView.getContext()) / 3));
        Glide.with(helper.itemView).load(item).into(imageView);
        helper.addOnClickListener(R.id.img_delete);
    }
}
