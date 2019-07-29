package product.prison.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import product.prison.R;
import product.prison.nbean.Dir;


/**
 * Created by zhu on 2017/9/26.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String menus[] = {"主要新闻","监区新闻","大墙内外"};
    Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView left_list_icon;
        TextView left_list_name;

        public ViewHolder(View v) {
            super(v);

            left_list_icon = v.findViewById(R.id.left_list_icon);
            left_list_name = v.findViewById(R.id.left_list_name);
            left_list_name.setFocusable(true);
            v.setOnClickListener(NewsListAdapter.this);
        }
    }

    public NewsListAdapter(Context context) {
        this.context = context;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_app2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        holder.left_list_name.setText(menus[position]);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return menus.length;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
