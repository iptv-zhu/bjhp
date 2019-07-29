package product.prison.ui.video.play;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.VodDetails;
import product.prison.ui.BaseActivity;


public class ResOffice extends BaseActivity {
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        app = (App) getApplication();
        setContentView(R.layout.res_web);

        initview();
        setvalue();

    }

    private void setvalue() {
        // TODO Auto-generated method stub

        VodDetails resData = (VodDetails) getIntent().getExtras().getSerializable(
                "key");
//		new Req(this, null).play(resData.getId(), What.play);
        msgtitle.setText(resData.getName());

        msgweb.loadUrl(resData.getFilePath());
        msgweb.getSettings().setDefaultTextEncodingName("GBK");
        // msgweb.getSettings().setDefaultTextEncodingName("UTF-8");
        // msgweb.loadDataWithBaseURL(null, resData.getPath(), "text/html",
        // "utf-8", null);

        System.out.println(resData.getFilePath());
    }

    WebView msgweb;
    TextView msgtitle;

    private void initview() {
        // TODO Auto-generated method stub

        msgtitle = (TextView) findViewById(R.id.msgtitle);
        msgweb = (WebView) findViewById(R.id.msgweb);
        WebSettings websettings = msgweb.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(true);
        msgweb.setBackgroundColor(Color.TRANSPARENT);
        msgweb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

}
