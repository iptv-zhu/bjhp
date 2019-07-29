package product.prison.ui.diy;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import product.prison.R;
import product.prison.app.App;
import product.prison.config.What;
import product.prison.ui.msg.EmailActivity;

/**
 * Created by zhu on 2017/8/28.
 */

public class Bottom18 extends LinearLayout implements View.OnClickListener {
    View view;
    Context context;
    App app;

    public Bottom18(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.bottom18, this);
        this.context = context;
        app = (App) context.getApplicationContext();
        initview();

        setvalue();
    }

    private void setvalue() {
        handler.sendEmptyMessage(What.getNotice);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case What.getNotice:
                    if (!app.getReademail().isEmpty()) {
                        bottom_msg_tips.setText(context.getString(R.string.bottom_msg_tips));
                        bottom_msg.setBackgroundResource(R.drawable.bottom_message_tip);
                    } else {
                        bottom_msg_tips.setText("");
                        bottom_msg.setBackgroundResource(R.drawable.bottom_message);
                    }

                    handler.sendEmptyMessageDelayed(What.getNotice, 5 * 1000);
                    break;
            }
        }
    };


    ImageButton bottom_msg;
    TextView bottom_msg_tips;

    private void initview() {
        bottom_msg = view.findViewById(R.id.bottom_msg);
        bottom_msg_tips = view.findViewById(R.id.bottom_msg_tips);
        bottom_msg.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == bottom_msg) {
            context.startActivity(new Intent(context, EmailActivity.class));
        }

    }
}
