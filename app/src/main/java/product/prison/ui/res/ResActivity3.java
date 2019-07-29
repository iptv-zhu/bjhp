package product.prison.ui.res;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.nbean.Res;
import product.prison.nbean.ResData;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.tools.AppTool;
import product.prison.tools.RsType;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.ResAdapter2;
import product.prison.ui.video.play.ResAudio;
import product.prison.ui.video.play.ResImage;
import product.prison.ui.video.play.ResTxt;
import product.prison.ui.video.play.ResVideo;

/**
 * Created by zhu on 2017/9/26.
 */

public class ResActivity3 extends BaseActivity implements AdapterView.OnItemClickListener {
    int pageNo = 1;
    int type;
    int pageSize = 99999;
    int maxpageSize;

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res3);
        app = (App) getApplication();

        initview();
        setvalue();
    }

    private ResData resData;

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            resData = (ResData) getIntent().getSerializableExtra("key");
            if (resData != null) {
                getDirF();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


    List<ResData> griddir = new ArrayList<ResData>();


    String filedir;

    private void getDirF() {
        if (resData.getId() > 0) {
            filedir = "&file.dir=" + resData.getId();
        }
        String api = "getFileAction_getSourceFile";
        String url = App.requrl(api, filedir
                + "&pageNo=" + pageNo + "&pageSize=" + pageSize + "&sub=true");
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
                                try {
                                    griddir = json.getData().getData();
                                    res2_grid.setAdapter(new ResAdapter2(ResActivity3.this, griddir));
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
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


    GridView res2_grid;


    private void initview() {

        res2_grid = $(R.id.res2_grid);
        res2_grid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

        if (parent == res2_grid) {
            try {
                String path = griddir.get(position).getPath();
                String temp = path.substring(path.lastIndexOf("."));
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putSerializable("key", griddir.get(position));
                System.out.println(path + "*************" + temp);
                switch (RsType.type.get(temp.toLowerCase())) {
                    case 1:
                        intent.setClass(ResActivity3.this, ResImage.class);
                        break;
                    case 2:
                        intent.setClass(ResActivity3.this, ResAudio.class);
                        break;
                    case 3:
                        intent.setClass(ResActivity3.this, ResVideo.class);
                        break;
                    case 4:
                        intent.setClass(ResActivity3.this, ResTxt.class);
                        break;

                    default:
                        break;
                }
                startActivity(intent.putExtras(bundle));
            } catch (Exception e) {
                // TODO: handle exception
                AppTool.toast(ResActivity3.this, getString(R.string.play_error), 0, Gravity.CENTER, 0, 0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
