package product.prison.ui.video.play;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.config.What;
import product.prison.nbean.Res;
import product.prison.nbean.ResData;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.ui.BaseActivity;


public class ResTxt extends BaseActivity {
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

        ResData resData = (ResData) getIntent().getExtras().getSerializable(
                "key");
//		new Req(this, null).play(resData.getId(), What.play);
        msgtitle.setText(resData.getName());

        msgweb.loadUrl(resData.getPath());
        msgweb.getSettings().setDefaultTextEncodingName("GBK");
        // msgweb.getSettings().setDefaultTextEncodingName("UTF-8");
        // msgweb.loadDataWithBaseURL(null, resData.getPath(), "text/html",
        // "utf-8", null);

        System.out.println(resData.getPath());
        req(resData.getId());

    }

    private void req(int id) {
        String api = "userLoginAction_addVisitCount";
        String url = App.requrl(api, "&id=" + id);
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);
                            GsonBean<Res> json = App.gson.fromJson(result,
                                    new TypeToken<GsonBean<Res>>() {
                                    }.getType());
                            if (json.getData() != null) {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        return;
                    }
                }, false);

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
