package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.VOrderMm;

public class ManyToManyAdapter extends BaseQuickAdapter<VOrderMm, BaseViewHolder> {

    int resId;

    public ManyToManyAdapter(int layoutResId, @Nullable List<VOrderMm> data, int resId) {
        super(layoutResId, data);
        this.resId = resId;
    }

    @Override
    protected void convert(BaseViewHolder helper, VOrderMm item) {
        helper.setImageResource(R.id.img_type, resId);
        helper.setText(R.id.txt_order_price, String.valueOf(item.getPrice()) + "元 ");
//        01 日间  02夜间  03全天  04临时
        if ("01".equals(item.getServicetypeid())) {
            helper.setText(R.id.txt_order_time, ToolUitls.timeFormart(item.getBegindate()) + " 07:00~ " + ToolUitls.timeFormart(item.getEnddate()) + " 19:00");
        }
        if ("02".equals(item.getServicetypeid())) {
            helper.setText(R.id.txt_order_time, ToolUitls.timeFormart(item.getBegindate()) + " 19:00~ " + ToolUitls.timeFormart(item.getEnddate()) + " 07:00");
        }
        if ("03".equals(item.getServicetypeid())) {
            helper.setText(R.id.txt_order_time, ToolUitls.timeFormart(item.getBegindate()) + " 00:00~ " + ToolUitls.timeFormart(item.getEnddate()) + " 23:59");
        }
        if ("04".equals(item.getServicetypeid())) {
            helper.setText(R.id.txt_order_time, ToolUitls.timeFormart(item.getBegindate()) + "~" + ToolUitls.timeFormart(item.getEnddate()));
        }
        if ("06".equals(item.getServicetypeid())) {
            helper.setText(R.id.txt_order_time, ToolUitls.timeFormart(item.getBegindate()) + "~" + ToolUitls.timeFormart(item.getEnddate()));
        }
    }


}
