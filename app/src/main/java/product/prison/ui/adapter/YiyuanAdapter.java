package product.prison.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import product.prison.R;
import product.prison.bean.Details;
import product.prison.bean.Info;

/**
 * Created by zhu on 2017/9/26.
 */

public class YiyuanAdapter extends RecyclerView.Adapter<YiyuanAdapter.ViewHolder> implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    List<Details> menus;
    Context context;



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView left_list_icon;
        TextView left_list_name;

        public ViewHolder(View v) {
            super(v);

            left_list_icon = v.findViewById(R.id.left_list_icon);
            left_list_name = v.findViewById(R.id.left_list_name);
            v.setOnClickListener(YiyuanAdapter.this);
        }
    }
    public YiyuanAdapter(Context context, List<Details> infos) {
        this.context = context;
        this.menus = infos;
    }

    private OnItemClickListener mOnItemClickListener = null;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_app, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        System.out.println(menus.get(position).getName()+"@@@@@");
        Picasso.with(context).load(menus.get(position).getIcon()).into(holder.left_list_icon);
        holder.left_list_name.setText(menus.get(position).getName());


        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
