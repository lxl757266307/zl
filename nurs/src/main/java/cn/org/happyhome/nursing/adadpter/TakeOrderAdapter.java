package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.nursing.R;

public class TakeOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TakeOrderAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView txt = helper.getView(R.id.txt_message);
        txt.setText(item);
    }
}
