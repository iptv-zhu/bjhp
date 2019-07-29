package product.prison.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.bean.Game;


public class GameAdapter extends BaseAdapter {
    private Context context;
    private List<Game> list = new ArrayList<>();

    public GameAdapter(Context context, List<Game> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.adapter_game, null);
            holder.game_bg = convertView
                    .findViewById(R.id.game_bg);
            holder.game_name = convertView
                    .findViewById(R.id.game_name);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.game_name.setText(list.get(position).getName());

        Picasso.with(context).load(list.get(position).getIcon()).into(holder.game_bg);

        return convertView;

    }


    public class ViewHolder {
        private ImageView game_bg;
        private TextView game_name;

    }

}
