package product.prison.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import product.prison.R;
import product.prison.bean.Email;

public class EmailAdapter extends BaseAdapter {
    private Context context;
    private List<Email> list;

    public EmailAdapter(Context context, List<Email> list) {
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

    private int current;

    public void change(int current) {
        // TODO Auto-generated method stub
        this.current = current;
        this.notifyDataSetChanged();

    }

    View view;
    TextView email_time, email_title, email_content;
    TextView email_status;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        view = LayoutInflater.from(context).inflate(R.layout.adapter_email,
                null);

        try {
            email_status = view.findViewById(R.id.email_status);

            if (list.get(position).getReadStatus() == 1) {
                email_status.setBackgroundResource(R.drawable.email_ico_r);
            } else {
                email_status.setBackgroundResource(R.drawable.email_ico_p);
            }

            email_time = view.findViewById(R.id.email_time);

            email_time.setText(date(list.get(position).getCreatetime()));

            email_title = view.findViewById(R.id.email_title);
            email_title.setText(list.get(position).getNotifyNews());

        } catch (Exception e) {
            // TODO: handle exception
        }
        return view;
    }

    private String date(long string) {
        // TODO Auto-generated method stub
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd");
            Date date = new Date(string);
            return dateFormat.format(string);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

}
