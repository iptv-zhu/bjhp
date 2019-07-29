package product.prison.ui.res;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.config.What;
import product.prison.nbean.Dir;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.DirAdapter;

public class ResActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private App app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);
        app = (App) getApplication();
        initview();
        setvalue();
    }

    GridView res_list;

    private void initview() {
        res_list = $(R.id.res_list);
        res_list.setOnItemClickListener(this);
    }


    private void setvalue() {
        try {

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    getfile();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    List<Dir> dirs;

    private void getfile() {
        String api = "getFileAction_getDir";
        String url = App.requrl(api, "");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);
                            GsonBean<List<Dir>> json = App.gson.fromJson(result,
                                    new TypeToken<GsonBean<List<Dir>>>() {
                                    }.getType());
                            if (json.getData() != null) {
                                dirs = json.getData();
                                res_list.setAdapter(new DirAdapter(ResActivity.this, dirs));
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        if (parent == res_list) {

            final int p=position;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("key", dirs.get(p));

                    startActivity(new Intent(ResActivity.this, ResActivity2.class)
                            .putExtras(bundle));
                }
            });


        }
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
