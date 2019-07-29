package product.prison.ui.live;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.LiveCat;
import product.prison.tools.AppTool;
import product.prison.tools.WebInstaller;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.LiveAdapter;

/**
 * Created by zhu on 2017/9/26.
 */

public class CLiveActivity extends BaseActivity implements LiveAdapter.OnItemClickListener {
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        app = (App) getApplication();
        initview();
        setvalue();
    }


    RecyclerView left_list;
    LinearLayoutManager layoutmanager;


    private void initview() {
        left_list = $(R.id.left_list);

        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        left_list.setLayoutManager(layoutmanager);

    }

    LiveAdapter adapter;
    List<LiveCat> subses = new ArrayList<>();

    private void setvalue() {
        try {

            for (int i = 0; i < 2; i++) {
                LiveCat liveCat = new LiveCat();

                if (i == 0) {
                    liveCat.setId(-1);
                    liveCat.setName(getString(R.string.live_cat1));
                    liveCat.setIcon(R.drawable.live_cat1);
                } else if (i == 1) {
                    liveCat.setId(-2);
                    liveCat.setName(getString(R.string.live_cat2));
                    liveCat.setIcon(R.drawable.live_cat2);
                }
                subses.add(liveCat);
            }

            if (!app.getLive().getApps().isEmpty()) {
                for (int i = 0; i < app.getLive().getApps().size(); i++) {
                    LiveCat liveCat = new LiveCat();
                    liveCat.setId(app.getLive().getApps().get(i).getId());
                    liveCat.setName(app.getLive().getApps().get(i).getName());
                    liveCat.setIcon(R.drawable.live_cat3);
                    subses.add(liveCat);
                }
            }

            if (!subses.isEmpty()) {
                adapter = new LiveAdapter(this, subses);
                left_list.setAdapter(adapter);
                adapter.setOnItemClickListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//1.数字  2.模拟  3.其他

    private void toFram(int position) {

        try {
            switch (subses.get(position).getId()) {
                case -1:
                    startActivity(new Intent(this, IPLiveActivity.class));
                    break;
                case -2:
                    try {
                        ComponentName componentName = new ComponentName(
                                "com.mstar.tv.tvplayer.ui",
                                "com.mstar.tv.tvplayer.ui.RootActivity");
                        startActivity(new Intent().setComponent(componentName));
                    } catch (Exception e) {
                        AppTool.toast(this, getString(R.string.live_cat2_error), 0, Gravity.CENTER, 0, 0);
                        e.printStackTrace();
                    }
                    break;
                default:

                    if (AppTool.IsInstall(this, app.getLive().getApps().get(position - 2).getPackage_name())) {
                        AppTool.Ostart(this, app.getLive().getApps().get(position - 2).getPackage_name(), "");
                    } else {
                        new WebInstaller(this, app.getLive().getApps().get(position - 2).getPath())
                                .downloadAndInstall(this
                                        .getString(R.string.Downloading_live));
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        System.out.println(position + "*****");


        toFram(position);
    }
}
