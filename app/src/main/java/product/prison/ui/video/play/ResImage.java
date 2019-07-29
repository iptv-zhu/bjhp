package product.prison.ui.video.play;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.config.What;
import product.prison.nbean.Res;
import product.prison.nbean.ResData;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.ui.BaseActivity;


public class ResImage extends BaseActivity {
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.res_image);

        app = (App) getApplication();

        initview();

        setvalue();
    }

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            ResData resData = (ResData) getIntent().getExtras()
                    .getSerializable("key");
            res_image_title.setText(resData.getName());
            Picasso.with(this).load(resData.getPath()).into(res_image);
            req(resData.getId());
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void req( int id) {
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

    ImageView res_image;
    TextView res_image_title;

    private void initview() {
        // TODO Auto-generated method stub
        res_image = (ImageView) findViewById(R.id.res_image);
        res_image_title = (TextView) findViewById(R.id.res_image_title);
    }

}