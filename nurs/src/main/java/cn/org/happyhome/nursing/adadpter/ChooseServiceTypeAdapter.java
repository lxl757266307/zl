package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.ServiceTypeBean;

public class ChooseServiceTypeAdapter extends BaseQuickAdapter<ServiceTypeBean, BaseViewHolder> {

    public ChooseServiceTypeAdapter(int layoutResId, @Nullable List<ServiceTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceTypeBean item) {

        String note1 = item.getNote1();
//        1 代表 已被选中
        if ("1".equals(note1)) {
            helper.setChecked(R.id.cb_check, true);
        } else {
            helper.setChecked(R.id.cb_check, false);
        }
        helper.setText(R.id.txt_service_name, item.getServicetypename());

    }
}
