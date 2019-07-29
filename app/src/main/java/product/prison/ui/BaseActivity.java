package product.prison.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;

import product.prison.app.App;
import product.prison.config.What;
import product.prison.ui.dialog.ServerIpDialog;

/**
 * Created by zhu on 2017/8/24.
 */

public class BaseActivity extends Activity implements View.OnClickListener {

    public <T, View> T $(int id) {
        return (T) super.findViewById(id);
    }


    App app;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case What.BG:
                    try {
                        if (app.getBg() != null) {
                            getWindow().getDecorView().setBackground(new BitmapDrawable(app.getBg()));
                        } else {
                            if (retry < What.retry_times) {
                                handler.sendEmptyMessageDelayed(What.BG, 1 * 1000);
                                retry++;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    if (key_temp.equals(App.SetServerIP)
                            || key_temp.equals(App.admin)) {
                        new ServerIpDialog(handler, BaseActivity.this).crt();
                    } else if (key_temp.equals(App.SystemSet)
                            || key_temp.equals(App.admin)) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                    key_temp = "";
                    break;
            }
        }
    };

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();
        activity = this;
        initview();
        setvalue();
        setThemes();
    }


    int retry;

    private void setThemes() {

        if (!this.getClass().getSimpleName().equals("WelcomeActivity") &&
                !this.getClass().getSimpleName().equals("IPLiveActivity")) {
            handler.sendEmptyMessage(What.BG);
        }

    }

    @Override
    protected void onDestroy() {
        handler.removeMessages(What.BG);
        super.onDestroy();
    }


    private void initview() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setvalue() {

    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            handler.removeMessages(0);
            key_temp += keyCode - 7;
            handler.sendEmptyMessageDelayed(0, 1 * 1000);
        }
        return super.onKeyDown(keyCode, event);
    }

    private String key_temp = "";


}
