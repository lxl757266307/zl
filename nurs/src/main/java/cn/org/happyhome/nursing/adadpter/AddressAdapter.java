package cn.org.happyhome.nursing.adadpter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.Address;

public class AddressAdapter extends BaseQuickAdapter<Address, BaseViewHolder> {

    public AddressAdapter(int layoutResId, @Nullable List<Address> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Address item) {
        helper.setText(R.id.txt_address, item.getAddress());
        Glide.with(mContext).load(R.mipmap.header).into((ImageView) helper.getView(R.id.img_header));
        helper.addOnClickListener(R.id.img_delete);
    }
}
