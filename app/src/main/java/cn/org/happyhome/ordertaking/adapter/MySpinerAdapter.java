package cn.org.happyhome.ordertaking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.org.happyhome.ordertaking.R;
import cn.org.happyhome.ordertaking.bean.OrgBean;

public class MySpinerAdapter extends BaseAdapter {
    List<OrgBean> list;
    Context context;

    public MySpinerAdapter(List<OrgBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spiner, parent, false);
            new ViewHolder(convertView);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textView.setText(list.get(position).getOrganizationname());
        return convertView;
    }

    class ViewHolder {
        TextView textView;

        public ViewHolder(View view) {
            view.setTag(this);
            textView = view.findViewById(R.id.txt_name);
        }
    }
}
