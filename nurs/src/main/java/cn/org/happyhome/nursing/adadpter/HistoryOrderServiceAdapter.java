package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.OrderContentBean;

public class HistoryOrderServiceAdapter extends BaseQuickAdapter<OrderContentBean, BaseViewHolder> {
    public HistoryOrderServiceAdapter(int layoutResId, @Nullable List<OrderContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderContentBean item) {
        helper.setText(R.id.txt_service_name, item.getServicecontentname());
    }
}
