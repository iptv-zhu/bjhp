package product.prison.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import product.prison.R;
import product.prison.bean.Food;

public class ShopOrderAdapter extends BaseAdapter {
	private Context context;
	private List<Food> list;

	public ShopOrderAdapter(Context context, List<Food> lists) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = lists;
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

	private View view;
	private TextView shop_order_no, shop_order_name, shop_num,
			shop_order_price;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		view = LayoutInflater.from(context).inflate(
				R.layout.adapter_shop_order, null);

		shop_order_no =  view.findViewById(R.id.shop_order_no);
		shop_order_name =  view.findViewById(R.id.shop_order_name);
		shop_num =  view.findViewById(R.id.shop_num);
		shop_order_price =  view.findViewById(R.id.shop_order_price);
		try {
			shop_order_no.setText((position + 1) + "");
			shop_order_name.setText(list.get(position).getName());
			shop_num.setText(list.get(position).getCount());
			shop_order_price.setText("￥" + list.get(position).getPrice());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return view;
	}

}
