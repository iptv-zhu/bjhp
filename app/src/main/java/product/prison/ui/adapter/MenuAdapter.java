package product.prison.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import product.prison.R;
import product.prison.bean.Menu;

/**
 * Created by zhu on 2017/9/26.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> implements View.OnClickListener {
        List<Menu> menus;
    int[] image = {R.drawable.index_icon, R.drawable.video_icon, R.drawable.live_icon, R.drawable.record_icon, R.drawable.use_icon, R.drawable.set_icon,};
    String[] name = {"首页", "点播", "直播", "录播回放", "使用说明", "设置功能"};

//    int[] image = {R.drawable.index_icon, R.drawable.video_icon, R.drawable.live_icon, R.drawable.record_icon, R.drawable.set_icon,};
//    String[] name = {"首页", "点播", "直播", "录播回放", "设置功能"};
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        RelativeLayout menu_type;

        public ViewHolder(View v) {
            super(v);

            menu_type = v.findViewById(R.id.menu_type);
            mImageView = v.findViewById(R.id.main_menu_icon);
            mTextView = v.findViewById(R.id.main_menu_text);
            v.setOnClickListener(MenuAdapter.this);
        }
    }

    public MenuAdapter(Context context,List<Menu> menus) {
        this.context = context;
        this.menus=menus;
    }

//    public MenuAdapter(Context context) {
//        this.context = context;
//    }

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            String url = menus.get(position).getPath();
            if (!url.isEmpty()) {
                Picasso.with(context).load(url).error(R.drawable.pic).into(holder.mImageView);
            }
//            if (position % 2 == 0) {
//                holder.menu_type.setBackgroundResource(R.drawable.menu_type1);
//            } else {
//                holder.menu_type.setBackgroundResource(R.drawable.menu_type2);
//            }
//            holder.mTextView.setText(name[position]);
            holder.mTextView.setText(menus.get(position).getName());
            holder.itemView.setTag(position);
//            holder.mImageView.setBackgroundResource(image[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        try {
            return menus.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  0;
    }
}
