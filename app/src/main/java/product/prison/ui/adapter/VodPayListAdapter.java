package product.prison.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;

/**
 * Created by zhu on 2017/9/26.
 */

public class VodPayListAdapter extends BaseAdapter {
    private Activity activity;
    private List<String> list = new ArrayList<>();

    public VodPayListAdapter(Activity activity, List<String> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.adapter_pay_list, null);
            holder.vod_pay_rb = convertView.findViewById(R.id.vod_pay_rb);
            holder.vod_pay_money = convertView.findViewById(R.id.vod_pay_money);
            holder.vod_pay_money2 = convertView.findViewById(R.id.vod_pay_money2);
            holder.pay_icon = convertView.findViewById(R.id.pay_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.vod_pay_money2.setText(activity.getString(R.string.vod_pay_money).replace("x", list.get(position)));
        if (position == 0) {
            holder.vod_pay_money.setText(activity.getString(R.string.vod_pay_time));
        } else if (position == 1) {
            holder.vod_pay_money.setText(activity.getString(R.string.vod_pay_day));
            holder.pay_icon.setVisibility(View.VISIBLE);
        }
        holder.vod_pay_rb.setClickable(false);

        if (selectedIndex == position) {
            holder.vod_pay_rb.setChecked(true);
        } else {
            holder.vod_pay_rb.setChecked(false);
        }
        return convertView;

    }

    int selectedIndex;

    public void setSelectedIndex(int position) {
        this.selectedIndex = position;
        notifyDataSetChanged();
    }


    public class ViewHolder {
        private RadioButton vod_pay_rb;
        private TextView vod_pay_money;
        private TextView vod_pay_money2;
        private ImageView pay_icon;
    }

}
