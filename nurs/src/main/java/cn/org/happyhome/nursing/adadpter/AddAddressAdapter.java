package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.AddAddressBean;
import cn.org.happyhome.nursing.bean.Address;

public class AddAddressAdapter extends BaseQuickAdapter<AddAddressBean, BaseViewHolder> {

    public AddAddressAdapter(int layoutResId, @Nullable List<AddAddressBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddAddressBean item) {
        helper.setText(R.id.txt_address, item.getHospitalname());
        Glide.with(mContext).load(R.mipmap.header).into((ImageView) helper.getView(R.id.img_header));
        helper.setChecked(R.id.cb_check, item.isCheck());

    }
}
