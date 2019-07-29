package product.prison.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.bean.IntroduceData;
import product.prison.bean.NewsData;
import product.prison.bean.NewsData2;
import product.prison.config.What;
import product.prison.nbean.Dir;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.tools.AppTool;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.DirListAdapter;
import product.prison.ui.adapter.NewsAdapter;
import product.prison.ui.adapter.NewsListAdapter;
import product.prison.ui.adapter.ResAdapter2;
import product.prison.ui.res.ResActivity2;
import product.prison.ui.video.play.ResTxt;

/**
 * Created by zhu on 2017/9/26.
 */

public class NewsActivity extends BaseActivity implements AdapterView.OnItemClickListener, NewsListAdapter.OnItemClickListener {

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res2);
        app = (App) getApplication();

        initview();
        setvalue();
    }


    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            adapter = new NewsListAdapter(NewsActivity.this);
            left_list.setAdapter(adapter);
            adapter.setOnItemClickListener(NewsActivity.this);
            getdirlist(1);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    NewsListAdapter adapter;

    private void getdirlist(int p) {
        String api = "prison_list";
        String url = App.requrl(api, "&type=" + p+ "&pageNo=1&pageSize=99999");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);
                            GsonBean<NewsData> json = App.gson.fromJson(result,
                                    new TypeToken<GsonBean<NewsData>>() {
                                    }.getType());
                            griddir = json.getData().getData();
                            res2_grid.setAdapter(new NewsAdapter(NewsActivity.this,
                                    griddir));
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
    public void onItemClick(View view, int position) {
        try {
            getdirlist(position + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    List<NewsData2> griddir = new ArrayList<>();


    RecyclerView left_list;
    LinearLayoutManager layoutmanager;
    GridView res2_grid;

    LinearLayout res_line;

    private void initview() {
        left_list = $(R.id.left_list);
        res_line = $(R.id.res_line);
        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        left_list.setLayoutManager(layoutmanager);
        res2_grid = $(R.id.res2_grid);
        res2_grid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

        if (parent == res2_grid) {

            try {
                System.out.println("#############2" + position);
//                String path = griddir.get(position).getDes();
//                String temp = path.substring(path.lastIndexOf("."));
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", griddir.get(position));
                intent.setClass(NewsActivity.this, NewsWeb.class);
                startActivity(intent.putExtras(bundle));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
