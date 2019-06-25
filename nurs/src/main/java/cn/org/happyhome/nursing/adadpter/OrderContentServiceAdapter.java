package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.OrderContentBean;

public class OrderContentServiceAdapter extends BaseQuickAdapter<OrderContentBean, BaseViewHolder> {
    public OrderContentServiceAdapter(int layoutResId, @Nullable List<OrderContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderContentBean item) {
        String userid = item.getUserid();
//        0表示未被选择
//        if (!"0".equals(userid)) {
//            CheckBox checkBox = helper.getView(R.id.cb_check);
//            checkBox.setButtonDrawable(R.mipmap.bandcheck);
//            checkBox.setPadding(10, 0, 0, 0);
////            checkBox.setChecked(true);
////            checkBox.setClickable(false);
//        }
        helper.setChecked(R.id.cb_check, item.isCheck());


        if (BuildConfig.DEBUG) Log.d(TAG, "item:" + item);
        helper.setText(R.id.txt_service_name, item.getServicecontentname());

    }
}
