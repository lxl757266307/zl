package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.ServiceContentBean;

public class ChooseServiceContentAdapter extends BaseQuickAdapter<ServiceContentBean, BaseViewHolder> {

    public static final String CHECKED = "1";
    public static final String UNCHECKED = "0";

    public ChooseServiceContentAdapter(int layoutResId, @Nullable List<ServiceContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceContentBean item) {
        String servicecontentname = item.getServicecontentname();
        String note1 = item.getNote1();
        if (CHECKED.equals(note1)) {
            helper.setChecked(R.id.cb_check, true);
        } else if (UNCHECKED.equals(note1)) {
            helper.setChecked(R.id.cb_check, false);
        }
        if (servicecontentname != null) {
            helper.setText(R.id.txt_service_name, servicecontentname);
        }
    }
}
