package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.TuserSumdetails;
import cn.org.happyhome.nursing.bean.VOrderMm;

public class IntegralAdapter extends BaseQuickAdapter<TuserSumdetails, BaseViewHolder> {

    public IntegralAdapter(int layoutResId, @Nullable List<TuserSumdetails> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuserSumdetails item) {
        helper.setText(R.id.txt_time, ToolUitls.timeFormart(item.getCreattime()));
        if (item.getIntegration() > 0) {
            helper.setText(R.id.txt_integral, "+" + String.valueOf(item.getIntegration()));
        } else {
            helper.setText(R.id.txt_integral, String.valueOf(item.getIntegration()));
        }
    }


}
