package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.VOrderMm;

public class OrderHistoryAdapter extends BaseQuickAdapter<VOrderMm, BaseViewHolder> {

    String type;

    public OrderHistoryAdapter(int layoutResId, @Nullable List<VOrderMm> data, String type) {
        super(layoutResId, data);
        this.type = type;
        if (BuildConfig.DEBUG) Log.d("OrderHistoryAdapter", "type===" + type);
    }

    @Override
    protected void convert(BaseViewHolder helper, VOrderMm item) {
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
//        待完成时可申诉 1 代表待完成
        if ("1".equals(type)) {
            helper.setVisible(R.id.btn_appeal, true);
            helper.addOnClickListener(R.id.btn_appeal);
            helper.setImageResource(R.id.img_type, R.mipmap.waitdo);
        } else if ("T".equals(type)) {
            helper.setImageResource(R.id.img_type, R.mipmap.haddid);
        }else if ("2".equals(type)){
            helper.setImageResource(R.id.img_type, R.mipmap.hadcancle);
        }
    }


}
