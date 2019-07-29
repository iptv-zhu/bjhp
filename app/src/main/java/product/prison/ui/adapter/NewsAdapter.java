package product.prison.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import product.prison.R;
import product.prison.bean.NewsData;
import product.prison.bean.NewsData2;
import product.prison.nbean.ResData;
import product.prison.tools.RsType;

public class NewsAdapter extends BaseAdapter {

    private List<NewsData2> list;
    private Context context;

    public NewsAdapter(Context context, List<NewsData2> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        try {
            return list.size();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
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
    private ImageView vod_img;
    private TextView vod_name;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(context).inflate(R.layout.adapter_res2,
                null);
        try {
            vod_name = view.findViewById(R.id.vod_name);
            vod_img = view.findViewById(R.id.vod_img);

            vod_name.setText(list.get(position).getName());
            Picasso.with(context).load(list.get(position).getIcon()).into(vod_img);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return view;
    }
}
