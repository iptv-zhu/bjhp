package product.prison.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.bean.VodDetails;

/**
 * Created by zhu on 2017/10/10.
 */

public class TeleplayGridAdapter extends BaseAdapter {
    private Activity activity;
    private List<VodDetails> list = new ArrayList<>();

    public TeleplayGridAdapter(Activity activity, List<VodDetails> list) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.adapter_teleplay, null);
            holder.teleplay_name = convertView
                    .findViewById(R.id.teleplay_name);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.teleplay_name.setText((position + 1) + "");


        return convertView;

    }


    public class ViewHolder {
        private TextView teleplay_name;

    }

}
