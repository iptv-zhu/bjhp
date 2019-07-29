package product.prison.ui.app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Subs;
import product.prison.tools.FragmentTools;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.AppAdapter;

/**
 * Created by zhu on 2017/9/26.
 */

public class AppActivity extends BaseActivity implements AppAdapter.OnItemClickListener {
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


    AppAdapter adapter;
    List<Subs> subses = new ArrayList<Subs>();

    private void setvalue() {
        subses = app.getSubses();

        if (!subses.isEmpty()) {
            adapter = new AppAdapter(this, subses);
            left_list.setAdapter(adapter);
            adapter.setOnItemClickListener(this);

            toFram(0);
        }


    }


//6 互动应用{7WIFI 8浏览器  9互动应用  10客房智控  11语言选择  12网络设置   13多屏互动   14 操作说明 }

    private void toFram(int position) {
        System.out.println(position);
        switch (subses.get(position).getId()) {
            case 7:
                FragmentTools.Replace(getFragmentManager(),
                        new WifiActivity());
                break;
            case 8:
                FragmentTools.Replace(getFragmentManager(),
                        new WebActivity());

                break;
            case 9:
                FragmentTools.Replace(getFragmentManager(),
                        new GameActivity());
                break;
            case 10:
                FragmentTools.Replace(getFragmentManager(),
                        new ControlActivity());
                break;
            case 11:
                FragmentTools.Replace(getFragmentManager(),
                        new LangActivity());
                break;
            case 12:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                break;
            case 13:
                FragmentTools.Replace(getFragmentManager(),
                        new ShareActivity());
                break;
            case 14:
                FragmentTools.Replace(getFragmentManager(),
                        new UsefulActivity());
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        toFram(position);
    }
}
