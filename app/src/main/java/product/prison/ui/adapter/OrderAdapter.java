package product.prison.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import product.prison.R;
import product.prison.bean.Bills;
import product.prison.tools.LtoDate;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Bills> list;

    public OrderAdapter(Context context, List<Bills> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        try {
            if (list.size() < 7) {
                return 7;
            } else {
                return list.size();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 7;
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

    View view;
    TextView order_date, order_no, order_time, order_msg, order_price,
            order_num, order_total;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        view = LayoutInflater.from(context).inflate(R.layout.adapter_order,
                null);

        try {

            order_no = (TextView) view.findViewById(R.id.order_no);

            if (position < list.size()) {
                order_no.setText((position + 1) + "");
            }


            order_time = (TextView) view.findViewById(R.id.order_time);
            order_time.setText(LtoDate.HMmd(new Date(list.get(position).getDate()).getTime()));

            order_msg = (TextView) view.findViewById(R.id.order_msg);

            order_msg.setText(list.get(position).getContent());

            order_price = (TextView) view.findViewById(R.id.order_price);

            order_price.setText(list.get(position).getPrice());

            order_num = (TextView) view.findViewById(R.id.order_num);

            order_num.setText(list.get(position).getCount());

            order_total = (TextView) view.findViewById(R.id.order_total);

            order_total.setText(list.get(position).getTotal());

        } catch (Exception e) {
            // TODO: handle exception
        }
        return view;
    }


}
