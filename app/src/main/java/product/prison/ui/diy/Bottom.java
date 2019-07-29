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

public class Bottom extends LinearLayout {
    View view;
    Context context;
    App app;

    public Bottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.bottom, this);
        this.context = context;
        app = (App) context.getApplicationContext();
    }

}
