package product.prison.ui.zhinan;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.IntroduceData;
import product.prison.ui.BaseFram;
import product.prison.ui.adapter.GalleryAdapter;

public class IntroduceActivity2 extends BaseFram {

    private App app;
    Context context;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        app = (App) context.getApplicationContext();
        view = inflater.inflate(R.layout.activity_webinfo2, container, false);


        initview();
        setvalue();
        return view;
    }

    TextView webinfo_title;
    WebView webinfo_web;
    ImageView webinfo_img;
    LinearLayout webinfo_line2;
    WebView webinfo_otweb;

    private void initview() {
        webinfo_title = view.findViewById(R.id.webinfo_title);
        webinfo_web = view.findViewById(R.id.webinfo_web);
        webinfo_img = view.findViewById(R.id.webinfo_img);
        WebSettings websettings = webinfo_web.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(true);
        webinfo_web.setBackgroundColor(Color.TRANSPARENT);
        webinfo_web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        webinfo_line2 = view.findViewById(R.id.webinfo_line2);
        webinfo_otweb = view.findViewById(R.id.webinfo_otweb);
        WebSettings settings2 = webinfo_otweb.getSettings();
        settings2.setJavaScriptEnabled(true);
        settings2.setBuiltInZoomControls(true);
        webinfo_otweb.setBackgroundColor(Color.TRANSPARENT);
        webinfo_otweb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }


    IntroduceData details;

    GalleryAdapter galleryAdapter;

    private void setvalue() {
        try {
            details = (IntroduceData) getArguments().get("key");
            setdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setdate() {
        try {
            String name = details.getName();

            if (webinfo_line2.getVisibility() == View.GONE) {
                webinfo_line2.setVisibility(View.VISIBLE);
                webinfo_otweb.setVisibility(View.GONE);
            }
            webinfo_title.setText(name);
            webinfo_web.loadDataWithBaseURL(null, details.getDes(),
                    "text/html", "utf-8", null);
//            Picasso.with(context).load(details.getIcon()).error(R.drawable.error_img).into(webinfo_img);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
