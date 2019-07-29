package product.prison.ui.diy;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.config.What;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.tools.LtoDate;

/**
 * Created by zhu on 2017/8/28.
 */

public class Head extends LinearLayout {
    View view;

    App app;
    int retry = 0;
    Context context;

    public Head(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.head, this);
        app = (App) context.getApplicationContext();
        initview();

        setvalue();
    }


    ImageView head_logo;
    TextView head_time;

    private void initview() {
        head_logo = view.findViewById(R.id.head_logo);
        head_time = view.findViewById(R.id.head_time);

        if (context.getClass().getSimpleName().equals("MainActivity")) {
            if (head_time.getVisibility() != View.INVISIBLE)
                head_time.setVisibility(View.INVISIBLE);

        } else {
            if (head_time.getVisibility() != View.VISIBLE)
                head_time.setVisibility(View.VISIBLE);
        }
    }


    private void setvalue() {
        handler.sendEmptyMessage(What.HeadLogo);
        handler.sendEmptyMessage(What.HeadTime);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case What.HeadTime:
//                    head_time.setText("666");
                    head_time.setText(LtoDate.Hm(App.internettime));
                    handler.sendEmptyMessageDelayed(What.HeadTime, 1 * 1000);
                    break;
                case What.HeadLogo:
                    if (app.getLogo() != null) {
                        head_logo.setImageBitmap(app.getLogo());
                    } else {
                        if (retry < What.retry_times) {
                            handler.sendEmptyMessageDelayed(What.HeadLogo, 1 * 1000);
                            retry++;
                        }
                    }
                    break;
            }
        }
    };
}
