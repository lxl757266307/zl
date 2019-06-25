package cn.org.happyhome.ordertaking.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.org.happyhome.ordertaking.R;
import cn.org.happyhome.ordertaking.bean.LocationBean;

public class LocationAdapter extends BaseQuickAdapter<LocationBean, BaseViewHolder> {
    public LocationAdapter(@Nullable List<LocationBean> data) {
        super(R.layout.item_location, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationBean item) {
        helper.setText(R.id.txt_poi_name, item.getName());
    }
}
