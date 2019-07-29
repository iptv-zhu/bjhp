package product.prison.ui.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import product.prison.R;
import product.prison.app.App;
import product.prison.ui.BaseActivity;
import product.prison.ui.BaseFram;

/**
 * Created by zhu on 2017/9/26.
 */

public class WebActivity extends BaseFram {

    private App app;


    Context context;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        app = (App) context.getApplicationContext();
        view = inflater.inflate(R.layout.activity_web, container, false);


        initview();
        setvalue();
        return view;
    }

    private String Home = "https://www.baidu.com/";

    private void setvalue() {
        try {
            WebSettings websettings = fram_web.getSettings();
            websettings.setJavaScriptEnabled(true);
            websettings.setBuiltInZoomControls(true);
            fram_web.loadUrl(Home);
            fram_web.setBackgroundColor(Color.TRANSPARENT);
            fram_web.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    WebView fram_web;

    private void initview() {
        fram_web = view.findViewById(R.id.fram_web);
    }


}
